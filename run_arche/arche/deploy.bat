

if [%1]==[] goto usage

mvn clean deploy -Dgpg.passphrase=%1


goto :eof
:usage

@echo ----------------
@echo Usage: deploy.bat [CONTRASENYA_GPG]
@echo .
@echo Per més informació per favor llegir readme.txt
@echo ----------------
exit /B 1

