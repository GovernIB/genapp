package org.fundaciobit.genapp.common.web.form;

import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genapp.common.query.BigDecimalField;
import org.fundaciobit.genapp.common.query.BigIntegerField;
import org.fundaciobit.genapp.common.query.BooleanField;
import org.fundaciobit.genapp.common.query.ByteField;
import org.fundaciobit.genapp.common.query.CustomField;
import org.fundaciobit.genapp.common.query.DateField;
import org.fundaciobit.genapp.common.query.DoubleField;
import org.fundaciobit.genapp.common.query.Field;
import org.fundaciobit.genapp.common.query.FieldHour;
import org.fundaciobit.genapp.common.query.FieldMonth;
import org.fundaciobit.genapp.common.query.FieldYear;
import org.fundaciobit.genapp.common.query.FloatField;
import org.fundaciobit.genapp.common.query.GroupByItem;
import org.fundaciobit.genapp.common.query.GroupByValueItem;
import org.fundaciobit.genapp.common.query.ITableManager;
import org.fundaciobit.genapp.common.query.IntegerField;
import org.fundaciobit.genapp.common.query.LongField;
import org.fundaciobit.genapp.common.query.OrderBy;
import org.fundaciobit.genapp.common.query.OrderType;
import org.fundaciobit.genapp.common.query.SelectGroupByAndCountForField;
import org.fundaciobit.genapp.common.query.SelectGroupByAndCountForHour;
import org.fundaciobit.genapp.common.query.SelectGroupByAndCountForYear;
import org.fundaciobit.genapp.common.query.SelectGroupByAndCountForYearMonth;
import org.fundaciobit.genapp.common.query.ShortField;
import org.fundaciobit.genapp.common.query.StringField;
import org.fundaciobit.genapp.common.query.TimeField;
import org.fundaciobit.genapp.common.query.TimestampField;
import org.fundaciobit.genapp.common.query.Where;
import org.fundaciobit.genapp.common.utils.Utils;

/**
 *
 * @author anadal
 *
 */
public class LogicForBaseFilterForm {
  
  protected final Logger log = Logger.getLogger(getClass());

  final BaseFilterForm filterForm;


  /**
   * @param filterForm
   */
  public LogicForBaseFilterForm(BaseFilterForm filterForm) {
    super();
    this.filterForm = filterForm;
  }

  // ======== Order By

  protected final OrderBy[] getQueryOrderBy() {
    if (filterForm.getOrderBy() == null) {
      return filterForm.getDefaultOrderBy();
    } else {
      // TODO AFEGIR ORDER BY per defecte
      return new OrderBy[] {
          new OrderBy(filterForm.getOrderBy(), filterForm.isOrderAsc() ? OrderType.ASC : OrderType.DESC)   
      };
    }
  }

