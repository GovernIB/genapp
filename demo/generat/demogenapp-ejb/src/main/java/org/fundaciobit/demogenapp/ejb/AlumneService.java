
package org.fundaciobit.demogenapp.ejb;

// NO MODIFICAR - DO NOT MODIFY;
import javax.ejb.Local;

import org.fundaciobit.demogenapp.persistence.AlumneJPA;
import org.fundaciobit.demogenapp.persistence.AlumneIJPAManager;
import org.fundaciobit.demogenapp.model.dao.IAlumneManager;

import org.fundaciobit.demogenapp.model.entity.Alumne;
import org.fundaciobit.genapp.common.i18n.I18NException;

@Local
public interface AlumneService extends AlumneIJPAManager,IAlumneManager {

    public static final String JNDI_NAME = "java:app/demogenapp-ejb/AlumneEJB!org.fundaciobit.demogenapp.ejb.AlumneService";

    public AlumneJPA findByPrimaryKey(Long _ID_);

    public void deleteIncludingFiles(Alumne instance, FitxerService fitxerEjb) throws I18NException;
}
