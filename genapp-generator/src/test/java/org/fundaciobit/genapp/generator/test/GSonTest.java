package org.fundaciobit.genapp.generator.test;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;

import org.fundaciobit.genapp.Project;
import org.fundaciobit.genapp.generator.gui.RebApp;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;


/**
 * 
 * @author anadal
 *
 */
public class GSonTest {

    public static void main(String[] args) {

        try {

            File f = new File(
                    "D:\\dades\\dades\\CarpetesPersonals\\ProgramacioPortaFIB3\\enviafib\\rungenapp\\enviafib.genapp");

            Project project = RebApp.readProjectFromFile(f);

            Gson gson = RebApp.instantiateGSon();

            String dest;
            {
                dest = gson.toJson(project);
                File json1 = new File(f.getParentFile(), f.getName() + "json");
                FileWriter gdest = new FileWriter(json1);
                gdest.write(dest);
                gdest.close();
            }

            Project project2 = gson.fromJson(dest, Project.class);

            {

                String dest2 = gson.toJson(project2);
                File json2 = new File(f.getParentFile(), f.getName() + "json2");
                FileWriter gdest2 = new FileWriter(json2);
                gdest2.write(dest2);
                gdest2.close();

            }
            
           File f2 = new File(f.getParent(), f.getName() + "2");
           RebApp.saveProjectToFileJson(f2, project2);
            
            
            System.out.println("Final OK");

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    
    public static class SuperclassExclusionStrategy implements ExclusionStrategy {
        @Override
        public boolean shouldSkipClass(Class<?> arg0) {
            // System.out.println(" === C: " + arg0.getName() + " =====");
            return false;
        }

        @Override
        public boolean shouldSkipField(FieldAttributes fieldAttributes) {
            String fieldName = fieldAttributes.getName();
            // System.out.println(" * F[" + fieldName + "]");
            Class<?> theClass = fieldAttributes.getDeclaringClass();

            return isFieldInSuperclass(theClass, fieldName);
        }

        private boolean isFieldInSuperclass(Class<?> subclass, String fieldName) {
            Class<?> superclass = subclass.getSuperclass();

            // System.out.println(" ??? SuperClass: " + superclass.getName() + "::" +
            // fieldName + " =====");
            Field field;

            while (superclass != null) {
                field = getField(superclass, fieldName);

                if (field != null)
                    return true;

                superclass = superclass.getSuperclass();
            }

            return false;
        }

        private Field getField(Class<?> theClass, String fieldName) {
            try {
                return theClass.getDeclaredField(fieldName);
            } catch (Exception e) {
                return null;
            }
        }
    }

}
