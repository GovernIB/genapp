
package org.fundaciobit.demogenapp.ejb;

// NO MODIFICAR - DO NOT MODIFY;
import javax.ejb.Stateless;
import javax.annotation.security.RolesAllowed;
import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.demogenapp.model.entity.AssignaturaAlumne;
import org.fundaciobit.demogenapp.persistence.AssignaturaAlumneJPA;
import org.fundaciobit.demogenapp.persistence.AssignaturaAlumneJPAManager;

import org.fundaciobit.demogenapp.commons.utils.Constants;

@Stateless
public class AssignaturaAlumneEJB extends AssignaturaAlumneJPAManager implements AssignaturaAlumneService {

    @Override
    @RolesAllowed({Constants.ROLE_EJB_FULL_ACCESS, Constants.ROLE_EJB_BASIC_ACCESS, Constants.ROLE_EJB_WS_ACCESS})
    public void delete(AssignaturaAlumne instance) {
        super.delete(instance);
    }

    @Override
    @RolesAllowed({Constants.ROLE_EJB_FULL_ACCESS, Constants.ROLE_EJB_BASIC_ACCESS, Constants.ROLE_EJB_WS_ACCESS})
    public AssignaturaAlumne create(AssignaturaAlumne instance) throws I18NException {
        return super.create(instance);
    }

    @Override
    @RolesAllowed({Constants.ROLE_EJB_FULL_ACCESS, Constants.ROLE_EJB_BASIC_ACCESS, Constants.ROLE_EJB_WS_ACCESS})
    public AssignaturaAlumne update(AssignaturaAlumne instance) throws I18NException {
         return super.update(instance);
    }

    @Override
    @RolesAllowed({Constants.ROLE_EJB_FULL_ACCESS, Constants.ROLE_EJB_BASIC_ACCESS, Constants.ROLE_EJB_WS_ACCESS})
    public void deleteIncludingFiles(AssignaturaAlumne instance,  FitxerService fitxerEjb)
            throws I18NException {

        this.delete(instance);
    }

    @Override
    @RolesAllowed({Constants.ROLE_EJB_FULL_ACCESS, Constants.ROLE_EJB_BASIC_ACCESS, Constants.ROLE_EJB_WS_ACCESS})
    public AssignaturaAlumneJPA findByPrimaryKey(Long _ID_) {
        return (AssignaturaAlumneJPA)super.findByPrimaryKey(_ID_);
    }

}
