package org.fundaciobit.genappsqltutorial.persistence.validator;

import org.fundaciobit.genappsqltutorial.persistence.ProductsJPA;
import org.fundaciobit.genapp.common.validation.BeanValidatorResult;
import java.util.List;
import org.fundaciobit.genapp.common.i18n.I18NFieldError;
import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genapp.common.validation.AbstractBeanValidator;

/**
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * @author GenApp
 * @author anadal
 */
public class ProductsBeanValidator 
      extends AbstractBeanValidator<ProductsJPA> {


  // EJB's
  protected final org.fundaciobit.genappsqltutorial.model.dao.IProductsManager __productsManager;


  public final ProductsValidator<ProductsJPA> _validator;


  public ProductsBeanValidator(org.fundaciobit.genappsqltutorial.model.dao.IProductsManager __productsManager) { 
    this.__productsManager = __productsManager;
    _validator = new ProductsValidator<ProductsJPA>();
  }

  public ProductsBeanValidator(ProductsValidator<ProductsJPA> _validator,
     org.fundaciobit.genappsqltutorial.model.dao.IProductsManager __productsManager) {
    this.__productsManager = __productsManager;
    this._validator = _validator;
  }

  @Override
  public List<I18NFieldError> validate(ProductsJPA target, boolean isNou) throws I18NException {
    BeanValidatorResult<ProductsJPA> _bvr_ = new BeanValidatorResult<ProductsJPA>();
    _validator.validate(_bvr_, target, isNou, __productsManager);
    return _bvr_.getErrors();
  }
}
