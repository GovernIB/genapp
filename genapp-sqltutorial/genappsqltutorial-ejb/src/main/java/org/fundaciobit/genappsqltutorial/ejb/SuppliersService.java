
package org.fundaciobit.genappsqltutorial.ejb;

// NO MODIFICAR - DO NOT MODIFY;
import javax.ejb.Local;

import org.fundaciobit.genappsqltutorial.persistence.SuppliersJPA;
import org.fundaciobit.genappsqltutorial.persistence.SuppliersIJPAManager;
import org.fundaciobit.genappsqltutorial.model.dao.ISuppliersManager;

@Local
public interface SuppliersService extends SuppliersIJPAManager,ISuppliersManager {

    public static final String JNDI_NAME = "java:app/genappsqltutorial-ejb/SuppliersEJB!org.fundaciobit.genappsqltutorial.ejb.SuppliersService";

    public SuppliersJPA findByPrimaryKey(Long _ID_);
}
