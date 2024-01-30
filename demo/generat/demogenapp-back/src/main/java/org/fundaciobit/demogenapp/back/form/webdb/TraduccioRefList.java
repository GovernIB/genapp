
package org.fundaciobit.demogenapp.back.form.webdb;

import java.util.List;
import javax.ejb.EJB;
import org.springframework.stereotype.Component;

import org.fundaciobit.genapp.common.StringKeyValue;
import org.fundaciobit.genapp.common.query.Field;
import org.fundaciobit.genapp.common.query.OrderBy;
import org.fundaciobit.genapp.common.query.Select;
import org.fundaciobit.genapp.common.query.Where;

import org.fundaciobit.demogenapp.ejb.TraduccioService;
import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.demogenapp.model.fields.TraduccioFields;
import org.fundaciobit.genapp.common.web.controller.RefListBase;

/**
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * @author GenApp
 * 
 */
@Component
public class TraduccioRefList extends RefListBase
    implements TraduccioFields {

  @EJB(mappedName = TraduccioService.JNDI_NAME)
  private TraduccioService traduccioEjb;

  public TraduccioRefList(TraduccioRefList __clone) {
    super(__clone);
    this.traduccioEjb = __clone.traduccioEjb;
  }
  public TraduccioRefList() {
    setSelects(new Select<?>[] { TRADUCCIOID.select });
  }
  public List<StringKeyValue> getReferenceList(Field<?> keyField, Where where, OrderBy ... orderBy) throws I18NException {
    List<org.fundaciobit.demogenapp.model.entity.Traduccio> traduccions = traduccioEjb.select(where);
    List<StringKeyValue> _list = new java.util.ArrayList<StringKeyValue>(traduccions.size());
    final String _lang = org.fundaciobit.genapp.common.web.i18n.I18NUtils.getLocale().getLanguage();
    for (org.fundaciobit.demogenapp.model.entity.Traduccio traduccio : traduccions) {
      org.fundaciobit.demogenapp.persistence.TraduccioJPA traduccioJPA = (org.fundaciobit.demogenapp.persistence.TraduccioJPA) traduccio;
      String key = String.valueOf(traduccioJPA.getTraduccioID());
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
