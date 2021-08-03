package org.fundaciobit.genappsqltutorial.tutorial.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.fundaciobit.genappsqltutorial.tutorial.utils.annotations.AnnotationManager;
import org.fundaciobit.genappsqltutorial.tutorial.utils.translator.Translator;

/**
 * 
 * @author anadal
 *
 */
public class UnitsManager {

    private static Map<String, Map<String, UnitInfo> /* unitsByClassName */> unitsByLang = new HashMap<String, Map<String, UnitInfo>>();

    public static Collection<UnitInfo> getAllUnits(String language) {

        Map<String, UnitInfo> um = getUnitsByClassName(language);

        return um.values();
    }

    public static Map<String, UnitInfo> getUnitsByClassName(String language) {

        Map<String, UnitInfo> unitsByClassName = unitsByLang.get(language);

        if (unitsByClassName == null) {
            List<UnitInfo> units = AnnotationManager.getAllUnits(language);

            unitsByClassName = new TreeMap<String, UnitInfo>();

            for (UnitInfo unitInfo : units) {
                unitsByClassName.put(unitInfo.getClasseNom(), unitInfo);
            }

           
            unitsByLang.put(language, unitsByClassName);
        }

        return unitsByClassName;
    }

    public static UnitInfo getUnitByClassName(String classname, String language) {

        Map<String, UnitInfo> um = getUnitsByClassName(language);

        return um.get(classname);

    }

    public static UnitInfo getPartsOfUnit(String classname, String language) {

        UnitInfo u = getUnitByClassName(classname, language);

        if (u.getParts() == null) {
            
            List<PartInfo> parts = AnnotationManager.getPartsOfUnit(u.getCls());
            
            if (!language.equals("en")) {
                for (PartInfo p : parts) {
                    p.setTitol(Translator.translate(language, p.getTitol()));
                    p.setDescripcio(Translator.translate(language, p.getDescripcio()));
                }
            }
            u.setParts(parts);
        }

        return u;

    }

}
