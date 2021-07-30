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
import org.fundaciobit.genappsqltutorial.persistence.validator.EmployeesValidator;

import org.fundaciobit.genappsqltutorial.back.form.webdb.EmployeesForm;
import org.fundaciobit.genapp.common.web.validation.AbstractWebValidator;
import org.fundaciobit.genappsqltutorial.model.entity.Employees;


/**
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * @author anadal
 */
@Component
public class EmployeesWebValidator extends AbstractWebValidator<EmployeesForm, Employees>
     implements Validator, EmployeesFields {

     protected final Logger log = Logger.getLogger(getClass());

  protected EmployeesValidator<Employees> validator = new EmployeesValidator<Employees>();

  // EJB's
  @javax.ejb.EJB(mappedName = org.fundaciobit.genappsqltutorial.ejb.EmployeesService.JNDI_NAME)
  protected org.fundaciobit.genappsqltutorial.ejb.EmployeesService employeesEjb;



  public EmployeesWebValidator() {
    super();    
  }
  
  @Override
  public Employees getBeanOfForm(EmployeesForm form) {
    return  form.getEmployees();
  }

  @Override
  public Class<EmployeesForm> getClassOfForm() {
    return EmployeesForm.class;
  }

  @Override
  public void validate(EmployeesForm __form, Employees __bean, Errors errors) {

    WebValidationResult<EmployeesForm> wvr;
    wvr = new WebValidationResult<EmployeesForm>(errors);

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


  public void validate(EmployeesForm __form, Employees __bean, Errors errors,
    WebValidationResult<EmployeesForm> wvr, boolean isNou) {

    BeanValidatorResult<Employees> __vr = new BeanValidatorResult<Employees>();
    validator.validate(__vr, __bean,
      isNou, employeesEjb);

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

  public EmployeesValidator<Employees> getValidator() {
    return validator;
  }

  public void setValidator(EmployeesValidator<Employees> validator) {
    this.validator = validator;
  }

}