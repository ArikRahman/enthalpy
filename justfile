# Enthalpy — justfile
# Install just: https://github.com/casey/just
# Run from enthalpy/ with: just <recipe>

# ── Settings ─────────────────────────────────────────────────────────────────

set shell := ["sh", "-c"]

# Default: list available recipes
default:
    @just --list

# ── Dependency management ─────────────────────────────────────────────────────

# Install all npm/bun dependencies
install:
    bun install

# ── Development ───────────────────────────────────────────────────────────────

# Start the shadow-cljs dev server with hot reload (http://localhost:3000)
dev:
    bun run dev

# Open the local dev site in the default browser
open:
    @echo "Opening http://localhost:3000 …"
    xdg-open http://localhost:3000 2>/dev/null || open http://localhost:3000 2>/dev/null || echo "Could not open browser — visit http://localhost:3000 manually"

# Stop the background shadow-cljs JVM server
stop:
    bun run stop

# Kill any lingering shadow-cljs JVM process (use if 'just stop' hangs)
kill:
    @echo "Killing shadow-cljs server processes …"
    pkill -f "shadow.cljs.devtools.cli" && echo "Done." || echo "No shadow-cljs process found."

# ── Build ─────────────────────────────────────────────────────────────────────

# Production release build → docs/js/main.js
build:
    bun run build

# Remove the shadow-cljs compilation cache
clean:
    rm -rf .shadow-cljs
    @echo "Cache cleared."

# Clean cache then do a full production build
rebuild: clean build

# ── Checks & tests ────────────────────────────────────────────────────────────

# Quick syntax check — parse every .cljs file and print OK / ERR
check:
    sh scripts/check-syntax.sh

# Run the full integrity test suite
test:
    clj -M scripts/test.clj

# Run syntax check then the full test suite
verify: check test

# Run the full verify suite then a production build — use before committing
ci: verify build

# ── Utilities ─────────────────────────────────────────────────────────────────

# Count lines of ClojureScript source
loc:
    @find src -name "*.cljs" | xargs wc -l | tail -1

# List all .cljs source files
files:
    @find src -name "*.cljs" | sort

# Show the current size of the production bundle
bundle-size:
    @ls -lh docs/js/main.js 2>/dev/null || echo "docs/js/main.js not found — run: just build"

# Print a quick summary of the project state
status:
    @echo "── Source files ──────────────────────────────────────────────────"
    @find src -name "*.cljs" | sort
    @echo ""
    @echo "── Bundle ────────────────────────────────────────────────────────"
    @ls -lh docs/js/main.js 2>/dev/null || echo "  (not built)"
    @echo ""
    @echo "── shadow-cljs cache ─────────────────────────────────────────────"
    @ls .shadow-cljs/ 2>/dev/null && echo "  (cache present)" || echo "  (no cache)"
