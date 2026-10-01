
package org.fundaciobit.genappsqltutorial.back.form.webdb;

import java.util.List;
import javax.ejb.EJB;
import org.springframework.stereotype.Component;

import org.fundaciobit.genapp.common.StringKeyValue;
import org.fundaciobit.genapp.common.query.Field;
import org.fundaciobit.genapp.common.query.OrderBy;
import org.fundaciobit.genapp.common.query.Select;
import org.fundaciobit.genapp.common.query.Where;

import org.fundaciobit.genappsqltutorial.ejb.OrdersService;
import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genappsqltutorial.model.fields.OrdersFields;
import org.fundaciobit.genapp.common.web.controller.RefListBase;

/**
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * @author GenApp
 * 
 */
@Component
public class OrdersRefList extends RefListBase
    implements OrdersFields {

  @EJB(mappedName = OrdersService.JNDI_NAME)
  private OrdersService ordersEjb;

  public OrdersRefList(OrdersRefList __clone) {
    super(__clone);
    this.ordersEjb = __clone.ordersEjb;
  }
  public OrdersRefList() {
    setSelects(new Select<?>[] { CUSTOMERID.select, EMPLOYEEID.select });
  }
  public List<StringKeyValue> getReferenceList(Field<?> keyField, Where where, OrderBy ... orderBy) throws I18NException {
    Select<StringKeyValue> select =  new org.fundaciobit.genapp.common.query.SelectMultipleStringKeyValue(keyField.select, getSeparator(), getSelects());
    List<StringKeyValue> list = ordersEjb.executeQuery(select, where, (orderBy==null || orderBy.length == 0) ? getOrderBy() : orderBy);
    return list;
  }
}
