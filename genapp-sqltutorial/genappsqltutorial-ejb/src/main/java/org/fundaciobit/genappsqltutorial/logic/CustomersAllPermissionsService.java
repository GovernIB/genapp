
package org.fundaciobit.genappsqltutorial.logic;

import javax.ejb.Local;

import org.fundaciobit.genappsqltutorial.persistence.CustomersJPA;
import org.fundaciobit.genappsqltutorial.ejb.CustomersService;

/**
 * 
 * @author anadal
 *
 */
@Local
public interface CustomersAllPermissionsService extends CustomersService {

    public static final String JNDI_NAME = "java:app/genappsqltutorial-ejb/CustomersAllPermissionsEJB!org.fundaciobit.genappsqltutorial.logic.CustomersAllPermissionsService";

    public CustomersJPA findByPrimaryKey(Long _ID_);
}
