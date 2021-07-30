package org.fundaciobit.genappsqltutorial.tutorial.utils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.fundaciobit.genappsqltutorial.tutorial.utils.annotations.AnnotationManager;

/**
 * 
 * @author anadal
 *
 */
public class UnitsManager {

    private static Map<String, UnitInfo> unitsByClassName = null;

    public static Collection<UnitInfo> getAllUnits() {

        Map<String, UnitInfo> um = getUnitsByClassName();

        return um.values();
    }

    public static Map<String, UnitInfo> getUnitsByClassName() {

        if (unitsByClassName == null) {
            List<UnitInfo> units = AnnotationManager.getAllUnits();

            Map<String, UnitInfo> um = new TreeMap<String, UnitInfo>();

            for (UnitInfo unitInfo : units) {
                um.put(unitInfo.getClasseNom(), unitInfo);
            }

            unitsByClassName = um;
        }

        return unitsByClassName;
    }

    public static UnitInfo getUnitByClassName(String classname) {

        Map<String, UnitInfo> um = getUnitsByClassName();

        return um.get(classname);

    }

    public static UnitInfo getPartsOfUnit(String classname) {
        
        
        UnitInfo u = getUnitByClassName(classname);

        if (u.getParts() == null) {
        
          u.setParts(AnnotationManager.getPartsOfUnit(u.getCls()));
        }

        return u;
        
    }

}
