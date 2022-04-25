package org.fundaciobit.genappsqltutorial.tutorial.units;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genapp.common.query.OrderBy;
import org.fundaciobit.genapp.common.query.SelectConstant;
import org.fundaciobit.genapp.common.query.Where;
import org.fundaciobit.genapp.common.query.selectcolumn.Select2Columns;
import org.fundaciobit.genapp.common.query.selectcolumn.Select2Values;
import org.fundaciobit.genapp.common.query.selectcolumn.Select4Columns;
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
        title = "SQL UNION Operator",
        description = "The UNION operator is used to combine the result-set of two or more SELECT statements.\r\n"
                + "   - Every SELECT statement within UNION must have the same number of columns\n"
                + "   - The columns must also have similar data types\n"
                + "   - The columns in every SELECT statement must also be in the same order\n",
        url = "https://www.w3schools.com/sql/sql_union.asp")
public class SQL17Union extends AbstractUnit {

    @TutorialPart(
            title = "SQL UNION Example",
            description = "The following SQL statement returns the cities (only distinct values) from both the \"Customers\" and the \"Suppliers\" table:",
            sql = "SELECT City FROM Customers\n" + "UNION\n"
                    + "SELECT City FROM Suppliers ORDER BY City;",
            order = 1)
    public void union() throws I18NException, Exception {

        Set<String> cities = new TreeSet<String>();

        cities.addAll(getCustomerManager().executeQuery(CustomersFields.CITY, null));

        cities.addAll(getSuppliersManager().executeQuery(SuppliersFields.CITY, null));

        print(new ArrayList<String>(cities), SuppliersFields.CITY.javaName);
    }

    @TutorialPart(
            title = "SQL UNION ALL Example",
            description = "The following SQL statement returns the cities (duplicate values also) from both the \"Customers\" and the \"Suppliers\" table:",
            sql = "SELECT City FROM Customers\n" + "UNION\n"
                    + "SELECT City FROM Suppliers ORDER BY City;",
            order = 2)
    public void unionAll() throws I18NException, Exception {

        List<String> cities = new ArrayList<String>();

        cities.addAll(getCustomerManager().executeQuery(CustomersFields.CITY, null));

        cities.addAll(getSuppliersManager().executeQuery(SuppliersFields.CITY, null));

        Collections.sort(cities);
        print(cities, SuppliersFields.CITY.javaName);

    }

    @TutorialPart(
            title = "SQL UNION With WHERE",
            description = "The following SQL statement returns the German cities (only distinct values) from both the \"Customers\" and the \"Suppliers\" table:",
            sql = "SELECT City, Country FROM Customers\n" + "WHERE Country='Germany'\n" + "UNION\n"
                    + "SELECT City, Country FROM Suppliers\n" + "WHERE Country='Germany'\n"
                    + "ORDER BY City;",
            order = 3)
    public void unionWithWhere() throws I18NException, Exception {

        Comparator<Select2Values<String, String>> c = new Comparator<Select2Values<String, String>>() {

            @Override
            public int compare(Select2Values<String, String> o1, Select2Values<String, String> o2) {
                if (o1 == null) {
                    if (o2 == null) {
                        return 0;
                    } else {
                        return "null".compareToIgnoreCase(o2.getValue1());
                    }
                } else {
                    if (o2 == null) {
                        return o1.getValue1().compareToIgnoreCase("null");
                    } else {
                        return o1.getValue1().compareToIgnoreCase(o2.getValue1());
                    }
                }

            }
        };

        Set<Select2Values<String, String>> cityCountrySet = new TreeSet<Select2Values<String, String>>(
                c);
        {
            Select2Columns<String, String> s2c;
            s2c = new Select2Columns<String, String>(CustomersFields.CITY.select,
                    CustomersFields.COUNTRY.select);

            cityCountrySet.addAll(getCustomerManager().executeQuery(s2c,
                    CustomersFields.COUNTRY.equal("Germany"), new OrderBy(CustomersFields.CITY)));
        }
        {
            Select2Columns<String, String> s2c;
            s2c = new Select2Columns<String, String>(SuppliersFields.CITY.select,
                    SuppliersFields.COUNTRY.select);

            cityCountrySet.addAll(getSuppliersManager().executeQuery(s2c,
                    SuppliersFields.COUNTRY.equal("Germany"), new OrderBy(SuppliersFields.CITY)));
        }

        print(new ArrayList<Select2Values<String, String>>(cityCountrySet),
                CustomersFields.CITY.javaName, CustomersFields.COUNTRY.javaName);
    }

