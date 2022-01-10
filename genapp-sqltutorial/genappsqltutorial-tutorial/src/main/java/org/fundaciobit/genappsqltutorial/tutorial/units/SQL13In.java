package org.fundaciobit.genappsqltutorial.tutorial.units;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genapp.common.query.SubQuery;
import org.fundaciobit.genappsqltutorial.model.entity.Customers;
import org.fundaciobit.genappsqltutorial.model.entity.Suppliers;
import org.fundaciobit.genappsqltutorial.model.fields.CustomersFields;
import org.fundaciobit.genappsqltutorial.model.fields.SuppliersFields;
import org.fundaciobit.genappsqltutorial.tutorial.utils.AbstractUnit;
import org.fundaciobit.genappsqltutorial.tutorial.utils.annotations.TutorialPart;
import org.fundaciobit.genappsqltutorial.tutorial.utils.annotations.TutorialUnit;

/**
 * 
 * @author anadal
 *
 */
@TutorialUnit(
        title = "SQL IN Operator",
        description = "The IN operator allows you to specify multiple values in a WHERE clause.\n"
                + "The IN operator is a shorthand for multiple OR conditions.",
        url = "https://www.w3schools.com/sql/sql_in.asp")
public class SQL13In extends AbstractUnit {

    @TutorialPart(
            title = "IN Operator Example: 'in array'",
            description = "The following SQL statement selects all customers that are located in \"Germany\", \"France\" or \"UK\":",
            sql = "SELECT * FROM Customers WHERE Country IN ('Germany', 'France', 'UK');",
            order = 1)
    public void inArray() throws I18NException, Exception {

        final String[] countries = { "Germany", "France", "UK" };
        List<Customers> list;
        list = getCustomerManager().select(CustomersFields.COUNTRY.in(countries));
        print(list);
    }

    @TutorialPart(
            title = "IN Operator Example: 'in collection'",
            description = "The following SQL statement selects all customers that are located in \"Germany\", \"France\" or \"UK\":",
            sql = "SELECT * FROM Customers WHERE Country IN ('Germany', 'France', 'UK');",
            order = 2)
    public void inCollection() throws I18NException, Exception {
        final Set<String> countries = new HashSet<String>();
        countries.add("Germany");
        countries.add("France");
        countries.add("UK");
        List<Customers> list;
        list = getCustomerManager().select(CustomersFields.COUNTRY.in(countries));
        print(list);
    }

    @TutorialPart(
            title = "IN Operator Example: 'not in'",
            description = "The following SQL statement selects all customers that are not located in \"Germany\", \"France\" or \"UK\":",
            sql = "SELECT * FROM Customers WHERE Country IN ('Germany', 'France', 'UK');",
            order = 3)
    public void notIn() throws I18NException, Exception {
        final String[] countries = { "Germany", "France", "UK" };
        List<Customers> list;
        list = getCustomerManager().select(CustomersFields.COUNTRY.notIn(countries));
        print(list);
    }

    @TutorialPart(
            title = "IN Operator Example: 'in select'",
            description = "The following SQL statement selects all customers that are from the same countries as the suppliers:",
            sql = "SELECT * FROM Customers WHERE Country IN (SELECT Country FROM Suppliers);",
            order = 4)
    public void inSelect() throws I18NException, Exception {
        SubQuery<Suppliers, String> subquery;
        subquery = getSuppliersManager().getSubQuery(SuppliersFields.COUNTRY, null);
        List<Customers> list;
        list = getCustomerManager().select(CustomersFields.COUNTRY.in(subquery));
        print(list);
    }

}
