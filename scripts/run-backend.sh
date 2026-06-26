#!/bin/bash
# Smart Mobility System - Backend
# Usage: ./run-backend.sh
set -e
cd "$(dirname "$0")"

echo "=== Building backend ==="
mvn compile -q

echo "=== Starting backend (dev profile) ==="
exec mvn spring-boot:run -Dspring-boot.run.profiles=dev
