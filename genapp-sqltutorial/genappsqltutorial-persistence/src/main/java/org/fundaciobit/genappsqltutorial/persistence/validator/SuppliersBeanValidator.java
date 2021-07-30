package org.fundaciobit.genappsqltutorial.persistence.validator;

import org.fundaciobit.genappsqltutorial.persistence.SuppliersJPA;
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
public class SuppliersBeanValidator 
      extends AbstractBeanValidator<SuppliersJPA> {


  // EJB's
  protected final org.fundaciobit.genappsqltutorial.model.dao.ISuppliersManager __suppliersManager;


  public final SuppliersValidator<SuppliersJPA> _validator;


  public SuppliersBeanValidator(org.fundaciobit.genappsqltutorial.model.dao.ISuppliersManager __suppliersManager) { 
    this.__suppliersManager = __suppliersManager;
    _validator = new SuppliersValidator<SuppliersJPA>();
  }

  public SuppliersBeanValidator(SuppliersValidator<SuppliersJPA> _validator,
     org.fundaciobit.genappsqltutorial.model.dao.ISuppliersManager __suppliersManager) {
    this.__suppliersManager = __suppliersManager;
    this._validator = _validator;
  }

  @Override
  public List<I18NFieldError> validate(SuppliersJPA target, boolean isNou) throws I18NException {
    BeanValidatorResult<SuppliersJPA> _bvr_ = new BeanValidatorResult<SuppliersJPA>();
    _validator.validate(_bvr_, target, isNou, __suppliersManager);
    return _bvr_.getErrors();
  }
}
