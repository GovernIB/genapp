package org.fundaciobit.genappsqltutorial.tutorial.units;

import java.util.List;

import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genapp.common.query.SelectCount;
import org.fundaciobit.genapp.common.query.SelectDistinct;
import org.fundaciobit.genappsqltutorial.model.fields.CustomersFields;
import org.fundaciobit.genappsqltutorial.tutorial.utils.AbstractUnit;
import org.fundaciobit.genappsqltutorial.tutorial.utils.annotations.TutorialPart;
import org.fundaciobit.genappsqltutorial.tutorial.utils.annotations.TutorialUnit;

/**
 *
 * 
 * @author anadal
 *
 */
@TutorialUnit(title = "SQL SELECT DISTINCT Statement", description = "The SELECT DISTINCT statement is used to return only distinct (different) values.\r\n"
        + "Inside a table, a column often contains many duplicate values; and sometimes you only want to list the different (distinct) values.", url = "https://www.w3schools.com/sql/sql_distinct.asp")
public class SQL02SelectDistinct extends AbstractUnit {

    @TutorialPart(title = "SELECT Example Without DISTINCT", description = "The following SQL statement selects all (including the duplicates) values from the \"Country\" column in the \"Customers\" table:", sql = "SELECT Country FROM Customers;", order = 1)
    public void selectWithoutDistinct() throws I18NException, Exception {
        List<String> allcountriesrepeat = this.getCustomerManager()
                .executeQuery(CustomersFields.COUNTRY, null);
        print(allcountriesrepeat, "Countries Without DISTINCT");
    }

    @TutorialPart(title = "SELECT DISTINCT Examples - With DISTINCT", description = "The following SQL statement selects only the DISTINCT values from the \"Country\" column in the \"Customers\" table:", sql = "SELECT DISTINCT Country FROM Customers;", order = 2)
    public void selectDistinct() throws I18NException, Exception {
        SelectDistinct<String> sd = new SelectDistinct<String>(CustomersFields.COUNTRY);
        List<String> distinctcountries = this.getCustomerManager().executeQuery(sd, null);
        print(distinctcountries, "Countries With DISTINCT");
    }

    @TutorialPart(title = "SELECT DISTINCT Examples - Count Countries", description = "The following SQL statement lists the number customer countries:", sql = "SELECT COUNT( Country) FROM Customers;", order = 3)
    public void countCountries() throws I18NException, Exception {
        SelectCount sc = new SelectCount(CustomersFields.COUNTRY);
        Long countdisctintcountries = this.getCustomerManager().executeQueryOne(sc, null);
        print(countdisctintcountries, "Count Counties ");
    }

    @TutorialPart(title = "SELECT DISTINCT Examples - Count Distinct Countries", description = "The following SQL statement lists the number of different (distinct) customer countries:", sql = "SELECT COUNT(DISTINCT Country) FROM Customers;", order = 4)
    public void countDistinctCountries() throws I18NException, Exception {
        SelectDistinct<String> sd = new SelectDistinct<String>(CustomersFields.COUNTRY);
        SelectCount sc = new SelectCount(sd);
        Long countdisctintcountries = this.getCustomerManager().executeQueryOne(sc, null);
        print(countdisctintcountries, "Count DISTINCT Counties ");
    }

}
