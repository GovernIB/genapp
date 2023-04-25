
package org.fundaciobit.genappsqltutorial.ejb;

// NO MODIFICAR - DO NOT MODIFY;
import javax.ejb.Local;

import org.fundaciobit.genappsqltutorial.persistence.OrderDetailsJPA;
import org.fundaciobit.genappsqltutorial.persistence.OrderDetailsIJPAManager;
import org.fundaciobit.genappsqltutorial.model.dao.IOrderDetailsManager;

import org.fundaciobit.genappsqltutorial.model.entity.OrderDetails;
import org.fundaciobit.genapp.common.i18n.I18NException;

@Local
public interface OrderDetailsService extends OrderDetailsIJPAManager,IOrderDetailsManager {

    public static final String JNDI_NAME = "java:app/genappsqltutorial-ejb/OrderDetailsEJB!org.fundaciobit.genappsqltutorial.ejb.OrderDetailsService";

    public OrderDetailsJPA findByPrimaryKey(Long _ID_);

    public void deleteIncludingFiles(OrderDetails instance, FitxerService fitxerEjb) throws I18NException;
}
