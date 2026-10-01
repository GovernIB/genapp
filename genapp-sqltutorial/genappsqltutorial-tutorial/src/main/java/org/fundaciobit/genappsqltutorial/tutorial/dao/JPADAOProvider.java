package org.fundaciobit.genappsqltutorial.tutorial.dao;


import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FlushModeType;
import javax.persistence.Persistence;

import org.fundaciobit.genappsqltutorial.model.GenAppSqlTutorialDaoManager;
import org.fundaciobit.genappsqltutorial.model.dao.ICustomersManager;
import org.fundaciobit.genappsqltutorial.model.dao.IOrderDetailsManager;
import org.fundaciobit.genappsqltutorial.model.dao.IOrdersManager;
import org.fundaciobit.genappsqltutorial.model.dao.IProductsManager;
import org.fundaciobit.genappsqltutorial.model.dao.ISuppliersManager;
import org.fundaciobit.genappsqltutorial.persistence.GenAppSqlTutorialJPADaoManagers;
import org.fundaciobit.genappsqltutorial.tutorial.printer.ConsoleTableFormatPrinterResultsImpl;

/**
 *
 * @author anadal
 *
 */
public class JPADAOProvider extends ConsoleTableFormatPrinterResultsImpl implements IDAOProvider  {

    private boolean showSql = false;
    
    public JPADAOProvider() {
        getEntityManagerFactory();
    }
    
    public JPADAOProvider(boolean showSql) {
        this.showSql = showSql;
        getEntityManagerFactory();
    }

    private EntityManager em = null;

    private EntityManagerFactory emf;

    @Override
    public EntityManager getEntityManagerFactory() {

        if (em == null) {

            Properties prop = new Properties();

            prop.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
            prop.put("javax.persistence.jdbc.driver", "org.postgresql.Driver");
            prop.put("javax.persistence.jdbc.url", "jdbc:postgresql://localhost:5432/genappsqltutorial");
            prop.put("javax.persistence.jdbc.user", "genappsqltutorial");
            prop.put("javax.persistence.jdbc.password", "genappsqltutorial");

            prop.put("hibernate.connection.driver_class", "org.postgresql.Driver");
            // prop.put("javax.persistence.jdbc.url","jdbc:postgresql://192.168.35.151:5432/pinbaladmin");
            prop.put("hibernate.connection.url", "jdbc:postgresql://localhost:5432/genappsqltutorial");
            prop.put("hibernate.connection.username", "genappsqltutorial");
            prop.put("hibernate.connection.password", "genappsqltutorial");

            prop.put("hibernate.show_sql", this.showSql);

            // Veure persistence.xml
            emf = Persistence.createEntityManagerFactory("genappsqltutorialPULocal", prop);

            em = emf.createEntityManager();

            em.setFlushMode(FlushModeType.AUTO);

            GenAppSqlTutorialDaoManager.setDaoManagers(new GenAppSqlTutorialJPADaoManagers(em));
        }

        return em;
    }

    @Override
    public void close() throws Exception {
        getEntityManagerFactory();
        this.em.close();
        this.emf.close();
    }

    @Override
    public ICustomersManager getCustomerManager() throws Exception {
        return GenAppSqlTutorialDaoManager.getDaoManagers().getCustomersManager();
    }

    @Override
    public IProductsManager getProductsManager() throws Exception {
        return GenAppSqlTutorialDaoManager.getDaoManagers().getProductsManager();
    }
    
    @Override
    public IOrderDetailsManager getOrderDetailsManager() throws Exception {
        return GenAppSqlTutorialDaoManager.getDaoManagers().getOrderDetailsManager();
    }

    @Override
    public ISuppliersManager getSuppliersManager() throws Exception {
        return GenAppSqlTutorialDaoManager.getDaoManagers().getSuppliersManager();
    }
    
    @Override
    public IOrdersManager getOrdersManager() throws Exception {
        return GenAppSqlTutorialDaoManager.getDaoManagers().getOrdersManager();
    }
    
}
