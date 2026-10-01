package org.fundaciobit.genappsqltutorial.persistence.validator;

import org.fundaciobit.genappsqltutorial.persistence.OrderDetailsJPA;
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
public class OrderDetailsBeanValidator 
      extends AbstractBeanValidator<OrderDetailsJPA> {


  // EJB's
  protected final org.fundaciobit.genappsqltutorial.model.dao.IOrderDetailsManager __orderDetailsManager;

  protected final org.fundaciobit.genappsqltutorial.model.dao.IOrdersManager __ordersManager;

  protected final org.fundaciobit.genappsqltutorial.model.dao.IProductsManager __productsManager;


  public final OrderDetailsValidator<OrderDetailsJPA> _validator;


  public OrderDetailsBeanValidator(org.fundaciobit.genappsqltutorial.model.dao.IOrderDetailsManager __orderDetailsManager,
     org.fundaciobit.genappsqltutorial.model.dao.IOrdersManager __ordersManager,
     org.fundaciobit.genappsqltutorial.model.dao.IProductsManager __productsManager) { 
    this.__orderDetailsManager = __orderDetailsManager;
    this.__ordersManager = __ordersManager;
    this.__productsManager = __productsManager;
    _validator = new OrderDetailsValidator<OrderDetailsJPA>();
  }

  public OrderDetailsBeanValidator(OrderDetailsValidator<OrderDetailsJPA> _validator,
     org.fundaciobit.genappsqltutorial.model.dao.IOrderDetailsManager __orderDetailsManager,
     org.fundaciobit.genappsqltutorial.model.dao.IOrdersManager __ordersManager,
     org.fundaciobit.genappsqltutorial.model.dao.IProductsManager __productsManager) {
    this.__orderDetailsManager = __orderDetailsManager;
    this.__ordersManager = __ordersManager;
    this.__productsManager = __productsManager;
    this._validator = _validator;
  }

  @Override
  public List<I18NFieldError> validate(OrderDetailsJPA target, boolean isNou) throws I18NException {
    BeanValidatorResult<OrderDetailsJPA> _bvr_ = new BeanValidatorResult<OrderDetailsJPA>();
    _validator.validate(_bvr_, target, isNou, __orderDetailsManager, __ordersManager, __productsManager);
    return _bvr_.getErrors();
  }
}
