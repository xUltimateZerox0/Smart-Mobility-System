# Clarity Gate v2.1 Assessment — Smart Mobility System

**Assessor:** opcode AI
**Date:** 2026-06-26
**Documents Assessed:**
1. `documentazione.md` (1469 lines, no version tag)
2. `Master_Spec.md` v5.0 (885 lines)
3. `Master_Spec.cgd.md` v4.0 (1202+ lines)
4. `chiarimenti-vari.md` (23 lines, reference)

---

## 1. Per-Document 9-Point Clarity Gate Score

### 1.1 documentazione.md

| # | Point | Score | Notes |
|---|-------|-------|-------|
| 1 | Hypothesis vs Fact Labeling | **FAIL** | No distinction between design intent and verified fact. Entire document is speculative (user stories, backlog, use case specs) presented as definitive. |
| 2 | Uncertainty Marker Enforcement | **FAIL** | Zero uncertainty markers. No "might", "TBD", "unclear", "hypothesized". Everything stated as absolute despite being a planning document. |
| 3 | Assumption Visibility | **PARTIAL** | Some assumptions surface (WiFi coverage in glossary §2.1, simulated externals §4.0, 2km/5km/15min defaults) but not structured as assumptions. No dedicated assumptions section. |
| 4 | Authoritative-Looking Unvalidated Data | **FAIL** | Very high risk. Detailed UC tables with flows, pre/post conditions, alternatives — all look implementation-ready but none are validated against working code. |
| 5 | Data Consistency | **PARTIAL** | Internal inconsistency: UT.08 listed as **non-functional** in §1.5.1 but as functional UC.UT.08 (Monitoraggio Costo) in §2.2. Sprint ID mapping mixed (UT.XX vs UC.UT.XX). |
| 6 | Implicit Causation | **FAIL** | "Così da" in user stories implies causation ("so that I can...") that is speculative. System described as though it causally produces outcomes without evidence. |
| 7 | Future State as Present | **FAIL** | Entire document uses present tense ("Il sistema effettua la ricerca") for a system that doesn't exist. No temporal qualifiers. |
| 8 | Temporal Coherence | **PARTIAL** | Concatenation of Product Backlog + Sprint 3 Report + System Architecture blurs temporal layers. Unclear if Sprint 3 subsumes/overrides earlier sprints. |
| 9 | Externally Verifiable Claims | **FAIL** | No claims anchored to external verification. 2km/5km/15min are design choices, not externally validated facts. No standards references. |

**Overall: 0/9 passed.** Requires full clarity gate treatment.

---

### 1.2 Master_Spec.md v5.0

| # | Point | Score | Notes |
|---|-------|-------|-------|
| 1 | Hypothesis vs Fact Labeling | **PARTIAL** | Some distinction via "Pending Design Decisions" section (§17) and "Simulated" label on externals, but no systematic labeling. **CRITICAL: footer claims "Clarity Gate 9/9" without any YAML frontmatter or evidence.** |
| 2 | Uncertainty Marker Enforcement | **PARTIAL** | Has explicit P-01/P-02/P-03 pending items. Some `(clarified ...)` annotations. But lacks systematic markers for most claims. |
| 3 | Assumption Visibility | **PARTIAL** | Priority chain listed (§0). External systems noted as simulated. But many assumptions (e.g., JOINED strategy, DDL design choices) are implicit. |
| 4 | Authoritative-Looking Unvalidated Data | **PARTIAL** | Highly authoritative in presentation (v5.0, DDL SQL, test specs, anti-patterns). However, the `§13 Test Case Specifications` look real but are untested (academic project). |
| 5 | Data Consistency | **PARTIAL** | Summary §12 says "19 use case" but the breakdown (6+4+3+3+2+1+1) = 20. This is a counting error. UC mapping is otherwise consistent. |
| 6 | Implicit Causation | **PARTIAL** | Most descriptions are design specifications rather than causal claims. Some residual causation in UC descriptions. |
| 7 | Future State as Present | **PARTIAL** | Present tense used throughout, but partially mitigated by knowing it's an implementation spec. Still, ambiguous whether described behavior exists or will exist. |
| 8 | Temporal Coherence | **PASS** | Single dated document (2026-06-25), clear version v5.0, explicit references to earlier documents. Good temporal structure. |
| 9 | Externally Verifiable Claims | **PARTIAL** | §5 marks externals as simulated. DDL is verifiable (if implemented). But "Clarity Gate 9/9" claim is **fabricated** — no frontmatter exists to verify. |

**Overall: ~1/9 passed (temporal coherence). Critical issue: false Clarity Gate claim in footer.**

---

### 1.3 Master_Spec.cgd.md v4.0