  protected final Where getFilterWhere() {
    List<Where> __wheres = new ArrayList<Where>();
    
    
    TreeMap<Integer, AdditionalField<?,?>> additionalFields = filterForm.getAdditionalFields();
    
    for (Integer pos : additionalFields.keySet()) {
      
      AdditionalField<?,?> af = additionalFields.get(pos);
      Field<?> searchBy = af.getSearchBy(); 
      if (searchBy != null) {
        
        // Cerca
        if (searchBy instanceof StringField) {
          // Cerques per String  
          String _value = af.getSearchByValue();
          if (_value != null && _value.trim().length() != 0) {
            _value = _value.trim();
            __wheres.add(searchBy.like("%" + _value.replace(' ', '%') + "%"));
          }          
        } else {
           String _valueFrom = af.getSearchByValue();
           String _valueTo = af.getSearchByValueFins();
        
          // Només Cerques per numeros
          if ( (_valueFrom != null && _valueFrom.trim().length() != 0)  
            || (_valueTo != null && _valueTo.trim().length() != 0) ) {

            try {
              if ((searchBy instanceof LongField)) {
                __wheres.add(getWhere(Long.class, (Field<Number>)searchBy, _valueFrom,_valueTo));
              } else if (searchBy instanceof IntegerField) {
                __wheres.add(getWhere(Integer.class, (Field<Number>)searchBy, _valueFrom,_valueTo));
              } else if (searchBy instanceof ByteField) {
                __wheres.add(getWhere(Byte.class, (Field<Number>)searchBy, _valueFrom,_valueTo));
              } else if (searchBy instanceof FloatField) {
                __wheres.add(getWhere(Float.class, (Field<Number>)searchBy, _valueFrom,_valueTo));
              } else if (searchBy instanceof DoubleField) {
                __wheres.add(getWhere(Double.class, (Field<Number>)searchBy, _valueFrom,_valueTo));
              } else if (searchBy instanceof ShortField) {
                __wheres.add(getWhere(Short.class, (Field<Number>)searchBy, _valueFrom,_valueTo));
              } else if (searchBy instanceof BigIntegerField) {
                __wheres.add(getWhere(BigInteger.class, (Field<Number>)searchBy, _valueFrom,_valueTo));
              } else if (searchBy instanceof BigDecimalField) {
                __wheres.add(getWhere(BigDecimal.class, (Field<Number>)searchBy, _valueFrom,_valueTo));
              } else {
                log.warn("Només es por cercar sobre camps addicionals de tipus "
                    + "String o Numèric. Camp = " + searchBy.fullName, new Exception());
              }

            } catch (Throwable e) {
              log.error("Error intentant filtrar pel camp " + searchBy.fullName
                  + " amb el valors [FROM: {" + _valueFrom + "} | TO: {" + _valueTo  + "}]: "
                  + e.getMessage(), e);
              af.setSearchByValue(e.getMessage());
            }
          }
        }
      }
    }
    

    if (filterForm.getFilterByFields() != null) {
      for (Field<?> f : filterForm.getFilterByFields()) {
        

        // STRING
        if (f instanceof StringField) {
          String _value = (String) Utils.getValueOfJavaField(this.filterForm, f.javaName);
          if (_value != null && _value.length() != 0) {
            __wheres.add(f.like("%" + _value.trim().replace(' ', '%') + "%"));
          }
          continue;
        }
        // TIME
        if (f instanceof TimeField) {
          TimeField tf = (TimeField) f;
          Time _valueDesde = (Time) Utils.getValueOfJavaField(this.filterForm, f.javaName + "Desde");
          if (_valueDesde != null) {
            __wheres.add(tf.greaterThanOrEqual(_valueDesde));
          }
  
          Time _valueFins = (Time) Utils.getValueOfJavaField(this.filterForm, f.javaName + "Fins");
          if (_valueFins != null) {
            __wheres.add(tf.lessThanOrEqual(_valueFins));
          }
          continue;
        }
  
        // DATE
        if (f instanceof DateField) {
          DateField df = (DateField) f;
          java.sql.Date _valueDesde = (java.sql.Date) Utils.getValueOfJavaField(this.filterForm, f.javaName
              + "Desde");
          if (_valueDesde != null) {
            __wheres.add(df.greaterThanOrEqual(_valueDesde));
          }
  
          java.sql.Date _valueFins = (java.sql.Date) Utils.getValueOfJavaField(this.filterForm, f.javaName
              + "Fins");
          if (_valueFins != null) {
            __wheres.add(df.lessThanOrEqual(_valueFins));
          }
          continue;
        }
  
        // TIMESTAMP
        if (f instanceof TimestampField) {
          TimestampField df = (TimestampField) f;
          java.sql.Timestamp _valueDesde = (java.sql.Timestamp) Utils.getValueOfJavaField(this.filterForm,
              f.javaName + "Desde");
          if (_valueDesde != null) {
            __wheres.add(df.greaterThanOrEqual(_valueDesde));
          }
  
          java.sql.Timestamp _valueFins = (java.sql.Timestamp) Utils.getValueOfJavaField(this.filterForm,
              f.javaName + "Fins");
          if (_valueFins != null) {
            __wheres.add(df.lessThanOrEqual(_valueFins));
          }
          continue;
        }

        // NUMBER 
        if ((f instanceof LongField) || (f instanceof IntegerField) ||
            (f instanceof ByteField) || (f instanceof FloatField) ||
            (f instanceof DoubleField) || (f instanceof ShortField)|| 
            (f instanceof BigIntegerField) || (f instanceof BigDecimalField) ) {
          Field<Number > ff = (Field<Number>) f;
          
          // NOTA: Els false de les cridades a getValueOfJavaField s'ha de llevar quan 
          // Dins Field s'afegeixi informació de si és COMBOBOX o no.
          
          //  Estudiar la possibilitat de filtrar elements ComBox com a select multiple #129 
          List<Number> _values = (List<Number>) Utils.getValueOfJavaField(this.filterForm,
                  f.javaName + "Select", false); 

          if (_values != null) {
              if (_values.size() != 0) {
                __wheres.add(ff.in(_values));
              }
          } else {

              Number _valueDesde = (Number) Utils.getValueOfJavaField(this.filterForm,
                  f.javaName + "Desde", false);
              if (_valueDesde != null) {
                __wheres.add(ff.greaterThanOrEqual(_valueDesde));
              }
      
              Number _valueFins = (Number) Utils.getValueOfJavaField(this.filterForm,
                  f.javaName + "Fins", false);
              if (_valueFins != null) {
                __wheres.add(ff.lessThanOrEqual(_valueFins));
              }
          }

          continue;
        }
  
        log.error(
            "No s'ha trobat gestor en el metode getFilterWhere de la classe " + this.getClass()
                + " pel Field de tipus " + f.getClass(), new Exception());
  
      }
    }

    if (__wheres.size() == 0) {
      return null;
    } else {
      return Where.AND(__wheres.toArray(new Where[__wheres.size()]));
    }

  }

  
  
