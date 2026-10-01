package org.fundaciobit.demogenapp.persistence.validator;

import org.fundaciobit.demogenapp.persistence.AssignaturaAlumneJPA;
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
public class AssignaturaAlumneBeanValidator 
      extends AbstractBeanValidator<AssignaturaAlumneJPA> {


  // EJB's
  protected final org.fundaciobit.demogenapp.model.dao.IAlumneManager __alumneManager;

  protected final org.fundaciobit.demogenapp.model.dao.IAssignaturaManager __assignaturaManager;

  protected final org.fundaciobit.demogenapp.model.dao.IAssignaturaAlumneManager __assignaturaAlumneManager;


  public final AssignaturaAlumneValidator<AssignaturaAlumneJPA> _validator;


  public AssignaturaAlumneBeanValidator(org.fundaciobit.demogenapp.model.dao.IAlumneManager __alumneManager,
     org.fundaciobit.demogenapp.model.dao.IAssignaturaManager __assignaturaManager,
     org.fundaciobit.demogenapp.model.dao.IAssignaturaAlumneManager __assignaturaAlumneManager) { 
    this.__alumneManager = __alumneManager;
    this.__assignaturaManager = __assignaturaManager;
    this.__assignaturaAlumneManager = __assignaturaAlumneManager;
    _validator = new AssignaturaAlumneValidator<AssignaturaAlumneJPA>();
  }

  public AssignaturaAlumneBeanValidator(AssignaturaAlumneValidator<AssignaturaAlumneJPA> _validator,
     org.fundaciobit.demogenapp.model.dao.IAlumneManager __alumneManager,
     org.fundaciobit.demogenapp.model.dao.IAssignaturaManager __assignaturaManager,
     org.fundaciobit.demogenapp.model.dao.IAssignaturaAlumneManager __assignaturaAlumneManager) {
    this.__alumneManager = __alumneManager;
    this.__assignaturaManager = __assignaturaManager;
    this.__assignaturaAlumneManager = __assignaturaAlumneManager;
    this._validator = _validator;
  }

  @Override
  public List<I18NFieldError> validate(AssignaturaAlumneJPA target, boolean isNou) throws I18NException {
    BeanValidatorResult<AssignaturaAlumneJPA> _bvr_ = new BeanValidatorResult<AssignaturaAlumneJPA>();
    _validator.validate(_bvr_, target, isNou, __alumneManager, __assignaturaManager, __assignaturaAlumneManager);
    return _bvr_.getErrors();
  }
}
