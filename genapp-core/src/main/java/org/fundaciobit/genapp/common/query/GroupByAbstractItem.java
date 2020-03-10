package org.fundaciobit.genapp.common.query;

public abstract class GroupByAbstractItem {

  
  private Field<?> field;
  
  private String codeLabel;
  
  private String value;
  
  private boolean selected;

  /**
   * @param label
   * @param value
   * @param selected
   */
  public GroupByAbstractItem(Field<?> field, String codeLabel, String value, boolean selected) {
    super();
    this.codeLabel = codeLabel;
    this.value = value;
    this.selected = selected;
    this.field = field;
  }

  public String getCodeLabel() {
    return codeLabel;
  }

  public void setCodeLabel(String codeLabel) {
    this.codeLabel = codeLabel;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public boolean isSelected() {
    return selected;
  }

  public void setSelected(boolean selected) {
    this.selected = selected;
  }

  public Field<?> getField() {
    return field;
  }

  public void setField(Field<?> field) {
    this.field = field;
  }

}
