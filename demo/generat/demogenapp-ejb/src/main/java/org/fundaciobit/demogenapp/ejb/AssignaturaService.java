
package org.fundaciobit.demogenapp.ejb;

// NO MODIFICAR - DO NOT MODIFY;
import javax.ejb.Local;

import org.fundaciobit.demogenapp.persistence.AssignaturaJPA;
import org.fundaciobit.demogenapp.persistence.AssignaturaIJPAManager;
import org.fundaciobit.demogenapp.model.dao.IAssignaturaManager;

import org.fundaciobit.demogenapp.model.entity.Assignatura;
import org.fundaciobit.genapp.common.i18n.I18NException;

@Local
public interface AssignaturaService extends AssignaturaIJPAManager,IAssignaturaManager {

    public static final String JNDI_NAME = "java:app/demogenapp-ejb/AssignaturaEJB!org.fundaciobit.demogenapp.ejb.AssignaturaService";

    public AssignaturaJPA findByPrimaryKey(Long _ID_);

    public void deleteIncludingFiles(Assignatura instance, FitxerService fitxerEjb) throws I18NException;
}
