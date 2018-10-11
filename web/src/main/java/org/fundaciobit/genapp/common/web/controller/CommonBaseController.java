package org.fundaciobit.genapp.common.web.controller;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.fundaciobit.genapp.common.IGenAppEntity;
import org.fundaciobit.genapp.common.KeyValue.KeyValueComparator;
import org.fundaciobit.genapp.common.StringKeyValue;
import org.fundaciobit.genapp.common.filesystem.FileSystemManager;
import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genapp.common.query.BigDecimalField;
import org.fundaciobit.genapp.common.query.BigIntegerField;
import org.fundaciobit.genapp.common.query.ByteField;
import org.fundaciobit.genapp.common.query.DoubleField;
import org.fundaciobit.genapp.common.query.Field;
import org.fundaciobit.genapp.common.query.FloatField;
import org.fundaciobit.genapp.common.query.GroupByItem;
import org.fundaciobit.genapp.common.query.GroupByValueItem;
import org.fundaciobit.genapp.common.query.ITableManager;
import org.fundaciobit.genapp.common.query.IntegerField;
import org.fundaciobit.genapp.common.query.LongField;
import org.fundaciobit.genapp.common.query.OrderBy;
import org.fundaciobit.genapp.common.query.ShortField;
import org.fundaciobit.genapp.common.query.Where;
import org.fundaciobit.genapp.common.utils.ModelGenerator;
import org.fundaciobit.genapp.common.web.HtmlUtils;
import org.fundaciobit.genapp.common.web.exportdata.DataExported;
import org.fundaciobit.genapp.common.web.exportdata.DataExporterManager;
import org.fundaciobit.genapp.common.web.exportdata.IDataExporter;
import org.fundaciobit.genapp.common.web.form.AdditionalField;
import org.fundaciobit.genapp.common.web.form.BaseFilterForm;
import org.fundaciobit.genapp.common.web.form.FilterFormData;
import org.fundaciobit.genapp.common.web.form.LogicForBaseFilterForm;
import org.fundaciobit.genapp.common.web.i18n.CustomDateI18NEditor;
import org.fundaciobit.genapp.common.web.i18n.I18NUtils;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author anadal
 *
 */
public abstract class CommonBaseController <I extends IGenAppEntity, PK extends Object> extends ModelGenerator {

  public static final List<StringKeyValue> EMPTY_STRINGKEYVALUE_LIST = Collections.unmodifiableList(new ArrayList<StringKeyValue>());
  
  public static final KeyValueComparator<String> STRINGKEYVALUE_COMPARATOR = new KeyValueComparator<String>();
  
  protected final Logger log = Logger.getLogger(getClass());

  /**
   * 
   * @param mav
   * @param pagina
   * @param itemsPerPagina
   * @param total
   */
  public void omplirDadesPaginacio(ModelAndView mav, Integer pagina, Integer itemsPerPagina,
      Long total) {
    
    if (itemsPerPagina == null || itemsPerPagina == -1) {
      // Tots els elements en una sola pàgina
      mav.addObject("totalPages", 1);
      mav.addObject("beginIndex", 1);
      mav.addObject("endIndex", 1);
      mav.addObject("currentIndex", 1);
    } else {
    
      int totalPages = (int) (total / itemsPerPagina);
  
      if (total % itemsPerPagina != 0) {
        totalPages = totalPages + 1;
      }
  
      int current = pagina;
      int begin = Math.max(1, current - itemsPerPagina);
      int end = Math.min(begin + 10, totalPages);
  
      mav.addObject("totalPages", totalPages);
      mav.addObject("beginIndex", begin);
      mav.addObject("endIndex", end);
      mav.addObject("currentIndex", current);
    }
  }
  
  
  
  
  
  public void initBinder(WebDataBinder binder) {
    // Allow for null values in date fields.
    binder.registerCustomEditor( java.sql.Date.class, new CustomDateI18NEditor(I18NUtils.i18NDateFormat, true ));

    // Allow for null values in time fields.
    binder.registerCustomEditor( java.sql.Time.class, new CustomDateI18NEditor(I18NUtils.i18NTimeFormat, true ));
    
    // Allow for null values in date-time fields.
    binder.registerCustomEditor( java.sql.Timestamp.class, new CustomDateI18NEditor(I18NUtils.i18NDateTimeFormat, true ));
   
    // tell spring to set empty values as null instead of empty string.
    binder.registerCustomEditor( String.class, new StringTrimmerEditor( true ));
  }
  
  
  public abstract String[] getArgumentsMissatge(Object pk, Throwable e);
  
  
  
  public abstract String getContextWeb();
  

