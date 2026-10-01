
package org.fundaciobit.demogenapp.ejb;

// NO MODIFICAR - DO NOT MODIFY;
import javax.ejb.Local;

import org.fundaciobit.demogenapp.persistence.AssignaturaAlumneJPA;
import org.fundaciobit.demogenapp.persistence.AssignaturaAlumneIJPAManager;
import org.fundaciobit.demogenapp.model.dao.IAssignaturaAlumneManager;

import org.fundaciobit.demogenapp.model.entity.AssignaturaAlumne;
import org.fundaciobit.genapp.common.i18n.I18NException;

@Local
public interface AssignaturaAlumneService extends AssignaturaAlumneIJPAManager,IAssignaturaAlumneManager {

    public static final String JNDI_NAME = "java:app/demogenapp-ejb/AssignaturaAlumneEJB!org.fundaciobit.demogenapp.ejb.AssignaturaAlumneService";

    public AssignaturaAlumneJPA findByPrimaryKey(Long _ID_);

    public void deleteIncludingFiles(AssignaturaAlumne instance, FitxerService fitxerEjb) throws I18NException;
}
