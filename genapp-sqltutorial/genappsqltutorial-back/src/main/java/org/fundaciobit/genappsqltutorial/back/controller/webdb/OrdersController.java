package org.fundaciobit.genappsqltutorial.back.controller.webdb;

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

import org.fundaciobit.genappsqltutorial.back.form.webdb.*;
import org.fundaciobit.genappsqltutorial.back.form.webdb.OrdersForm;

import org.fundaciobit.genappsqltutorial.back.validator.webdb.OrdersWebValidator;

import org.fundaciobit.genappsqltutorial.persistence.OrdersJPA;
import org.fundaciobit.genappsqltutorial.model.entity.Orders;
import org.fundaciobit.genappsqltutorial.model.fields.*;

/**
 * Controller per gestionar un Orders
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * 
 * @author GenApp
 */
@Controller
@RequestMapping(value = "/webdb/orders")
@SessionAttributes(types = { OrdersForm.class, OrdersFilterForm.class })
public class OrdersController
    extends org.fundaciobit.genappsqltutorial.back.controller.GenAppSqlTutorialBaseController<Orders, java.lang.Long> implements OrdersFields {

  @EJB(mappedName = org.fundaciobit.genappsqltutorial.ejb.OrdersService.JNDI_NAME)
  protected org.fundaciobit.genappsqltutorial.ejb.OrdersService ordersEjb;

  @Autowired
  private OrdersWebValidator ordersWebValidator;

  @Autowired
  protected OrdersRefList ordersRefList;

  // References 
  @Autowired
  protected CustomersRefList customersRefList;

  // References 
  @Autowired
  protected EmployeesRefList employeesRefList;

  // References 
  @Autowired
  protected SuppliersRefList suppliersRefList;

  /**
   * Llistat de totes Orders
   */
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public String llistat(HttpServletRequest request,
    HttpServletResponse response) throws I18NException {
    OrdersFilterForm ff;
    ff = (OrdersFilterForm) request.getSession().getAttribute(getSessionAttributeFilterForm());
    int pagina = (ff == null)? 1: ff.getPage();
    return "redirect:" + getContextWeb() + "/list/" + pagina;
  }

  /**
   * Primera peticio per llistar Orders de forma paginada
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
    llistat(mav, request, getOrdersFilterForm(pagina, mav, request));
    return mav;
  }

  public OrdersFilterForm getOrdersFilterForm(Integer pagina, ModelAndView mav,
    HttpServletRequest request) throws I18NException {
    OrdersFilterForm ordersFilterForm;
    ordersFilterForm = (OrdersFilterForm) request.getSession().getAttribute(getSessionAttributeFilterForm());
    if(ordersFilterForm == null) {
      ordersFilterForm = new OrdersFilterForm();
      ordersFilterForm.setContexte(getContextWeb());
      ordersFilterForm.setEntityNameCode(getEntityNameCode());
      ordersFilterForm.setEntityNameCodePlural(getEntityNameCodePlural());
      ordersFilterForm.setNou(true);
    } else {
      ordersFilterForm.setNou(false);
    }
    ordersFilterForm.setPage(pagina == null ? 1 : pagina);
    return ordersFilterForm;
  }

  /**
   * Segona i següent peticions per llistar Orders de forma paginada
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
      @ModelAttribute OrdersFilterForm filterForm) throws I18NException {
    if(!isActiveList()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }

    ModelAndView mav = new ModelAndView(getTileList());

    filterForm.setPage(pagina == null ? 1 : pagina);
    // Actualitza el filter form

    request.getSession().setAttribute(getSessionAttributeFilterForm(), filterForm);
    filterForm = getOrdersFilterForm(pagina, mav, request);

    llistat(mav, request, filterForm);
    return mav;
  }

  /**
   * Codi centralitzat de llistat de Orders de forma paginada.
   * 
   * @param request
   * @param filterForm
   * @param pagina
   * @return
   * @throws I18NException
   */
  protected List<Orders> llistat(ModelAndView mav, HttpServletRequest request,
     OrdersFilterForm filterForm) throws I18NException {

    int pagina = filterForm.getPage();
    request.getSession().setAttribute(getSessionAttributeFilterForm(), filterForm);

    captureSearchByValueOfAdditionalFields(request, filterForm);

    preList(request, mav, filterForm);

    List<Orders> orders = processarLlistat(ordersEjb,
        filterForm, pagina, getAdditionalCondition(request), mav);

    mav.addObject("ordersItems", orders);

    mav.addObject("ordersFilterForm", filterForm);

    fillReferencesForList(filterForm,request, mav, orders, (List<GroupByItem>)mav.getModel().get("groupby_items"));

    postList(request, mav, filterForm, orders);

    return orders;
  }


  public Map<Field<?>, GroupByItem> fillReferencesForList(OrdersFilterForm filterForm,
    HttpServletRequest request, ModelAndView mav,
      List<Orders> list, List<GroupByItem> groupItems) throws I18NException {
    Map<Field<?>, GroupByItem> groupByItemsMap = new HashMap<Field<?>, GroupByItem>();
    for (GroupByItem groupByItem : groupItems) {
      groupByItemsMap.put(groupByItem.getField(),groupByItem);
    }

    Map<String, String> _tmp;
    List<StringKeyValue> _listSKV;

    // Field customerid
    {
      _listSKV = getReferenceListForCustomerid(request, mav, filterForm, list, groupByItemsMap, null);
      _tmp = Utils.listToMap(_listSKV);
      filterForm.setMapOfCustomersForCustomerid(_tmp);
      if (filterForm.getGroupByFields().contains(CUSTOMERID)) {
        fillValuesToGroupByItems(_tmp, groupByItemsMap, CUSTOMERID, false);
      };
    }

    // Field employeeid
    {
      _listSKV = getReferenceListForEmployeeid(request, mav, filterForm, list, groupByItemsMap, null);
      _tmp = Utils.listToMap(_listSKV);
      filterForm.setMapOfEmployeesForEmployeeid(_tmp);
      if (filterForm.getGroupByFields().contains(EMPLOYEEID)) {
        fillValuesToGroupByItems(_tmp, groupByItemsMap, EMPLOYEEID, false);
      };
    }

    // Field shipperid
    {
      _listSKV = getReferenceListForShipperid(request, mav, filterForm, list, groupByItemsMap, null);
      _tmp = Utils.listToMap(_listSKV);
      filterForm.setMapOfSuppliersForShipperid(_tmp);
      if (filterForm.getGroupByFields().contains(SHIPPERID)) {
        fillValuesToGroupByItems(_tmp, groupByItemsMap, SHIPPERID, false);
      };
    }


    return groupByItemsMap;
  }

  @RequestMapping(value = "/export/{dataExporterID}", method = RequestMethod.POST)
  public void exportList(@PathVariable("dataExporterID") String dataExporterID,
    HttpServletRequest request, HttpServletResponse response,
    OrdersFilterForm filterForm) throws Exception, I18NException {

    ModelAndView mav = new ModelAndView(getTileList());
    List<Orders> list = llistat(mav, request, filterForm);
    Field<?>[] allFields = ALL_ORDERS_FIELDS;

    java.util.Map<Field<?>, java.util.Map<String, String>> __mapping;
    __mapping = new java.util.HashMap<Field<?>, java.util.Map<String, String>>();
    __mapping.put(CUSTOMERID, filterForm.getMapOfCustomersForCustomerid());
    __mapping.put(EMPLOYEEID, filterForm.getMapOfEmployeesForEmployeeid());
    __mapping.put(SHIPPERID, filterForm.getMapOfSuppliersForShipperid());
    exportData(request, response, dataExporterID, filterForm,
          list, allFields, __mapping, PRIMARYKEY_FIELDS);
  }



  /**
   * Carregar el formulari per un nou Orders
   */
  @RequestMapping(value = "/new", method = RequestMethod.GET)
  public ModelAndView crearOrdersGet(HttpServletRequest request,
      HttpServletResponse response) throws I18NException {

    if(!isActiveFormNew()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }
    ModelAndView mav = new ModelAndView(getTileForm());
    OrdersForm ordersForm = getOrdersForm(null, false, request, mav);
    mav.addObject("ordersForm" ,ordersForm);
    fillReferencesForForm(ordersForm, request, mav);
  
    return mav;
  }
  
  /**
   * 
   * @return
   * @throws Exception
   */
  public OrdersForm getOrdersForm(OrdersJPA _jpa,
       boolean __isView, HttpServletRequest request, ModelAndView mav) throws I18NException {
    OrdersForm ordersForm;
    if(_jpa == null) {
      ordersForm = new OrdersForm(new OrdersJPA(), true);
    } else {
      ordersForm = new OrdersForm(_jpa, false);
      ordersForm.setView(__isView);
    }
    ordersForm.setContexte(getContextWeb());
    ordersForm.setEntityNameCode(getEntityNameCode());
    ordersForm.setEntityNameCodePlural(getEntityNameCodePlural());
    return ordersForm;
  }

  public void fillReferencesForForm(OrdersForm ordersForm,
    HttpServletRequest request, ModelAndView mav) throws I18NException {
    // Comprovam si ja esta definida la llista
    if (ordersForm.getListOfCustomersForCustomerid() == null) {
      List<StringKeyValue> _listSKV = getReferenceListForCustomerid(request, mav, ordersForm, null);

      if(_listSKV != null && !_listSKV.isEmpty()) { 
          java.util.Collections.sort(_listSKV, STRINGKEYVALUE_COMPARATOR);
      }
      ordersForm.setListOfCustomersForCustomerid(_listSKV);
    }
    // Comprovam si ja esta definida la llista
    if (ordersForm.getListOfEmployeesForEmployeeid() == null) {
      List<StringKeyValue> _listSKV = getReferenceListForEmployeeid(request, mav, ordersForm, null);

      if(_listSKV != null && !_listSKV.isEmpty()) { 
          java.util.Collections.sort(_listSKV, STRINGKEYVALUE_COMPARATOR);
      }
      ordersForm.setListOfEmployeesForEmployeeid(_listSKV);
    }
    // Comprovam si ja esta definida la llista
    if (ordersForm.getListOfSuppliersForShipperid() == null) {
      List<StringKeyValue> _listSKV = getReferenceListForShipperid(request, mav, ordersForm, null);

      if(_listSKV != null && !_listSKV.isEmpty()) { 
          java.util.Collections.sort(_listSKV, STRINGKEYVALUE_COMPARATOR);
      }
      ordersForm.setListOfSuppliersForShipperid(_listSKV);
    }
    
  }

  /**
   * Guardar un nou Orders
   */
  @RequestMapping(value = "/new", method = RequestMethod.POST)
  public String crearOrdersPost(@ModelAttribute OrdersForm ordersForm,
      BindingResult result, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    if(!isActiveFormNew()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }

    OrdersJPA orders = ordersForm.getOrders();

    try {
      preValidate(request, ordersForm, result);
      getWebValidator().validate(ordersForm, result);
      postValidate(request,ordersForm, result);

      if (result.hasErrors()) {
        result.reject("error.form");
        return getTileForm();
      } else {
        orders = create(request, orders);
        createMessageSuccess(request, "success.creation", orders.getOrderid());
        ordersForm.setOrders(orders);
        return getRedirectWhenCreated(request, ordersForm);
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

  @RequestMapping(value = "/view/{orderid}", method = RequestMethod.GET)
  public ModelAndView veureOrdersGet(@PathVariable("orderid") java.lang.Long orderid,
      HttpServletRequest request,
      HttpServletResponse response) throws I18NException {
      return editAndViewOrdersGet(orderid,
        request, response, true);
  }


  protected ModelAndView editAndViewOrdersGet(@PathVariable("orderid") java.lang.Long orderid,
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
    OrdersJPA orders = findByPrimaryKey(request, orderid);

    if (orders == null) {
      createMessageWarning(request, "error.notfound", orderid);
      new ModelAndView(new RedirectView(getRedirectWhenCancel(request, orderid), true));
      return llistatPaginat(request, response, 1);
    } else {
      ModelAndView mav = new ModelAndView(getTileForm());
      OrdersForm ordersForm = getOrdersForm(orders, __isView, request, mav);
      ordersForm.setView(__isView);
      if(__isView) {
        ordersForm.setAllFieldsReadOnly(ALL_ORDERS_FIELDS);
        ordersForm.setSaveButtonVisible(false);
        ordersForm.setDeleteButtonVisible(false);
      }
      fillReferencesForForm(ordersForm, request, mav);
      mav.addObject("ordersForm", ordersForm);
      return mav;
    }
  }


  /**
   * Carregar el formulari per modificar un Orders existent
   */
  @RequestMapping(value = "/{orderid}/edit", method = RequestMethod.GET)
  public ModelAndView editarOrdersGet(@PathVariable("orderid") java.lang.Long orderid,
      HttpServletRequest request,
      HttpServletResponse response) throws I18NException {
      return editAndViewOrdersGet(orderid,
        request, response, false);
  }



  /**
   * Editar un Orders existent
   */
  @RequestMapping(value = "/{orderid}/edit", method = RequestMethod.POST)
  public String editarOrdersPost(@ModelAttribute OrdersForm ordersForm,
      BindingResult result, SessionStatus status, HttpServletRequest request,
      HttpServletResponse response) throws I18NException {

    if(!isActiveFormEdit()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }
    OrdersJPA orders = ordersForm.getOrders();

    try {
      preValidate(request, ordersForm, result);
      getWebValidator().validate(ordersForm, result);
      postValidate(request, ordersForm, result);

      if (result.hasErrors()) {
        result.reject("error.form");
        return getTileForm();
      } else {
        orders = update(request, orders);
        createMessageSuccess(request, "success.modification", orders.getOrderid());
        status.setComplete();
        return getRedirectWhenModified(request, ordersForm, null);
      }
    } catch (Throwable __e) {
      if (__e instanceof I18NValidationException) {
        ValidationWebUtils.addFieldErrorsToBindingResult(result, (I18NValidationException)__e);
        return getTileForm();
      }
      String msg = createMessageError(request, "error.modification",
          orders.getOrderid(), __e);
      log.error(msg, __e);
      return getRedirectWhenModified(request, ordersForm, __e);
    }

  }


  /**
   * Eliminar un Orders existent
   */
  @RequestMapping(value = "/{orderid}/delete")
  public String eliminarOrders(@PathVariable("orderid") java.lang.Long orderid,
      HttpServletRequest request,HttpServletResponse response) {

    if(!isActiveDelete()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }
    try {
      Orders orders = ordersEjb.findByPrimaryKey(orderid);
      if (orders == null) {
        String __msg =createMessageError(request, "error.notfound", orderid);
        return getRedirectWhenDelete(request, orderid, new Exception(__msg));
      } else {
        delete(request, orders);
        createMessageSuccess(request, "success.deleted", orderid);
        return getRedirectWhenDelete(request, orderid,null);
      }

    } catch (Throwable e) {
      String msg = createMessageError(request, "error.deleting", orderid, e);
      log.error(msg, e);
      return getRedirectWhenDelete(request, orderid, e);
    }
  }


@RequestMapping(value = "/deleteSelected", method = RequestMethod.POST)
public String deleteSelected(HttpServletRequest request,
    HttpServletResponse response,
    @ModelAttribute OrdersFilterForm filterForm) throws Exception {

  if(!isActiveDelete()) {
    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    return null;
  }
  
  String[] seleccionats = filterForm.getSelectedItems();
  String redirect = null;
  if (seleccionats != null && seleccionats.length != 0) {
    for (int i = 0; i < seleccionats.length; i++) {
      redirect = eliminarOrders(stringToPK(seleccionats[i]), request, response);
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
  public String[] getArgumentsMissatge(Object __orderid, Throwable e) {
    java.lang.Long orderid = (java.lang.Long)__orderid;
    String exceptionMsg = "";
    if (e != null) {
      if (e instanceof I18NException) {
        exceptionMsg = I18NUtils.getMessage((I18NException)e);
      } else if (e instanceof I18NValidationException) {
      } else {
        exceptionMsg = e.getMessage();
      };
    };
    if (orderid == null) {
      return new String[] { I18NUtils.tradueix(getEntityNameCode()),
         getPrimaryKeyColumnsTranslated(), null, exceptionMsg };
    } else {
      return new String[] { I18NUtils.tradueix(getEntityNameCode()),
        getPrimaryKeyColumnsTranslated(),
         String.valueOf(orderid),
 exceptionMsg };
    }
  }

  public String getEntityNameCode() {
    return "orders.orders";
  }

  public String getEntityNameCodePlural() {
    return "orders.orders.plural";
  }

  public String getPrimaryKeyColumnsTranslated() {
    return  I18NUtils.tradueix("orders.orderid");
  }

  @InitBinder("ordersFilterForm")
  public void initBinderFilterForm(WebDataBinder binder) {
    super.initBinder(binder);
  }

  @InitBinder("ordersForm")
  public void initBinderForm(WebDataBinder binder) {
    super.initBinder(binder);

    binder.setValidator(getWebValidator());


    initDisallowedFields(binder, "orders.orderid");
  }

  public OrdersWebValidator getWebValidator() {
    return ordersWebValidator;
  }


  public void setWebValidator(OrdersWebValidator __val) {
    if (__val != null) {
      this.ordersWebValidator= __val;
    }
  }


  /**
   * Entra aqui al pitjar el boto cancel en el llistat de Orders
   */
  @RequestMapping(value = "/{orderid}/cancel")
  public String cancelOrders(@PathVariable("orderid") java.lang.Long orderid,
      HttpServletRequest request,HttpServletResponse response) {
     return getRedirectWhenCancel(request, orderid);
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


  public List<StringKeyValue> getReferenceListForCustomerid(HttpServletRequest request,
       ModelAndView mav, OrdersForm ordersForm, Where where)  throws I18NException {
    if (ordersForm.isHiddenField(CUSTOMERID)) {
      return EMPTY_STRINGKEYVALUE_LIST;
    }
    Where _where = null;
    if (ordersForm.isReadOnlyField(CUSTOMERID)) {
      _where = CustomersFields.CUSTOMERID.equal(ordersForm.getOrders().getCustomerid());
    }
    return getReferenceListForCustomerid(request, mav, Where.AND(where, _where));
  }


  public List<StringKeyValue> getReferenceListForCustomerid(HttpServletRequest request,
       ModelAndView mav, OrdersFilterForm ordersFilterForm,
       List<Orders> list, Map<Field<?>, GroupByItem> _groupByItemsMap, Where where)  throws I18NException {
    if (ordersFilterForm.isHiddenField(CUSTOMERID)
      && !ordersFilterForm.isGroupByField(CUSTOMERID)) {
      return EMPTY_STRINGKEYVALUE_LIST;
    }
    Where _w = null;
    if (!_groupByItemsMap.containsKey(CUSTOMERID)) {
      // OBTENIR TOTES LES CLAUS (PK) i despres només cercar referències d'aquestes PK
      java.util.Set<java.lang.Long> _pkList = new java.util.HashSet<java.lang.Long>();
      for (Orders _item : list) {
        if(_item.getCustomerid() == null) { continue; };
        _pkList.add(_item.getCustomerid());
        }
        _w = CustomersFields.CUSTOMERID.in(_pkList);
      }
    return getReferenceListForCustomerid(request, mav, Where.AND(where,_w));
  }


  public List<StringKeyValue> getReferenceListForCustomerid(HttpServletRequest request,
       ModelAndView mav, Where where)  throws I18NException {
    return customersRefList.getReferenceList(CustomersFields.CUSTOMERID, where );
  }


  public List<StringKeyValue> getReferenceListForEmployeeid(HttpServletRequest request,
       ModelAndView mav, OrdersForm ordersForm, Where where)  throws I18NException {
    if (ordersForm.isHiddenField(EMPLOYEEID)) {
      return EMPTY_STRINGKEYVALUE_LIST;
    }
    Where _where = null;
    if (ordersForm.isReadOnlyField(EMPLOYEEID)) {
      _where = EmployeesFields.EMPLOYEEID.equal(ordersForm.getOrders().getEmployeeid());
    }
    return getReferenceListForEmployeeid(request, mav, Where.AND(where, _where));
  }


  public List<StringKeyValue> getReferenceListForEmployeeid(HttpServletRequest request,
       ModelAndView mav, OrdersFilterForm ordersFilterForm,
       List<Orders> list, Map<Field<?>, GroupByItem> _groupByItemsMap, Where where)  throws I18NException {
    if (ordersFilterForm.isHiddenField(EMPLOYEEID)
      && !ordersFilterForm.isGroupByField(EMPLOYEEID)) {
      return EMPTY_STRINGKEYVALUE_LIST;
    }
    Where _w = null;
    if (!_groupByItemsMap.containsKey(EMPLOYEEID)) {
      // OBTENIR TOTES LES CLAUS (PK) i despres només cercar referències d'aquestes PK
      java.util.Set<java.lang.Long> _pkList = new java.util.HashSet<java.lang.Long>();
      for (Orders _item : list) {
        if(_item.getEmployeeid() == null) { continue; };
        _pkList.add(_item.getEmployeeid());
        }
        _w = EmployeesFields.EMPLOYEEID.in(_pkList);
      }
    return getReferenceListForEmployeeid(request, mav, Where.AND(where,_w));
  }


  public List<StringKeyValue> getReferenceListForEmployeeid(HttpServletRequest request,
       ModelAndView mav, Where where)  throws I18NException {
    return employeesRefList.getReferenceList(EmployeesFields.EMPLOYEEID, where );
  }


  public List<StringKeyValue> getReferenceListForShipperid(HttpServletRequest request,
       ModelAndView mav, OrdersForm ordersForm, Where where)  throws I18NException {
    if (ordersForm.isHiddenField(SHIPPERID)) {
      return EMPTY_STRINGKEYVALUE_LIST;
    }
    Where _where = null;
    if (ordersForm.isReadOnlyField(SHIPPERID)) {
      _where = SuppliersFields.SUPPLIERID.equal(ordersForm.getOrders().getShipperid());
    }
    return getReferenceListForShipperid(request, mav, Where.AND(where, _where));
  }


  public List<StringKeyValue> getReferenceListForShipperid(HttpServletRequest request,
       ModelAndView mav, OrdersFilterForm ordersFilterForm,
       List<Orders> list, Map<Field<?>, GroupByItem> _groupByItemsMap, Where where)  throws I18NException {
    if (ordersFilterForm.isHiddenField(SHIPPERID)
      && !ordersFilterForm.isGroupByField(SHIPPERID)) {
      return EMPTY_STRINGKEYVALUE_LIST;
    }
    Where _w = null;
    if (!_groupByItemsMap.containsKey(SHIPPERID)) {
      // OBTENIR TOTES LES CLAUS (PK) i despres només cercar referències d'aquestes PK
      java.util.Set<java.lang.Long> _pkList = new java.util.HashSet<java.lang.Long>();
      for (Orders _item : list) {
        if(_item.getShipperid() == null) { continue; };
        _pkList.add(_item.getShipperid());
        }
        _w = SuppliersFields.SUPPLIERID.in(_pkList);
      }
    return getReferenceListForShipperid(request, mav, Where.AND(where,_w));
  }


  public List<StringKeyValue> getReferenceListForShipperid(HttpServletRequest request,
       ModelAndView mav, Where where)  throws I18NException {
    return suppliersRefList.getReferenceList(SuppliersFields.SUPPLIERID, where );
  }


  public void preValidate(HttpServletRequest request,OrdersForm ordersForm , BindingResult result)  throws I18NException {
  }

  public void postValidate(HttpServletRequest request,OrdersForm ordersForm, BindingResult result)  throws I18NException {
  }

  public void preList(HttpServletRequest request, ModelAndView mav, OrdersFilterForm filterForm)  throws I18NException {
  }

  public void postList(HttpServletRequest request, ModelAndView mav, OrdersFilterForm filterForm,  List<Orders> list) throws I18NException {
  }

  public String getRedirectWhenCreated(HttpServletRequest request, OrdersForm ordersForm) {
    return "redirect:" + getContextWeb() + "/list/1";
  }

  public String getRedirectWhenModified(HttpServletRequest request, OrdersForm ordersForm, Throwable __e) {
    if (__e == null) {
      return "redirect:" + getContextWeb() + "/list";
    } else {
      return  getTileForm();
    }
  }

  public String getRedirectWhenDelete(HttpServletRequest request, java.lang.Long orderid, Throwable __e) {
    return "redirect:" + getContextWeb() + "/list";
  }

  public String getRedirectWhenCancel(HttpServletRequest request, java.lang.Long orderid) {
    return "redirect:" + getContextWeb() + "/list";
  }

  public String getTileForm() {
    return "ordersFormWebDB";
  }

  public String getTileList() {
    return "ordersListWebDB";
  }

  @Override
  /** Ha de ser igual que el RequestMapping de la Classe */
  public String getContextWeb() {
    RequestMapping rm = AnnotationUtils.findAnnotation(this.getClass(), RequestMapping.class);
    return rm.value()[0];
  }

  public String getSessionAttributeFilterForm() {
    return "OrdersWebDB_FilterForm";
  }



  public Where getAdditionalCondition(HttpServletRequest request) throws I18NException {
    return null;
  }


  public OrdersJPA findByPrimaryKey(HttpServletRequest request, java.lang.Long orderid) throws I18NException {
    return (OrdersJPA) ordersEjb.findByPrimaryKey(orderid);
  }


  public OrdersJPA create(HttpServletRequest request, OrdersJPA orders)
    throws Exception,I18NException, I18NValidationException {
    return (OrdersJPA) ordersEjb.create(orders);
  }


  public OrdersJPA update(HttpServletRequest request, OrdersJPA orders)
    throws Exception,I18NException, I18NValidationException {
    return (OrdersJPA) ordersEjb.update(orders);
  }


  public void delete(HttpServletRequest request, Orders orders) throws Exception,I18NException {
    ordersEjb.delete(orders);
  }

} // Final de Classe

