package org.fundaciobit.demogenapp.persistence.validator;

import org.fundaciobit.demogenapp.persistence.AssignaturaJPA;
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
public class AssignaturaBeanValidator 
      extends AbstractBeanValidator<AssignaturaJPA> {


  // EJB's
  protected final org.fundaciobit.demogenapp.model.dao.IAssignaturaManager __assignaturaManager;


  public final AssignaturaValidator<AssignaturaJPA> _validator;


  public AssignaturaBeanValidator(org.fundaciobit.demogenapp.model.dao.IAssignaturaManager __assignaturaManager) { 
    this.__assignaturaManager = __assignaturaManager;
    _validator = new AssignaturaValidator<AssignaturaJPA>();
  }

  public AssignaturaBeanValidator(AssignaturaValidator<AssignaturaJPA> _validator,
     org.fundaciobit.demogenapp.model.dao.IAssignaturaManager __assignaturaManager) {
    this.__assignaturaManager = __assignaturaManager;
    this._validator = _validator;
  }

  @Override
  public List<I18NFieldError> validate(AssignaturaJPA target, boolean isNou) throws I18NException {
    BeanValidatorResult<AssignaturaJPA> _bvr_ = new BeanValidatorResult<AssignaturaJPA>();
    _validator.validate(_bvr_, target, isNou, __assignaturaManager);
    return _bvr_.getErrors();
  }
}