    @TutorialPart(
            title = "SQL UNION ALL With WHERE",
            description = "The following SQL statement returns the German cities (duplicate values also) from both the \"Customers\" and the \"Suppliers\" table",
            sql = "SELECT City, Country FROM Customers\n" + "WHERE Country='Germany'\n"
                    + "UNION ALL\n" + "SELECT City, Country FROM Suppliers\n"
                    + "WHERE Country='Germany'\n" + "ORDER BY City;",
            order = 4)
    public void unionAllWithWhere() throws I18NException, Exception {

        List<Select2Values<String, String>> cityCountryList = new ArrayList<Select2Values<String, String>>();
        {
            Select2Columns<String, String> s2c;
            s2c = new Select2Columns<String, String>(CustomersFields.CITY.select,
                    CustomersFields.COUNTRY.select);
            Where w = CustomersFields.COUNTRY.equal("Germany");
            OrderBy o = new OrderBy(CustomersFields.CITY);

            cityCountryList.addAll(getCustomerManager().executeQuery(s2c, w, o));
        }
        {
            Select2Columns<String, String> s2c;
            s2c = new Select2Columns<String, String>(SuppliersFields.CITY.select,
                    SuppliersFields.COUNTRY.select);
            Where w = SuppliersFields.COUNTRY.equal("Germany");
            OrderBy o = new OrderBy(SuppliersFields.CITY);
            cityCountryList.addAll(getSuppliersManager().executeQuery(s2c, w, o));
        }

        Comparator<Select2Values<String, String>> c = new Comparator<Select2Values<String, String>>() {

            @Override
            public int compare(Select2Values<String, String> o1, Select2Values<String, String> o2) {
                if (o1 == null) {
                    if (o2 == null) {
                        return 0;
                    } else {
                        return "null".compareToIgnoreCase(o2.getValue1());
                    }
                } else {
                    if (o2 == null) {
                        return o1.getValue1().compareToIgnoreCase("null");
                    } else {
                        return o1.getValue1().compareToIgnoreCase(o2.getValue1());
                    }
                }

            }
        };

        Collections.sort(cityCountryList, c);
        print(new ArrayList<Select2Values<String, String>>(cityCountryList),
                CustomersFields.CITY.javaName, CustomersFields.COUNTRY.javaName);

    }

    @TutorialPart(
            title = "Another UNION Example",
            description = "The following SQL statement lists all customers and suppliers:",
            sql = "SELECT 'Customer' AS Type, ContactName, City, Country\r\n" + "FROM Customers\r\n"
                    + "UNION\r\n" + "SELECT 'Supplier', ContactName, City, Country\r\n"
                    + "FROM Suppliers;",
            order = 5)
    public void anotherUnionExample() throws I18NException, Exception {

        List<Select2Values<String, String>> cityCountryList = new ArrayList<Select2Values<String, String>>();
        {
            Select4Columns<String, String, String, String> s4c;
            s4c = new Select4Columns<String, String, String, String>(new SelectConstant("Customer"),
                    CustomersFields.CONTACTNAME.select, CustomersFields.CITY.select,
                    CustomersFields.COUNTRY.select);

            cityCountryList.addAll(getCustomerManager().executeQuery(s4c, null));
        }
        {
            Select4Columns<String, String, String, String> s4c;
            s4c = new Select4Columns<String, String, String, String>(new SelectConstant("Supplier"),
                    SuppliersFields.CONTACTNAME.select, SuppliersFields.CITY.select,
                    SuppliersFields.COUNTRY.select);

            cityCountryList.addAll(getSuppliersManager().executeQuery(s4c, null));
        }

        print(new ArrayList<Select2Values<String, String>>(cityCountryList), "Tipus",
                CustomersFields.CONTACTNAME.javaName, CustomersFields.CITY.javaName,
                CustomersFields.COUNTRY.javaName);

    }

}
