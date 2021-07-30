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
import org.fundaciobit.genappsqltutorial.persistence.validator.SuppliersValidator;

import org.fundaciobit.genappsqltutorial.back.form.webdb.SuppliersForm;
import org.fundaciobit.genapp.common.web.validation.AbstractWebValidator;
import org.fundaciobit.genappsqltutorial.model.entity.Suppliers;


/**
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * @author anadal
 */
@Component
public class SuppliersWebValidator extends AbstractWebValidator<SuppliersForm, Suppliers>
     implements Validator, SuppliersFields {

     protected final Logger log = Logger.getLogger(getClass());

  protected SuppliersValidator<Suppliers> validator = new SuppliersValidator<Suppliers>();

  // EJB's
  @javax.ejb.EJB(mappedName = org.fundaciobit.genappsqltutorial.ejb.SuppliersService.JNDI_NAME)
  protected org.fundaciobit.genappsqltutorial.ejb.SuppliersService suppliersEjb;



  public SuppliersWebValidator() {
    super();    
  }
  
  @Override
  public Suppliers getBeanOfForm(SuppliersForm form) {
    return  form.getSuppliers();
  }

  @Override
  public Class<SuppliersForm> getClassOfForm() {
    return SuppliersForm.class;
  }

  @Override
  public void validate(SuppliersForm __form, Suppliers __bean, Errors errors) {

    WebValidationResult<SuppliersForm> wvr;
    wvr = new WebValidationResult<SuppliersForm>(errors);

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


  public void validate(SuppliersForm __form, Suppliers __bean, Errors errors,
    WebValidationResult<SuppliersForm> wvr, boolean isNou) {

    BeanValidatorResult<Suppliers> __vr = new BeanValidatorResult<Suppliers>();
    validator.validate(__vr, __bean,
      isNou, suppliersEjb);

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

  public SuppliersValidator<Suppliers> getValidator() {
    return validator;
  }

  public void setValidator(SuppliersValidator<Suppliers> validator) {
    this.validator = validator;
  }

}