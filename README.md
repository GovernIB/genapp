# ![Logo](https://github.com/GovernIB/maven/blob/binaris/genapp/projectinfo_Attachments/icon.jpg) GenApp v2.0 (genapp)  
*Generador d'Aplicacions*

***Descripció***

A partir d'una bbdd en postgresql o oracle, genera totes les capes d'una aplicació web: model, jpa, ejb, logica(ejb), web i webservices, per Java 11 i JBoss 7.2.

***Requeriments***
* JDK 11
* JBoss 7.2

***Com executar***

(0) Afegir al fitxer settings.xml ([HOME]/.m2/) la següent entrada dins <settings>\<profiles>:

```
<settings  ...>
  ...
	<profiles>
		<profile>
			<id>governib-maven-repos</id>
			<activation>
				<activeByDefault>true</activeByDefault>
			</activation>
			<repositories>
				<repository>
					<id>github-governib-maven-repos</id>
					<name>GitHub GovernIB Maven Repository</name>
					<url>https://governib.github.io/maven/maven/</url>
				</repository>
			</repositories>
		</profile>
	</profiles>
  ...
</settings>
```

(1) Executar la següent comanda

```
mvn archetype:generate -DarchetypeGroupId=org.fundaciobit.genapp -DarchetypeArtifactId=genapp-exec
-DarchetypeVersion=2.0.0-SNAPSHOT -B -Dpackage=rungenapp -DgroupId=rungenapp -DartifactId=rungenapp -Dversion=2.0.0-SNAPSHOT
```

![Exemple](https://github.com/GovernIB/maven/blob/binaris/genapp/images/comanda_rungenapp.png)
	
	
(2) Es crearà un directori rungenapp, entrar-hi i executar genapp.bat. Pitjar sobre el boto "Generar Taules Base"

(3) Seguir les instruccions del diàleg que apareixerà al final.

***Caracteristiques:***
* Incorpora un llenguatge d'alt nivell generat per realitzar consultes a bbdd de forma que l'aplicació es pot construir sense haver d'escriure ni una linia de codi SQL. D'aqui que qualsevol canvi produeixi errors de compilació i no d'execució com passava amb el codi SQL.
* Gestiona traduccions en multiples idiomes
* Gestiona la pujada de fitxers
* EJBs (lògica) extensibles
* Llistats i formularis Webs extensibles i totalment configurables

El projecte està partit en tres subprojectes:
* genapp-core: classes base i utilitats genèriques (classes del llenguatge de consultes a BBDD, classes d'excepcions de WebServices, ...)
* genapp-web: classes base i d'utilitat per entorn Web (emprant Spring)
* genapp-gen: generador de les diferent caps del projecte abans descrita i generador de sql a partir d'hibernate

***Documentació***

[Accedir a Documentació en linia](../../tree/genapp-2.0/README.md#documentaci%C3%B3)

***Dades***

* Registered: 2013-02-19
* Project Labels: Generador  JPA  EJB 3.0  Spring  Web  WebServices  WS  Java 11  JBoss 7.2
* Topic: Agile development tools,  Frameworks,  Templates
* License:  European Union Public License, GNU General Public License version 3.0 (GPLv3)
* Database Environment: JDBC
* Development Status: Production/Stable
* Intended Audience:  Government
* Operating System:  OS Independent (Written in an interpreted language)
* Programming Language: Java
* User Interface: Java Swing, Console/Terminal
