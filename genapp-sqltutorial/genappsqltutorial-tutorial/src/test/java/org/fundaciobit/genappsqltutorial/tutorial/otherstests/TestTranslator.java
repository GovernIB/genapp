package org.fundaciobit.genappsqltutorial.tutorial.otherstests;

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
        
        String t =  "The following SQL statement selects all (including the duplicates) values from the \"Country\" column in the \"Customers\" table:";

        System.out.println("EN:" + t);
        
        String word = Translator.translate("ca", t); 

        System.out.println("CA:" + word);
    }
}
