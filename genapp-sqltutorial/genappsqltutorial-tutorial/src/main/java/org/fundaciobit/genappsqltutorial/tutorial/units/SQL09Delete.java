package org.fundaciobit.genappsqltutorial.tutorial.units;

import java.util.List;

import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genapp.common.query.Where;
import org.fundaciobit.genappsqltutorial.model.dao.IOrderDetailsManager;
import org.fundaciobit.genappsqltutorial.model.entity.OrderDetails;
import org.fundaciobit.genappsqltutorial.model.fields.OrderDetailsFields;
import org.fundaciobit.genappsqltutorial.tutorial.utils.AbstractUnit;
import org.fundaciobit.genappsqltutorial.tutorial.utils.annotations.TutorialPart;
import org.fundaciobit.genappsqltutorial.tutorial.utils.annotations.TutorialUnit;

/**
 * 
 * @author anadal
 *
 */
@TutorialUnit(
        title = "SQL DELETE Statement",
        description = "TThe DELETE statement is used to delete existing records in a table."
                + "Note: Be careful when deleting records in a table! Notice the WHERE clause in the DELETE statement."
                + " The WHERE clause specifies which record(s) should be deleted. "
                + "If you omit the WHERE clause, all records in the table will be deleted!",
        url = "https://www.w3schools.com/sql/sql_delete.asp")
public class SQL09Delete extends AbstractUnit {

    @TutorialPart(
            title = "SQL DELETE Example",
            description = "The following SQL statement deletes Order Details with quantity lower than 5",
            sql = "DELETE FROM OrderDetails where quantity < 5;",
            order = 1)
    public void deleteExample() throws I18NException, Exception {

        IOrderDetailsManager odm = getOrderDetailsManager();

        // Obtain items to delete
        Where where = OrderDetailsFields.QUANTITY.lessThan(5);
        List<OrderDetails> all = odm.select(where);
        print(all.size(), "Elements a esborrar");

        // Delete selected items
        int countDeleted = odm.delete(where);
        print(countDeleted, "Elements esborrats");

        Long countCurrent = odm.count(where);
        print(countCurrent, "Elements després de l'esborrat");

        // Restore to initial state
        for (OrderDetails od : all) {
            // Here 'update' is used because 'od' was a element before deletion 
            odm.update(od);
        }

        all = getOrderDetailsManager().select(where);
        print(all.size(), "Elements després de la restauració");

    }

    @TutorialPart(
            title = "Delete All Records",
            description = "It is possible to delete all rows in a table without deleting the table. This means that the table structure, attributes, and indexes will be intact: DELETE FROM table_name;. The following SQL statement deletes all rows in the \"OrderDetails\" table, without deleting the table:",
            sql = "DELETE FROM OrderDetails;",
            order = 2)
    public void deleteAllRecords() throws I18NException, Exception {

        IOrderDetailsManager odm = getOrderDetailsManager();

        // Obtain all items
        List<OrderDetails> all = odm.select();
        print(all.size(), "Elements inicials");

        // Delete all items
        int countDeleted = odm.delete((Where) null);
        print(countDeleted, "Elements esborrats");

        Long countCurrent = odm.count(null);
        print(countCurrent, "Elements després de l'esborrat");

        // Restore to initial state
        for (OrderDetails od : all) {
            // Here 'update' is used because 'od' was a element before deletion 
            odm.update(od);
        }

        all = getOrderDetailsManager().select();
        print(all.size(), "Elements després de la restauració");

    }

}
