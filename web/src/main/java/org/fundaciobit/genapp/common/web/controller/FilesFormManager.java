package org.fundaciobit.genapp.common.web.controller;

import org.apache.log4j.Logger;
import org.fundaciobit.genapp.common.filesystem.IFile;
import org.fundaciobit.genapp.common.filesystem.IFileManager;
import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genapp.common.utils.Utils;
import org.fundaciobit.genapp.common.web.filesystem.WebFileSystemManager;
import org.fundaciobit.genapp.common.web.i18n.I18NUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Gestiona Multiples Fitxers d'un Form
 * 
 * @author anadal
 * 
 */
public abstract class FilesFormManager <F extends IFile> {

  protected final Logger log = Logger.getLogger(getClass());
  
  protected IFileManager<F, Long> fitxerEjb; 

  public FilesFormManager(IFileManager<F, Long> fitxerEjb) {
    this.fitxerEjb = fitxerEjb;
  }
  

  List<FileFormManager> arxius = new ArrayList<FileFormManager>();

  FileFormManager lastFileFormManager = null;
  
  public abstract F createEmptyFile();

  public FileFormManager getLastFileFormManager() {
    return lastFileFormManager;
  }

  public F preProcessFile(MultipartFile uploadedFile, boolean borrar,
      F currentFile) throws I18NException {

    FileFormManager gaf;
    gaf = new FileFormManager(uploadedFile, borrar, currentFile);

    try {
      return gaf.prePersist();
    } finally {
      arxius.add(gaf);
      lastFileFormManager = gaf;
    }

  }

  public void postPersistFiles() throws Exception {

    for (FileFormManager gaf : arxius) {
      gaf.postPersist();
    }
  }

  public void processErrorFilesWithoutThrowException() {

    for (FileFormManager gaf : arxius) {
      gaf.processErrorWithoutThrowException();
    }
  }

  public void processErrorFiles() throws I18NException {

    ContainerException ce = new ContainerException();
    for (FileFormManager gaf : arxius) {
      try {
        gaf.processError();
      } catch (I18NException e) {
        ce.add(e);
      }
    }
    if (ce.getExceptions().size() != 0) {
      throw new I18NException("error.unknown", ce.getMessage());
    }
  }

  
  

  public enum EstatPujadaArxiu {
    VAL_NULL_I_QUEDA_NULL, EXISTEIX_IMATGE_I_ES_BORRA, EXISTEIX_IMATGE_I_ES_DEIXA_COM_ESTA, VAL_NULL_I_ES_PUJA_NOU_ARXIU, EXISTEIX_IMATGE_I_S_ACTUALITZA
  }

  /**
   * Gestiona un Fitxer d'un Form
   * 
   * @author anadal
   * 
   */
  public class FileFormManager  {

    private final MultipartFile arxiuPujat;

    private F arxiuActual;

    private F perBorrar = null;

    private File perMoure = null;

    private boolean borrar;

    private EstatPujadaArxiu estatPujadaArxiu = null;

    public FileFormManager(MultipartFile uploadedFile, boolean borrar, F arxiuActual) {
      this.arxiuPujat = uploadedFile;
      this.arxiuActual = arxiuActual;
      this.borrar = borrar;
    }
    
    
    
    

    /**
     * Abans de la persistència
     * 
     * @return
     */
    public F prePersist() throws I18NException {

      // Imatge no s'ha pujat
      if (arxiuPujat == null || arxiuPujat.isEmpty()) {
        if (arxiuActual == null) {
          // CAS 1: Val null i es queda null
          log.debug("CAS 1: Val null i es queda null");
          // No fer res
          this.estatPujadaArxiu = EstatPujadaArxiu.VAL_NULL_I_QUEDA_NULL;
          return arxiuActual;
        } else {
          log.debug("El flag borrar val = " + this.borrar);
          if (this.borrar) {
            // CAS 2: Existeix imatge i es borra
            log.debug("CAS 2: Existeix imatge i es borra");
            perBorrar = arxiuActual;
            this.estatPujadaArxiu = EstatPujadaArxiu.EXISTEIX_IMATGE_I_ES_BORRA;
            return null;
          } else {
            // CAS 3: Existeix imatge i es deixa com esta
            log.debug("CAS 3: Existeix imatge i es deixa com esta");
            // No fer res
            this.estatPujadaArxiu = EstatPujadaArxiu.EXISTEIX_IMATGE_I_ES_DEIXA_COM_ESTA;
            return arxiuActual;
          }
        }
      } else {
        // Guardar Fitxer

        if (arxiuActual == null) {
          // CAS 4: Val null i es puja una nova
          log.debug("CAS 4: Val null i es puja una nova");
          arxiuActual = createEmptyFile();;

          log.debug(" Nom arxiu = " + arxiuPujat.getOriginalFilename());

          // Valors temporals
          arxiuActual.setNom(arxiuPujat.getOriginalFilename());
          arxiuActual.setMime("application/octet-stream");
          arxiuActual.setTamany(-1);
          arxiuActual.setMime(Utils.getMimeType(arxiuPujat.getOriginalFilename()));

          arxiuActual = fitxerEjb.create(arxiuActual);
          File file = WebFileSystemManager.crearFitxer(arxiuPujat, arxiuActual.getFitxerID());
          
          
          arxiuActual.setTamany(file.length());
          arxiuActual = fitxerEjb.update(arxiuActual);

          this.estatPujadaArxiu = EstatPujadaArxiu.VAL_NULL_I_ES_PUJA_NOU_ARXIU;
          return arxiuActual;
        } else {
          // CAS 5: Existeix imatge i s'actualitza
          log.debug("CAS 5: Existeix imatge i s'actualitza");
          perMoure = WebFileSystemManager.crearFitxerTmp(arxiuPujat, arxiuActual.getFitxerID());

          arxiuActual.setNom(arxiuPujat.getOriginalFilename());
          arxiuActual.setMime(Utils.getMimeType(arxiuPujat.getOriginalFilename()));
          arxiuActual.setTamany(perMoure.length());

          this.estatPujadaArxiu = EstatPujadaArxiu.EXISTEIX_IMATGE_I_S_ACTUALITZA;
          return arxiuActual;
        }
      }

    }

