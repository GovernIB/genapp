package org.fundaciobit.demogenapp.back.validator.webdb;

import org.apache.log4j.Logger;

import org.fundaciobit.genapp.common.validation.BeanValidatorResult;
import org.fundaciobit.genapp.common.i18n.I18NFieldError;
import java.util.List;
import org.fundaciobit.genapp.common.query.Field;
import org.fundaciobit.genapp.common.web.validation.WebValidationResult;
import org.fundaciobit.demogenapp.model.fields.*;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.fundaciobit.demogenapp.persistence.validator.AssignaturaValidator;

import org.fundaciobit.demogenapp.back.form.webdb.AssignaturaForm;
import org.fundaciobit.genapp.common.web.validation.AbstractWebValidator;
import org.fundaciobit.demogenapp.model.entity.Assignatura;


/**
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * @author anadal
 */
@Component
public class AssignaturaWebValidator extends AbstractWebValidator<AssignaturaForm, Assignatura>
     implements Validator, AssignaturaFields {

     protected final Logger log = Logger.getLogger(getClass());

  protected AssignaturaValidator<Assignatura> validator = new AssignaturaValidator<Assignatura>();

  // EJB's
  @javax.ejb.EJB(mappedName = org.fundaciobit.demogenapp.ejb.AssignaturaService.JNDI_NAME)
  protected org.fundaciobit.demogenapp.ejb.AssignaturaService assignaturaEjb;



  public AssignaturaWebValidator() {
    super();    
  }
  
  @Override
  public Assignatura getBeanOfForm(AssignaturaForm form) {
    return  form.getAssignatura();
  }

  @Override
  public Class<AssignaturaForm> getClassOfForm() {
    return AssignaturaForm.class;
  }

  @Override
  public void validate(AssignaturaForm __form, Assignatura __bean, Errors errors) {

    WebValidationResult<AssignaturaForm> wvr;
    wvr = new WebValidationResult<AssignaturaForm>(errors);

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


  public void validate(AssignaturaForm __form, Assignatura __bean, Errors errors,
    WebValidationResult<AssignaturaForm> wvr, boolean isNou) {

    BeanValidatorResult<Assignatura> __vr = new BeanValidatorResult<Assignatura>();
    validator.validate(__vr, __bean,
      isNou, assignaturaEjb);

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

  public AssignaturaValidator<Assignatura> getValidator() {
    return validator;
  }

  public void setValidator(AssignaturaValidator<Assignatura> validator) {
    this.validator = validator;
  }

}