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
import org.fundaciobit.genappsqltutorial.back.form.webdb.ShippersForm;

import org.fundaciobit.genappsqltutorial.back.validator.webdb.ShippersWebValidator;

import org.fundaciobit.genappsqltutorial.persistence.ShippersJPA;
import org.fundaciobit.genappsqltutorial.model.entity.Shippers;
import org.fundaciobit.genappsqltutorial.model.fields.*;

/**
 * Controller per gestionar un Shippers
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * 
 * @author GenApp
 */
@Controller
@RequestMapping(value = "/webdb/shippers")
@SessionAttributes(types = { ShippersForm.class, ShippersFilterForm.class })
public class ShippersController
    extends org.fundaciobit.genappsqltutorial.back.controller.GenAppSqlTutorialBaseController<Shippers, java.lang.Long> implements ShippersFields {

  @EJB(mappedName = org.fundaciobit.genappsqltutorial.ejb.ShippersService.JNDI_NAME)
  protected org.fundaciobit.genappsqltutorial.ejb.ShippersService shippersEjb;

  @Autowired
  private ShippersWebValidator shippersWebValidator;

  @Autowired
  protected ShippersRefList shippersRefList;

  /**
   * Llistat de totes Shippers
   */
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public String llistat(HttpServletRequest request,
    HttpServletResponse response) throws I18NException {
    ShippersFilterForm ff;
    ff = (ShippersFilterForm) request.getSession().getAttribute(getSessionAttributeFilterForm());
    int pagina = (ff == null)? 1: ff.getPage();
    return "redirect:" + getContextWeb() + "/list/" + pagina;
  }

  /**
   * Primera peticio per llistar Shippers de forma paginada
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
    llistat(mav, request, getShippersFilterForm(pagina, mav, request));
    return mav;
  }

  public ShippersFilterForm getShippersFilterForm(Integer pagina, ModelAndView mav,
    HttpServletRequest request) throws I18NException {
    ShippersFilterForm shippersFilterForm;
    shippersFilterForm = (ShippersFilterForm) request.getSession().getAttribute(getSessionAttributeFilterForm());
    if(shippersFilterForm == null) {
      shippersFilterForm = new ShippersFilterForm();
      shippersFilterForm.setContexte(getContextWeb());
      shippersFilterForm.setEntityNameCode(getEntityNameCode());
      shippersFilterForm.setEntityNameCodePlural(getEntityNameCodePlural());
      shippersFilterForm.setNou(true);
    } else {
      shippersFilterForm.setNou(false);
    }
    shippersFilterForm.setPage(pagina == null ? 1 : pagina);
    return shippersFilterForm;
  }

  /**
   * Segona i següent peticions per llistar Shippers de forma paginada
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
      @ModelAttribute ShippersFilterForm filterForm) throws I18NException {
    if(!isActiveList()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }

    ModelAndView mav = new ModelAndView(getTileList());

    filterForm.setPage(pagina == null ? 1 : pagina);
    // Actualitza el filter form

    request.getSession().setAttribute(getSessionAttributeFilterForm(), filterForm);
    filterForm = getShippersFilterForm(pagina, mav, request);

    llistat(mav, request, filterForm);
    return mav;
  }

  /**
   * Codi centralitzat de llistat de Shippers de forma paginada.
   * 
   * @param request
   * @param filterForm
   * @param pagina
   * @return
   * @throws I18NException
   */
  protected List<Shippers> llistat(ModelAndView mav, HttpServletRequest request,
     ShippersFilterForm filterForm) throws I18NException {

    int pagina = filterForm.getPage();
    request.getSession().setAttribute(getSessionAttributeFilterForm(), filterForm);

    captureSearchByValueOfAdditionalFields(request, filterForm);

    preList(request, mav, filterForm);

    List<Shippers> shippers = processarLlistat(shippersEjb,
        filterForm, pagina, getAdditionalCondition(request), mav);

    mav.addObject("shippersItems", shippers);

    mav.addObject("shippersFilterForm", filterForm);

    fillReferencesForList(filterForm,request, mav, shippers, (List<GroupByItem>)mav.getModel().get("groupby_items"));

    postList(request, mav, filterForm, shippers);

    return shippers;
  }


  public Map<Field<?>, GroupByItem> fillReferencesForList(ShippersFilterForm filterForm,
    HttpServletRequest request, ModelAndView mav,
      List<Shippers> list, List<GroupByItem> groupItems) throws I18NException {
    Map<Field<?>, GroupByItem> groupByItemsMap = new HashMap<Field<?>, GroupByItem>();
    for (GroupByItem groupByItem : groupItems) {
      groupByItemsMap.put(groupByItem.getField(),groupByItem);
    }


    return groupByItemsMap;
  }

  @RequestMapping(value = "/export/{dataExporterID}", method = RequestMethod.POST)
  public void exportList(@PathVariable("dataExporterID") String dataExporterID,
    HttpServletRequest request, HttpServletResponse response,
    ShippersFilterForm filterForm) throws Exception, I18NException {

    ModelAndView mav = new ModelAndView(getTileList());
    List<Shippers> list = llistat(mav, request, filterForm);
    Field<?>[] allFields = ALL_SHIPPERS_FIELDS;

    java.util.Map<Field<?>, java.util.Map<String, String>> __mapping;
    __mapping = new java.util.HashMap<Field<?>, java.util.Map<String, String>>();
    exportData(request, response, dataExporterID, filterForm,
          list, allFields, __mapping, PRIMARYKEY_FIELDS);
  }



  /**
   * Carregar el formulari per un nou Shippers
   */
  @RequestMapping(value = "/new", method = RequestMethod.GET)
  public ModelAndView crearShippersGet(HttpServletRequest request,
      HttpServletResponse response) throws I18NException {

    if(!isActiveFormNew()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }
    ModelAndView mav = new ModelAndView(getTileForm());
    ShippersForm shippersForm = getShippersForm(null, false, request, mav);
    mav.addObject("shippersForm" ,shippersForm);
    fillReferencesForForm(shippersForm, request, mav);
  
    return mav;
  }
  
  /**
   * 
   * @return
   * @throws Exception
   */
  public ShippersForm getShippersForm(ShippersJPA _jpa,
       boolean __isView, HttpServletRequest request, ModelAndView mav) throws I18NException {
    ShippersForm shippersForm;
    if(_jpa == null) {
      shippersForm = new ShippersForm(new ShippersJPA(), true);
    } else {
      shippersForm = new ShippersForm(_jpa, false);
      shippersForm.setView(__isView);
    }
    shippersForm.setContexte(getContextWeb());
    shippersForm.setEntityNameCode(getEntityNameCode());
    shippersForm.setEntityNameCodePlural(getEntityNameCodePlural());
    return shippersForm;
  }

  public void fillReferencesForForm(ShippersForm shippersForm,
    HttpServletRequest request, ModelAndView mav) throws I18NException {
    
  }

  /**
   * Guardar un nou Shippers
   */
  @RequestMapping(value = "/new", method = RequestMethod.POST)
  public String crearShippersPost(@ModelAttribute ShippersForm shippersForm,
      BindingResult result, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    if(!isActiveFormNew()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }

    ShippersJPA shippers = shippersForm.getShippers();

    try {
      preValidate(request, shippersForm, result);
      getWebValidator().validate(shippersForm, result);
      postValidate(request,shippersForm, result);

      if (result.hasErrors()) {
        result.reject("error.form");
        return getTileForm();
      } else {
        shippers = create(request, shippers);
        createMessageSuccess(request, "success.creation", shippers.getShipperid());
        shippersForm.setShippers(shippers);
        return getRedirectWhenCreated(request, shippersForm);
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

  @RequestMapping(value = "/view/{shipperid}", method = RequestMethod.GET)
  public ModelAndView veureShippersGet(@PathVariable("shipperid") java.lang.Long shipperid,
      HttpServletRequest request,
      HttpServletResponse response) throws I18NException {
      return editAndViewShippersGet(shipperid,
        request, response, true);
  }


  protected ModelAndView editAndViewShippersGet(@PathVariable("shipperid") java.lang.Long shipperid,
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
    ShippersJPA shippers = findByPrimaryKey(request, shipperid);

    if (shippers == null) {
      createMessageWarning(request, "error.notfound", shipperid);
      new ModelAndView(new RedirectView(getRedirectWhenCancel(request, shipperid), true));
      return llistatPaginat(request, response, 1);
    } else {
      ModelAndView mav = new ModelAndView(getTileForm());
      ShippersForm shippersForm = getShippersForm(shippers, __isView, request, mav);
      shippersForm.setView(__isView);
      if(__isView) {
        shippersForm.setAllFieldsReadOnly(ALL_SHIPPERS_FIELDS);
        shippersForm.setSaveButtonVisible(false);
        shippersForm.setDeleteButtonVisible(false);
      }
      fillReferencesForForm(shippersForm, request, mav);
      mav.addObject("shippersForm", shippersForm);
      return mav;
    }
  }


  /**
   * Carregar el formulari per modificar un Shippers existent
   */
  @RequestMapping(value = "/{shipperid}/edit", method = RequestMethod.GET)
  public ModelAndView editarShippersGet(@PathVariable("shipperid") java.lang.Long shipperid,
      HttpServletRequest request,
      HttpServletResponse response) throws I18NException {
      return editAndViewShippersGet(shipperid,
        request, response, false);
  }



  /**
   * Editar un Shippers existent
   */
  @RequestMapping(value = "/{shipperid}/edit", method = RequestMethod.POST)
  public String editarShippersPost(@ModelAttribute ShippersForm shippersForm,
      BindingResult result, SessionStatus status, HttpServletRequest request,
      HttpServletResponse response) throws I18NException {

    if(!isActiveFormEdit()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }
    ShippersJPA shippers = shippersForm.getShippers();

    try {
      preValidate(request, shippersForm, result);
      getWebValidator().validate(shippersForm, result);
      postValidate(request, shippersForm, result);

      if (result.hasErrors()) {
        result.reject("error.form");
        return getTileForm();
      } else {
        shippers = update(request, shippers);
        createMessageSuccess(request, "success.modification", shippers.getShipperid());
        status.setComplete();
        return getRedirectWhenModified(request, shippersForm, null);
      }
    } catch (Throwable __e) {
      if (__e instanceof I18NValidationException) {
        ValidationWebUtils.addFieldErrorsToBindingResult(result, (I18NValidationException)__e);
        return getTileForm();
      }
      String msg = createMessageError(request, "error.modification",
          shippers.getShipperid(), __e);
      log.error(msg, __e);
      return getRedirectWhenModified(request, shippersForm, __e);
    }

  }


  /**
   * Eliminar un Shippers existent
   */
  @RequestMapping(value = "/{shipperid}/delete")
  public String eliminarShippers(@PathVariable("shipperid") java.lang.Long shipperid,
      HttpServletRequest request,HttpServletResponse response) {

    if(!isActiveDelete()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }
    try {
      Shippers shippers = shippersEjb.findByPrimaryKey(shipperid);
      if (shippers == null) {
        String __msg =createMessageError(request, "error.notfound", shipperid);
        return getRedirectWhenDelete(request, shipperid, new Exception(__msg));
      } else {
        delete(request, shippers);
        createMessageSuccess(request, "success.deleted", shipperid);
        return getRedirectWhenDelete(request, shipperid,null);
      }

    } catch (Throwable e) {
      String msg = createMessageError(request, "error.deleting", shipperid, e);
      log.error(msg, e);
      return getRedirectWhenDelete(request, shipperid, e);
    }
  }


@RequestMapping(value = "/deleteSelected", method = RequestMethod.POST)
public String deleteSelected(HttpServletRequest request,
    HttpServletResponse response,
    @ModelAttribute ShippersFilterForm filterForm) throws Exception {

  if(!isActiveDelete()) {
    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    return null;
  }
  
  String[] seleccionats = filterForm.getSelectedItems();
  String redirect = null;
  if (seleccionats != null && seleccionats.length != 0) {
    for (int i = 0; i < seleccionats.length; i++) {
      redirect = eliminarShippers(stringToPK(seleccionats[i]), request, response);
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
  public String[] getArgumentsMissatge(Object __shipperid, Throwable e) {
    java.lang.Long shipperid = (java.lang.Long)__shipperid;
    String exceptionMsg = "";
    if (e != null) {
      if (e instanceof I18NException) {
        exceptionMsg = I18NUtils.getMessage((I18NException)e);
      } else if (e instanceof I18NValidationException) {
      } else {
        exceptionMsg = e.getMessage();
      };
    };
    if (shipperid == null) {
      return new String[] { I18NUtils.tradueix(getEntityNameCode()),
         getPrimaryKeyColumnsTranslated(), null, exceptionMsg };
    } else {
      return new String[] { I18NUtils.tradueix(getEntityNameCode()),
        getPrimaryKeyColumnsTranslated(),
         String.valueOf(shipperid),
 exceptionMsg };
    }
  }

  public String getEntityNameCode() {
    return "shippers.shippers";
  }

  public String getEntityNameCodePlural() {
    return "shippers.shippers.plural";
  }

  public String getPrimaryKeyColumnsTranslated() {
    return  I18NUtils.tradueix("shippers.shipperid");
  }

  @InitBinder("shippersFilterForm")
  public void initBinderFilterForm(WebDataBinder binder) {
    super.initBinder(binder);
  }

  @InitBinder("shippersForm")
  public void initBinderForm(WebDataBinder binder) {
    super.initBinder(binder);

    binder.setValidator(getWebValidator());

    binder.setDisallowedFields("shipperid");

  }

  public ShippersWebValidator getWebValidator() {
    return shippersWebValidator;
  }


  public void setWebValidator(ShippersWebValidator __val) {
    if (__val != null) {
      this.shippersWebValidator= __val;
    }
  }


  /**
   * Entra aqui al pitjar el boto cancel en el llistat de Shippers
   */
  @RequestMapping(value = "/{shipperid}/cancel")
  public String cancelShippers(@PathVariable("shipperid") java.lang.Long shipperid,
      HttpServletRequest request,HttpServletResponse response) {
     return getRedirectWhenCancel(request, shipperid);
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


  public void preValidate(HttpServletRequest request,ShippersForm shippersForm , BindingResult result)  throws I18NException {
  }

  public void postValidate(HttpServletRequest request,ShippersForm shippersForm, BindingResult result)  throws I18NException {
  }

  public void preList(HttpServletRequest request, ModelAndView mav, ShippersFilterForm filterForm)  throws I18NException {
  }

  public void postList(HttpServletRequest request, ModelAndView mav, ShippersFilterForm filterForm,  List<Shippers> list) throws I18NException {
  }

  public String getRedirectWhenCreated(HttpServletRequest request, ShippersForm shippersForm) {
    return "redirect:" + getContextWeb() + "/list/1";
  }

  public String getRedirectWhenModified(HttpServletRequest request, ShippersForm shippersForm, Throwable __e) {
    if (__e == null) {
      return "redirect:" + getContextWeb() + "/list";
    } else {
      return  getTileForm();
    }
  }

  public String getRedirectWhenDelete(HttpServletRequest request, java.lang.Long shipperid, Throwable __e) {
    return "redirect:" + getContextWeb() + "/list";
  }

  public String getRedirectWhenCancel(HttpServletRequest request, java.lang.Long shipperid) {
    return "redirect:" + getContextWeb() + "/list";
  }

  public String getTileForm() {
    return "shippersFormWebDB";
  }

  public String getTileList() {
    return "shippersListWebDB";
  }

  @Override
  /** Ha de ser igual que el RequestMapping de la Classe */
  public String getContextWeb() {
    RequestMapping rm = AnnotationUtils.findAnnotation(this.getClass(), RequestMapping.class);
    return rm.value()[0];
  }

  public String getSessionAttributeFilterForm() {
    return "ShippersWebDB_FilterForm";
  }



  public Where getAdditionalCondition(HttpServletRequest request) throws I18NException {
    return null;
  }


  public ShippersJPA findByPrimaryKey(HttpServletRequest request, java.lang.Long shipperid) throws I18NException {
    return (ShippersJPA) shippersEjb.findByPrimaryKey(shipperid);
  }


  public ShippersJPA create(HttpServletRequest request, ShippersJPA shippers)
    throws Exception,I18NException, I18NValidationException {
    return (ShippersJPA) shippersEjb.create(shippers);
  }


  public ShippersJPA update(HttpServletRequest request, ShippersJPA shippers)
    throws Exception,I18NException, I18NValidationException {
    return (ShippersJPA) shippersEjb.update(shippers);
  }


  public void delete(HttpServletRequest request, Shippers shippers) throws Exception,I18NException {
    shippersEjb.delete(shippers);
  }

} // Final de Classe

