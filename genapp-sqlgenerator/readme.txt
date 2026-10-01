(1) Generar scripts de Base de Dades: 
     - Anar a projecteorigen\scripts\sqlgenerator
     - Executar la comanda "gensql.bat org.hibernate.dialect.PostgreSQL82Dialect" i copiar els fitxers 
       create_schema.sql i drop_schema.sql al directori projecteorigen\scripts\bdd\postgresql.
     - Executar la comanda "gensql.bat org.hibernate.dialect.Oracle10gDialect" i copiar els fitxers 
       create_schema.sql, create_schema_caib.sql i drop_schema.sql al directori projecteorigen\scripts\bdd\oracle.