package org.fundaciobit.demogenapp.back.controller;

import org.fundaciobit.demogenapp.persistence.FitxerJPA;
import org.fundaciobit.demogenapp.model.entity.Fitxer;

import org.fundaciobit.genapp.common.filesystem.IFileManager;
import org.fundaciobit.genapp.common.web.controller.FilesFormManager;


/**
 * Gestiona Multiples Fitxers d'un Form
 * 
 * @author anadal
 * 
 */
public class DemoGenAppFilesFormManager extends FilesFormManager<Fitxer> {

  public DemoGenAppFilesFormManager(IFileManager<Fitxer> fitxerEjb) {
    super(fitxerEjb);
  }

  @Override
  public FitxerJPA createEmptyFile() {    
    return new FitxerJPA();
  }

}