  public String createMessageError(HttpServletRequest request, String code, Object pk) {
    String msg = I18NUtils.tradueix(code, getArgumentsMissatge(pk, null));
    HtmlUtils.saveMessageError(request, msg);
    return msg;
  }

  public String createMessageError(HttpServletRequest request, String code, Object pk,
      Throwable e) {
    String msg = I18NUtils.tradueix(code, getArgumentsMissatge(pk, e));
    HtmlUtils.saveMessageError(request, msg);
    return msg;
  }

  public String createMessageSuccess(HttpServletRequest request, String code, Object pk) {
    String msg = I18NUtils.tradueix(code, getArgumentsMissatge(pk, null));
    HtmlUtils.saveMessageSuccess(request, msg);
    return msg;
  }

  public String createMessageWarning(HttpServletRequest request, String code, Object pk) {
    String msg = I18NUtils.tradueix(code, getArgumentsMissatge(pk, null));
    HtmlUtils.saveMessageWarning(request, msg);
    return msg;
  }
  
  protected List<I> processarLlistat(ITableManager<I, PK> ejb,
      BaseFilterForm filterForm, int pagina,
      Where whereAdditionalCondition, ModelAndView mav) throws I18NException {
    if (filterForm == null) {
      throw new NullPointerException("FilterForm mai pot ser NULL !!!!");
    }
    
    FilterFormData ffd = LogicForBaseFilterForm.getFilterFormData(filterForm, ejb, whereAdditionalCondition);

    // FilterBy and GroupBy
    Where where = Where.AND(whereAdditionalCondition, ffd.getWhere());
    // Preload Data required by FilterBy or GroupBy in ModelAndView
    mapTomav(ffd.getMav(), mav);
    // Order By
    final OrderBy[] orderBy = ffd.getOrderBy();
    // Elements per pàgina
    final Integer itemsPerPage = filterForm.getItemsPerPage();

    log.debug("WHERE: " + (where == null ? null : where.toSQL()));

    // ============== CONSULTA
    List<I> items = null;
    Long total = null;

    if (pagina < 1) {
      pagina = 1;
    }
    
    if (itemsPerPage == null || itemsPerPage < 0) {
      pagina = 1;
      items = executeSelect(ejb, where, orderBy, null , 0); 
    } else {
      for (;;) {
        final int inici = (pagina - 1) * itemsPerPage;
        // Elements que apareixeram a la pàgina "pagina".
        items = executeSelect(ejb, where, orderBy, itemsPerPage, inici);
  
        // Numero total de registres amb aquell filtre
        total = ejb.count(where);
  
        if (items.size() == 0 && total > 0 && pagina > 1) {
          // Calcula la nova pàgina, ja que resulta que amb el nou filtre
          // ja no surten tants resultats i la pàgina actual ja no es pot omplir,
          // per lo qual hem de calcular la darrera pàgina amb elements
          pagina = (int) (total / itemsPerPage) + ((total % itemsPerPage) == 0L ? 0 : 1);
        } else {
          break;
        }
      }
    }

    omplirDadesPaginacio(mav, pagina, itemsPerPage, total);
    return items;
  }


  public List<I> executeSelect(ITableManager<I, PK> ejb, Where where,
      final OrderBy[] orderBy, final Integer itemsPerPage, final int inici)
      throws I18NException {
    if (itemsPerPage == null) {
      return ejb.select(where, orderBy);
    } else {
      return ejb.select(where, inici, itemsPerPage, orderBy);
    }
  }
  
  
  public void captureSearchByValueOfAdditionalFields(HttpServletRequest request,
      BaseFilterForm filterForm) {
    TreeMap<Integer, AdditionalField<?,?>> additionalFields = filterForm.getAdditionalFields();
    if (additionalFields != null) {
      for (Integer pos : additionalFields.keySet()) {
        
        AdditionalField<?,?> af = additionalFields.get(pos);
        Field<?> searchBy = af.getSearchBy(); 
        if (searchBy != null) {
          af.setSearchByValue(request.getParameter(searchBy.fullName));
          af.setSearchByValueFins(request.getParameter(searchBy.fullName + "Fins"));
          if (log.isDebugEnabled()) {
            log.debug("Cercant valor searchBy per ]" + searchBy.fullName  + "[ = " + af.getSearchByValue() );
          }
        }
      }
    }
  }
  
  /**
   * Retorna true si la cerca del Field es fa en rang (from,to). 
   * Un exemple serien Integer, Double, Short, Byte, BigDecimal i BigInteger)
   * @param f
   * @return
   */
  public static boolean isFieldSearchInRange(Field<?> f) {
    if (f instanceof IntegerField || f instanceof LongField
     || f instanceof DoubleField || f instanceof FloatField
     || f instanceof ByteField || f instanceof ShortField
     || f instanceof BigDecimalField || f instanceof BigIntegerField) {
      return true;
    } else {
      return false;
    }
  }
  

  

