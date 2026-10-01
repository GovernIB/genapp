package org.fundaciobit.genappsqltutorial.persistence.validator;

import org.apache.log4j.Logger;

import org.fundaciobit.genappsqltutorial.model.entity.Categories;
import org.fundaciobit.genapp.common.query.Field;
import org.fundaciobit.genappsqltutorial.model.fields.CategoriesFields;

import org.fundaciobit.genapp.common.validation.IValidatorResult;


/**
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * @author GenApp
 * @author anadal
 */
public class CategoriesValidator<I extends Categories>
    extends org.fundaciobit.genapp.common.validation.AbstractValidator    implements CategoriesFields {

    protected final Logger log = Logger.getLogger(getClass());


  public CategoriesValidator() {
    super();    
  }
  

  /** Constructor */
  public void validate(IValidatorResult<I> __vr,I __target__, boolean __isNou__
    ,org.fundaciobit.genappsqltutorial.model.dao.ICategoriesManager __categoriesManager) {

    // Valors Not Null
    // Check size
    if (__vr.getFieldErrorCount(CATEGORYNAME) == 0) {
      java.lang.String __categoryname = __target__.getCategoryName();
      if (__categoryname!= null && __categoryname.length() > 255) {
        __vr.rejectValue(CATEGORYNAME, "genapp.validation.sizeexceeds",
            new org.fundaciobit.genapp.common.i18n.I18NArgumentCode(get(CATEGORYNAME)), new org.fundaciobit.genapp.common.i18n.I18NArgumentString(String.valueOf(255)));
      }
    }

    if (__vr.getFieldErrorCount(DESCRIPTION) == 0) {
      java.lang.String __description = __target__.getDescription();
      if (__description!= null && __description.length() > 255) {
        __vr.rejectValue(DESCRIPTION, "genapp.validation.sizeexceeds",
            new org.fundaciobit.genapp.common.i18n.I18NArgumentCode(get(DESCRIPTION)), new org.fundaciobit.genapp.common.i18n.I18NArgumentString(String.valueOf(255)));
      }
    }

    if (__isNou__) { // Creació
      // ================ CREATION
      // Fitxers 
      // ====== Check Unique MULTIPLES - NOU =======

      // Check Unique - no PK
      // Check Unique - PK no AutoIncrement amb UNA SOLA PK 
    } else {
      // ================ UPDATE

      // ====== Check Unique MULTIPLES - EDIT  =======

      // Check Unique - no PK
    }

    // Fields with References to Other tables 
  } // Final de mètode
  public String get(Field<?> field) {
    return field.fullName;
  }
  
}