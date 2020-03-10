package org.fundaciobit.genapp.common.filesystem;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.fundaciobit.genapp.common.i18n.I18NException;

/**
 * Distribuexi el fitxers en subdirectoris (concretament en tres subnivells). Per exemple el
 * fitxer amb ID 12345 es guardara a \5\4\3\12345
 * 
 * @author anadal
 *
 */
public class ThreeFolderFileSystemManager extends SimpleFileSystemManager implements
    IFileSystemManager {

  /**
   * 
   */
  public ThreeFolderFileSystemManager() {
    super();
  }

  public static File getTreeFolder(File filesPath, Long id) {
    id = Math.abs(id);
    File dir = new File(filesPath, ((id / 100) % 10) + "/" + ((id / 10) % 10) + "/"
        + (id % 10));
    dir.mkdirs();
    return dir;

  }

  @Override
  public File getFile(File filesPath, Long id) {
    return super.getFile(getTreeFolder(filesPath, id), id);
  }

  @Override
  public String getChecksum(File filesPath, Long id) throws Exception {

    return super.getChecksum(getTreeFolder(filesPath, id), id);
  }

  @Override
  public File crearFitxer(File filesPath, InputStream is, Long id) throws I18NException {

    return super.crearFitxer(getTreeFolder(filesPath, id), is, id);
  }

  @Override
  public File crearFitxer(File filesPath, File input, Long id) throws I18NException {
    return super.crearFitxer(getTreeFolder(filesPath, id), input, id);
  }

  @Override
  public File sobreescriureFitxer(File filesPath, File src, Long id) {

    return super.sobreescriureFitxer(getTreeFolder(filesPath, id), src, id);
  }

  @Override
  public boolean eliminarArxiu(File filesPath, Long id) {
    return super.eliminarArxiu(getTreeFolder(filesPath, id), id);
  }

  @Override
  public boolean eliminarArxius(File filesPath, Set<Long> fitxers) {

    boolean resultat = true;
    if (fitxers != null && !fitxers.isEmpty()) {
      for (Long fitxerID : fitxers) {
        resultat = resultat
            && super.eliminarArxiu(getTreeFolder(filesPath, fitxerID), fitxerID);
      }
    }
    return resultat;

  }

  @Override
  public File getTmpFile(File filesPath, Long dstId) {
    // Crearem el temporal en l'arrel
    return super.getTmpFile(filesPath, dstId);
  }

  @Override
  public byte[] getFileContent(File filesPath, long id) throws FileNotFoundException,
      IOException {
    return FileSystemManager.readFileToByteArray(getFile(filesPath, id)); 
  }
  
  @Override
  public Map<Long, File> getAllFiles(File filesPath) {
    File[] rootDirs1 = filesPath.listFiles(new FileFilter() {

      @Override
      public boolean accept(File pathname) {

        if (pathname.isDirectory()) {
          String name = pathname.getName();
          if (name.length() == 1 && Character.isDigit(name.charAt(0))) {
            return true;
          }
        }

        return false;
      }

    });

    Map<Long, File> map = new HashMap<Long, File>();

    for (File rootDir1 : rootDirs1) {
      
      File[] rootDirs2 = rootDir1.listFiles();

      for (File rootDir2 : rootDirs2) {
        
        File[] rootDirs3 = rootDir2.listFiles();

        for (File rootDir3 : rootDirs3) {

          File[] files = rootDir3.listFiles();
          
          for (File file : files) {
            Long id;
            try {
              id = Long.parseLong(file.getName());
  
              map.put(id, file);
  
            } catch (Throwable th) {
              th.printStackTrace();
            }
          }
        }
      }
    }
    
    return map;

  }
  

  public static void main(String[] args) {
    
    
    File f = new File("C:\\tmp\\sql.sql");
    
    File dest = new File("D:\\sql.sql");
    
    System.out.println("Rename: " + f.renameTo(dest));
    
    /*
    File filesPath = new File("D:\\dades\\dades\\CarpetesPersonals\\ProgramacioPortaFIB2\\portafib-files\\postgresql_2.0_three");

    ThreeFolderFileSystemManager tf = new ThreeFolderFileSystemManager();

//    Long[] ids = new Long[] { 1L, 25L, 123L, 56785345L, -2435666L };
//
//    for (Long id : ids) {
//      System.out.println("Path for " + id + "\t= " + ThreeFolderFileSystemManager.getTreeFolder(filesPath, id));
//    }
    
    Map<Long, File> fitxers = tf.getAllFiles(filesPath);
    
    for (Map.Entry<Long, File> fFisic : fitxers.entrySet()) {
      System.out.println(fFisic.getKey() +  "=>\t" + fFisic.getValue().getAbsolutePath());
      
    }
    
    
    //D:\dades\dades\CarpetesPersonals\ProgramacioPortaFIB2\portafib-files\postgresql_2.0_three
    
*/
  }

}
