package org.fundaciobit.genappsqltutorial.tutorial.dao;

/**
 * 
 * @author anadal
 *
 */
public class DAOManager {
    
   
    private static IDAOProvider daoProvider = null;
    
    
    
    public static IDAOProvider getDAOProvider() throws Exception {
        if (daoProvider == null) {
            throw new Exception("Abans ha de cridar a DAOManager.setDAOProvider(...).");
        }
        return daoProvider;
    }
    
    
    
    public static void setDAOProvider(IDAOProvider dao) throws Exception {
        daoProvider = dao;
    }
    
    
    public static void close() throws Exception {
        if (daoProvider == null) {
            throw new Exception("Abans ha de cridar a DAOManager.setDAOProvider(...).");
        }
        daoProvider.close();
    }

}
