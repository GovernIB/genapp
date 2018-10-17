package org.fundaciobit.genapp.common.filesystem;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * 
 * @author anadal
 *
 */
public class ConverterSimpleToThreeFolder {

  
  public static void main(String[] args) {
    
    if (args.length != 1) {
      
      System.err.println("Necessita un argument que és la ruta la directori de fitxers");
      
      System.exit(-1);
      
    }
    
    
    File dir = new File(args[0]);
    
    if (!dir.exists()) {
      System.err.println("No existeix el directori " + dir.getAbsolutePath());
      
      System.exit(-1);
    }
    
    File[] files = dir.listFiles(new FileFilter() {
      
      @Override
      public boolean accept(File pathname) {
        
        if (pathname.isFile()) {
          try {
            Long.parseLong(pathname.getName());
            return true;
          } catch (Throwable e) {
          }
        }
        return false;
      }
    
    });
    
    for (File file : files) {
      Long id = Long.parseLong(file.getName());
      File destDir = getTreeFolder(dir, id);
      
      try {
        File destFile = new File(destDir, file.getName());
        copy(file, destFile);
        System.out.println("Copia de " + file.getAbsolutePath() + " a " + destFile);
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      
    }
    
  }
  
  
  public static File getTreeFolder(File filesPath, Long id) {
    id = Math.abs(id);
    File dir = new File(filesPath, ((id / 100) % 10) + "/" + ((id / 10) % 10) + "/"
        + (id % 10));
    dir.mkdirs();
    return dir;

  }
  
  
  public static void copy(File input, File output) throws Exception {
    try {
      FileInputStream fis = new FileInputStream(input);
      FileOutputStream fos = new FileOutputStream(output);
      copy(fis, fos);
      fis.close();
      fos.flush();
      fos.close();
    } catch (Exception e) {
      throw new Exception(e);
    }
  }

  public static void copy(InputStream input, OutputStream output) throws IOException {
    byte[] buffer = new byte[4096];
    int n = 0;
    while (-1 != (n = input.read(buffer))) {
      output.write(buffer, 0, n);
    }
  }
  
  
}
