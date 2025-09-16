package org.fundaciobit.genapp.common;

/**
 * 
 * @author anadal
 * 
 */
public class StringKeyValue extends KeyValue<String> implements Comparable<StringKeyValue> {

    public StringKeyValue(String key, String value) {
        super(key, value);
    }

    @Override
    public int compareTo(StringKeyValue o2) {

        if (o2 == null) {
            return 1;
        }

        return this.getKey().compareTo(o2.getKey());
    }

}
