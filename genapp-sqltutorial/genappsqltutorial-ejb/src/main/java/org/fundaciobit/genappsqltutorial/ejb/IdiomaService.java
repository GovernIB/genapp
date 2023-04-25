
package org.fundaciobit.genappsqltutorial.ejb;

// NO MODIFICAR - DO NOT MODIFY;
import javax.ejb.Local;

import org.fundaciobit.genappsqltutorial.persistence.IdiomaJPA;
import org.fundaciobit.genappsqltutorial.persistence.IdiomaIJPAManager;
import org.fundaciobit.genappsqltutorial.model.dao.IIdiomaManager;

import org.fundaciobit.genappsqltutorial.model.entity.Idioma;
import org.fundaciobit.genapp.common.i18n.I18NException;

@Local
public interface IdiomaService extends IdiomaIJPAManager,IIdiomaManager {

    public static final String JNDI_NAME = "java:app/genappsqltutorial-ejb/IdiomaEJB!org.fundaciobit.genappsqltutorial.ejb.IdiomaService";

    public IdiomaJPA findByPrimaryKey(String _ID_);

    public void deleteIncludingFiles(Idioma instance, FitxerService fitxerEjb) throws I18NException;
}
