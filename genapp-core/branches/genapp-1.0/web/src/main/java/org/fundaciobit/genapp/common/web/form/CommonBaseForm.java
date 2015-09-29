package org.fundaciobit.genapp.common.web.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.fundaciobit.genapp.common.query.Field;

/**
 * 
 * @author anadal
 * 
 */
public class CommonBaseForm {

  protected final Logger log = Logger.getLogger(getClass());

  private boolean nou;

  private String contexte;

  private String entityNameCode;

  private String entityNameCodePlural;
  
  private Object additionalObject;

  /**
   * Conté nous codis de traducció perls noms dels camps:
   *      + codi de traducció en el nom dels camps en formularis
   *      + codi de traducció en el nom de les columnes en llistats
   */
  private Map<Field<?>, String> labels = new HashMap<Field<?>, String>();

  /**
   * Conte la llista de javaName de Field que no volem que es mostrin
   */
  private Set<Field<?>> hiddenFields = new HashSet<Field<?>>();

  private TreeMap<Integer, AdditionalField<?,?>> additionalFields = new TreeMap<Integer, AdditionalField<?,?>>();

  /**
   * Títol del formulari o llistat. Si val null s'usa en entityNameCode del
   * Controlador
   */
  private String titleCode;
  
  /**
   * Paràmetre del títol
   */
  private String titleParam;
  

  /**
   * Subtítol del formulari o llistat. Si val null no apareix.
   */
  private String subTitleCode;

  /**
   * Mostra o oculta el boto de borrar: + En form, durant l'edició el boto del
   * cantó dret inferior + En list, en cada element de la llista
   */
  private boolean deleteButtonVisible = true;

  /**
   * Conté la llista de botons adicionals que es poden mostrar en la 
   * part d'abaix d'un form o en la part superior d'un llistat
   */
  private List<AdditionalButton> additionalButtons = new ArrayList<AdditionalButton>();
  
  
  private boolean attachedAdditionalJspCode = false;
  

  public CommonBaseForm() {
    super();
  }

  public CommonBaseForm(CommonBaseForm toClone) {

    super();

    if (toClone == null) {
      return;
    }

    this.nou = toClone.nou;
    this.contexte = toClone.contexte;
    this.labels = toClone.labels;
    this.hiddenFields = toClone.hiddenFields;
    this.titleCode = toClone.titleCode;
    this.titleParam = toClone.titleParam;
    this.subTitleCode = toClone.subTitleCode;
    this.deleteButtonVisible = toClone.deleteButtonVisible;
    this.entityNameCode = toClone.entityNameCode;
    this.entityNameCodePlural = toClone.entityNameCodePlural;
    this.additionalButtons = toClone.additionalButtons;
    this.additionalFields = toClone.additionalFields;
    this.additionalObject = toClone.additionalObject;
    this.attachedAdditionalJspCode = toClone.attachedAdditionalJspCode;
    
  }

  /**
   * @param nou
   */
  public CommonBaseForm(boolean nou) {
    super();
    this.nou = nou;
  }

  public final String getStringOfField(Field<?> field) {
    if (field == null) {
      return null;
    }
    return field.javaName;
  }

  public Set<Field<?>> getHiddenFields() {
    return hiddenFields;
  }

  public void setHiddenFields(Set<Field<?>> hiddenFields) {
    this.hiddenFields = hiddenFields;
  }

  public void addHiddenField(Field<?> field) {
    if (field != null) {
      this.hiddenFields.add(field);
    }
  }

  public boolean isHiddenField(Field<?> field) {
    return this.hiddenFields.contains(field);
  }

  public String getContexte() {
    return contexte;
  }

  public void setContexte(String contexte) {
    this.contexte = contexte;
  }

  public String getTitleCode() {
    return titleCode;
  }

  public void setTitleCode(String titleCode) {
    this.titleCode = titleCode;
  }

  public String getTitleParam() {
    return titleParam;
  }

  public void setTitleParam(String titleParam) {
    this.titleParam = titleParam;
  }

  public String getSubTitleCode() {
    return subTitleCode;
  }

  public void setSubTitleCode(String subTitleCode) {
    this.subTitleCode = subTitleCode;
  }

  public List<AdditionalButton> getAdditionalButtons() {
    return additionalButtons;
  }

  public void setAdditionalButtons(List<AdditionalButton> additionalButtons) {
    this.additionalButtons = additionalButtons;
  }

  public void addAdditionalButton(AdditionalButton additionalButton) {

    if (additionalButton != null) {
      if (this.additionalButtons.contains(additionalButton)) {
        log.warn("Esta intentant afegir un camp AdditionalButton"
            + " a una llista que ja el conté !!!!", new Exception());
      }
      this.additionalButtons.add(additionalButton);
    }

  }

  public void setNou(boolean nou) {
    this.nou = nou;
  }

  public boolean isNou() {
    return nou;
  }

  public boolean isDeleteButtonVisible() {
    return deleteButtonVisible;
  }

  public void setDeleteButtonVisible(boolean deleteButtonVisible) {
    this.deleteButtonVisible = deleteButtonVisible;
  }

  public Map<Field<?>, String> getLabels() {
    return labels;
  }

  public void setLabels(Map<Field<?>, String> labels) {
    this.labels = labels;
  }

  public void addLabel(Field<?> field, String labelCode) {
    if (field != null && labelCode != null) {
      this.labels.put(field, labelCode);
    }
  }

  public String getEntityNameCode() {
    return entityNameCode;
  }

  public void setEntityNameCode(String entityNameCode) {
    this.entityNameCode = entityNameCode;
  }

  public String getEntityNameCodePlural() {
    return entityNameCodePlural;
  }

  public void setEntityNameCodePlural(String entityNameCodePlural) {
    this.entityNameCodePlural = entityNameCodePlural;
  }

  public TreeMap<Integer, AdditionalField<?, ?>> getAdditionalFields() {
    return additionalFields;
  }

  public void setAdditionalFields(TreeMap<Integer, AdditionalField<?, ?>> additionalFields) {
    this.additionalFields = additionalFields;
  }
  
  public void addAdditionalField(AdditionalField<?, ?> additionalField) {
    this.additionalFields.put(additionalField.getPosition(), additionalField);
  }
  
  public AdditionalField<?, ?> getAdditionalField(int position) {
    return this.additionalFields.get(position);
  }

  public Object getAdditionalObject() {
    return additionalObject;
  }

  public void setAdditionalObject(Object additionalObject) {
    this.additionalObject = additionalObject;
  }

  public boolean isAttachedAdditionalJspCode() {
    return attachedAdditionalJspCode;
  }

  public void setAttachedAdditionalJspCode(boolean attachedAdditionalJspCode) {
    this.attachedAdditionalJspCode = attachedAdditionalJspCode;
  }

}
