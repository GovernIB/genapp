package org.fundaciobit.genappsqltutorial.persistence.validator;

import org.fundaciobit.genappsqltutorial.persistence.CategoriesJPA;
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
public class CategoriesBeanValidator 
      extends AbstractBeanValidator<CategoriesJPA> {


  // EJB's
  protected final org.fundaciobit.genappsqltutorial.model.dao.ICategoriesManager __categoriesManager;


  public final CategoriesValidator<CategoriesJPA> _validator;


  public CategoriesBeanValidator(org.fundaciobit.genappsqltutorial.model.dao.ICategoriesManager __categoriesManager) { 
    this.__categoriesManager = __categoriesManager;
    _validator = new CategoriesValidator<CategoriesJPA>();
  }

  public CategoriesBeanValidator(CategoriesValidator<CategoriesJPA> _validator,
     org.fundaciobit.genappsqltutorial.model.dao.ICategoriesManager __categoriesManager) {
    this.__categoriesManager = __categoriesManager;
    this._validator = _validator;
  }

  @Override
  public List<I18NFieldError> validate(CategoriesJPA target, boolean isNou) throws I18NException {
    BeanValidatorResult<CategoriesJPA> _bvr_ = new BeanValidatorResult<CategoriesJPA>();
    _validator.validate(_bvr_, target, isNou, __categoriesManager);
    return _bvr_.getErrors();
  }
}
