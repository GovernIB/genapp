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
import org.fundaciobit.genappsqltutorial.persistence.validator.ShippersValidator;

import org.fundaciobit.genappsqltutorial.back.form.webdb.ShippersForm;
import org.fundaciobit.genapp.common.web.validation.AbstractWebValidator;
import org.fundaciobit.genappsqltutorial.model.entity.Shippers;


/**
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * @author anadal
 */
@Component
public class ShippersWebValidator extends AbstractWebValidator<ShippersForm, Shippers>
     implements Validator, ShippersFields {

     protected final Logger log = Logger.getLogger(getClass());

  protected ShippersValidator<Shippers> validator = new ShippersValidator<Shippers>();

  // EJB's
  @javax.ejb.EJB(mappedName = org.fundaciobit.genappsqltutorial.ejb.ShippersService.JNDI_NAME)
  protected org.fundaciobit.genappsqltutorial.ejb.ShippersService shippersEjb;



  public ShippersWebValidator() {
    super();    
  }
  
  @Override
  public Shippers getBeanOfForm(ShippersForm form) {
    return  form.getShippers();
  }

  @Override
  public Class<ShippersForm> getClassOfForm() {
    return ShippersForm.class;
  }

  @Override
  public void validate(ShippersForm __form, Shippers __bean, Errors errors) {

    WebValidationResult<ShippersForm> wvr;
    wvr = new WebValidationResult<ShippersForm>(errors);

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


  public void validate(ShippersForm __form, Shippers __bean, Errors errors,
    WebValidationResult<ShippersForm> wvr, boolean isNou) {

    BeanValidatorResult<Shippers> __vr = new BeanValidatorResult<Shippers>();
    validator.validate(__vr, __bean,
      isNou, shippersEjb);

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

  public ShippersValidator<Shippers> getValidator() {
    return validator;
  }

  public void setValidator(ShippersValidator<Shippers> validator) {
    this.validator = validator;
  }

}