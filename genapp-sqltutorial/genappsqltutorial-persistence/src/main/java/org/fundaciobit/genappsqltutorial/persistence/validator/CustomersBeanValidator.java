package org.fundaciobit.genappsqltutorial.persistence.validator;

import org.fundaciobit.genappsqltutorial.persistence.CustomersJPA;
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
public class CustomersBeanValidator 
      extends AbstractBeanValidator<CustomersJPA> {


  // EJB's
  protected final org.fundaciobit.genappsqltutorial.model.dao.ICustomersManager __customersManager;


  public final CustomersValidator<CustomersJPA> _validator;


  public CustomersBeanValidator(org.fundaciobit.genappsqltutorial.model.dao.ICustomersManager __customersManager) { 
    this.__customersManager = __customersManager;
    _validator = new CustomersValidator<CustomersJPA>();
  }

  public CustomersBeanValidator(CustomersValidator<CustomersJPA> _validator,
     org.fundaciobit.genappsqltutorial.model.dao.ICustomersManager __customersManager) {
    this.__customersManager = __customersManager;
    this._validator = _validator;
  }

  @Override
  public List<I18NFieldError> validate(CustomersJPA target, boolean isNou) throws I18NException {
    BeanValidatorResult<CustomersJPA> _bvr_ = new BeanValidatorResult<CustomersJPA>();
    _validator.validate(_bvr_, target, isNou, __customersManager);
    return _bvr_.getErrors();
  }
}
