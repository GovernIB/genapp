package ${package_jpa}.test;


import java.util.Properties;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;


import ${package_jpa}.${fullname}JPADaoManagers;
import ${package}.model.${fullname}DaoManager;



/**
 * 
 * @author anadal
 *
 */
public class TestPersistenceJPA {

    public static final Logger log = Logger.getLogger(TestPersistenceJPA.class);


    public static final void main(String[] args) {
        try {
            log.info(">>>>>>>>>>>>  Hello World!");

            // USING GENAPP
            // ============

            Properties prop = new Properties();

            prop.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
            prop.put("javax.persistence.jdbc.driver", "org.postgresql.Driver");
            prop.put("javax.persistence.jdbc.url", "jdbc:postgresql://localhost:5432/${name}");
            prop.put("javax.persistence.jdbc.user", "${name}");
            prop.put("javax.persistence.jdbc.password", "${name}");

            prop.put("hibernate.connection.driver_class", "org.postgresql.Driver");
            // prop.put("javax.persistence.jdbc.url","jdbc:postgresql://192.168.35.151:5432/pinbaladmin");
            prop.put("hibernate.connection.url", "jdbc:postgresql://localhost:5432/${name}");
            prop.put("hibernate.connection.username", "${name}");
            prop.put("hibernate.connection.password", "${name}");

            prop.put("hibernate.show_sql", "true");

            EntityManagerFactory emf;

            // Veure persistence.xml
            emf = Persistence.createEntityManagerFactory("${name}PULocal", prop);

            EntityManager em = emf.createEntityManager();

            em.setFlushMode(FlushModeType.AUTO);

            EntityTransaction tx = em.getTransaction();

            tx.begin();


            ${fullname}DaoManager.setDaoManagers(new ${fullname}JPADaoManagers(em)); 
            
            
            
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
             
            IPluginManager pluginMan = ${fullname}DaoManager.getDaoManagers().getPluginManager();

           
            SelectTraduccio st = new SelectTraduccio(PluginFields.NOMID, "es");

            List<String> noms = pluginMan.executeQuery(st, null);
            
            for (String nom : noms) {
                System.out.println("NOM[" + nom + "]");
            }
            
            */
            

            /*  CONSULTA IDIOMES DISPONIBLES
             * IIdiomaManager idioma = ${fullname}DaoManager.getDaoManagers().getIdiomaManager();
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

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
