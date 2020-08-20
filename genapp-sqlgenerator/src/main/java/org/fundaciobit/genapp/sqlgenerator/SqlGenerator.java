package org.fundaciobit.genapp.sqlgenerator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.persistence.Persistence;

import org.hibernate.dialect.Dialect;

/**
 * 
 * @author anadal
 * 
 */
public class SqlGenerator {

    private static final String SHORTNAMES_FILE = "shortNames.properties";

    public static class ClassComparator implements Comparator<Class<?>> {

        public int compare(Class<?> o1, Class<?> o2) {
            return o1.getSimpleName().compareToIgnoreCase(o2.getSimpleName());
        }

    }

    public static void main(String[] args) {

        final int NUM_PARAMS = 2;

        if (args.length < NUM_PARAMS || args.length > (NUM_PARAMS + 1)) {
            System.err.println("Usage:   Sqlgenerator [projectName] [persistentUnit] [dialect(optional)] ");
            System.exit(-1);
        }

        String projectName = args[0];
        String persistenceUnit = args[1];

        try {
            String dialect;
            if (args.length > NUM_PARAMS) {
                dialect = args[NUM_PARAMS];
            } else {
                Class<?> dialecte = selectDialect();
                dialect = dialecte.getName();
            }

            System.out.println("Ha elegit el dialecte " + dialect);

            Properties hash = new Properties();

            if (dialect.startsWith("org.hibernate.dialect.Oracle")) {
                if (dialect.equals("org.hibernate.dialect.Oracle12cDialect")) {
                    dialect = Oracle12cDialectCAIB.class.getName();
                } else if (dialect.equals("org.hibernate.dialect.Oracle10gDialect")) {
                    dialect = Oracle10gDialectCAIB.class.getName();
                } else if (dialect.equals("org.hibernate.dialect.Oracle8iDialect")) {
                    dialect = Oracle8iDialectCAIB.class.getName();
                } else if (dialect.equals("org.hibernate.dialect.Oracle9iDialect")) {
                    dialect = Oracle9iDialectCAIB.class.getName();
                } else if (dialect.equals("org.hibernate.dialect.Oracle9Dialect")
                        || dialect.equals("org.hibernate.dialect.OracleDialect")) {
                    throw new Exception("El dialecte " + dialect + " no esta suportat.");
                } else {
                    System.err.println("================ WARNING !!! ==============");
                    System.err.println(" El dialecte " + dialect + " no te adaptador" + " per BigDecinal o BigInteger");
                    System.err.println("===========================================");
                }
            }

            hash.put("hibernate.dialect", dialect);

            String path = "META-INF/persistence.xml";

            URL url = SqlGenerator.class.getResource("/" + path);
            if (url == null) {
                System.err.println("No puc trobar el recurs " + path);
                System.exit(-1);
            }

            File schemaFile = new File("create_schema.sql");
            File dropFile = new File("drop_schema.sql");

            createSchema(dialect, persistenceUnit, schemaFile, dropFile);

            oracleCaib(schemaFile, dialect, projectName);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void createSchema(String dialect, String persistenceUnit, File schemaFile, File dropFile) {
        // Les propietats per la generació es poden indicar dins el persistence.xml o es
        // poden
        // passar dins el Map.

        Map<String, String> map = new HashMap<String, String>();

        if (schemaFile.exists()) {
            schemaFile.delete();
        }

        if (dropFile.exists()) {
            dropFile.delete();
        }

        // Configuració per generar els scripts de creació de la base de dades -->
        map.put("javax.persistence.schema-generation.scripts.action", "drop-and-create");
        map.put("javax.persistence.schema-generation.scripts.create-target", schemaFile.getAbsolutePath());
        map.put("javax.persistence.schema-generation.scripts.drop-target", dropFile.getAbsolutePath());

        /*
         * Propietats específiques de Hibernate
         * https://docs.jboss.org/hibernate/orm/5.3/userguide/html_single/
         * Hibernate_User_Guide.html#configurations
         */
        map.put("hibernate.show_sql", "true");
        map.put("hibernate.format_sql", "true");
        map.put("hibernate.hbm2ddl.delimiter", ";");
        
        
        
        map.put("hibernate.connection.SetBigStringTryClob", "true");
        map.put("hibernate.jdbc.batch_size", "0");
        
        
        // map.put("provider", "org.hibernate.jpa.HibernatePersistenceProvider");

        /*
         * Si no indicam una connexió de bdd (per la generació no cal), Hibernate
         * necessita que indiquem el dialecte que emprarà per la generació. Postgresql:
         * org.hibernate.dialect.PostgreSQL95Dialect Oracle:
         * org.hibernate.dialect.Oracle12cDialect
         */

        // String dialect = "org.hibernate.dialect.PostgreSQL95Dialect";
        // String dialect = "org.hibernate.dialect.Oracle12cDialect";

        map.put("hibernate.dialect", dialect);
        if (dialect.indexOf("Oracle") != -1) {
            map.put("hibernate.query.substitutions", "true 1, false 0");
        }

        Persistence.generateSchema(persistenceUnit, map);

    }

    public static Class<?> selectDialect() throws Exception, ClassNotFoundException {
        String pkg = "org.hibernate.dialect";
        List<String> list = getClassNamesFromPackage(pkg);

        List<Class<?>> dialects = new ArrayList<Class<?>>();

        for (String str : list) {

            Class<?> class1 = Class.forName(pkg + "." + str);

            if (Dialect.class.isAssignableFrom(class1) && !Modifier.isAbstract(class1.getModifiers())) {

                dialects.add(class1);

            }

        }

        Collections.sort(dialects, new ClassComparator());

        int count = 0;
        for (Class<?> class1 : dialects) {
            System.out.println(count + ".- " + class1.getName());
            count++;
        }

        System.out.println(" -------------------------------- ");
        System.out.println(" Seleciona un dialecte: ");

        Scanner input = new Scanner(System.in);
        int pos = input.nextInt();

        Class<?> dialecte = dialects.get(pos);

        input.close();

        System.out.println();
        System.out.println();
        return dialecte;
    }

    public static ArrayList<String> getClassNamesFromPackage(String packageName) throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL packageURL;
        ArrayList<String> names = new ArrayList<String>();

        packageName = packageName.replace(".", "/");
        packageURL = classLoader.getResource(packageName);

        if (packageURL.getProtocol().equals("jar")) {
            String jarFileName;
            JarFile jf;
            Enumeration<JarEntry> jarEntries;
            String entryName;

            // build jar file name, then loop through zipped entries
            jarFileName = URLDecoder.decode(packageURL.getFile(), "UTF-8");
            jarFileName = jarFileName.substring(5, jarFileName.indexOf("!"));
            System.out.println(">" + jarFileName);
            jf = new JarFile(jarFileName);
            jarEntries = jf.entries();
            while (jarEntries.hasMoreElements()) {
                entryName = jarEntries.nextElement().getName();

                if (entryName.endsWith("/")) {
                    continue;
                }

                if (entryName.startsWith(packageName) && entryName.length() > packageName.length() + 5) {
                    entryName = entryName.substring(packageName.length() + 1, entryName.lastIndexOf('.'));

                    if (entryName.indexOf('/') != -1) {
                        continue;
                    }

                    names.add(entryName);
                }
            }

            jf.close();

            // loop through files in classpath
        } else {
            URI uri = new URI(packageURL.toString());
            File folder = new File(uri.getPath());
            // won't work with path which contains blank (%20)
            // File folder = new File(packageURL.getFile());
            File[] contenuti = folder.listFiles();
            String entryName;
            for (File actual : contenuti) {
                entryName = actual.getName();
                entryName = entryName.substring(0, entryName.lastIndexOf('.'));
                names.add(entryName);
            }
        }
        return names;
    }

    public static String checkValue(String key, Map<String, String> prop) throws Exception {

        if (prop.containsKey(key)) {
            String value = prop.get(key);
            if (value.length() > 30) {
                System.err.println("El valor [" + value + "] assignat a la clau " + key
                        + " te una longitud superior a 30 dins el fitxer " + SHORTNAMES_FILE);
                throw new Exception();
            }
            return value;
        } else {
            if (key.length() > 30) {
                System.err.println("La clau " + key + " te una longitud superior a 30.");
                System.err.println("Ha de definir un nom curt dins el fitxer " + SHORTNAMES_FILE);
                System.err.println(" Per exemple:");
                System.err.println("      " + key.replace(" ", "\\u0020") + "=" + key.substring(0, 30));
                throw new Exception();
            }
            return key;
        }

    }

    public static void oracleCaib(File schemaFile, String dialect, String projectName) throws Exception {

        File parent = schemaFile.getParentFile();

        File file_seq = new File(parent, "01_sequences.sql");

        File file_tab = new File(parent, "02_tables.sql"); // (inclouen clau primaria)

        File file_ind = new File(parent, "03_indexes.sql");

        File file_con = new File(parent, "04_constraints.sql"); // (foreignkeys i unikes)

        File file_blo = new File(parent, "05_blobs.sql");

        File file_gra = new File(parent, "06_grants.sql");

        StringBuffer allSequences = new StringBuffer();
        StringBuffer allTables = new StringBuffer();
        StringBuffer allIndexes = new StringBuffer();
        StringBuffer allConstraints = new StringBuffer();
        StringBuffer allLOBs = new StringBuffer();
        StringBuffer allGrants = new StringBuffer();

        File[] files = { file_seq, file_tab, file_ind, file_con, file_blo, file_gra };
        StringBuffer[] buffers = { allSequences, allTables, allIndexes, allConstraints, allLOBs, allGrants };

        for (File file : files) {
            if (file.exists()) {
                file.delete();
            }
        }

        // String filename = schemaFile2.getName();
        // int punt = filename.lastIndexOf('.');
        // String caibFileName = filename.substring(0, punt) + "_caib." +
        // filename.substring(punt + 1);

        Map<Integer, String> tagsReplacer = new HashMap<Integer, String>();
        int tagCounter = 0;

        if (dialect == null || dialect.indexOf("Oracle") == -1) {
            System.out.println("------------ No Dialect ORACLE");
            return;
        }

        java.util.Map<String, String> shortnames = new java.util.TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);

        {
            Properties prop = new Properties();
            File shortnamesfile = new File(SHORTNAMES_FILE);
            if (shortnamesfile.exists()) {
                prop.load(new FileInputStream(shortnamesfile));

                for (Object key : prop.keySet()) {
                    shortnames.put((String) key, prop.getProperty((String) key));
                }

            }
        }

        final String postIndex = shortnames.get("__postindex_");

        final boolean generatelob;
        {
            String genblobvalue = System.getProperty("sqlgenerator.oracle.generatelob");
            if (genblobvalue == null) {
                generatelob = true;
            } else {
                generatelob = "true".equals(genblobvalue.trim());
            }
        }

        BufferedReader br = new BufferedReader(new FileReader(schemaFile));
        try {
            String table = null;

            StringBuffer allUniques = new StringBuffer();
            StringBuffer allPKs = new StringBuffer();
            StringBuffer allFKs = new StringBuffer();

            final String uk = "unique (";
            final String pk = "primary key (";
            final String ct = "create table ";
            final String cs = "create sequence ";

            // final String projectName = persistenceunit;

            for (String l; (l = br.readLine()) != null;) {
                String line = l.trim();

                // Project Name

                // Create table
                if (line.startsWith(ct)) {

                    table = line.substring(line.indexOf(ct) + ct.length(), line.indexOf("("));

                    table = table.trim();

                    // shortName = prop.getProperty(table);

                    allGrants.append(
                            "    grant select,insert,delete,update on " + table + " to www_" + projectName + ";\n");

                    allTables.append(l).append('\n');
                    continue;
                }

                // alter table STR_DOCNIV
                // add constraint STR_DNV_PK primary key (DNV_CODIGO);
                if (line.startsWith(pk)) {

                    String pkC = checkValue(table + "_pk", shortnames);
                    allPKs.append("    alter table " + table + " add constraint " + pkC + " ");
                    if (line.endsWith(",")) {
                        allPKs.append(line.substring(0, line.length() - 1));
                    } else {
                        allPKs.append(line);
                    }
                    allPKs.append(";\n\n");
                    continue;
                }

                // alter table STR_DOCNIV
                // add constraint STR_DNVNIV_UNI unique (DNV_CODDOC, DNV_NIVAUT);

                // UNique Multiple (de varies columnes)
                if (line.startsWith(uk)) {

                    String uniqueSimple;
                    if (line.endsWith(",")) {
                        uniqueSimple = line.substring(line.indexOf("("), line.length() - 1);
                    } else {
                        uniqueSimple = line.substring(line.indexOf("("));
                    }

                    String uniqueName = checkValue(table + "_UNIQUE_" + uniqueSimple, shortnames);

                    allUniques.append("    alter table " + table + " add constraint " + uniqueName + " unique "
                            + uniqueSimple + ";\n");
                    continue;
                }

                // Unique Simple (d'una columna)
                if (line.indexOf(" unique ") != -1) { // line.endsWith(" unique,") || line.endsWith(" unique")) {

                    allTables.append(l.substring(0, l.lastIndexOf(" unique")));
                    if (line.endsWith(" unique,")) {
                        allTables.append(",\n");
                    } else {
                        allTables.append("\n");
                    }

                    String column = line.substring(0, line.indexOf(' '));

                    String uniqueName = checkValue(table + "_" + column + "_uk", shortnames);
                    allUniques.append("    alter table " + table + " add constraint " + uniqueName + " unique ("
                            + column + ");\n");

                    continue;
                }

                if (line.startsWith("alter table ")) {

                    StringBuffer unknown = new StringBuffer();

                    unknown.append("\n").append(l).append("\n");
                    while ((l = br.readLine()) != null) {
                        unknown.append(l).append("\n");
                        if (l.endsWith(";")) {
                            br.readLine(); // Retorn de carro

                            break;
                        }
                    }

                    String u = unknown.toString();

                    if (u.indexOf(" unique (") == -1) {
                        allFKs.append(u);
                    } else {
                        allUniques.append(u);
                    }

                    continue;
                }

                if (line.startsWith("create index ")) {

                    if (postIndex == null) {
                        allIndexes.append(l).append("\n");
                    } else {
                        final int c = tagCounter++;
                        tagsReplacer.put(c, postIndex);
                        final int pos = l.lastIndexOf(';');
                        allIndexes.append(l.substring(0, pos));
                        allIndexes.append(getFormat(c));
                        allIndexes.append(l.substring(pos)).append("\n");
                    }
                    br.readLine(); // Retorn de carro
                    continue;
                }

                // Final taula
                if (line.startsWith(");")) {

                    if (allTables.toString().endsWith(",\n")) {
                        // Hem de llevar la coma
                        allTables.setLength(allTables.length() - 2);
                        allTables.append('\n');
                    }

                    String postStr = shortnames.get("__post_" + table);
                    // hem d'afegir alguna cosa al final de la taula (p.e. Partitions) ?
                    if (postStr == null) {
                        allTables.append(l);
                    } else {

                        final int c = tagCounter++;
                        tagsReplacer.put(c, postStr);

                        allTables.append(l.replace(";", ""));
                        allTables.append(getFormat(c));
                        allTables.append(";");
                    }

                    allTables.append("\n");

                    table = null;
                    // shortName = null;
                    continue;
                }

                // Create sequence
                if (line.startsWith(cs)) {
                    int to = line.indexOf(' ', line.indexOf(cs) + cs.length() + 2);
                    String seqname = line.substring(line.indexOf(cs) + cs.length(), to);
                    allGrants.append("    grant select on " + seqname + " to www_" + projectName + ";\n");
                    allSequences.append(line).append('\n');
                    continue;
                }

                // Mirar si és CLOB o BLOB
                if (generatelob) {
                    String lineLowerCase = line.toLowerCase();
                    if (lineLowerCase.indexOf(" blob,") != -1 || lineLowerCase.indexOf(" blob ") != -1
                            || lineLowerCase.indexOf(" clob,") != -1 || lineLowerCase.indexOf(" clob ") != -1
                            || lineLowerCase.indexOf(" nclob,") != -1 || lineLowerCase.indexOf(" nclob ") != -1)  {

                        String field = line.substring(0, line.indexOf(' '));

                        String lobname = checkValue(table + "_" + field + "_lob", shortnames);

                        allLOBs.append("    alter table " + table + " move lob (" + field + ")" + " store as " + lobname
                                + " (tablespace " + projectName + "_lob index " + lobname + "_i);\n");

                        continue;
                    }
                } else {
                    
                    String lineLowerCase = line.toLowerCase();
                    if (lineLowerCase.indexOf(" long") != -1) {
                        allTables.append(l.replace(" long",  " clob")).append("\n");
                        continue;
                    }
                    
                }

                // Afegir la linia original
                if (table == null && l.trim().length() != 0) {
                    System.err.println("AFEGIDA LINIA INCONTROLADA: ]" + l + "[");
                }

                allTables.append(l).append("\n");
            }

            allTables.append("\n");
            allTables.append("\n");

            allConstraints.append("\n -- INICI PKs\n");
            allConstraints.append(allPKs.toString());
            allConstraints.append(" -- FINAL PKs\n\n");

            allConstraints.append("\n -- INICI FKs\n");
            allConstraints.append(allFKs.toString());
            allConstraints.append(" -- FINAL FKs\n\n");

            if (allUniques.length() != 0) {
                allConstraints.append("\n -- INICI UNIQUEs\n");
                allConstraints.append(allUniques.toString());
                allConstraints.append(" -- FINAL UNIQUEs\n\n");
            }

            for (int i = 0; i < buffers.length; i++) {
                guardarFitxer(files[i], tagsReplacer, buffers[i].toString());
            }


        } finally {
            try {
                br.close();
            } catch (Exception e) {
            }

        }

    }

