#!/bin/bash
# Finding 5 (minor): deeply nested expressions overflow the Java stack rather
# than being reported as an input that exceeds a nesting limit.
#
# Expected: a diagnostic naming the limit, or successful analysis.
# Actual:   java.lang.StackOverflowError, exit -8.
n=${1:-2000}
{
  echo "node main() returns (ok : bool);"
  echo "let"
  printf '  ok = ('
  for ((i = 0; i < n; i++)); do printf '(1 + '; done
  printf '1'
  for ((i = 0; i < n; i++)); do printf ')'; done
  echo ') > 0;'
  echo "  --%PROPERTY ok;"
  echo "tel;"
}
