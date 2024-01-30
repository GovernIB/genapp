
package org.fundaciobit.demogenapp.back.form.webdb;

import java.util.List;
import javax.ejb.EJB;
import org.springframework.stereotype.Component;

import org.fundaciobit.genapp.common.StringKeyValue;
import org.fundaciobit.genapp.common.query.Field;
import org.fundaciobit.genapp.common.query.OrderBy;
import org.fundaciobit.genapp.common.query.Select;
import org.fundaciobit.genapp.common.query.Where;

import org.fundaciobit.demogenapp.ejb.AssignaturaAlumneService;
import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.demogenapp.model.fields.AssignaturaAlumneFields;
import org.fundaciobit.genapp.common.web.controller.RefListBase;

/**
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * @author GenApp
 * 
 */
@Component
public class AssignaturaAlumneRefList extends RefListBase
    implements AssignaturaAlumneFields {

  @EJB(mappedName = AssignaturaAlumneService.JNDI_NAME)
  private AssignaturaAlumneService assignaturaAlumneEjb;

  public AssignaturaAlumneRefList(AssignaturaAlumneRefList __clone) {
    super(__clone);
    this.assignaturaAlumneEjb = __clone.assignaturaAlumneEjb;
  }
  public AssignaturaAlumneRefList() {
    setSelects(new Select<?>[] { ALUMNEID.select, ASSIGNATURAID.select });
  }
  public List<StringKeyValue> getReferenceList(Field<?> keyField, Where where, OrderBy ... orderBy) throws I18NException {
    Select<StringKeyValue> select =  new org.fundaciobit.genapp.common.query.SelectMultipleStringKeyValue(keyField.select, getSeparator(), getSelects());
    List<StringKeyValue> list = assignaturaAlumneEjb.executeQuery(select, where, (orderBy==null || orderBy.length == 0) ? getOrderBy() : orderBy);
    return list;
  }
}
