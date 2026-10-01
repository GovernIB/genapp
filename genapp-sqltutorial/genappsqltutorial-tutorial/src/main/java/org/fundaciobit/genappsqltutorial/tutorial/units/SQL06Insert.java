package org.fundaciobit.genappsqltutorial.tutorial.units;

import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genappsqltutorial.model.bean.CustomersBean;
import org.fundaciobit.genappsqltutorial.model.entity.Customers;
import org.fundaciobit.genappsqltutorial.tutorial.utils.AbstractUnit;
import org.fundaciobit.genappsqltutorial.tutorial.utils.annotations.TutorialPart;
import org.fundaciobit.genappsqltutorial.tutorial.utils.annotations.TutorialUnit;

/**
 * 
 * @author anadal
 *
 */
@TutorialUnit(
        title = "The SQL INSERT INTO Statement",
        description = "The INSERT INTO statement is used to insert new records in a table.",
        url = "https://www.w3schools.com/sql/sql_insert.asp")
public class SQL06Insert extends AbstractUnit {

    @TutorialPart(
            title = "INSERT INTO Example",
            description = "The following SQL statement inserts a new record in the \"Customers\" table:",
            sql = "INSERT INTO Customers (CustomerName, ContactName, Address, City, PostalCode, Country) VALUES ('Cardinal', 'Tom B. Erichsen', 'Skagen 21', 'Stavanger', '4006', 'Norway');",
            order = 1)
    public void insertCustomer() throws I18NException, Exception {
        // customername , contactname , address , city , country , postalcode
        Customers c;
        c = new CustomersBean("Cardinal", "Tom B. Erichsen", "Skagen 21", "Stavanger", "Norway",
                "4006");

        c = super.getCustomerManager().create(c);

        print(c);
    }

    @TutorialPart(
            title = "Insert Data Only in Specified Columns",
            description = "It is also possible to only insert data in specific columns.\r\n"
                    + "\r\n"
                    + "The following SQL statement will insert a new record, but only insert data in the \"CustomerName\", \"City\", and \"Country\" columns (CustomerID will be updated automatically)",
            sql = "INSERT INTO Customers (CustomerName, City, Country) VALUES ('Cardinal', 'Stavanger', 'Norway'););",
            order = 2)
    public void insertPartialCustomer() throws I18NException, Exception {
        // customername , contactname , address , city , country , postalcode
        Customers c = new CustomersBean("Cardinal", null, null, "Stavanger", "Norway", null);

        c = super.getCustomerManager().create(c);

        print(c);
    }

}
