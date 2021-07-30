
package org.fundaciobit.genappsqltutorial.ejb;

// NO MODIFICAR - DO NOT MODIFY;
import javax.ejb.Local;

import org.fundaciobit.genappsqltutorial.persistence.OrdersJPA;
import org.fundaciobit.genappsqltutorial.persistence.OrdersIJPAManager;
import org.fundaciobit.genappsqltutorial.model.dao.IOrdersManager;

@Local
public interface OrdersService extends OrdersIJPAManager,IOrdersManager {

    public static final String JNDI_NAME = "java:app/genappsqltutorial-ejb/OrdersEJB!org.fundaciobit.genappsqltutorial.ejb.OrdersService";

    public OrdersJPA findByPrimaryKey(Long _ID_);
}
