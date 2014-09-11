

call mvn install:install-file -Dfile=./lib/genapp-core-1.0.jar -DgroupId=org.fundaciobit.genapp -DartifactId=genapp-core -Dversion=1.0 -Dpackaging=jar


call mvn install:install-file -Dfile=./lib/genapp-web-1.0.jar -DgroupId=org.fundaciobit.genapp -DartifactId=genapp-web -Dversion=1.0 -Dpackaging=jar

call mvn install:install-file -Dfile=./lib/genapp-gen-1.0.jar -DgroupId=org.fundaciobit.genapp -DartifactId=genapp-gen -Dversion=1.0 -Dpackaging=jar

mvn exec:java -Dgenapp_run=true -Dexec.mainClass="org.fundaciobit.genapp.generator.gui.RebApp" 