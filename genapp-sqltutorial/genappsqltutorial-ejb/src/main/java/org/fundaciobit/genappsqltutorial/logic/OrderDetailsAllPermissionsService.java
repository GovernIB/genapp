
package org.fundaciobit.genappsqltutorial.logic;

import javax.ejb.Local;
import javax.persistence.EntityManager;

import org.fundaciobit.genappsqltutorial.persistence.OrderDetailsJPA;
import org.fundaciobit.genappsqltutorial.ejb.OrderDetailsService;

/**
 * 
 * @author anadal
 *
 */
@Local
public interface OrderDetailsAllPermissionsService extends OrderDetailsService {

    public static final String JNDI_NAME = "java:app/genappsqltutorial-ejb/OrderDetailsAllPermissionsEJB!org.fundaciobit.genappsqltutorial.logic.OrderDetailsAllPermissionsService";

    public OrderDetailsJPA findByPrimaryKey(Long _ID_);

    public EntityManager getEntityManagerFactory();
}
