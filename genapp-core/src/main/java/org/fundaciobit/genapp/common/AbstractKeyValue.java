package org.fundaciobit.genapp.common;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.Collator;
import java.util.Comparator;
/**
 * Title:        Rapit Entity Bean 2010
 * Description:
 * Copyright:    Copyright (c) 2010
 * Company:      XmasSoft
 * @author Xmas
 */
public class AbstractKeyValue<S, O> {
   public final S sortField;
   public final O object;
  /**
   * @param key
   * @param value
   */
  public AbstractKeyValue(S sortField, O object) {
    super();
    this.sortField = sortField;
    this.object = object;
  }

  
  public static class KeyValueComparator<S,O>
    implements Comparator<AbstractKeyValue<S,O>> {
    
    Comparator<S> comparator;

    public KeyValueComparator(Comparator<S> comparator) {
      this.comparator = comparator;
    }

    public int compare(AbstractKeyValue<S,O> o1, AbstractKeyValue<S,O> o2) {

      if (o1.sortField == null) {
        if (o2.sortField == null) {
          return 0;
        } else {
          return -1;
        }
      } else {
        if (o2.sortField == null) {
          return +1;
        } else {
          return comparator.compare(o1.sortField, o2.sortField);
        }
      }
      
    }
    
  }
  
  public static class StringComparator implements Comparator<String> {
    
    public static final Collator collator = Collator.getInstance();

    public int compare(String o1, String o2) {
      return collator.compare(o1, o2);
    }
    
  }
  
  
  public static class IntegerComparator implements Comparator<Integer> {
  
    public int compare(Integer o1, Integer o2) {
      return o1.compareTo(o2);
    }
    
  }
  
  public static class DateComparator implements Comparator<Date> {
    
    public int compare(Date o1, Date o2) {
      return o1.compareTo(o2);
    }
    
  }
  
  public static class TimestampComparator implements Comparator<Timestamp> {
    
    public int compare(Timestamp o1, Timestamp o2) {
      return o1.compareTo(o2);
    }
    
  }
   
}
