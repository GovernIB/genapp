
package org.fundaciobit.genappsqltutorial.back.form.webdb;

import java.util.List;
import javax.ejb.EJB;
import org.springframework.stereotype.Component;

import org.fundaciobit.genapp.common.StringKeyValue;
import org.fundaciobit.genapp.common.query.Field;
import org.fundaciobit.genapp.common.query.OrderBy;
import org.fundaciobit.genapp.common.query.Select;
import org.fundaciobit.genapp.common.query.Where;

import org.fundaciobit.genappsqltutorial.ejb.SuppliersService;
import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genappsqltutorial.model.fields.SuppliersFields;
import org.fundaciobit.genapp.common.web.controller.RefListBase;

/**
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * @author GenApp
 * 
 */
@Component
public class SuppliersRefList extends RefListBase
    implements SuppliersFields {

  @EJB(mappedName = SuppliersService.JNDI_NAME)
  private SuppliersService suppliersEjb;

  public SuppliersRefList(SuppliersRefList __clone) {
    super(__clone);
    this.suppliersEjb = __clone.suppliersEjb;
  }
  public SuppliersRefList() {
    setSelects(new Select<?>[] { SUPPLIERNAME.select });
  }
  public List<StringKeyValue> getReferenceList(Field<?> keyField, Where where, OrderBy ... orderBy) throws I18NException {
    Select<StringKeyValue> select =  new org.fundaciobit.genapp.common.query.SelectMultipleStringKeyValue(keyField.select, getSeparator(), getSelects());
    List<StringKeyValue> list = suppliersEjb.executeQuery(select, where, (orderBy==null || orderBy.length == 0) ? getOrderBy() : orderBy);
    return list;
  }
}
