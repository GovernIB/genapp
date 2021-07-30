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
import org.fundaciobit.genappsqltutorial.persistence.validator.ProductsValidator;

import org.fundaciobit.genappsqltutorial.back.form.webdb.ProductsForm;
import org.fundaciobit.genapp.common.web.validation.AbstractWebValidator;
import org.fundaciobit.genappsqltutorial.model.entity.Products;


/**
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * @author anadal
 */
@Component
public class ProductsWebValidator extends AbstractWebValidator<ProductsForm, Products>
     implements Validator, ProductsFields {

     protected final Logger log = Logger.getLogger(getClass());

  protected ProductsValidator<Products> validator = new ProductsValidator<Products>();

  // EJB's
  @javax.ejb.EJB(mappedName = org.fundaciobit.genappsqltutorial.ejb.ProductsService.JNDI_NAME)
  protected org.fundaciobit.genappsqltutorial.ejb.ProductsService productsEjb;



  public ProductsWebValidator() {
    super();    
  }
  
  @Override
  public Products getBeanOfForm(ProductsForm form) {
    return  form.getProducts();
  }

  @Override
  public Class<ProductsForm> getClassOfForm() {
    return ProductsForm.class;
  }

  @Override
  public void validate(ProductsForm __form, Products __bean, Errors errors) {

    WebValidationResult<ProductsForm> wvr;
    wvr = new WebValidationResult<ProductsForm>(errors);

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


  public void validate(ProductsForm __form, Products __bean, Errors errors,
    WebValidationResult<ProductsForm> wvr, boolean isNou) {

    BeanValidatorResult<Products> __vr = new BeanValidatorResult<Products>();
    validator.validate(__vr, __bean,
      isNou, productsEjb);

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

  public ProductsValidator<Products> getValidator() {
    return validator;
  }

  public void setValidator(ProductsValidator<Products> validator) {
    this.validator = validator;
  }

}