  public static FilterFormData getFilterFormData(BaseFilterForm filterForm,
      ITableManager<?, ?> ejb, Where whereAdditionalCondition) throws I18NException {
    
    LogicForBaseFilterForm logicForm = new LogicForBaseFilterForm(filterForm);
    FilterFormData ffd = logicForm.getFilterFormData(ejb, whereAdditionalCondition);
    return ffd;
  }
  

  public final FilterFormData getFilterFormData(ITableManager<?, ?> atm,
      Where whereAdditionalCondition) throws I18NException {
    
    if (filterForm.getGroupByFields() == null) {
      filterForm.setGroupByFields(filterForm.getDefaultGroupByFields());
    }
    if (filterForm.getFilterByFields() == null) {
      filterForm.setFilterByFields(filterForm.getDefaultFilterByFields());
    }

    
    Map<String, Object> map = new HashMap<String, Object>();
    
    final Where fw = getFilterWhere();
    filterForm.setVisibleFilterBy(fw != null);

    final Where gw = getGroupWhere();
    filterForm.setVisibleGroupBy(gw != null);

    loadDataForGroupByFilterBy(atm, map, whereAdditionalCondition);

    Where where = Where.AND(fw, gw);
   
    return new FilterFormData(where, getQueryOrderBy(), map);
  }
  
  
  
  
  
  protected <T extends Number> Where getWhere(Class<T> clazz, Field<Number> searchBy, String _valueFrom,
      String _valueTo) throws Exception {
    return new FilterSearchGeneratorOfNumber<T>(clazz, searchBy, _valueFrom,_valueTo).getWhere();
  }
  
  
  
  /**
   * 
   * @author anadal
   *
   * @param <T>
   */
  public class FilterSearchGeneratorOfNumber<T extends Number> {
    private final Class<T> clazz;
    private final String from;
    private final String to;
    private final Field<Number> searchBy;

    public FilterSearchGeneratorOfNumber(Class<T> clazz, Field<Number> searchBy, String from,
        String to) {
      this.clazz = clazz;
      this.from = from;
      this.to = to;
      this.searchBy = searchBy;
    }

    protected T buildOne(String value) throws Exception {
      Constructor<T> constructor = clazz.getConstructor(String.class);
      return constructor.newInstance(value);
    }

    public Where getWhere() throws Exception {

      if (from == null || from.trim().length() == 0) {
        // Llavors "to" no es null
        return ((Field<Number>) searchBy).lessThanOrEqual(buildOne(to));
      }

      if (to == null || to.trim().length() == 0) {
        // Llavors "from" no es null
        return ((Field<Number>) searchBy).greaterThan(buildOne(from));
      }

      // És el rang complet
      return ((Field<Number>) searchBy).between(buildOne(from), buildOne(to));
    }

  }
  
  
  

  /**
   *
   * 
   * @param mav
   * @throws Exception
   */
  protected void loadDataForGroupByFilterBy(ITableManager<?, ?> atm,
      Map<String, Object> mav, Where whereAdditionalCondition) throws I18NException {

    //ITableManager<?, ?> atm = form.getTableManager();

    List<GroupByItem> groupByItems = new ArrayList<GroupByItem>();
    
    
    //if (filterForm.getGroupByFields() != null) 
    {

      for (Field<?> f : filterForm.getGroupByFields()) {
        // groupByItems.add(new GroupByItem(get(f), f.javaName, true, null));
  
        if (f instanceof DateField) {
          groupByItems.addAll(loadDataForGroupByDateAndTimestamp((DateField) f, atm, whereAdditionalCondition));
          continue;
        }
        if (f instanceof TimestampField) {
          groupByItems.addAll(loadDataForGroupByDateAndTimestamp((TimestampField) f, atm, whereAdditionalCondition));
          continue;
        }
        if (f instanceof TimeField) {
          groupByItems.add(loadDataForGroupByTime((TimeField) f, atm, whereAdditionalCondition));
          continue;
        }
  
        // Boolean && String && Integer && LongField
        if (f instanceof BooleanField || f instanceof StringField 
            || f instanceof IntegerField || f instanceof LongField
            || f instanceof DoubleField || f instanceof FloatField
            || f instanceof ByteField || (f instanceof ShortField)
            || f instanceof BigDecimalField || f instanceof BigIntegerField) {
          groupByItems.add(loadDataForGroupByField(f, atm, whereAdditionalCondition));
          continue;
        }
        
        if (f instanceof CustomField) {
          // Gestionat per l'usuari
          continue;
        }

        // TODO Falten altres tipus ???
        log.error("No s'ha trobat gestor en el metode loadDataForGroupByFilterBy de la classe "
            + this.getClass() + " pel Field de tipus " + f.getClass(), new Exception());
  
      }
    }

    mav.put("groupby_items", groupByItems);

  }

