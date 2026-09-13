#!/bin/bash
# Downloads the SMT solvers JKind supports, pinned to the versions it is
# verified against, into a local directory.
#
# Nothing is vendored into the repository and nothing is redistributed: each
# solver is fetched from its own upstream release, under its own license. See
# ../BACKLOG.md for why that distinction matters before anyone considers
# shipping these binaries with JKind.
#
# Usage:
#   ./fetch.sh                 # fetch into ./toolchain
#   ./fetch.sh --dir DIR       # fetch somewhere else
#   ./fetch.sh --list          # show what would be fetched, download nothing
#
# Afterwards, source the generated env.sh to put the solvers where JKind looks:
#   source toolchain/env.sh
set -euo pipefail

here="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
dir="$here/toolchain"
list_only=0

while [[ $# -gt 0 ]]; do
  case "$1" in
    --dir) dir="$2"; shift 2 ;;
    --list) list_only=1; shift ;;
    -h|--help) sed -n '2,16p' "${BASH_SOURCE[0]}" | sed 's/^# \?//'; exit 0 ;;
    *) echo "unknown option: $1" >&2; exit 2 ;;
  esac
done

# Each entry: name|version|home-subdirectory|executable|url|sha256|license
# The home subdirectory is what <SOLVER>_HOME must point at: JKind appends
# bin/ to it when that exists. The executable is the name JKind invokes.
solvers=(
"z3|5.1.0|z3-5.1.0-x64-glibc-2.39|z3|https://github.com/Z3Prover/z3/releases/download/z3-5.1.0/z3-5.1.0-x64-glibc-2.39.zip|f47be8d27d3230e823bf1eeede2fe0abaca55bb78d0b59974370e6689a92284a|MIT"
"cvc5|1.3.4|cvc5-Linux-x86_64-static|cvc5|https://github.com/cvc5/cvc5/releases/download/cvc5-1.3.4/cvc5-Linux-x86_64-static.zip|dcdbfada0ce493ee98259c0816e0daafc561c223aadb3af298c2968e73ea39c6|BSD-3-Clause (non-GPL build)"
"yices2|2.7.0|yices-2.7.0|yices-smt2|https://github.com/SRI-CSL/yices2/releases/download/yices-2.7.0/yices-2.7.0-x86_64-pc-linux-gnu-static-gmp.tar.gz|49566b6f817692820538df78fe406878400d79810631c9372b2495bc81d3e00a|GPL-3.0"
)

if [[ "$(uname -s)" != "Linux" || "$(uname -m)" != "x86_64" ]]; then
  echo "This script pins Linux x86_64 release artifacts, and $(uname -s)/$(uname -m) is not that." >&2
  echo "Install the solvers from their release pages and set the environment yourself:" >&2
  echo "  Z3_HOME     https://github.com/Z3Prover/z3/releases/tag/z3-5.1.0" >&2
  echo "  CVC5_HOME   https://github.com/cvc5/cvc5/releases/tag/cvc5-1.3.4" >&2
  echo "  YICES2_HOME https://github.com/SRI-CSL/yices2/releases/tag/yices-2.7.0" >&2
  exit 1
fi

if [[ $list_only -eq 1 ]]; then
  printf '%-9s %-8s %-26s %s\n' SOLVER VERSION LICENSE URL
  for entry in "${solvers[@]}"; do
    IFS='|' read -r name version home exe url sha license <<< "$entry"
    printf '%-9s %-8s %-26s %s\n' "$name" "$version" "$license" "$url"
  done
  echo
  echo "SMTInterpol is bundled in jkind.jar and needs no download."
  echo "MathSAT is not fetched: its license covers research and evaluation only,"
  echo "so it must be obtained directly by whoever will use it."
  exit 0
fi

mkdir -p "$dir/archives"
env_file="$dir/env.sh"
: > "$env_file"

verify() {
  local file="$1" expected="$2" actual
  actual="$(sha256sum "$file" | cut -d' ' -f1)"
  if [[ "$actual" != "$expected" ]]; then
    echo "checksum mismatch for $file" >&2
    echo "  expected $expected" >&2
    echo "  actual   $actual" >&2
    return 1
  fi
}

for entry in "${solvers[@]}"; do
  IFS='|' read -r name version home exe url sha license <<< "$entry"
  target="$dir/$home"
  archive="$dir/archives/$(basename "$url")"

  if [[ -d "$target" ]]; then
    echo "$name $version already present"
  else
    if [[ ! -f "$archive" ]] || ! verify "$archive" "$sha" 2>/dev/null; then
      echo "Fetching $name $version ($license)..."
      curl -fsSL -o "$archive.part" "$url"
      mv "$archive.part" "$archive"
    fi
    verify "$archive" "$sha"

    echo "Extracting $name..."
    case "$archive" in
      # unzip is not always installed; python3 is already required by compare.py.
      *.zip) python3 -c "import sys,zipfile; zipfile.ZipFile(sys.argv[1]).extractall(sys.argv[2])" "$archive" "$dir" ;;
      *.tar.gz) tar xzf "$archive" -C "$dir" ;;
      *) echo "unhandled archive type: $archive" >&2; exit 1 ;;
    esac
    [[ -d "$target/bin" ]] && chmod +x "$target/bin/"* || true
  fi

  var="$(echo "$name" | tr '[:lower:]' '[:upper:]')_HOME"
  echo "export $var=\"$target\"" >> "$env_file"
done

cat >> "$env_file" <<'ENV'

# JKind resolves each solver through <SOLVER>_HOME before falling back to PATH,
# so nothing needs to be on PATH for these to be used.
ENV

echo
echo "Solvers installed under $dir"
failed=0
for entry in "${solvers[@]}"; do
  IFS='|' read -r name version home exe url sha license <<< "$entry"
  binary="$dir/$home/bin/$exe"
  if [[ ! -x "$binary" ]]; then
    printf '  %-9s MISSING  expected %s\n' "$name" "$binary"
    failed=1
    continue
  fi
  # Run it, so a broken download or an incompatible build is caught here
  # rather than in the middle of a verification run.
  reported="$("$binary" --version 2>&1 | head -1 || true)"
  printf '  %-9s %s\n' "$name" "$reported"
done
echo
echo "To use them:  source $env_file"
exit $failed
