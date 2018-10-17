package org.fundaciobit.genapp.common.filesystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import org.fundaciobit.genapp.common.i18n.I18NException;

/**
 * 
 * @author anadal
 *
 */
public interface IFileSystemManager {

  public File getFile(File filesPath, Long id);

  public String getChecksum(File filesPath, Long id) throws Exception;

  public File crearFitxer(File filesPath, InputStream is, Long dstId) throws I18NException;

  public File crearFitxer(File filesPath, File input, Long dstId) throws I18NException;

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
  public File sobreescriureFitxer(File filesPath, File src, Long dstId);

  /**
   * 
   * @param id
   * @return true si l'arxiu no existeix o s'ha borrat. false en els altres casos.
   */
  public boolean eliminarArxiu(File filesPath, Long id);

  /**
   * 
   * @param fitxers
   * @return true si tots els arxius s'han borrat. false si algun fitxer no s'ha pogut borrar
   */
  public boolean eliminarArxius(File filesPath, Set<Long> fitxers);

  public File getTmpFile(File filesPath, Long dstId);

  public byte[] getFileContent(File filesPath, long fitxerID) throws FileNotFoundException, IOException;

}
