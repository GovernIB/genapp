package org.fundaciobit.genappsqltutorial.back.utils;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.fundaciobit.genappsqltutorial.model.dao.ICustomersManager;
import org.fundaciobit.genappsqltutorial.model.dao.IOrderDetailsManager;
import org.fundaciobit.genappsqltutorial.model.dao.IProductsManager;
import org.fundaciobit.genappsqltutorial.tutorial.dao.IDAOProvider;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


/**
 * 
 * @author anadal
 *
 */
public class DAOProviderEjb implements IDAOProvider {

    @Override
    public void close() throws Exception {
        // no fer res
    }

    @Override
    public ICustomersManager getCustomerManager() throws Exception {
        
        return getIDAOProviderOfSession().getCustomerManager();
    }

    @Override
    public IProductsManager getProductsManager() throws Exception {
        return getIDAOProviderOfSession().getProductsManager();
    }
    
    
    public IOrderDetailsManager getOrderDetailsManager() throws Exception {
        return getIDAOProviderOfSession().getOrderDetailsManager();
    }
    
    
    private static IDAOProvider getIDAOProviderOfSession() {

        HttpServletRequest request;
        request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();

        HttpSession session = request.getSession();

        IDAOProvider str = (IDAOProvider) session.getAttribute("IDAOProvider_SESSION");

        return str;

    }
    
    
    public static void setIDAOProviderOfSession(IDAOProvider dao) {

        HttpServletRequest request;
        request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();

        HttpSession session = request.getSession();
        session.setAttribute("IDAOProvider_SESSION", dao);

    }

    @Override
    public EntityManager getEntityManagerFactory() throws Exception {
        return getIDAOProviderOfSession().getEntityManagerFactory();
    }

    

}
