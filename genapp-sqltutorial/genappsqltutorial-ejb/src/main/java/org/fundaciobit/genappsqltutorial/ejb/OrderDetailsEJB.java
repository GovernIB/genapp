
package org.fundaciobit.genappsqltutorial.ejb;

// NO MODIFICAR - DO NOT MODIFY;
import javax.ejb.Stateless;
import javax.annotation.security.RolesAllowed;
import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genappsqltutorial.model.entity.OrderDetails;
import org.fundaciobit.genappsqltutorial.persistence.OrderDetailsJPA;
import org.fundaciobit.genappsqltutorial.persistence.OrderDetailsJPAManager;

import org.fundaciobit.genappsqltutorial.commons.utils.Constants;

@Stateless
public class OrderDetailsEJB extends OrderDetailsJPAManager implements OrderDetailsService {

    @Override
    @RolesAllowed({Constants.ROLE_EJB_FULL_ACCESS, Constants.ROLE_EJB_BASIC_ACCESS})
    public void delete(OrderDetails instance) {
        super.delete(instance);
    }

    @Override
    @RolesAllowed({Constants.ROLE_EJB_FULL_ACCESS, Constants.ROLE_EJB_BASIC_ACCESS})
    public OrderDetails create(OrderDetails instance) throws I18NException {
        return super.create(instance);
    }

    @Override
    @RolesAllowed({Constants.ROLE_EJB_FULL_ACCESS, Constants.ROLE_EJB_BASIC_ACCESS})
    public OrderDetails update(OrderDetails instance) throws I18NException {
         return super.update(instance);
    }

    @Override
    @RolesAllowed({Constants.ROLE_EJB_FULL_ACCESS, Constants.ROLE_EJB_BASIC_ACCESS})
    public OrderDetailsJPA findByPrimaryKey(Long _ID_) {
        return (OrderDetailsJPA)super.findByPrimaryKey(_ID_);
    }

}
