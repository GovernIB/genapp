package org.fundaciobit.genappsqltutorial.persistence.validator;

import org.fundaciobit.genappsqltutorial.persistence.ShippersJPA;
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
public class ShippersBeanValidator 
      extends AbstractBeanValidator<ShippersJPA> {


  // EJB's
  protected final org.fundaciobit.genappsqltutorial.model.dao.IShippersManager __shippersManager;


  public final ShippersValidator<ShippersJPA> _validator;


  public ShippersBeanValidator(org.fundaciobit.genappsqltutorial.model.dao.IShippersManager __shippersManager) { 
    this.__shippersManager = __shippersManager;
    _validator = new ShippersValidator<ShippersJPA>();
  }

  public ShippersBeanValidator(ShippersValidator<ShippersJPA> _validator,
     org.fundaciobit.genappsqltutorial.model.dao.IShippersManager __shippersManager) {
    this.__shippersManager = __shippersManager;
    this._validator = _validator;
  }

  @Override
  public List<I18NFieldError> validate(ShippersJPA target, boolean isNou) throws I18NException {
    BeanValidatorResult<ShippersJPA> _bvr_ = new BeanValidatorResult<ShippersJPA>();
    _validator.validate(_bvr_, target, isNou, __shippersManager);
    return _bvr_.getErrors();
  }
}