    private static void guardarFitxer(File file, Map<Integer, String> tagsReplacer, String out)
            throws FileNotFoundException, IOException {
        {

            if (out == null || out.trim().length() == 0) {
                return;
            }

            FileOutputStream fos = new FileOutputStream(file);

            String toWrite = out.replace(",\n    );", "\n    );");

            for (int c : tagsReplacer.keySet()) {
                toWrite = toWrite.replace(getFormat(c), tagsReplacer.get(c));
            }

            fos.write(toWrite.getBytes());
            fos.flush();
            fos.close();
        }
    }

    public static String getFormat(final int c) {
        return "__%%" + String.format("%06d", c) + "%%__";
    }

    /**
     * 
     * @author anadal
     *
     */
    public static class Oracle10gDialectCAIB extends org.hibernate.dialect.Oracle10gDialect {

        @Override
        protected void registerNumericTypeMappings() {
            super.registerNumericTypeMappings();
            registerColumnType(Types.NUMERIC, "number");
        }

    }

    /**
     * 
     * @author anadal
     *
     */
    public static class Oracle12cDialectCAIB extends org.hibernate.dialect.Oracle12cDialect {

        @Override
        protected void registerNumericTypeMappings() {
            super.registerNumericTypeMappings();
            
            
            System.out.println("getTypeName(Types.BLOB) => " + super.getTypeName(Types.BLOB));
            System.out.println("getTypeName(Types.CLOB) => " + super.getTypeName(Types.CLOB));
           System.out.println("getTypeName(Types.NCLOB) => " + super.getTypeName(Types.NCLOB));
            
            registerColumnType(Types.NUMERIC, "number");
            

        }

    }

    /**
     * 
     * @author anadal
     *
     */
    public class Oracle8iDialectCAIB extends org.hibernate.dialect.Oracle8iDialect {

        @Override
        protected void registerNumericTypeMappings() {
            super.registerNumericTypeMappings();
            registerColumnType(Types.NUMERIC, "number");
        }

    }

    /**
     * 
     * @author anadal
     *
     */
    public class Oracle9iDialectCAIB extends org.hibernate.dialect.Oracle9iDialect {

        @Override
        protected void registerNumericTypeMappings() {
            super.registerNumericTypeMappings();
            registerColumnType(Types.NUMERIC, "number");
        }

    }
    
    


}