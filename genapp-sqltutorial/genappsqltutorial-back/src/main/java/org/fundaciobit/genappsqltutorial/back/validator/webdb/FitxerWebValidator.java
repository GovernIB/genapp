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
import org.fundaciobit.genappsqltutorial.persistence.validator.FitxerValidator;

import org.fundaciobit.genappsqltutorial.back.form.webdb.FitxerForm;
import org.fundaciobit.genapp.common.web.validation.AbstractWebValidator;
import org.fundaciobit.genappsqltutorial.model.entity.Fitxer;


/**
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * @author anadal
 */
@Component
public class FitxerWebValidator extends AbstractWebValidator<FitxerForm, Fitxer>
     implements Validator, FitxerFields {

     protected final Logger log = Logger.getLogger(getClass());

  protected FitxerValidator<Fitxer> validator = new FitxerValidator<Fitxer>();

  // EJB's
  @javax.ejb.EJB(mappedName = org.fundaciobit.genappsqltutorial.ejb.FitxerService.JNDI_NAME)
  protected org.fundaciobit.genappsqltutorial.ejb.FitxerService fitxerEjb;



  public FitxerWebValidator() {
    super();    
  }
  
  @Override
  public Fitxer getBeanOfForm(FitxerForm form) {
    return  form.getFitxer();
  }

  @Override
  public Class<FitxerForm> getClassOfForm() {
    return FitxerForm.class;
  }

  @Override
  public void validate(FitxerForm __form, Fitxer __bean, Errors errors) {

    WebValidationResult<FitxerForm> wvr;
    wvr = new WebValidationResult<FitxerForm>(errors);

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


  public void validate(FitxerForm __form, Fitxer __bean, Errors errors,
    WebValidationResult<FitxerForm> wvr, boolean isNou) {

    BeanValidatorResult<Fitxer> __vr = new BeanValidatorResult<Fitxer>();
    validator.validate(__vr, __bean,
      isNou, fitxerEjb);

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

  public FitxerValidator<Fitxer> getValidator() {
    return validator;
  }

  public void setValidator(FitxerValidator<Fitxer> validator) {
    this.validator = validator;
  }

}