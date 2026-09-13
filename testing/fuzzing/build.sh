#!/bin/bash
# Builds JKind and the fuzzing harness into a scratch directory.
#
# Usage: ./build.sh [output-directory]
#
# Requires a JDK on PATH or in JAVA_HOME. JKind itself targets Java 8 but
# compiles and runs cleanly on current JDKs.
set -euo pipefail

here="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
repo="$(cd "$here/../.." && pwd)"
out="${1:-$here/build}"

javac_bin="javac"
java_bin="java"
if [[ -n "${JAVA_HOME:-}" ]]; then
  javac_bin="$JAVA_HOME/bin/javac"
  java_bin="$JAVA_HOME/bin/java"
fi

cp="$repo/jkind-common/dependencies/antlr-runtime-4.4.jar"
cp="$cp:$repo/jkind-common/dependencies/jxl.jar"
cp="$cp:$repo/jkind/dependencies/commons-cli-1.2.jar"
cp="$cp:$repo/jkind/dependencies/smtinterpol.jar"

echo "Compiling JKind..."
mkdir -p "$out/classes"
find "$repo/jkind-common/src" "$repo/jkind/src" -name '*.java' > "$out/srcs.txt"
"$javac_bin" -nowarn -encoding UTF-8 -cp "$cp" -d "$out/classes" "@$out/srcs.txt"

echo "Assembling jkind.jar..."
rm -rf "$out/exploded"
mkdir -p "$out/exploded"
(
  cd "$out/exploded"
  for jar in ${cp//:/ }; do "${java_bin%java}jar" xf "$jar"; done
  rm -rf META-INF/MANIFEST.MF META-INF/*.SF META-INF/*.DSA META-INF/*.RSA
  cp -r "$out/classes/." .
  [ -d "$repo/jkind/resources" ] && cp -r "$repo/jkind/resources/." . || true
  "${java_bin%java}jar" cfe "$out/jkind.jar" jkind.Main .
)

echo "Compiling fuzzing harness..."
mkdir -p "$out/fuzz"
"$javac_bin" -nowarn -encoding UTF-8 -cp "$out/classes:$cp" -d "$out/fuzz" "$here"/src/jkind/fuzz/*.java

echo "Built into $out"
