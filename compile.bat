@echo off
setlocal

set PAPER_API=paper-api-1.21.5-R0.1-20250925.065803-26.jar
set LIBRARIES=libraries
set SRC=DatapackPlugin.java DatapackPluginBootstrap.java
set OUT=.

echo Collecting classpath from %LIBRARIES%\...
powershell -Command "(Get-ChildItem -Recurse -Filter *.jar '%LIBRARIES%').FullName -join ';'" > classpath.tmp

set /p LIB_CP=<classpath.tmp
del classpath.tmp

echo Compiling...
javac --release 21 -cp "%PAPER_API%;%LIB_CP%" -d "%OUT%" %SRC%

if %ERRORLEVEL% == 0 (
    echo Done. Class files written to %OUT%\datapackplugin\
) else (
    echo Compilation failed.
)

pause