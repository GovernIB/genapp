REM Si no volem LOBs en un altre tablespace llavors afegir la següent
REM linia despres de mvn: -Dsqlgenerator.oracle.generatelob=false
mvn exec:java -Dsqlgenerator.project.name=${name} -Dexec.mainClass="org.fundaciobit.genapp.gensql.SqlGenerator" -Dexec.args="${name}DB %1%"
