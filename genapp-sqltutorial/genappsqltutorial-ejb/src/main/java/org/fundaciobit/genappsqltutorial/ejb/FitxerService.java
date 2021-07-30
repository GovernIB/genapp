
package org.fundaciobit.genappsqltutorial.ejb;

// NO MODIFICAR - DO NOT MODIFY;
import javax.ejb.Local;

import org.fundaciobit.genappsqltutorial.persistence.FitxerJPA;
import org.fundaciobit.genappsqltutorial.persistence.FitxerIJPAManager;
import org.fundaciobit.genappsqltutorial.model.dao.IFitxerManager;

@Local
public interface FitxerService extends FitxerIJPAManager,IFitxerManager {

    public static final String JNDI_NAME = "java:app/genappsqltutorial-ejb/FitxerEJB!org.fundaciobit.genappsqltutorial.ejb.FitxerService";

    public FitxerJPA findByPrimaryKey(Long _ID_);
}
