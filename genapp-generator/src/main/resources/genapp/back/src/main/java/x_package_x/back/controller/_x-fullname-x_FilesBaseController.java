package ${package}.back.controller;

import ${package}.ejb.${fitxer}Local;
import ${package}.model.entity.${fitxer};

import org.fundaciobit.genapp.common.IGenAppEntity;
import org.fundaciobit.genapp.common.filesystem.FileSystemManager;
import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genapp.common.web.controller.CommonFilesBaseController;
import org.fundaciobit.genapp.common.web.controller.FilesFormManager;
import org.fundaciobit.genapp.common.web.form.BaseForm;
import org.fundaciobit.genapp.common.web.i18n.I18NUtils;
import org.fundaciobit.genapp.common.i18n.I18NException;
import org.springframework.stereotype.Controller;

import javax.ejb.EJB;

/**
 * POT SOBRESCRIURE AQUESTA CLASSE
 * 
 * @author anadal
 * 
 */
@Controller
public abstract class ${fullname}FilesBaseController<I extends IGenAppEntity,PK extends Object,F extends BaseForm> extends CommonFilesBaseController<I,PK,F,${fitxer}>{


	@EJB(mappedName=${fitxer}Local.JNDI_NAME)
	protected ${fitxer}Local fitxerEjb;


	/**
	 * 
	 * @return
	 */
	protected FilesFormManager<${fitxer}>

	getFilesFormManager() {
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
      } catch (I18NException e) {
            log.error("Error esborrant arxiu fisic amb id=" + fileID +
                ((file == null)? "" : ("("+ file.getNom() + ")")) + ": " + I18NUtils.getMessage(e),e);
      } catch (Exception e) {
            log.error("Error esborrant arxiu fisic amb id=" + fileID +
              ((file == null)? "" : ("("+ file.getNom() + ")")) + ": " + e.getMessage(),e);
      }

      return FileSystemManager.eliminarArxiu(fileID);
    }
    return true;
  }

}
