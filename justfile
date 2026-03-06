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

# Build, commit all changes (including docs/), and push to GitHub Pages.
# GitHub Pages serves docs/ from the main branch — no separate gh-pages branch needed.
# Usage: just deploy           (uses default message "deploy")
#        just deploy "message"
deploy msg="deploy": test build
    jj commit -m "{{msg}}"
    jj bookmark set main --revision @-
    jj git push --bookmark main

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

# ── Version control (jujutsu) ─────────────────────────────────────────────────

# Show working copy status
st:
    jj status

# Show recent commit log (last 10 changes)
log:
    jj log --limit 10

# Show diff of all working copy changes
diff:
    jj diff

# Show diff for a specific file — usage: just fdiff src/enthalpy/trainer.cljs
fdiff file:
    jj diff --name-only -- "{{file}}" && jj diff -- "{{file}}"

# Set the description (commit message) of the current working copy change
# Usage: just describe "what I changed"
describe message:
    jj describe -m "{{message}}"

# Finalise the current change and open a new empty child change.
# Advances the main bookmark to the committed change.
# Usage: just commit "what I changed"
commit message:
    jj commit -m "{{message}}"
    jj bookmark set main --revision @-

# Push the main bookmark to origin
push:
    jj git push --bookmark main

# Fetch latest changes from origin
fetch:
    jj git fetch
    jj log --limit 5

# One-step: commit, advance bookmark, and push to origin.
# Usage: just snap "what I changed"
snap message:
    jj commit -m "{{message}}"
    jj bookmark set main --revision @-
    jj git push --bookmark main

# One-step: run ci checks, commit, advance bookmark, push.
# Usage: just ship "what I changed"
ship message: ci
    jj commit -m "{{message}}"
    jj bookmark set main --revision @-
    jj git push --bookmark main

# Abandon the current (empty) working copy change and move @ to parent
# Useful if you started a new change by mistake before you were ready
abandon:
    jj abandon @
