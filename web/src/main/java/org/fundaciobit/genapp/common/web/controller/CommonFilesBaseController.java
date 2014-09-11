package org.fundaciobit.genapp.common.web.controller;

import org.apache.log4j.Logger;
import org.fundaciobit.genapp.common.filesystem.IFile;
import org.fundaciobit.genapp.common.i18n.I18NException;
import org.springframework.stereotype.Controller;


/**
 * 
 * @author anadal
 * 
 */
// TODO MOURE A GENAPP
@Controller
public abstract class CommonFilesBaseController<P, F, E extends IFile> extends CommonBaseController {


  protected final Logger log = Logger.getLogger(getClass());

  /**
   * 
   * @return
   */
  protected abstract FilesFormManager<E> getFilesFormManager();


  public abstract void deleteFiles(P entity);
  

  public abstract void setFilesFormToEntity(FilesFormManager<E> afm,
      P entity, F form) throws I18NException;

}
