
@echo off
set "GENERAT_DIR=%CD%\generat"

REM Esborrar tots els subdirectoris excepte "doc"
for /D %%D in ("%GENERAT_DIR%\*") do (
    if /I not "%%~nxD"=="doc" (
        rd /S /Q "%%D"
    )
)

REM Esborrar tots els fitxers de l'arrel de "generat"
del /Q "%GENERAT_DIR%\*" >nul 2>&1