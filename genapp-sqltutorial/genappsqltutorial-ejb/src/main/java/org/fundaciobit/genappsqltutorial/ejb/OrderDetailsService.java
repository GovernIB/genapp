
package org.fundaciobit.genappsqltutorial.ejb;

// NO MODIFICAR - DO NOT MODIFY;
import javax.ejb.Local;

import org.fundaciobit.genappsqltutorial.persistence.OrderDetailsJPA;
import org.fundaciobit.genappsqltutorial.persistence.OrderDetailsIJPAManager;
import org.fundaciobit.genappsqltutorial.model.dao.IOrderDetailsManager;

@Local
public interface OrderDetailsService extends OrderDetailsIJPAManager,IOrderDetailsManager {

    public static final String JNDI_NAME = "java:app/genappsqltutorial-ejb/OrderDetailsEJB!org.fundaciobit.genappsqltutorial.ejb.OrderDetailsService";

    public OrderDetailsJPA findByPrimaryKey(Long _ID_);
}
