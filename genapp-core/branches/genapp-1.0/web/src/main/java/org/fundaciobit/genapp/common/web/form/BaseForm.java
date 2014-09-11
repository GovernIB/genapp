package org.fundaciobit.genapp.common.web.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fundaciobit.genapp.common.query.Field;

/**
 * 
 * @author anadal
 * 
 */
public abstract class BaseForm extends CommonBaseForm {

  /**
   * Conte la llista de javaName de Field que volem que siguin només de lectura
   */
  private List<Field<?>> readOnlyFields = new ArrayList<Field<?>>();

  private boolean cancelButtonVisible = true;

  private boolean saveButtonVisible = true;
  
  private Map<Field<?>, String> help = new HashMap<Field<?>, String>();
  
  private boolean view=false;

  public BaseForm() {
  }

  public BaseForm(boolean nou) {
    super(nou);
  }

  public BaseForm(BaseForm toClone) {
    super(toClone);

    if (toClone == null) {
      return;
    }

    this.readOnlyFields = toClone.getReadOnlyFields();
    this.cancelButtonVisible = toClone.cancelButtonVisible;
    this.saveButtonVisible = toClone.saveButtonVisible;
    this.help = toClone.help;
    this.view = toClone.view;
  }

  public List<Field<?>> getReadOnlyFields() {
    return readOnlyFields;
  }

  public void setReadOnlyFields(List<Field<?>> readOnlyFields) {
    this.readOnlyFields = readOnlyFields;
  }

  public void addReadOnlyField(Field<?> field) {
    if (field != null) {
      this.readOnlyFields.add(field);
    }
  }
  
  public boolean isReadOnlyField(String javaName) {
    return this.readOnlyFields.contains(javaName);
  }
  
  public void setAllFieldsReadOnly(Field<?> ... fieldToSetReadOnly) {
    if (fieldToSetReadOnly == null || fieldToSetReadOnly.length == 0) {
      return;
    }
    for (Field<?> field : fieldToSetReadOnly) {
      addReadOnlyField(field);
    }
  }

  public boolean isCancelButtonVisible() {
    return cancelButtonVisible;
  }

  public void setCancelButtonVisible(boolean cancelButtonVisible) {
    this.cancelButtonVisible = cancelButtonVisible;
  }

  public boolean isSaveButtonVisible() {
    return saveButtonVisible;
  }

  public void setSaveButtonVisible(boolean saveButtonVisible) {
    this.saveButtonVisible = saveButtonVisible;
  }

  public Map<Field<?>, String> getHelp() {
    return help;
  }

  public void setHelp(Map<Field<?>, String> help) {
    this.help = help;
  }

  public void addHelpToField(Field<?> field, String message) {
    if (field != null && message != null) { 
      help.put(field, message);
    }
  }

  public boolean isView() {
    return view;
  }

  public void setView(boolean view) {
    this.view = view;
  }

  
 
}
