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
import org.fundaciobit.genappsqltutorial.persistence.validator.OrderDetailsValidator;

import org.fundaciobit.genappsqltutorial.back.form.webdb.OrderDetailsForm;
import org.fundaciobit.genapp.common.web.validation.AbstractWebValidator;
import org.fundaciobit.genappsqltutorial.model.entity.OrderDetails;


/**
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * @author anadal
 */
@Component
public class OrderDetailsWebValidator extends AbstractWebValidator<OrderDetailsForm, OrderDetails>
     implements Validator, OrderDetailsFields {

     protected final Logger log = Logger.getLogger(getClass());

  protected OrderDetailsValidator<OrderDetails> validator = new OrderDetailsValidator<OrderDetails>();

  // EJB's
  @javax.ejb.EJB(mappedName = org.fundaciobit.genappsqltutorial.ejb.OrderDetailsService.JNDI_NAME)
  protected org.fundaciobit.genappsqltutorial.ejb.OrderDetailsService orderDetailsEjb;

  @javax.ejb.EJB(mappedName = org.fundaciobit.genappsqltutorial.ejb.OrdersService.JNDI_NAME)
  protected org.fundaciobit.genappsqltutorial.ejb.OrdersService ordersEjb;

  @javax.ejb.EJB(mappedName = org.fundaciobit.genappsqltutorial.ejb.ProductsService.JNDI_NAME)
  protected org.fundaciobit.genappsqltutorial.ejb.ProductsService productsEjb;



  public OrderDetailsWebValidator() {
    super();    
  }
  
  @Override
  public OrderDetails getBeanOfForm(OrderDetailsForm form) {
    return  form.getOrderDetails();
  }

  @Override
  public Class<OrderDetailsForm> getClassOfForm() {
    return OrderDetailsForm.class;
  }

  @Override
  public void validate(OrderDetailsForm __form, OrderDetails __bean, Errors errors) {

    WebValidationResult<OrderDetailsForm> wvr;
    wvr = new WebValidationResult<OrderDetailsForm>(errors);

    boolean isNou;
    {
        Object objNou = errors.getFieldValue("nou");
        if (objNou == null) {
            isNou = false;
        } else { 
         Boolean nou = Boolean.parseBoolean((String)objNou);
         isNou =  nou != null && nou.booleanValue();
        }
    }

    validate(__form, __bean , errors, wvr, isNou);
  }


  public void validate(OrderDetailsForm __form, OrderDetails __bean, Errors errors,
    WebValidationResult<OrderDetailsForm> wvr, boolean isNou) {

    BeanValidatorResult<OrderDetails> __vr = new BeanValidatorResult<OrderDetails>();
    validator.validate(__vr, __bean,
      isNou, orderDetailsEjb, ordersEjb, productsEjb);

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

  public OrderDetailsValidator<OrderDetails> getValidator() {
    return validator;
  }

  public void setValidator(OrderDetailsValidator<OrderDetails> validator) {
    this.validator = validator;
  }

}