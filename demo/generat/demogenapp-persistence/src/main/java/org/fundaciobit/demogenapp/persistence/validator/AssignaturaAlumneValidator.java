package org.fundaciobit.demogenapp.persistence.validator;

import org.apache.log4j.Logger;

import org.fundaciobit.demogenapp.model.entity.AssignaturaAlumne;
import org.fundaciobit.genapp.common.query.Field;
import org.fundaciobit.demogenapp.model.fields.AssignaturaAlumneFields;
import org.fundaciobit.demogenapp.model.fields.AlumneFields;
import org.fundaciobit.demogenapp.model.fields.AssignaturaFields;

import org.fundaciobit.genapp.common.validation.IValidatorResult;


/**
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * @author GenApp
 * @author anadal
 */
public class AssignaturaAlumneValidator<I extends AssignaturaAlumne>
    extends org.fundaciobit.genapp.common.validation.AbstractValidator    implements AssignaturaAlumneFields {

    protected final Logger log = Logger.getLogger(getClass());


  public AssignaturaAlumneValidator() {
    super();    
  }
  

  /** Constructor */
  public void validate(IValidatorResult<I> __vr,I __target__, boolean __isNou__
    ,org.fundaciobit.demogenapp.model.dao.IAlumneManager __alumneManager
    ,org.fundaciobit.demogenapp.model.dao.IAssignaturaManager __assignaturaManager
    ,org.fundaciobit.demogenapp.model.dao.IAssignaturaAlumneManager __assignaturaAlumneManager) {

    // Valors Not Null
    __vr.rejectIfEmptyOrWhitespace(__target__,ALUMNEID, 
        "genapp.validation.required",
        new org.fundaciobit.genapp.common.i18n.I18NArgumentCode(get(ALUMNEID)));

    __vr.rejectIfEmptyOrWhitespace(__target__,ASSIGNATURAID, 
        "genapp.validation.required",
        new org.fundaciobit.genapp.common.i18n.I18NArgumentCode(get(ASSIGNATURAID)));

    // Check size
    if (__isNou__) { // Creació
      // ================ CREATION
      // Fitxers 
      // ====== Check Unique MULTIPLES - NOU =======

      // Check Unique MULTIPLE for (alumneid, assignaturaid)
      if (__vr.getFieldErrorCount(ALUMNEID) == 0 && __vr.getFieldErrorCount(ASSIGNATURAID) == 0) {
        java.lang.Long __alumneid = __target__.getAlumneID();
        java.lang.Long __assignaturaid = __target__.getAssignaturaID();
        Long __count_ = null;
        try { __count_ = __assignaturaAlumneManager.count(org.fundaciobit.genapp.common.query.Where.AND(ALUMNEID.equal(__alumneid), ASSIGNATURAID.equal(__assignaturaid))); } catch(org.fundaciobit.genapp.common.i18n.I18NException e) { e.printStackTrace(); };
        if (__count_ == null || __count_ != 0) {        
            __vr.rejectValue(ALUMNEID, "genapp.validation.unique",
                new org.fundaciobit.genapp.common.i18n.I18NArgumentString(String.valueOf(__alumneid)),
                     new org.fundaciobit.genapp.common.i18n.I18NArgumentCode(get(ALUMNEID)));
            __vr.rejectValue(ASSIGNATURAID, "genapp.validation.unique",
                new org.fundaciobit.genapp.common.i18n.I18NArgumentString(String.valueOf(__assignaturaid)),
                     new org.fundaciobit.genapp.common.i18n.I18NArgumentCode(get(ASSIGNATURAID)));
        }
      }

      // Check Unique - no PK
      // Check Unique - PK no AutoIncrement amb UNA SOLA PK 
    } else {
      // ================ UPDATE

      // ====== Check Unique MULTIPLES - EDIT  =======

      // Check Unique MULTIPLE for (alumneid, assignaturaid)
      if (__vr.getFieldErrorCount(ALUMNEID) == 0 && __vr.getFieldErrorCount(ASSIGNATURAID) == 0 && __vr.getFieldErrorCount(ID) == 0) {
        java.lang.Long __alumneid = __target__.getAlumneID();
        java.lang.Long __assignaturaid = __target__.getAssignaturaID();
        java.lang.Long __id = __target__.getId();
        Long __count_ = null;
        try { __count_ = __assignaturaAlumneManager.count(org.fundaciobit.genapp.common.query.Where.AND(ALUMNEID.equal(__alumneid), ASSIGNATURAID.equal(__assignaturaid), ID.notEqual(__id))); } catch(org.fundaciobit.genapp.common.i18n.I18NException e) { e.printStackTrace(); };
        if (__count_ == null || __count_ != 0) {        
            __vr.rejectValue(ALUMNEID, "genapp.validation.unique",
                new org.fundaciobit.genapp.common.i18n.I18NArgumentString(String.valueOf(__alumneid)),
                     new org.fundaciobit.genapp.common.i18n.I18NArgumentCode(get(ALUMNEID)));
            __vr.rejectValue(ASSIGNATURAID, "genapp.validation.unique",
                new org.fundaciobit.genapp.common.i18n.I18NArgumentString(String.valueOf(__assignaturaid)),
                     new org.fundaciobit.genapp.common.i18n.I18NArgumentCode(get(ASSIGNATURAID)));
        }
      }

      // Check Unique - no PK
    }

    // Fields with References to Other tables 
    if (__vr.getFieldErrorCount(ALUMNEID) == 0) {
      java.lang.Long __alumneid = __target__.getAlumneID();
      Long __count_ = null;
      try { __count_ = __alumneManager.count(AlumneFields.ALUMNEID.equal(__alumneid)); } catch(org.fundaciobit.genapp.common.i18n.I18NException e) { e.printStackTrace(); };
      if (__count_ == null || __count_ == 0) {        
        __vr.rejectValue(ALUMNEID, "error.notfound",
         new org.fundaciobit.genapp.common.i18n.I18NArgumentCode("alumne.alumne"),
         new org.fundaciobit.genapp.common.i18n.I18NArgumentCode("alumne.alumneID"),
         new org.fundaciobit.genapp.common.i18n.I18NArgumentString(String.valueOf(__alumneid)));
      }
    }

    if (__vr.getFieldErrorCount(ASSIGNATURAID) == 0) {
      java.lang.Long __assignaturaid = __target__.getAssignaturaID();
      Long __count_ = null;
      try { __count_ = __assignaturaManager.count(AssignaturaFields.ASSIGNATURAID.equal(__assignaturaid)); } catch(org.fundaciobit.genapp.common.i18n.I18NException e) { e.printStackTrace(); };
      if (__count_ == null || __count_ == 0) {        
        __vr.rejectValue(ASSIGNATURAID, "error.notfound",
         new org.fundaciobit.genapp.common.i18n.I18NArgumentCode("assignatura.assignatura"),
         new org.fundaciobit.genapp.common.i18n.I18NArgumentCode("assignatura.assignaturaID"),
         new org.fundaciobit.genapp.common.i18n.I18NArgumentString(String.valueOf(__assignaturaid)));
      }
    }

  } // Final de mètode
  public String get(Field<?> field) {
    return field.fullName;
  }
  
}