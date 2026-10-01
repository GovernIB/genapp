
package org.fundaciobit.demogenapp.ejb;

// NO MODIFICAR - DO NOT MODIFY;
import javax.ejb.Local;

import org.fundaciobit.demogenapp.persistence.TraduccioJPA;
import org.fundaciobit.demogenapp.persistence.TraduccioIJPAManager;
import org.fundaciobit.demogenapp.model.dao.ITraduccioManager;

import org.fundaciobit.demogenapp.model.entity.Traduccio;
import org.fundaciobit.genapp.common.i18n.I18NException;

@Local
public interface TraduccioService extends TraduccioIJPAManager,ITraduccioManager {

    public static final String JNDI_NAME = "java:app/demogenapp-ejb/TraduccioEJB!org.fundaciobit.demogenapp.ejb.TraduccioService";

    public TraduccioJPA findByPrimaryKey(Long _ID_);

    public void deleteIncludingFiles(Traduccio instance, FitxerService fitxerEjb) throws I18NException;
}
