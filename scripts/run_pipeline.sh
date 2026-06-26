#!/bin/bash
# Smart Mobility System - Pipeline Script
# Usage: ./run_pipeline.sh

set -e

echo "=== Smart Mobility System Pipeline ==="
echo ""

# Source environment variables if .env exists
if [ -f .env ]; then
    source .env
fi

# Phase 1: Compile
echo "=== Phase 1: Compile ==="
mvn compile -q
echo "✅ Compilation successful"
echo ""

# Phase 2: Run tests
echo "=== Phase 2: Tests ==="
mvn test
echo "✅ Tests complete"
echo ""

# Phase 3: Package
echo "=== Phase 3: Package ==="
mvn package -DskipTests -q
echo "✅ Package successful"
echo ""

# Phase 4: Verify
echo "=== Phase 4: Verify ==="
mvn verify -q
echo "✅ Verification complete"
echo ""

echo "=== Pipeline finished successfully ==="
