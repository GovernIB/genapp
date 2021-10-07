package org.fundaciobit.genappsqltutorial.tutorial.units;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.Query;

import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genappsqltutorial.model.entity.Customers;
import org.fundaciobit.genappsqltutorial.model.fields.CustomersFields;
import org.fundaciobit.genappsqltutorial.persistence.CustomersJPA;
import org.fundaciobit.genappsqltutorial.tutorial.utils.AbstractUnit;
import org.fundaciobit.genappsqltutorial.tutorial.utils.annotations.TutorialPart;
import org.fundaciobit.genappsqltutorial.tutorial.utils.annotations.TutorialUnit;

/**
 * 
 * @author anadal
 *
 */
@TutorialUnit(
        title = "SQL UPDATE Statement",
        description = "The UPDATE statement is used to modify the existing records in a table.\r\n"
                + "Note: Be careful when updating records in a table! Notice the WHERE clause in the UPDATE statement. "
                + "The WHERE clause specifies which record(s) that should be updated. If you omit the WHERE clause, "
                + "all records in the table will be updated!",
        url = "https://www.w3schools.com/sql/sql_update.asp")
public class SQL08Update extends AbstractUnit {

    @TutorialPart(
            title = "UPDATE a Table",
            description = "The following SQL statement updates the first customer (CustomerID = 1) with a new contact person and a new city.",
            sql = "UPDATE Customers SET ContactName = 'Alfred Schmidt', City= 'Frankfurt' WHERE CustomerID = 1;",
            order = 1)
    public void updateTable() throws I18NException, Exception {
        long customerID = 1;
        Customers customer = getCustomerManager().findByPrimaryKey(customerID);
        print(customer);

        customer.setContactname("Alfred Schmidt");
        customer.setCity("Frankfurt");

        getCustomerManager().update(customer);

        customer = getCustomerManager().findByPrimaryKey(customerID);
        print(customer);

        // Restaurar a estat original
        customer.setContactname("Maria Anders");
        customer.setCity("Berlin");
        getCustomerManager().update(customer);

    }

    @TutorialPart(
            title = "UPDATE Multiple Records (GenQL)",
            description = "It is the WHERE clause that determines how many records will be updated.\r\n"
                    + "The following SQL statement will update the ContactName to \"Juan\" for all records where country is \"Mexico\":",
            sql = "UPDATE Customers SET ContactName='Juan' WHERE Country='Mexico';",
            order = 2)
    public void updateMultipleRecordsGenQL() throws I18NException, Exception {
        Map<Long, String> original = new HashMap<Long, String>();

        // TODO Pending to implement better solution

        List<Customers> customers;
        customers = getCustomerManager().select(CustomersFields.COUNTRY.equal("Mexico"));
        print(customers);

        for (Customers customer : customers) {
            original.put(customer.getCustomerid(), customer.getContactname());
            customer.setContactname("Juan");
            getCustomerManager().update(customer);
        }

        print(customers);

        // Restaurar a estat original
        for (Customers customer : customers) {
            String contactname = original.get(customer.getCustomerid());
            customer.setContactname(contactname);
            getCustomerManager().update(customer);
        }

    }

    /*
     * update gas_customers set contactname='Ana Trujillo' where CustomerID = 2;
     * update gas_customers set contactname='Francisco Chang' where CustomerID = 13;
     * update gas_customers set contactname='Antonio Moreno' where CustomerID = 3;
     * update gas_customers set contactname='Miguel Angel Paolino' where CustomerID
     * = 80; update gas_customers set contactname='Guillermo Fernández' where
     * CustomerID = 58 ;
     */
    /*
     * @TutorialPart( title = "UPDATE Multiple Records (Native)", description =
     * "It is the WHERE clause that determines how many records will be updated.\r\n"
     * +
     * "The following SQL statement will update the ContactName to \"Juan\" for all records where country is \"Mexico\":"
     * , sql = "UPDATE Customers SET ContactName='Juan' WHERE Country='Mexico';",
     * order = 2)
     */

    // TODO NO FUNCIONA !!!!
    // ERROR 1: El segon select no mostra la llista actualitzada,
    // ERROR 2: Les comandes d'actualització no restauren els valors inicials
    public void updateMultipleRecordsNative() throws I18NException, Exception {

        EntityManager entityManager = getEntityManagerFactory();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Map<Long, String> original = new HashMap<Long, String>();

        // Obtenir estat original
        List<CustomersJPA> customers = entityManager
                .createQuery(
                        "from CustomersJPA where " + CustomersFields.COUNTRY.sqlName + "='Mexico'")
                .getResultList();
        // customers =
        // getCustomerManager().select(CustomersFields.COUNTRY.equal("Mexico"));
        print(customers);

        for (Customers customer : customers) {
            original.put(customer.getCustomerid(), customer.getContactname());
        }

        // Update native
        {

            Query query = entityManager.createQuery("update CustomersJPA " // +
                                                                           // CustomersFields._TABLE_SQL
                    + " set " + CustomersFields.CONTACTNAME.sqlName + "='Juan'" + " where "
                    + CustomersFields.COUNTRY.sqlName + "='Mexico'");
            // query.setFlushMode(FlushModeType.COMMIT);
            query.executeUpdate();

        }
        entityManager.flush();
        transaction.commit();

        transaction.begin();
        entityManager.flush();

        // Imprimir valors actualitzats
        customers = entityManager
                .createQuery(
                        "from CustomersJPA where " + CustomersFields.COUNTRY.sqlName + "='Mexico'")
                .getResultList();
        // getCustomerManager().select(CustomersFields.COUNTRY.equal("Mexico"));
        print(customers);

        // Restaurar a estat original
        for (Customers customer : customers) {
            String contactname = original.get(customer.getCustomerid());
            customer.setContactname(contactname);
            getCustomerManager().update(customer);
        }

        transaction.commit();

    }

    @TutorialPart(
            title = "UPDATE All Records (GenQL)",
            description = "Update Warning!\r\n"
                    + "Be careful when updating records. If you omit the WHERE clause, ALL records will be updated!",
            sql = "UPDATE Customers SET ContactName='Juan';",
            order = 3)
    public void updateAllRecords() throws I18NException, Exception {

        // TODO Pending to implement better solution

    }

}
