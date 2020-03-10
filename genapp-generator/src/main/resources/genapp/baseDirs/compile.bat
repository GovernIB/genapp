@echo off

echo optional parameters -Dcaib -Psqlgen

cmd /C mvn -DskipTests %* install

if %errorlevel% EQU 0 (

	@echo off
	IF DEFINED ${name_uppercase}_DEPLOY_DIR (
      for /f "tokens=* delims=" %%x in (versio.txt) do set ${name_uppercase}_VERSIO=%%x
	  @echo on
	  echo --------- COPIANT EAR %${name_uppercase}_VERSIO% ---------

	  xcopy /Y ear\target\${name}.ear %${name_uppercase}_DEPLOY_DIR%

	) ELSE (
	  echo  =================================================================
	  echo    Definex la variable d'entorn ${name_uppercase}_DEPLOY_DIR apuntant al
	  echo    directori de deploy del JBOSS  i automaticament s'hi copiara
	  echo    l'ear generat.
	  echo  =================================================================
	) 

)