
package org.fundaciobit.genappsqltutorial.ejb;

// NO MODIFICAR - DO NOT MODIFY;
import javax.ejb.Local;

import org.fundaciobit.genappsqltutorial.persistence.ProductsJPA;
import org.fundaciobit.genappsqltutorial.persistence.ProductsIJPAManager;
import org.fundaciobit.genappsqltutorial.model.dao.IProductsManager;

import org.fundaciobit.genappsqltutorial.model.entity.Products;
import org.fundaciobit.genapp.common.i18n.I18NException;

@Local
public interface ProductsService extends ProductsIJPAManager,IProductsManager {

    public static final String JNDI_NAME = "java:app/genappsqltutorial-ejb/ProductsEJB!org.fundaciobit.genappsqltutorial.ejb.ProductsService";

    public ProductsJPA findByPrimaryKey(Long _ID_);

    public void deleteIncludingFiles(Products instance, FitxerService fitxerEjb) throws I18NException;
}