    /*
    private String getMimeType(MultipartFile mpf) {
      String mime = mpf.getContentType();
      if (mime == null) {
        mime = URLConnection.guessContentTypeFromName(mpf.getName());
        return mime == null ? "application/octet-stream" : mime;
      } else {
        return mime;
      }
    }
    */

    public void processErrorWithoutThrowException() {
      try {
        processError();
      } catch (I18NException e) {
        String msg = I18NUtils.getMessage(e);
        if (this.arxiuActual == null) {
          log.error("Error desconegut cridant a onError: " + msg, e);
        } else {
          log.error("Error cridant a onError de la imatge " + this.arxiuActual.getFitxerID()
              + ": " + msg, e);
        }
      }
    }

    public void processError() throws I18NException {
      if (this.estatPujadaArxiu == null) {
        // TODO traduir
        throw new I18NException("Abans de cridar al mètode onError()"
            + " s'ha d'haver cridat al mètode prePersist()");
      }
      switch (this.estatPujadaArxiu) {
      case VAL_NULL_I_QUEDA_NULL:
        // No fer res
        break;
      case EXISTEIX_IMATGE_I_ES_BORRA:
        // No fer res
        perBorrar = null; // Per si despres sense voler s'executa postPersist()
        break;
      case EXISTEIX_IMATGE_I_ES_DEIXA_COM_ESTA:
        // No fer res
        break;
      case VAL_NULL_I_ES_PUJA_NOU_ARXIU:
        // Hem de borrar el nou arxiu de BBDD i de Sistema de fitxers
        try {
          fitxerEjb.delete(this.arxiuActual);
          WebFileSystemManager.eliminarArxiu(this.arxiuActual.getFitxerID());
        } catch (Exception e) {
          log.error("No s'ha pogut borrar la imatge " + this.arxiuActual.getNom() + "("
              + this.arxiuActual.getFitxerID() + ") de la BBDD:" + e.getMessage(), e);
        }
        break;
      case EXISTEIX_IMATGE_I_S_ACTUALITZA:
        // La imatge temporal guardada en fitxer s'ha de borrar
        if (!perMoure.delete()) {
          perMoure.deleteOnExit();
        }
        break;
      }
    }

    public void postPersist() throws Exception {
      if (this.estatPujadaArxiu == null) {
        throw new Exception("Abans de cridar al mètode postPersist()"
            + " s'ha d'haver cridat al mètode prePersist()");
      }

      switch (this.estatPujadaArxiu) {
      case VAL_NULL_I_QUEDA_NULL:
        // No fer res
        break;
      case EXISTEIX_IMATGE_I_ES_BORRA:
        // S'ha de borrar la imatge de la BBDD i del sistema de fitxers
        if (perBorrar != null) {
          log.debug("POST_PERSIST: Borrant arxiu " + perBorrar.getFitxerID());
          fitxerEjb.delete(perBorrar);
          if (!WebFileSystemManager.eliminarArxiu(perBorrar.getFitxerID())) {
            log.error("No s'ha pogut eliminar el fitxer amb ID=" + perBorrar.getFitxerID()
                + " del sistema de fitxers.", new Exception());
          }
        }
        break;
      case EXISTEIX_IMATGE_I_ES_DEIXA_COM_ESTA:
        // No fer res
        break;
      case VAL_NULL_I_ES_PUJA_NOU_ARXIU:
        // No fer res
        break;
      case EXISTEIX_IMATGE_I_S_ACTUALITZA:
        if (perMoure != null) {
          log.debug("POST_PERSIST: Moure arxiu " + perMoure.getAbsolutePath() + " ==> "
              + arxiuActual.getFitxerID());
          // S'ha d'actualitzar les dades de l'arxiu (nom, tamany, mime, ..)
          try {
            fitxerEjb.update(arxiuActual);
          } catch (I18NException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
          // S'actualitza el fitxer fisicament: Mou el fitxer
          WebFileSystemManager.sobreescriureFitxer(perMoure, arxiuActual.getFitxerID());
        }
        break;
      }
    }

    public EstatPujadaArxiu getEstatPujadaArxiu() {
      return estatPujadaArxiu;
    }

    public MultipartFile getArxiuPujat() {
      return arxiuPujat;
    }

    public File getPerMoure() {
      return perMoure;
    }

    public F getArxiuActual() {
      return arxiuActual;
    }

  }

}
