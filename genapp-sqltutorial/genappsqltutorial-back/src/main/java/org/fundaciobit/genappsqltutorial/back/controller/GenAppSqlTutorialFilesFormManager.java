package org.fundaciobit.genappsqltutorial.back.controller;

import org.fundaciobit.genappsqltutorial.persistence.FitxerJPA;
import org.fundaciobit.genappsqltutorial.model.entity.Fitxer;

import org.fundaciobit.genapp.common.filesystem.IFileManager;
import org.fundaciobit.genapp.common.web.controller.FilesFormManager;


/**
 * Gestiona Multiples Fitxers d'un Form
 * 
 * @author anadal
 * 
 */
public class GenAppSqlTutorialFilesFormManager extends FilesFormManager<Fitxer> {

  public GenAppSqlTutorialFilesFormManager(IFileManager<Fitxer> fitxerEjb) {
    super(fitxerEjb);
  }

  @Override
  public FitxerJPA createEmptyFile() {    
    return new FitxerJPA();
  }

}
