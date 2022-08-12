package org.fundaciobit.genappsqltutorial.back.validator.webdb;

import org.apache.log4j.Logger;

import org.fundaciobit.genapp.common.validation.BeanValidatorResult;
import org.fundaciobit.genapp.common.i18n.I18NFieldError;
import java.util.List;
import org.fundaciobit.genapp.common.query.Field;
import org.fundaciobit.genapp.common.web.validation.WebValidationResult;
import org.fundaciobit.genappsqltutorial.model.fields.*;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.fundaciobit.genappsqltutorial.persistence.validator.OrdersValidator;

import org.fundaciobit.genappsqltutorial.back.form.webdb.OrdersForm;
import org.fundaciobit.genapp.common.web.validation.AbstractWebValidator;
import org.fundaciobit.genappsqltutorial.model.entity.Orders;


/**
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * @author anadal
 */
@Component
public class OrdersWebValidator extends AbstractWebValidator<OrdersForm, Orders>
     implements Validator, OrdersFields {

     protected final Logger log = Logger.getLogger(getClass());

  protected OrdersValidator<Orders> validator = new OrdersValidator<Orders>();

  // EJB's
  @javax.ejb.EJB(mappedName = org.fundaciobit.genappsqltutorial.ejb.CustomersService.JNDI_NAME)
  protected org.fundaciobit.genappsqltutorial.ejb.CustomersService customersEjb;

  @javax.ejb.EJB(mappedName = org.fundaciobit.genappsqltutorial.ejb.EmployeesService.JNDI_NAME)
  protected org.fundaciobit.genappsqltutorial.ejb.EmployeesService employeesEjb;

  @javax.ejb.EJB(mappedName = org.fundaciobit.genappsqltutorial.ejb.OrdersService.JNDI_NAME)
  protected org.fundaciobit.genappsqltutorial.ejb.OrdersService ordersEjb;

  @javax.ejb.EJB(mappedName = org.fundaciobit.genappsqltutorial.ejb.SuppliersService.JNDI_NAME)
  protected org.fundaciobit.genappsqltutorial.ejb.SuppliersService suppliersEjb;



  public OrdersWebValidator() {
    super();    
  }
  
  @Override
  public Orders getBeanOfForm(OrdersForm form) {
    return  form.getOrders();
  }

  @Override
  public Class<OrdersForm> getClassOfForm() {
    return OrdersForm.class;
  }

  @Override
  public void validate(OrdersForm __form, Orders __bean, Errors errors) {

    WebValidationResult<OrdersForm> wvr;
    wvr = new WebValidationResult<OrdersForm>(errors);

    boolean isNou;
    {
        Object objNou = errors.getFieldValue("nou");
        if (objNou == null) {
            isNou = false;
        } else { 
         Boolean nou = Boolean.parseBoolean(String.valueOf(objNou));
         isNou =  nou != null && nou.booleanValue();
        }
    }

    validate(__form, __bean , errors, wvr, isNou);
  }


  public void validate(OrdersForm __form, Orders __bean, Errors errors,
    WebValidationResult<OrdersForm> wvr, boolean isNou) {

    BeanValidatorResult<Orders> __vr = new BeanValidatorResult<Orders>();
    validator.validate(__vr, __bean,
      isNou, customersEjb, employeesEjb, ordersEjb, suppliersEjb);

    if (__vr.hasErrors()) {
        List<I18NFieldError> vrErrors = __vr.getErrors();
    	   for (I18NFieldError i18nFieldError : vrErrors) {
    	       wvr.rejectValue(i18nFieldError.getField(), i18nFieldError.getTranslation().getCode(), i18nFieldError.getTranslation().getArgs());
        }
    }


  } // Final de metode

  public String get(Field<?> field) {
    return field.fullName;
  }

  public OrdersValidator<Orders> getValidator() {
    return validator;
  }

  public void setValidator(OrdersValidator<Orders> validator) {
    this.validator = validator;
  }

}