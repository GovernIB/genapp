
package org.fundaciobit.genappsqltutorial.ejb;

// NO MODIFICAR - DO NOT MODIFY;
import javax.ejb.Local;

import org.fundaciobit.genappsqltutorial.persistence.CustomersJPA;
import org.fundaciobit.genappsqltutorial.persistence.CustomersIJPAManager;
import org.fundaciobit.genappsqltutorial.model.dao.ICustomersManager;

@Local
public interface CustomersService extends CustomersIJPAManager,ICustomersManager {

    public static final String JNDI_NAME = "java:app/genappsqltutorial-ejb/CustomersEJB!org.fundaciobit.genappsqltutorial.ejb.CustomersService";

    public CustomersJPA findByPrimaryKey(Long _ID_);
}
