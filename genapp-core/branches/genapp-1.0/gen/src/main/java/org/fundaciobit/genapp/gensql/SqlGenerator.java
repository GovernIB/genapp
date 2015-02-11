package org.fundaciobit.genapp.gensql;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.Oracle9iDialect;
import org.hibernate.ejb.Ejb3Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

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

    if (args.length < 1) {
      System.err.println("Usage:     Sqlgenerator [persistentunit] [dialect](optional) ");
      System.exit(-1);
    }
    String persistenceunit2 = args[0];
    
    String projectName = System.getProperty("sqlgenerator.project.name");
    if (projectName == null) {
      projectName = persistenceunit2;
    }

    try {
      String dialect;
      if (args.length > 1) {
        dialect = args[1];
      } else {
        Class<?> dialecte = selectDialect();
        dialect = dialecte.getName();
      }

      System.out.println("Ha elegit el dialecte " + dialect);

      Properties hash = new Properties();
      
      if (dialect.startsWith("org.hibernate.dialect.Oracle")) {
        if (dialect.equals("org.hibernate.dialect.Oracle10gDialect")) {
          dialect = Oracle10gDialectCAIB.class.getName();
        } else if (dialect.equals("org.hibernate.dialect.Oracle8iDialect")) {
          dialect = Oracle8iDialectCAIB.class.getName();
        } else if (dialect.equals("org.hibernate.dialect.Oracle9iDialect")) {
          dialect = Oracle9iDialectCAIB.class.getName();
        } else if (dialect.equals("org.hibernate.dialect.Oracle9Dialect")
            || dialect.equals("org.hibernate.dialect.OracleDialect") ) {
          throw new Exception("El dialecte " + dialect + " no esta suportat.");
        } else {
          System.err.println("================ WARNING !!! ==============");
          System.err.println(" El dialecte " + dialect + " no te adaptador"
              + " per BigDecinal o BigInteger");
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

      Ejb3Configuration cfg3 = new Ejb3Configuration().configure(persistenceunit2, hash);

      cfg3.addResource(path);

      Configuration cfg = cfg3.getHibernateConfiguration();

      File schemaFile = new File(projectName + "_create_schema.sql");

      SchemaExport export = new SchemaExport(cfg);
      export.setDelimiter(";");
      export.setOutputFile(schemaFile.getAbsolutePath());
      export.setFormat(true);

      export.execute(true, false, false, true);

      oracleCaib(schemaFile, dialect, projectName);

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public static Class<?> selectDialect() throws Exception, ClassNotFoundException {
    String pkg = "org.hibernate.dialect";
    List<String> list = getClassNamesFromPackage(pkg);

    List<Class<?>> dialects = new ArrayList<Class<?>>();

    for (String str : list) {

      Class<?> class1 = Class.forName(pkg + "." + str);

      if (Dialect.class.isAssignableFrom(class1)
          && !Modifier.isAbstract(class1.getModifiers())) {

        dialects.add(class1);

      }

    }

    Collections.sort(dialects, new ClassComparator());

    int count = 0;
    for (Class<?> class1 : dialects) {
      System.out.println(count + ".- " + class1.getSimpleName());
      count++;
    }

    System.out.println(" -------------------------------- ");
    System.out.println(" Seleciona un dialecte: ");

    Scanner input = new Scanner(System.in);
    int pos = input.nextInt();

    Class<?> dialecte = dialects.get(pos);

    System.out.println();
    System.out.println();
    return dialecte;
  }

  public static ArrayList<String> getClassNamesFromPackage(String packageName)
      throws Exception {
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    URL packageURL;
    ArrayList<String> names = new ArrayList<String>();
    ;

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
          entryName = entryName
              .substring(packageName.length() + 1, entryName.lastIndexOf('.'));

          if (entryName.indexOf('/') != -1) {
            continue;
          }

          names.add(entryName);
        }
      }

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
        System.err.println("      " + key.replaceAll(" ", "\u0020") + "="
            + key.substring(0, 30));
        throw new Exception();
      }
      return key;
    }

  }

  public static void oracleCaib(File schemaFile2, String dialect, String projectName)
      throws Exception {

    String filename = schemaFile2.getName();
    int punt = filename.lastIndexOf('.');
    String caibFileName = filename.substring(0, punt) + "_caib."
        + filename.substring(punt + 1);
    
    if (dialect == null || dialect.indexOf("Oracle") == -1) {
      System.out.println("------------ No Dialect ORACLE");
      // Borram versio ORACLE-CAIB de sql 
      File f = new File(caibFileName);
      if (f.exists()) {
        f.delete();
      }
      return;
    }


    java.util.Map<String, String> shortnames = new java.util.TreeMap<String, String>(
        String.CASE_INSENSITIVE_ORDER);

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
    
    
    final boolean generatelob;
    {
      String genblobvalue = System.getProperty("sqlgenerator.oracle.generatelob");
      if (genblobvalue == null) {
        generatelob = true;
      } else {
        generatelob = "true".equals(genblobvalue.trim());
      }
    }
    

    StringBuffer out = new StringBuffer();

    BufferedReader br = new BufferedReader(new FileReader(schemaFile2));
    try {
      String table = null;

      StringBuffer allUniques = new StringBuffer();
      StringBuffer allPKs = new StringBuffer();
      StringBuffer allFKs = new StringBuffer();
      StringBuffer allIndexes = new StringBuffer();
      StringBuffer allGrants = new StringBuffer();
      StringBuffer allLOBs = new StringBuffer();

      final String uk = "unique (";
      final String pk = "primary key (";
      final String ct = "create table ";
      final String cs = "create sequence ";

      //final String projectName = persistenceunit;

      for (String l; (l = br.readLine()) != null;) {
        String line = l.trim();

        // Project Name

        // Create table
        if (line.startsWith(ct)) {

          table = line.substring(line.indexOf(ct) + ct.length(), line.indexOf("("));

          table = table.trim();

          // shortName = prop.getProperty(table);

          allGrants.append("    grant select,insert,delete,update on " + table + " to www_"
              + projectName + ";\n");

          out.append(l).append('\n');
          continue;
        }

        // alter table STR_DOCNIV
        // add constraint STR_DNV_PK primary key (DNV_CODIGO);
        if (line.startsWith(pk)) {

          /*
           * String pkC = table + "_pk"; if (pkC.length() > 30) { pkC =
           * projectPrefix + "_"+ shortName + "_pk"; }
           */
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

          allUniques.append("    alter table " + table + " add constraint " + uniqueName
              + " unique " + uniqueSimple + ";\n");
          continue;
        }

        // Unique Simple (d'una columna)
        if (line.endsWith(" unique,") || line.endsWith(" unique")) {

          out.append(l.substring(0, l.lastIndexOf(" unique")));
          if (line.endsWith(" unique,")) {
            out.append(",\n");
          } else {
            out.append("\n");
          }

          String column = line.substring(0, line.indexOf(' '));

          String uniqueName = checkValue(table + "_" + column + "_uk", shortnames);
          allUniques.append("    alter table " + table + " add constraint " + uniqueName
              + " unique (" + column + ");\n");

          continue;
        }

        if (line.startsWith("alter table ")) {
          allFKs.append("\n").append(l).append("\n");
          while ((l = br.readLine()) != null) {
            allFKs.append(l).append("\n");
            if (l.endsWith(";")) {
              br.readLine(); // Retorn de carro

              break;
            }
          }
          continue;
        }

        if (line.startsWith("create index ")) {
          allIndexes.append(l).append("\n");
          br.readLine(); // Retorn de carro
          continue;
        }

        // Final taula
        if (line.startsWith(");")) {

          out.append(l).append("\n");

          table = null;
          // shortName = null;
          continue;
        }

        // Create sequence
        if (line.startsWith(cs)) {
          String seqname = line.substring(line.indexOf(cs) + cs.length(), line.indexOf(";"));
          allGrants.append("    grant select on " + seqname + " to www_" + projectName + ";\n");

        }
        
        // Mirar si és CLOB o BLOB
        if (generatelob) {
          String lineLowerCase = line.toLowerCase();
          if (lineLowerCase.indexOf(" blob,") != -1 || lineLowerCase.indexOf(" blob ") != -1
            || lineLowerCase.indexOf(" clob,") != -1 || lineLowerCase.indexOf(" clob ") != -1
            || lineLowerCase.indexOf(" nclob,") != -1 || lineLowerCase.indexOf(" nclob ") != -1) {
            
            String field = line.substring(0, line.indexOf(' '));
            
            
            String lobname = checkValue(table + "_" + field + "_lob", shortnames);
            
            allLOBs.append("    alter table " + table + " move lob (" + field + ")"
                + " store as " + lobname + " (tablespace "+ projectName + "_lob index " + lobname + "_i);\n");
          }
        }

        // Afegir la linia original
        out.append(l).append("\n");
      }

      out.append("\n");
      out.append("\n");

      out.append(" -- INICI Indexes\n");
      out.append(allIndexes.toString());
      out.append(" -- FINAL Indexes\n\n");

      out.append(" -- INICI PK's\n");
      out.append(allPKs.toString());
      out.append(" -- FINAL PK's\n\n");

      out.append(" -- INICI FK's\n");
      out.append(allFKs.toString());
      out.append(" -- FINAL FK's\n\n");

      if (allUniques.length() != 0) {
        out.append(" -- INICI UNIQUES\n");
        out.append(allUniques.toString());
        out.append(" -- FINAL UNIQUES\n\n");
      }

      // String filenameBase = schemaFile2.getAbsolutePath();
      

      schemaFile2.renameTo(new File(filename + "_original"));

      {
        FileOutputStream fos = new FileOutputStream(filename);

        fos.write(out.toString().replace(",\n    );", "\n    );").getBytes());
        fos.flush();
        fos.close();
      }

      out.append(" -- INICI GRANTS\n");
      out.append(allGrants.toString());
      out.append(" -- FINAL GRANTS\n\n");
      
      if (allLOBs.length() != 0) {
        out.append(" -- INICI LOBS\n");
        out.append(allLOBs.toString());
        out.append(" -- FINAL LOBS\n\n");
      }

      { 
        FileOutputStream fos = new FileOutputStream(caibFileName);

        fos.write(out.toString().replace(",\n    );", "\n    );").getBytes());
        fos.flush();
        fos.close();
      }
    } finally {
      try {
        br.close();
      } catch (Exception e) {
      }

    }

  }

}