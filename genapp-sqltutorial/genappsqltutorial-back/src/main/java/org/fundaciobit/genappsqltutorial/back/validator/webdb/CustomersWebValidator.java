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
import org.fundaciobit.genappsqltutorial.persistence.validator.CustomersValidator;

import org.fundaciobit.genappsqltutorial.back.form.webdb.CustomersForm;
import org.fundaciobit.genapp.common.web.validation.AbstractWebValidator;
import org.fundaciobit.genappsqltutorial.model.entity.Customers;


/**
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * @author anadal
 */
@Component
public class CustomersWebValidator extends AbstractWebValidator<CustomersForm, Customers>
     implements Validator, CustomersFields {

     protected final Logger log = Logger.getLogger(getClass());

  protected CustomersValidator<Customers> validator = new CustomersValidator<Customers>();

  // EJB's
  @javax.ejb.EJB(mappedName = org.fundaciobit.genappsqltutorial.ejb.CustomersService.JNDI_NAME)
  protected org.fundaciobit.genappsqltutorial.ejb.CustomersService customersEjb;



  public CustomersWebValidator() {
    super();    
  }
  
  @Override
  public Customers getBeanOfForm(CustomersForm form) {
    return  form.getCustomers();
  }

  @Override
  public Class<CustomersForm> getClassOfForm() {
    return CustomersForm.class;
  }

  @Override
  public void validate(CustomersForm __form, Customers __bean, Errors errors) {

    WebValidationResult<CustomersForm> wvr;
    wvr = new WebValidationResult<CustomersForm>(errors);

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


  public void validate(CustomersForm __form, Customers __bean, Errors errors,
    WebValidationResult<CustomersForm> wvr, boolean isNou) {

    BeanValidatorResult<Customers> __vr = new BeanValidatorResult<Customers>();
    validator.validate(__vr, __bean,
      isNou, customersEjb);

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

  public CustomersValidator<Customers> getValidator() {
    return validator;
  }

  public void setValidator(CustomersValidator<Customers> validator) {
    this.validator = validator;
  }

}