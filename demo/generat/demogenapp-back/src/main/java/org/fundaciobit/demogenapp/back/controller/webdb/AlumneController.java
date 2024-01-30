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
import org.fundaciobit.demogenapp.back.form.webdb.AlumneForm;

import org.fundaciobit.demogenapp.back.validator.webdb.AlumneWebValidator;

import org.fundaciobit.demogenapp.model.entity.Fitxer;
import org.fundaciobit.demogenapp.persistence.FitxerJPA;
import org.fundaciobit.genapp.common.web.controller.FilesFormManager;
import org.fundaciobit.demogenapp.persistence.AlumneJPA;
import org.fundaciobit.demogenapp.model.entity.Alumne;
import org.fundaciobit.demogenapp.model.fields.*;

/**
 * Controller per gestionar un Alumne
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * 
 * @author GenApp
 */
@Controller
@RequestMapping(value = "/webdb/alumne")
@SessionAttributes(types = { AlumneForm.class, AlumneFilterForm.class })
public class AlumneController
    extends org.fundaciobit.demogenapp.back.controller.DemoGenAppFilesBaseController<Alumne, java.lang.Long, AlumneForm> implements AlumneFields {

  @EJB(mappedName = org.fundaciobit.demogenapp.ejb.IdiomaService.JNDI_NAME)
  protected org.fundaciobit.demogenapp.ejb.IdiomaService idiomaEjb;

  @EJB(mappedName = org.fundaciobit.demogenapp.ejb.AlumneService.JNDI_NAME)
  protected org.fundaciobit.demogenapp.ejb.AlumneService alumneEjb;

  @Autowired
  private AlumneWebValidator alumneWebValidator;

  @Autowired
  protected AlumneRefList alumneRefList;

  // References 
  @Autowired
  protected IdiomaRefList idiomaRefList;

  // References 
  @Autowired
  protected TraduccioRefList traduccioRefList;

  /**
   * Llistat de totes Alumne
   */
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public String llistat(HttpServletRequest request,
    HttpServletResponse response) throws I18NException {
    AlumneFilterForm ff;
    ff = (AlumneFilterForm) request.getSession().getAttribute(getSessionAttributeFilterForm());
    int pagina = (ff == null)? 1: ff.getPage();
    return "redirect:" + getContextWeb() + "/list/" + pagina;
  }

  /**
   * Primera peticio per llistar Alumne de forma paginada
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
    llistat(mav, request, getAlumneFilterForm(pagina, mav, request));
    return mav;
  }

  public AlumneFilterForm getAlumneFilterForm(Integer pagina, ModelAndView mav,
    HttpServletRequest request) throws I18NException {
    AlumneFilterForm alumneFilterForm;
    alumneFilterForm = (AlumneFilterForm) request.getSession().getAttribute(getSessionAttributeFilterForm());
    if(alumneFilterForm == null) {
      alumneFilterForm = new AlumneFilterForm();
      alumneFilterForm.setContexte(getContextWeb());
      alumneFilterForm.setEntityNameCode(getEntityNameCode());
      alumneFilterForm.setEntityNameCodePlural(getEntityNameCodePlural());
      alumneFilterForm.setNou(true);
    } else {
      alumneFilterForm.setNou(false);
    }
    alumneFilterForm.setPage(pagina == null ? 1 : pagina);
    return alumneFilterForm;
  }

  /**
   * Segona i següent peticions per llistar Alumne de forma paginada
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
      @ModelAttribute AlumneFilterForm filterForm) throws I18NException {
    if(!isActiveList()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }

    ModelAndView mav = new ModelAndView(getTileList());

    filterForm.setPage(pagina == null ? 1 : pagina);
    // Actualitza el filter form

    request.getSession().setAttribute(getSessionAttributeFilterForm(), filterForm);
    filterForm = getAlumneFilterForm(pagina, mav, request);

    llistat(mav, request, filterForm);
    return mav;
  }

  /**
   * Codi centralitzat de llistat de Alumne de forma paginada.
   * 
   * @param request
   * @param filterForm
   * @param pagina
   * @return
   * @throws I18NException
   */
  protected List<Alumne> llistat(ModelAndView mav, HttpServletRequest request,
     AlumneFilterForm filterForm) throws I18NException {

    int pagina = filterForm.getPage();
    request.getSession().setAttribute(getSessionAttributeFilterForm(), filterForm);

    captureSearchByValueOfAdditionalFields(request, filterForm);

    preList(request, mav, filterForm);

    List<Alumne> alumne = processarLlistat(alumneEjb,
        filterForm, pagina, getAdditionalCondition(request), mav);

    mav.addObject("alumneItems", alumne);

    mav.addObject("alumneFilterForm", filterForm);

    fillReferencesForList(filterForm,request, mav, alumne, (List<GroupByItem>)mav.getModel().get("groupby_items"));

    postList(request, mav, filterForm, alumne);

    return alumne;
  }


  public Map<Field<?>, GroupByItem> fillReferencesForList(AlumneFilterForm filterForm,
    HttpServletRequest request, ModelAndView mav,
      List<Alumne> list, List<GroupByItem> groupItems) throws I18NException {
    Map<Field<?>, GroupByItem> groupByItemsMap = new HashMap<Field<?>, GroupByItem>();
    for (GroupByItem groupByItem : groupItems) {
      groupByItemsMap.put(groupByItem.getField(),groupByItem);
    }

    Map<String, String> _tmp;
    List<StringKeyValue> _listSKV;

    // Field idiomaID
    {
      _listSKV = getReferenceListForIdiomaID(request, mav, filterForm, list, groupByItemsMap, null);
      _tmp = Utils.listToMap(_listSKV);
      filterForm.setMapOfIdiomaForIdiomaID(_tmp);
      if (filterForm.getGroupByFields().contains(IDIOMAID)) {
        fillValuesToGroupByItems(_tmp, groupByItemsMap, IDIOMAID, false);
      };
    }

    // Field sexe
    {
      _listSKV = getReferenceListForSexe(request, mav, filterForm, list, groupByItemsMap, null);
      _tmp = Utils.listToMap(_listSKV);
      filterForm.setMapOfValuesForSexe(_tmp);
      if (filterForm.getGroupByFields().contains(SEXE)) {
        fillValuesToGroupByItems(_tmp, groupByItemsMap, SEXE, false);
      };
    }


      fillValuesToGroupByItemsBoolean("genapp.checkbox", groupByItemsMap, ACTIU);

    // Field titolAcademicID
    {
      _listSKV = getReferenceListForTitolAcademicID(request, mav, filterForm, list, groupByItemsMap, null);
      _tmp = Utils.listToMap(_listSKV);
      filterForm.setMapOfTraduccioForTitolAcademicID(_tmp);
      if (filterForm.getGroupByFields().contains(TITOLACADEMICID)) {
        fillValuesToGroupByItems(_tmp, groupByItemsMap, TITOLACADEMICID, false);
      };
    }


    return groupByItemsMap;
  }

  @RequestMapping(value = "/export/{dataExporterID}", method = RequestMethod.POST)
  public void exportList(@PathVariable("dataExporterID") String dataExporterID,
    HttpServletRequest request, HttpServletResponse response,
    AlumneFilterForm filterForm) throws Exception, I18NException {

    ModelAndView mav = new ModelAndView(getTileList());
    List<Alumne> list = llistat(mav, request, filterForm);
    Field<?>[] allFields = ALL_ALUMNE_FIELDS;

    java.util.Map<Field<?>, java.util.Map<String, String>> __mapping;
    __mapping = new java.util.HashMap<Field<?>, java.util.Map<String, String>>();
    __mapping.put(IDIOMAID, filterForm.getMapOfIdiomaForIdiomaID());
    __mapping.put(SEXE, filterForm.getMapOfValuesForSexe());
    __mapping.put(TITOLACADEMICID, filterForm.getMapOfTraduccioForTitolAcademicID());
    exportData(request, response, dataExporterID, filterForm,
          list, allFields, __mapping, PRIMARYKEY_FIELDS);
  }



  /**
   * Carregar el formulari per un nou Alumne
   */
  @RequestMapping(value = "/new", method = RequestMethod.GET)
  public ModelAndView crearAlumneGet(HttpServletRequest request,
      HttpServletResponse response) throws I18NException {

    if(!isActiveFormNew()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }
    ModelAndView mav = new ModelAndView(getTileForm());
    AlumneForm alumneForm = getAlumneForm(null, false, request, mav);
    
    if (alumneForm.getAlumne().getTitolAcademic() == null){
      org.fundaciobit.demogenapp.persistence.TraduccioJPA trad = new org.fundaciobit.demogenapp.persistence.TraduccioJPA();
      for (org.fundaciobit.demogenapp.model.entity.Idioma idioma : alumneForm.getIdiomesTraduccio()) {
        trad.addTraduccio(idioma.getIdiomaID(), new org.fundaciobit.demogenapp.persistence.TraduccioMapJPA());
      }
      alumneForm.getAlumne().setTitolAcademic(trad);
    }

    mav.addObject("alumneForm" ,alumneForm);
    fillReferencesForForm(alumneForm, request, mav);
  
    return mav;
  }
  
  /**
   * 
   * @return
   * @throws Exception
   */
  public AlumneForm getAlumneForm(AlumneJPA _jpa,
       boolean __isView, HttpServletRequest request, ModelAndView mav) throws I18NException {
    AlumneForm alumneForm;
    if(_jpa == null) {
      alumneForm = new AlumneForm(new AlumneJPA(), true);
    } else {
      alumneForm = new AlumneForm(_jpa, false);
      alumneForm.setView(__isView);
    }
    alumneForm.setContexte(getContextWeb());
    alumneForm.setEntityNameCode(getEntityNameCode());
    alumneForm.setEntityNameCodePlural(getEntityNameCodePlural());
    alumneForm.setIdiomesTraduccio(getIdiomesSuportats());
    return alumneForm;
  }

  public void fillReferencesForForm(AlumneForm alumneForm,
    HttpServletRequest request, ModelAndView mav) throws I18NException {
    // Comprovam si ja esta definida la llista
    if (alumneForm.getListOfIdiomaForIdiomaID() == null) {
      List<StringKeyValue> _listSKV = getReferenceListForIdiomaID(request, mav, alumneForm, null);

      if(_listSKV != null && !_listSKV.isEmpty()) { 
          java.util.Collections.sort(_listSKV, STRINGKEYVALUE_COMPARATOR);
      }
      alumneForm.setListOfIdiomaForIdiomaID(_listSKV);
    }
    // Comprovam si ja esta definida la llista
    if (alumneForm.getListOfValuesForSexe() == null) {
      List<StringKeyValue> _listSKV = getReferenceListForSexe(request, mav, alumneForm, null);

      if(_listSKV != null && !_listSKV.isEmpty()) { 
          java.util.Collections.sort(_listSKV, STRINGKEYVALUE_COMPARATOR);
      }
      alumneForm.setListOfValuesForSexe(_listSKV);
    }
    
  }


  public List<org.fundaciobit.demogenapp.model.entity.Idioma> getIdiomesSuportats() throws I18NException {
    List<org.fundaciobit.demogenapp.model.entity.Idioma> idiomes = idiomaEjb.select(org.fundaciobit.demogenapp.model.fields.IdiomaFields.SUPORTAT.equal(true));
    return idiomes;
  }


  /**
   * Guardar un nou Alumne
   */
  @RequestMapping(value = "/new", method = RequestMethod.POST)
  public String crearAlumnePost(@ModelAttribute AlumneForm alumneForm,
      BindingResult result, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    if(!isActiveFormNew()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }

    AlumneJPA alumne = alumneForm.getAlumne();

    FilesFormManager<Fitxer> afm = getFilesFormManager(); // FILE

    try {
      this.setFilesFormToEntity(afm, alumne, alumneForm); // FILE
      preValidate(request, alumneForm, result);
      getWebValidator().validate(alumneForm, result);
      postValidate(request,alumneForm, result);

      if (result.hasErrors()) {
        afm.processErrorFilesWithoutThrowException(); // FILE
        result.reject("error.form");
        return getTileForm();
      } else {
        alumne = create(request, alumne);
        afm.postPersistFiles(); // FILE
        createMessageSuccess(request, "success.creation", alumne.getAlumneID());
        alumneForm.setAlumne(alumne);
        return getRedirectWhenCreated(request, alumneForm);
      }
    } catch (Throwable __e) {
      afm.processErrorFilesWithoutThrowException(); // FILE
      if (__e instanceof I18NValidationException) {
        ValidationWebUtils.addFieldErrorsToBindingResult(result, (I18NValidationException)__e);
        return getTileForm();
      }
      String msg = createMessageError(request, "error.creation", null, __e);
      log.error(msg, __e);
      return getTileForm();
    }
  }

  @RequestMapping(value = "/view/{alumneID}", method = RequestMethod.GET)
  public ModelAndView veureAlumneGet(@PathVariable("alumneID") java.lang.Long alumneID,
      HttpServletRequest request,
      HttpServletResponse response) throws I18NException {
      return editAndViewAlumneGet(alumneID,
        request, response, true);
  }


  protected ModelAndView editAndViewAlumneGet(@PathVariable("alumneID") java.lang.Long alumneID,
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
    AlumneJPA alumne = findByPrimaryKey(request, alumneID);

    if (alumne == null) {
      createMessageWarning(request, "error.notfound", alumneID);
      new ModelAndView(new RedirectView(getRedirectWhenCancel(request, alumneID), true));
      return llistatPaginat(request, response, 1);
    } else {
      ModelAndView mav = new ModelAndView(getTileForm());
      AlumneForm alumneForm = getAlumneForm(alumne, __isView, request, mav);
      alumneForm.setView(__isView);
      if(__isView) {
        alumneForm.setAllFieldsReadOnly(ALL_ALUMNE_FIELDS);
        alumneForm.setSaveButtonVisible(false);
        alumneForm.setDeleteButtonVisible(false);
      }
      fillReferencesForForm(alumneForm, request, mav);
      mav.addObject("alumneForm", alumneForm);
      return mav;
    }
  }


  /**
   * Carregar el formulari per modificar un Alumne existent
   */
  @RequestMapping(value = "/{alumneID}/edit", method = RequestMethod.GET)
  public ModelAndView editarAlumneGet(@PathVariable("alumneID") java.lang.Long alumneID,
      HttpServletRequest request,
      HttpServletResponse response) throws I18NException {
      return editAndViewAlumneGet(alumneID,
        request, response, false);
  }



  /**
   * Editar un Alumne existent
   */
  @RequestMapping(value = "/{alumneID}/edit", method = RequestMethod.POST)
  public String editarAlumnePost(@ModelAttribute AlumneForm alumneForm,
      BindingResult result, SessionStatus status, HttpServletRequest request,
      HttpServletResponse response) throws I18NException {

    if(!isActiveFormEdit()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }
    AlumneJPA alumne = alumneForm.getAlumne();

    FilesFormManager<Fitxer> afm = getFilesFormManager(); // FILE
    try {
      this.setFilesFormToEntity(afm, alumne, alumneForm); // FILE
      preValidate(request, alumneForm, result);
      getWebValidator().validate(alumneForm, result);
      postValidate(request, alumneForm, result);

      if (result.hasErrors()) {
        afm.processErrorFilesWithoutThrowException(); // FILE
        result.reject("error.form");
        return getTileForm();
      } else {
        alumne = update(request, alumne);
        afm.postPersistFiles(); // FILE
        createMessageSuccess(request, "success.modification", alumne.getAlumneID());
        status.setComplete();
        return getRedirectWhenModified(request, alumneForm, null);
      }
    } catch (Throwable __e) {
      afm.processErrorFilesWithoutThrowException(); // FILE
      if (__e instanceof I18NValidationException) {
        ValidationWebUtils.addFieldErrorsToBindingResult(result, (I18NValidationException)__e);
        return getTileForm();
      }
      String msg = createMessageError(request, "error.modification",
          alumne.getAlumneID(), __e);
      log.error(msg, __e);
      return getRedirectWhenModified(request, alumneForm, __e);
    }

  }


  /**
   * Eliminar un Alumne existent
   */
  @RequestMapping(value = "/{alumneID}/delete")
  public String eliminarAlumne(@PathVariable("alumneID") java.lang.Long alumneID,
      HttpServletRequest request,HttpServletResponse response) {

    if(!isActiveDelete()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }
    try {
      Alumne alumne = this.findByPrimaryKey(request, alumneID);
      if (alumne == null) {
        String __msg = createMessageError(request, "error.notfound", alumneID);
        return getRedirectWhenDelete(request, alumneID, new Exception(__msg));
      } else {
        delete(request, alumne);
        createMessageSuccess(request, "success.deleted", alumneID);
        return getRedirectWhenDelete(request, alumneID,null);
      }

    } catch (Throwable e) {
      String msg = createMessageError(request, "error.deleting", alumneID, e);
      log.error(msg, e);
      return getRedirectWhenDelete(request, alumneID, e);
    }
  }


@RequestMapping(value = "/deleteSelected", method = RequestMethod.POST)
public String deleteSelected(HttpServletRequest request,
    HttpServletResponse response,
    @ModelAttribute AlumneFilterForm filterForm) throws Exception {

  if(!isActiveDelete()) {
    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    return null;
  }
  
  String[] seleccionats = filterForm.getSelectedItems();
  String redirect = null;
  if (seleccionats != null && seleccionats.length != 0) {
    for (int i = 0; i < seleccionats.length; i++) {
      redirect = eliminarAlumne(stringToPK(seleccionats[i]), request, response);
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
  public String[] getArgumentsMissatge(Object __alumneID, Throwable e) {
    java.lang.Long alumneID = (java.lang.Long)__alumneID;
    String exceptionMsg = "";
    if (e != null) {
      if (e instanceof I18NException) {
        exceptionMsg = I18NUtils.getMessage((I18NException)e);
      } else if (e instanceof I18NValidationException) {
      } else {
        exceptionMsg = e.getMessage();
      };
    };
    if (alumneID == null) {
      return new String[] { I18NUtils.tradueix(getEntityNameCode()),
         getPrimaryKeyColumnsTranslated(), null, exceptionMsg };
    } else {
      return new String[] { I18NUtils.tradueix(getEntityNameCode()),
        getPrimaryKeyColumnsTranslated(),
         String.valueOf(alumneID),
 exceptionMsg };
    }
  }

  public String getEntityNameCode() {
    return "alumne.alumne";
  }

  public String getEntityNameCodePlural() {
    return "alumne.alumne.plural";
  }

  public String getPrimaryKeyColumnsTranslated() {
    return  I18NUtils.tradueix("alumne.alumneID");
  }

  @InitBinder("alumneFilterForm")
  public void initBinderFilterForm(WebDataBinder binder) {
    super.initBinder(binder);
  }

  @InitBinder("alumneForm")
  public void initBinderForm(WebDataBinder binder) {
    super.initBinder(binder);

    binder.setValidator(getWebValidator());


    initDisallowedFields(binder, "alumne.alumneID");
  }

  public AlumneWebValidator getWebValidator() {
    return alumneWebValidator;
  }


  public void setWebValidator(AlumneWebValidator __val) {
    if (__val != null) {
      this.alumneWebValidator= __val;
    }
  }


  /**
   * Entra aqui al pitjar el boto cancel en el llistat de Alumne
   */
  @RequestMapping(value = "/{alumneID}/cancel")
  public String cancelAlumne(@PathVariable("alumneID") java.lang.Long alumneID,
      HttpServletRequest request,HttpServletResponse response) {
     return getRedirectWhenCancel(request, alumneID);
  }

  @Override
  public String getTableModelName() {
    return _TABLE_MODEL;
  }

  // FILE
  @Override
  public void setFilesFormToEntity(FilesFormManager<Fitxer> afm, Alumne alumne,
      AlumneForm form) throws I18NException {

    FitxerJPA f;
    f = (FitxerJPA)afm.preProcessFile(form.getFotoID(), form.isFotoIDDelete(),
        form.isNou()? null : alumne.getFoto());
    ((AlumneJPA)alumne).setFoto(f);
    if (f != null) { 
      alumne.setFotoID(f.getFitxerID());
    } else {
      alumne.setFotoID(null);
    }


  }

  // FILE
  @Override
  public void deleteFiles(Alumne alumne) {
    deleteFile(alumne.getFotoID());
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


  public List<StringKeyValue> getReferenceListForIdiomaID(HttpServletRequest request,
       ModelAndView mav, AlumneForm alumneForm, Where where)  throws I18NException {
    if (alumneForm.isHiddenField(IDIOMAID)) {
      return EMPTY_STRINGKEYVALUE_LIST;
    }
    Where _where = null;
    if (alumneForm.isReadOnlyField(IDIOMAID)) {
      _where = IdiomaFields.IDIOMAID.equal(alumneForm.getAlumne().getIdiomaID());
    }
    return getReferenceListForIdiomaID(request, mav, Where.AND(where, _where));
  }


  public List<StringKeyValue> getReferenceListForIdiomaID(HttpServletRequest request,
       ModelAndView mav, AlumneFilterForm alumneFilterForm,
       List<Alumne> list, Map<Field<?>, GroupByItem> _groupByItemsMap, Where where)  throws I18NException {
    if (alumneFilterForm.isHiddenField(IDIOMAID)
       && !alumneFilterForm.isGroupByField(IDIOMAID)) {
      return EMPTY_STRINGKEYVALUE_LIST;
    }
    Where _w = null;
    if (!_groupByItemsMap.containsKey(IDIOMAID)) {
      // OBTENIR TOTES LES CLAUS (PK) i despres només cercar referències d'aquestes PK
      java.util.Set<java.lang.String> _pkList = new java.util.HashSet<java.lang.String>();
      for (Alumne _item : list) {
        _pkList.add(_item.getIdiomaID());
        }
        _w = IdiomaFields.IDIOMAID.in(_pkList);
      }
    return getReferenceListForIdiomaID(request, mav, Where.AND(where,_w));
  }


  public List<StringKeyValue> getReferenceListForIdiomaID(HttpServletRequest request,
       ModelAndView mav, Where where)  throws I18NException {
    return idiomaRefList.getReferenceList(IdiomaFields.IDIOMAID, where );
  }


  public List<StringKeyValue> getReferenceListForSexe(HttpServletRequest request,
       ModelAndView mav, AlumneForm alumneForm, Where where)  throws I18NException {
    if (alumneForm.isHiddenField(SEXE)) {
      return EMPTY_STRINGKEYVALUE_LIST;
    }
    return getReferenceListForSexe(request, mav, where);
  }


  public List<StringKeyValue> getReferenceListForSexe(HttpServletRequest request,
       ModelAndView mav, AlumneFilterForm alumneFilterForm,
       List<Alumne> list, Map<Field<?>, GroupByItem> _groupByItemsMap, Where where)  throws I18NException {
    if (alumneFilterForm.isHiddenField(SEXE)
       && !alumneFilterForm.isGroupByField(SEXE)
       && !alumneFilterForm.isFilterByField(SEXE)) {
      return EMPTY_STRINGKEYVALUE_LIST;
    }
    Where _w = null;
    return getReferenceListForSexe(request, mav, Where.AND(where,_w));
  }


  public List<StringKeyValue> getReferenceListForSexe(HttpServletRequest request,
       ModelAndView mav, Where where)  throws I18NException {
    List<StringKeyValue> __tmp = new java.util.ArrayList<StringKeyValue>();
    __tmp.add(new StringKeyValue("" , I18NUtils.tradueix("genapp.checkbox.")));
    __tmp.add(new StringKeyValue("false" , I18NUtils.tradueix("genapp.checkbox.false")));
    __tmp.add(new StringKeyValue("true" , I18NUtils.tradueix("genapp.checkbox.true")));
    return __tmp;
  }

  public List<StringKeyValue> getReferenceListForTitolAcademicID(HttpServletRequest request,
       ModelAndView mav, AlumneFilterForm alumneFilterForm,
       List<Alumne> list, Map<Field<?>, GroupByItem> _groupByItemsMap, Where where)  throws I18NException {
    if (alumneFilterForm.isHiddenField(TITOLACADEMICID)
       && !alumneFilterForm.isGroupByField(TITOLACADEMICID)) {
      return EMPTY_STRINGKEYVALUE_LIST;
    }
    Where _w = null;
    if (!_groupByItemsMap.containsKey(TITOLACADEMICID)) {
      // OBTENIR TOTES LES CLAUS (PK) i despres només cercar referències d'aquestes PK
      java.util.Set<java.lang.Long> _pkList = new java.util.HashSet<java.lang.Long>();
      for (Alumne _item : list) {
        if(_item.getTitolAcademicID() == null) { continue; };
        _pkList.add(_item.getTitolAcademicID());
        }
        _w = TraduccioFields.TRADUCCIOID.in(_pkList);
      }
    return getReferenceListForTitolAcademicID(request, mav, Where.AND(where,_w));
  }


  public List<StringKeyValue> getReferenceListForTitolAcademicID(HttpServletRequest request,
       ModelAndView mav, Where where)  throws I18NException {
    return traduccioRefList.getReferenceList(TraduccioFields.TRADUCCIOID, where );
  }


  @Override
  /** Ha de ser igual que el RequestMapping de la Classe */
  public String getContextWeb() {
    RequestMapping rm = AnnotationUtils.findAnnotation(this.getClass(), RequestMapping.class);
    return rm.value()[0];
  }

  public void preValidate(HttpServletRequest request,AlumneForm alumneForm , BindingResult result)  throws I18NException {
  }

  public void postValidate(HttpServletRequest request,AlumneForm alumneForm, BindingResult result)  throws I18NException {
  }

  public void preList(HttpServletRequest request, ModelAndView mav, AlumneFilterForm filterForm)  throws I18NException {
  }

  public void postList(HttpServletRequest request, ModelAndView mav, AlumneFilterForm filterForm,  List<Alumne> list) throws I18NException {
  }

  public String getRedirectWhenCreated(HttpServletRequest request, AlumneForm alumneForm) {
    return "redirect:" + getContextWeb() + "/list/1";
  }

  public String getRedirectWhenModified(HttpServletRequest request, AlumneForm alumneForm, Throwable __e) {
    if (__e == null) {
      return "redirect:" + getContextWeb() + "/list";
    } else {
      return  getTileForm();
    }
  }

  public String getRedirectWhenDelete(HttpServletRequest request, java.lang.Long alumneID, Throwable __e) {
    return "redirect:" + getContextWeb() + "/list";
  }

  public String getRedirectWhenCancel(HttpServletRequest request, java.lang.Long alumneID) {
    return "redirect:" + getContextWeb() + "/list";
  }

  public String getTileForm() {
    return "alumneFormWebDB";
  }

  public String getTileList() {
    return "alumneListWebDB";
  }

  public String getSessionAttributeFilterForm() {
    return "Alumne_FilterForm_" + this.getClass().getName();
  }



  public Where getAdditionalCondition(HttpServletRequest request) throws I18NException {
    return null;
  }


  public AlumneJPA findByPrimaryKey(HttpServletRequest request, java.lang.Long alumneID) throws I18NException {
    return (AlumneJPA) alumneEjb.findByPrimaryKey(alumneID);
  }


  public AlumneJPA create(HttpServletRequest request, AlumneJPA alumne)
    throws I18NException, I18NValidationException {
    return (AlumneJPA) alumneEjb.create(alumne);
  }


  public AlumneJPA update(HttpServletRequest request, AlumneJPA alumne)
    throws I18NException, I18NValidationException {
    return (AlumneJPA) alumneEjb.update(alumne);
  }


  public void delete(HttpServletRequest request, Alumne alumne) throws I18NException {
    alumneEjb.delete(alumne);
  }

} // Final de Classe

