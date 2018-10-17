package org.fundaciobit.genapp.common.filesystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import org.apache.log4j.Logger;
import org.fundaciobit.genapp.common.i18n.I18NArgumentString;
import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genapp.common.utils.Utils;

/**
 * 
 * @author anadal
 *
 */
public class SimpleFileSystemManager implements IFileSystemManager {

  protected final Logger log = Logger.getLogger(this.getClass());

  @Override
  public File getFile(File filesPath, Long id) {
    File newFile = new File(filesPath, String.valueOf(id));
    return newFile;
  }

  @Override
  public String getChecksum(File filesPath, Long id) throws Exception {
    FileInputStream fis = null;
    try {
      fis = new FileInputStream(new File(filesPath, String.valueOf(id)));
      return Utils.getChecksum(fis);
    } finally {
      try {
        if (fis != null) {
          fis.close();
        }
      } catch (Throwable e) {
        log.error("Error tancant InputStream: " + e.getMessage(), e);
      }
    }
  }

  @Override
  public File crearFitxer(File filesPath, InputStream is, Long dstId) throws I18NException {
    File newFile = new File(filesPath, String.valueOf(dstId));
    FileOutputStream fos = null;
    try {
      fos = new FileOutputStream(newFile, false);
      FileSystemManager.copy(is, fos);
      fos.flush();
    } catch (Exception e) {
      throw new I18NException(e, "error.createfile", new I18NArgumentString(
          String.valueOf(dstId)));
    } finally {
      try {
        fos.close();
      } catch (Throwable e) {
      }
    }
    return newFile;
  }

  @Override
  public File crearFitxer(File filesPath, File input, Long dstId) throws I18NException {
    File newFile = new File(filesPath, String.valueOf(dstId));
    FileSystemManager.copy(input, newFile);
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
  @Override
  public File sobreescriureFitxer(File filesPath, File src, Long dstId) {
    File newFile = new File(filesPath, String.valueOf(dstId));
    if (newFile.exists()) {
      if (!newFile.delete()) {
        log.warn("sobreescriureFitxer:: El fitxer desti existeix i no s'ha pogut esborrar: "
            + newFile.getAbsolutePath());
      }
    }
    // TODO Substituir per FileUtils.moveFile()
    if (!src.renameTo(newFile)) {
      log.warn("sobreescriureFitxer:: No s'ha renombrat el fitxer de ["
          + src.getAbsolutePath() + "] a [" + newFile.getAbsolutePath() + "]");
      FileSystemManager.printMiniStackTrace();
    }
    return newFile;
  }

  /**
   * 
   * @param id
   * @return true si l'arxiu no existeix o s'ha borrat. false en els altres casos.
   */
  @Override
  public boolean eliminarArxiu(File filesPath, Long id) {
    File file = new File(filesPath, String.valueOf(id));
    if (file.exists()) {
      if (!file.delete()) {
        file.deleteOnExit();
        log.warn("Per algun motiu desconegut no s'ha pogut esborrar l'arxiu "
            + file.getAbsolutePath());
        FileSystemManager.printMiniStackTrace();
        return false;
      }
    } else {
      log.warn("El fitxer " + file.getAbsolutePath() + " no existeix");
    }
    return true;
  }

  /**
   * 
   * @param fitxers
   * @return true si tots els arxius s'han borrat. false si algun fitxer no s'ha pogut borrar
   */
  @Override
  public boolean eliminarArxius(File filesPath, Set<Long> fitxers) {
    boolean resultat = true;
    if (fitxers != null && !fitxers.isEmpty()) {
      for (Long fitxerID : fitxers) {
        resultat = resultat && eliminarArxiu(filesPath, fitxerID);
      }
    }
    return resultat;
  }

  @Override
  public File getTmpFile(File filesPath, Long dstId) {
    return new File(filesPath, String.valueOf(dstId) + ".tmp");
  }

  @Override
  public byte[] getFileContent(File filesPath, long fitxerID) throws FileNotFoundException,
      IOException {

    return FileSystemManager.readFileToByteArray(getFile(filesPath, fitxerID));

  }

}
