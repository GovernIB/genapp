package org.fundaciobit.demogenapp.back.validator.webdb;

import org.apache.log4j.Logger;

import org.fundaciobit.genapp.common.validation.BeanValidatorResult;
import org.fundaciobit.genapp.common.i18n.I18NFieldError;
import java.util.List;
import org.fundaciobit.genapp.common.query.Field;
import org.fundaciobit.genapp.common.web.validation.WebValidationResult;
import org.fundaciobit.demogenapp.model.fields.*;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.fundaciobit.demogenapp.persistence.validator.AlumneValidator;

import org.fundaciobit.demogenapp.back.form.webdb.AlumneForm;
import org.fundaciobit.genapp.common.web.validation.AbstractWebValidator;
import org.fundaciobit.demogenapp.model.entity.Alumne;


/**
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * @author anadal
 */
@Component
public class AlumneWebValidator extends AbstractWebValidator<AlumneForm, Alumne>
     implements Validator, AlumneFields {

     protected final Logger log = Logger.getLogger(getClass());

  protected AlumneValidator<Alumne> validator = new AlumneValidator<Alumne>();

  // EJB's
  @javax.ejb.EJB(mappedName = org.fundaciobit.demogenapp.ejb.AlumneService.JNDI_NAME)
  protected org.fundaciobit.demogenapp.ejb.AlumneService alumneEjb;

  @javax.ejb.EJB(mappedName = org.fundaciobit.demogenapp.ejb.IdiomaService.JNDI_NAME)
  protected org.fundaciobit.demogenapp.ejb.IdiomaService idiomaEjb;

  @javax.ejb.EJB(mappedName = org.fundaciobit.demogenapp.ejb.TraduccioService.JNDI_NAME)
  protected org.fundaciobit.demogenapp.ejb.TraduccioService traduccioEjb;



  public AlumneWebValidator() {
    super();    
  }
  
  @Override
  public Alumne getBeanOfForm(AlumneForm form) {
    return  form.getAlumne();
  }

  @Override
  public Class<AlumneForm> getClassOfForm() {
    return AlumneForm.class;
  }

  @Override
  public void validate(AlumneForm __form, Alumne __bean, Errors errors) {

java.util.List<Field<?>> _ignoreFields = new java.util.ArrayList<Field<?>>();
_ignoreFields.add(TITOLACADEMICID);
    WebValidationResult<AlumneForm> wvr;
    wvr = new WebValidationResult<AlumneForm>(errors, _ignoreFields);

    boolean isNou;
    {
        Object objNou = errors.getFieldValue("nou");
        if (objNou == null) {
            isNou = false;
        } else { 
         Boolean nou = Boolean.parseBoolean(String.valueOf(objNou));
         isNou =  nou != null && nou.booleanValue();
        }
    }

    validate(__form, __bean , errors, wvr, isNou);
  }


  public void validate(AlumneForm __form, Alumne __bean, Errors errors,
    WebValidationResult<AlumneForm> wvr, boolean isNou) {

  {
      org.fundaciobit.demogenapp.persistence.AlumneJPA __jpa;
      __jpa = (org.fundaciobit.demogenapp.persistence.AlumneJPA)__bean;
    {
      // IF CAMP ES NOT NULL verificar que totes les traduccions son not null
      org.fundaciobit.demogenapp.persistence.TraduccioJPA tradJPA = __jpa.getTitolAcademic();
      if (tradJPA != null) {
        // TODO ERROR
        java.util.Map<String,org.fundaciobit.demogenapp.persistence.TraduccioMapJPA> _trad = tradJPA.getTraduccions();
        int countNull= 0;
        int countNotNull = 0;
        for (String _idioma : _trad.keySet()) {
          org.fundaciobit.demogenapp.persistence.TraduccioMapJPA _map = _trad.get(_idioma);
          if (_map == null || (_map.getValor() == null || _map.getValor().length() == 0 )) {
            countNull++;
          } else {
            countNotNull++;
          }
        }

        if (countNull == _trad.size()) {
          // OK Tot esta buit ==> passam el camp a NULL
          __jpa.setTitolAcademicID(null);
          __jpa.setTitolAcademic(null);
        } else {
          if (countNotNull  == _trad.size()) {
            // OK Tot esta ple
          } else {
            for (String _idioma : _trad.keySet()) {
              org.fundaciobit.demogenapp.persistence.TraduccioMapJPA _map = _trad.get(_idioma);
              if (_map == null || (_map.getValor() == null || _map.getValor().length() == 0 )) {
                errors.rejectValue("alumne.titolAcademic", "genapp.validation.required", new String[] {org.fundaciobit.genapp.common.web.i18n.I18NUtils.tradueix(TITOLACADEMICID.fullName)}, null);
                errors.rejectValue("alumne.titolAcademic.traduccions["+ _idioma +"].valor",
                  "genapp.validation.required", new String[] {org.fundaciobit.genapp.common.web.i18n.I18NUtils.tradueix(TITOLACADEMICID.fullName)}, null);
              }
            }
          }
        }
      } else {
          __jpa.setTitolAcademicID(null);
          __jpa.setTitolAcademic(null);
      }
    }

  }
    BeanValidatorResult<Alumne> __vr = new BeanValidatorResult<Alumne>();
    validator.validate(__vr, __bean,
      isNou, alumneEjb, idiomaEjb, traduccioEjb);

    if (__vr.hasErrors()) {
        List<I18NFieldError> vrErrors = __vr.getErrors();
    	   for (I18NFieldError i18nFieldError : vrErrors) {
    	       wvr.rejectValue(i18nFieldError.getField(), i18nFieldError.getTranslation().getCode(), i18nFieldError.getTranslation().getArgs());
        }
    }

    if (isNou) { // Creacio
      // ================ CREATION
      // Fitxers 
    }

  } // Final de metode

  public String get(Field<?> field) {
    return field.fullName;
  }

  public AlumneValidator<Alumne> getValidator() {
    return validator;
  }

  public void setValidator(AlumneValidator<Alumne> validator) {
    this.validator = validator;
  }

}