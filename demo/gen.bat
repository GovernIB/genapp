REM genapp.bat ./DemoGenApp.genappjson ./generat 


@echo off
setlocal

REM Guardar el directori actual per tornar-hi al final
set "ORIG_DIR=%CD%"

REM Definició de directoris
set "GENERAT_DIR=%ORIG_DIR%\generat"
set "RUNGENAPP_DIR=%GENERAT_DIR%\rungenapp"
set "ARCH_DIR=..\run_arche\arche\src\main\resources\archetype-resources"

echo.
echo === Preparant directoris ===

REM Comprovar si existeix demogenapp-back
set "DEMOGENAPP_BACK_EXISTS=0"

if exist "%GENERAT_DIR%\demogenapp-back\" (
    set "DEMOGENAPP_BACK_EXISTS=1"
)

REM (1) Crear generat
if not exist "%GENERAT_DIR%" mkdir "%GENERAT_DIR%"

REM (2) Copiar genapp_root.bat com a genapp.bat
copy /Y "%ARCH_DIR%\genapp_root.bat" "%GENERAT_DIR%\genapp.bat" >nul

REM (3) Crear rungenapp
if not exist "%RUNGENAPP_DIR%" mkdir "%RUNGENAPP_DIR%"

REM (4) Copiar genapp.bat
copy /Y "%ARCH_DIR%\genapp.bat" "%RUNGENAPP_DIR%\" >nul

REM (5) Copiar pom.xml
copy /Y "%ARCH_DIR%\pom.xml" "%RUNGENAPP_DIR%\" >nul

echo.
echo === Executant GenApp ===

REM (6) Executar genapp.bat
pushd "%RUNGENAPP_DIR%"
call genapp.bat ../../DemoGenApp.genappjson ../../generat
set "RET=%ERRORLEVEL%"
popd

if "%DEMOGENAPP_BACK_EXISTS%"=="0" (

    echo.
    echo === Restaurant fitxers especifics de Back ===

    pushd "%GENERAT_DIR%\demogenapp-back"

    call git restore src/main/java/org/fundaciobit/demogenapp/back/controller/admin/AlumneAdminController.java
    call git restore src/main/java/org/fundaciobit/demogenapp/back/controller/all/AssignaturaAllController.java

    popd
)

REM Tornar sempre al directori original
cd /d "%ORIG_DIR%"

echo.
if %RET%==0 (
    echo === Proces finalitzat correctament ===
) else (
    echo === Proces finalitzat amb error (%RET%) ===
)

endlocal
exit /b %RET%
