package org.fundaciobit.genappsqltutorial.tutorial.units;

import java.util.List;

import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genapp.common.query.SelectGroupBy;
import org.fundaciobit.genapp.common.query.Where;
import org.fundaciobit.genapp.common.query.Field;
import org.fundaciobit.genapp.common.query.OrderBy;
import org.fundaciobit.genapp.common.query.OrderType;
import org.fundaciobit.genapp.common.query.SelectCount;
import org.fundaciobit.genapp.common.query.selectcolumn.Select2Columns;
import org.fundaciobit.genapp.common.query.selectcolumn.Select2Values;
import org.fundaciobit.genappsqltutorial.model.fields.CustomersFields;
import org.fundaciobit.genappsqltutorial.model.fields.OrdersFields;
import org.fundaciobit.genappsqltutorial.model.fields.OrdersQueryPath;
import org.fundaciobit.genappsqltutorial.tutorial.utils.AbstractUnit;
import org.fundaciobit.genappsqltutorial.tutorial.utils.annotations.TutorialPart;
import org.fundaciobit.genappsqltutorial.tutorial.utils.annotations.TutorialUnit;

/**
 * 
 * @author anadal
 *
 */
@TutorialUnit(
        title = "The SQL HAVING Clause",
        description = "The HAVING clause was added to SQL because the WHERE keyword cannot be used with aggregate functions.",
        url = "https://www.w3schools.com/sql/sql_having.asp")
public class SQL19Having extends AbstractUnit {

    @TutorialPart(
            title = "SQL HAVING Example 1",
            description = "The following SQL statement lists the number of customers in each country. Only include countries with more than 5 customers:",
            sql = "SELECT COUNT(CustomerID), Country\r\n" + "FROM Customers\r\n"
                    + "GROUP BY Country\r\n" + "HAVING COUNT(CustomerID) > 5;",
            order = 1)
    public void exampleHaving1() throws I18NException, Exception {

        SelectCount customerIdCount = new SelectCount(CustomersFields.CUSTOMERID);

        SelectGroupBy<String> countryGroupBy = new SelectGroupBy<String>(CustomersFields.COUNTRY);

        Select2Columns<Long, String> s2c;
        s2c = new Select2Columns<Long, String>(customerIdCount, countryGroupBy);

        Where where = null;
        Where having = customerIdCount.having().greaterThan(5L);

        List<Select2Values<Long, String>> list = getCustomerManager().executeQuery(s2c, where,
                having);

        print(list, customerIdCount.getSelectString(), CustomersFields.COUNTRY.javaName);
    }

    @TutorialPart(
            title = "SQL HAVING Example 2",
            description = "The following SQL statement lists the number of customers in each country, sorted high to low (Only include countries with more than 5 customers):",
            sql = "SELECT COUNT(CustomerID), Country\r\n" + "FROM Customers\r\n"
                    + "GROUP BY Country\r\n" + "HAVING COUNT(CustomerID) > 5\r\n"
                    + "ORDER BY COUNT(CustomerID) DESC;",
            order = 2)
    public void exampleHaving2() throws I18NException, Exception {

        SelectCount customerIdCount = new SelectCount(CustomersFields.CUSTOMERID);

        SelectGroupBy<String> countryGroupBy = new SelectGroupBy<String>(CustomersFields.COUNTRY);

        Select2Columns<Long, String> s2c;
        s2c = new Select2Columns<Long, String>(customerIdCount, countryGroupBy);

        Where where = null;
        Where having = customerIdCount.having().greaterThan(5L);

        OrderBy orderBy = new OrderBy(customerIdCount.getSelectString(), OrderType.DESC);

        List<Select2Values<Long, String>> list = getCustomerManager().executeQuery(s2c, where,
                having, orderBy);

        print(list, customerIdCount.getSelectString(), CustomersFields.COUNTRY.javaName);
    }

    @TutorialPart(
            title = "SQL HAVING Example 3",
            description = "The following SQL statement lists the employees that have registered more than 10 orders:",
            sql = "SELECT Employees.LastName, COUNT(Orders.OrderID) AS NumberOfOrders\r\n"
                    + "FROM (Orders\r\n"
                    + "INNER JOIN Employees ON Orders.EmployeeID = Employees.EmployeeID)\r\n"
                    + "GROUP BY LastName\r\n" + "HAVING COUNT(Orders.OrderID) > 10;",
            order = 3)
    public void exampleHaving3() throws I18NException, Exception {

        Field<String> lastName = new OrdersQueryPath().EMPLOYEES().LASTNAME();
        SelectGroupBy<String> lastNameGroupBy = new SelectGroupBy<String>(lastName);

        SelectCount orderIdCount = new SelectCount(OrdersFields.ORDERID);

        Select2Columns<String, Long> s2c;
        s2c = new Select2Columns<String, Long>(lastNameGroupBy, orderIdCount);

        Where where = null;
        Where having = orderIdCount.having().greaterThan(10L);

        List<Select2Values<String, Long>> list;
        list = getOrdersManager().executeQuery(s2c, where, having);

        print(list, lastNameGroupBy.getSelectString(), orderIdCount.getSelectString());
    }

    
    
    @TutorialPart(
            title = "SQL HAVING Example 4",
            description = "The following SQL statement lists if the employees \"Davolio\" or \"Fuller\" have registered more than 25 orders:",
            sql = "SELECT Employees.LastName, COUNT(Orders.OrderID) AS NumberOfOrders\r\n"
                    + "FROM Orders\r\n"
                    + "INNER JOIN Employees ON Orders.EmployeeID = Employees.EmployeeID\r\n"
                    + "WHERE LastName = 'Davolio' OR LastName = 'Fuller'\r\n"
                    + "GROUP BY LastName\r\n"
                    + "HAVING COUNT(Orders.OrderID) > 25;",
            order = 4)
    public void exampleHaving4() throws I18NException, Exception {

        Field<String> lastName = new OrdersQueryPath().EMPLOYEES().LASTNAME();
        SelectGroupBy<String> lastNameGroupBy = new SelectGroupBy<String>(lastName);

        SelectCount orderIdCount = new SelectCount(OrdersFields.ORDERID);

        Select2Columns<String, Long> s2c;
        s2c = new Select2Columns<String, Long>(lastNameGroupBy, orderIdCount);

        Where where;
        {
           Where w1 = lastName.equal("Davolio");
           Where w2 = lastName.equal("Fuller");
           where = Where.OR(w1, w2);
        }
        
        Where having = orderIdCount.having().greaterThan(25L);

        List<Select2Values<String, Long>> list;
        list = getOrdersManager().executeQuery(s2c, where, having);

        print(list, lastNameGroupBy.getSelectString(), orderIdCount.getSelectString());
    }
}
