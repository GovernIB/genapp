package org.fundaciobit.genapp.common;

import java.text.Collator;
import java.util.Comparator;

/**
 * Title: Rapit Entity Bean 2010 Description: Copyright: Copyright (c) 2010
 * Company: XmasSoft
 * 
 * @author Xmas
 * @version 1.0
 */
public class KeyValue<K> {
  public K key;
  public String value;

  public KeyValue() {
  }

  /**
   * @param key
   * @param value
   */
  public KeyValue(K key, String value) {
    super();
    this.key = key;
    this.value = value;
  }

  public K getKey() {
    return key;
  }

  public void setKey(K key) {
    this.key = key;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  /**
   * 
   * @author anadal
   * 
   * @param <T>
   */
  public static class KeyValueComparator<T> implements Comparator<KeyValue<T>> {

    Collator col;

    public KeyValueComparator() {
      col = Collator.getInstance();
      col.setStrength(Collator.PRIMARY);
    }

    public int compare(KeyValue<T> o1, KeyValue<T> o2) {

      if (o1.value == null) {
        if (o2.value == null) {
          return 0;
        } else {
          return -1;
        }
      } else {
        if (o2.value == null) {
          return +1;
        } else {
          return col.compare(o1.value, o2.value);
        }
      }

    }

  }

}
