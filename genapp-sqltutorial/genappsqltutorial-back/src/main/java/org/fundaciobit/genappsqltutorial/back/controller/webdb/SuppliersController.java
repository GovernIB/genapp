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
import org.fundaciobit.genappsqltutorial.back.form.webdb.SuppliersForm;

import org.fundaciobit.genappsqltutorial.back.validator.webdb.SuppliersWebValidator;

import org.fundaciobit.genappsqltutorial.persistence.SuppliersJPA;
import org.fundaciobit.genappsqltutorial.model.entity.Suppliers;
import org.fundaciobit.genappsqltutorial.model.fields.*;

/**
 * Controller per gestionar un Suppliers
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * 
 * @author GenApp
 */
@Controller
@RequestMapping(value = "/webdb/suppliers")
@SessionAttributes(types = { SuppliersForm.class, SuppliersFilterForm.class })
public class SuppliersController
    extends org.fundaciobit.genappsqltutorial.back.controller.GenAppSqlTutorialBaseController<Suppliers, java.lang.Long> implements SuppliersFields {

  @EJB(mappedName = org.fundaciobit.genappsqltutorial.ejb.SuppliersService.JNDI_NAME)
  protected org.fundaciobit.genappsqltutorial.ejb.SuppliersService suppliersEjb;

  @Autowired
  private SuppliersWebValidator suppliersWebValidator;

  @Autowired
  protected SuppliersRefList suppliersRefList;

  /**
   * Llistat de totes Suppliers
   */
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public String llistat(HttpServletRequest request,
    HttpServletResponse response) throws I18NException {
    SuppliersFilterForm ff;
    ff = (SuppliersFilterForm) request.getSession().getAttribute(getSessionAttributeFilterForm());
    int pagina = (ff == null)? 1: ff.getPage();
    return "redirect:" + getContextWeb() + "/list/" + pagina;
  }

  /**
   * Primera peticio per llistar Suppliers de forma paginada
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
    llistat(mav, request, getSuppliersFilterForm(pagina, mav, request));
    return mav;
  }

  public SuppliersFilterForm getSuppliersFilterForm(Integer pagina, ModelAndView mav,
    HttpServletRequest request) throws I18NException {
    SuppliersFilterForm suppliersFilterForm;
    suppliersFilterForm = (SuppliersFilterForm) request.getSession().getAttribute(getSessionAttributeFilterForm());
    if(suppliersFilterForm == null) {
      suppliersFilterForm = new SuppliersFilterForm();
      suppliersFilterForm.setContexte(getContextWeb());
      suppliersFilterForm.setEntityNameCode(getEntityNameCode());
      suppliersFilterForm.setEntityNameCodePlural(getEntityNameCodePlural());
      suppliersFilterForm.setNou(true);
    } else {
      suppliersFilterForm.setNou(false);
    }
    suppliersFilterForm.setPage(pagina == null ? 1 : pagina);
    return suppliersFilterForm;
  }

  /**
   * Segona i següent peticions per llistar Suppliers de forma paginada
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
      @ModelAttribute SuppliersFilterForm filterForm) throws I18NException {
    if(!isActiveList()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }

    ModelAndView mav = new ModelAndView(getTileList());

    filterForm.setPage(pagina == null ? 1 : pagina);
    // Actualitza el filter form

    request.getSession().setAttribute(getSessionAttributeFilterForm(), filterForm);
    filterForm = getSuppliersFilterForm(pagina, mav, request);

    llistat(mav, request, filterForm);
    return mav;
  }

  /**
   * Codi centralitzat de llistat de Suppliers de forma paginada.
   * 
   * @param request
   * @param filterForm
   * @param pagina
   * @return
   * @throws I18NException
   */
  protected List<Suppliers> llistat(ModelAndView mav, HttpServletRequest request,
     SuppliersFilterForm filterForm) throws I18NException {

    int pagina = filterForm.getPage();
    request.getSession().setAttribute(getSessionAttributeFilterForm(), filterForm);

    captureSearchByValueOfAdditionalFields(request, filterForm);

    preList(request, mav, filterForm);

    List<Suppliers> suppliers = processarLlistat(suppliersEjb,
        filterForm, pagina, getAdditionalCondition(request), mav);

    mav.addObject("suppliersItems", suppliers);

    mav.addObject("suppliersFilterForm", filterForm);

    fillReferencesForList(filterForm,request, mav, suppliers, (List<GroupByItem>)mav.getModel().get("groupby_items"));

    postList(request, mav, filterForm, suppliers);

    return suppliers;
  }


  public Map<Field<?>, GroupByItem> fillReferencesForList(SuppliersFilterForm filterForm,
    HttpServletRequest request, ModelAndView mav,
      List<Suppliers> list, List<GroupByItem> groupItems) throws I18NException {
    Map<Field<?>, GroupByItem> groupByItemsMap = new HashMap<Field<?>, GroupByItem>();
    for (GroupByItem groupByItem : groupItems) {
      groupByItemsMap.put(groupByItem.getField(),groupByItem);
    }


    return groupByItemsMap;
  }

  @RequestMapping(value = "/export/{dataExporterID}", method = RequestMethod.POST)
  public void exportList(@PathVariable("dataExporterID") String dataExporterID,
    HttpServletRequest request, HttpServletResponse response,
    SuppliersFilterForm filterForm) throws Exception, I18NException {

    ModelAndView mav = new ModelAndView(getTileList());
    List<Suppliers> list = llistat(mav, request, filterForm);
    Field<?>[] allFields = ALL_SUPPLIERS_FIELDS;

    java.util.Map<Field<?>, java.util.Map<String, String>> __mapping;
    __mapping = new java.util.HashMap<Field<?>, java.util.Map<String, String>>();
    exportData(request, response, dataExporterID, filterForm,
          list, allFields, __mapping, PRIMARYKEY_FIELDS);
  }



  /**
   * Carregar el formulari per un nou Suppliers
   */
  @RequestMapping(value = "/new", method = RequestMethod.GET)
  public ModelAndView crearSuppliersGet(HttpServletRequest request,
      HttpServletResponse response) throws I18NException {

    if(!isActiveFormNew()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }
    ModelAndView mav = new ModelAndView(getTileForm());
    SuppliersForm suppliersForm = getSuppliersForm(null, false, request, mav);
    mav.addObject("suppliersForm" ,suppliersForm);
    fillReferencesForForm(suppliersForm, request, mav);
  
    return mav;
  }
  
  /**
   * 
   * @return
   * @throws Exception
   */
  public SuppliersForm getSuppliersForm(SuppliersJPA _jpa,
       boolean __isView, HttpServletRequest request, ModelAndView mav) throws I18NException {
    SuppliersForm suppliersForm;
    if(_jpa == null) {
      suppliersForm = new SuppliersForm(new SuppliersJPA(), true);
    } else {
      suppliersForm = new SuppliersForm(_jpa, false);
      suppliersForm.setView(__isView);
    }
    suppliersForm.setContexte(getContextWeb());
    suppliersForm.setEntityNameCode(getEntityNameCode());
    suppliersForm.setEntityNameCodePlural(getEntityNameCodePlural());
    return suppliersForm;
  }

  public void fillReferencesForForm(SuppliersForm suppliersForm,
    HttpServletRequest request, ModelAndView mav) throws I18NException {
    
  }

  /**
   * Guardar un nou Suppliers
   */
  @RequestMapping(value = "/new", method = RequestMethod.POST)
  public String crearSuppliersPost(@ModelAttribute SuppliersForm suppliersForm,
      BindingResult result, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    if(!isActiveFormNew()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }

    SuppliersJPA suppliers = suppliersForm.getSuppliers();

    try {
      preValidate(request, suppliersForm, result);
      getWebValidator().validate(suppliersForm, result);
      postValidate(request,suppliersForm, result);

      if (result.hasErrors()) {
        result.reject("error.form");
        return getTileForm();
      } else {
        suppliers = create(request, suppliers);
        createMessageSuccess(request, "success.creation", suppliers.getSupplierid());
        suppliersForm.setSuppliers(suppliers);
        return getRedirectWhenCreated(request, suppliersForm);
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

  @RequestMapping(value = "/view/{supplierid}", method = RequestMethod.GET)
  public ModelAndView veureSuppliersGet(@PathVariable("supplierid") java.lang.Long supplierid,
      HttpServletRequest request,
      HttpServletResponse response) throws I18NException {
      return editAndViewSuppliersGet(supplierid,
        request, response, true);
  }


  protected ModelAndView editAndViewSuppliersGet(@PathVariable("supplierid") java.lang.Long supplierid,
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
    SuppliersJPA suppliers = findByPrimaryKey(request, supplierid);

    if (suppliers == null) {
      createMessageWarning(request, "error.notfound", supplierid);
      new ModelAndView(new RedirectView(getRedirectWhenCancel(request, supplierid), true));
      return llistatPaginat(request, response, 1);
    } else {
      ModelAndView mav = new ModelAndView(getTileForm());
      SuppliersForm suppliersForm = getSuppliersForm(suppliers, __isView, request, mav);
      suppliersForm.setView(__isView);
      if(__isView) {
        suppliersForm.setAllFieldsReadOnly(ALL_SUPPLIERS_FIELDS);
        suppliersForm.setSaveButtonVisible(false);
        suppliersForm.setDeleteButtonVisible(false);
      }
      fillReferencesForForm(suppliersForm, request, mav);
      mav.addObject("suppliersForm", suppliersForm);
      return mav;
    }
  }


  /**
   * Carregar el formulari per modificar un Suppliers existent
   */
  @RequestMapping(value = "/{supplierid}/edit", method = RequestMethod.GET)
  public ModelAndView editarSuppliersGet(@PathVariable("supplierid") java.lang.Long supplierid,
      HttpServletRequest request,
      HttpServletResponse response) throws I18NException {
      return editAndViewSuppliersGet(supplierid,
        request, response, false);
  }



  /**
   * Editar un Suppliers existent
   */
  @RequestMapping(value = "/{supplierid}/edit", method = RequestMethod.POST)
  public String editarSuppliersPost(@ModelAttribute SuppliersForm suppliersForm,
      BindingResult result, SessionStatus status, HttpServletRequest request,
      HttpServletResponse response) throws I18NException {

    if(!isActiveFormEdit()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }
    SuppliersJPA suppliers = suppliersForm.getSuppliers();

    try {
      preValidate(request, suppliersForm, result);
      getWebValidator().validate(suppliersForm, result);
      postValidate(request, suppliersForm, result);

      if (result.hasErrors()) {
        result.reject("error.form");
        return getTileForm();
      } else {
        suppliers = update(request, suppliers);
        createMessageSuccess(request, "success.modification", suppliers.getSupplierid());
        status.setComplete();
        return getRedirectWhenModified(request, suppliersForm, null);
      }
    } catch (Throwable __e) {
      if (__e instanceof I18NValidationException) {
        ValidationWebUtils.addFieldErrorsToBindingResult(result, (I18NValidationException)__e);
        return getTileForm();
      }
      String msg = createMessageError(request, "error.modification",
          suppliers.getSupplierid(), __e);
      log.error(msg, __e);
      return getRedirectWhenModified(request, suppliersForm, __e);
    }

  }


  /**
   * Eliminar un Suppliers existent
   */
  @RequestMapping(value = "/{supplierid}/delete")
  public String eliminarSuppliers(@PathVariable("supplierid") java.lang.Long supplierid,
      HttpServletRequest request,HttpServletResponse response) {

    if(!isActiveDelete()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }
    try {
      Suppliers suppliers = suppliersEjb.findByPrimaryKey(supplierid);
      if (suppliers == null) {
        String __msg =createMessageError(request, "error.notfound", supplierid);
        return getRedirectWhenDelete(request, supplierid, new Exception(__msg));
      } else {
        delete(request, suppliers);
        createMessageSuccess(request, "success.deleted", supplierid);
        return getRedirectWhenDelete(request, supplierid,null);
      }

    } catch (Throwable e) {
      String msg = createMessageError(request, "error.deleting", supplierid, e);
      log.error(msg, e);
      return getRedirectWhenDelete(request, supplierid, e);
    }
  }


@RequestMapping(value = "/deleteSelected", method = RequestMethod.POST)
public String deleteSelected(HttpServletRequest request,
    HttpServletResponse response,
    @ModelAttribute SuppliersFilterForm filterForm) throws Exception {

  if(!isActiveDelete()) {
    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    return null;
  }
  
  String[] seleccionats = filterForm.getSelectedItems();
  String redirect = null;
  if (seleccionats != null && seleccionats.length != 0) {
    for (int i = 0; i < seleccionats.length; i++) {
      redirect = eliminarSuppliers(stringToPK(seleccionats[i]), request, response);
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
  public String[] getArgumentsMissatge(Object __supplierid, Throwable e) {
    java.lang.Long supplierid = (java.lang.Long)__supplierid;
    String exceptionMsg = "";
    if (e != null) {
      if (e instanceof I18NException) {
        exceptionMsg = I18NUtils.getMessage((I18NException)e);
      } else if (e instanceof I18NValidationException) {
      } else {
        exceptionMsg = e.getMessage();
      };
    };
    if (supplierid == null) {
      return new String[] { I18NUtils.tradueix(getEntityNameCode()),
         getPrimaryKeyColumnsTranslated(), null, exceptionMsg };
    } else {
      return new String[] { I18NUtils.tradueix(getEntityNameCode()),
        getPrimaryKeyColumnsTranslated(),
         String.valueOf(supplierid),
 exceptionMsg };
    }
  }

  public String getEntityNameCode() {
    return "suppliers.suppliers";
  }

  public String getEntityNameCodePlural() {
    return "suppliers.suppliers.plural";
  }

  public String getPrimaryKeyColumnsTranslated() {
    return  I18NUtils.tradueix("suppliers.supplierid");
  }

  @InitBinder("suppliersFilterForm")
  public void initBinderFilterForm(WebDataBinder binder) {
    super.initBinder(binder);
  }

  @InitBinder("suppliersForm")
  public void initBinderForm(WebDataBinder binder) {
    super.initBinder(binder);

    binder.setValidator(getWebValidator());


    initDisallowedFields(binder, "suppliers.supplierid");
  }

  public SuppliersWebValidator getWebValidator() {
    return suppliersWebValidator;
  }


  public void setWebValidator(SuppliersWebValidator __val) {
    if (__val != null) {
      this.suppliersWebValidator= __val;
    }
  }


  /**
   * Entra aqui al pitjar el boto cancel en el llistat de Suppliers
   */
  @RequestMapping(value = "/{supplierid}/cancel")
  public String cancelSuppliers(@PathVariable("supplierid") java.lang.Long supplierid,
      HttpServletRequest request,HttpServletResponse response) {
     return getRedirectWhenCancel(request, supplierid);
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

  public void preValidate(HttpServletRequest request,SuppliersForm suppliersForm , BindingResult result)  throws I18NException {
  }

  public void postValidate(HttpServletRequest request,SuppliersForm suppliersForm, BindingResult result)  throws I18NException {
  }

  public void preList(HttpServletRequest request, ModelAndView mav, SuppliersFilterForm filterForm)  throws I18NException {
  }

  public void postList(HttpServletRequest request, ModelAndView mav, SuppliersFilterForm filterForm,  List<Suppliers> list) throws I18NException {
  }

  public String getRedirectWhenCreated(HttpServletRequest request, SuppliersForm suppliersForm) {
    return "redirect:" + getContextWeb() + "/list/1";
  }

  public String getRedirectWhenModified(HttpServletRequest request, SuppliersForm suppliersForm, Throwable __e) {
    if (__e == null) {
      return "redirect:" + getContextWeb() + "/list";
    } else {
      return  getTileForm();
    }
  }

  public String getRedirectWhenDelete(HttpServletRequest request, java.lang.Long supplierid, Throwable __e) {
    return "redirect:" + getContextWeb() + "/list";
  }

  public String getRedirectWhenCancel(HttpServletRequest request, java.lang.Long supplierid) {
    return "redirect:" + getContextWeb() + "/list";
  }

  public String getTileForm() {
    return "suppliersFormWebDB";
  }

  public String getTileList() {
    return "suppliersListWebDB";
  }

  public String getSessionAttributeFilterForm() {
    return "SuppliersWebDB_FilterForm";
  }



  public Where getAdditionalCondition(HttpServletRequest request) throws I18NException {
    return null;
  }


  public SuppliersJPA findByPrimaryKey(HttpServletRequest request, java.lang.Long supplierid) throws I18NException {
    return (SuppliersJPA) suppliersEjb.findByPrimaryKey(supplierid);
  }


  public SuppliersJPA create(HttpServletRequest request, SuppliersJPA suppliers)
    throws I18NException, I18NValidationException {
    return (SuppliersJPA) suppliersEjb.create(suppliers);
  }


  public SuppliersJPA update(HttpServletRequest request, SuppliersJPA suppliers)
    throws I18NException, I18NValidationException {
    return (SuppliersJPA) suppliersEjb.update(suppliers);
  }


  public void delete(HttpServletRequest request, Suppliers suppliers) throws I18NException {
    suppliersEjb.delete(suppliers);
  }

} // Final de Classe

