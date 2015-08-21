package org.fundaciobit.genapp.common.web.controller;

import org.apache.log4j.Logger;
import org.fundaciobit.genapp.common.IGenAppEntity;
import org.fundaciobit.genapp.common.filesystem.IFile;
import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genapp.common.web.form.BaseForm;
import org.springframework.stereotype.Controller;


/**
 * 
 * @author anadal
 * 
 */
@Controller
public abstract class CommonFilesBaseController<I extends IGenAppEntity, PK extends Object, F extends BaseForm, E extends IFile> extends CommonBaseController<I,PK> {


  protected final Logger log = Logger.getLogger(getClass());

  /**
   * 
   * @return
   */
  protected abstract FilesFormManager<E> getFilesFormManager();


  public abstract void deleteFiles(I entity);
  

  public abstract void setFilesFormToEntity(FilesFormManager<E> afm,
      I entity, F form) throws I18NException;

}
