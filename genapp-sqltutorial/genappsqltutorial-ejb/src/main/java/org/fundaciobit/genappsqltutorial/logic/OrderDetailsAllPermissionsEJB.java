
package org.fundaciobit.genappsqltutorial.logic;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.annotation.security.PermitAll;
import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genappsqltutorial.model.entity.OrderDetails;
import org.fundaciobit.genappsqltutorial.persistence.OrderDetailsJPA;

import org.fundaciobit.genappsqltutorial.ejb.OrderDetailsEJB;

/**
 * 
 * @author anadal
 *
 */
@Stateless
@PermitAll
public class OrderDetailsAllPermissionsEJB extends OrderDetailsEJB
        implements OrderDetailsAllPermissionsService {

    @Override
    @PermitAll
    public void delete(OrderDetails instance) {
        super.delete(instance);
    }

    @Override
    @PermitAll
    public OrderDetails create(OrderDetails instance) throws I18NException {
        return super.create(instance);
    }

    @Override
    @PermitAll
    public OrderDetails update(OrderDetails instance) throws I18NException {
        return super.update(instance);
    }

    @Override
    @PermitAll
    public OrderDetailsJPA findByPrimaryKey(Long _ID_) {
        return (OrderDetailsJPA) super.findByPrimaryKey(_ID_);
    }
    
    @Override
    @PermitAll
    public EntityManager getEntityManagerFactory() {
        return super.getEntityManager();
    }

}
