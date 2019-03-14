# ![Logo](https://github.com/GovernIB/maven/blob/binaris/genapp/projectinfo_Attachments/icon.jpg) Generador d'Aplicacions (genapp)  


***Descripció***

A partir d'una bbdd en postgresql o oracle, genera totes les capes d'una aplicació web: model, jpa, ejb, logica(ejb), web i webservices. Caracteristiques:
* Incorpora un llenguatge d'alt nivell generat per realitzar consultes a bbdd de forma que l'aplicació es pot construir sense haver d'escriure ni una linia de codi SQL. D'aqui que qualsevol canvi produeixi errors de compilació i no d'execució com passava amb el codi SQL.
* Gestiona traduccions en multiples idiomes
* Gestiona la pujada de fitxers
* EJBs (lògica) extendibles
* Llistats i formularis Webs extendibles i totalment configurables

El projecte està partit en tres subprojectes:
* genapp-core: classes base i utilitats genèriques (classes del llenguatge de consultes a BBDD, classes d'excepcions de WebServices, ...)
* genapp-web: classes base i d'utilitat per entorn Web (emprant Spring)
* genapp-gen: generador de les diferent caps del projecte abans descrita i generador de sql a partir d'hibernate

***Característiques***

* Registered: 2013-02-19
* Project Labels: Generador  JPA  EJB 3.0  Spring  Web  WebServices  WS  Java  JBoss 5.1  JBoss 5.2  
* Topic: Agile development tools,  Frameworks,  Templates
* License:  European Union Public License, GNU General Public License version 3.0 (GPLv3)
* Database Environment: JDBC
* Development Status: Production/Stable
* Intended Audience:  Government
* Operating System:  OS Independent (Written in an interpreted language)
* Programming Language: Java
* Translations: Spanish, Catalan
* User Interface: Java Swing, Console/Terminal
