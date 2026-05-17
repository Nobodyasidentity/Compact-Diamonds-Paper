@echo off
setlocal
set PAPER_API=paper-api-1.21.5.jar
set LIBRARIES=libraries
set SRC=DatapackPlugin.java DatapackPluginBootstrap.java
set OUT=.

echo Collecting classpath from %LIBRARIES%\...
for /f "delims=" %%i in ('powershell -Command "(Get-ChildItem -Recurse -Filter *.jar '%LIBRARIES%').FullName -join ';'"') do set LIB_CP=%%i

echo Compiling...
javac --release 21 -cp "%PAPER_API%;%LIB_CP%" -d "%OUT%" %SRC%

if %ERRORLEVEL% == 0 (
    echo Done. Class files written to %OUT%\datapackplugin\
) else (
    echo Compilation failed.
)

pause