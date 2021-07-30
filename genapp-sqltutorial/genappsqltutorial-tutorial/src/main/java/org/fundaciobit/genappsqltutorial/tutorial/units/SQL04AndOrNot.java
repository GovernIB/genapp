package org.fundaciobit.genappsqltutorial.tutorial.units;

import java.util.List;

import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genapp.common.query.Where;
import org.fundaciobit.genappsqltutorial.model.entity.Customers;
import org.fundaciobit.genappsqltutorial.model.fields.CustomersFields;
import org.fundaciobit.genappsqltutorial.tutorial.utils.AbstractUnit;
import org.fundaciobit.genappsqltutorial.tutorial.utils.annotations.TutorialPart;
import org.fundaciobit.genappsqltutorial.tutorial.utils.annotations.TutorialUnit;

/**
 *
 * 
 * @author anadal
 *
 */
@TutorialUnit(title = "SQL AND, OR and NOT Operators", description = "The WHERE clause can be combined with AND, OR, and NOT operators.\r\n"
        + "\r\n"
        + "The AND and OR operators are used to filter records based on more than one condition:\r\n"
        + "\r\n"
        + "    The AND operator displays a record if all the conditions separated by AND are TRUE.\r\n"
        + "    The OR operator displays a record if any of the conditions separated by OR is TRUE.\r\n"
        + "\r\n"
        + "The NOT operator displays a record if the condition(s) is NOT TRUE.", url = " https://www.w3schools.com/sql/sql_and_or.asp")
public class SQL04AndOrNot extends AbstractUnit {

    @TutorialPart(title = "AND Example", description = "The following SQL statement selects all fields from \"Customers\" where country is \"Germany\" AND city is \"Berlin\":", sql = "SELECT * FROM Customers WHERE Country='Germany' AND City='Berlin';", order = 1)
    public void and() throws I18NException, Exception {

        Where w = Where.AND(CustomersFields.COUNTRY.equal("Germany"),
                CustomersFields.CITY.equal("Berlin"));
        List<Customers> andCustomers = this.getCustomerManager().select(w);
        print(andCustomers);
    }

    @TutorialPart(title = "OR Example", description = "The following SQL statement selects all fields from \"Customers\" where city is \"Berlin\" OR \"München\":", sql = "SELECT * FROM Customers WHERE City='Berlin' OR City='München';", order = 2)
    public void or1() throws I18NException, Exception {
        Where w = Where.OR(CustomersFields.CITY.equal("Berlin"),
                CustomersFields.CITY.equal("München"));
        List<Customers> orCustomers = this.getCustomerManager().select(w);
        print(orCustomers);
    }

    @TutorialPart(title = "OR Example", description = "The following SQL statement selects all fields from \"Customers\" where country is \"Germany\" OR \"Spain\":", sql = "SELECT * FROM Customers WHERE Country='Germany' OR Country='Spain';", order = 3)
    public void or2() throws I18NException, Exception {
        Where w = Where.OR(CustomersFields.COUNTRY.equal("Germany"),
                CustomersFields.COUNTRY.equal("Spain"));
        List<Customers> orCustomers = this.getCustomerManager().select(w);
        print(orCustomers);
    }

    @TutorialPart(title = "NOT Example v1", description = "The following SQL statement selects all fields from \"Customers\" where country is NOT \"Germany\":", sql = "SELECT * FROM Customers WHERE NOT Country='Germany';", order = 4)
    public void not1() throws I18NException, Exception {

        Where w = CustomersFields.COUNTRY.notEqual("Germany");
        List<Customers> notv1Customers = this.getCustomerManager().select(w);
        print(notv1Customers);
    }

    @TutorialPart(title = "NOT Example v2", description = "The following SQL statement selects all fields from \"Customers\" where country is NOT \"Germany\":", sql = "SELECT * FROM Customers WHERE NOT Country='Germany';", order = 5)
    public void not2() throws I18NException, Exception {
        Where w = CustomersFields.COUNTRY.notIn(new String[] { "Germany" });
        List<Customers> notv2Customers = this.getCustomerManager().select(w);
        print(notv2Customers);
    }

    @TutorialPart(title = "Combining AND, OR and NOT", description = "The following SQL statement selects all fields from \"Customers\" where country is \"Germany\" AND city must be \"Berlin\" OR \"München\" (use parenthesis to form complex expressions):", sql = "SELECT * FROM Customers WHERE Country='Germany' AND (City='Berlin' OR City='München');", order = 5)
    public void combiningAndOrNot1() throws I18NException, Exception {

        Where wOr = Where.OR(CustomersFields.CITY.equal("Berlin"),
                CustomersFields.CITY.equal("München"));
        Where wAnd = Where.AND(CustomersFields.COUNTRY.notEqual("Germany"), wOr);
        List<Customers> customers = this.getCustomerManager().select(wAnd);
        print(customers);
    }

    @TutorialPart(title = "Combining AND, OR and NOT", description = "The following SQL statement selects all fields from \"Customers\" where country is NOT \"Germany\" and NOT \"USA\":", sql = "SELECT * FROM Customers  WHERE NOT Country='Germany' AND NOT Country='USA';", order = 6)
    public void combiningAndOrNot2() throws I18NException, Exception {
        Where w1 = CustomersFields.COUNTRY.notEqual("Germany");
        Where w2 = CustomersFields.COUNTRY.notEqual("USA");
        Where w = Where.AND(w1, w2);
        List<Customers> customers = this.getCustomerManager().select(w);
        print(customers);
    }

}
