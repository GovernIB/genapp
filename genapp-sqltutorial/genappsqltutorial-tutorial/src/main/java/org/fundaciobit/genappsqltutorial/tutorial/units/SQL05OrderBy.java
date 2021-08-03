package org.fundaciobit.genappsqltutorial.tutorial.units;

import java.util.List;

import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genapp.common.query.OrderBy;
import org.fundaciobit.genapp.common.query.OrderType;
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
        title = "SQL ORDER BY Keyword",
        description = "The ORDER BY keyword is used to sort the result-set in ascending or descending order.\r\n"
                + "The ORDER BY keyword sorts the records in ascending order by default. To sort the records in descending order, use the DESC keyword.",
        url = "https://www.w3schools.com/sql/sql_orderby.asp")
public class SQL05OrderBy extends AbstractUnit {

    @TutorialPart(
            title = "ORDER BY Example",
            description = "The following SQL statement selects all customers from the \"Customers\" table, sorted by the \"Country\" column:",
            sql = "SELECT * FROM Customers ORDER BY Country;",
            order = 1)
    public void orderby() throws I18NException, Exception {
        final OrderBy orderBy = new OrderBy(CustomersFields.COUNTRY);
        List<Customers> orderByCustomers = this.getCustomerManager().select(orderBy);
        print(orderByCustomers);
    }

    @TutorialPart(
            title = "ORDER BY DESC Example",
            description = "The following SQL statement selects all customers from the \"Customers\" table, sorted DESCENDING by the \"Country\" column:",
            sql = "SELECT * FROM Customers ORDER BY Country DESC;",
            order = 2)
    public void orderbyDesc() throws I18NException, Exception {
        final OrderBy orderBy = new OrderBy(CustomersFields.COUNTRY, OrderType.DESC);
        List<Customers> orderByCustomers = this.getCustomerManager().select(orderBy);
        print(orderByCustomers);
    }

    @TutorialPart(
            title = "ORDER BY Several Columns Example",
            description = "The following SQL statement selects all customers from the \"Customers\" table, sorted by the \"Country\" and the \"CustomerName\" column. This means that it orders by Country, but if some rows have the same Country, it orders them by CustomerName:",
            sql = "SELECT * FROM Customers ORDER BY Country, CustomerName; ",
            order = 3)
    public void orderbyseveralcolumnsv1() throws I18NException, Exception {
        final OrderBy orderBy1 = new OrderBy(CustomersFields.COUNTRY);
        final OrderBy orderBy2 = new OrderBy(CustomersFields.CUSTOMERNAME);
        List<Customers> orderByCustomers = this.getCustomerManager().select(orderBy1, orderBy2);
        print(orderByCustomers);
    }

    @TutorialPart(
            title = "ORDER BY Several Columns Example 2",
            description = "The following SQL statement selects all customers from the \"Customers\" table, sorted ascending by the \"Country\" and descending by the \"CustomerName\" column:",
            sql = "SELECT * FROM Customers ORDER BY Country ASC, CustomerName DESC;",
            order = 4)
    public void orderbyseveralcolumnsv2() throws I18NException, Exception {
        final OrderBy orderBy1 = new OrderBy(CustomersFields.COUNTRY, OrderType.ASC);
        final OrderBy orderBy2 = new OrderBy(CustomersFields.CUSTOMERNAME, OrderType.DESC);
        List<Customers> orderByCustomers = this.getCustomerManager().select(orderBy1, orderBy2);
        print(orderByCustomers);
    }

}
