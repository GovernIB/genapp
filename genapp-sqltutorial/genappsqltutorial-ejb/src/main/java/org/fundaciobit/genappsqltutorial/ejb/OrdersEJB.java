
package org.fundaciobit.genappsqltutorial.ejb;

// NO MODIFICAR - DO NOT MODIFY;
import javax.ejb.Stateless;
import javax.annotation.security.RolesAllowed;
import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genappsqltutorial.model.entity.Orders;
import org.fundaciobit.genappsqltutorial.persistence.OrdersJPA;
import org.fundaciobit.genappsqltutorial.persistence.OrdersJPAManager;

import org.fundaciobit.genappsqltutorial.commons.utils.Constants;

@Stateless
public class OrdersEJB extends OrdersJPAManager implements OrdersService {

    @Override
    @RolesAllowed({Constants.ROLE_EJB_FULL_ACCESS, Constants.ROLE_EJB_BASIC_ACCESS})
    public void delete(Orders instance) {
        super.delete(instance);
    }

    @Override
    @RolesAllowed({Constants.ROLE_EJB_FULL_ACCESS, Constants.ROLE_EJB_BASIC_ACCESS})
    public Orders create(Orders instance) throws I18NException {
        return super.create(instance);
    }

    @Override
    @RolesAllowed({Constants.ROLE_EJB_FULL_ACCESS, Constants.ROLE_EJB_BASIC_ACCESS})
    public Orders update(Orders instance) throws I18NException {
         return super.update(instance);
    }

    public void deleteIncludingFiles(Orders instance, org.fundaciobit.genappsqltutorial.ejb.FitxerService fitxerEjb)
            throws I18NException {

        this.delete(instance);
    }

    @Override
    @RolesAllowed({Constants.ROLE_EJB_FULL_ACCESS, Constants.ROLE_EJB_BASIC_ACCESS})
    public OrdersJPA findByPrimaryKey(Long _ID_) {
        return (OrdersJPA)super.findByPrimaryKey(_ID_);
    }

}
