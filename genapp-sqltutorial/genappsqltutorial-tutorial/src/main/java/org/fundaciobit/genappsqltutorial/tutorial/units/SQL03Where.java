package org.fundaciobit.genappsqltutorial.tutorial.units;

import java.util.ArrayList;
import java.util.List;

import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genappsqltutorial.model.entity.Customers;
import org.fundaciobit.genappsqltutorial.model.entity.Products;
import org.fundaciobit.genappsqltutorial.model.fields.CustomersFields;
import org.fundaciobit.genappsqltutorial.model.fields.ProductsFields;
import org.fundaciobit.genappsqltutorial.tutorial.utils.AbstractUnit;
import org.fundaciobit.genappsqltutorial.tutorial.utils.annotations.TutorialPart;
import org.fundaciobit.genappsqltutorial.tutorial.utils.annotations.TutorialUnit;

/**
 * 
 * 
 * @author anadal
 *
 */
@TutorialUnit(
        title = "SQL WHERE Clause",
        description = "The WHERE clause is used to filter records.\n"
                + "It is used to extract only those records that fulfill a specified condition.",
        url = "https://www.w3schools.com/sql/sql_where.asp")
public class SQL03Where extends AbstractUnit {

    @TutorialPart(
            title = "WHERE Clause Example",
            description = "The following SQL statement selects all the customers from the country \"Mexico\", in the \"Customers\" table:",
            sql = "SELECT * FROM Customers WHERE Country='Mexico';",
            order = 1)
    public void where1() throws I18NException, Exception {
        List<Customers> mexicoCustomers = this.getCustomerManager()
                .select(CustomersFields.COUNTRY.equal("Mexico"));
        print(mexicoCustomers);
    }

    @TutorialPart(
            title = "Text Fields vs. Numeric Fields",
            description = "SQL requires single quotes around text values (most database systems will also allow double quotes).\r\n"
                    + "However, numeric fields should not be enclosed in quotes:",
            sql = "SELECT * FROM Customers WHERE CustomerID=1;",
            order = 2)
    public void where2a() throws I18NException, Exception {
        List<Customers> customerID1 = this.getCustomerManager()
                .select(CustomersFields.CUSTOMERID.equal(1L));
        print(customerID1);
    }

    @TutorialPart(
            title = "Text Fields vs. Numeric Fields",
            description = "SQL requires single quotes around text values (most database systems will also allow double quotes).\r\n"
                    + "However, numeric fields should not be enclosed in quotes:",
            sql = "SELECT * FROM Customers WHERE CustomerID=1;",
            order = 2)
    public void where2b() throws I18NException, Exception {
        Customers customerID1 = this.getCustomerManager().findByPrimaryKey(1L);
        print(customerID1);
    }

    @TutorialPart(
            title = "Operators in The WHERE Clause: Equal(=)",
            description = "",
            sql = " SELECT * FROM Products WHERE Price = 18;",
            order = 10)
    public void whereOperatorsEqual() throws I18NException, Exception {
        List<Products> products = this.getProductsManager().select(ProductsFields.PRICE.equal(18.0));
        print(products);
    }

    @TutorialPart(
            title = "Operators in The WHERE Clause: Greater than (>)",
            description = "",
            sql = "SELECT * FROM Products WHERE Price > 30;",
            order = 11)
    public void whereOperatorsGreaterThan() throws I18NException, Exception {
        List<Products> products = this.getProductsManager()
                .select(ProductsFields.PRICE.greaterThan(30.0));
        print(products);
    }

    @TutorialPart(
            title = "Operators in The WHERE Clause: Less than (<) ",
            description = "",
            sql = "SELECT * FROM Products WHERE Price < 30;",
            order = 12)
    public void whereOperatorsLessThan() throws I18NException, Exception {
        List<Products> products = this.getProductsManager()
                .select(ProductsFields.PRICE.lessThan(30.0));
        print(products);
    }

    @TutorialPart(
            title = "Operators in The WHERE Clause: Greater than or equal (>=)",
            description = "",
            sql = "SELECT * FROM Products WHERE Price >= 30;",
            order = 13)
    public void whereOperatorsGreaterThanOrEqual() throws I18NException, Exception {
        List<Products> products = this.getProductsManager()
                .select(ProductsFields.PRICE.greaterThanOrEqual(30.0));
        print(products);
    }

    @TutorialPart(
            title = "Operators in The WHERE Clause: Less than or equal (<=)",
            description = "",
            sql = "SELECT * FROM Products WHERE Price <= 30;",
            order = 14)
    public void whereOperatorsLessThanOrEqual() throws I18NException, Exception {
        List<Products> products = this.getProductsManager()
                .select(ProductsFields.PRICE.lessThanOrEqual(30.0));
        print(products);
    }

    @TutorialPart(
            title = "Operators in The WHERE Clause: Not Equal(<>)",
            description = "",
            sql = "SELECT * FROM Products WHERE Price <> 18",
            order = 15)
    public void whereOperatorsNotEqual() throws I18NException, Exception {
        List<Products> products = this.getProductsManager()
                .select(ProductsFields.PRICE.notEqual(18.0));
        print(products);
    }

    @TutorialPart(
            title = "Operators in The WHERE Clause: BETWEEN",
            description = "",
            sql = "SELECT * FROM Products WHERE Price BETWEEN 50 AND 60;",
            order = 16)
    public void whereOperatorsBETWEEN() throws I18NException, Exception {
        List<Products> products = this.getProductsManager()
                .select(ProductsFields.PRICE.between(50.0, 60.0));
        print(products);
    }

    @TutorialPart(
            title = "Operators in The WHERE Clause: LIKE",
            description = "",
            sql = "SELECT * FROM Customers WHERE City LIKE 's%';",
            order = 17)
    public void whereOperatorsLIKE() throws I18NException, Exception {
        List<Customers> customers = this.getCustomerManager()
                .select(CustomersFields.CITY.like("s%"));
        print(customers);
    }

    @TutorialPart(
            title = "Operators in The WHERE Clause: IN (array)",
            description = "",
            sql = "SELECT * FROM Customers WHERE City IN ('Paris','London')",
            order = 18)
    public void whereOperatorsInArray() throws I18NException, Exception {
        String[] cities = new String[] { "Paris", "London" };
        List<Customers> customers = this.getCustomerManager()
                .select(CustomersFields.CITY.in(cities));
        print(customers);
    }

    @TutorialPart(
            title = "Operators in The WHERE Clause: IN (collection)",
            description = "",
            sql = "SELECT * FROM Customers WHERE City IN ('Paris','London');",
            order = 19)
    public void whereOperatorsInCollection() throws I18NException, Exception {
        List<String> cities = new ArrayList<String>();
        cities.add("Paris");
        cities.add("London");
        List<Customers> customers = this.getCustomerManager()
                .select(CustomersFields.CITY.in(cities));
        print(customers);
    }

}