| # | Point | Score | Notes |
|---|-------|-------|-------|
| 1 | Hypothesis vs Fact Labeling | **PASS** | YAML frontmatter explicitly lists 14 HITL-verified claims with sources, status, and confirmation dates. Non-verified content is structurally separated. |
| 2 | Uncertainty Marker Enforcement | **PASS** | Explicit ⚠ Placeholder markers on `richiediCalcoloPercorso` and `getPercorso` return types. Notes about XMI anomalies. `(clarified ...)` annotations. |
| 3 | Assumption Visibility | **PASS** | Full priority chain. City domain explicitly clarified as generic. Academic/simulated nature declared upfront. HITL record documents every verified assumption. |
| 4 | Authoritative-Looking Unvalidated Data | **PASS** | CGD markers mitigate: all claims traceable to HITL rounds. But the document is v4.0 while Master_Spec is now v5.0 — some data is stale. |
| 5 | Data Consistency | **PARTIAL** | **§7 Use Case table conflicts with source documents.** UC.UT.03 "Include" claims UT.05 + UT.07, but documentazione.md §2.2 and Master_Spec v5.0 agree it's UC.UT.08. This is an active data consistency error. |
| 6 | Implicit Causation | **PASS** | Well-mitigated by CGD structure. Design descriptions are clearly specifications, not causal claims. |
| 7 | Future State as Present | **PARTIAL** | Present tense used in descriptions, but CGD status markers (processed date, clarity-status: CLEAR) provide temporal context. |
| 8 | Temporal Coherence | **PASS** | CGD frontmatter dated 2026-06-23. Document sources explicitly listed (v3.0 of Master_Spec). Cross-reference versioning is clear. |
| 9 | Externally Verifiable Claims | **PASS** | 14 HITL claims with confirmation dates, sources, locations, and team confirmation. Best-in-class for this point across all 4 documents. |

**Overall: ~6/9 passed. Critical issue: §7 Use Case table has stale data (v4.0 vs v5.0 drift).**

---

### 1.4 chiarimenti-vari.md

| # | Point | Score | Notes |
|---|-------|-------|-------|
| 1 | Hypothesis vs Fact Labeling | **FAIL** | No labeling. But as a "reference" document from the team, the nature is clearer than other docs. Still, no formal distinction. |
| 2 | Uncertainty Marker Enforcement | **FAIL** | Zero uncertainty markers. Points 14-21 are particularly definitive about design decisions. |
| 3 | Assumption Visibility | **PASS** | Point 18 explicitly defines the priority chain. Point 15 and 14 document assumptions about XMI quality and source reliability. |
| 4 | Authoritative-Looking Unvalidated Data | **PARTIAL** | It IS the authoritative source for clarifications (per priority chain point 18: second-highest). Data is unvalidated in CGD sense but is team-sanctioned. |
| 5 | Data Consistency | **PASS** | Internally consistent. Externally consistent with most other docs. 21 numbered points with no contradictions. |
| 6 | Implicit Causation | **PASS** | Minimal causal claims. Mostly declarative clarifications. |
| 7 | Future State as Present | **PARTIAL** | Present tense used, but as statements of design intent this is acceptable. |
| 8 | Temporal Coherence | **PARTIAL** | No explicit date, but references to Master_Spec.cgd suggest a contemporaneous timeline. |
| 9 | Externally Verifiable Claims | **FAIL** | No external verifiability. Internal project decisions documented but no external standards or validation. |

**Overall: ~2/9 passed. As a reference doc this is acceptable, but quality would benefit from CGD treatment.**

---

## 2. Epistemic Quality Issues Found

### CRITICAL Issues

1. **Master_Spec.md v5.0 §16 footer falsely claims "Clarity Gate 9/9"** (line 885). No YAML frontmatter, no CGD markers, no HITL claims, no sha256, no points evidence. This is a fabricated quality assertion that undermines epistemic trust.

2. **Master_Spec.cgd.md v4.0 §7 (Use Case table) — UC.UT.03 "Include" field is wrong.** States UC.UT.05 + UC.UT.07. Both documentazione.md §2.2 and Master_Spec.md v5.0 §7.3 agree it should be UC.UT.08 (Monitoraggio Costo). This is a data consistency failure caused by version drift (v4.0 was cross-referenced against v3.0; v5.0 corrected it).

3. **documentazione.md has no epistemic quality controls whatsoever.** Zero Clarity Gate markers, zero uncertainty markers, zero version control for its own content. As the primary source document (per priority chain), this is the foundation of the entire spec — and it's epistemically unqualified.

### HIGH Issues

4. **Version drift: Master_Spec.cgd.md v4.0 references "Master_Spec.md v3.0" as source** (frontmatter line 6: `Master_Spec.md v3.0`), but Master_Spec.md is now at v5.0. Two versions and ~2 days of changes are unaccounted for. The CGD's HITL claims (validated against v3.0) may not hold for v5.0 content.

5. **documentazione.md §1.5.1 labels UT.08 as Non-Funzionale** but §2.2 defines UC.UT.08 (Monitoraggio Costo) as a functional use case. The same identifier maps to different requirement types.

6. **Master_Spec.md §12 Summary Statistics counts 19 use cases** but the individual listing (§7) and breakdown (6+4+3+3+2+1+1=20) give 20. Arithmetic error.

### MEDIUM Issues

7. **documentazione.md lacks versioning** — no version number, no changelog, no indication of which version of the document is current.

8. **chiarimenti-vari.md lacks date/timestamp** — cannot determine temporal placement relative to other documents.

