package org.fundaciobit.genappsqltutorial.tutorial.units;

import java.util.List;

import org.fundaciobit.genapp.common.KeyValue;
import org.fundaciobit.genapp.common.query.SelectMultipleKeyValue;
import org.fundaciobit.genappsqltutorial.model.entity.Customers;
import org.fundaciobit.genappsqltutorial.model.fields.CustomersFields;
import org.fundaciobit.genappsqltutorial.tutorial.utils.AbstractUnit;
import org.fundaciobit.genappsqltutorial.tutorial.utils.annotations.TutorialPart;
import org.fundaciobit.genappsqltutorial.tutorial.utils.annotations.TutorialUnit;

/**
 *
 * @author anadal
 *
 */
@TutorialUnit(title = "SQL SELECT Statement", description = "The SELECT statement is used to select data from a database."
        + "\nThe data returned is stored in a result table, called the result-set.", url = "https://www.w3schools.com/sql/sql_select.asp")
public class SQL01Select extends AbstractUnit {
    /**
     * 
     * @throws Exception
     */
    @TutorialPart(title = "SELECT Column Example", description = "The following SQL statement selects the \"CustomerName\" and \"City\" columns from the \"Customers\" table:", sql = "SELECT CustomerName,City FROM Customers;", order = 1)
    public void fieldsSelect() throws Exception {
        SelectMultipleKeyValue<String> select = new SelectMultipleKeyValue<String>(
                CustomersFields.CUSTOMERNAME.select, CustomersFields.CITY.select);
        List<KeyValue<String>> customersFields = this.getCustomerManager().executeQuery(select,
                null);
        print(CustomersFields.CUSTOMERNAME, CustomersFields.CITY, customersFields);
    }

    /**
     * 
     * @param customerManager
     * @return
     * @throws Exception
     */
    @TutorialPart(title = "SELECT * Example", description = "The following SQL statement selects all the columns from the \"Customers\" table:", sql = "SELECT * FROM Customers;", order = 2)
    public void fullSelect() throws Exception {
        List<Customers> customers = this.getCustomerManager().select();
        print(customers, "SELECT * FROM Customers");
    }

}
