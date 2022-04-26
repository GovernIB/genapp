package org.fundaciobit.genappsqltutorial.back.utils;

import java.util.List;

import org.fundaciobit.genapp.common.KeyValue;
import org.fundaciobit.genapp.common.query.SelectMultipleKeyValue;
import org.fundaciobit.genappsqltutorial.model.fields.CustomersFields;
import org.fundaciobit.genappsqltutorial.tutorial.utils.AbstractUnit;

/**
 * 
 * @author anadal
 *
 */
public class ExecuteSql extends AbstractUnit {
    public void execute() throws Exception {
        SelectMultipleKeyValue<String> select;
        select = new SelectMultipleKeyValue<String>(CustomersFields.CUSTOMERNAME.select,
                CustomersFields.CITY.select);
        List<KeyValue<String>> customersFields = this.getCustomerManager().executeQuery(select);
        print(CustomersFields.CUSTOMERNAME.javaName, CustomersFields.CITY.javaName,
                customersFields);
    }
}