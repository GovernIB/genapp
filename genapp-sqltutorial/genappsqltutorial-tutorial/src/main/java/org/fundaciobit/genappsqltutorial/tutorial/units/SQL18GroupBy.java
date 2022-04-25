package org.fundaciobit.genappsqltutorial.tutorial.units;

import java.util.List;

import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genapp.common.query.OrderBy;
import org.fundaciobit.genapp.common.query.OrderType;
import org.fundaciobit.genapp.common.query.SelectGroupBy;
import org.fundaciobit.genapp.common.query.SelectCount;
import org.fundaciobit.genapp.common.query.StringField;
import org.fundaciobit.genapp.common.query.selectcolumn.Select2Columns;
import org.fundaciobit.genapp.common.query.selectcolumn.Select2Values;
import org.fundaciobit.genappsqltutorial.model.fields.CustomersFields;
import org.fundaciobit.genappsqltutorial.model.fields.OrdersFields;
import org.fundaciobit.genappsqltutorial.model.fields.OrdersQueryPath;
import org.fundaciobit.genappsqltutorial.model.fields.ShippersFields;
import org.fundaciobit.genappsqltutorial.tutorial.utils.AbstractUnit;
import org.fundaciobit.genappsqltutorial.tutorial.utils.annotations.TutorialPart;
import org.fundaciobit.genappsqltutorial.tutorial.utils.annotations.TutorialUnit;

/**
 * 
 * @author anadal
 *
 */
@TutorialUnit(
        title = "The SQL GROUP BY Statement",
        description = "The GROUP BY statement groups rows that have the same values into summary rows, like \"find the number of customers in each country\".\r\n"
                + "\r\n"
                + "The GROUP BY statement is often used with aggregate functions (COUNT(), MAX(), MIN(), SUM(), AVG()) to group the result-set by one or more columns.",
        url = "https://www.w3schools.com/sql/sql_groupby.asp")
public class SQL18GroupBy extends AbstractUnit {

    @TutorialPart(
            title = "SQL GROUP BY Example 1",
            description = "The following SQL statement lists the number of customers in each country:",
            sql = "SELECT COUNT(CustomerID), Country\r\n" + "FROM Customers\r\n"
                    + "GROUP BY Country;",
            order = 1)
    public void exampleGroupBy1() throws I18NException, Exception {

        SelectGroupBy<String> countryGroupBy = new SelectGroupBy<String>(CustomersFields.COUNTRY);
        SelectCount customerCount = new SelectCount(CustomersFields.CUSTOMERID);

        Select2Columns<Long, String> s2c;
        s2c = new Select2Columns<Long, String>(customerCount, countryGroupBy);

        List<Select2Values<Long, String>> list = getCustomerManager().executeQuery(s2c, null);

        print(list, customerCount.getSelectString(), CustomersFields.COUNTRY.javaName);
    }

    @TutorialPart(
            title = "SQL GROUP BY Example 2",
            description = "The following SQL statement lists the number of customers in each country, sorted high to low:",
            sql = "SELECT COUNT(CustomerID), Country\r\n" + "FROM Customers\r\n"
                    + "GROUP BY Country\r\n" + "ORDER BY COUNT(CustomerID) DESC;",
            order = 2)
    public void exampleGroupBy2() throws I18NException, Exception {

        SelectGroupBy<String> countryGroupBy = new SelectGroupBy<String>(CustomersFields.COUNTRY);
        SelectCount customerCount = new SelectCount(CustomersFields.CUSTOMERID);

        Select2Columns<Long, String> s2c;
        s2c = new Select2Columns<Long, String>(customerCount, countryGroupBy);

        OrderBy orderby = new OrderBy(customerCount.getSelectString(), OrderType.DESC);

        List<Select2Values<Long, String>> list = getCustomerManager().executeQuery(s2c, orderby);

        print(list, customerCount.getSelectString(), CustomersFields.COUNTRY.javaName);
    }

    @TutorialPart(
            title = "GROUP BY With JOIN Example",
            description = "The following SQL statement lists the number of orders sent by each shipper:",
            sql = "SELECT Shippers.ShipperName, COUNT(Orders.OrderID) AS NumberOfOrders FROM Orders\n"
                    + "LEFT JOIN Shippers ON Orders.ShipperID = Shippers.ShipperID\n"
                    + "GROUP BY ShipperName;",
            order = 3)
    public void groupByWithJoin() throws I18NException, Exception {

        SelectGroupBy<String> shipperNameGroupBy;
        {
            StringField shipperName = new OrdersQueryPath().SHIPPERS().SHIPPERNAME();
            shipperNameGroupBy = new SelectGroupBy<String>(shipperName);
        }

        SelectCount orderCount = new SelectCount(OrdersFields.ORDERID);

        Select2Columns<String, Long> s2c;
        s2c = new Select2Columns<String, Long>(shipperNameGroupBy, orderCount);

        List<Select2Values<String, Long>> list = getOrdersManager().executeQuery(s2c, null);

        print(list, ShippersFields.SHIPPERNAME.javaName, orderCount.getSelectString());
    }

}
