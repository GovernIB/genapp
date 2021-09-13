package org.fundaciobit.genappsqltutorial.persistence.test;


import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;

import org.fundaciobit.genappsqltutorial.persistence.GenAppSqlTutorialJPADaoManagers;
import org.fundaciobit.genappsqltutorial.model.GenAppSqlTutorialDaoManager;



/**
 * 
 * @author anadal
 *
 */
public class TestPersistenceJPA {

    public static final Logger log = Logger.getLogger(TestPersistenceJPA.class);


    public static final void main(String[] args) {
        EntityManagerFactory emf = null;
        try {
            log.info(">>>>>>>>>>>>  Hello World!");

            // USING GENAPP
            // ============

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

            prop.put("hibernate.show_sql", "true");

            

            // Veure persistence.xml
            emf = Persistence.createEntityManagerFactory("genappsqltutorialPULocal", prop);

            EntityManager em = emf.createEntityManager();

            em.setFlushMode(FlushModeType.AUTO);

            EntityTransaction tx = em.getTransaction();

            tx.begin();


            GenAppSqlTutorialDaoManager.setDaoManagers(new GenAppSqlTutorialJPADaoManagers(em)); 
            
            
            
            /*   EXEMPLE DE CRIDADA DIRECTE
              
             
            String hsql = "SELECT " + PluginFields.NOMID.fullName
             + " FROM PluginJPA plugin, 
             + " ORDER BY " + PluginFields.NOMID.fullName + " DESC";
            
            javax.persistence.Query qry = em.createQuery(hsql);
            
            List<Object> list = qry.getResultList();
            for (Object object : list) {
                System.out.println("Object[] => " + object);
            }
            
            */
            

            /*
             EXEMPLE DE LLISTAT 
             
            IPluginManager pluginMan = GenAppSqlTutorialDaoManager.getDaoManagers().getPluginManager();

           
            SelectTraduccio st = new SelectTraduccio(PluginFields.NOMID, "es");

            List<String> noms = pluginMan.executeQuery(st, null);
            
            for (String nom : noms) {
                System.out.println("NOM[" + nom + "]");
            }
            
            */
            

            /*  CONSULTA IDIOMES DISPONIBLES
             * IIdiomaManager idioma = GenAppSqlTutorialDaoManager.getDaoManagers().getIdiomaManager();
             * 
             * List<Idioma> llist = idioma.select(new OrderBy(IdiomaFields.IDIOMAID,
             * OrderType.DESC));
             * 
             * for (Idioma idioma2 : llist) { System.out.println("Idoma = " +
             * idioma2.getIdiomaID() + " => " + idioma2.getNom()); }
             * 
             * System.out.println("===");
             * 
             * llist = idioma.select(IdiomaFields.NOM.like("%Cat%"));
             * 
             * for (Idioma idioma2 : llist) { System.out.println("Idoma222 = " +
             * idioma2.getIdiomaID() + " => " + idioma2.getNom()); }
             * 
             */

            tx.commit();
            log.info("<<<<<<<<<<<  Good Bye!");
            System.out.println("FINAL");

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (emf != null) {
                emf.close();
            }
        }
    }

}