#!/usr/bin/env bash
set -euo pipefail

# Build an arguments string by escaping backslashes and double quotes,
# and wrapping each argument in quotes to preserve spaces.
ARGSTRING=""
TEST_MODE=false
TASK_ID=""
USE_SOLUTION=false
EXPECT_TASK=false
for a in "$@"; do
  if $EXPECT_TASK; then
    TASK_ID="$a"
    EXPECT_TASK=false
  else
    case "$a" in
      --test) TEST_MODE=true ;;
      --solution) USE_SOLUTION=true ;;
      --task) EXPECT_TASK=true ;;
      -t) EXPECT_TASK=true ;;
    esac
  fi

  esc=${a//\\/\\\\}    # escape backslashes
  esc=${esc//\"/\\\"}  # escape double quotes
  if [ -z "$ARGSTRING" ]; then
    ARGSTRING="\"$esc\""
  else
    ARGSTRING="$ARGSTRING \"$esc\""
  fi
done

if $TEST_MODE; then
  if [ -z "$TASK_ID" ]; then
    echo "--test requires --task <id>"
    exit 1
  fi
  if ! [[ "$TASK_ID" =~ ^[0-9]+$ ]]; then
    echo "Invalid task ID: $TASK_ID"
    exit 1
  fi

  printf -v TASK_MODULE "task%02d" "$((10#$TASK_ID))"
  if [ ! -f "tests/$TASK_MODULE/build.gradle.kts" ]; then
    echo "No tests available for task $TASK_ID"
    exit 1
  fi

  IMPLEMENTATION="exercise"
  if $USE_SOLUTION; then
    IMPLEMENTATION="solution"
  fi
  echo "Running $IMPLEMENTATION tests for task $TASK_ID..."
  exec ./gradlew ":tests:$TASK_MODULE:${IMPLEMENTATION}Test"
fi

# Execute the wrapper with the entire string as --args
if [ -z "$ARGSTRING" ]; then
  exec ./gradlew run --quiet
else
  exec ./gradlew run --quiet --args="$ARGSTRING"
fi
