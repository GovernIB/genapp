
package org.fundaciobit.genappsqltutorial.ejb;

// NO MODIFICAR - DO NOT MODIFY;
import javax.ejb.Local;

import org.fundaciobit.genappsqltutorial.persistence.TraduccioJPA;
import org.fundaciobit.genappsqltutorial.persistence.TraduccioIJPAManager;
import org.fundaciobit.genappsqltutorial.model.dao.ITraduccioManager;

@Local
public interface TraduccioService extends TraduccioIJPAManager,ITraduccioManager {

    public static final String JNDI_NAME = "java:app/genappsqltutorial-ejb/TraduccioEJB!org.fundaciobit.genappsqltutorial.ejb.TraduccioService";

    public TraduccioJPA findByPrimaryKey(Long _ID_);
}
