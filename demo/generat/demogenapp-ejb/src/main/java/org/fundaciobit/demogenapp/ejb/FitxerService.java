
package org.fundaciobit.demogenapp.ejb;

// NO MODIFICAR - DO NOT MODIFY;
import javax.ejb.Local;

import org.fundaciobit.demogenapp.persistence.FitxerJPA;
import org.fundaciobit.demogenapp.persistence.FitxerIJPAManager;
import org.fundaciobit.demogenapp.model.dao.IFitxerManager;

import org.fundaciobit.demogenapp.model.entity.Fitxer;
import org.fundaciobit.genapp.common.i18n.I18NException;

@Local
public interface FitxerService extends FitxerIJPAManager,IFitxerManager {

    public static final String JNDI_NAME = "java:app/demogenapp-ejb/FitxerEJB!org.fundaciobit.demogenapp.ejb.FitxerService";

    public FitxerJPA findByPrimaryKey(Long _ID_);

    public void deleteIncludingFiles(Fitxer instance, FitxerService fitxerEjb) throws I18NException;
}
