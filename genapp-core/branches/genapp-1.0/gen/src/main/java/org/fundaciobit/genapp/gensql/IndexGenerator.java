package org.fundaciobit.genapp.gensql;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * A partir d'un fitxer indices.sql amb els indexos amb el següent format 
 * 
 * ----------------------------------------------
 * 
 * -- Tabla DIR_HISTORICOUO
 *
 * CREATE INDEX DIR_UNI_UNI_HISTANTE_FK_I
 * ON DIR_HISTORICOUO(CODANTERIOR);
 * 
 * CREATE INDEX DIR_UNI_UNI_HISTULTI_FK_I
 * ON DIR_HISTORICOUO(CODULTIMA);
 * ----------------------------------------------
 * 
 * genera les anotacions per les classes java. No suporta indexos de @JoinTable !!!!!
 * 
 * @author anadal
 *
 */
public class IndexGenerator {


  public static void main(String[] args) {
    BufferedReader br = null;
    try {

      br = new BufferedReader(new FileReader("indices.sql"));

      String lastTable = null;
      String lastIndex = null;

      StringBuffer indexes = new StringBuffer();

      for (String l; (l = br.readLine()) != null;) {
        String line = l.trim();

        if (line.length() == 0) {
          continue;
        }

        if (line.startsWith("-- Tabla")) {

          int i = line.lastIndexOf(' ');

          if (lastTable != null) {

            System.out.println("@org.hibernate.annotations.Table(appliesTo = \"" + lastTable
                + "\", indexes = {");
            System.out.println(indexes.toString());
            System.out.println("})");
            System.out.println();

            indexes.setLength(0);
          }

          lastTable = line.substring(i + 1, line.length());

          // System.out.println(" NOU TABLE = ]" + lastTable + "[");

        } else {

          if (line.startsWith("CREATE INDEX")) {

            int i = line.lastIndexOf(' ');

            lastIndex = line.substring(i + 1, line.length());

            // System.out.println(" DARRER INDEX = ]" + lastIndex + "[");

          } else {
            if (line.startsWith("ON ")) {
              // ON DIR_OFICINA(CODPROVINCIA, CODENTGEOGRAFICA, CODLOCALIDAD);
              int i = line.lastIndexOf('(');
              int i2 = line.lastIndexOf(')');
              //int i3 = line.indexOf(' ');

              //String table = line.substring(i3 + 1, i - 1).trim();
              String[] columns = line.substring(i + 1, i2).split(",");

              if (indexes.length() != 0) {

                indexes.append(",\n");
              }

              indexes.append("    @Index(name=\"" + lastIndex + "\", columnNames = {");
              for (int j = 0; j < columns.length; j++) {
                if (j != 0) {
                  indexes.append(", ");
                }
                indexes.append("\"" + columns[j].trim() + "\"");
              }

              indexes.append("})");
              // @org.hibernate.annotations.Table(appliesTo = "XXXX", indexes =
              // {
              // @Index(name="FK_I", columnNames = {"COL1", "COL2" })
              // })

            } else {
              System.err.println(" LINIA no entesa = ]" + line + "[");
              br.close();
              throw new Exception();
            }
          }

        }

      }

      System.out.println("@org.hibernate.annotations.Table(appliesTo = \"" + lastTable
          + "\", indexes = {");
      System.out.println(indexes.toString());
      System.out.println("})");
      System.out.println();
      
      

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (br != null) { 
          br.close();
        }
      } catch (Exception e2) {
        // TODO: handle exception
      }
      
    }

  }
  
}
