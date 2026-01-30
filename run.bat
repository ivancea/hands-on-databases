@echo off
setlocal enabledelayedexpansion

rem Build an arguments string by escaping backslashes and double quotes,
rem and wrapping each argument in quotes to preserve spaces.
set "ARGSTRING="
:loop
if "%~1"=="" goto run
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
rem wrap the entire string so gradle receives a single --args argument
if defined ARGSTRING (
  call gradlew.bat run --quiet --args="%ARGSTRING%"
) else (
  call gradlew.bat run --quiet
)
endlocal
