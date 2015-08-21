package ${package}.back.controller;

import ${package}.ejb.${fitxer}Local;
import ${package}.model.entity.${fitxer};

import org.apache.log4j.Logger;
import org.fundaciobit.genapp.common.IGenAppEntity;
import org.fundaciobit.genapp.common.filesystem.FileSystemManager;
import org.fundaciobit.genapp.common.web.controller.CommonFilesBaseController;
import org.fundaciobit.genapp.common.web.controller.FilesFormManager;
import org.fundaciobit.genapp.common.web.form.BaseForm;
import org.springframework.stereotype.Controller;

import javax.ejb.EJB;

/**
 * POT SOBRESCRIURE AQUESTA CLASSE
 * @author anadal
 * 
 */
@Controller
public abstract class ${fullname}FilesBaseController<I extends IGenAppEntity, PK extends Object, F extends BaseForm>
  extends CommonFilesBaseController<I, PK, F, ${fitxer}>
  

  @EJB(mappedName = "${name}/${fitxer}EJB/local")
  protected ${fitxer}Local fitxerEjb;

  protected final Logger log = Logger.getLogger(getClass());

  /**
   * 
   * @return
   */
  protected FilesFormManager<${fitxer}> getFilesFormManager() {
    return new ${fullname}FilesFormManager(fitxerEjb);
  }

  /**
   * 
   * @param arxiu
   * @return
   */
  public boolean deleteFile(Long fileID) {
    if (fileID != null) {
      ${fitxer} file = null;
      try {
        file = fitxerEjb.findByPrimaryKey(fileID);
        if (file != null) {
          fitxerEjb.delete(file);
        }
      } catch (Exception e) {
        log.error("Error borrant arxiu fisic amb id=" + fileID +
            ((file == null)? "" : ("("+ file.getNom() + ")")));
      }

      return FileSystemManager.eliminarArxiu(fileID);
    }
    return true;
  }


}
