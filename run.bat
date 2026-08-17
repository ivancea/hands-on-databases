@echo off
setlocal enabledelayedexpansion

rem Build an arguments string by escaping backslashes and double quotes,
rem and wrapping each argument in quotes to preserve spaces.
set "ARGSTRING="
set "TEST_MODE="
set "TASK_ID="
set "USE_SOLUTION="
set "EXPECT_TASK="
:loop
if "%~1"=="" goto run
if defined EXPECT_TASK (
  set "TASK_ID=%~1"
  set "EXPECT_TASK="
) else (
  if /i "%~1"=="--test" set "TEST_MODE=1"
  if /i "%~1"=="--solution" set "USE_SOLUTION=1"
  if /i "%~1"=="--task" set "EXPECT_TASK=1"
  if /i "%~1"=="-t" set "EXPECT_TASK=1"
)
set "arg=%~1"
rem escape backslashes
set "arg=!arg:\=\\!"
rem escape double quotes
set "arg=!arg:"=\"!"
if defined ARGSTRING (
  set "ARGSTRING=!ARGSTRING! \"!arg!\""
) else (
  set "ARGSTRING=\"!arg!\""
)
shift
goto loop

:run
if defined TEST_MODE goto test
rem wrap the entire string so gradle receives a single --args argument
if defined ARGSTRING (
  call gradlew.bat run --quiet --args="%ARGSTRING%"
) else (
  call gradlew.bat run --quiet
)
set "EXIT_CODE=%ERRORLEVEL%"
endlocal & exit /b %EXIT_CODE%

:test
if not defined TASK_ID (
  echo --test requires --task ^<id^>
  endlocal & exit /b 1
)
set "TASK_MODULE=task%TASK_ID%"
if "%TASK_ID:~1,1%"=="" set "TASK_MODULE=task0%TASK_ID%"
if not exist "tests\%TASK_MODULE%\build.gradle.kts" (
  echo No tests available for task %TASK_ID%
  endlocal & exit /b 1
)
set "IMPLEMENTATION=exercise"
if defined USE_SOLUTION set "IMPLEMENTATION=solution"
echo Running %IMPLEMENTATION% tests for task %TASK_ID%...
call gradlew.bat ":tests:%TASK_MODULE%:%IMPLEMENTATION%Test"
set "EXIT_CODE=%ERRORLEVEL%"
endlocal & exit /b %EXIT_CODE%
