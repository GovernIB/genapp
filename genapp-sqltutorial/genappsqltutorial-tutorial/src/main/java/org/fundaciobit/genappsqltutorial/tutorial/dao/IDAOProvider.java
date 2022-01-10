package org.fundaciobit.genappsqltutorial.tutorial.dao;

import javax.persistence.EntityManager;

import org.fundaciobit.genappsqltutorial.model.dao.ICustomersManager;
import org.fundaciobit.genappsqltutorial.model.dao.IOrderDetailsManager;
import org.fundaciobit.genappsqltutorial.model.dao.IOrdersManager;
import org.fundaciobit.genappsqltutorial.model.dao.IProductsManager;
import org.fundaciobit.genappsqltutorial.model.dao.ISuppliersManager;

/**
 * 
 * @author anadal
 *
 */
public interface IDAOProvider {

    public void close() throws Exception;

    public IOrderDetailsManager getOrderDetailsManager() throws Exception;

    public ICustomersManager getCustomerManager() throws Exception;

    public IProductsManager getProductsManager() throws Exception;
    
    public ISuppliersManager getSuppliersManager() throws Exception;
    
    public EntityManager getEntityManagerFactory() throws Exception;
    
    public IOrdersManager getOrdersManager() throws Exception;

}
