



NOTA: Al repositori de Maven Central només es poden pujar versions "no" SNAPSHOT. 

Quan es vulgui enviar una nova versió s'ha de pujar la versió, ja que un cop pujada una versió release, llavors no es pot enviar de nou una mateixa versió.

S'ha de crear una crontrasenya per signar el jar tal i com explica a l'apartat "manual"

Aquesta contrasenya s'ha d'utilitzar com a paràmetre a la cridada a "deploy.bat" 








Manual: https://yoprogramo.com/2019/06/16/subir-a-maven-central-una-libreria-propia/

Generacio de Signatures:
            - PGP:  http://www.gnupg.org/download/
            - https://blog.sonatype.com/2010/01/how-to-generate-pgp-signatures-with-maven/


sonatype: governdigital@fundaciobit.org | usr: kitanibaladmin |pwd: <<ESTA AL KEEPASSWEB>>


Issue d'Alta: https://issues.sonatype.org/browse/OSSRH-95981

Gestio del Repositori: https://s01.oss.sonatype.org/


ERRORS:

-------------------------
[ERROR]
[ERROR] Nexus Staging Rules Failure Report
[ERROR] ==================================
[ERROR]
[ERROR] Repository "orgfundaciobitgenapp-1005" failures
[ERROR]   Rule "RepositoryWritePolicy" failures
[ERROR]     * Artifact updating: Repository ='releases:Releases' does not allow updating artifact='/org/fundaciobit/genapp/genapp-exec/2.0.0/genapp-exec-2.0.0.pom'
[ERROR]     * Artifact updating: Repository ='releases:Releases' does not allow updating artifact='/org/fundaciobit/genapp/genapp-exec/2.0.0/genapp-exec-2.0.0-sources.jar'
[ERROR]     * Artifact updating: Repository ='releases:Releases' does not allow updating artifact='/org/fundaciobit/genapp/genapp-exec/2.0.0/genapp-exec-2.0.0.jar'
[ERROR]
[ERROR]
-------------------------

Això significa que la versió ja existeix en el REPOSITORI i s'ha de incrementar.