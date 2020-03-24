call mvn -f ..\genapp-generator\pom.xml install

if %errorlevel% EQU 0 (

    cd ..\demo\appweb
    rmdir /Q /S logic jpa back ear ejb utils model scripts persistence pom.xml
    cd ..\..\run

    call mvn exec:java -Dgenapp_run=true -Dexec.mainClass="org.fundaciobit.genapp.generator.CodeGenerator" -Dexec.args="..\demo\DemoGenApp.genapp ..\demo\appweb"

    if %errorlevel% EQU 0 (
        cd ..\demo\appweb
        call compile.bat clean
        cd ..\..\run
    )
)