# ![Logo](https://github.com/GovernIB/genapp/blob/binaris/projectinfo_Attachments/icon.jpg) Generador d'Aplicacions (genapp)  


**Descripció**

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

** Captures de pantalla **

![Formulari Web Generat](https://github.com/GovernIB/genapp/blob/binaris/projectinfo_Attachments/screenshots/genapp_form.png)
Formulari Web Generat

![Llistat Web Generat](https://github.com/GovernIB/genapp/blob/binaris/projectinfo_Attachments/screenshots/genapp_llistat.png)
Llistat Web Generat

![Aplicació GenApp:  Menú principal](https://github.com/GovernIB/genapp/blob/binaris/projectinfo_Attachments/screenshots/genapp_aplicacio_menu.png)
Aplicació GenApp:  Menú principal

![Aplicació GenApp: Edició de les propietats d'una taula](https://github.com/GovernIB/genapp/blob/binaris/projectinfo_Attachments/screenshots/genapp_aplicacio_editar_projecte.png)
Aplicació GenApp: Edició de les propietats d'una taula

