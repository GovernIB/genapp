package org.fundaciobit.genappsqltutorial.persistence.validator;

import org.apache.log4j.Logger;

import org.fundaciobit.genappsqltutorial.model.entity.OrderDetails;
import org.fundaciobit.genapp.common.query.Field;
import org.fundaciobit.genappsqltutorial.model.fields.OrderDetailsFields;
import org.fundaciobit.genappsqltutorial.model.fields.OrdersFields;
import org.fundaciobit.genappsqltutorial.model.fields.ProductsFields;

import org.fundaciobit.genapp.common.validation.IValidatorResult;


/**
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * @author GenApp
 * @author anadal
 */
public class OrderDetailsValidator<I extends OrderDetails>
    extends org.fundaciobit.genapp.common.validation.AbstractValidator    implements OrderDetailsFields {

    protected final Logger log = Logger.getLogger(getClass());


  public OrderDetailsValidator() {
    super();    
  }
  

  /** Constructor */
  public void validate(IValidatorResult<I> __vr,I __target__, boolean __isNou__
    ,org.fundaciobit.genappsqltutorial.model.dao.IOrderDetailsManager __orderDetailsManager
    ,org.fundaciobit.genappsqltutorial.model.dao.IOrdersManager __ordersManager
    ,org.fundaciobit.genappsqltutorial.model.dao.IProductsManager __productsManager) {

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
    if (__vr.getFieldErrorCount(ORDERID) == 0) {
      java.lang.Long __orderid = __target__.getOrderid();
      if (__orderid != null ) {
        Long __count_ = null;
        try { __count_ = __ordersManager.count(OrdersFields.ORDERID.equal(__orderid)); } catch(org.fundaciobit.genapp.common.i18n.I18NException e) { e.printStackTrace(); };
        if (__count_ == null || __count_ == 0) {        
          __vr.rejectValue(ORDERID, "error.notfound",
         new org.fundaciobit.genapp.common.i18n.I18NArgumentCode("orders.orders"),
         new org.fundaciobit.genapp.common.i18n.I18NArgumentCode("orders.orderid"),
         new org.fundaciobit.genapp.common.i18n.I18NArgumentString(String.valueOf(__orderid)));
        }
      }
    }

    if (__vr.getFieldErrorCount(PRODUCTID) == 0) {
      java.lang.Long __productid = __target__.getProductid();
      if (__productid != null ) {
        Long __count_ = null;
        try { __count_ = __productsManager.count(ProductsFields.PRODUCTID.equal(__productid)); } catch(org.fundaciobit.genapp.common.i18n.I18NException e) { e.printStackTrace(); };
        if (__count_ == null || __count_ == 0) {        
          __vr.rejectValue(PRODUCTID, "error.notfound",
         new org.fundaciobit.genapp.common.i18n.I18NArgumentCode("products.products"),
         new org.fundaciobit.genapp.common.i18n.I18NArgumentCode("products.productid"),
         new org.fundaciobit.genapp.common.i18n.I18NArgumentString(String.valueOf(__productid)));
        }
      }
    }

  } // Final de mètode
  public String get(Field<?> field) {
    return field.fullName;
  }
  
}