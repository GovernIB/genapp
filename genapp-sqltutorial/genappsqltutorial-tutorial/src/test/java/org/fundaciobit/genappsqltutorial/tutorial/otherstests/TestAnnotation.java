package org.fundaciobit.genappsqltutorial.tutorial.otherstests;


import java.util.List;

import org.fundaciobit.genappsqltutorial.tutorial.utils.PartInfo;
import org.fundaciobit.genappsqltutorial.tutorial.utils.UnitInfo;
import org.fundaciobit.genappsqltutorial.tutorial.utils.annotations.AnnotationManager;


/**
 * 
 * 
 * @author anadal
 *
 */

public class TestAnnotation {

    public static void main(String[] args) {
        
        String lang = "ca";

        List<UnitInfo> list = AnnotationManager.getAllUnits(lang);
        
        
        for (UnitInfo unitInfo : list) {
            System.out.println("   Unit " + unitInfo.getIndex() + "-" + unitInfo.getTitol() + " => DESC: " + unitInfo.getDescripcio() + " [Class => " + unitInfo.getClasse() + "]");
            
            List<PartInfo> parts = AnnotationManager.getPartsOfUnit(unitInfo.getCls());
            for (PartInfo p : parts) {
                System.out.println("============" + p.getMetode() + "============");
                System.out.println("T: " + p.getTitol());
                System.out.println("D: " + p.getDescripcio());
                System.out.println("S: " + p.getSql());
                System.out.println("C: " + p.getSourcecode());
            }
            
            
            break;
            
        }
        
        
        
        

    }

}
