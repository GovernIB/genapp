
package org.fundaciobit.genappsqltutorial.ejb;

// NO MODIFICAR - DO NOT MODIFY;
import javax.ejb.Local;

import org.fundaciobit.genappsqltutorial.persistence.ShippersJPA;
import org.fundaciobit.genappsqltutorial.persistence.ShippersIJPAManager;
import org.fundaciobit.genappsqltutorial.model.dao.IShippersManager;

import org.fundaciobit.genappsqltutorial.model.entity.Shippers;
import org.fundaciobit.genapp.common.i18n.I18NException;

@Local
public interface ShippersService extends ShippersIJPAManager,IShippersManager {

    public static final String JNDI_NAME = "java:app/genappsqltutorial-ejb/ShippersEJB!org.fundaciobit.genappsqltutorial.ejb.ShippersService";

    public ShippersJPA findByPrimaryKey(Long _ID_);

    public void deleteIncludingFiles(Shippers instance, FitxerService fitxerEjb) throws I18NException;
}
