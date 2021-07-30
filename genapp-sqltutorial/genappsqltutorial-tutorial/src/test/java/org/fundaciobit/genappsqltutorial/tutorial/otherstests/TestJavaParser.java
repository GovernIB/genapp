package org.fundaciobit.genappsqltutorial.tutorial.otherstests;

import java.util.Map;
import java.util.Map.Entry;

import org.fundaciobit.genappsqltutorial.tutorial.utils.javaparser.JavaParser;

/**
 * 
 * @author anadal
 *
 */
public class TestJavaParser {

    public static void main(String[] args) {

        // "SQL01Select"; //

        String fullClassName = "org.fundaciobit.genappsqltutorial.tutorial.SQL01Select";

        Map<String, String> bodymethodsByName = JavaParser.getMethods(fullClassName);

        for (Entry<String, String> method : bodymethodsByName.entrySet()) {
            System.out.println(" =========== " + method.getKey() + " ============");
            System.out.println(method.getValue());
        }

    }

}
