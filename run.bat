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
echo(!TASK_ID!| %SystemRoot%\System32\findstr.exe /r /x "[0-9][0-9]*" >nul
if errorlevel 1 (
  echo Invalid task ID: !TASK_ID!
  endlocal & exit /b 1
)

set "NORMALIZED_TASK_ID=!TASK_ID!"
:strip_leading_zero
if "!NORMALIZED_TASK_ID!"=="0" goto task_id_normalized
if not "!NORMALIZED_TASK_ID:~0,1!"=="0" goto task_id_normalized
set "NORMALIZED_TASK_ID=!NORMALIZED_TASK_ID:~1!"
goto strip_leading_zero

:task_id_normalized
set "TASK_ID=!NORMALIZED_TASK_ID!"
set "TASK_MODULE=task!TASK_ID!"
if "!TASK_ID:~1,1!"=="" set "TASK_MODULE=task0!TASK_ID!"
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
