
package org.fundaciobit.genappsqltutorial.ejb;

// NO MODIFICAR - DO NOT MODIFY;
import javax.ejb.Local;

import org.fundaciobit.genappsqltutorial.persistence.ProductsJPA;
import org.fundaciobit.genappsqltutorial.persistence.ProductsIJPAManager;
import org.fundaciobit.genappsqltutorial.model.dao.IProductsManager;

@Local
public interface ProductsService extends ProductsIJPAManager,IProductsManager {

    public static final String JNDI_NAME = "java:app/genappsqltutorial-ejb/ProductsEJB!org.fundaciobit.genappsqltutorial.ejb.ProductsService";

    public ProductsJPA findByPrimaryKey(Long _ID_);
}
