@echo off
Setlocal EnableDelayedExpansion

if [%1]==[] if [%2]==[] (
    echo "specify year and day"
    exit
)

set year=%1
set day=%2
set day=00%day%
set day=%day:~-2%

echo %year%
echo %day%

.\aocdl.exe -year %year% -day %day% -output "resources/%year%/%year%_%day%.txt"
REM git add "resources/%year%/%year%_%day%.txt"