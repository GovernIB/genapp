package org.fundaciobit.genapp.common.query;

import java.util.List;

/**
 * 
 * @author anadal
 *
 */
public class GroupByItem  extends GroupByAbstractItem {

  
  private String codeParamLabel;

  private List<GroupByValueItem> values;

  

  /**
   * @param label
   * @param value
   * @param selected
   * @param values
   */
  public GroupByItem(Field<?> field, String label, String value, boolean selected,
      List<GroupByValueItem> values) {
    this(field, label, value, selected,values, null);
  }
  
  public GroupByItem(Field<?> field, String label, String value, boolean selected,
      List<GroupByValueItem> values, String codeParamLabel) {
    super(field, label, value, selected);
    this.values = values;
    this.codeParamLabel = codeParamLabel;
  }
  
  
  public List<GroupByValueItem> getValues() {
    return values;
  }

  public void setValues(List<GroupByValueItem> values) {
    this.values = values;
  }

  public String getCodeParamLabel() {
    return codeParamLabel;
  }

  public void setCodeParamLabel(String codeParamLabel) {
    this.codeParamLabel = codeParamLabel;
  }
  
  
  
  
}
