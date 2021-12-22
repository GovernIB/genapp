package org.fundaciobit.genappsqltutorial.tutorial.otherstests;

import java.io.File;

import org.fundaciobit.genappsqltutorial.commons.utils.Constants;
import org.fundaciobit.genappsqltutorial.tutorial.utils.translator.Translator;

/**
 * 
 * @author anadal
 *
 */
public class TestTranslator {

    
    
    public static void main(String[] args) throws Exception {
        /**
         * <dependency> <groupId>org.json</groupId> <artifactId>json</artifactId>
         * <version>20090211</version> </dependency>
         */
        
         System.setProperty(Constants.GENAPPSQLTUTORIAL_PROPERTY_BASE + "translationsfile", new File("./traduccions.properties").getAbsolutePath());
        
        //String t =  "The following SQL statement selects all (including the duplicates) values from the \"Country\" column in the \"Customers\" table:";
        
        String t = "The LIKE operator is used in a WHERE clause to search for a specified pattern in a column."
                + "\n" + "There are two wildcards often used in conjunction with the LIKE operator:"
                + "\n" + "-The percent sign (%) represents zero, one, or multiple characters"
                + "\n" + "-The underscore sign (_) represents one, single character" + "\n"
                + "The percent sign and the underscore can also be used in combinations!";

        System.out.println("EN:" + t);
        
        String word = Translator.translate("ca", t); 

        System.out.println("CA:" + word);
    }
}
