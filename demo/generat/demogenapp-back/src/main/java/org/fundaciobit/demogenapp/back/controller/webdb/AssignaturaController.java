package org.fundaciobit.demogenapp.back.controller.webdb;

import org.fundaciobit.genapp.common.StringKeyValue;
import org.fundaciobit.genapp.common.utils.Utils;
import org.fundaciobit.genapp.common.web.i18n.I18NUtils;
import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genapp.common.query.GroupByItem;
import org.fundaciobit.genapp.common.query.Field;
import org.fundaciobit.genapp.common.query.Where;
import org.fundaciobit.genapp.common.i18n.I18NValidationException;
import org.fundaciobit.genapp.common.web.validation.ValidationWebUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.fundaciobit.demogenapp.back.form.webdb.*;
import org.fundaciobit.demogenapp.back.form.webdb.AssignaturaForm;

import org.fundaciobit.demogenapp.back.validator.webdb.AssignaturaWebValidator;

import org.fundaciobit.demogenapp.persistence.AssignaturaJPA;
import org.fundaciobit.demogenapp.model.entity.Assignatura;
import org.fundaciobit.demogenapp.model.fields.*;

/**
 * Controller per gestionar un Assignatura
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * 
 * @author GenApp
 */
@Controller
@RequestMapping(value = "/webdb/assignatura")
@SessionAttributes(types = { AssignaturaForm.class, AssignaturaFilterForm.class })
public class AssignaturaController
    extends org.fundaciobit.demogenapp.back.controller.DemoGenAppBaseController<Assignatura, java.lang.Long> implements AssignaturaFields {

  @EJB(mappedName = org.fundaciobit.demogenapp.ejb.AssignaturaService.JNDI_NAME)
  protected org.fundaciobit.demogenapp.ejb.AssignaturaService assignaturaEjb;

  @Autowired
  private AssignaturaWebValidator assignaturaWebValidator;

  @Autowired
  protected AssignaturaRefList assignaturaRefList;

  /**
   * Llistat de totes Assignatura
   */
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public String llistat(HttpServletRequest request,
    HttpServletResponse response) throws I18NException {
    AssignaturaFilterForm ff;
    ff = (AssignaturaFilterForm) request.getSession().getAttribute(getSessionAttributeFilterForm());
    int pagina = (ff == null)? 1: ff.getPage();
    return "redirect:" + getContextWeb() + "/list/" + pagina;
  }

  /**
   * Primera peticio per llistar Assignatura de forma paginada
   */
  @RequestMapping(value = "/list/{pagina}", method = RequestMethod.GET)
  public ModelAndView llistatPaginat(HttpServletRequest request,
    HttpServletResponse response, @PathVariable Integer pagina)
      throws I18NException {
    if(!isActiveList()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }
    ModelAndView mav = new ModelAndView(getTileList());
    llistat(mav, request, getAssignaturaFilterForm(pagina, mav, request));
    return mav;
  }

  public AssignaturaFilterForm getAssignaturaFilterForm(Integer pagina, ModelAndView mav,
    HttpServletRequest request) throws I18NException {
    AssignaturaFilterForm assignaturaFilterForm;
    assignaturaFilterForm = (AssignaturaFilterForm) request.getSession().getAttribute(getSessionAttributeFilterForm());
    if(assignaturaFilterForm == null) {
      assignaturaFilterForm = new AssignaturaFilterForm();
      assignaturaFilterForm.setContexte(getContextWeb());
      assignaturaFilterForm.setEntityNameCode(getEntityNameCode());
      assignaturaFilterForm.setEntityNameCodePlural(getEntityNameCodePlural());
      assignaturaFilterForm.setNou(true);
    } else {
      assignaturaFilterForm.setNou(false);
    }
    assignaturaFilterForm.setPage(pagina == null ? 1 : pagina);
    return assignaturaFilterForm;
  }

  /**
   * Segona i següent peticions per llistar Assignatura de forma paginada
   * 
   * @param request
   * @param pagina
   * @param filterForm
   * @return
   * @throws I18NException
   */
  @RequestMapping(value = "/list/{pagina}", method = RequestMethod.POST)
  public ModelAndView llistatPaginat(HttpServletRequest request,
      HttpServletResponse response,@PathVariable Integer pagina,
      @ModelAttribute AssignaturaFilterForm filterForm) throws I18NException {
    if(!isActiveList()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }

    ModelAndView mav = new ModelAndView(getTileList());

    filterForm.setPage(pagina == null ? 1 : pagina);
    // Actualitza el filter form

    request.getSession().setAttribute(getSessionAttributeFilterForm(), filterForm);
    filterForm = getAssignaturaFilterForm(pagina, mav, request);

    llistat(mav, request, filterForm);
    return mav;
  }

  /**
   * Codi centralitzat de llistat de Assignatura de forma paginada.
   * 
   * @param request
   * @param filterForm
   * @param pagina
   * @return
   * @throws I18NException
   */
  protected List<Assignatura> llistat(ModelAndView mav, HttpServletRequest request,
     AssignaturaFilterForm filterForm) throws I18NException {

    int pagina = filterForm.getPage();
    request.getSession().setAttribute(getSessionAttributeFilterForm(), filterForm);

    captureSearchByValueOfAdditionalFields(request, filterForm);

    preList(request, mav, filterForm);

    List<Assignatura> assignatura = processarLlistat(assignaturaEjb,
        filterForm, pagina, getAdditionalCondition(request), mav);

    mav.addObject("assignaturaItems", assignatura);

    mav.addObject("assignaturaFilterForm", filterForm);

    fillReferencesForList(filterForm,request, mav, assignatura, (List<GroupByItem>)mav.getModel().get("groupby_items"));

    postList(request, mav, filterForm, assignatura);

    return assignatura;
  }


  public Map<Field<?>, GroupByItem> fillReferencesForList(AssignaturaFilterForm filterForm,
    HttpServletRequest request, ModelAndView mav,
      List<Assignatura> list, List<GroupByItem> groupItems) throws I18NException {
    Map<Field<?>, GroupByItem> groupByItemsMap = new HashMap<Field<?>, GroupByItem>();
    for (GroupByItem groupByItem : groupItems) {
      groupByItemsMap.put(groupByItem.getField(),groupByItem);
    }

    Map<String, String> _tmp;
    List<StringKeyValue> _listSKV;

    // Field diaSetmana
    {
      _listSKV = getReferenceListForDiaSetmana(request, mav, filterForm, list, groupByItemsMap, null);
      _tmp = Utils.listToMap(_listSKV);
      filterForm.setMapOfValuesForDiaSetmana(_tmp);
      if (filterForm.getGroupByFields().contains(DIASETMANA)) {
        fillValuesToGroupByItems(_tmp, groupByItemsMap, DIASETMANA, false);
      };
    }


    return groupByItemsMap;
  }

  @RequestMapping(value = "/export/{dataExporterID}", method = RequestMethod.POST)
  public void exportList(@PathVariable("dataExporterID") String dataExporterID,
    HttpServletRequest request, HttpServletResponse response,
    AssignaturaFilterForm filterForm) throws Exception, I18NException {

    ModelAndView mav = new ModelAndView(getTileList());
    List<Assignatura> list = llistat(mav, request, filterForm);
    Field<?>[] allFields = ALL_ASSIGNATURA_FIELDS;

    java.util.Map<Field<?>, java.util.Map<String, String>> __mapping;
    __mapping = new java.util.HashMap<Field<?>, java.util.Map<String, String>>();
    __mapping.put(DIASETMANA, filterForm.getMapOfValuesForDiaSetmana());
    exportData(request, response, dataExporterID, filterForm,
          list, allFields, __mapping, PRIMARYKEY_FIELDS);
  }



  /**
   * Carregar el formulari per un nou Assignatura
   */
  @RequestMapping(value = "/new", method = RequestMethod.GET)
  public ModelAndView crearAssignaturaGet(HttpServletRequest request,
      HttpServletResponse response) throws I18NException {

    if(!isActiveFormNew()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }
    ModelAndView mav = new ModelAndView(getTileForm());
    AssignaturaForm assignaturaForm = getAssignaturaForm(null, false, request, mav);
    mav.addObject("assignaturaForm" ,assignaturaForm);
    fillReferencesForForm(assignaturaForm, request, mav);
  
    return mav;
  }
  
  /**
   * 
   * @return
   * @throws Exception
   */
  public AssignaturaForm getAssignaturaForm(AssignaturaJPA _jpa,
       boolean __isView, HttpServletRequest request, ModelAndView mav) throws I18NException {
    AssignaturaForm assignaturaForm;
    if(_jpa == null) {
      assignaturaForm = new AssignaturaForm(new AssignaturaJPA(), true);
    } else {
      assignaturaForm = new AssignaturaForm(_jpa, false);
      assignaturaForm.setView(__isView);
    }
    assignaturaForm.setContexte(getContextWeb());
    assignaturaForm.setEntityNameCode(getEntityNameCode());
    assignaturaForm.setEntityNameCodePlural(getEntityNameCodePlural());
    return assignaturaForm;
  }

  public void fillReferencesForForm(AssignaturaForm assignaturaForm,
    HttpServletRequest request, ModelAndView mav) throws I18NException {
    // Comprovam si ja esta definida la llista
    if (assignaturaForm.getListOfValuesForDiaSetmana() == null) {
      List<StringKeyValue> _listSKV = getReferenceListForDiaSetmana(request, mav, assignaturaForm, null);

      if(_listSKV != null && !_listSKV.isEmpty()) { 
          java.util.Collections.sort(_listSKV, STRINGKEYVALUE_COMPARATOR);
      }
      assignaturaForm.setListOfValuesForDiaSetmana(_listSKV);
    }
    
  }

  /**
   * Guardar un nou Assignatura
   */
  @RequestMapping(value = "/new", method = RequestMethod.POST)
  public String crearAssignaturaPost(@ModelAttribute AssignaturaForm assignaturaForm,
      BindingResult result, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    if(!isActiveFormNew()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }

    AssignaturaJPA assignatura = assignaturaForm.getAssignatura();

    try {
      preValidate(request, assignaturaForm, result);
      getWebValidator().validate(assignaturaForm, result);
      postValidate(request,assignaturaForm, result);

      if (result.hasErrors()) {
        result.reject("error.form");
        return getTileForm();
      } else {
        assignatura = create(request, assignatura);
        createMessageSuccess(request, "success.creation", assignatura.getAssignaturaID());
        assignaturaForm.setAssignatura(assignatura);
        return getRedirectWhenCreated(request, assignaturaForm);
      }
    } catch (Throwable __e) {
      if (__e instanceof I18NValidationException) {
        ValidationWebUtils.addFieldErrorsToBindingResult(result, (I18NValidationException)__e);
        return getTileForm();
      }
      String msg = createMessageError(request, "error.creation", null, __e);
      log.error(msg, __e);
      return getTileForm();
    }
  }

  @RequestMapping(value = "/view/{assignaturaID}", method = RequestMethod.GET)
  public ModelAndView veureAssignaturaGet(@PathVariable("assignaturaID") java.lang.Long assignaturaID,
      HttpServletRequest request,
      HttpServletResponse response) throws I18NException {
      return editAndViewAssignaturaGet(assignaturaID,
        request, response, true);
  }


  protected ModelAndView editAndViewAssignaturaGet(@PathVariable("assignaturaID") java.lang.Long assignaturaID,
      HttpServletRequest request,
      HttpServletResponse response, boolean __isView) throws I18NException {
    if((!__isView) && !isActiveFormEdit()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    } else {
      if(__isView && !isActiveFormView()) {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return null;
      }
    }
    AssignaturaJPA assignatura = findByPrimaryKey(request, assignaturaID);

    if (assignatura == null) {
      createMessageWarning(request, "error.notfound", assignaturaID);
      new ModelAndView(new RedirectView(getRedirectWhenCancel(request, assignaturaID), true));
      return llistatPaginat(request, response, 1);
    } else {
      ModelAndView mav = new ModelAndView(getTileForm());
      AssignaturaForm assignaturaForm = getAssignaturaForm(assignatura, __isView, request, mav);
      assignaturaForm.setView(__isView);
      if(__isView) {
        assignaturaForm.setAllFieldsReadOnly(ALL_ASSIGNATURA_FIELDS);
        assignaturaForm.setSaveButtonVisible(false);
        assignaturaForm.setDeleteButtonVisible(false);
      }
      fillReferencesForForm(assignaturaForm, request, mav);
      mav.addObject("assignaturaForm", assignaturaForm);
      return mav;
    }
  }


  /**
   * Carregar el formulari per modificar un Assignatura existent
   */
  @RequestMapping(value = "/{assignaturaID}/edit", method = RequestMethod.GET)
  public ModelAndView editarAssignaturaGet(@PathVariable("assignaturaID") java.lang.Long assignaturaID,
      HttpServletRequest request,
      HttpServletResponse response) throws I18NException {
      return editAndViewAssignaturaGet(assignaturaID,
        request, response, false);
  }



  /**
   * Editar un Assignatura existent
   */
  @RequestMapping(value = "/{assignaturaID}/edit", method = RequestMethod.POST)
  public String editarAssignaturaPost(@ModelAttribute AssignaturaForm assignaturaForm,
      BindingResult result, SessionStatus status, HttpServletRequest request,
      HttpServletResponse response) throws I18NException {

    if(!isActiveFormEdit()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }
    AssignaturaJPA assignatura = assignaturaForm.getAssignatura();

    try {
      preValidate(request, assignaturaForm, result);
      getWebValidator().validate(assignaturaForm, result);
      postValidate(request, assignaturaForm, result);

      if (result.hasErrors()) {
        result.reject("error.form");
        return getTileForm();
      } else {
        assignatura = update(request, assignatura);
        createMessageSuccess(request, "success.modification", assignatura.getAssignaturaID());
        status.setComplete();
        return getRedirectWhenModified(request, assignaturaForm, null);
      }
    } catch (Throwable __e) {
      if (__e instanceof I18NValidationException) {
        ValidationWebUtils.addFieldErrorsToBindingResult(result, (I18NValidationException)__e);
        return getTileForm();
      }
      String msg = createMessageError(request, "error.modification",
          assignatura.getAssignaturaID(), __e);
      log.error(msg, __e);
      return getRedirectWhenModified(request, assignaturaForm, __e);
    }

  }


  /**
   * Eliminar un Assignatura existent
   */
  @RequestMapping(value = "/{assignaturaID}/delete")
  public String eliminarAssignatura(@PathVariable("assignaturaID") java.lang.Long assignaturaID,
      HttpServletRequest request,HttpServletResponse response) {

    if(!isActiveDelete()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }
    try {
      Assignatura assignatura = this.findByPrimaryKey(request, assignaturaID);
      if (assignatura == null) {
        String __msg = createMessageError(request, "error.notfound", assignaturaID);
        return getRedirectWhenDelete(request, assignaturaID, new Exception(__msg));
      } else {
        delete(request, assignatura);
        createMessageSuccess(request, "success.deleted", assignaturaID);
        return getRedirectWhenDelete(request, assignaturaID,null);
      }

    } catch (Throwable e) {
      String msg = createMessageError(request, "error.deleting", assignaturaID, e);
      log.error(msg, e);
      return getRedirectWhenDelete(request, assignaturaID, e);
    }
  }


@RequestMapping(value = "/deleteSelected", method = RequestMethod.POST)
public String deleteSelected(HttpServletRequest request,
    HttpServletResponse response,
    @ModelAttribute AssignaturaFilterForm filterForm) throws Exception {

  if(!isActiveDelete()) {
    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    return null;
  }
  
  String[] seleccionats = filterForm.getSelectedItems();
  String redirect = null;
  if (seleccionats != null && seleccionats.length != 0) {
    for (int i = 0; i < seleccionats.length; i++) {
      redirect = eliminarAssignatura(stringToPK(seleccionats[i]), request, response);
    }
  }
  if (redirect == null) {
    redirect = getRedirectWhenDelete(request, null,null);
  }

  return redirect;
}



public java.lang.Long stringToPK(String value) {
  return java.lang.Long.parseLong(value, 10);
}

  @Override
  public String[] getArgumentsMissatge(Object __assignaturaID, Throwable e) {
    java.lang.Long assignaturaID = (java.lang.Long)__assignaturaID;
    String exceptionMsg = "";
    if (e != null) {
      if (e instanceof I18NException) {
        exceptionMsg = I18NUtils.getMessage((I18NException)e);
      } else if (e instanceof I18NValidationException) {
      } else {
        exceptionMsg = e.getMessage();
      };
    };
    if (assignaturaID == null) {
      return new String[] { I18NUtils.tradueix(getEntityNameCode()),
         getPrimaryKeyColumnsTranslated(), null, exceptionMsg };
    } else {
      return new String[] { I18NUtils.tradueix(getEntityNameCode()),
        getPrimaryKeyColumnsTranslated(),
         String.valueOf(assignaturaID),
 exceptionMsg };
    }
  }

  public String getEntityNameCode() {
    return "assignatura.assignatura";
  }

  public String getEntityNameCodePlural() {
    return "assignatura.assignatura.plural";
  }

  public String getPrimaryKeyColumnsTranslated() {
    return  I18NUtils.tradueix("assignatura.assignaturaID");
  }

  @InitBinder("assignaturaFilterForm")
  public void initBinderFilterForm(WebDataBinder binder) {
    super.initBinder(binder);
  }

  @InitBinder("assignaturaForm")
  public void initBinderForm(WebDataBinder binder) {
    super.initBinder(binder);

    binder.setValidator(getWebValidator());


    initDisallowedFields(binder, "assignatura.assignaturaID");
  }

  public AssignaturaWebValidator getWebValidator() {
    return assignaturaWebValidator;
  }


  public void setWebValidator(AssignaturaWebValidator __val) {
    if (__val != null) {
      this.assignaturaWebValidator= __val;
    }
  }


  /**
   * Entra aqui al pitjar el boto cancel en el llistat de Assignatura
   */
  @RequestMapping(value = "/{assignaturaID}/cancel")
  public String cancelAssignatura(@PathVariable("assignaturaID") java.lang.Long assignaturaID,
      HttpServletRequest request,HttpServletResponse response) {
     return getRedirectWhenCancel(request, assignaturaID);
  }

  @Override
  public String getTableModelName() {
    return _TABLE_MODEL;
  }

  // Mètodes a sobreescriure 

  public boolean isActiveList() {
    return true;
  }


  public boolean isActiveFormNew() {
    return true;
  }


  public boolean isActiveFormEdit() {
    return true;
  }


  public boolean isActiveDelete() {
    return true;
  }


  public boolean isActiveFormView() {
    return isActiveFormEdit();
  }


  public List<StringKeyValue> getReferenceListForDiaSetmana(HttpServletRequest request,
       ModelAndView mav, AssignaturaForm assignaturaForm, Where where)  throws I18NException {
    if (assignaturaForm.isHiddenField(DIASETMANA)) {
      return EMPTY_STRINGKEYVALUE_LIST;
    }
    return getReferenceListForDiaSetmana(request, mav, where);
  }


  public List<StringKeyValue> getReferenceListForDiaSetmana(HttpServletRequest request,
       ModelAndView mav, AssignaturaFilterForm assignaturaFilterForm,
       List<Assignatura> list, Map<Field<?>, GroupByItem> _groupByItemsMap, Where where)  throws I18NException {
    if (assignaturaFilterForm.isHiddenField(DIASETMANA)
       && !assignaturaFilterForm.isGroupByField(DIASETMANA)
       && !assignaturaFilterForm.isFilterByField(DIASETMANA)) {
      return EMPTY_STRINGKEYVALUE_LIST;
    }
    Where _w = null;
    return getReferenceListForDiaSetmana(request, mav, Where.AND(where,_w));
  }


  public List<StringKeyValue> getReferenceListForDiaSetmana(HttpServletRequest request,
       ModelAndView mav, Where where)  throws I18NException {
    List<StringKeyValue> __tmp = new java.util.ArrayList<StringKeyValue>();
    __tmp.add(new StringKeyValue("1" , "1"));
    __tmp.add(new StringKeyValue("2" , "2"));
    __tmp.add(new StringKeyValue("3" , "3"));
    __tmp.add(new StringKeyValue("4" , "4"));
    __tmp.add(new StringKeyValue("5" , "5"));
    return __tmp;
  }


  @Override
  /** Ha de ser igual que el RequestMapping de la Classe */
  public String getContextWeb() {
    RequestMapping rm = AnnotationUtils.findAnnotation(this.getClass(), RequestMapping.class);
    return rm.value()[0];
  }

  public void preValidate(HttpServletRequest request,AssignaturaForm assignaturaForm , BindingResult result)  throws I18NException {
  }

  public void postValidate(HttpServletRequest request,AssignaturaForm assignaturaForm, BindingResult result)  throws I18NException {
  }

  public void preList(HttpServletRequest request, ModelAndView mav, AssignaturaFilterForm filterForm)  throws I18NException {
  }

  public void postList(HttpServletRequest request, ModelAndView mav, AssignaturaFilterForm filterForm,  List<Assignatura> list) throws I18NException {
  }

  public String getRedirectWhenCreated(HttpServletRequest request, AssignaturaForm assignaturaForm) {
    return "redirect:" + getContextWeb() + "/list/1";
  }

  public String getRedirectWhenModified(HttpServletRequest request, AssignaturaForm assignaturaForm, Throwable __e) {
    if (__e == null) {
      return "redirect:" + getContextWeb() + "/list";
    } else {
      return  getTileForm();
    }
  }

  public String getRedirectWhenDelete(HttpServletRequest request, java.lang.Long assignaturaID, Throwable __e) {
    return "redirect:" + getContextWeb() + "/list";
  }

  public String getRedirectWhenCancel(HttpServletRequest request, java.lang.Long assignaturaID) {
    return "redirect:" + getContextWeb() + "/list";
  }

  public String getTileForm() {
    return "assignaturaFormWebDB";
  }

  public String getTileList() {
    return "assignaturaListWebDB";
  }

  public String getSessionAttributeFilterForm() {
    return "Assignatura_FilterForm_" + this.getClass().getName();
  }



  public Where getAdditionalCondition(HttpServletRequest request) throws I18NException {
    return null;
  }


  public AssignaturaJPA findByPrimaryKey(HttpServletRequest request, java.lang.Long assignaturaID) throws I18NException {
    return (AssignaturaJPA) assignaturaEjb.findByPrimaryKey(assignaturaID);
  }


  public AssignaturaJPA create(HttpServletRequest request, AssignaturaJPA assignatura)
    throws I18NException, I18NValidationException {
    return (AssignaturaJPA) assignaturaEjb.create(assignatura);
  }


  public AssignaturaJPA update(HttpServletRequest request, AssignaturaJPA assignatura)
    throws I18NException, I18NValidationException {
    return (AssignaturaJPA) assignaturaEjb.update(assignatura);
  }


  public void delete(HttpServletRequest request, Assignatura assignatura) throws I18NException {
    assignaturaEjb.delete(assignatura);
  }

} // Final de Classe

