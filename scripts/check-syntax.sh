#!/usr/bin/env sh
# scripts/check-syntax.sh — parse every .cljs file and report OK / ERR
# Run from enthalpy/ with: sh scripts/check-syntax.sh

set -e

GREEN='\033[32m'
RED='\033[31m'
BOLD='\033[1m'
RESET='\033[0m'

ERRORS=0
CHECKED=0

printf "${BOLD}── Syntax check ──${RESET}\n"

while IFS= read -r -d '' f; do
  result=$(clj -M -e "
(try
  (read-string (str \"[\" (slurp \"$f\") \"]\"))
  (println \"OK\")
  (catch Exception e
    (println (str \"ERR \" (.getMessage e)))))" 2>&1)

  CHECKED=$((CHECKED + 1))

  case "$result" in
    OK*)
      printf "  ${GREEN}OK${RESET}  %s\n" "$f"
      ;;
    ERR*)
      msg=$(printf '%s' "$result" | sed 's/^ERR //')
      printf "  ${RED}ERR${RESET} %s — %s\n" "$f" "$msg"
      ERRORS=$((ERRORS + 1))
      ;;
    *)
      printf "  ${RED}ERR${RESET} %s — unexpected output: %s\n" "$f" "$result"
      ERRORS=$((ERRORS + 1))
      ;;
  esac
done < <(find src -name "*.cljs" -print0 | sort -z)

printf "\n%d file(s) checked.\n" "$CHECKED"

if [ "$ERRORS" -eq 0 ]; then
  printf "${GREEN}${BOLD}All files OK.${RESET}\n"
else
  printf "${RED}${BOLD}%d file(s) had errors.${RESET}\n" "$ERRORS"
  exit 1
fi
