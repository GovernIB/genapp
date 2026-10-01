package org.fundaciobit.genappsqltutorial.tutorial.units;

import java.util.List;

import org.fundaciobit.genapp.common.i18n.I18NException;
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
@TutorialUnit(
        title = "SQL NULL Values",
        description = "A field with a NULL value is a field with no value.\r\n"
                + "If a field in a table is optional, it is possible to insert a new record or update a record without adding a value to this field. Then, the field will be saved with a NULL value.\r\n"
                + "Note: A NULL value is different from a zero value or a field that contains spaces. A field with a NULL value is one that has been left blank during record creation!",
        url = "https://www.w3schools.com/sql/sql_null_values.asp")
public class SQL07NullValues extends AbstractUnit {

    @TutorialPart(
            title = "The IS NULL Operator",
            description = "The IS NULL operator is used to test for empty values (NULL values).The following SQL lists all \"customers\" with a NULL value in the \"Address\" field:",
            sql = "SELECT CustomerName, ContactName, Address FROM Customers WHERE Address IS NULL;",
            order = 1)
    public void addressNull() throws I18NException, Exception {
        List<Customers> customers;
        customers = super.getCustomerManager().select(CustomersFields.ADDRESS.isNull());
        print(customers);
    }

    @TutorialPart(
            title = "The IS NOT NULL Operator",
            description = "The IS NOT NULL operator is used to test for non-empty values (NOT NULL values). The following SQL lists all  \"customers\" with a value in the \"Address\" field:",
            sql = "SELECT CustomerName, ContactName, Address FROM Customers WHERE Address IS NOT NULL;",
            order = 1)
    public void addressNotNull() throws I18NException, Exception {
        List<Customers> customers;
        customers = super.getCustomerManager().select(CustomersFields.ADDRESS.isNotNull());
        print(customers);
    }

}
