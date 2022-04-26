package org.fundaciobit.genapp.common.query;

import java.util.Comparator;

/**
 * 
 * @author anadal
 *
 */
public class GroupByValueItem extends GroupByAbstractItem {

  private Long count;

  /**
   * @param label
   * @param value
   * @param selected
   * @param count
   */
  public GroupByValueItem(Field<?> field,String codeLabel, String value, boolean selected, Long count) {
    super(field, codeLabel, value, selected);
    this.count = count;
  }

  public Long getCount() {
    return count;
  }

  public void setCount(Long count) {
    this.count = count;
  }
  
  
  public static final Comparator<GroupByValueItem> GROUPBYVALUEITEMCOMPARATOR = new Comparator<GroupByValueItem>() {
    @Override
    public int compare(GroupByValueItem o1, GroupByValueItem o2) {
      //System.out.println(" Compare o1: " + o1 + " ( " + o1.getCodeLabel() + ")");
      //System.out.println(" Compare o2: " + o2 + " ( " + o2.getCodeLabel() + ")");
      if (o1.getCodeLabel() == null) {
        if (o2.getCodeLabel() == null) {
          return 0;
        } else {
          return -1;
        }
      } else {
        if (o2.getCodeLabel() == null) {
          return +1;
        } else {
          return  o1.getCodeLabel().compareTo(o2.getCodeLabel());
        }
      }
    }

  };
  
  
  @Override
    public String toString() {
     
        return "[" + this.getField().fullName + "\t" + this.getCodeLabel() + "\t" + this.getValue() + "\t" + this.getCount() + "]";
    }

}
