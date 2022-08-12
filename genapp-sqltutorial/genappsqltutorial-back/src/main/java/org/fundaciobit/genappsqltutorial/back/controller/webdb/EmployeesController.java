package org.fundaciobit.genappsqltutorial.back.controller.webdb;

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

import org.fundaciobit.genappsqltutorial.back.form.webdb.*;
import org.fundaciobit.genappsqltutorial.back.form.webdb.EmployeesForm;

import org.fundaciobit.genappsqltutorial.back.validator.webdb.EmployeesWebValidator;

import org.fundaciobit.genappsqltutorial.persistence.EmployeesJPA;
import org.fundaciobit.genappsqltutorial.model.entity.Employees;
import org.fundaciobit.genappsqltutorial.model.fields.*;

/**
 * Controller per gestionar un Employees
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * 
 * @author GenApp
 */
@Controller
@RequestMapping(value = "/webdb/employees")
@SessionAttributes(types = { EmployeesForm.class, EmployeesFilterForm.class })
public class EmployeesController
    extends org.fundaciobit.genappsqltutorial.back.controller.GenAppSqlTutorialBaseController<Employees, java.lang.Long> implements EmployeesFields {

  @EJB(mappedName = org.fundaciobit.genappsqltutorial.ejb.EmployeesService.JNDI_NAME)
  protected org.fundaciobit.genappsqltutorial.ejb.EmployeesService employeesEjb;

  @Autowired
  private EmployeesWebValidator employeesWebValidator;

  @Autowired
  protected EmployeesRefList employeesRefList;

  /**
   * Llistat de totes Employees
   */
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public String llistat(HttpServletRequest request,
    HttpServletResponse response) throws I18NException {
    EmployeesFilterForm ff;
    ff = (EmployeesFilterForm) request.getSession().getAttribute(getSessionAttributeFilterForm());
    int pagina = (ff == null)? 1: ff.getPage();
    return "redirect:" + getContextWeb() + "/list/" + pagina;
  }

  /**
   * Primera peticio per llistar Employees de forma paginada
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
    llistat(mav, request, getEmployeesFilterForm(pagina, mav, request));
    return mav;
  }

  public EmployeesFilterForm getEmployeesFilterForm(Integer pagina, ModelAndView mav,
    HttpServletRequest request) throws I18NException {
    EmployeesFilterForm employeesFilterForm;
    employeesFilterForm = (EmployeesFilterForm) request.getSession().getAttribute(getSessionAttributeFilterForm());
    if(employeesFilterForm == null) {
      employeesFilterForm = new EmployeesFilterForm();
      employeesFilterForm.setContexte(getContextWeb());
      employeesFilterForm.setEntityNameCode(getEntityNameCode());
      employeesFilterForm.setEntityNameCodePlural(getEntityNameCodePlural());
      employeesFilterForm.setNou(true);
    } else {
      employeesFilterForm.setNou(false);
    }
    employeesFilterForm.setPage(pagina == null ? 1 : pagina);
    return employeesFilterForm;
  }

  /**
   * Segona i següent peticions per llistar Employees de forma paginada
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
      @ModelAttribute EmployeesFilterForm filterForm) throws I18NException {
    if(!isActiveList()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }

    ModelAndView mav = new ModelAndView(getTileList());

    filterForm.setPage(pagina == null ? 1 : pagina);
    // Actualitza el filter form

    request.getSession().setAttribute(getSessionAttributeFilterForm(), filterForm);
    filterForm = getEmployeesFilterForm(pagina, mav, request);

    llistat(mav, request, filterForm);
    return mav;
  }

  /**
   * Codi centralitzat de llistat de Employees de forma paginada.
   * 
   * @param request
   * @param filterForm
   * @param pagina
   * @return
   * @throws I18NException
   */
  protected List<Employees> llistat(ModelAndView mav, HttpServletRequest request,
     EmployeesFilterForm filterForm) throws I18NException {

    int pagina = filterForm.getPage();
    request.getSession().setAttribute(getSessionAttributeFilterForm(), filterForm);

    captureSearchByValueOfAdditionalFields(request, filterForm);

    preList(request, mav, filterForm);

    List<Employees> employees = processarLlistat(employeesEjb,
        filterForm, pagina, getAdditionalCondition(request), mav);

    mav.addObject("employeesItems", employees);

    mav.addObject("employeesFilterForm", filterForm);

    fillReferencesForList(filterForm,request, mav, employees, (List<GroupByItem>)mav.getModel().get("groupby_items"));

    postList(request, mav, filterForm, employees);

    return employees;
  }


  public Map<Field<?>, GroupByItem> fillReferencesForList(EmployeesFilterForm filterForm,
    HttpServletRequest request, ModelAndView mav,
      List<Employees> list, List<GroupByItem> groupItems) throws I18NException {
    Map<Field<?>, GroupByItem> groupByItemsMap = new HashMap<Field<?>, GroupByItem>();
    for (GroupByItem groupByItem : groupItems) {
      groupByItemsMap.put(groupByItem.getField(),groupByItem);
    }


    return groupByItemsMap;
  }

  @RequestMapping(value = "/export/{dataExporterID}", method = RequestMethod.POST)
  public void exportList(@PathVariable("dataExporterID") String dataExporterID,
    HttpServletRequest request, HttpServletResponse response,
    EmployeesFilterForm filterForm) throws Exception, I18NException {

    ModelAndView mav = new ModelAndView(getTileList());
    List<Employees> list = llistat(mav, request, filterForm);
    Field<?>[] allFields = ALL_EMPLOYEES_FIELDS;

    java.util.Map<Field<?>, java.util.Map<String, String>> __mapping;
    __mapping = new java.util.HashMap<Field<?>, java.util.Map<String, String>>();
    exportData(request, response, dataExporterID, filterForm,
          list, allFields, __mapping, PRIMARYKEY_FIELDS);
  }



  /**
   * Carregar el formulari per un nou Employees
   */
  @RequestMapping(value = "/new", method = RequestMethod.GET)
  public ModelAndView crearEmployeesGet(HttpServletRequest request,
      HttpServletResponse response) throws I18NException {

    if(!isActiveFormNew()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }
    ModelAndView mav = new ModelAndView(getTileForm());
    EmployeesForm employeesForm = getEmployeesForm(null, false, request, mav);
    mav.addObject("employeesForm" ,employeesForm);
    fillReferencesForForm(employeesForm, request, mav);
  
    return mav;
  }
  
  /**
   * 
   * @return
   * @throws Exception
   */
  public EmployeesForm getEmployeesForm(EmployeesJPA _jpa,
       boolean __isView, HttpServletRequest request, ModelAndView mav) throws I18NException {
    EmployeesForm employeesForm;
    if(_jpa == null) {
      employeesForm = new EmployeesForm(new EmployeesJPA(), true);
    } else {
      employeesForm = new EmployeesForm(_jpa, false);
      employeesForm.setView(__isView);
    }
    employeesForm.setContexte(getContextWeb());
    employeesForm.setEntityNameCode(getEntityNameCode());
    employeesForm.setEntityNameCodePlural(getEntityNameCodePlural());
    return employeesForm;
  }

  public void fillReferencesForForm(EmployeesForm employeesForm,
    HttpServletRequest request, ModelAndView mav) throws I18NException {
    
  }

  /**
   * Guardar un nou Employees
   */
  @RequestMapping(value = "/new", method = RequestMethod.POST)
  public String crearEmployeesPost(@ModelAttribute EmployeesForm employeesForm,
      BindingResult result, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    if(!isActiveFormNew()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }

    EmployeesJPA employees = employeesForm.getEmployees();

    try {
      preValidate(request, employeesForm, result);
      getWebValidator().validate(employeesForm, result);
      postValidate(request,employeesForm, result);

      if (result.hasErrors()) {
        result.reject("error.form");
        return getTileForm();
      } else {
        employees = create(request, employees);
        createMessageSuccess(request, "success.creation", employees.getEmployeeid());
        employeesForm.setEmployees(employees);
        return getRedirectWhenCreated(request, employeesForm);
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

  @RequestMapping(value = "/view/{employeeid}", method = RequestMethod.GET)
  public ModelAndView veureEmployeesGet(@PathVariable("employeeid") java.lang.Long employeeid,
      HttpServletRequest request,
      HttpServletResponse response) throws I18NException {
      return editAndViewEmployeesGet(employeeid,
        request, response, true);
  }


  protected ModelAndView editAndViewEmployeesGet(@PathVariable("employeeid") java.lang.Long employeeid,
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
    EmployeesJPA employees = findByPrimaryKey(request, employeeid);

    if (employees == null) {
      createMessageWarning(request, "error.notfound", employeeid);
      new ModelAndView(new RedirectView(getRedirectWhenCancel(request, employeeid), true));
      return llistatPaginat(request, response, 1);
    } else {
      ModelAndView mav = new ModelAndView(getTileForm());
      EmployeesForm employeesForm = getEmployeesForm(employees, __isView, request, mav);
      employeesForm.setView(__isView);
      if(__isView) {
        employeesForm.setAllFieldsReadOnly(ALL_EMPLOYEES_FIELDS);
        employeesForm.setSaveButtonVisible(false);
        employeesForm.setDeleteButtonVisible(false);
      }
      fillReferencesForForm(employeesForm, request, mav);
      mav.addObject("employeesForm", employeesForm);
      return mav;
    }
  }


  /**
   * Carregar el formulari per modificar un Employees existent
   */
  @RequestMapping(value = "/{employeeid}/edit", method = RequestMethod.GET)
  public ModelAndView editarEmployeesGet(@PathVariable("employeeid") java.lang.Long employeeid,
      HttpServletRequest request,
      HttpServletResponse response) throws I18NException {
      return editAndViewEmployeesGet(employeeid,
        request, response, false);
  }



  /**
   * Editar un Employees existent
   */
  @RequestMapping(value = "/{employeeid}/edit", method = RequestMethod.POST)
  public String editarEmployeesPost(@ModelAttribute EmployeesForm employeesForm,
      BindingResult result, SessionStatus status, HttpServletRequest request,
      HttpServletResponse response) throws I18NException {

    if(!isActiveFormEdit()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }
    EmployeesJPA employees = employeesForm.getEmployees();

    try {
      preValidate(request, employeesForm, result);
      getWebValidator().validate(employeesForm, result);
      postValidate(request, employeesForm, result);

      if (result.hasErrors()) {
        result.reject("error.form");
        return getTileForm();
      } else {
        employees = update(request, employees);
        createMessageSuccess(request, "success.modification", employees.getEmployeeid());
        status.setComplete();
        return getRedirectWhenModified(request, employeesForm, null);
      }
    } catch (Throwable __e) {
      if (__e instanceof I18NValidationException) {
        ValidationWebUtils.addFieldErrorsToBindingResult(result, (I18NValidationException)__e);
        return getTileForm();
      }
      String msg = createMessageError(request, "error.modification",
          employees.getEmployeeid(), __e);
      log.error(msg, __e);
      return getRedirectWhenModified(request, employeesForm, __e);
    }

  }


  /**
   * Eliminar un Employees existent
   */
  @RequestMapping(value = "/{employeeid}/delete")
  public String eliminarEmployees(@PathVariable("employeeid") java.lang.Long employeeid,
      HttpServletRequest request,HttpServletResponse response) {

    if(!isActiveDelete()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }
    try {
      Employees employees = employeesEjb.findByPrimaryKey(employeeid);
      if (employees == null) {
        String __msg =createMessageError(request, "error.notfound", employeeid);
        return getRedirectWhenDelete(request, employeeid, new Exception(__msg));
      } else {
        delete(request, employees);
        createMessageSuccess(request, "success.deleted", employeeid);
        return getRedirectWhenDelete(request, employeeid,null);
      }

    } catch (Throwable e) {
      String msg = createMessageError(request, "error.deleting", employeeid, e);
      log.error(msg, e);
      return getRedirectWhenDelete(request, employeeid, e);
    }
  }


@RequestMapping(value = "/deleteSelected", method = RequestMethod.POST)
public String deleteSelected(HttpServletRequest request,
    HttpServletResponse response,
    @ModelAttribute EmployeesFilterForm filterForm) throws Exception {

  if(!isActiveDelete()) {
    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    return null;
  }
  
  String[] seleccionats = filterForm.getSelectedItems();
  String redirect = null;
  if (seleccionats != null && seleccionats.length != 0) {
    for (int i = 0; i < seleccionats.length; i++) {
      redirect = eliminarEmployees(stringToPK(seleccionats[i]), request, response);
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
  public String[] getArgumentsMissatge(Object __employeeid, Throwable e) {
    java.lang.Long employeeid = (java.lang.Long)__employeeid;
    String exceptionMsg = "";
    if (e != null) {
      if (e instanceof I18NException) {
        exceptionMsg = I18NUtils.getMessage((I18NException)e);
      } else if (e instanceof I18NValidationException) {
      } else {
        exceptionMsg = e.getMessage();
      };
    };
    if (employeeid == null) {
      return new String[] { I18NUtils.tradueix(getEntityNameCode()),
         getPrimaryKeyColumnsTranslated(), null, exceptionMsg };
    } else {
      return new String[] { I18NUtils.tradueix(getEntityNameCode()),
        getPrimaryKeyColumnsTranslated(),
         String.valueOf(employeeid),
 exceptionMsg };
    }
  }

  public String getEntityNameCode() {
    return "employees.employees";
  }

  public String getEntityNameCodePlural() {
    return "employees.employees.plural";
  }

  public String getPrimaryKeyColumnsTranslated() {
    return  I18NUtils.tradueix("employees.employeeid");
  }

  @InitBinder("employeesFilterForm")
  public void initBinderFilterForm(WebDataBinder binder) {
    super.initBinder(binder);
  }

  @InitBinder("employeesForm")
  public void initBinderForm(WebDataBinder binder) {
    super.initBinder(binder);

    binder.setValidator(getWebValidator());


    initDisallowedFields(binder, "employees.employeeid");
  }

  public EmployeesWebValidator getWebValidator() {
    return employeesWebValidator;
  }


  public void setWebValidator(EmployeesWebValidator __val) {
    if (__val != null) {
      this.employeesWebValidator= __val;
    }
  }


  /**
   * Entra aqui al pitjar el boto cancel en el llistat de Employees
   */
  @RequestMapping(value = "/{employeeid}/cancel")
  public String cancelEmployees(@PathVariable("employeeid") java.lang.Long employeeid,
      HttpServletRequest request,HttpServletResponse response) {
     return getRedirectWhenCancel(request, employeeid);
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


  @Override
  /** Ha de ser igual que el RequestMapping de la Classe */
  public String getContextWeb() {
    RequestMapping rm = AnnotationUtils.findAnnotation(this.getClass(), RequestMapping.class);
    return rm.value()[0];
  }

  public void preValidate(HttpServletRequest request,EmployeesForm employeesForm , BindingResult result)  throws I18NException {
  }

  public void postValidate(HttpServletRequest request,EmployeesForm employeesForm, BindingResult result)  throws I18NException {
  }

  public void preList(HttpServletRequest request, ModelAndView mav, EmployeesFilterForm filterForm)  throws I18NException {
  }

  public void postList(HttpServletRequest request, ModelAndView mav, EmployeesFilterForm filterForm,  List<Employees> list) throws I18NException {
  }

  public String getRedirectWhenCreated(HttpServletRequest request, EmployeesForm employeesForm) {
    return "redirect:" + getContextWeb() + "/list/1";
  }

  public String getRedirectWhenModified(HttpServletRequest request, EmployeesForm employeesForm, Throwable __e) {
    if (__e == null) {
      return "redirect:" + getContextWeb() + "/list";
    } else {
      return  getTileForm();
    }
  }

  public String getRedirectWhenDelete(HttpServletRequest request, java.lang.Long employeeid, Throwable __e) {
    return "redirect:" + getContextWeb() + "/list";
  }

  public String getRedirectWhenCancel(HttpServletRequest request, java.lang.Long employeeid) {
    return "redirect:" + getContextWeb() + "/list";
  }

  public String getTileForm() {
    return "employeesFormWebDB";
  }

  public String getTileList() {
    return "employeesListWebDB";
  }

  public String getSessionAttributeFilterForm() {
    return "EmployeesWebDB_FilterForm";
  }



  public Where getAdditionalCondition(HttpServletRequest request) throws I18NException {
    return null;
  }


  public EmployeesJPA findByPrimaryKey(HttpServletRequest request, java.lang.Long employeeid) throws I18NException {
    return (EmployeesJPA) employeesEjb.findByPrimaryKey(employeeid);
  }


  public EmployeesJPA create(HttpServletRequest request, EmployeesJPA employees)
    throws I18NException, I18NValidationException {
    return (EmployeesJPA) employeesEjb.create(employees);
  }


  public EmployeesJPA update(HttpServletRequest request, EmployeesJPA employees)
    throws I18NException, I18NValidationException {
    return (EmployeesJPA) employeesEjb.update(employees);
  }


  public void delete(HttpServletRequest request, Employees employees) throws I18NException {
    employeesEjb.delete(employees);
  }

} // Final de Classe

