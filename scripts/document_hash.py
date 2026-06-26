#!/usr/bin/env python3
"""document_hash.py — Compute SHA-256 per Clarity Gate FORMAT_SPEC §2.2-2.4.

Algorithm:
1. Extract content between opening `---\n` and `<!-- CLARITY_GATE_END -->`
2. Remove `document-sha256` line from YAML frontmatter (with multiline continuation)
3. Canonicalize: strip trailing whitespace, collapse 3+ newlines to 2,
   normalize final newline (exactly 1 LF), UTF-8 NFC normalization
4. Compute SHA-256
"""

import sys
import re
import unicodedata
import hashlib


def canonicalize(text: str) -> str:
    # BOM removal
    if text.startswith('\ufeff'):
        text = text[1:]
    # CRLF → LF
    text = text.replace('\r\n', '\n')
    # CR → LF (old Mac)
    text = text.replace('\r', '\n')
    # NFC normalization
    text = unicodedata.normalize('NFC', text)
    # Strip trailing whitespace per line
    lines = text.split('\n')
    lines = [line.rstrip() for line in lines]
    text = '\n'.join(lines)
    # Collapse 3+ consecutive newlines to 2
    text = re.sub(r'\n{3,}', '\n\n', text)
    # Normalize final newline: exactly 1 LF
    text = text.rstrip('\n') + '\n'
    return text


def extract_cgd_content(raw: str) -> str:
    # Find opening YAML delimiter
    yaml_start = 0
    if raw.startswith('---\n'):
        yaml_start = 4
    else:
        # Try to find --- anywhere
        m = re.match(r'\s*---\n', raw)
        if m:
            yaml_start = m.end()
        else:
            raise ValueError("No opening YAML delimiter `---` found")

    # Find CLARITY_GATE_END marker
    end_marker = '<!-- CLARITY_GATE_END -->'
    end_pos = raw.find(end_marker)
    if end_pos == -1:
        raise ValueError("No `<!-- CLARITY_GATE_END -->` marker found")

    # Content is from after opening `---\n` to before `<!-- CLARITY_GATE_END -->`
    content = raw[yaml_start:end_pos]

    # Remove `document-sha256` line from YAML frontmatter
    # The frontmatter is from start of content to first blank line + non-YAML content
    # Actually we need to find the end of YAML frontmatter: the closing `---\n`
    # The content starts after the opening `---\n` and goes to `<!-- CLARITY_GATE_END -->`
    # Within that, the frontmatter ends at the closing `---\n`

    # Find the closing `---` of frontmatter within content
    fm_end = content.find('\n---\n')
    if fm_end == -1:
        fm_end = content.find('\n---')
    if fm_end != -1:
        frontmatter = content[:fm_end]
        rest = content[fm_end:]

        # Remove document-sha256 line (with multiline continuation support)
        # Pattern: line starting with `document-sha256:` optionally continuing on next lines
        lines = frontmatter.split('\n')
        filtered = []
        skip = False
        for line in lines:
            if skip:
                # Check if this line continues the previous (indented or continuation)
                if line.startswith(' ') or line.startswith('\t'):
                    continue
                else:
                    skip = False
            if line.startswith('document-sha256:'):
                skip = True
                continue
            if not skip:
                filtered.append(line)
        frontmatter = '\n'.join(filtered)
        content = frontmatter + rest

    return content


def compute_hash(filepath: str) -> str:
    with open(filepath, 'r', encoding='utf-8') as f:
        raw = f.read()

    extracted = extract_cgd_content(raw)
    canonical = canonicalize(extracted)
    sha = hashlib.sha256(canonical.encode('utf-8')).hexdigest()
    return sha


def verify_hash(filepath: str) -> bool:
    with open(filepath, 'r', encoding='utf-8') as f:
        raw = f.read()

    # Extract current hash from frontmatter
    m = re.search(r'^document-sha256:\s*([a-f0-9]{64})', raw, re.MULTILINE)
    if not m:
        print(f"FAIL: No document-sha256 found in {filepath}")
        return False

    expected = m.group(1)
    computed = compute_hash(filepath)
    if computed == expected:
        print(f"PASS: Hash verified: {computed}")
        return True
    else:
        print(f"FAIL: Hash mismatch. Expected: {expected}, Computed: {computed}")
        return False


def insert_hash(filepath: str, dry_run: bool = False) -> str:
    sha = compute_hash(filepath)
    with open(filepath, 'r', encoding='utf-8') as f:
        raw = f.read()

    # Replace PENDING or existing hash
    new_raw = re.sub(
        r'^document-sha256:\s*.+$',
        f'document-sha256: {sha}',
        raw,
        count=1,
        flags=re.MULTILINE
    )

    if dry_run:
        return sha

    with open(filepath, 'w', encoding='utf-8') as f:
        f.write(new_raw)
    return sha


def run_tests():
    """Run test vectors from FORMAT_SPEC."""
    print("Running document_hash.py --test")
    print()

    # Test canonicalize
    assert canonicalize('hello\n') == 'hello\n'
    assert canonicalize('hello\r\nworld\r\n') == 'hello\nworld\n'
    assert canonicalize('hello\rworld\r') == 'hello\nworld\n'
    assert canonicalize('hello   \nworld\n') == 'hello\nworld\n'
    assert canonicalize('a\n\n\n\nb\n') == 'a\n\nb\n'
    assert canonicalize('\ufeffhello\n') == 'hello\n'
    assert canonicalize(' Héllo\n') == ' Héllo\n'  # NFC

    # Test with a simple CGD structure
    test_cgd = """---
key: value
document-sha256: PENDING
other: data
---

# Test

Content here

<!-- CLARITY_GATE_END -->
Clarity Gate: CLEAR | REVIEWED
"""
    extracted = extract_cgd_content(test_cgd)
    assert 'document-sha256:' not in extracted, "document-sha256 should be removed"
    assert 'key: value' in extracted
    assert 'other: data' in extracted
    assert 'Content here' in extracted

    canonical = canonicalize(extracted)
    assert canonical.endswith('\n')
    assert '\n\n\n' not in canonical

    sha = hashlib.sha256(canonical.encode('utf-8')).hexdigest()
    assert len(sha) == 64

    print(f"All tests passed (hash example: {sha})")


if __name__ == '__main__':
    if '--test' in sys.argv:
        run_tests()
    elif '--verify' in sys.argv:
        idx = sys.argv.index('--verify')
        filepath = sys.argv[idx + 1]
        result = verify_hash(filepath)
        sys.exit(0 if result else 1)
    elif '--insert' in sys.argv:
        idx = sys.argv.index('--insert')
        filepaths = [a for a in sys.argv[idx + 1:] if not a.startswith('--')]
        for filepath in filepaths:
            sha = insert_hash(filepath)
            print(f"INSERTED {filepath}: {sha}")
    else:
        for filepath in sys.argv[1:]:
            if filepath.startswith('-'):
                continue
            sha = compute_hash(filepath)
            print(sha)
