#!/bin/bash
# Smart Mobility System - Frontend
# Usage: ./run-frontend.sh
set -e
cd "$(dirname "$0")/frontend"

echo "=== Installing frontend dependencies ==="
npm install --silent

echo "=== Starting frontend dev server ==="
exec npm run dev
