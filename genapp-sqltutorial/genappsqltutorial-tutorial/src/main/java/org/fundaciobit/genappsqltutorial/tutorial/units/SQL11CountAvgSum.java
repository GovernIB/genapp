package org.fundaciobit.genappsqltutorial.tutorial.units;

import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genappsqltutorial.model.fields.ProductsFields;
import org.fundaciobit.genappsqltutorial.tutorial.utils.AbstractUnit;
import org.fundaciobit.genappsqltutorial.tutorial.utils.annotations.TutorialPart;
import org.fundaciobit.genappsqltutorial.tutorial.utils.annotations.TutorialUnit;

/**
 * 
 * @author anadal
 *
 */
@TutorialUnit(
        title = "SQL COUNT(), AVG() and SUM() Functions",
        description = "The COUNT() function returns the number of rows that matches a specified criterion.\n"
                + "The AVG() function returns the average value of a numeric column.\n"
                + "The SUM() function returns the total sum of a numeric column.\n",
        url = "https://www.w3schools.com/sql/sql_count_avg_sum.asp")
public class SQL11CountAvgSum extends AbstractUnit {

    @TutorialPart(
            title = "COUNT() Example",
            description = "The following SQL statement finds the number of products:\n"
                    + "Note: NULL values are not counted.",
            sql = "SELECT COUNT(ProductID)  FROM Products;",
            order = 1)
    public void countExample() throws I18NException, Exception {
        Long count;
        count = getProductsManager().count(ProductsFields.PRODUCTID.isNotNull());
        print(count, "Total");
    }

    @TutorialPart(
            title = "AVG() Example",
            description = "The following SQL statement finds the average price of all products:",
            sql = "SELECT AVG(Price)  FROM Products;",
            order = 2)
    public void avgExample() throws I18NException, Exception {
        Double avgPrice;
        avgPrice = getProductsManager().avg(ProductsFields.PRICE, null);
        print(avgPrice, "Average");
    }

    @TutorialPart(
            title = "SUM() Example",
            description = "The following SQL statement finds the sum of the \"Quantity\" fields in the \"OrderDetails\" table:",
            sql = "SELECT SUM(Quantity) FROM OrderDetails;",
            order = 3)
    public void sumExample() throws I18NException, Exception {
        // Sum of Integer Fields: BigInteger, Byte, Long, Short, Integer
        Long sumInt;
        sumInt = getOrderDetailsManager().sumInteger(ProductsFields.PRODUCTID, null);
        print(sumInt, "Integer Sum");

        // Sum of Decimal Fields: Double, Float, BigDecimal
        Double sumDec;
        sumDec = getOrderDetailsManager().sumDecimal(ProductsFields.PRICE, null);
        print(sumDec, "Decimal Sum");

    }

    @TutorialPart(
            title = "Count() Example with \"where\"",
            description = "The following SQL statement return the number of records that have the Price value set to 18:",
            sql = "SELECT Count(*) FROM Products WHERE Price=18;",
            order = 4)
    public void countExampleWithWhere() throws I18NException, Exception {
        Long count;
        count = getProductsManager().count(ProductsFields.PRICE.equal(18.0));
        print(count, "#Price=18");
    }

}
