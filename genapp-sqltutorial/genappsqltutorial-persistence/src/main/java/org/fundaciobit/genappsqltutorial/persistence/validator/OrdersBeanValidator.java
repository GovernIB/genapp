package org.fundaciobit.genappsqltutorial.persistence.validator;

import org.fundaciobit.genappsqltutorial.persistence.OrdersJPA;
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
public class OrdersBeanValidator 
      extends AbstractBeanValidator<OrdersJPA> {


  // EJB's
  protected final org.fundaciobit.genappsqltutorial.model.dao.ICustomersManager __customersManager;

  protected final org.fundaciobit.genappsqltutorial.model.dao.IEmployeesManager __employeesManager;

  protected final org.fundaciobit.genappsqltutorial.model.dao.IOrdersManager __ordersManager;

  protected final org.fundaciobit.genappsqltutorial.model.dao.ISuppliersManager __suppliersManager;


  public final OrdersValidator<OrdersJPA> _validator;


  public OrdersBeanValidator(org.fundaciobit.genappsqltutorial.model.dao.ICustomersManager __customersManager,
     org.fundaciobit.genappsqltutorial.model.dao.IEmployeesManager __employeesManager,
     org.fundaciobit.genappsqltutorial.model.dao.IOrdersManager __ordersManager,
     org.fundaciobit.genappsqltutorial.model.dao.ISuppliersManager __suppliersManager) { 
    this.__customersManager = __customersManager;
    this.__employeesManager = __employeesManager;
    this.__ordersManager = __ordersManager;
    this.__suppliersManager = __suppliersManager;
    _validator = new OrdersValidator<OrdersJPA>();
  }

  public OrdersBeanValidator(OrdersValidator<OrdersJPA> _validator,
     org.fundaciobit.genappsqltutorial.model.dao.ICustomersManager __customersManager,
     org.fundaciobit.genappsqltutorial.model.dao.IEmployeesManager __employeesManager,
     org.fundaciobit.genappsqltutorial.model.dao.IOrdersManager __ordersManager,
     org.fundaciobit.genappsqltutorial.model.dao.ISuppliersManager __suppliersManager) {
    this.__customersManager = __customersManager;
    this.__employeesManager = __employeesManager;
    this.__ordersManager = __ordersManager;
    this.__suppliersManager = __suppliersManager;
    this._validator = _validator;
  }

  @Override
  public List<I18NFieldError> validate(OrdersJPA target, boolean isNou) throws I18NException {
    BeanValidatorResult<OrdersJPA> _bvr_ = new BeanValidatorResult<OrdersJPA>();
    _validator.validate(_bvr_, target, isNou, __customersManager, __employeesManager, __ordersManager, __suppliersManager);
    return _bvr_.getErrors();
  }
}
