#!/usr/bin/env bash
set -euo pipefail

# Build an arguments string by escaping backslashes and double quotes,
# and wrapping each argument in quotes to preserve spaces.
ARGSTRING=""
for a in "$@"; do
  esc=${a//\\/\\\\}    # escape backslashes
  esc=${esc//\"/\\\"}  # escape double quotes
  if [ -z "$ARGSTRING" ]; then
    ARGSTRING="\"$esc\""
  else
    ARGSTRING="$ARGSTRING \"$esc\""
  fi
done

# Execute the wrapper with the entire string as --args
if [ -z "$ARGSTRING" ]; then
  exec ./gradlew run --quiet
else
  exec ./gradlew run --quiet --args="$ARGSTRING"
fi
