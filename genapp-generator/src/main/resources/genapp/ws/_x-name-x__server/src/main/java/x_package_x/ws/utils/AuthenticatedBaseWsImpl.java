package ${package}.ws.utils;

import java.util.List;

import javax.activation.DataHandler;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.io.FileUtils;
import org.fundaciobit.genapp.common.filesystem.FileSystemManager;

import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genapp.common.i18n.I18NArgumentString;
import org.fundaciobit.genapp.common.i18n.I18NArgumentCode;

import org.fundaciobit.genapp.common.ws.WsI18NException;
import org.fundaciobit.genapp.common.ws.WsValidationException;

import ${package}.hibernate.HibernateFileUtil;
import ${package}.persistence.${fitxer}JPA;
import ${package}.model.bean.${fitxer}Bean;
import ${package}.model.entity.${fitxer};
import ${package}.model.fields.${fitxer}Fields;
import ${package}.ejb.${fitxer}Local;

/**
 * 
 * @author anadal
 * 
 */
public class AuthenticatedBaseWsImpl extends BaseWsImpl implements ${fitxer}Fields  {


  @EJB(mappedName = ${fitxer}Local.JNDI_NAME)
  protected ${fitxer}Local fitxerEjb;
  
  // -------------------------------------------------------------------
  // -------------------------------------------------------------------
  // --------------------------| UTILITATS |----------------------------
  // -------------------------------------------------------------------
  // -------------------------------------------------------------------

  @RolesAllowed({ ${prefix}_ADMIN, ${prefix}_USER })
  @WebMethod
  public FitxerBean downloadFileUsingEncryptedFileID(String encryptedFileID)
      throws WsI18NException, Throwable {

    ${fitxer}JPA.enableEncryptedFileIDGeneration();
    try {
      long fitxerID = HibernateFileUtil.decryptFileID(encryptedFileID);

      // Checks
      ${fitxer} fitxer = checkBasic(fitxerID);

      ${fitxer}Bean fitxerBean = new ${fitxer}Bean(fitxer);
      
      byte[] data = FileUtils.readFileToByteArray(FileSystemManager.getFile(fitxerID));

      DataHandler dh = new DataHandler(new ByteArrayDataSource(data, fitxerBean.getMime()));

      fitxerBean.setData(dh);

      return fitxerBean;

    } finally {
      ${fitxer}JPA.disableEncryptedFileIDGeneration();
    }

  }
  
  
  
  public ${fitxer}JPA checkBasic(long fitxerID) throws I18NException {
    if (fitxerID == 0) {
      // error.notfound=No s�ha trobat cap {0} amb {1} igual a {2}
      throw new I18NException("error.notfound",
          new I18NArgumentCode(_TABLE_TRANSLATION),
          new I18NArgumentCode(FITXERID.fullName),
          new I18NArgumentString(String.valueOf(fitxerID))
          );
    }
    
    ${fitxer}JPA ua = (${fitxer}JPA)fitxerEjb.findByPrimaryKey(fitxerID);
    if (ua == null) {
      // error.notfound=No s�ha trobat cap {0} amb {1} igual a {2}
      throw new I18NException("error.notfound",
          new I18NArgumentCode(_TABLE_TRANSLATION),
          new I18NArgumentCode(FITXERID.fullName),
          new I18NArgumentString(String.valueOf(fitxerID))
          );
    }
    
    return ua;
  }

}
