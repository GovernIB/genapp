
package org.fundaciobit.genappsqltutorial.ejb;

// NO MODIFICAR - DO NOT MODIFY;
import javax.ejb.Local;

import org.fundaciobit.genappsqltutorial.persistence.CategoriesJPA;
import org.fundaciobit.genappsqltutorial.persistence.CategoriesIJPAManager;
import org.fundaciobit.genappsqltutorial.model.dao.ICategoriesManager;

import org.fundaciobit.genappsqltutorial.model.entity.Categories;
import org.fundaciobit.genapp.common.i18n.I18NException;

@Local
public interface CategoriesService extends CategoriesIJPAManager,ICategoriesManager {

    public static final String JNDI_NAME = "java:app/genappsqltutorial-ejb/CategoriesEJB!org.fundaciobit.genappsqltutorial.ejb.CategoriesService";

    public CategoriesJPA findByPrimaryKey(Long _ID_);

    public void deleteIncludingFiles(Categories instance, FitxerService fitxerEjb) throws I18NException;
}
