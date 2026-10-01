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
        title = "SQL MIN() and MAX() Functions",
        description = "The MIN() function returns the smallest value of the selected column.\r\n"
                + "The MAX() function returns the largest value of the selected column.",
        url = "https://www.w3schools.com/sql/sql_min_max.asp")
public class SQL10MinMax extends AbstractUnit {

    @TutorialPart(
            title = "MIN() Example",
            description = "The following SQL statement finds the price of the cheapest product:",
            sql = "SELECT MIN(Price) AS SmallestPrice FROM Products;",
            order = 1)
    public void minExample() throws I18NException, Exception {
        Double minPrice;
        minPrice = super.getProductsManager().min(ProductsFields.PRICE, null);
        print(minPrice, "SmallestPrice");
    }

    @TutorialPart(
            title = "MAX() Example",
            description = "The following SQL statement finds the price of the most expensive product:",
            sql = "SELECT MAX(Price) AS LargestPrice FROM Products;",
            order = 2)
    public void maxExample() throws I18NException, Exception {
        Double minPrice;
        minPrice = super.getProductsManager().max(ProductsFields.PRICE, null);
        print(minPrice, "LargestPrice");
    }

}
