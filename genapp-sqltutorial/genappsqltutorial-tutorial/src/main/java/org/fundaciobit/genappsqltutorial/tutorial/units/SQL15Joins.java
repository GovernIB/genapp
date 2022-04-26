package org.fundaciobit.genappsqltutorial.tutorial.units;

import java.sql.Date;
import java.util.List;

import org.fundaciobit.genapp.common.KeyValue;
import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genapp.common.query.OrderBy;
import org.fundaciobit.genapp.common.query.OrderType;
import org.fundaciobit.genapp.common.query.SelectMultipleKeyValue;
import org.fundaciobit.genapp.common.query.selectcolumn.Select3Columns;
import org.fundaciobit.genapp.common.query.selectcolumn.Select3Values;
import org.fundaciobit.genapp.common.query.selectcolumn.Select4Columns;
import org.fundaciobit.genapp.common.query.selectcolumn.Select4Values;
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
        title = "'SQL JOINS'",
        description = "A JOIN clause is used to combine rows from two or more tables, based on a related column between them.",
        url = "https://www.w3schools.com/sql/sql_join.asp")
public class SQL15Joins extends AbstractUnit {
    @TutorialPart(
            title = "JOIN Example using KeyValue",
            description = "Notice that the \"CustomerID\" column in the \"Orders\" table refers to the \"CustomerID\" in the \"Customers\" table. The relationship between the two tables above is the \"CustomerID\" column.\n"
                    + "Then, we can create the following SQL statement (that contains an INNER JOIN), that selects records that have matching values in both tables:",
            sql = "SELECT Orders.OrderID, Customers.CustomerName, Orders.OrderDate\n"
                    + "FROM Orders\n"
                    + "INNER JOIN Customers ON Orders.CustomerID=Customers.CustomerID;",
            order = 1)
    public void joinKeyValue() throws I18NException, Exception {

        final String SEPARATOR = "|";
        SelectMultipleKeyValue<Long> smskv;
        smskv = new SelectMultipleKeyValue<Long>(OrdersFields.ORDERID.select, SEPARATOR,
                new OrdersQueryPath().CUSTOMERS().CUSTOMERNAME().select,
                OrdersFields.ORDERDATE.select);

        List<KeyValue<Long>> list;
        list = getOrdersManager().executeQuery(smskv);

        print(OrdersFields.ORDERID.javaName,
                CustomersFields.CUSTOMERNAME.javaName + SEPARATOR + OrdersFields.ORDERDATE.javaName,
                list);
    }

    @TutorialPart(
            title = "JOIN Example using Select3Columns",
            description = "Notice that the \"CustomerID\" column in the \"Orders\" table refers to the \"CustomerID\" in the \"Customers\" table. The relationship between the two tables above is the \"CustomerID\" column.\n"
                    + "Then, we can create the following SQL statement (that contains an INNER JOIN), that selects records that have matching values in both tables:",
            sql = "SELECT Orders.OrderID, Customers.CustomerName, Orders.OrderDate\n"
                    + "FROM Orders\n"
                    + "INNER JOIN Customers ON Orders.CustomerID=Customers.CustomerID;",
            order = 2)
    public void joinSelect3Columns() throws I18NException, Exception {

        Select3Columns<Long, String, Date> s3c;
        s3c = new Select3Columns<Long, String, Date>(OrdersFields.ORDERID.select,
                new OrdersQueryPath().CUSTOMERS().CUSTOMERNAME().select,
                OrdersFields.ORDERDATE.select);

        List<Select3Values<Long, String, Date>> list;
        list = getOrdersManager().executeQuery(s3c);

        print(list, OrdersFields.ORDERID.javaName, CustomersFields.CUSTOMERNAME.javaName,
                OrdersFields.ORDERDATE.javaName);
    }

    @TutorialPart(
            title = "JOIN Example using Select4Columns and sort",
            description = "Notice that the \"CustomerID\" column in the \"Orders\" table refers to the \"CustomerID\" in the \"Customers\" table. The relationship between the two tables above is the \"CustomerID\" column.\n"
                    + "Then, we can create the following SQL statement (that contains an INNER JOIN), that selects records that have matching values in both tables:",
            sql = "SELECT Orders.OrderID, Orders.CustomerID, Customers.CustomerName, Orders.OrderDate\n"
                    + "FROM Orders\n"
                    + "INNER JOIN Customers ON Orders.CustomerID=Customers.CustomerID;",
            order = 3)
    public void joinSelect4Columns() throws I18NException, Exception {

        Select4Columns<Long, Long, String, Date> s4c;
        s4c = new Select4Columns<Long, Long, String, Date>(OrdersFields.ORDERID.select,
                OrdersFields.CUSTOMERID.select,
                new OrdersQueryPath().CUSTOMERS().CUSTOMERNAME().select,
                OrdersFields.ORDERDATE.select);

        OrderBy orderby = new OrderBy(OrdersFields.CUSTOMERID, OrderType.ASC); 
        List<Select4Values<Long, Long, String, Date>> list;
        list = getOrdersManager().executeQuery(s4c, orderby);

        print(list, OrdersFields.ORDERID.javaName, OrdersFields.CUSTOMERID.javaName,
                CustomersFields.CUSTOMERNAME.javaName, OrdersFields.ORDERDATE.javaName);
    }

}
