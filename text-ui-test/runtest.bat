@echo off
setlocal enableextensions
pushd %~dp0

cd ..
call gradlew clean shadowJar

cd build\libs
for /f "tokens=*" %%a in ('dir /b *.jar') do (
    set jarloc=%%a
)

java -jar %jarloc% < ..\..\text-ui-test\input.txt > ..\..\text-ui-test\ACTUAL.TXT 2> ..\..\text-ui-test\ERROR.TXT

cd ..\..\text-ui-test

echo ===== ERROR.TXT =====
type ERROR.TXT

echo.
echo ===== ACTUAL vs EXPECTED =====
FC ACTUAL.TXT EXPECTED.TXT

pause
