package org.fundaciobit.genappsqltutorial.persistence.validator;

import org.apache.log4j.Logger;

import org.fundaciobit.genappsqltutorial.model.entity.Customers;
import org.fundaciobit.genapp.common.query.Field;
import org.fundaciobit.genappsqltutorial.model.fields.CustomersFields;

import org.fundaciobit.genapp.common.validation.IValidatorResult;


/**
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * @author GenApp
 * @author anadal
 */
public class CustomersValidator<I extends Customers>
    extends org.fundaciobit.genapp.common.validation.AbstractValidator    implements CustomersFields {

    protected final Logger log = Logger.getLogger(getClass());


  public CustomersValidator() {
    super();    
  }
  

  /** Constructor */
  public void validate(IValidatorResult<I> __vr,I __target__, boolean __isNou__
    ,org.fundaciobit.genappsqltutorial.model.dao.ICustomersManager __customersManager) {

    // Valors Not Null
    // Check size
    if (__vr.getFieldErrorCount(CUSTOMERNAME) == 0) {
      java.lang.String __customername = __target__.getCustomername();
      if (__customername!= null && __customername.length() > 255) {
        __vr.rejectValue(CUSTOMERNAME, "genapp.validation.sizeexceeds",
            new org.fundaciobit.genapp.common.i18n.I18NArgumentCode(get(CUSTOMERNAME)), new org.fundaciobit.genapp.common.i18n.I18NArgumentString(String.valueOf(255)));
      }
    }

    if (__vr.getFieldErrorCount(CONTACTNAME) == 0) {
      java.lang.String __contactname = __target__.getContactname();
      if (__contactname!= null && __contactname.length() > 255) {
        __vr.rejectValue(CONTACTNAME, "genapp.validation.sizeexceeds",
            new org.fundaciobit.genapp.common.i18n.I18NArgumentCode(get(CONTACTNAME)), new org.fundaciobit.genapp.common.i18n.I18NArgumentString(String.valueOf(255)));
      }
    }

    if (__vr.getFieldErrorCount(ADDRESS) == 0) {
      java.lang.String __address = __target__.getAddress();
      if (__address!= null && __address.length() > 255) {
        __vr.rejectValue(ADDRESS, "genapp.validation.sizeexceeds",
            new org.fundaciobit.genapp.common.i18n.I18NArgumentCode(get(ADDRESS)), new org.fundaciobit.genapp.common.i18n.I18NArgumentString(String.valueOf(255)));
      }
    }

    if (__vr.getFieldErrorCount(CITY) == 0) {
      java.lang.String __city = __target__.getCity();
      if (__city!= null && __city.length() > 255) {
        __vr.rejectValue(CITY, "genapp.validation.sizeexceeds",
            new org.fundaciobit.genapp.common.i18n.I18NArgumentCode(get(CITY)), new org.fundaciobit.genapp.common.i18n.I18NArgumentString(String.valueOf(255)));
      }
    }

    if (__vr.getFieldErrorCount(COUNTRY) == 0) {
      java.lang.String __country = __target__.getCountry();
      if (__country!= null && __country.length() > 255) {
        __vr.rejectValue(COUNTRY, "genapp.validation.sizeexceeds",
            new org.fundaciobit.genapp.common.i18n.I18NArgumentCode(get(COUNTRY)), new org.fundaciobit.genapp.common.i18n.I18NArgumentString(String.valueOf(255)));
      }
    }

    if (__vr.getFieldErrorCount(POSTALCODE) == 0) {
      java.lang.String __postalcode = __target__.getPostalcode();
      if (__postalcode!= null && __postalcode.length() > 255) {
        __vr.rejectValue(POSTALCODE, "genapp.validation.sizeexceeds",
            new org.fundaciobit.genapp.common.i18n.I18NArgumentCode(get(POSTALCODE)), new org.fundaciobit.genapp.common.i18n.I18NArgumentString(String.valueOf(255)));
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