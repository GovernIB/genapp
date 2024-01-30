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
import org.fundaciobit.demogenapp.persistence.validator.AssignaturaAlumneValidator;

import org.fundaciobit.demogenapp.back.form.webdb.AssignaturaAlumneForm;
import org.fundaciobit.genapp.common.web.validation.AbstractWebValidator;
import org.fundaciobit.demogenapp.model.entity.AssignaturaAlumne;


/**
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * @author anadal
 */
@Component
public class AssignaturaAlumneWebValidator extends AbstractWebValidator<AssignaturaAlumneForm, AssignaturaAlumne>
     implements Validator, AssignaturaAlumneFields {

     protected final Logger log = Logger.getLogger(getClass());

  protected AssignaturaAlumneValidator<AssignaturaAlumne> validator = new AssignaturaAlumneValidator<AssignaturaAlumne>();

  // EJB's
  @javax.ejb.EJB(mappedName = org.fundaciobit.demogenapp.ejb.AlumneService.JNDI_NAME)
  protected org.fundaciobit.demogenapp.ejb.AlumneService alumneEjb;

  @javax.ejb.EJB(mappedName = org.fundaciobit.demogenapp.ejb.AssignaturaService.JNDI_NAME)
  protected org.fundaciobit.demogenapp.ejb.AssignaturaService assignaturaEjb;

  @javax.ejb.EJB(mappedName = org.fundaciobit.demogenapp.ejb.AssignaturaAlumneService.JNDI_NAME)
  protected org.fundaciobit.demogenapp.ejb.AssignaturaAlumneService assignaturaAlumneEjb;



  public AssignaturaAlumneWebValidator() {
    super();    
  }
  
  @Override
  public AssignaturaAlumne getBeanOfForm(AssignaturaAlumneForm form) {
    return  form.getAssignaturaAlumne();
  }

  @Override
  public Class<AssignaturaAlumneForm> getClassOfForm() {
    return AssignaturaAlumneForm.class;
  }

  @Override
  public void validate(AssignaturaAlumneForm __form, AssignaturaAlumne __bean, Errors errors) {

    WebValidationResult<AssignaturaAlumneForm> wvr;
    wvr = new WebValidationResult<AssignaturaAlumneForm>(errors);

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


  public void validate(AssignaturaAlumneForm __form, AssignaturaAlumne __bean, Errors errors,
    WebValidationResult<AssignaturaAlumneForm> wvr, boolean isNou) {

    BeanValidatorResult<AssignaturaAlumne> __vr = new BeanValidatorResult<AssignaturaAlumne>();
    validator.validate(__vr, __bean,
      isNou, alumneEjb, assignaturaEjb, assignaturaAlumneEjb);

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

  public AssignaturaAlumneValidator<AssignaturaAlumne> getValidator() {
    return validator;
  }

  public void setValidator(AssignaturaAlumneValidator<AssignaturaAlumne> validator) {
    this.validator = validator;
  }

}