  private Collection<GroupByItem> loadDataForGroupByDateAndTimestamp(
      Field<? extends Date> field, ITableManager<?, ?> atm,
      Where whereAdditionalCondition) throws I18NException {

    // Year
    GroupByItem byyear;
    {
      SelectGroupByAndCountForYear s = new SelectGroupByAndCountForYear(field);

      List<GroupByValueItem> gbi = atm.executeQuery(s, whereAdditionalCondition , s.getOrderBy());

      byyear = checkSelected(field, gbi);

      byyear.setSelected(byyear.isSelected() && !contains(filterForm.getGroupValue(), "-"));

    }
    // Year-Month
    GroupByItem byyearmonth;
    {
      SelectGroupByAndCountForYearMonth s = new SelectGroupByAndCountForYearMonth(field);
      List<GroupByValueItem> gbi = atm.executeQuery(s, whereAdditionalCondition , s.getOrderBy());

      byyearmonth = checkSelected(field, gbi);

      byyearmonth.setCodeLabel("genapp.withmonth");
      byyearmonth.setCodeParamLabel(field.codeLabel);

      byyearmonth.setSelected(byyearmonth.isSelected() && contains(filterForm.getGroupValue(), "-"));
    }

    Collection<GroupByItem> col = new ArrayList<GroupByItem>();
    col.add(byyear);
    col.add(byyearmonth);
    return col;
  }

  private boolean contains(String base, String substring) {
    if (base == null) {
      return false;
    } else {
      return base.indexOf(substring) != -1;
    }
  }

  private boolean equals(String s1, String s2) {
    if (s1 == null) {
      return (s2 == null);
    } else {
      return s1.equals(s2);
    }
  }

  /**
   * GROUP BY HOUR DATA
   * 
   * @param field
   * @param atm
   * @return
   * @throws Exception
   */
  private GroupByItem loadDataForGroupByTime(Field<? extends Date> field,
      ITableManager<?, ?> atm, Where whereAdditionalCondition) throws I18NException {

    SelectGroupByAndCountForHour s = new SelectGroupByAndCountForHour(field);

    List<GroupByValueItem> gbi = atm.executeQuery(s, whereAdditionalCondition, s.getOrderBy());

    return checkSelected(field, gbi);

  }

  /**
   * 
   * @param field
   * @param gbi
   * @return
   */
  private GroupByItem checkSelected(Field<?> field, List<GroupByValueItem> gbi) {
    boolean selected = field.javaName.equals(filterForm.getGroupBy());

    if (selected) {
      for (GroupByValueItem gbvi : gbi) {
        if (equals(gbvi.getValue(), filterForm.getGroupValue())) {
          gbvi.setSelected(true);
          break;
        }
      }
    }

    return new GroupByItem(field, field.codeLabel, field.javaName, selected, gbi);
  }

  /**
   * GROUP BY BOOLEAN
   * 
   * @param field
   * @param atm
   * @return
   * @throws Exception
   */
  private GroupByItem loadDataForGroupByField(Field<?> field, ITableManager<?, ?> atm,
      Where whereAdditionalCondition) throws I18NException {

    SelectGroupByAndCountForField s = new SelectGroupByAndCountForField(field);

    List<GroupByValueItem> gbi = atm.executeQuery(s, whereAdditionalCondition, s.getOrderBy());

    return checkSelected(field, gbi);

  }
  
  
  