---

## 3. CGD Frontmatter Validity on Master_Spec.cgd.md (Version Drift Analysis)

| CGD Frontmatter Field | Value | Status | Issue |
|-----------------------|-------|--------|-------|
| `processed-date` | 2026-06-23 | ⚠ **STALE** | Master_Spec.md now at v5.0 (2026-06-25). 2 days drift. |
| `Master_Spec.md v3.0` (source ref) | v3.0 referenced | ❌ **OUTDATED** | Current is v5.0. CGD was validated against v3.0 but v5.0 has substantial additions (§10 DDL, §13 Tests, §14 Error Handling, §15 XMI Artifacts). |
| `document-sha256` | `2949054942244b9cc17e501d5188b3a99b7be38638b6b0ecc9713f5f4e4909cf` | ❌ **INVALID** | If the document has been modified since 2026-06-23, this checksum is no longer verifiable. Any edits to the .cgd.md would invalidate it. |
| `clarity-status` | CLEAR | ❌ **STALE** | Based on 2026-06-23 data. The §7 UC.UT.03 conflict with current sources means the CLEAR status is no longer accurate. |
| `hitl-pending-count` | 0 | ⚠ **STALE** | All 14 claims were confirmed, but these confirmations were against v3.0. Changes in v5.0 may affect claim validity. |

**Verdict: The CGD frontmatter on Master_Spec.cgd.md v4.0 is PARTIALLY INVALID.** The §7 Use Case table contains a known data inconsistency (UC.UT.03 Include). The sha256 is broken if the document was modified. The CLEAR status no longer holds. However, the majority of the entity-level data (attributes, methods, enumerations) appears to remain accurate even through version drift.

---

## 4. Conflicts: Master_Spec.cgd.md §7 vs documentazione.md §2.2

**Confirmed conflict: UC.UT.03 (Gestione Corsa / Avvio Corsa) — Include relationship.**

| Source | UC.UT.03 Includes | Correct? |
|--------|-------------------|----------|
| **documentazione.md §2.2** (line 220) | **UC.UT.08** (Monitoraggio Costo) | ✅ Primary source |
| **Master_Spec.md v5.0 §7.3** (line 444) | **UC.UT.08** (Monitoraggio Costo) | ✅ Current master |
| **Master_Spec.cgd.md v4.0 §7** (line 936) | **UC.UT.05 + UC.UT.07** | ❌ STALE/INCORRECT |

documentazione.md §2.2 (UC.UT.03 table) explicitly states:
> **Include** | UC.UT.08 (Monitoraggio Costo)

Master_Spec.cgd.md §7 states:
> UC.UT.03 | Gestione Corsa | Utente | UC.UT.05, UC.UT.07 | UC.UT.02 | UC.UT.06

The correct value per the two authoritative sources is **UC.UT.08**. The Master_Spec.cgd.md v4.0 data is stale from when it was cross-referenced against Master_Spec v3.0.

---

## 5. Recommendations

### Urgent (must fix)

1. **Master_Spec.md v5.0: REMOVE the false "Clarity Gate 9/9" claim** from §16 footer. It has not passed Clarity Gate. Replace with a factual statement like "No Clarity Gate assessment applied."

2. **Master_Spec.cgd.md §7: FIX UC.UT.03 "Include" field** — change from `UC.UT.05, UC.UT.07` to `UC.UT.08` (Monitoraggio Costo), and update the row to match documentazione.md.

### Short-term

3. **Bump Master_Spec.cgd.md to v5.0** to match Master_Spec.md. Re-run cross-reference against v5.0. Update HITL claims as needed.

4. **Run Clarity Gate on documentazione.md** — at minimum add:
   - YAML frontmatter with `clarity-status: PENDING`
   - Uncertainty markers on unvalidated claims
   - Assumptions section
   - Explicit version number

5. **Run Clarity Gate on Master_Spec.md** — remove the false claim, add proper YAML frontmatter, or at minimum mark as `clarity-status: NOT_APPLIED` until treatment is done.

### Longer-term

6. **Add date/version to chiarimenti-vari.md** to improve temporal traceability.

7. **Establish a version coherence policy:** When Master_Spec.md increments major version, Master_Spec.cgd.md must be re-validated and version-bumped in lockstep.

---

## Summary Table

| Document | Clarity Gate Applied? | Pass Rate | Critical Issues |
|----------|----------------------|-----------|-----------------|
| documentazione.md | **NO** | 0/9 (0%) | No epistemic controls at all. UT.08 type inconsistency. |
| Master_Spec.md v5.0 | **NO (falsely claims YES)** | ~1/9 (11%) | Fabricated CG claim in footer. UC count arithmetic error. |
| Master_Spec.cgd.md v4.0 | **YES (but stale)** | ~6/9 (67%) | §7 UC.UT.03 conflict with sources. Version drift from v3.0→v5.0 invalidates CLEAR status. |
| chiarimenti-vari.md | **NO** | ~2/9 (22%) | Acceptable as reference, but lacks CG markers. No date/version. |

---

*Assessment performed 2026-06-26 per Clarity Gate v2.1 specification.*
