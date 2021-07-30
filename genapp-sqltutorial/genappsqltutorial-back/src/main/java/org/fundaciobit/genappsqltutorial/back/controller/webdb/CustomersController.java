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
import org.fundaciobit.genappsqltutorial.back.form.webdb.CustomersForm;

import org.fundaciobit.genappsqltutorial.back.validator.webdb.CustomersWebValidator;

import org.fundaciobit.genappsqltutorial.persistence.CustomersJPA;
import org.fundaciobit.genappsqltutorial.model.entity.Customers;
import org.fundaciobit.genappsqltutorial.model.fields.*;

/**
 * Controller per gestionar un Customers
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * 
 * @author GenApp
 */
@Controller
@RequestMapping(value = "/webdb/customers")
@SessionAttributes(types = { CustomersForm.class, CustomersFilterForm.class })
public class CustomersController
    extends org.fundaciobit.genappsqltutorial.back.controller.GenAppSqlTutorialBaseController<Customers, java.lang.Long> implements CustomersFields {

  @EJB(mappedName = org.fundaciobit.genappsqltutorial.ejb.CustomersService.JNDI_NAME)
  protected org.fundaciobit.genappsqltutorial.ejb.CustomersService customersEjb;

  @Autowired
  private CustomersWebValidator customersWebValidator;

  @Autowired
  protected CustomersRefList customersRefList;

  /**
   * Llistat de totes Customers
   */
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public String llistat(HttpServletRequest request,
    HttpServletResponse response) throws I18NException {
    CustomersFilterForm ff;
    ff = (CustomersFilterForm) request.getSession().getAttribute(getSessionAttributeFilterForm());
    int pagina = (ff == null)? 1: ff.getPage();
    return "redirect:" + getContextWeb() + "/list/" + pagina;
  }

  /**
   * Primera peticio per llistar Customers de forma paginada
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
    llistat(mav, request, getCustomersFilterForm(pagina, mav, request));
    return mav;
  }

  public CustomersFilterForm getCustomersFilterForm(Integer pagina, ModelAndView mav,
    HttpServletRequest request) throws I18NException {
    CustomersFilterForm customersFilterForm;
    customersFilterForm = (CustomersFilterForm) request.getSession().getAttribute(getSessionAttributeFilterForm());
    if(customersFilterForm == null) {
      customersFilterForm = new CustomersFilterForm();
      customersFilterForm.setContexte(getContextWeb());
      customersFilterForm.setEntityNameCode(getEntityNameCode());
      customersFilterForm.setEntityNameCodePlural(getEntityNameCodePlural());
      customersFilterForm.setNou(true);
    } else {
      customersFilterForm.setNou(false);
    }
    customersFilterForm.setPage(pagina == null ? 1 : pagina);
    return customersFilterForm;
  }

  /**
   * Segona i següent peticions per llistar Customers de forma paginada
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
      @ModelAttribute CustomersFilterForm filterForm) throws I18NException {
    if(!isActiveList()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }

    ModelAndView mav = new ModelAndView(getTileList());

    filterForm.setPage(pagina == null ? 1 : pagina);
    // Actualitza el filter form

    request.getSession().setAttribute(getSessionAttributeFilterForm(), filterForm);
    filterForm = getCustomersFilterForm(pagina, mav, request);

    llistat(mav, request, filterForm);
    return mav;
  }

  /**
   * Codi centralitzat de llistat de Customers de forma paginada.
   * 
   * @param request
   * @param filterForm
   * @param pagina
   * @return
   * @throws I18NException
   */
  protected List<Customers> llistat(ModelAndView mav, HttpServletRequest request,
     CustomersFilterForm filterForm) throws I18NException {

    int pagina = filterForm.getPage();
    request.getSession().setAttribute(getSessionAttributeFilterForm(), filterForm);

    captureSearchByValueOfAdditionalFields(request, filterForm);

    preList(request, mav, filterForm);

    List<Customers> customers = processarLlistat(customersEjb,
        filterForm, pagina, getAdditionalCondition(request), mav);

    mav.addObject("customersItems", customers);

    mav.addObject("customersFilterForm", filterForm);

    fillReferencesForList(filterForm,request, mav, customers, (List<GroupByItem>)mav.getModel().get("groupby_items"));

    postList(request, mav, filterForm, customers);

    return customers;
  }


  public Map<Field<?>, GroupByItem> fillReferencesForList(CustomersFilterForm filterForm,
    HttpServletRequest request, ModelAndView mav,
      List<Customers> list, List<GroupByItem> groupItems) throws I18NException {
    Map<Field<?>, GroupByItem> groupByItemsMap = new HashMap<Field<?>, GroupByItem>();
    for (GroupByItem groupByItem : groupItems) {
      groupByItemsMap.put(groupByItem.getField(),groupByItem);
    }


    return groupByItemsMap;
  }

  @RequestMapping(value = "/export/{dataExporterID}", method = RequestMethod.POST)
  public void exportList(@PathVariable("dataExporterID") String dataExporterID,
    HttpServletRequest request, HttpServletResponse response,
    CustomersFilterForm filterForm) throws Exception, I18NException {

    ModelAndView mav = new ModelAndView(getTileList());
    List<Customers> list = llistat(mav, request, filterForm);
    Field<?>[] allFields = ALL_CUSTOMERS_FIELDS;

    java.util.Map<Field<?>, java.util.Map<String, String>> __mapping;
    __mapping = new java.util.HashMap<Field<?>, java.util.Map<String, String>>();
    exportData(request, response, dataExporterID, filterForm,
          list, allFields, __mapping, PRIMARYKEY_FIELDS);
  }



  /**
   * Carregar el formulari per un nou Customers
   */
  @RequestMapping(value = "/new", method = RequestMethod.GET)
  public ModelAndView crearCustomersGet(HttpServletRequest request,
      HttpServletResponse response) throws I18NException {

    if(!isActiveFormNew()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }
    ModelAndView mav = new ModelAndView(getTileForm());
    CustomersForm customersForm = getCustomersForm(null, false, request, mav);
    mav.addObject("customersForm" ,customersForm);
    fillReferencesForForm(customersForm, request, mav);
  
    return mav;
  }
  
  /**
   * 
   * @return
   * @throws Exception
   */
  public CustomersForm getCustomersForm(CustomersJPA _jpa,
       boolean __isView, HttpServletRequest request, ModelAndView mav) throws I18NException {
    CustomersForm customersForm;
    if(_jpa == null) {
      customersForm = new CustomersForm(new CustomersJPA(), true);
    } else {
      customersForm = new CustomersForm(_jpa, false);
      customersForm.setView(__isView);
    }
    customersForm.setContexte(getContextWeb());
    customersForm.setEntityNameCode(getEntityNameCode());
    customersForm.setEntityNameCodePlural(getEntityNameCodePlural());
    return customersForm;
  }

  public void fillReferencesForForm(CustomersForm customersForm,
    HttpServletRequest request, ModelAndView mav) throws I18NException {
    
  }

  /**
   * Guardar un nou Customers
   */
  @RequestMapping(value = "/new", method = RequestMethod.POST)
  public String crearCustomersPost(@ModelAttribute CustomersForm customersForm,
      BindingResult result, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    if(!isActiveFormNew()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }

    CustomersJPA customers = customersForm.getCustomers();

    try {
      preValidate(request, customersForm, result);
      getWebValidator().validate(customersForm, result);
      postValidate(request,customersForm, result);

      if (result.hasErrors()) {
        result.reject("error.form");
        return getTileForm();
      } else {
        customers = create(request, customers);
        createMessageSuccess(request, "success.creation", customers.getCustomerid());
        customersForm.setCustomers(customers);
        return getRedirectWhenCreated(request, customersForm);
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

  @RequestMapping(value = "/view/{customerid}", method = RequestMethod.GET)
  public ModelAndView veureCustomersGet(@PathVariable("customerid") java.lang.Long customerid,
      HttpServletRequest request,
      HttpServletResponse response) throws I18NException {
      return editAndViewCustomersGet(customerid,
        request, response, true);
  }


  protected ModelAndView editAndViewCustomersGet(@PathVariable("customerid") java.lang.Long customerid,
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
    CustomersJPA customers = findByPrimaryKey(request, customerid);

    if (customers == null) {
      createMessageWarning(request, "error.notfound", customerid);
      new ModelAndView(new RedirectView(getRedirectWhenCancel(request, customerid), true));
      return llistatPaginat(request, response, 1);
    } else {
      ModelAndView mav = new ModelAndView(getTileForm());
      CustomersForm customersForm = getCustomersForm(customers, __isView, request, mav);
      customersForm.setView(__isView);
      if(__isView) {
        customersForm.setAllFieldsReadOnly(ALL_CUSTOMERS_FIELDS);
        customersForm.setSaveButtonVisible(false);
        customersForm.setDeleteButtonVisible(false);
      }
      fillReferencesForForm(customersForm, request, mav);
      mav.addObject("customersForm", customersForm);
      return mav;
    }
  }


  /**
   * Carregar el formulari per modificar un Customers existent
   */
  @RequestMapping(value = "/{customerid}/edit", method = RequestMethod.GET)
  public ModelAndView editarCustomersGet(@PathVariable("customerid") java.lang.Long customerid,
      HttpServletRequest request,
      HttpServletResponse response) throws I18NException {
      return editAndViewCustomersGet(customerid,
        request, response, false);
  }



  /**
   * Editar un Customers existent
   */
  @RequestMapping(value = "/{customerid}/edit", method = RequestMethod.POST)
  public String editarCustomersPost(@ModelAttribute CustomersForm customersForm,
      BindingResult result, SessionStatus status, HttpServletRequest request,
      HttpServletResponse response) throws I18NException {

    if(!isActiveFormEdit()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }
    CustomersJPA customers = customersForm.getCustomers();

    try {
      preValidate(request, customersForm, result);
      getWebValidator().validate(customersForm, result);
      postValidate(request, customersForm, result);

      if (result.hasErrors()) {
        result.reject("error.form");
        return getTileForm();
      } else {
        customers = update(request, customers);
        createMessageSuccess(request, "success.modification", customers.getCustomerid());
        status.setComplete();
        return getRedirectWhenModified(request, customersForm, null);
      }
    } catch (Throwable __e) {
      if (__e instanceof I18NValidationException) {
        ValidationWebUtils.addFieldErrorsToBindingResult(result, (I18NValidationException)__e);
        return getTileForm();
      }
      String msg = createMessageError(request, "error.modification",
          customers.getCustomerid(), __e);
      log.error(msg, __e);
      return getRedirectWhenModified(request, customersForm, __e);
    }

  }


  /**
   * Eliminar un Customers existent
   */
  @RequestMapping(value = "/{customerid}/delete")
  public String eliminarCustomers(@PathVariable("customerid") java.lang.Long customerid,
      HttpServletRequest request,HttpServletResponse response) {

    if(!isActiveDelete()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }
    try {
      Customers customers = customersEjb.findByPrimaryKey(customerid);
      if (customers == null) {
        String __msg =createMessageError(request, "error.notfound", customerid);
        return getRedirectWhenDelete(request, customerid, new Exception(__msg));
      } else {
        delete(request, customers);
        createMessageSuccess(request, "success.deleted", customerid);
        return getRedirectWhenDelete(request, customerid,null);
      }

    } catch (Throwable e) {
      String msg = createMessageError(request, "error.deleting", customerid, e);
      log.error(msg, e);
      return getRedirectWhenDelete(request, customerid, e);
    }
  }


@RequestMapping(value = "/deleteSelected", method = RequestMethod.POST)
public String deleteSelected(HttpServletRequest request,
    HttpServletResponse response,
    @ModelAttribute CustomersFilterForm filterForm) throws Exception {

  if(!isActiveDelete()) {
    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    return null;
  }
  
  String[] seleccionats = filterForm.getSelectedItems();
  String redirect = null;
  if (seleccionats != null && seleccionats.length != 0) {
    for (int i = 0; i < seleccionats.length; i++) {
      redirect = eliminarCustomers(stringToPK(seleccionats[i]), request, response);
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
  public String[] getArgumentsMissatge(Object __customerid, Throwable e) {
    java.lang.Long customerid = (java.lang.Long)__customerid;
    String exceptionMsg = "";
    if (e != null) {
      if (e instanceof I18NException) {
        exceptionMsg = I18NUtils.getMessage((I18NException)e);
      } else if (e instanceof I18NValidationException) {
      } else {
        exceptionMsg = e.getMessage();
      };
    };
    if (customerid == null) {
      return new String[] { I18NUtils.tradueix(getEntityNameCode()),
         getPrimaryKeyColumnsTranslated(), null, exceptionMsg };
    } else {
      return new String[] { I18NUtils.tradueix(getEntityNameCode()),
        getPrimaryKeyColumnsTranslated(),
         String.valueOf(customerid),
 exceptionMsg };
    }
  }

  public String getEntityNameCode() {
    return "customers.customers";
  }

  public String getEntityNameCodePlural() {
    return "customers.customers.plural";
  }

  public String getPrimaryKeyColumnsTranslated() {
    return  I18NUtils.tradueix("customers.customerid");
  }

  @InitBinder("customersFilterForm")
  public void initBinderFilterForm(WebDataBinder binder) {
    super.initBinder(binder);
  }

  @InitBinder("customersForm")
  public void initBinderForm(WebDataBinder binder) {
    super.initBinder(binder);

    binder.setValidator(getWebValidator());

    binder.setDisallowedFields("customerid");

  }

  public CustomersWebValidator getWebValidator() {
    return customersWebValidator;
  }


  public void setWebValidator(CustomersWebValidator __val) {
    if (__val != null) {
      this.customersWebValidator= __val;
    }
  }


  /**
   * Entra aqui al pitjar el boto cancel en el llistat de Customers
   */
  @RequestMapping(value = "/{customerid}/cancel")
  public String cancelCustomers(@PathVariable("customerid") java.lang.Long customerid,
      HttpServletRequest request,HttpServletResponse response) {
     return getRedirectWhenCancel(request, customerid);
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


  public void preValidate(HttpServletRequest request,CustomersForm customersForm , BindingResult result)  throws I18NException {
  }

  public void postValidate(HttpServletRequest request,CustomersForm customersForm, BindingResult result)  throws I18NException {
  }

  public void preList(HttpServletRequest request, ModelAndView mav, CustomersFilterForm filterForm)  throws I18NException {
  }

  public void postList(HttpServletRequest request, ModelAndView mav, CustomersFilterForm filterForm,  List<Customers> list) throws I18NException {
  }

  public String getRedirectWhenCreated(HttpServletRequest request, CustomersForm customersForm) {
    return "redirect:" + getContextWeb() + "/list/1";
  }

  public String getRedirectWhenModified(HttpServletRequest request, CustomersForm customersForm, Throwable __e) {
    if (__e == null) {
      return "redirect:" + getContextWeb() + "/list";
    } else {
      return  getTileForm();
    }
  }

  public String getRedirectWhenDelete(HttpServletRequest request, java.lang.Long customerid, Throwable __e) {
    return "redirect:" + getContextWeb() + "/list";
  }

  public String getRedirectWhenCancel(HttpServletRequest request, java.lang.Long customerid) {
    return "redirect:" + getContextWeb() + "/list";
  }

  public String getTileForm() {
    return "customersFormWebDB";
  }

  public String getTileList() {
    return "customersListWebDB";
  }

  @Override
  /** Ha de ser igual que el RequestMapping de la Classe */
  public String getContextWeb() {
    RequestMapping rm = AnnotationUtils.findAnnotation(this.getClass(), RequestMapping.class);
    return rm.value()[0];
  }

  public String getSessionAttributeFilterForm() {
    return "CustomersWebDB_FilterForm";
  }



  public Where getAdditionalCondition(HttpServletRequest request) throws I18NException {
    return null;
  }


  public CustomersJPA findByPrimaryKey(HttpServletRequest request, java.lang.Long customerid) throws I18NException {
    return (CustomersJPA) customersEjb.findByPrimaryKey(customerid);
  }


  public CustomersJPA create(HttpServletRequest request, CustomersJPA customers)
    throws Exception,I18NException, I18NValidationException {
    return (CustomersJPA) customersEjb.create(customers);
  }


  public CustomersJPA update(HttpServletRequest request, CustomersJPA customers)
    throws Exception,I18NException, I18NValidationException {
    return (CustomersJPA) customersEjb.update(customers);
  }


  public void delete(HttpServletRequest request, Customers customers) throws Exception,I18NException {
    customersEjb.delete(customers);
  }

} // Final de Classe

