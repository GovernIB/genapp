call mvn -f D:\dades\dades\CarpetesPersonals\ProgramacioPortaFIB3\genapp-2.0\genapp-generator\pom.xml  clean install

if %errorlevel% EQU 0 (

cd D:\dades\dades\CarpetesPersonals\ProgramacioPortaFIB3\genapp-2.0\demo\appweb

call neteja.bat

cd D:\dades\dades\CarpetesPersonals\ProgramacioPortaFIB3\genapp-2.0\run

call mvn exec:java -Dgenapp_run=true -Dexec.mainClass="org.fundaciobit.genapp.generator.CodeGenerator" -Dexec.args="D:\dades\dades\CarpetesPersonals\ProgramacioPortaFIB3\genapp-2.0\demo\DemoGenApp.genapp D:\dades\dades\CarpetesPersonals\ProgramacioPortaFIB3\genapp-2.0\demo\appweb"

    if %errorlevel% EQU 0 (
      cd D:\dades\dades\CarpetesPersonals\ProgramacioPortaFIB3\genapp-2.0\demo\appweb
      call compile.bat clean

    ) ELSE (
    goto EXIT
    )
) ELSE (
    goto EXIT
)
:EXIT


cd D:\dades\dades\CarpetesPersonals\ProgramacioPortaFIB3\genapp-2.0\run