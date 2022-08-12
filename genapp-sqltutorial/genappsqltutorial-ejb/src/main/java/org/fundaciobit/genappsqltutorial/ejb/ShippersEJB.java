
package org.fundaciobit.genappsqltutorial.ejb;

// NO MODIFICAR - DO NOT MODIFY;
import javax.ejb.Stateless;
import javax.annotation.security.RolesAllowed;
import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genappsqltutorial.model.entity.Shippers;
import org.fundaciobit.genappsqltutorial.persistence.ShippersJPA;
import org.fundaciobit.genappsqltutorial.persistence.ShippersJPAManager;

import org.fundaciobit.genappsqltutorial.commons.utils.Constants;

@Stateless
public class ShippersEJB extends ShippersJPAManager implements ShippersService {

    @Override
    @RolesAllowed({Constants.ROLE_EJB_FULL_ACCESS, Constants.ROLE_EJB_BASIC_ACCESS})
    public void delete(Shippers instance) {
        super.delete(instance);
    }

    @Override
    @RolesAllowed({Constants.ROLE_EJB_FULL_ACCESS, Constants.ROLE_EJB_BASIC_ACCESS})
    public Shippers create(Shippers instance) throws I18NException {
        return super.create(instance);
    }

    @Override
    @RolesAllowed({Constants.ROLE_EJB_FULL_ACCESS, Constants.ROLE_EJB_BASIC_ACCESS})
    public Shippers update(Shippers instance) throws I18NException {
         return super.update(instance);
    }

    public void deleteIncludingFiles(Shippers instance, org.fundaciobit.genappsqltutorial.ejb.FitxerService fitxerEjb)
            throws I18NException {

        this.delete(instance);
    }

    @Override
    @RolesAllowed({Constants.ROLE_EJB_FULL_ACCESS, Constants.ROLE_EJB_BASIC_ACCESS})
    public ShippersJPA findByPrimaryKey(Long _ID_) {
        return (ShippersJPA)super.findByPrimaryKey(_ID_);
    }

}
