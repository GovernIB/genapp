package org.fundaciobit.genapp.traductor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TraduccioBundleFileAppItem implements ITraduccioItem {

    final String type;

    final String key;

    Map<String, String> valuesByLang = new HashMap<String, String>();

    public TraduccioBundleFileAppItem(String type, String key) {
        super();
        this.type = type;
        this.key = key;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getStringValue(String lang) {
        return valuesByLang.get(lang);
    }

    @Override
    public void setStringValue(String lang, String newValue) {
        valuesByLang.put(lang, newValue);
    }

    @Override
    public Set<String> getLanguages() {
        return valuesByLang.keySet();
    }

}
