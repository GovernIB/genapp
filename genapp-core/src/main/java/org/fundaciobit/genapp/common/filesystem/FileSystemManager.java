package org.fundaciobit.genapp.common.filesystem;

import org.apache.log4j.Logger;
import org.fundaciobit.genapp.common.i18n.I18NArgumentString;
import org.fundaciobit.genapp.common.i18n.I18NException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
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

  public static IFileSystemManager fileSystemManager = new SimpleFileSystemManager();

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
    return fileSystemManager.getFile(getFilesPath(), id);
  }
  

  public static IFileSystemManager getFileSystemManager() {
    return fileSystemManager;
  }

  public static void setFileSystemManager(IFileSystemManager fileSystemManager) {
    FileSystemManager.fileSystemManager = fileSystemManager;
  }

  public static String getChecksum(Long id) throws Exception {
    return fileSystemManager.getChecksum(getFilesPath(), id);
  }

  public static File crearFitxer(InputStream is, Long dstId) throws I18NException {

    return fileSystemManager.crearFitxer(getFilesPath(), is, dstId);
  }

  public static File crearFitxer(File input, Long dstId) throws I18NException {

    return fileSystemManager.crearFitxer(getFilesPath(), input, dstId);
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

    return fileSystemManager.sobreescriureFitxer(getFilesPath(), src, dstId);
  }

  /**
   * 
   * @param id
   * @return true si l'arxiu no existeix o s'ha borrat. false en els altres casos.
   */
  public static boolean eliminarArxiu(Long id) {

    return fileSystemManager.eliminarArxiu(getFilesPath(), id);
  }

  /**
   * 
   * @param fitxers
   * @return true si tots els arxius s'han borrat. false si algun fitxer no s'ha pogut borrar
   */
  public static boolean eliminarArxius(Set<Long> fitxers) {
    return fileSystemManager.eliminarArxius(getFilesPath(), fitxers);
  }

  public static File getTmpFile(Long dstId) {
    return fileSystemManager.getTmpFile(getFilesPath(), dstId);
  }

  public static byte[] getFileContent(long fitxerID) throws FileNotFoundException, IOException {
    return fileSystemManager.getFileContent(getFilesPath(), fitxerID);
  }
  
  public static Map<Long,File> getAllFiles() {
    return fileSystemManager.getAllFiles(getFilesPath());
  }
  

  // ------------------------------------------------------------------
  // UTILITATS
  // -------------------------------------------------------------------

  public static void printMiniStackTrace() {
    int count = 8;
    for (StackTraceElement stack : new Exception().getStackTrace()) {
      log.warn(stack.toString());
      if (0 == count--) {
        break;
      }
    }
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

  public static void copy(File input, File output) throws I18NException {
    try {
      FileInputStream fis = new FileInputStream(input);
      FileOutputStream fos = new FileOutputStream(output);
      copy(fis, fos);
      fis.close();
      fos.flush();
      fos.close();
    } catch (Exception e) {
      throw new I18NException(e, "error.copyfile", new I18NArgumentString(
          String.valueOf(input == null ? "null" : input.getAbsolutePath())),
          new I18NArgumentString(String.valueOf(output == null ? "null" : output
              .getAbsolutePath())));
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
