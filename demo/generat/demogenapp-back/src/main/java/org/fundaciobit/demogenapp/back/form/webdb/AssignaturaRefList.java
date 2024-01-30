
package org.fundaciobit.demogenapp.back.form.webdb;

import java.util.List;
import javax.ejb.EJB;
import org.springframework.stereotype.Component;

import org.fundaciobit.genapp.common.StringKeyValue;
import org.fundaciobit.genapp.common.query.Field;
import org.fundaciobit.genapp.common.query.OrderBy;
import org.fundaciobit.genapp.common.query.Select;
import org.fundaciobit.genapp.common.query.Where;

import org.fundaciobit.demogenapp.ejb.AssignaturaService;
import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.demogenapp.model.fields.AssignaturaFields;
import org.fundaciobit.genapp.common.web.controller.RefListBase;

/**
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * @author GenApp
 * 
 */
@Component
public class AssignaturaRefList extends RefListBase
    implements AssignaturaFields {

  @EJB(mappedName = AssignaturaService.JNDI_NAME)
  private AssignaturaService assignaturaEjb;

  public AssignaturaRefList(AssignaturaRefList __clone) {
    super(__clone);
    this.assignaturaEjb = __clone.assignaturaEjb;
  }
  public AssignaturaRefList() {
    setSelects(new Select<?>[] { NOM.select });
  }
  public List<StringKeyValue> getReferenceList(Field<?> keyField, Where where, OrderBy ... orderBy) throws I18NException {
    Select<StringKeyValue> select =  new org.fundaciobit.genapp.common.query.SelectMultipleStringKeyValue(keyField.select, getSeparator(), getSelects());
    List<StringKeyValue> list = assignaturaEjb.executeQuery(select, where, (orderBy==null || orderBy.length == 0) ? getOrderBy() : orderBy);
    return list;
  }
}
