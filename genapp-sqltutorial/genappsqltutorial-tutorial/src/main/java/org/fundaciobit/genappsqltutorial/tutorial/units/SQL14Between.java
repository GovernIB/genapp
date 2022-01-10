package org.fundaciobit.genappsqltutorial.tutorial.units;

import java.util.List;

import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genappsqltutorial.model.entity.Products;
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
        title = "SQL BETWEEN Operator",
        description = "The BETWEEN operator selects values within a given range. The values can be numbers, text, or dates.\n"
                + "The BETWEEN operator is inclusive: begin and end values are included. ",
        url = "https://www.w3schools.com/sql/sql_between.asp")
public class SQL14Between extends AbstractUnit {

    @TutorialPart(
            title = "BETWEEN Example",
            description = "The following SQL statement selects all products with a price between 10 and 20:",
            sql = "SELECT * FROM Products WHERE Price BETWEEN 10 AND 20;",
            order = 1)
    public void between() throws I18NException, Exception {
        List<Products> list;
        list = getProductsManager().select(ProductsFields.PRICE.between(10.0,20.0));
        print(list);
    }
    
    
    @TutorialPart(
            title = "NOT BETWEEN Example",
            description = "To display the products outside the range of the previous example, use NOT BETWEEN:",
            sql = "SELECT * FROM Products WHERE Price NOT BETWEEN 10 AND 20;",
            order = 1)
    public void notBetween() throws I18NException, Exception {
        List<Products> list;
        list = getProductsManager().select(ProductsFields.PRICE.notBetween(10.0,20.0));
        print(list);
    }

}
