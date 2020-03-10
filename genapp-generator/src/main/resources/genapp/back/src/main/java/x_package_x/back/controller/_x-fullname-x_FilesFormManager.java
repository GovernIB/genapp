package ${package}.back.controller;

import ${package}.jpa.${fitxer}JPA;
import ${package}.model.entity.${fitxer};

import org.fundaciobit.genapp.common.filesystem.IFileManager;
import org.fundaciobit.genapp.common.web.controller.FilesFormManager;


/**
 * Gestiona Multiples Fitxers d'un Form
 * 
 * @author anadal
 * 
 */
public class ${fullname}FilesFormManager extends FilesFormManager<${fitxer}> {

  public ${fullname}FilesFormManager(IFileManager<${fitxer}> fitxerEjb) {
    super(fitxerEjb);
  }

  @Override
  public ${fitxer}JPA createEmptyFile() {    
    return new ${fitxer}JPA();
  }

}
