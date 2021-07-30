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
import org.fundaciobit.genappsqltutorial.back.form.webdb.CategoriesForm;

import org.fundaciobit.genappsqltutorial.back.validator.webdb.CategoriesWebValidator;

import org.fundaciobit.genappsqltutorial.persistence.CategoriesJPA;
import org.fundaciobit.genappsqltutorial.model.entity.Categories;
import org.fundaciobit.genappsqltutorial.model.fields.*;

/**
 * Controller per gestionar un Categories
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * 
 * @author GenApp
 */
@Controller
@RequestMapping(value = "/webdb/categories")
@SessionAttributes(types = { CategoriesForm.class, CategoriesFilterForm.class })
public class CategoriesController
    extends org.fundaciobit.genappsqltutorial.back.controller.GenAppSqlTutorialBaseController<Categories, java.lang.Long> implements CategoriesFields {

  @EJB(mappedName = org.fundaciobit.genappsqltutorial.ejb.CategoriesService.JNDI_NAME)
  protected org.fundaciobit.genappsqltutorial.ejb.CategoriesService categoriesEjb;

  @Autowired
  private CategoriesWebValidator categoriesWebValidator;

  @Autowired
  protected CategoriesRefList categoriesRefList;

  /**
   * Llistat de totes Categories
   */
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public String llistat(HttpServletRequest request,
    HttpServletResponse response) throws I18NException {
    CategoriesFilterForm ff;
    ff = (CategoriesFilterForm) request.getSession().getAttribute(getSessionAttributeFilterForm());
    int pagina = (ff == null)? 1: ff.getPage();
    return "redirect:" + getContextWeb() + "/list/" + pagina;
  }

  /**
   * Primera peticio per llistar Categories de forma paginada
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
    llistat(mav, request, getCategoriesFilterForm(pagina, mav, request));
    return mav;
  }

  public CategoriesFilterForm getCategoriesFilterForm(Integer pagina, ModelAndView mav,
    HttpServletRequest request) throws I18NException {
    CategoriesFilterForm categoriesFilterForm;
    categoriesFilterForm = (CategoriesFilterForm) request.getSession().getAttribute(getSessionAttributeFilterForm());
    if(categoriesFilterForm == null) {
      categoriesFilterForm = new CategoriesFilterForm();
      categoriesFilterForm.setContexte(getContextWeb());
      categoriesFilterForm.setEntityNameCode(getEntityNameCode());
      categoriesFilterForm.setEntityNameCodePlural(getEntityNameCodePlural());
      categoriesFilterForm.setNou(true);
    } else {
      categoriesFilterForm.setNou(false);
    }
    categoriesFilterForm.setPage(pagina == null ? 1 : pagina);
    return categoriesFilterForm;
  }

  /**
   * Segona i següent peticions per llistar Categories de forma paginada
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
      @ModelAttribute CategoriesFilterForm filterForm) throws I18NException {
    if(!isActiveList()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }

    ModelAndView mav = new ModelAndView(getTileList());

    filterForm.setPage(pagina == null ? 1 : pagina);
    // Actualitza el filter form

    request.getSession().setAttribute(getSessionAttributeFilterForm(), filterForm);
    filterForm = getCategoriesFilterForm(pagina, mav, request);

    llistat(mav, request, filterForm);
    return mav;
  }

  /**
   * Codi centralitzat de llistat de Categories de forma paginada.
   * 
   * @param request
   * @param filterForm
   * @param pagina
   * @return
   * @throws I18NException
   */
  protected List<Categories> llistat(ModelAndView mav, HttpServletRequest request,
     CategoriesFilterForm filterForm) throws I18NException {

    int pagina = filterForm.getPage();
    request.getSession().setAttribute(getSessionAttributeFilterForm(), filterForm);

    captureSearchByValueOfAdditionalFields(request, filterForm);

    preList(request, mav, filterForm);

    List<Categories> categories = processarLlistat(categoriesEjb,
        filterForm, pagina, getAdditionalCondition(request), mav);

    mav.addObject("categoriesItems", categories);

    mav.addObject("categoriesFilterForm", filterForm);

    fillReferencesForList(filterForm,request, mav, categories, (List<GroupByItem>)mav.getModel().get("groupby_items"));

    postList(request, mav, filterForm, categories);

    return categories;
  }


  public Map<Field<?>, GroupByItem> fillReferencesForList(CategoriesFilterForm filterForm,
    HttpServletRequest request, ModelAndView mav,
      List<Categories> list, List<GroupByItem> groupItems) throws I18NException {
    Map<Field<?>, GroupByItem> groupByItemsMap = new HashMap<Field<?>, GroupByItem>();
    for (GroupByItem groupByItem : groupItems) {
      groupByItemsMap.put(groupByItem.getField(),groupByItem);
    }


    return groupByItemsMap;
  }

  @RequestMapping(value = "/export/{dataExporterID}", method = RequestMethod.POST)
  public void exportList(@PathVariable("dataExporterID") String dataExporterID,
    HttpServletRequest request, HttpServletResponse response,
    CategoriesFilterForm filterForm) throws Exception, I18NException {

    ModelAndView mav = new ModelAndView(getTileList());
    List<Categories> list = llistat(mav, request, filterForm);
    Field<?>[] allFields = ALL_CATEGORIES_FIELDS;

    java.util.Map<Field<?>, java.util.Map<String, String>> __mapping;
    __mapping = new java.util.HashMap<Field<?>, java.util.Map<String, String>>();
    exportData(request, response, dataExporterID, filterForm,
          list, allFields, __mapping, PRIMARYKEY_FIELDS);
  }



  /**
   * Carregar el formulari per un nou Categories
   */
  @RequestMapping(value = "/new", method = RequestMethod.GET)
  public ModelAndView crearCategoriesGet(HttpServletRequest request,
      HttpServletResponse response) throws I18NException {

    if(!isActiveFormNew()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }
    ModelAndView mav = new ModelAndView(getTileForm());
    CategoriesForm categoriesForm = getCategoriesForm(null, false, request, mav);
    mav.addObject("categoriesForm" ,categoriesForm);
    fillReferencesForForm(categoriesForm, request, mav);
  
    return mav;
  }
  
  /**
   * 
   * @return
   * @throws Exception
   */
  public CategoriesForm getCategoriesForm(CategoriesJPA _jpa,
       boolean __isView, HttpServletRequest request, ModelAndView mav) throws I18NException {
    CategoriesForm categoriesForm;
    if(_jpa == null) {
      categoriesForm = new CategoriesForm(new CategoriesJPA(), true);
    } else {
      categoriesForm = new CategoriesForm(_jpa, false);
      categoriesForm.setView(__isView);
    }
    categoriesForm.setContexte(getContextWeb());
    categoriesForm.setEntityNameCode(getEntityNameCode());
    categoriesForm.setEntityNameCodePlural(getEntityNameCodePlural());
    return categoriesForm;
  }

  public void fillReferencesForForm(CategoriesForm categoriesForm,
    HttpServletRequest request, ModelAndView mav) throws I18NException {
    
  }

  /**
   * Guardar un nou Categories
   */
  @RequestMapping(value = "/new", method = RequestMethod.POST)
  public String crearCategoriesPost(@ModelAttribute CategoriesForm categoriesForm,
      BindingResult result, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    if(!isActiveFormNew()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }

    CategoriesJPA categories = categoriesForm.getCategories();

    try {
      preValidate(request, categoriesForm, result);
      getWebValidator().validate(categoriesForm, result);
      postValidate(request,categoriesForm, result);

      if (result.hasErrors()) {
        result.reject("error.form");
        return getTileForm();
      } else {
        categories = create(request, categories);
        createMessageSuccess(request, "success.creation", categories.getCategoryid());
        categoriesForm.setCategories(categories);
        return getRedirectWhenCreated(request, categoriesForm);
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

  @RequestMapping(value = "/view/{categoryid}", method = RequestMethod.GET)
  public ModelAndView veureCategoriesGet(@PathVariable("categoryid") java.lang.Long categoryid,
      HttpServletRequest request,
      HttpServletResponse response) throws I18NException {
      return editAndViewCategoriesGet(categoryid,
        request, response, true);
  }


  protected ModelAndView editAndViewCategoriesGet(@PathVariable("categoryid") java.lang.Long categoryid,
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
    CategoriesJPA categories = findByPrimaryKey(request, categoryid);

    if (categories == null) {
      createMessageWarning(request, "error.notfound", categoryid);
      new ModelAndView(new RedirectView(getRedirectWhenCancel(request, categoryid), true));
      return llistatPaginat(request, response, 1);
    } else {
      ModelAndView mav = new ModelAndView(getTileForm());
      CategoriesForm categoriesForm = getCategoriesForm(categories, __isView, request, mav);
      categoriesForm.setView(__isView);
      if(__isView) {
        categoriesForm.setAllFieldsReadOnly(ALL_CATEGORIES_FIELDS);
        categoriesForm.setSaveButtonVisible(false);
        categoriesForm.setDeleteButtonVisible(false);
      }
      fillReferencesForForm(categoriesForm, request, mav);
      mav.addObject("categoriesForm", categoriesForm);
      return mav;
    }
  }


  /**
   * Carregar el formulari per modificar un Categories existent
   */
  @RequestMapping(value = "/{categoryid}/edit", method = RequestMethod.GET)
  public ModelAndView editarCategoriesGet(@PathVariable("categoryid") java.lang.Long categoryid,
      HttpServletRequest request,
      HttpServletResponse response) throws I18NException {
      return editAndViewCategoriesGet(categoryid,
        request, response, false);
  }



  /**
   * Editar un Categories existent
   */
  @RequestMapping(value = "/{categoryid}/edit", method = RequestMethod.POST)
  public String editarCategoriesPost(@ModelAttribute CategoriesForm categoriesForm,
      BindingResult result, SessionStatus status, HttpServletRequest request,
      HttpServletResponse response) throws I18NException {

    if(!isActiveFormEdit()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }
    CategoriesJPA categories = categoriesForm.getCategories();

    try {
      preValidate(request, categoriesForm, result);
      getWebValidator().validate(categoriesForm, result);
      postValidate(request, categoriesForm, result);

      if (result.hasErrors()) {
        result.reject("error.form");
        return getTileForm();
      } else {
        categories = update(request, categories);
        createMessageSuccess(request, "success.modification", categories.getCategoryid());
        status.setComplete();
        return getRedirectWhenModified(request, categoriesForm, null);
      }
    } catch (Throwable __e) {
      if (__e instanceof I18NValidationException) {
        ValidationWebUtils.addFieldErrorsToBindingResult(result, (I18NValidationException)__e);
        return getTileForm();
      }
      String msg = createMessageError(request, "error.modification",
          categories.getCategoryid(), __e);
      log.error(msg, __e);
      return getRedirectWhenModified(request, categoriesForm, __e);
    }

  }


  /**
   * Eliminar un Categories existent
   */
  @RequestMapping(value = "/{categoryid}/delete")
  public String eliminarCategories(@PathVariable("categoryid") java.lang.Long categoryid,
      HttpServletRequest request,HttpServletResponse response) {

    if(!isActiveDelete()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }
    try {
      Categories categories = categoriesEjb.findByPrimaryKey(categoryid);
      if (categories == null) {
        String __msg =createMessageError(request, "error.notfound", categoryid);
        return getRedirectWhenDelete(request, categoryid, new Exception(__msg));
      } else {
        delete(request, categories);
        createMessageSuccess(request, "success.deleted", categoryid);
        return getRedirectWhenDelete(request, categoryid,null);
      }

    } catch (Throwable e) {
      String msg = createMessageError(request, "error.deleting", categoryid, e);
      log.error(msg, e);
      return getRedirectWhenDelete(request, categoryid, e);
    }
  }


@RequestMapping(value = "/deleteSelected", method = RequestMethod.POST)
public String deleteSelected(HttpServletRequest request,
    HttpServletResponse response,
    @ModelAttribute CategoriesFilterForm filterForm) throws Exception {

  if(!isActiveDelete()) {
    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    return null;
  }
  
  String[] seleccionats = filterForm.getSelectedItems();
  String redirect = null;
  if (seleccionats != null && seleccionats.length != 0) {
    for (int i = 0; i < seleccionats.length; i++) {
      redirect = eliminarCategories(stringToPK(seleccionats[i]), request, response);
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
  public String[] getArgumentsMissatge(Object __categoryid, Throwable e) {
    java.lang.Long categoryid = (java.lang.Long)__categoryid;
    String exceptionMsg = "";
    if (e != null) {
      if (e instanceof I18NException) {
        exceptionMsg = I18NUtils.getMessage((I18NException)e);
      } else if (e instanceof I18NValidationException) {
      } else {
        exceptionMsg = e.getMessage();
      };
    };
    if (categoryid == null) {
      return new String[] { I18NUtils.tradueix(getEntityNameCode()),
         getPrimaryKeyColumnsTranslated(), null, exceptionMsg };
    } else {
      return new String[] { I18NUtils.tradueix(getEntityNameCode()),
        getPrimaryKeyColumnsTranslated(),
         String.valueOf(categoryid),
 exceptionMsg };
    }
  }

  public String getEntityNameCode() {
    return "categories.categories";
  }

  public String getEntityNameCodePlural() {
    return "categories.categories.plural";
  }

  public String getPrimaryKeyColumnsTranslated() {
    return  I18NUtils.tradueix("categories.categoryid");
  }

  @InitBinder("categoriesFilterForm")
  public void initBinderFilterForm(WebDataBinder binder) {
    super.initBinder(binder);
  }

  @InitBinder("categoriesForm")
  public void initBinderForm(WebDataBinder binder) {
    super.initBinder(binder);

    binder.setValidator(getWebValidator());

    binder.setDisallowedFields("categoryid");

  }

  public CategoriesWebValidator getWebValidator() {
    return categoriesWebValidator;
  }


  public void setWebValidator(CategoriesWebValidator __val) {
    if (__val != null) {
      this.categoriesWebValidator= __val;
    }
  }


  /**
   * Entra aqui al pitjar el boto cancel en el llistat de Categories
   */
  @RequestMapping(value = "/{categoryid}/cancel")
  public String cancelCategories(@PathVariable("categoryid") java.lang.Long categoryid,
      HttpServletRequest request,HttpServletResponse response) {
     return getRedirectWhenCancel(request, categoryid);
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


  public void preValidate(HttpServletRequest request,CategoriesForm categoriesForm , BindingResult result)  throws I18NException {
  }

  public void postValidate(HttpServletRequest request,CategoriesForm categoriesForm, BindingResult result)  throws I18NException {
  }

  public void preList(HttpServletRequest request, ModelAndView mav, CategoriesFilterForm filterForm)  throws I18NException {
  }

  public void postList(HttpServletRequest request, ModelAndView mav, CategoriesFilterForm filterForm,  List<Categories> list) throws I18NException {
  }

  public String getRedirectWhenCreated(HttpServletRequest request, CategoriesForm categoriesForm) {
    return "redirect:" + getContextWeb() + "/list/1";
  }

  public String getRedirectWhenModified(HttpServletRequest request, CategoriesForm categoriesForm, Throwable __e) {
    if (__e == null) {
      return "redirect:" + getContextWeb() + "/list";
    } else {
      return  getTileForm();
    }
  }

  public String getRedirectWhenDelete(HttpServletRequest request, java.lang.Long categoryid, Throwable __e) {
    return "redirect:" + getContextWeb() + "/list";
  }

  public String getRedirectWhenCancel(HttpServletRequest request, java.lang.Long categoryid) {
    return "redirect:" + getContextWeb() + "/list";
  }

  public String getTileForm() {
    return "categoriesFormWebDB";
  }

  public String getTileList() {
    return "categoriesListWebDB";
  }

  @Override
  /** Ha de ser igual que el RequestMapping de la Classe */
  public String getContextWeb() {
    RequestMapping rm = AnnotationUtils.findAnnotation(this.getClass(), RequestMapping.class);
    return rm.value()[0];
  }

  public String getSessionAttributeFilterForm() {
    return "CategoriesWebDB_FilterForm";
  }



  public Where getAdditionalCondition(HttpServletRequest request) throws I18NException {
    return null;
  }


  public CategoriesJPA findByPrimaryKey(HttpServletRequest request, java.lang.Long categoryid) throws I18NException {
    return (CategoriesJPA) categoriesEjb.findByPrimaryKey(categoryid);
  }


  public CategoriesJPA create(HttpServletRequest request, CategoriesJPA categories)
    throws Exception,I18NException, I18NValidationException {
    return (CategoriesJPA) categoriesEjb.create(categories);
  }


  public CategoriesJPA update(HttpServletRequest request, CategoriesJPA categories)
    throws Exception,I18NException, I18NValidationException {
    return (CategoriesJPA) categoriesEjb.update(categories);
  }


  public void delete(HttpServletRequest request, Categories categories) throws Exception,I18NException {
    categoriesEjb.delete(categories);
  }

} // Final de Classe

