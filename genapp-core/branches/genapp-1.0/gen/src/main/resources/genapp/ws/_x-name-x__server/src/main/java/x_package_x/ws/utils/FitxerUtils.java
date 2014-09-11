package ${package}.ws.utils;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fundaciobit.genapp.common.filesystem.FileSystemManager;
import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genapp.common.query.Field;

import ${package}.jpa.${fitxer}JPA;
import ${package}.model.bean.${fitxer}Bean;
import ${package}.model.entity.${fitxer};
import ${package}.ejb.${fitxer}Local;


/**
 * 
 * @author anadal
 *
 */
public class FitxerUtils {

  protected static final Log log = LogFactory.getLog(FitxerUtils.class);
  
  
  public static void cleanPostError(${fitxer}Local fitxerEjb, Set<Long> fitxersCreats) {
    if (fitxersCreats == null) {
      return;
    }
    
    for (Long fileID : fitxersCreats) {
      try {
        fitxerEjb.delete(fileID);
      } catch (Throwable e) {
        // TODO Enviar mail a ADMINISTRADOR
        log.error("Error borrant fitxer després d'un error: " + e.getMessage(), e);
      }
      FileSystemManager.eliminarArxiu(fileID);
    }
    
  }
  
  
  // TODO throw I18NException
  public static ${fitxer}JPA createFitxer(FitxerBean fitxer,
      ${fitxer}Local fitxerEjb, Set<Long> fitxersCreats,
      Field<?> field) throws I18NException {
    
    if (fitxer == null) {
      return null;
    }
    
    
    File tmp;
    try {
      if (fitxer.getData() == null) {
        // TODO Translate
        throw new IOException();
      }

      tmp = File.createTempFile("ws_", ".tmp", FileSystemManager.getFilesPath());
      
      FileUtils.copyInputStreamToFile(fitxer.getData().getInputStream(), tmp);

    } catch(IOException ioe) {
      throw new I18NException("fitxer.sensedades", field.fullName);
    }

    
    log.info(" TAMANY DE FITXER REBUT = "+ tmp.length() );
    
    fitxer.setTamany(tmp.length());
    

    ${fitxer}JPA fitxerJPA = ${fitxer}JPA.toJPA(fitxer);

    // TODO Arreglar aquest
    fitxerJPA = (${fitxer}JPA)fitxerEjb.create(fitxerJPA);
    
    long fitxerID = fitxerJPA.getFitxerID();
    
    log.info("ID FITXER CREAT = "+ fitxerID );
    
    //FileSystemManager.crearFitxer(tmp, fitxerID);
    FileSystemManager.sobreescriureFitxer(tmp, fitxerID);
    
    fitxersCreats.add(fitxerJPA.getFitxerID());

    return fitxerJPA;

  }
 
  
}