  /**
   * 
   * @return
   */
  public Where getGroupWhere() {

    final String groupBy = filterForm.getGroupBy();
    if (groupBy == null || (filterForm.getGroupByFields() == null)) {
      return null;
    }

    for (Field<?> f : filterForm.getGroupByFields()) {

      if (f.javaName.equals(groupBy)) {

        // BOOLEAN
        if (f instanceof BooleanField) {
          BooleanField bf = (BooleanField) f;
          return bf.equal(Boolean.valueOf(filterForm.getGroupValue()));
        }

        // TIME
        if (f instanceof TimeField) {
          TimeField tf = (TimeField) f;
          String hour = Utils.empty2Null(filterForm.getGroupValue());
          filterForm.setGroupValue(hour);

          return new FieldHour(tf).equal(hour == null ? null : Integer.parseInt(hour));
        }

        // DATE OR TIMESTAMP
        if (f instanceof DateField || f instanceof TimestampField) {
          Field<? extends Date> ff = (Field<? extends Date>) f;
          String year = Utils.empty2Null(filterForm.getGroupValue());
          filterForm.setGroupValue(year);

          if (year == null || year.indexOf('-') == -1) {
            // Nomes Any
            return new FieldYear(ff).equal(year == null ? null : Integer.parseInt(year));
          } else {
            // Any i Mes
            if (year.equals("-")) {
              return f.isNull();
            } else {
              final String[] params = year.split("-");
              return Where.AND(new FieldYear(ff).equal(Integer.parseInt(params[0])),
                  new FieldMonth(ff).equal(Integer.parseInt(params[1])));
            }
          }
        }
        
        
        // StringField
        if (f instanceof StringField) {
          StringField sf = (StringField) f;
          return sf.equal(filterForm.getGroupValue());
        }
        
        
        // LongField
        if (f instanceof LongField) {
          LongField sf = (LongField) f;
          try {
             String txt = filterForm.getGroupValue();
             if (txt == null || "null".equals(txt)) {
               return sf.isNull();
             }
             Long number = Long.valueOf(txt);
             return sf.equal(number);
          } catch (NumberFormatException nfe) {
            log.error("Error " + nfe.getMessage(), nfe);
            return null;
          }
        }
        
        // IntegerField
        if (f instanceof IntegerField) {
          IntegerField sf = (IntegerField) f;
          try {
            String txt = filterForm.getGroupValue();
            if (txt == null || "null".equals(txt)) {
              return sf.isNull();
            }
            Integer number = Integer.valueOf(txt);
            return sf.equal(number);
          } catch (NumberFormatException nfe) {
            log.error("Error " + nfe.getMessage(), nfe);
            return null;
          }
        }
        
        // DoubleField
        if (f instanceof DoubleField) {
          DoubleField sf = (DoubleField) f;
          try {
            String txt = filterForm.getGroupValue();
            if (txt == null || "null".equals(txt)) {
              return sf.isNull();
            }
            Double number = Double.valueOf(txt);
            return sf.equal(number);
          } catch (NumberFormatException nfe) {
            log.error("Error " + nfe.getMessage(), nfe);
            return null;
          }
        }
        
        // FloatField
        if (f instanceof FloatField) {
          FloatField sf = (FloatField) f;
          try {
            String txt = filterForm.getGroupValue();
            if (txt == null || "null".equals(txt)) {
              return sf.isNull();
            }
            Float number = Float.valueOf(txt);
            return sf.equal(number);
          } catch (NumberFormatException nfe) {
            log.error("Error " + nfe.getMessage(), nfe);
            return null;
          }
        }
        
        // ShortField
        if (f instanceof ShortField) {
          ShortField sf = (ShortField) f;
          try {
            String txt = filterForm.getGroupValue();
            if (txt == null || "null".equals(txt)) {
              return sf.isNull();
            }
            Short number = Short.valueOf(txt);
            return sf.equal(number);
          } catch (NumberFormatException nfe) {
            log.error("Error " + nfe.getMessage(), nfe);
            return null;
          }
        }
        
        
        // ByteField
        if (f instanceof ByteField) {
          ByteField sf = (ByteField) f;
          try {
            String txt = filterForm.getGroupValue();
            if (txt == null || "null".equals(txt)) {
              return sf.isNull();
            }
            Byte number = Byte.valueOf(txt);
            return sf.equal(number);
          } catch (NumberFormatException nfe) {
            log.error("Error " + nfe.getMessage(), nfe);
            return null;
          }
        }
        
        
        if (f instanceof CustomField) {
          // Ho gestionarà l'usuari dins algun mètode com postList o fillReferencesForList
          return null;
        }

        // TODO Falten altres tipus !!!!
        log.error(
            "No s'ha trobat gestor en el mètode getGroupWhere() de la classe "
                + this.getClass() + " pel Field de tipus " + f.getClass(), new Exception());

      }
    }

    return null;

  }

  
}
