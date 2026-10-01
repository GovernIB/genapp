@echo off

echo optional parameters -Dcaib -Psqlgen

cmd /C mvn clean install -DskipTests %* 

if %errorlevel% EQU 0 (

	@echo off
	IF DEFINED GENAPPSQLTUTORIAL_DEPLOY_DIR (
      for /f "tokens=* delims=" %%x in (versio.txt) do set GENAPPSQLTUTORIAL_VERSIO=%%x
	  @echo on
	  echo --------- COPIANT EAR %GENAPPSQLTUTORIAL_VERSIO% ---------

	  xcopy /Y genappsqltutorial-ear\target\genappsqltutorial.ear %GENAPPSQLTUTORIAL_DEPLOY_DIR%

	) ELSE (
	  echo  =================================================================
	  echo    Definex la variable d'entorn GENAPPSQLTUTORIAL_DEPLOY_DIR apuntant al
	  echo    directori de deploy del JBOSS  i automaticament s'hi copiara
	  echo    l'ear generat.
	  echo  =================================================================
	) 

)