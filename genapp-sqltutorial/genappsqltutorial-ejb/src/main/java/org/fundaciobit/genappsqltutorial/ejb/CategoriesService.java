
package org.fundaciobit.genappsqltutorial.ejb;

// NO MODIFICAR - DO NOT MODIFY;
import javax.ejb.Local;

import org.fundaciobit.genappsqltutorial.persistence.CategoriesJPA;
import org.fundaciobit.genappsqltutorial.persistence.CategoriesIJPAManager;
import org.fundaciobit.genappsqltutorial.model.dao.ICategoriesManager;

@Local
public interface CategoriesService extends CategoriesIJPAManager,ICategoriesManager {

    public static final String JNDI_NAME = "java:app/genappsqltutorial-ejb/CategoriesEJB!org.fundaciobit.genappsqltutorial.ejb.CategoriesService";

    public CategoriesJPA findByPrimaryKey(Long _ID_);
}
