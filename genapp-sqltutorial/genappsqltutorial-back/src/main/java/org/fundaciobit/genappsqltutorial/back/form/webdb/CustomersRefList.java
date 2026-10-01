
package org.fundaciobit.genappsqltutorial.back.form.webdb;

import java.util.List;
import javax.ejb.EJB;
import org.springframework.stereotype.Component;

import org.fundaciobit.genapp.common.StringKeyValue;
import org.fundaciobit.genapp.common.query.Field;
import org.fundaciobit.genapp.common.query.OrderBy;
import org.fundaciobit.genapp.common.query.Select;
import org.fundaciobit.genapp.common.query.Where;

import org.fundaciobit.genappsqltutorial.ejb.CustomersService;
import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genappsqltutorial.model.fields.CustomersFields;
import org.fundaciobit.genapp.common.web.controller.RefListBase;

/**
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * @author GenApp
 * 
 */
@Component
public class CustomersRefList extends RefListBase
    implements CustomersFields {

  @EJB(mappedName = CustomersService.JNDI_NAME)
  private CustomersService customersEjb;

  public CustomersRefList(CustomersRefList __clone) {
    super(__clone);
    this.customersEjb = __clone.customersEjb;
  }
  public CustomersRefList() {
    setSelects(new Select<?>[] { CUSTOMERNAME.select });
  }
  public List<StringKeyValue> getReferenceList(Field<?> keyField, Where where, OrderBy ... orderBy) throws I18NException {
    Select<StringKeyValue> select =  new org.fundaciobit.genapp.common.query.SelectMultipleStringKeyValue(keyField.select, getSeparator(), getSelects());
    List<StringKeyValue> list = customersEjb.executeQuery(select, where, (orderBy==null || orderBy.length == 0) ? getOrderBy() : orderBy);
    return list;
  }
}
