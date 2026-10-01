package org.fundaciobit.genappsqltutorial.persistence.validator;

import org.apache.log4j.Logger;

import org.fundaciobit.genappsqltutorial.model.entity.Products;
import org.fundaciobit.genapp.common.query.Field;
import org.fundaciobit.genappsqltutorial.model.fields.ProductsFields;

import org.fundaciobit.genapp.common.validation.IValidatorResult;


/**
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * @author GenApp
 * @author anadal
 */
public class ProductsValidator<I extends Products>
    extends org.fundaciobit.genapp.common.validation.AbstractValidator    implements ProductsFields {

    protected final Logger log = Logger.getLogger(getClass());


  public ProductsValidator() {
    super();    
  }
  

  /** Constructor */
  public void validate(IValidatorResult<I> __vr,I __target__, boolean __isNou__
    ,org.fundaciobit.genappsqltutorial.model.dao.IProductsManager __productsManager) {

    // Valors Not Null
    // Check size
    if (__vr.getFieldErrorCount(PRODUCTNAME) == 0) {
      java.lang.String __productname = __target__.getProductname();
      if (__productname!= null && __productname.length() > 255) {
        __vr.rejectValue(PRODUCTNAME, "genapp.validation.sizeexceeds",
            new org.fundaciobit.genapp.common.i18n.I18NArgumentCode(get(PRODUCTNAME)), new org.fundaciobit.genapp.common.i18n.I18NArgumentString(String.valueOf(255)));
      }
    }

    if (__vr.getFieldErrorCount(UNIT) == 0) {
      java.lang.String __unit = __target__.getUnit();
      if (__unit!= null && __unit.length() > 255) {
        __vr.rejectValue(UNIT, "genapp.validation.sizeexceeds",
            new org.fundaciobit.genapp.common.i18n.I18NArgumentCode(get(UNIT)), new org.fundaciobit.genapp.common.i18n.I18NArgumentString(String.valueOf(255)));
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