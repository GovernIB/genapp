package org.fundaciobit.genapp.common.web.filesystem;

import org.fundaciobit.genapp.common.filesystem.FileSystemManager;
import org.fundaciobit.genapp.common.i18n.I18NArgumentString;
import org.fundaciobit.genapp.common.i18n.I18NException;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * Classe que Gestiona Fitxers rebuts via web.
 * 
 * @author anadal
 * 
 */
public class WebFileSystemManager extends FileSystemManager {
  
  public static File crearFitxerTmp(MultipartFile src, Long dstId) throws I18NException {
    File newFile = getTmpFile(dstId);
    try {
      src.transferTo(newFile);
    } catch (Exception e) {
      // TODO Traduir
      throw new I18NException(e, "error.unknown",
         new I18NArgumentString("Error transferint fitxer d'un objecte MultipartFile a " + dstId + " temporal"));
    }
    return newFile;
  }

  public static File crearFitxer(MultipartFile src, Long dstId) throws I18NException {
    File newFile = new File(WebFileSystemManager.getFilesPath(), String.valueOf(dstId));
    try {
      src.transferTo(newFile);
    } catch (Exception e) {
      // TODO Traduir
      throw new I18NException(e, "error.unknown",
         new I18NArgumentString("Error transferint fitxer d'un objecte MultipartFile a " + dstId));
    }
    return newFile;
  }

}
