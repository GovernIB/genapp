
package org.fundaciobit.genappsqltutorial.logic;

import javax.ejb.Stateless;
import javax.annotation.security.PermitAll;
import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genappsqltutorial.model.entity.Customers;
import org.fundaciobit.genappsqltutorial.persistence.CustomersJPA;

import org.fundaciobit.genappsqltutorial.ejb.CustomersEJB;

/**
 * 
 * @author anadal
 *
 */
@Stateless
@PermitAll
public class CustomersAllPermissionsEJB extends CustomersEJB
        implements CustomersAllPermissionsService {

    @Override
    @PermitAll
    public void delete(Customers instance) {
        super.delete(instance);
    }

    @Override
    @PermitAll
    public Customers create(Customers instance) throws I18NException {
        return super.create(instance);
    }

    @Override
    @PermitAll
    public Customers update(Customers instance) throws I18NException {
        return super.update(instance);
    }

    @Override
    @PermitAll
    public CustomersJPA findByPrimaryKey(Long _ID_) {
        return (CustomersJPA) super.findByPrimaryKey(_ID_);
    }

}
