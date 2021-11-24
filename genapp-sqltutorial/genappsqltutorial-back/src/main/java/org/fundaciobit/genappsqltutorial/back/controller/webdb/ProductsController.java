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
import org.fundaciobit.genappsqltutorial.back.form.webdb.ProductsForm;

import org.fundaciobit.genappsqltutorial.back.validator.webdb.ProductsWebValidator;

import org.fundaciobit.genappsqltutorial.persistence.ProductsJPA;
import org.fundaciobit.genappsqltutorial.model.entity.Products;
import org.fundaciobit.genappsqltutorial.model.fields.*;

/**
 * Controller per gestionar un Products
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * 
 * @author GenApp
 */
@Controller
@RequestMapping(value = "/webdb/products")
@SessionAttributes(types = { ProductsForm.class, ProductsFilterForm.class })
public class ProductsController
    extends org.fundaciobit.genappsqltutorial.back.controller.GenAppSqlTutorialBaseController<Products, java.lang.Long> implements ProductsFields {

  @EJB(mappedName = org.fundaciobit.genappsqltutorial.ejb.ProductsService.JNDI_NAME)
  protected org.fundaciobit.genappsqltutorial.ejb.ProductsService productsEjb;

  @Autowired
  private ProductsWebValidator productsWebValidator;

  @Autowired
  protected ProductsRefList productsRefList;

  /**
   * Llistat de totes Products
   */
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public String llistat(HttpServletRequest request,
    HttpServletResponse response) throws I18NException {
    ProductsFilterForm ff;
    ff = (ProductsFilterForm) request.getSession().getAttribute(getSessionAttributeFilterForm());
    int pagina = (ff == null)? 1: ff.getPage();
    return "redirect:" + getContextWeb() + "/list/" + pagina;
  }

  /**
   * Primera peticio per llistar Products de forma paginada
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
    llistat(mav, request, getProductsFilterForm(pagina, mav, request));
    return mav;
  }

  public ProductsFilterForm getProductsFilterForm(Integer pagina, ModelAndView mav,
    HttpServletRequest request) throws I18NException {
    ProductsFilterForm productsFilterForm;
    productsFilterForm = (ProductsFilterForm) request.getSession().getAttribute(getSessionAttributeFilterForm());
    if(productsFilterForm == null) {
      productsFilterForm = new ProductsFilterForm();
      productsFilterForm.setContexte(getContextWeb());
      productsFilterForm.setEntityNameCode(getEntityNameCode());
      productsFilterForm.setEntityNameCodePlural(getEntityNameCodePlural());
      productsFilterForm.setNou(true);
    } else {
      productsFilterForm.setNou(false);
    }
    productsFilterForm.setPage(pagina == null ? 1 : pagina);
    return productsFilterForm;
  }

  /**
   * Segona i següent peticions per llistar Products de forma paginada
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
      @ModelAttribute ProductsFilterForm filterForm) throws I18NException {
    if(!isActiveList()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }

    ModelAndView mav = new ModelAndView(getTileList());

    filterForm.setPage(pagina == null ? 1 : pagina);
    // Actualitza el filter form

    request.getSession().setAttribute(getSessionAttributeFilterForm(), filterForm);
    filterForm = getProductsFilterForm(pagina, mav, request);

    llistat(mav, request, filterForm);
    return mav;
  }

  /**
   * Codi centralitzat de llistat de Products de forma paginada.
   * 
   * @param request
   * @param filterForm
   * @param pagina
   * @return
   * @throws I18NException
   */
  protected List<Products> llistat(ModelAndView mav, HttpServletRequest request,
     ProductsFilterForm filterForm) throws I18NException {

    int pagina = filterForm.getPage();
    request.getSession().setAttribute(getSessionAttributeFilterForm(), filterForm);

    captureSearchByValueOfAdditionalFields(request, filterForm);

    preList(request, mav, filterForm);

    List<Products> products = processarLlistat(productsEjb,
        filterForm, pagina, getAdditionalCondition(request), mav);

    mav.addObject("productsItems", products);

    mav.addObject("productsFilterForm", filterForm);

    fillReferencesForList(filterForm,request, mav, products, (List<GroupByItem>)mav.getModel().get("groupby_items"));

    postList(request, mav, filterForm, products);

    return products;
  }


  public Map<Field<?>, GroupByItem> fillReferencesForList(ProductsFilterForm filterForm,
    HttpServletRequest request, ModelAndView mav,
      List<Products> list, List<GroupByItem> groupItems) throws I18NException {
    Map<Field<?>, GroupByItem> groupByItemsMap = new HashMap<Field<?>, GroupByItem>();
    for (GroupByItem groupByItem : groupItems) {
      groupByItemsMap.put(groupByItem.getField(),groupByItem);
    }


    return groupByItemsMap;
  }

  @RequestMapping(value = "/export/{dataExporterID}", method = RequestMethod.POST)
  public void exportList(@PathVariable("dataExporterID") String dataExporterID,
    HttpServletRequest request, HttpServletResponse response,
    ProductsFilterForm filterForm) throws Exception, I18NException {

    ModelAndView mav = new ModelAndView(getTileList());
    List<Products> list = llistat(mav, request, filterForm);
    Field<?>[] allFields = ALL_PRODUCTS_FIELDS;

    java.util.Map<Field<?>, java.util.Map<String, String>> __mapping;
    __mapping = new java.util.HashMap<Field<?>, java.util.Map<String, String>>();
    exportData(request, response, dataExporterID, filterForm,
          list, allFields, __mapping, PRIMARYKEY_FIELDS);
  }



  /**
   * Carregar el formulari per un nou Products
   */
  @RequestMapping(value = "/new", method = RequestMethod.GET)
  public ModelAndView crearProductsGet(HttpServletRequest request,
      HttpServletResponse response) throws I18NException {

    if(!isActiveFormNew()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }
    ModelAndView mav = new ModelAndView(getTileForm());
    ProductsForm productsForm = getProductsForm(null, false, request, mav);
    mav.addObject("productsForm" ,productsForm);
    fillReferencesForForm(productsForm, request, mav);
  
    return mav;
  }
  
  /**
   * 
   * @return
   * @throws Exception
   */
  public ProductsForm getProductsForm(ProductsJPA _jpa,
       boolean __isView, HttpServletRequest request, ModelAndView mav) throws I18NException {
    ProductsForm productsForm;
    if(_jpa == null) {
      productsForm = new ProductsForm(new ProductsJPA(), true);
    } else {
      productsForm = new ProductsForm(_jpa, false);
      productsForm.setView(__isView);
    }
    productsForm.setContexte(getContextWeb());
    productsForm.setEntityNameCode(getEntityNameCode());
    productsForm.setEntityNameCodePlural(getEntityNameCodePlural());
    return productsForm;
  }

  public void fillReferencesForForm(ProductsForm productsForm,
    HttpServletRequest request, ModelAndView mav) throws I18NException {
    
  }

  /**
   * Guardar un nou Products
   */
  @RequestMapping(value = "/new", method = RequestMethod.POST)
  public String crearProductsPost(@ModelAttribute ProductsForm productsForm,
      BindingResult result, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    if(!isActiveFormNew()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }

    ProductsJPA products = productsForm.getProducts();

    try {
      preValidate(request, productsForm, result);
      getWebValidator().validate(productsForm, result);
      postValidate(request,productsForm, result);

      if (result.hasErrors()) {
        result.reject("error.form");
        return getTileForm();
      } else {
        products = create(request, products);
        createMessageSuccess(request, "success.creation", products.getProductid());
        productsForm.setProducts(products);
        return getRedirectWhenCreated(request, productsForm);
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

  @RequestMapping(value = "/view/{productid}", method = RequestMethod.GET)
  public ModelAndView veureProductsGet(@PathVariable("productid") java.lang.Long productid,
      HttpServletRequest request,
      HttpServletResponse response) throws I18NException {
      return editAndViewProductsGet(productid,
        request, response, true);
  }


  protected ModelAndView editAndViewProductsGet(@PathVariable("productid") java.lang.Long productid,
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
    ProductsJPA products = findByPrimaryKey(request, productid);

    if (products == null) {
      createMessageWarning(request, "error.notfound", productid);
      new ModelAndView(new RedirectView(getRedirectWhenCancel(request, productid), true));
      return llistatPaginat(request, response, 1);
    } else {
      ModelAndView mav = new ModelAndView(getTileForm());
      ProductsForm productsForm = getProductsForm(products, __isView, request, mav);
      productsForm.setView(__isView);
      if(__isView) {
        productsForm.setAllFieldsReadOnly(ALL_PRODUCTS_FIELDS);
        productsForm.setSaveButtonVisible(false);
        productsForm.setDeleteButtonVisible(false);
      }
      fillReferencesForForm(productsForm, request, mav);
      mav.addObject("productsForm", productsForm);
      return mav;
    }
  }


  /**
   * Carregar el formulari per modificar un Products existent
   */
  @RequestMapping(value = "/{productid}/edit", method = RequestMethod.GET)
  public ModelAndView editarProductsGet(@PathVariable("productid") java.lang.Long productid,
      HttpServletRequest request,
      HttpServletResponse response) throws I18NException {
      return editAndViewProductsGet(productid,
        request, response, false);
  }



  /**
   * Editar un Products existent
   */
  @RequestMapping(value = "/{productid}/edit", method = RequestMethod.POST)
  public String editarProductsPost(@ModelAttribute ProductsForm productsForm,
      BindingResult result, SessionStatus status, HttpServletRequest request,
      HttpServletResponse response) throws I18NException {

    if(!isActiveFormEdit()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }
    ProductsJPA products = productsForm.getProducts();

    try {
      preValidate(request, productsForm, result);
      getWebValidator().validate(productsForm, result);
      postValidate(request, productsForm, result);

      if (result.hasErrors()) {
        result.reject("error.form");
        return getTileForm();
      } else {
        products = update(request, products);
        createMessageSuccess(request, "success.modification", products.getProductid());
        status.setComplete();
        return getRedirectWhenModified(request, productsForm, null);
      }
    } catch (Throwable __e) {
      if (__e instanceof I18NValidationException) {
        ValidationWebUtils.addFieldErrorsToBindingResult(result, (I18NValidationException)__e);
        return getTileForm();
      }
      String msg = createMessageError(request, "error.modification",
          products.getProductid(), __e);
      log.error(msg, __e);
      return getRedirectWhenModified(request, productsForm, __e);
    }

  }


  /**
   * Eliminar un Products existent
   */
  @RequestMapping(value = "/{productid}/delete")
  public String eliminarProducts(@PathVariable("productid") java.lang.Long productid,
      HttpServletRequest request,HttpServletResponse response) {

    if(!isActiveDelete()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }
    try {
      Products products = productsEjb.findByPrimaryKey(productid);
      if (products == null) {
        String __msg =createMessageError(request, "error.notfound", productid);
        return getRedirectWhenDelete(request, productid, new Exception(__msg));
      } else {
        delete(request, products);
        createMessageSuccess(request, "success.deleted", productid);
        return getRedirectWhenDelete(request, productid,null);
      }

    } catch (Throwable e) {
      String msg = createMessageError(request, "error.deleting", productid, e);
      log.error(msg, e);
      return getRedirectWhenDelete(request, productid, e);
    }
  }


@RequestMapping(value = "/deleteSelected", method = RequestMethod.POST)
public String deleteSelected(HttpServletRequest request,
    HttpServletResponse response,
    @ModelAttribute ProductsFilterForm filterForm) throws Exception {

  if(!isActiveDelete()) {
    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    return null;
  }
  
  String[] seleccionats = filterForm.getSelectedItems();
  String redirect = null;
  if (seleccionats != null && seleccionats.length != 0) {
    for (int i = 0; i < seleccionats.length; i++) {
      redirect = eliminarProducts(stringToPK(seleccionats[i]), request, response);
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
  public String[] getArgumentsMissatge(Object __productid, Throwable e) {
    java.lang.Long productid = (java.lang.Long)__productid;
    String exceptionMsg = "";
    if (e != null) {
      if (e instanceof I18NException) {
        exceptionMsg = I18NUtils.getMessage((I18NException)e);
      } else if (e instanceof I18NValidationException) {
      } else {
        exceptionMsg = e.getMessage();
      };
    };
    if (productid == null) {
      return new String[] { I18NUtils.tradueix(getEntityNameCode()),
         getPrimaryKeyColumnsTranslated(), null, exceptionMsg };
    } else {
      return new String[] { I18NUtils.tradueix(getEntityNameCode()),
        getPrimaryKeyColumnsTranslated(),
         String.valueOf(productid),
 exceptionMsg };
    }
  }

  public String getEntityNameCode() {
    return "products.products";
  }

  public String getEntityNameCodePlural() {
    return "products.products.plural";
  }

  public String getPrimaryKeyColumnsTranslated() {
    return  I18NUtils.tradueix("products.productid");
  }

  @InitBinder("productsFilterForm")
  public void initBinderFilterForm(WebDataBinder binder) {
    super.initBinder(binder);
  }

  @InitBinder("productsForm")
  public void initBinderForm(WebDataBinder binder) {
    super.initBinder(binder);

    binder.setValidator(getWebValidator());


    initDisallowedFields(binder, "products.productid");
  }

  public ProductsWebValidator getWebValidator() {
    return productsWebValidator;
  }


  public void setWebValidator(ProductsWebValidator __val) {
    if (__val != null) {
      this.productsWebValidator= __val;
    }
  }


  /**
   * Entra aqui al pitjar el boto cancel en el llistat de Products
   */
  @RequestMapping(value = "/{productid}/cancel")
  public String cancelProducts(@PathVariable("productid") java.lang.Long productid,
      HttpServletRequest request,HttpServletResponse response) {
     return getRedirectWhenCancel(request, productid);
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


  public void preValidate(HttpServletRequest request,ProductsForm productsForm , BindingResult result)  throws I18NException {
  }

  public void postValidate(HttpServletRequest request,ProductsForm productsForm, BindingResult result)  throws I18NException {
  }

  public void preList(HttpServletRequest request, ModelAndView mav, ProductsFilterForm filterForm)  throws I18NException {
  }

  public void postList(HttpServletRequest request, ModelAndView mav, ProductsFilterForm filterForm,  List<Products> list) throws I18NException {
  }

  public String getRedirectWhenCreated(HttpServletRequest request, ProductsForm productsForm) {
    return "redirect:" + getContextWeb() + "/list/1";
  }

  public String getRedirectWhenModified(HttpServletRequest request, ProductsForm productsForm, Throwable __e) {
    if (__e == null) {
      return "redirect:" + getContextWeb() + "/list";
    } else {
      return  getTileForm();
    }
  }

  public String getRedirectWhenDelete(HttpServletRequest request, java.lang.Long productid, Throwable __e) {
    return "redirect:" + getContextWeb() + "/list";
  }

  public String getRedirectWhenCancel(HttpServletRequest request, java.lang.Long productid) {
    return "redirect:" + getContextWeb() + "/list";
  }

  public String getTileForm() {
    return "productsFormWebDB";
  }

  public String getTileList() {
    return "productsListWebDB";
  }

  @Override
  /** Ha de ser igual que el RequestMapping de la Classe */
  public String getContextWeb() {
    RequestMapping rm = AnnotationUtils.findAnnotation(this.getClass(), RequestMapping.class);
    return rm.value()[0];
  }

  public String getSessionAttributeFilterForm() {
    return "ProductsWebDB_FilterForm";
  }



  public Where getAdditionalCondition(HttpServletRequest request) throws I18NException {
    return null;
  }


  public ProductsJPA findByPrimaryKey(HttpServletRequest request, java.lang.Long productid) throws I18NException {
    return (ProductsJPA) productsEjb.findByPrimaryKey(productid);
  }


  public ProductsJPA create(HttpServletRequest request, ProductsJPA products)
    throws Exception,I18NException, I18NValidationException {
    return (ProductsJPA) productsEjb.create(products);
  }


  public ProductsJPA update(HttpServletRequest request, ProductsJPA products)
    throws Exception,I18NException, I18NValidationException {
    return (ProductsJPA) productsEjb.update(products);
  }


  public void delete(HttpServletRequest request, Products products) throws Exception,I18NException {
    productsEjb.delete(products);
  }

} // Final de Classe

