package org.fundaciobit.genappsqltutorial.persistence.validator;

import org.fundaciobit.genappsqltutorial.persistence.EmployeesJPA;
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
public class EmployeesBeanValidator 
      extends AbstractBeanValidator<EmployeesJPA> {


  // EJB's
  protected final org.fundaciobit.genappsqltutorial.model.dao.IEmployeesManager __employeesManager;


  public final EmployeesValidator<EmployeesJPA> _validator;


  public EmployeesBeanValidator(org.fundaciobit.genappsqltutorial.model.dao.IEmployeesManager __employeesManager) { 
    this.__employeesManager = __employeesManager;
    _validator = new EmployeesValidator<EmployeesJPA>();
  }

  public EmployeesBeanValidator(EmployeesValidator<EmployeesJPA> _validator,
     org.fundaciobit.genappsqltutorial.model.dao.IEmployeesManager __employeesManager) {
    this.__employeesManager = __employeesManager;
    this._validator = _validator;
  }

  @Override
  public List<I18NFieldError> validate(EmployeesJPA target, boolean isNou) throws I18NException {
    BeanValidatorResult<EmployeesJPA> _bvr_ = new BeanValidatorResult<EmployeesJPA>();
    _validator.validate(_bvr_, target, isNou, __employeesManager);
    return _bvr_.getErrors();
  }
}
