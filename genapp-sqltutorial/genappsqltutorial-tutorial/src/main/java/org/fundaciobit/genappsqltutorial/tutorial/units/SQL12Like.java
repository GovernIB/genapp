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
        title = "SQL LIKE Operator",
        description = "The LIKE operator is used in a WHERE clause to search for a specified pattern in a column."
                + "\n" + "There are two wildcards often used in conjunction with the LIKE operator:"
                + "\n" + "- The percent sign (%) represents zero, one, or multiple characters"
                + "\n" + "- The underscore sign (_) represents one, single character" + "\n"
                + "The percent sign and the underscore can also be used in combinations!",
        url = "https://www.w3schools.com/sql/sql_like.asp")
public class SQL12Like extends AbstractUnit {

    @TutorialPart(
            title = "LIKE Example: starts with 'a'",
            description = "The following SQL statement selects all customers with a CustomerName starting with \"a\":",
            sql = "SELECT * FROM Customers WHERE CustomerName LIKE 'a%'",
            order = 1)
    public void likeStartsWith() throws I18NException, Exception {
        List<Customers> list;
        list = getCustomerManager().select(CustomersFields.CUSTOMERNAME.like("a%"));
        print(list);
    }

    @TutorialPart(
            title = "LIKE Example: ends with 'a'",
            description = "The following SQL statement selects all customers with a CustomerName ending with \"a\":",
            sql = "SELECT * FROM Customers WHERE CustomerName LIKE '%a'",
            order = 2)
    public void likeEndsWith() throws I18NException, Exception {
        List<Customers> list;
        list = getCustomerManager().select(CustomersFields.CUSTOMERNAME.like("%a"));
        print(list);
    }

    @TutorialPart(
            title = "LIKE Example: with substring",
            description = "The following SQL statement selects all customers with a CustomerName that have \"or\" in any position:",
            sql = "SELECT * FROM Customers WHERE CustomerName LIKE '%or%';",
            order = 3)
    public void likeWithSubstring() throws I18NException, Exception {
        List<Customers> list;
        list = getCustomerManager().select(CustomersFields.CUSTOMERNAME.like("%or%"));
        print(list);
    }

    @TutorialPart(
            title = "LIKE Example: with char",
            description = "The following SQL statement selects all customers with a CustomerName that have \"r\" in the second position:",
            sql = "SELECT * FROM Customers WHERE CustomerName LIKE '_r%'",
            order = 4)
    public void likeWithChar() throws I18NException, Exception {
        List<Customers> list;
        list = getCustomerManager().select(CustomersFields.CUSTOMERNAME.like("_r%"));
        print(list);
    }

    @TutorialPart(
            title = "LIKE Example: with al least 3 char",
            description = "The following SQL statement selects all customers with a CustomerName that starts with \"a\" and are at least 3 characters in length:",
            sql = "SELECT * FROM Customers WHERE CustomerName LIKE 'a__%'",
            order = 5)
    public void likeWith3Chars() throws I18NException, Exception {
        List<Customers> list;
        list = getCustomerManager().select(CustomersFields.CUSTOMERNAME.like("a__%"));
        print(list);
    }

    @TutorialPart(
            title = "LIKE Example: with start and end",
            description = "The following SQL statement selects all customers with a ContactName that starts with \"a\" and ends with \"s\":",
            sql = "SELECT * FROM Customers WHERE CustomerName LIKE 'a%a'",
            order = 6)
    public void likeWithStartEnd() throws I18NException, Exception {
        List<Customers> list;
        list = getCustomerManager().select(CustomersFields.CUSTOMERNAME.like("a%s"));
        print(list);
    }

    @TutorialPart(
            title = "LIKE Example: \"Not Like\"",
            description = "The following SQL statement selects all customers with a CustomerName that does NOT start with \"a\":",
            sql = "SELECT * FROM Customers WHERE CustomerName NOT LIKE 'a%'",
            order = 7)
    public void likeNot() throws I18NException, Exception {
        List<Customers> list;
        list = getCustomerManager().select(CustomersFields.CUSTOMERNAME.notLike("a%"));
        print(list);
    }

}
