
package org.fundaciobit.demogenapp.ejb;

// NO MODIFICAR - DO NOT MODIFY;
import javax.ejb.Local;

import org.fundaciobit.demogenapp.persistence.IdiomaJPA;
import org.fundaciobit.demogenapp.persistence.IdiomaIJPAManager;
import org.fundaciobit.demogenapp.model.dao.IIdiomaManager;

import org.fundaciobit.demogenapp.model.entity.Idioma;
import org.fundaciobit.genapp.common.i18n.I18NException;

@Local
public interface IdiomaService extends IdiomaIJPAManager,IIdiomaManager {

    public static final String JNDI_NAME = "java:app/demogenapp-ejb/IdiomaEJB!org.fundaciobit.demogenapp.ejb.IdiomaService";

    public IdiomaJPA findByPrimaryKey(String _ID_);

    public void deleteIncludingFiles(Idioma instance, FitxerService fitxerEjb) throws I18NException;
}