  public static void mapTomav(Map<String, Object> map, ModelAndView mav) {
    if (map != null) {
      for (Entry<String, Object> entry : map.entrySet()) {
        mav.addObject(entry.getKey(), entry.getValue());
      }
    }
  }
  
  
  public void fillValuesToGroupByItemsBoolean(String prefix,
      Map<Field<?>,GroupByItem> groupByItemsMap, Field<?> field) {
    
    Map<String, String> tmp = new HashMap<String, String>();
    
    tmp.put(null, prefix + ".");
    tmp.put("true", prefix + ".true");
    tmp.put("false", prefix + ".false");

    fillValuesToGroupByItems(tmp,groupByItemsMap, field, true);
  }
  
  
  
  public void fillValuesToGroupByItems(Map<String, String> tmp,
      Map<Field<?>,GroupByItem> groupByItemsMap, Field<?> field,
      boolean translate) {
    
    boolean isDebug = log.isDebugEnabled();

    // TODO això ha de cridar a CommonForm.getFieldToString()

    GroupByItem groupByItem = groupByItemsMap.get(field);
    if (isDebug) {
      log.info("groupByItem == " + groupByItem);
      log.info("field == " + field.fullName + "(" + field + ")");
      if (groupByItem == null) {
        
        Set<Field<?>> keys = groupByItemsMap.keySet();
        for (Field<?> field2 : keys) {
          if (field2 != null) {
            log.info("   + Field of map: " +  field2.fullName + "(" + field2 + ")");
          } else {
            log.info("   + Field of map: "  + field2 );
          }
        }
      }
    }

    if (groupByItem == null) {
      return;
    }

    String javaName = field.javaName;
    if (isDebug) {
      log.info("javaName.equals(groupByItem.getValue()) =>  " + javaName + " ==  " + groupByItem.getValue() + "????");
    }
    if (javaName.equals(groupByItem.getValue())) {        
      List<GroupByValueItem> items = groupByItem.getValues();
      for (GroupByValueItem groupByValueItem : items) {
        String codeLabel = groupByValueItem.getCodeLabel();
        String newValue;
        if (codeLabel == null || codeLabel.length() == 0) {
          newValue = null;
        } else {
          newValue = tmp.get(codeLabel);
          if (newValue == null) {            
            log.warn("Al cridar al mapping per la clau '" + codeLabel 
                + "' ha retornat un valor null.", new Exception());
            newValue = codeLabel;
          }
          if (translate) {
            newValue = I18NUtils.tradueix(newValue);
          }
        }
        groupByValueItem.setCodeLabel(newValue);
      }
      
      Collections.sort(items, GroupByValueItem.GROUPBYVALUEITEMCOMPARATOR);
    }
  }

  
  
  /**
   *
   * @param fitxers
   */
  protected void borrarFitxers(Set<Long> fitxers) {
    for (Long fitxerID : fitxers) {
      try {
        FileSystemManager.eliminarArxiu(fitxerID);
      } catch (Exception e) {
        // TODO: handle exception
        log.error(e.getMessage(), e);
      }
    }
  }
  

  public void exportData(HttpServletRequest request, HttpServletResponse response,
      String dataExporterID, BaseFilterForm filterForm, List<I> list,
      Field<?>[] allFields, Map<Field<?>,Map<String,String>> mapValuesByField, Field<?>[] primaryKeyFields) throws Exception {
    try {
      //log.debug(" Cridant a Export: TIPUS = " + dataExporterID);

      IDataExporter dataExporter = DataExporterManager.getByID(dataExporterID);
      
      if (dataExporter == null) {
         // TODO TRADUIR
        throw new Exception("No s'ha trobat cap exportador de dades " +
            "amb el següent identificador: " + dataExporterID);
      }

      DataExported exportFile = dataExporter.exportList(filterForm, list, allFields, mapValuesByField, primaryKeyFields);
      
      response.setContentType(exportFile.getContentType());
      response.setHeader("Content-Disposition", "inline; filename=\"" + exportFile.getFilename() + "\"");
      response.setContentLength((int) exportFile.getData().length);

      OutputStream output = response.getOutputStream();

      output.write(exportFile.getData());

      output.flush();

      output.close();

    } catch (Throwable e) {

      log.error(" Error exportant llistat: " + e.getMessage(), e);

      int pagina = (filterForm == null) ? 1 : filterForm.getPage();
      // TODO arreglar
      HtmlUtils.saveMessageError(request, "Error Exportant: " + e.getMessage());
      response.sendRedirect(request.getContextPath() + getContextWeb() + "/list/" + pagina);
    }
  }

 
  

}
