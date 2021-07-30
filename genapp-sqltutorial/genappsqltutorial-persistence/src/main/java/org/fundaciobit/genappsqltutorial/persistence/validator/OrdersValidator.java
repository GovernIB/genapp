package org.fundaciobit.genappsqltutorial.persistence.validator;

import org.apache.log4j.Logger;

import org.fundaciobit.genappsqltutorial.model.entity.Orders;
import org.fundaciobit.genapp.common.query.Field;
import org.fundaciobit.genappsqltutorial.model.fields.OrdersFields;
import org.fundaciobit.genappsqltutorial.model.fields.CustomersFields;
import org.fundaciobit.genappsqltutorial.model.fields.EmployeesFields;
import org.fundaciobit.genappsqltutorial.model.fields.SuppliersFields;

import org.fundaciobit.genapp.common.validation.IValidatorResult;


/**
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * @author GenApp
 * @author anadal
 */
public class OrdersValidator<I extends Orders>
    extends org.fundaciobit.genapp.common.validation.AbstractValidator    implements OrdersFields {

    protected final Logger log = Logger.getLogger(getClass());


  public OrdersValidator() {
    super();    
  }
  

  /** Constructor */
  public void validate(IValidatorResult<I> __vr,I __target__, boolean __isNou__
    ,org.fundaciobit.genappsqltutorial.model.dao.ICustomersManager __customersManager
    ,org.fundaciobit.genappsqltutorial.model.dao.IEmployeesManager __employeesManager
    ,org.fundaciobit.genappsqltutorial.model.dao.IOrdersManager __ordersManager
    ,org.fundaciobit.genappsqltutorial.model.dao.ISuppliersManager __suppliersManager) {

    // Valors Not Null
    // Check size
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
    if (__vr.getFieldErrorCount(CUSTOMERID) == 0) {
      java.lang.Long __customerid = __target__.getCustomerid();
      if (__customerid != null ) {
        Long __count_ = null;
        try { __count_ = __customersManager.count(CustomersFields.CUSTOMERID.equal(__customerid)); } catch(org.fundaciobit.genapp.common.i18n.I18NException e) { e.printStackTrace(); };
        if (__count_ == null || __count_ == 0) {        
          __vr.rejectValue(CUSTOMERID, "error.notfound",
         new org.fundaciobit.genapp.common.i18n.I18NArgumentCode("customers.customers"),
         new org.fundaciobit.genapp.common.i18n.I18NArgumentCode("customers.customerid"),
         new org.fundaciobit.genapp.common.i18n.I18NArgumentString(String.valueOf(__customerid)));
        }
      }
    }

    if (__vr.getFieldErrorCount(EMPLOYEEID) == 0) {
      java.lang.Long __employeeid = __target__.getEmployeeid();
      if (__employeeid != null ) {
        Long __count_ = null;
        try { __count_ = __employeesManager.count(EmployeesFields.EMPLOYEEID.equal(__employeeid)); } catch(org.fundaciobit.genapp.common.i18n.I18NException e) { e.printStackTrace(); };
        if (__count_ == null || __count_ == 0) {        
          __vr.rejectValue(EMPLOYEEID, "error.notfound",
         new org.fundaciobit.genapp.common.i18n.I18NArgumentCode("employees.employees"),
         new org.fundaciobit.genapp.common.i18n.I18NArgumentCode("employees.employeeid"),
         new org.fundaciobit.genapp.common.i18n.I18NArgumentString(String.valueOf(__employeeid)));
        }
      }
    }

    if (__vr.getFieldErrorCount(SHIPPERID) == 0) {
      java.lang.Long __shipperid = __target__.getShipperid();
      if (__shipperid != null ) {
        Long __count_ = null;
        try { __count_ = __suppliersManager.count(SuppliersFields.SUPPLIERID.equal(__shipperid)); } catch(org.fundaciobit.genapp.common.i18n.I18NException e) { e.printStackTrace(); };
        if (__count_ == null || __count_ == 0) {        
          __vr.rejectValue(SHIPPERID, "error.notfound",
         new org.fundaciobit.genapp.common.i18n.I18NArgumentCode("suppliers.suppliers"),
         new org.fundaciobit.genapp.common.i18n.I18NArgumentCode("suppliers.supplierid"),
         new org.fundaciobit.genapp.common.i18n.I18NArgumentString(String.valueOf(__shipperid)));
        }
      }
    }

  } // Final de mètode
  public String get(Field<?> field) {
    return field.fullName;
  }
  
}