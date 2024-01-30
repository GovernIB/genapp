package org.fundaciobit.demogenapp.persistence.validator;

import org.fundaciobit.demogenapp.persistence.AlumneJPA;
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
public class AlumneBeanValidator 
      extends AbstractBeanValidator<AlumneJPA> {


  // EJB's
  protected final org.fundaciobit.demogenapp.model.dao.IAlumneManager __alumneManager;

  protected final org.fundaciobit.demogenapp.model.dao.IIdiomaManager __idiomaManager;

  protected final org.fundaciobit.demogenapp.model.dao.ITraduccioManager __traduccioManager;


  public final AlumneValidator<AlumneJPA> _validator;


  public AlumneBeanValidator(org.fundaciobit.demogenapp.model.dao.IAlumneManager __alumneManager,
     org.fundaciobit.demogenapp.model.dao.IIdiomaManager __idiomaManager,
     org.fundaciobit.demogenapp.model.dao.ITraduccioManager __traduccioManager) { 
    this.__alumneManager = __alumneManager;
    this.__idiomaManager = __idiomaManager;
    this.__traduccioManager = __traduccioManager;
    _validator = new AlumneValidator<AlumneJPA>();
  }

  public AlumneBeanValidator(AlumneValidator<AlumneJPA> _validator,
     org.fundaciobit.demogenapp.model.dao.IAlumneManager __alumneManager,
     org.fundaciobit.demogenapp.model.dao.IIdiomaManager __idiomaManager,
     org.fundaciobit.demogenapp.model.dao.ITraduccioManager __traduccioManager) {
    this.__alumneManager = __alumneManager;
    this.__idiomaManager = __idiomaManager;
    this.__traduccioManager = __traduccioManager;
    this._validator = _validator;
  }

  @Override
  public List<I18NFieldError> validate(AlumneJPA target, boolean isNou) throws I18NException {
    BeanValidatorResult<AlumneJPA> _bvr_ = new BeanValidatorResult<AlumneJPA>();
    _validator.validate(_bvr_, target, isNou, __alumneManager, __idiomaManager, __traduccioManager);
    return _bvr_.getErrors();
  }
}
