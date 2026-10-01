package org.fundaciobit.genappsqltutorial.tutorial.units;

import java.util.List;

import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genapp.common.query.selectcolumn.Select2Columns;
import org.fundaciobit.genapp.common.query.selectcolumn.Select2Values;
import org.fundaciobit.genapp.common.query.selectcolumn.Select3Columns;
import org.fundaciobit.genapp.common.query.selectcolumn.Select3Values;
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
        title = "SQL INNER JOIN Keyword",
        description = "The INNER JOIN keyword selects records that have matching values in both tables.",
        url = "https://www.w3schools.com/sql/sql_join_inner.asp")
public class SQL16InnerJoin extends AbstractUnit {

    @TutorialPart(
            title = "SQL INNER JOIN Example",
            description = "The following SQL statement selects all orders with customer information:",
            sql = "SELECT Orders.OrderID, Customers.CustomerName\n" + "FROM Orders\n"
                    + "INNER JOIN Customers ON Orders.CustomerID = Customers.CustomerID; ",
            order = 1)
    public void joinSelect2Columns() throws I18NException, Exception {

        Select2Columns<Long, String> s2c;
        s2c = new Select2Columns<Long, String>(OrdersFields.ORDERID.select,
                new OrdersQueryPath().CUSTOMERS().CUSTOMERNAME().select);

        List<Select2Values<Long, String>> list;
        list = getOrdersManager().executeQuery(s2c);

        print(list, OrdersFields.ORDERID.javaName, CustomersFields.CUSTOMERNAME.javaName);
    }

    @TutorialPart(
            title = "JOIN Three Tables",
            description = "The following SQL statement selects all orders with customer and shipper information:",
            sql = "SELECT Orders.OrderID, Customers.CustomerName, Shippers.ShipperName\n"
                    + "FROM ((Orders\n"
                    + "INNER JOIN Customers ON Orders.CustomerID = Customers.CustomerID)\n"
                    + "INNER JOIN Shippers ON Orders.ShipperID = Shippers.ShipperID);",
            order = 2)
    public void joinSelect3Columns3Tables() throws I18NException, Exception {

        Select3Columns<Long, Long, Long> s3c;
        s3c = new Select3Columns<Long, Long, Long>(OrdersFields.ORDERID.select,
                new OrdersQueryPath().CUSTOMERS().CUSTOMERID().select,
                new OrdersQueryPath().SHIPPERID().select);

        List<Select3Values<Long, Long, Long>> list;
        list = getOrdersManager().executeQuery(s3c);

        print(list, OrdersFields.ORDERID.javaName, CustomersFields.CUSTOMERID.javaName,
                ShippersFields.SHIPPERID.javaName);
    }

}
