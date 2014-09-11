package ${package}.back.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ${package}.utils.Configuracio;

import org.apache.log4j.Logger;
import org.fundaciobit.genapp.common.StringKeyValue;
import org.fundaciobit.genapp.common.query.Field;
import org.fundaciobit.genapp.common.web.i18n.I18NUtils;
import org.fundaciobit.genapp.common.web.form.BaseFilterForm;


/**
 * 
 * @author anadal
 * 
 */
public class Utils {

  protected static final Logger log = Logger.getLogger(Utils.class);

  public static String getSortIcons(BaseFilterForm baseFilter, Field<?> theField) throws Exception {
    
    
    if (baseFilter == null) {
      throw new Exception("FilterForm is null.");
    }
    
    String field = theField.getJavaName();
    String code = theField.getFullName();
    String html;
  
    String newCode = baseFilter.getLabels().get(theField);
    if (newCode != null) {
      code = newCode;
    }
    
    if (baseFilter.isVisibleOrderBy()) {

      if (field.equals(baseFilter.getOrderBy())) {
  
        html = "<span style=\"cursor:row-resize\" onClick=\"javascript:executeOrderBy('"
            + field
            + "', "
            + !baseFilter.isOrderAsc()
            + ");\""
            + " title=\""
            + I18NUtils
                .tradueix(!baseFilter.isOrderAsc() ? "genapp.form.sort.asc" : "genapp.form.sort.desc")
            + "\" >" + I18NUtils.tradueix(code) + "<i class=\""
            + (baseFilter.isOrderAsc() ? "icon-chevron-up" : "icon-chevron-down")
            + "\"></i></span>";
      } else {
        html = "<span style=\"cursor:row-resize\" onclick=\"javascript:executeOrderBy('" + field + "', true);\" "
            + " title=\"" + I18NUtils.tradueix("genapp.form.sort.asc") + "\">"
            + I18NUtils.tradueix(code) + "<i class=\"icon-resize-vertical\"></i></span>";
      }
    } else {
      html = I18NUtils.tradueix(code);
    }
    
    return html;
  }

  public static String intArrayToString(int[] itemsPerPagines) {
    String str = Arrays.toString(itemsPerPagines);
    return str.substring(1, str.length() - 1).replace(" ", "");
  }

 
  
  public static final Comparator<StringKeyValue> STRINGKEYVALUECOMPARATOR = new Comparator<StringKeyValue>() {
    @Override
    public int compare(StringKeyValue o1, StringKeyValue o2) {
      return o1.value.compareToIgnoreCase(o2.value);
    }
  };
  
  
  public static void sortStringKeyValueList(List<StringKeyValue> listNovaFirma) {
    Collections.sort(listNovaFirma, STRINGKEYVALUECOMPARATOR);
  }

 
}
