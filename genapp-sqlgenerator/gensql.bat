REM Si no volem LOBs en un altre tablespace llavors afegir la següent
REM linia despres de mvn: -Dsqlgenerator.oracle.generatelob=false
mvn exec:java -Dsqlgenerator.oracle.generatelob=false -Dsqlgenerator.project.name=projectebase -Dexec.mainClass="es.caib.projectebase.sqlgenerator.SqlGenerator" -Dexec.args="projectebasePU %1%"

REM mvn package -Pgensql
