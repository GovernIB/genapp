
package org.fundaciobit.demogenapp.back.form.webdb;

import java.util.List;
import javax.ejb.EJB;
import org.springframework.stereotype.Component;

import org.fundaciobit.genapp.common.StringKeyValue;
import org.fundaciobit.genapp.common.query.Field;
import org.fundaciobit.genapp.common.query.OrderBy;
import org.fundaciobit.genapp.common.query.Select;
import org.fundaciobit.genapp.common.query.Where;

import org.fundaciobit.demogenapp.ejb.AlumneService;
import org.fundaciobit.demogenapp.ejb.TraduccioService;
import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.demogenapp.model.fields.AlumneFields;
import org.fundaciobit.genapp.common.web.controller.RefListBase;

/**
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * @author GenApp
 * 
 */
@Component
public class AlumneRefList extends RefListBase
    implements AlumneFields {

  @EJB(mappedName = AlumneService.JNDI_NAME)
  private AlumneService alumneEjb;

  @EJB(mappedName = TraduccioService.JNDI_NAME)
  private TraduccioService traduccioEjb;
  public AlumneRefList(AlumneRefList __clone) {
    super(__clone);
    this.alumneEjb = __clone.alumneEjb;
    this.traduccioEjb = __clone.traduccioEjb;
  }
  public AlumneRefList() {
    setSelects(new Select<?>[] { NOM.select });
    addCampTraduible(TITOLACADEMICID.select);
  }
  public List<StringKeyValue> getReferenceList(Field<?> keyField, Where where, OrderBy ... orderBy) throws I18NException {
    Select<Long> _transSelect = checkTranslationFields();
    Select<StringKeyValue> select =  new org.fundaciobit.genapp.common.query.SelectMultipleStringKeyValue(keyField.select, getSeparator(), getSelects());
    List<StringKeyValue> list = alumneEjb.executeQuery(select, where, (orderBy==null || orderBy.length == 0) ? getOrderBy() : orderBy);
    if (_transSelect == null) {
      return list;
    }
    // key => TransID | value => alumneEjb_PK
    java.util.Map<String,String> keysMap = org.fundaciobit.genapp.common.utils.Utils.listToMapInverse(list);
    org.fundaciobit.genapp.common.query.Where _w1 = org.fundaciobit.demogenapp.model.fields.TraduccioFields.TRADUCCIOID.in(alumneEjb.executeQuery(_transSelect, where));
    List<org.fundaciobit.demogenapp.model.entity.Traduccio> traduccions = traduccioEjb.select(_w1);
    List<StringKeyValue> _list = new java.util.ArrayList<StringKeyValue>(traduccions.size());
    final String _lang = org.fundaciobit.genapp.common.web.i18n.I18NUtils.getLocale().getLanguage();
    for (org.fundaciobit.demogenapp.model.entity.Traduccio traduccio : traduccions) {
      org.fundaciobit.demogenapp.persistence.TraduccioJPA traduccioJPA = (org.fundaciobit.demogenapp.persistence.TraduccioJPA) traduccio;
      String key = keysMap.get(String.valueOf(traduccioJPA.getTraduccioID()));
      org.fundaciobit.demogenapp.persistence.TraduccioMapJPA _tm = traduccioJPA.getTraduccio(_lang);
      String value;
      if (_tm == null) {
          value = "NO_TRADUCCIO_PER_CODI_" + traduccio.getTraduccioID() + "_[" + _lang + "]";
      } else {
          value= _tm.getValor();
      }
      StringKeyValue skv = new StringKeyValue(key, value);
      _list.add(skv);
    }
    if (!_list.isEmpty()) {
      java.util.Collections.sort(_list, new org.fundaciobit.genapp.common.KeyValue.KeyValueComparator<String>());
    }
    return _list;

  }
}
