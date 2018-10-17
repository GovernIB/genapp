package org.fundaciobit.genapp.common.filesystem;

import java.io.File;
import java.io.FileFilter;

import org.fundaciobit.genapp.common.i18n.I18NException;

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
        FileSystemManager.copy(file, destFile);
        System.out.println("Copia de " + file.getAbsolutePath() + " a " + destFile);
      } catch (I18NException e) {
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
  
  
}
