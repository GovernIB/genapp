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
import org.fundaciobit.demogenapp.back.form.webdb.AssignaturaAlumneForm;

import org.fundaciobit.demogenapp.back.validator.webdb.AssignaturaAlumneWebValidator;

import org.fundaciobit.demogenapp.persistence.AssignaturaAlumneJPA;
import org.fundaciobit.demogenapp.model.entity.AssignaturaAlumne;
import org.fundaciobit.demogenapp.model.fields.*;

/**
 * Controller per gestionar un AssignaturaAlumne
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * 
 * @author GenApp
 */
@Controller
@RequestMapping(value = "/webdb/assignaturaAlumne")
@SessionAttributes(types = { AssignaturaAlumneForm.class, AssignaturaAlumneFilterForm.class })
public class AssignaturaAlumneController
    extends org.fundaciobit.demogenapp.back.controller.DemoGenAppBaseController<AssignaturaAlumne, java.lang.Long> implements AssignaturaAlumneFields {

  @EJB(mappedName = org.fundaciobit.demogenapp.ejb.AssignaturaAlumneService.JNDI_NAME)
  protected org.fundaciobit.demogenapp.ejb.AssignaturaAlumneService assignaturaAlumneEjb;

  @Autowired
  private AssignaturaAlumneWebValidator assignaturaAlumneWebValidator;

  @Autowired
  protected AssignaturaAlumneRefList assignaturaAlumneRefList;

  // References 
  @Autowired
  protected AlumneRefList alumneRefList;

  // References 
  @Autowired
  protected AssignaturaRefList assignaturaRefList;

  /**
   * Llistat de totes AssignaturaAlumne
   */
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public String llistat(HttpServletRequest request,
    HttpServletResponse response) throws I18NException {
    AssignaturaAlumneFilterForm ff;
    ff = (AssignaturaAlumneFilterForm) request.getSession().getAttribute(getSessionAttributeFilterForm());
    int pagina = (ff == null)? 1: ff.getPage();
    return "redirect:" + getContextWeb() + "/list/" + pagina;
  }

  /**
   * Primera peticio per llistar AssignaturaAlumne de forma paginada
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
    llistat(mav, request, getAssignaturaAlumneFilterForm(pagina, mav, request));
    return mav;
  }

  public AssignaturaAlumneFilterForm getAssignaturaAlumneFilterForm(Integer pagina, ModelAndView mav,
    HttpServletRequest request) throws I18NException {
    AssignaturaAlumneFilterForm assignaturaAlumneFilterForm;
    assignaturaAlumneFilterForm = (AssignaturaAlumneFilterForm) request.getSession().getAttribute(getSessionAttributeFilterForm());
    if(assignaturaAlumneFilterForm == null) {
      assignaturaAlumneFilterForm = new AssignaturaAlumneFilterForm();
      assignaturaAlumneFilterForm.setContexte(getContextWeb());
      assignaturaAlumneFilterForm.setEntityNameCode(getEntityNameCode());
      assignaturaAlumneFilterForm.setEntityNameCodePlural(getEntityNameCodePlural());
      assignaturaAlumneFilterForm.setNou(true);
    } else {
      assignaturaAlumneFilterForm.setNou(false);
    }
    assignaturaAlumneFilterForm.setPage(pagina == null ? 1 : pagina);
    return assignaturaAlumneFilterForm;
  }

  /**
   * Segona i següent peticions per llistar AssignaturaAlumne de forma paginada
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
      @ModelAttribute AssignaturaAlumneFilterForm filterForm) throws I18NException {
    if(!isActiveList()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }

    ModelAndView mav = new ModelAndView(getTileList());

    filterForm.setPage(pagina == null ? 1 : pagina);
    // Actualitza el filter form

    request.getSession().setAttribute(getSessionAttributeFilterForm(), filterForm);
    filterForm = getAssignaturaAlumneFilterForm(pagina, mav, request);

    llistat(mav, request, filterForm);
    return mav;
  }

  /**
   * Codi centralitzat de llistat de AssignaturaAlumne de forma paginada.
   * 
   * @param request
   * @param filterForm
   * @param pagina
   * @return
   * @throws I18NException
   */
  protected List<AssignaturaAlumne> llistat(ModelAndView mav, HttpServletRequest request,
     AssignaturaAlumneFilterForm filterForm) throws I18NException {

    int pagina = filterForm.getPage();
    request.getSession().setAttribute(getSessionAttributeFilterForm(), filterForm);

    captureSearchByValueOfAdditionalFields(request, filterForm);

    preList(request, mav, filterForm);

    List<AssignaturaAlumne> assignaturaAlumne = processarLlistat(assignaturaAlumneEjb,
        filterForm, pagina, getAdditionalCondition(request), mav);

    mav.addObject("assignaturaAlumneItems", assignaturaAlumne);

    mav.addObject("assignaturaAlumneFilterForm", filterForm);

    fillReferencesForList(filterForm,request, mav, assignaturaAlumne, (List<GroupByItem>)mav.getModel().get("groupby_items"));

    postList(request, mav, filterForm, assignaturaAlumne);

    return assignaturaAlumne;
  }


  public Map<Field<?>, GroupByItem> fillReferencesForList(AssignaturaAlumneFilterForm filterForm,
    HttpServletRequest request, ModelAndView mav,
      List<AssignaturaAlumne> list, List<GroupByItem> groupItems) throws I18NException {
    Map<Field<?>, GroupByItem> groupByItemsMap = new HashMap<Field<?>, GroupByItem>();
    for (GroupByItem groupByItem : groupItems) {
      groupByItemsMap.put(groupByItem.getField(),groupByItem);
    }

    Map<String, String> _tmp;
    List<StringKeyValue> _listSKV;

    // Field alumneID
    {
      _listSKV = getReferenceListForAlumneID(request, mav, filterForm, list, groupByItemsMap, null);
      _tmp = Utils.listToMap(_listSKV);
      filterForm.setMapOfAlumneForAlumneID(_tmp);
      if (filterForm.getGroupByFields().contains(ALUMNEID)) {
        fillValuesToGroupByItems(_tmp, groupByItemsMap, ALUMNEID, false);
      };
    }

    // Field assignaturaID
    {
      _listSKV = getReferenceListForAssignaturaID(request, mav, filterForm, list, groupByItemsMap, null);
      _tmp = Utils.listToMap(_listSKV);
      filterForm.setMapOfAssignaturaForAssignaturaID(_tmp);
      if (filterForm.getGroupByFields().contains(ASSIGNATURAID)) {
        fillValuesToGroupByItems(_tmp, groupByItemsMap, ASSIGNATURAID, false);
      };
    }


    return groupByItemsMap;
  }

  @RequestMapping(value = "/export/{dataExporterID}", method = RequestMethod.POST)
  public void exportList(@PathVariable("dataExporterID") String dataExporterID,
    HttpServletRequest request, HttpServletResponse response,
    AssignaturaAlumneFilterForm filterForm) throws Exception, I18NException {

    ModelAndView mav = new ModelAndView(getTileList());
    List<AssignaturaAlumne> list = llistat(mav, request, filterForm);
    Field<?>[] allFields = ALL_ASSIGNATURAALUMNE_FIELDS;

    java.util.Map<Field<?>, java.util.Map<String, String>> __mapping;
    __mapping = new java.util.HashMap<Field<?>, java.util.Map<String, String>>();
    __mapping.put(ALUMNEID, filterForm.getMapOfAlumneForAlumneID());
    __mapping.put(ASSIGNATURAID, filterForm.getMapOfAssignaturaForAssignaturaID());
    exportData(request, response, dataExporterID, filterForm,
          list, allFields, __mapping, PRIMARYKEY_FIELDS);
  }



  /**
   * Carregar el formulari per un nou AssignaturaAlumne
   */
  @RequestMapping(value = "/new", method = RequestMethod.GET)
  public ModelAndView crearAssignaturaAlumneGet(HttpServletRequest request,
      HttpServletResponse response) throws I18NException {

    if(!isActiveFormNew()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }
    ModelAndView mav = new ModelAndView(getTileForm());
    AssignaturaAlumneForm assignaturaAlumneForm = getAssignaturaAlumneForm(null, false, request, mav);
    mav.addObject("assignaturaAlumneForm" ,assignaturaAlumneForm);
    fillReferencesForForm(assignaturaAlumneForm, request, mav);
  
    return mav;
  }
  
  /**
   * 
   * @return
   * @throws Exception
   */
  public AssignaturaAlumneForm getAssignaturaAlumneForm(AssignaturaAlumneJPA _jpa,
       boolean __isView, HttpServletRequest request, ModelAndView mav) throws I18NException {
    AssignaturaAlumneForm assignaturaAlumneForm;
    if(_jpa == null) {
      assignaturaAlumneForm = new AssignaturaAlumneForm(new AssignaturaAlumneJPA(), true);
    } else {
      assignaturaAlumneForm = new AssignaturaAlumneForm(_jpa, false);
      assignaturaAlumneForm.setView(__isView);
    }
    assignaturaAlumneForm.setContexte(getContextWeb());
    assignaturaAlumneForm.setEntityNameCode(getEntityNameCode());
    assignaturaAlumneForm.setEntityNameCodePlural(getEntityNameCodePlural());
    return assignaturaAlumneForm;
  }

  public void fillReferencesForForm(AssignaturaAlumneForm assignaturaAlumneForm,
    HttpServletRequest request, ModelAndView mav) throws I18NException {
    // Comprovam si ja esta definida la llista
    if (assignaturaAlumneForm.getListOfAlumneForAlumneID() == null) {
      List<StringKeyValue> _listSKV = getReferenceListForAlumneID(request, mav, assignaturaAlumneForm, null);

      if(_listSKV != null && !_listSKV.isEmpty()) { 
          java.util.Collections.sort(_listSKV, STRINGKEYVALUE_COMPARATOR);
      }
      assignaturaAlumneForm.setListOfAlumneForAlumneID(_listSKV);
    }
    // Comprovam si ja esta definida la llista
    if (assignaturaAlumneForm.getListOfAssignaturaForAssignaturaID() == null) {
      List<StringKeyValue> _listSKV = getReferenceListForAssignaturaID(request, mav, assignaturaAlumneForm, null);

      if(_listSKV != null && !_listSKV.isEmpty()) { 
          java.util.Collections.sort(_listSKV, STRINGKEYVALUE_COMPARATOR);
      }
      assignaturaAlumneForm.setListOfAssignaturaForAssignaturaID(_listSKV);
    }
    
  }

  /**
   * Guardar un nou AssignaturaAlumne
   */
  @RequestMapping(value = "/new", method = RequestMethod.POST)
  public String crearAssignaturaAlumnePost(@ModelAttribute AssignaturaAlumneForm assignaturaAlumneForm,
      BindingResult result, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    if(!isActiveFormNew()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }

    AssignaturaAlumneJPA assignaturaAlumne = assignaturaAlumneForm.getAssignaturaAlumne();

    try {
      preValidate(request, assignaturaAlumneForm, result);
      getWebValidator().validate(assignaturaAlumneForm, result);
      postValidate(request,assignaturaAlumneForm, result);

      if (result.hasErrors()) {
        result.reject("error.form");
        return getTileForm();
      } else {
        assignaturaAlumne = create(request, assignaturaAlumne);
        createMessageSuccess(request, "success.creation", assignaturaAlumne.getId());
        assignaturaAlumneForm.setAssignaturaAlumne(assignaturaAlumne);
        return getRedirectWhenCreated(request, assignaturaAlumneForm);
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

  @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
  public ModelAndView veureAssignaturaAlumneGet(@PathVariable("id") java.lang.Long id,
      HttpServletRequest request,
      HttpServletResponse response) throws I18NException {
      return editAndViewAssignaturaAlumneGet(id,
        request, response, true);
  }


  protected ModelAndView editAndViewAssignaturaAlumneGet(@PathVariable("id") java.lang.Long id,
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
    AssignaturaAlumneJPA assignaturaAlumne = findByPrimaryKey(request, id);

    if (assignaturaAlumne == null) {
      createMessageWarning(request, "error.notfound", id);
      new ModelAndView(new RedirectView(getRedirectWhenCancel(request, id), true));
      return llistatPaginat(request, response, 1);
    } else {
      ModelAndView mav = new ModelAndView(getTileForm());
      AssignaturaAlumneForm assignaturaAlumneForm = getAssignaturaAlumneForm(assignaturaAlumne, __isView, request, mav);
      assignaturaAlumneForm.setView(__isView);
      if(__isView) {
        assignaturaAlumneForm.setAllFieldsReadOnly(ALL_ASSIGNATURAALUMNE_FIELDS);
        assignaturaAlumneForm.setSaveButtonVisible(false);
        assignaturaAlumneForm.setDeleteButtonVisible(false);
      }
      fillReferencesForForm(assignaturaAlumneForm, request, mav);
      mav.addObject("assignaturaAlumneForm", assignaturaAlumneForm);
      return mav;
    }
  }


  /**
   * Carregar el formulari per modificar un AssignaturaAlumne existent
   */
  @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
  public ModelAndView editarAssignaturaAlumneGet(@PathVariable("id") java.lang.Long id,
      HttpServletRequest request,
      HttpServletResponse response) throws I18NException {
      return editAndViewAssignaturaAlumneGet(id,
        request, response, false);
  }



  /**
   * Editar un AssignaturaAlumne existent
   */
  @RequestMapping(value = "/{id}/edit", method = RequestMethod.POST)
  public String editarAssignaturaAlumnePost(@ModelAttribute AssignaturaAlumneForm assignaturaAlumneForm,
      BindingResult result, SessionStatus status, HttpServletRequest request,
      HttpServletResponse response) throws I18NException {

    if(!isActiveFormEdit()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }
    AssignaturaAlumneJPA assignaturaAlumne = assignaturaAlumneForm.getAssignaturaAlumne();

    try {
      preValidate(request, assignaturaAlumneForm, result);
      getWebValidator().validate(assignaturaAlumneForm, result);
      postValidate(request, assignaturaAlumneForm, result);

      if (result.hasErrors()) {
        result.reject("error.form");
        return getTileForm();
      } else {
        assignaturaAlumne = update(request, assignaturaAlumne);
        createMessageSuccess(request, "success.modification", assignaturaAlumne.getId());
        status.setComplete();
        return getRedirectWhenModified(request, assignaturaAlumneForm, null);
      }
    } catch (Throwable __e) {
      if (__e instanceof I18NValidationException) {
        ValidationWebUtils.addFieldErrorsToBindingResult(result, (I18NValidationException)__e);
        return getTileForm();
      }
      String msg = createMessageError(request, "error.modification",
          assignaturaAlumne.getId(), __e);
      log.error(msg, __e);
      return getRedirectWhenModified(request, assignaturaAlumneForm, __e);
    }

  }


  /**
   * Eliminar un AssignaturaAlumne existent
   */
  @RequestMapping(value = "/{id}/delete")
  public String eliminarAssignaturaAlumne(@PathVariable("id") java.lang.Long id,
      HttpServletRequest request,HttpServletResponse response) {

    if(!isActiveDelete()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }
    try {
      AssignaturaAlumne assignaturaAlumne = this.findByPrimaryKey(request, id);
      if (assignaturaAlumne == null) {
        String __msg = createMessageError(request, "error.notfound", id);
        return getRedirectWhenDelete(request, id, new Exception(__msg));
      } else {
        delete(request, assignaturaAlumne);
        createMessageSuccess(request, "success.deleted", id);
        return getRedirectWhenDelete(request, id,null);
      }

    } catch (Throwable e) {
      String msg = createMessageError(request, "error.deleting", id, e);
      log.error(msg, e);
      return getRedirectWhenDelete(request, id, e);
    }
  }


@RequestMapping(value = "/deleteSelected", method = RequestMethod.POST)
public String deleteSelected(HttpServletRequest request,
    HttpServletResponse response,
    @ModelAttribute AssignaturaAlumneFilterForm filterForm) throws Exception {

  if(!isActiveDelete()) {
    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    return null;
  }
  
  String[] seleccionats = filterForm.getSelectedItems();
  String redirect = null;
  if (seleccionats != null && seleccionats.length != 0) {
    for (int i = 0; i < seleccionats.length; i++) {
      redirect = eliminarAssignaturaAlumne(stringToPK(seleccionats[i]), request, response);
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
  public String[] getArgumentsMissatge(Object __id, Throwable e) {
    java.lang.Long id = (java.lang.Long)__id;
    String exceptionMsg = "";
    if (e != null) {
      if (e instanceof I18NException) {
        exceptionMsg = I18NUtils.getMessage((I18NException)e);
      } else if (e instanceof I18NValidationException) {
      } else {
        exceptionMsg = e.getMessage();
      };
    };
    if (id == null) {
      return new String[] { I18NUtils.tradueix(getEntityNameCode()),
         getPrimaryKeyColumnsTranslated(), null, exceptionMsg };
    } else {
      return new String[] { I18NUtils.tradueix(getEntityNameCode()),
        getPrimaryKeyColumnsTranslated(),
         String.valueOf(id),
 exceptionMsg };
    }
  }

  public String getEntityNameCode() {
    return "assignaturaAlumne.assignaturaAlumne";
  }

  public String getEntityNameCodePlural() {
    return "assignaturaAlumne.assignaturaAlumne.plural";
  }

  public String getPrimaryKeyColumnsTranslated() {
    return  I18NUtils.tradueix("assignaturaAlumne.id");
  }

  @InitBinder("assignaturaAlumneFilterForm")
  public void initBinderFilterForm(WebDataBinder binder) {
    super.initBinder(binder);
  }

  @InitBinder("assignaturaAlumneForm")
  public void initBinderForm(WebDataBinder binder) {
    super.initBinder(binder);

    binder.setValidator(getWebValidator());


    initDisallowedFields(binder, "assignaturaAlumne.id");
  }

  public AssignaturaAlumneWebValidator getWebValidator() {
    return assignaturaAlumneWebValidator;
  }


  public void setWebValidator(AssignaturaAlumneWebValidator __val) {
    if (__val != null) {
      this.assignaturaAlumneWebValidator= __val;
    }
  }


  /**
   * Entra aqui al pitjar el boto cancel en el llistat de AssignaturaAlumne
   */
  @RequestMapping(value = "/{id}/cancel")
  public String cancelAssignaturaAlumne(@PathVariable("id") java.lang.Long id,
      HttpServletRequest request,HttpServletResponse response) {
     return getRedirectWhenCancel(request, id);
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


  public List<StringKeyValue> getReferenceListForAlumneID(HttpServletRequest request,
       ModelAndView mav, AssignaturaAlumneForm assignaturaAlumneForm, Where where)  throws I18NException {
    if (assignaturaAlumneForm.isHiddenField(ALUMNEID)) {
      return EMPTY_STRINGKEYVALUE_LIST;
    }
    Where _where = null;
    if (assignaturaAlumneForm.isReadOnlyField(ALUMNEID)) {
      _where = AlumneFields.ALUMNEID.equal(assignaturaAlumneForm.getAssignaturaAlumne().getAlumneID());
    }
    return getReferenceListForAlumneID(request, mav, Where.AND(where, _where));
  }


  public List<StringKeyValue> getReferenceListForAlumneID(HttpServletRequest request,
       ModelAndView mav, AssignaturaAlumneFilterForm assignaturaAlumneFilterForm,
       List<AssignaturaAlumne> list, Map<Field<?>, GroupByItem> _groupByItemsMap, Where where)  throws I18NException {
    if (assignaturaAlumneFilterForm.isHiddenField(ALUMNEID)
       && !assignaturaAlumneFilterForm.isGroupByField(ALUMNEID)) {
      return EMPTY_STRINGKEYVALUE_LIST;
    }
    Where _w = null;
    if (!_groupByItemsMap.containsKey(ALUMNEID)) {
      // OBTENIR TOTES LES CLAUS (PK) i despres només cercar referències d'aquestes PK
      java.util.Set<java.lang.Long> _pkList = new java.util.HashSet<java.lang.Long>();
      for (AssignaturaAlumne _item : list) {
        _pkList.add(_item.getAlumneID());
        }
        _w = AlumneFields.ALUMNEID.in(_pkList);
      }
    return getReferenceListForAlumneID(request, mav, Where.AND(where,_w));
  }


  public List<StringKeyValue> getReferenceListForAlumneID(HttpServletRequest request,
       ModelAndView mav, Where where)  throws I18NException {
    return alumneRefList.getReferenceList(AlumneFields.ALUMNEID, where );
  }


  public List<StringKeyValue> getReferenceListForAssignaturaID(HttpServletRequest request,
       ModelAndView mav, AssignaturaAlumneForm assignaturaAlumneForm, Where where)  throws I18NException {
    if (assignaturaAlumneForm.isHiddenField(ASSIGNATURAID)) {
      return EMPTY_STRINGKEYVALUE_LIST;
    }
    Where _where = null;
    if (assignaturaAlumneForm.isReadOnlyField(ASSIGNATURAID)) {
      _where = AssignaturaFields.ASSIGNATURAID.equal(assignaturaAlumneForm.getAssignaturaAlumne().getAssignaturaID());
    }
    return getReferenceListForAssignaturaID(request, mav, Where.AND(where, _where));
  }


  public List<StringKeyValue> getReferenceListForAssignaturaID(HttpServletRequest request,
       ModelAndView mav, AssignaturaAlumneFilterForm assignaturaAlumneFilterForm,
       List<AssignaturaAlumne> list, Map<Field<?>, GroupByItem> _groupByItemsMap, Where where)  throws I18NException {
    if (assignaturaAlumneFilterForm.isHiddenField(ASSIGNATURAID)
       && !assignaturaAlumneFilterForm.isGroupByField(ASSIGNATURAID)) {
      return EMPTY_STRINGKEYVALUE_LIST;
    }
    Where _w = null;
    if (!_groupByItemsMap.containsKey(ASSIGNATURAID)) {
      // OBTENIR TOTES LES CLAUS (PK) i despres només cercar referències d'aquestes PK
      java.util.Set<java.lang.Long> _pkList = new java.util.HashSet<java.lang.Long>();
      for (AssignaturaAlumne _item : list) {
        _pkList.add(_item.getAssignaturaID());
        }
        _w = AssignaturaFields.ASSIGNATURAID.in(_pkList);
      }
    return getReferenceListForAssignaturaID(request, mav, Where.AND(where,_w));
  }


  public List<StringKeyValue> getReferenceListForAssignaturaID(HttpServletRequest request,
       ModelAndView mav, Where where)  throws I18NException {
    return assignaturaRefList.getReferenceList(AssignaturaFields.ASSIGNATURAID, where );
  }


  @Override
  /** Ha de ser igual que el RequestMapping de la Classe */
  public String getContextWeb() {
    RequestMapping rm = AnnotationUtils.findAnnotation(this.getClass(), RequestMapping.class);
    return rm.value()[0];
  }

  public void preValidate(HttpServletRequest request,AssignaturaAlumneForm assignaturaAlumneForm , BindingResult result)  throws I18NException {
  }

  public void postValidate(HttpServletRequest request,AssignaturaAlumneForm assignaturaAlumneForm, BindingResult result)  throws I18NException {
  }

  public void preList(HttpServletRequest request, ModelAndView mav, AssignaturaAlumneFilterForm filterForm)  throws I18NException {
  }

  public void postList(HttpServletRequest request, ModelAndView mav, AssignaturaAlumneFilterForm filterForm,  List<AssignaturaAlumne> list) throws I18NException {
  }

  public String getRedirectWhenCreated(HttpServletRequest request, AssignaturaAlumneForm assignaturaAlumneForm) {
    return "redirect:" + getContextWeb() + "/list/1";
  }

  public String getRedirectWhenModified(HttpServletRequest request, AssignaturaAlumneForm assignaturaAlumneForm, Throwable __e) {
    if (__e == null) {
      return "redirect:" + getContextWeb() + "/list";
    } else {
      return  getTileForm();
    }
  }

  public String getRedirectWhenDelete(HttpServletRequest request, java.lang.Long id, Throwable __e) {
    return "redirect:" + getContextWeb() + "/list";
  }

  public String getRedirectWhenCancel(HttpServletRequest request, java.lang.Long id) {
    return "redirect:" + getContextWeb() + "/list";
  }

  public String getTileForm() {
    return "assignaturaAlumneFormWebDB";
  }

  public String getTileList() {
    return "assignaturaAlumneListWebDB";
  }

  public String getSessionAttributeFilterForm() {
    return "AssignaturaAlumne_FilterForm_" + this.getClass().getName();
  }



  public Where getAdditionalCondition(HttpServletRequest request) throws I18NException {
    return null;
  }


  public AssignaturaAlumneJPA findByPrimaryKey(HttpServletRequest request, java.lang.Long id) throws I18NException {
    return (AssignaturaAlumneJPA) assignaturaAlumneEjb.findByPrimaryKey(id);
  }


  public AssignaturaAlumneJPA create(HttpServletRequest request, AssignaturaAlumneJPA assignaturaAlumne)
    throws I18NException, I18NValidationException {
    return (AssignaturaAlumneJPA) assignaturaAlumneEjb.create(assignaturaAlumne);
  }


  public AssignaturaAlumneJPA update(HttpServletRequest request, AssignaturaAlumneJPA assignaturaAlumne)
    throws I18NException, I18NValidationException {
    return (AssignaturaAlumneJPA) assignaturaAlumneEjb.update(assignaturaAlumne);
  }


  public void delete(HttpServletRequest request, AssignaturaAlumne assignaturaAlumne) throws I18NException {
    assignaturaAlumneEjb.delete(assignaturaAlumne);
  }

} // Final de Classe

