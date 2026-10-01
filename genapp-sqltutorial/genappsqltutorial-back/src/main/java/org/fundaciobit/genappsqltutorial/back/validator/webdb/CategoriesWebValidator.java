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
import org.fundaciobit.genappsqltutorial.persistence.validator.CategoriesValidator;

import org.fundaciobit.genappsqltutorial.back.form.webdb.CategoriesForm;
import org.fundaciobit.genapp.common.web.validation.AbstractWebValidator;
import org.fundaciobit.genappsqltutorial.model.entity.Categories;


/**
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * @author anadal
 */
@Component
public class CategoriesWebValidator extends AbstractWebValidator<CategoriesForm, Categories>
     implements Validator, CategoriesFields {

     protected final Logger log = Logger.getLogger(getClass());

  protected CategoriesValidator<Categories> validator = new CategoriesValidator<Categories>();

  // EJB's
  @javax.ejb.EJB(mappedName = org.fundaciobit.genappsqltutorial.ejb.CategoriesService.JNDI_NAME)
  protected org.fundaciobit.genappsqltutorial.ejb.CategoriesService categoriesEjb;



  public CategoriesWebValidator() {
    super();    
  }
  
  @Override
  public Categories getBeanOfForm(CategoriesForm form) {
    return  form.getCategories();
  }

  @Override
  public Class<CategoriesForm> getClassOfForm() {
    return CategoriesForm.class;
  }

  @Override
  public void validate(CategoriesForm __form, Categories __bean, Errors errors) {

    WebValidationResult<CategoriesForm> wvr;
    wvr = new WebValidationResult<CategoriesForm>(errors);

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


  public void validate(CategoriesForm __form, Categories __bean, Errors errors,
    WebValidationResult<CategoriesForm> wvr, boolean isNou) {

    BeanValidatorResult<Categories> __vr = new BeanValidatorResult<Categories>();
    validator.validate(__vr, __bean,
      isNou, categoriesEjb);

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

  public CategoriesValidator<Categories> getValidator() {
    return validator;
  }

  public void setValidator(CategoriesValidator<Categories> validator) {
    this.validator = validator;
  }

}