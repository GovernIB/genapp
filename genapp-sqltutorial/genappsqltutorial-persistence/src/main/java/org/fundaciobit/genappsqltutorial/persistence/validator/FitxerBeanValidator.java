package org.fundaciobit.genappsqltutorial.persistence.validator;

import org.fundaciobit.genappsqltutorial.persistence.FitxerJPA;
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
public class FitxerBeanValidator 
      extends AbstractBeanValidator<FitxerJPA> {


  // EJB's
  protected final org.fundaciobit.genappsqltutorial.model.dao.IFitxerManager __fitxerManager;


  public final FitxerValidator<FitxerJPA> _validator;


  public FitxerBeanValidator(org.fundaciobit.genappsqltutorial.model.dao.IFitxerManager __fitxerManager) { 
    this.__fitxerManager = __fitxerManager;
    _validator = new FitxerValidator<FitxerJPA>();
  }

  public FitxerBeanValidator(FitxerValidator<FitxerJPA> _validator,
     org.fundaciobit.genappsqltutorial.model.dao.IFitxerManager __fitxerManager) {
    this.__fitxerManager = __fitxerManager;
    this._validator = _validator;
  }

  @Override
  public List<I18NFieldError> validate(FitxerJPA target, boolean isNou) throws I18NException {
    BeanValidatorResult<FitxerJPA> _bvr_ = new BeanValidatorResult<FitxerJPA>();
    _validator.validate(_bvr_, target, isNou, __fitxerManager);
    return _bvr_.getErrors();
  }
}