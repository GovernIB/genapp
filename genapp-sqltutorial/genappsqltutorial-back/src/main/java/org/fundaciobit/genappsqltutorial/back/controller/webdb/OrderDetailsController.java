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
import org.fundaciobit.genappsqltutorial.back.form.webdb.OrderDetailsForm;

import org.fundaciobit.genappsqltutorial.back.validator.webdb.OrderDetailsWebValidator;

import org.fundaciobit.genappsqltutorial.persistence.OrderDetailsJPA;
import org.fundaciobit.genappsqltutorial.model.entity.OrderDetails;
import org.fundaciobit.genappsqltutorial.model.fields.*;

/**
 * Controller per gestionar un OrderDetails
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * 
 * @author GenApp
 */
@Controller
@RequestMapping(value = "/webdb/orderDetails")
@SessionAttributes(types = { OrderDetailsForm.class, OrderDetailsFilterForm.class })
public class OrderDetailsController
    extends org.fundaciobit.genappsqltutorial.back.controller.GenAppSqlTutorialBaseController<OrderDetails, java.lang.Long> implements OrderDetailsFields {

  @EJB(mappedName = org.fundaciobit.genappsqltutorial.ejb.OrderDetailsService.JNDI_NAME)
  protected org.fundaciobit.genappsqltutorial.ejb.OrderDetailsService orderDetailsEjb;

  @Autowired
  private OrderDetailsWebValidator orderDetailsWebValidator;

  @Autowired
  protected OrderDetailsRefList orderDetailsRefList;

  // References 
  @Autowired
  protected OrdersRefList ordersRefList;

  // References 
  @Autowired
  protected ProductsRefList productsRefList;

  /**
   * Llistat de totes OrderDetails
   */
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public String llistat(HttpServletRequest request,
    HttpServletResponse response) throws I18NException {
    OrderDetailsFilterForm ff;
    ff = (OrderDetailsFilterForm) request.getSession().getAttribute(getSessionAttributeFilterForm());
    int pagina = (ff == null)? 1: ff.getPage();
    return "redirect:" + getContextWeb() + "/list/" + pagina;
  }

  /**
   * Primera peticio per llistar OrderDetails de forma paginada
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
    llistat(mav, request, getOrderDetailsFilterForm(pagina, mav, request));
    return mav;
  }

  public OrderDetailsFilterForm getOrderDetailsFilterForm(Integer pagina, ModelAndView mav,
    HttpServletRequest request) throws I18NException {
    OrderDetailsFilterForm orderDetailsFilterForm;
    orderDetailsFilterForm = (OrderDetailsFilterForm) request.getSession().getAttribute(getSessionAttributeFilterForm());
    if(orderDetailsFilterForm == null) {
      orderDetailsFilterForm = new OrderDetailsFilterForm();
      orderDetailsFilterForm.setContexte(getContextWeb());
      orderDetailsFilterForm.setEntityNameCode(getEntityNameCode());
      orderDetailsFilterForm.setEntityNameCodePlural(getEntityNameCodePlural());
      orderDetailsFilterForm.setNou(true);
    } else {
      orderDetailsFilterForm.setNou(false);
    }
    orderDetailsFilterForm.setPage(pagina == null ? 1 : pagina);
    return orderDetailsFilterForm;
  }

  /**
   * Segona i següent peticions per llistar OrderDetails de forma paginada
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
      @ModelAttribute OrderDetailsFilterForm filterForm) throws I18NException {
    if(!isActiveList()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }

    ModelAndView mav = new ModelAndView(getTileList());

    filterForm.setPage(pagina == null ? 1 : pagina);
    // Actualitza el filter form

    request.getSession().setAttribute(getSessionAttributeFilterForm(), filterForm);
    filterForm = getOrderDetailsFilterForm(pagina, mav, request);

    llistat(mav, request, filterForm);
    return mav;
  }

  /**
   * Codi centralitzat de llistat de OrderDetails de forma paginada.
   * 
   * @param request
   * @param filterForm
   * @param pagina
   * @return
   * @throws I18NException
   */
  protected List<OrderDetails> llistat(ModelAndView mav, HttpServletRequest request,
     OrderDetailsFilterForm filterForm) throws I18NException {

    int pagina = filterForm.getPage();
    request.getSession().setAttribute(getSessionAttributeFilterForm(), filterForm);

    captureSearchByValueOfAdditionalFields(request, filterForm);

    preList(request, mav, filterForm);

    List<OrderDetails> orderDetails = processarLlistat(orderDetailsEjb,
        filterForm, pagina, getAdditionalCondition(request), mav);

    mav.addObject("orderDetailsItems", orderDetails);

    mav.addObject("orderDetailsFilterForm", filterForm);

    fillReferencesForList(filterForm,request, mav, orderDetails, (List<GroupByItem>)mav.getModel().get("groupby_items"));

    postList(request, mav, filterForm, orderDetails);

    return orderDetails;
  }


  public Map<Field<?>, GroupByItem> fillReferencesForList(OrderDetailsFilterForm filterForm,
    HttpServletRequest request, ModelAndView mav,
      List<OrderDetails> list, List<GroupByItem> groupItems) throws I18NException {
    Map<Field<?>, GroupByItem> groupByItemsMap = new HashMap<Field<?>, GroupByItem>();
    for (GroupByItem groupByItem : groupItems) {
      groupByItemsMap.put(groupByItem.getField(),groupByItem);
    }

    Map<String, String> _tmp;
    List<StringKeyValue> _listSKV;

    // Field orderid
    {
      _listSKV = getReferenceListForOrderid(request, mav, filterForm, list, groupByItemsMap, null);
      _tmp = Utils.listToMap(_listSKV);
      filterForm.setMapOfOrdersForOrderid(_tmp);
      if (filterForm.getGroupByFields().contains(ORDERID)) {
        fillValuesToGroupByItems(_tmp, groupByItemsMap, ORDERID, false);
      };
    }

    // Field productid
    {
      _listSKV = getReferenceListForProductid(request, mav, filterForm, list, groupByItemsMap, null);
      _tmp = Utils.listToMap(_listSKV);
      filterForm.setMapOfProductsForProductid(_tmp);
      if (filterForm.getGroupByFields().contains(PRODUCTID)) {
        fillValuesToGroupByItems(_tmp, groupByItemsMap, PRODUCTID, false);
      };
    }


    return groupByItemsMap;
  }

  @RequestMapping(value = "/export/{dataExporterID}", method = RequestMethod.POST)
  public void exportList(@PathVariable("dataExporterID") String dataExporterID,
    HttpServletRequest request, HttpServletResponse response,
    OrderDetailsFilterForm filterForm) throws Exception, I18NException {

    ModelAndView mav = new ModelAndView(getTileList());
    List<OrderDetails> list = llistat(mav, request, filterForm);
    Field<?>[] allFields = ALL_ORDERDETAILS_FIELDS;

    java.util.Map<Field<?>, java.util.Map<String, String>> __mapping;
    __mapping = new java.util.HashMap<Field<?>, java.util.Map<String, String>>();
    __mapping.put(ORDERID, filterForm.getMapOfOrdersForOrderid());
    __mapping.put(PRODUCTID, filterForm.getMapOfProductsForProductid());
    exportData(request, response, dataExporterID, filterForm,
          list, allFields, __mapping, PRIMARYKEY_FIELDS);
  }



  /**
   * Carregar el formulari per un nou OrderDetails
   */
  @RequestMapping(value = "/new", method = RequestMethod.GET)
  public ModelAndView crearOrderDetailsGet(HttpServletRequest request,
      HttpServletResponse response) throws I18NException {

    if(!isActiveFormNew()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }
    ModelAndView mav = new ModelAndView(getTileForm());
    OrderDetailsForm orderDetailsForm = getOrderDetailsForm(null, false, request, mav);
    mav.addObject("orderDetailsForm" ,orderDetailsForm);
    fillReferencesForForm(orderDetailsForm, request, mav);
  
    return mav;
  }
  
  /**
   * 
   * @return
   * @throws Exception
   */
  public OrderDetailsForm getOrderDetailsForm(OrderDetailsJPA _jpa,
       boolean __isView, HttpServletRequest request, ModelAndView mav) throws I18NException {
    OrderDetailsForm orderDetailsForm;
    if(_jpa == null) {
      orderDetailsForm = new OrderDetailsForm(new OrderDetailsJPA(), true);
    } else {
      orderDetailsForm = new OrderDetailsForm(_jpa, false);
      orderDetailsForm.setView(__isView);
    }
    orderDetailsForm.setContexte(getContextWeb());
    orderDetailsForm.setEntityNameCode(getEntityNameCode());
    orderDetailsForm.setEntityNameCodePlural(getEntityNameCodePlural());
    return orderDetailsForm;
  }

  public void fillReferencesForForm(OrderDetailsForm orderDetailsForm,
    HttpServletRequest request, ModelAndView mav) throws I18NException {
    // Comprovam si ja esta definida la llista
    if (orderDetailsForm.getListOfOrdersForOrderid() == null) {
      List<StringKeyValue> _listSKV = getReferenceListForOrderid(request, mav, orderDetailsForm, null);

      if(_listSKV != null && !_listSKV.isEmpty()) { 
          java.util.Collections.sort(_listSKV, STRINGKEYVALUE_COMPARATOR);
      }
      orderDetailsForm.setListOfOrdersForOrderid(_listSKV);
    }
    // Comprovam si ja esta definida la llista
    if (orderDetailsForm.getListOfProductsForProductid() == null) {
      List<StringKeyValue> _listSKV = getReferenceListForProductid(request, mav, orderDetailsForm, null);

      if(_listSKV != null && !_listSKV.isEmpty()) { 
          java.util.Collections.sort(_listSKV, STRINGKEYVALUE_COMPARATOR);
      }
      orderDetailsForm.setListOfProductsForProductid(_listSKV);
    }
    
  }

  /**
   * Guardar un nou OrderDetails
   */
  @RequestMapping(value = "/new", method = RequestMethod.POST)
  public String crearOrderDetailsPost(@ModelAttribute OrderDetailsForm orderDetailsForm,
      BindingResult result, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    if(!isActiveFormNew()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }

    OrderDetailsJPA orderDetails = orderDetailsForm.getOrderDetails();

    try {
      preValidate(request, orderDetailsForm, result);
      getWebValidator().validate(orderDetailsForm, result);
      postValidate(request,orderDetailsForm, result);

      if (result.hasErrors()) {
        result.reject("error.form");
        return getTileForm();
      } else {
        orderDetails = create(request, orderDetails);
        createMessageSuccess(request, "success.creation", orderDetails.getOrderdetailid());
        orderDetailsForm.setOrderDetails(orderDetails);
        return getRedirectWhenCreated(request, orderDetailsForm);
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

  @RequestMapping(value = "/view/{orderdetailid}", method = RequestMethod.GET)
  public ModelAndView veureOrderDetailsGet(@PathVariable("orderdetailid") java.lang.Long orderdetailid,
      HttpServletRequest request,
      HttpServletResponse response) throws I18NException {
      return editAndViewOrderDetailsGet(orderdetailid,
        request, response, true);
  }


  protected ModelAndView editAndViewOrderDetailsGet(@PathVariable("orderdetailid") java.lang.Long orderdetailid,
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
    OrderDetailsJPA orderDetails = findByPrimaryKey(request, orderdetailid);

    if (orderDetails == null) {
      createMessageWarning(request, "error.notfound", orderdetailid);
      new ModelAndView(new RedirectView(getRedirectWhenCancel(request, orderdetailid), true));
      return llistatPaginat(request, response, 1);
    } else {
      ModelAndView mav = new ModelAndView(getTileForm());
      OrderDetailsForm orderDetailsForm = getOrderDetailsForm(orderDetails, __isView, request, mav);
      orderDetailsForm.setView(__isView);
      if(__isView) {
        orderDetailsForm.setAllFieldsReadOnly(ALL_ORDERDETAILS_FIELDS);
        orderDetailsForm.setSaveButtonVisible(false);
        orderDetailsForm.setDeleteButtonVisible(false);
      }
      fillReferencesForForm(orderDetailsForm, request, mav);
      mav.addObject("orderDetailsForm", orderDetailsForm);
      return mav;
    }
  }


  /**
   * Carregar el formulari per modificar un OrderDetails existent
   */
  @RequestMapping(value = "/{orderdetailid}/edit", method = RequestMethod.GET)
  public ModelAndView editarOrderDetailsGet(@PathVariable("orderdetailid") java.lang.Long orderdetailid,
      HttpServletRequest request,
      HttpServletResponse response) throws I18NException {
      return editAndViewOrderDetailsGet(orderdetailid,
        request, response, false);
  }



  /**
   * Editar un OrderDetails existent
   */
  @RequestMapping(value = "/{orderdetailid}/edit", method = RequestMethod.POST)
  public String editarOrderDetailsPost(@ModelAttribute OrderDetailsForm orderDetailsForm,
      BindingResult result, SessionStatus status, HttpServletRequest request,
      HttpServletResponse response) throws I18NException {

    if(!isActiveFormEdit()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }
    OrderDetailsJPA orderDetails = orderDetailsForm.getOrderDetails();

    try {
      preValidate(request, orderDetailsForm, result);
      getWebValidator().validate(orderDetailsForm, result);
      postValidate(request, orderDetailsForm, result);

      if (result.hasErrors()) {
        result.reject("error.form");
        return getTileForm();
      } else {
        orderDetails = update(request, orderDetails);
        createMessageSuccess(request, "success.modification", orderDetails.getOrderdetailid());
        status.setComplete();
        return getRedirectWhenModified(request, orderDetailsForm, null);
      }
    } catch (Throwable __e) {
      if (__e instanceof I18NValidationException) {
        ValidationWebUtils.addFieldErrorsToBindingResult(result, (I18NValidationException)__e);
        return getTileForm();
      }
      String msg = createMessageError(request, "error.modification",
          orderDetails.getOrderdetailid(), __e);
      log.error(msg, __e);
      return getRedirectWhenModified(request, orderDetailsForm, __e);
    }

  }


  /**
   * Eliminar un OrderDetails existent
   */
  @RequestMapping(value = "/{orderdetailid}/delete")
  public String eliminarOrderDetails(@PathVariable("orderdetailid") java.lang.Long orderdetailid,
      HttpServletRequest request,HttpServletResponse response) {

    if(!isActiveDelete()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }
    try {
      OrderDetails orderDetails = orderDetailsEjb.findByPrimaryKey(orderdetailid);
      if (orderDetails == null) {
        String __msg =createMessageError(request, "error.notfound", orderdetailid);
        return getRedirectWhenDelete(request, orderdetailid, new Exception(__msg));
      } else {
        delete(request, orderDetails);
        createMessageSuccess(request, "success.deleted", orderdetailid);
        return getRedirectWhenDelete(request, orderdetailid,null);
      }

    } catch (Throwable e) {
      String msg = createMessageError(request, "error.deleting", orderdetailid, e);
      log.error(msg, e);
      return getRedirectWhenDelete(request, orderdetailid, e);
    }
  }


@RequestMapping(value = "/deleteSelected", method = RequestMethod.POST)
public String deleteSelected(HttpServletRequest request,
    HttpServletResponse response,
    @ModelAttribute OrderDetailsFilterForm filterForm) throws Exception {

  if(!isActiveDelete()) {
    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    return null;
  }
  
  String[] seleccionats = filterForm.getSelectedItems();
  String redirect = null;
  if (seleccionats != null && seleccionats.length != 0) {
    for (int i = 0; i < seleccionats.length; i++) {
      redirect = eliminarOrderDetails(stringToPK(seleccionats[i]), request, response);
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
  public String[] getArgumentsMissatge(Object __orderdetailid, Throwable e) {
    java.lang.Long orderdetailid = (java.lang.Long)__orderdetailid;
    String exceptionMsg = "";
    if (e != null) {
      if (e instanceof I18NException) {
        exceptionMsg = I18NUtils.getMessage((I18NException)e);
      } else if (e instanceof I18NValidationException) {
      } else {
        exceptionMsg = e.getMessage();
      };
    };
    if (orderdetailid == null) {
      return new String[] { I18NUtils.tradueix(getEntityNameCode()),
         getPrimaryKeyColumnsTranslated(), null, exceptionMsg };
    } else {
      return new String[] { I18NUtils.tradueix(getEntityNameCode()),
        getPrimaryKeyColumnsTranslated(),
         String.valueOf(orderdetailid),
 exceptionMsg };
    }
  }

  public String getEntityNameCode() {
    return "orderDetails.orderDetails";
  }

  public String getEntityNameCodePlural() {
    return "orderDetails.orderDetails.plural";
  }

  public String getPrimaryKeyColumnsTranslated() {
    return  I18NUtils.tradueix("orderDetails.orderdetailid");
  }

  @InitBinder("orderDetailsFilterForm")
  public void initBinderFilterForm(WebDataBinder binder) {
    super.initBinder(binder);
  }

  @InitBinder("orderDetailsForm")
  public void initBinderForm(WebDataBinder binder) {
    super.initBinder(binder);

    binder.setValidator(getWebValidator());

    binder.setDisallowedFields("orderdetailid");

  }

  public OrderDetailsWebValidator getWebValidator() {
    return orderDetailsWebValidator;
  }


  public void setWebValidator(OrderDetailsWebValidator __val) {
    if (__val != null) {
      this.orderDetailsWebValidator= __val;
    }
  }


  /**
   * Entra aqui al pitjar el boto cancel en el llistat de OrderDetails
   */
  @RequestMapping(value = "/{orderdetailid}/cancel")
  public String cancelOrderDetails(@PathVariable("orderdetailid") java.lang.Long orderdetailid,
      HttpServletRequest request,HttpServletResponse response) {
     return getRedirectWhenCancel(request, orderdetailid);
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


  public List<StringKeyValue> getReferenceListForOrderid(HttpServletRequest request,
       ModelAndView mav, OrderDetailsForm orderDetailsForm, Where where)  throws I18NException {
    if (orderDetailsForm.isHiddenField(ORDERID)) {
      return EMPTY_STRINGKEYVALUE_LIST;
    }
    Where _where = null;
    if (orderDetailsForm.isReadOnlyField(ORDERID)) {
      _where = OrdersFields.ORDERID.equal(orderDetailsForm.getOrderDetails().getOrderid());
    }
    return getReferenceListForOrderid(request, mav, Where.AND(where, _where));
  }


  public List<StringKeyValue> getReferenceListForOrderid(HttpServletRequest request,
       ModelAndView mav, OrderDetailsFilterForm orderDetailsFilterForm,
       List<OrderDetails> list, Map<Field<?>, GroupByItem> _groupByItemsMap, Where where)  throws I18NException {
    if (orderDetailsFilterForm.isHiddenField(ORDERID)
      && !orderDetailsFilterForm.isGroupByField(ORDERID)) {
      return EMPTY_STRINGKEYVALUE_LIST;
    }
    Where _w = null;
    if (!_groupByItemsMap.containsKey(ORDERID)) {
      // OBTENIR TOTES LES CLAUS (PK) i despres només cercar referències d'aquestes PK
      java.util.Set<java.lang.Long> _pkList = new java.util.HashSet<java.lang.Long>();
      for (OrderDetails _item : list) {
        if(_item.getOrderid() == null) { continue; };
        _pkList.add(_item.getOrderid());
        }
        _w = OrdersFields.ORDERID.in(_pkList);
      }
    return getReferenceListForOrderid(request, mav, Where.AND(where,_w));
  }


  public List<StringKeyValue> getReferenceListForOrderid(HttpServletRequest request,
       ModelAndView mav, Where where)  throws I18NException {
    return ordersRefList.getReferenceList(OrdersFields.ORDERID, where );
  }


  public List<StringKeyValue> getReferenceListForProductid(HttpServletRequest request,
       ModelAndView mav, OrderDetailsForm orderDetailsForm, Where where)  throws I18NException {
    if (orderDetailsForm.isHiddenField(PRODUCTID)) {
      return EMPTY_STRINGKEYVALUE_LIST;
    }
    Where _where = null;
    if (orderDetailsForm.isReadOnlyField(PRODUCTID)) {
      _where = ProductsFields.PRODUCTID.equal(orderDetailsForm.getOrderDetails().getProductid());
    }
    return getReferenceListForProductid(request, mav, Where.AND(where, _where));
  }


  public List<StringKeyValue> getReferenceListForProductid(HttpServletRequest request,
       ModelAndView mav, OrderDetailsFilterForm orderDetailsFilterForm,
       List<OrderDetails> list, Map<Field<?>, GroupByItem> _groupByItemsMap, Where where)  throws I18NException {
    if (orderDetailsFilterForm.isHiddenField(PRODUCTID)
      && !orderDetailsFilterForm.isGroupByField(PRODUCTID)) {
      return EMPTY_STRINGKEYVALUE_LIST;
    }
    Where _w = null;
    if (!_groupByItemsMap.containsKey(PRODUCTID)) {
      // OBTENIR TOTES LES CLAUS (PK) i despres només cercar referències d'aquestes PK
      java.util.Set<java.lang.Long> _pkList = new java.util.HashSet<java.lang.Long>();
      for (OrderDetails _item : list) {
        if(_item.getProductid() == null) { continue; };
        _pkList.add(_item.getProductid());
        }
        _w = ProductsFields.PRODUCTID.in(_pkList);
      }
    return getReferenceListForProductid(request, mav, Where.AND(where,_w));
  }


  public List<StringKeyValue> getReferenceListForProductid(HttpServletRequest request,
       ModelAndView mav, Where where)  throws I18NException {
    return productsRefList.getReferenceList(ProductsFields.PRODUCTID, where );
  }


  public void preValidate(HttpServletRequest request,OrderDetailsForm orderDetailsForm , BindingResult result)  throws I18NException {
  }

  public void postValidate(HttpServletRequest request,OrderDetailsForm orderDetailsForm, BindingResult result)  throws I18NException {
  }

  public void preList(HttpServletRequest request, ModelAndView mav, OrderDetailsFilterForm filterForm)  throws I18NException {
  }

  public void postList(HttpServletRequest request, ModelAndView mav, OrderDetailsFilterForm filterForm,  List<OrderDetails> list) throws I18NException {
  }

  public String getRedirectWhenCreated(HttpServletRequest request, OrderDetailsForm orderDetailsForm) {
    return "redirect:" + getContextWeb() + "/list/1";
  }

  public String getRedirectWhenModified(HttpServletRequest request, OrderDetailsForm orderDetailsForm, Throwable __e) {
    if (__e == null) {
      return "redirect:" + getContextWeb() + "/list";
    } else {
      return  getTileForm();
    }
  }

  public String getRedirectWhenDelete(HttpServletRequest request, java.lang.Long orderdetailid, Throwable __e) {
    return "redirect:" + getContextWeb() + "/list";
  }

  public String getRedirectWhenCancel(HttpServletRequest request, java.lang.Long orderdetailid) {
    return "redirect:" + getContextWeb() + "/list";
  }

  public String getTileForm() {
    return "orderDetailsFormWebDB";
  }

  public String getTileList() {
    return "orderDetailsListWebDB";
  }

  @Override
  /** Ha de ser igual que el RequestMapping de la Classe */
  public String getContextWeb() {
    RequestMapping rm = AnnotationUtils.findAnnotation(this.getClass(), RequestMapping.class);
    return rm.value()[0];
  }

  public String getSessionAttributeFilterForm() {
    return "OrderDetailsWebDB_FilterForm";
  }



  public Where getAdditionalCondition(HttpServletRequest request) throws I18NException {
    return null;
  }


  public OrderDetailsJPA findByPrimaryKey(HttpServletRequest request, java.lang.Long orderdetailid) throws I18NException {
    return (OrderDetailsJPA) orderDetailsEjb.findByPrimaryKey(orderdetailid);
  }


  public OrderDetailsJPA create(HttpServletRequest request, OrderDetailsJPA orderDetails)
    throws Exception,I18NException, I18NValidationException {
    return (OrderDetailsJPA) orderDetailsEjb.create(orderDetails);
  }


  public OrderDetailsJPA update(HttpServletRequest request, OrderDetailsJPA orderDetails)
    throws Exception,I18NException, I18NValidationException {
    return (OrderDetailsJPA) orderDetailsEjb.update(orderDetails);
  }


  public void delete(HttpServletRequest request, OrderDetails orderDetails) throws Exception,I18NException {
    orderDetailsEjb.delete(orderDetails);
  }

} // Final de Classe

