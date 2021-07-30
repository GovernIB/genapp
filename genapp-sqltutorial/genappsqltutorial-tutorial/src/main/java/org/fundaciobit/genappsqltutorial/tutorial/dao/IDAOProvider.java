package org.fundaciobit.genappsqltutorial.tutorial.dao;

import org.fundaciobit.genappsqltutorial.model.dao.ICustomersManager;
import org.fundaciobit.genappsqltutorial.model.dao.IProductsManager;

/**
 * 
 * @author anadal
 *
 */
public interface IDAOProvider {

    public void close() throws Exception;

    public ICustomersManager getCustomerManager() throws Exception;

    public IProductsManager getProductsManager() throws Exception;

}
