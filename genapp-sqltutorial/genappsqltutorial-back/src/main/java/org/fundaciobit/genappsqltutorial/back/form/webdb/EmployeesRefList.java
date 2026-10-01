
package org.fundaciobit.genappsqltutorial.back.form.webdb;

import java.util.List;
import javax.ejb.EJB;
import org.springframework.stereotype.Component;

import org.fundaciobit.genapp.common.StringKeyValue;
import org.fundaciobit.genapp.common.query.Field;
import org.fundaciobit.genapp.common.query.OrderBy;
import org.fundaciobit.genapp.common.query.Select;
import org.fundaciobit.genapp.common.query.Where;

import org.fundaciobit.genappsqltutorial.ejb.EmployeesService;
import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genappsqltutorial.model.fields.EmployeesFields;
import org.fundaciobit.genapp.common.web.controller.RefListBase;

/**
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * @author GenApp
 * 
 */
@Component
public class EmployeesRefList extends RefListBase
    implements EmployeesFields {

  @EJB(mappedName = EmployeesService.JNDI_NAME)
  private EmployeesService employeesEjb;

  public EmployeesRefList(EmployeesRefList __clone) {
    super(__clone);
    this.employeesEjb = __clone.employeesEjb;
  }
  public EmployeesRefList() {
    setSelects(new Select<?>[] { LASTNAME.select, FIRSTNAME.select });
  }
  public List<StringKeyValue> getReferenceList(Field<?> keyField, Where where, OrderBy ... orderBy) throws I18NException {
    Select<StringKeyValue> select =  new org.fundaciobit.genapp.common.query.SelectMultipleStringKeyValue(keyField.select, getSeparator(), getSelects());
    List<StringKeyValue> list = employeesEjb.executeQuery(select, where, (orderBy==null || orderBy.length == 0) ? getOrderBy() : orderBy);
    return list;
  }
}
