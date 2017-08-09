package org.fundaciobit.genapp.common.filesystem;

import org.apache.log4j.Logger;
import org.fundaciobit.genapp.common.i18n.I18NArgumentString;
import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genapp.common.utils.Utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

/**
 * Classe que Gestiona un repositori de Fitxers
 * 
 * @author anadal
 * 
 */
public class FileSystemManager {

  protected static final Logger log = Logger.getLogger(FileSystemManager.class);
  
  
  public static File filesPath = null;
  
  public static File getFilesPath() {
    if (filesPath == null) {
      String path = System.getProperty("FileSystemManager.Path");
      if (path == null) {
        path = System.getProperty("user.home");
        File f = new File(path, ".FileSystemManager");
        if (!f.mkdirs()) {
          filesPath = new File(System.getProperty("java.io.tmpdir"), ".FileSystemManager");
          filesPath.mkdirs();
        } else {
          filesPath = f;
        }
      } else {
        filesPath = new File(path);
      }
    }    
    return filesPath;
  }
  

  public static void setFilesPath(File newPath) {
    if (newPath != null) {
      filesPath = newPath;
    }
  }
  

  public static File getFile(Long id) {
    File newFile = new File(getFilesPath(), String.valueOf(id));
    return newFile;
  }

  
  public static String getChecksum(Long id) throws Exception {
    return Utils.getChecksum(new FileInputStream(new File(getFilesPath(), String.valueOf(id))));
  }

  public static File crearFitxer(InputStream is, Long dstId) throws I18NException {
    File newFile = new File(getFilesPath(), String.valueOf(dstId));
    FileOutputStream fos = null;
    try {
      fos = new FileOutputStream(newFile, false); 
      copy(is, fos );
      fos.flush();
    } catch(Exception e) {
      throw new I18NException(e, "error.createfile",
          new I18NArgumentString(String.valueOf(dstId)));
    } finally {
      try { fos.close(); } catch (Throwable e) { }
    }
    return newFile;
  }
  
  public static File crearFitxer(File input, Long dstId) throws I18NException {
    File newFile = new File(getFilesPath(), String.valueOf(dstId));
    copy(input, newFile);
    return newFile;
  }



  /**
   * Mou el fitxer 'src' a un fitxer amb ID 'dstID'
   * 
   * @param src
   *          Fitxer origen
   * @param dstId
   *          ID del fitxer destí
   * @return Nou fitxer creat
   * @throws Exception
   */
  public static File sobreescriureFitxer(File src, Long dstId) {
    File newFile = new File(getFilesPath(), String.valueOf(dstId));
    if (newFile.exists()) {
      if (!newFile.delete()) {
        log.warn("sobreescriureFitxer:: El fitxer desti existeix i no s'ha pogut borrar: " 
            + newFile.getAbsolutePath());
      }
    }
    // TODO Substituir per FileUtils.moveFile()
    if (!src.renameTo(newFile)) {
      log.warn("sobreescriureFitxer:: No s'ha renombrat el fitxer de [" + src.getAbsolutePath()
          + "] a [" + newFile.getAbsolutePath() + "]");
      printMiniStackTrace();
    }
    return newFile;
  }



  public static void copy(File input, File output) throws I18NException {
    try {
      FileInputStream fis = new FileInputStream(input);
      FileOutputStream fos = new FileOutputStream(output); 
      copy(fis, fos );
      fis.close();
      fos.flush();
      fos.close();
    } catch(Exception e) {
      throw new I18NException(e, "error.copyfile", 
         new I18NArgumentString(String.valueOf(input == null? "null" : input.getAbsolutePath())),
         new I18NArgumentString(String.valueOf(output == null? "null" : output.getAbsolutePath())));
    }
  }

  public static void copy(InputStream input, OutputStream output)
      throws IOException {
    byte[] buffer = new byte[4096];
    int n = 0;
    while (-1 != (n = input.read(buffer))) {
      output.write(buffer, 0, n);
    }
  }

  /**
   * 
   * @param id
   * @return true si l'arxiu no existeix o s'ha borrat. false en els altres
   *         casos.
   */
  public static boolean eliminarArxiu(Long id) {
    File file = new File(getFilesPath(), String.valueOf(id));
    if (file.exists()) {
      if (!file.delete()) {
        file.deleteOnExit();
        log.warn("Per algun motiu desconegut no s'ha pogut esborrar l'arxiu "
            + file.getAbsolutePath());
        printMiniStackTrace();
        return false;
      }
    } else {
      log.warn("El fitxer " + file.getAbsolutePath() + " no existeix");
    }
    return true;
  }


  private static void printMiniStackTrace() {
    int count = 8;
    for (StackTraceElement stack : new Exception().getStackTrace()) {
      log.warn(stack.toString());
      if (0 == count--) {
        break;
      }
    }
  }
  
  
  /**
   * 
   * @param fitxers
   * @return true si tots els arxius s'han borrat.
   *          false si algun fitxer no s'ha pogut borrar
   */
  public static boolean eliminarArxius(Set<Long> fitxers) {
    boolean resultat = true;
    if (fitxers != null && !fitxers.isEmpty()) {
      for (Long fitxerID : fitxers) {
        resultat = resultat && eliminarArxiu(fitxerID);
      }
    }
    return resultat;
  }
  

  
  public static File getTmpFile(Long dstId) {
    return new File(FileSystemManager.getFilesPath(), String.valueOf(dstId) + ".tmp");
  }
  
  public static byte[] getFileContent(long fitxerID) throws FileNotFoundException, IOException {
    
    /** Deixa bloquejat el fitxer
    byte[] bytes;
    File file = getFile(fitxerID);
    FileInputStream fin = null;
    FileChannel ch = null;
    
    try {
        fin = new FileInputStream(file);
        ch = fin.getChannel();
        int size = (int) ch.size();
        MappedByteBuffer buf = ch.map(MapMode.READ_ONLY, 0, size);
        bytes = new byte[size];
        buf.get(bytes);

    } finally {
        try {
            if (ch != null) {
              ch.close();
            }
            if (fin != null) {
                fin.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    return bytes;
    */
    
    return readFileToByteArray(getFile(fitxerID));
    
  }
  
  
  public static byte[] readFileToByteArray(File file) throws IOException {
     InputStream input = null;
     try {
         input = new FileInputStream(file);
         ByteArrayOutputStream output = new ByteArrayOutputStream();
         copy(input, output);
         return output.toByteArray();
     } finally {
        if (input != null) {
          try {
            input.close();
          } catch (IOException ioe) {
            // ignore
          }
        }                  
     }
  }
  


}
