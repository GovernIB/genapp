package org.fundaciobit.genappsqltutorial.back;

import java.lang.reflect.Modifier;
import java.util.ArrayList;

import org.fundaciobit.genapp.common.web.controller.CommonBaseController;
import org.springframework.stereotype.Controller;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;

/**
 * 
 * @author anadal
 * 25 mar 2025 8:12:14
 */
public class TilesXml2AnnotationTiles {

    public static void main(String[] args) {
        try {
            getDefinitionsByAnnotation("org.fundaciobit.genappsqltutorial.back");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void getDefinitionsByAnnotation(String packageToScan) throws Exception {

        ClassGraph cg = new ClassGraph().enableAnnotationInfo();

        if (packageToScan != null) {
            cg.acceptPackages(packageToScan);
        }

        // Habilitar escaneo de anotaciones
        ScanResult scanResult = cg.scan();

        ArrayList<ClassInfo> cil = scanResult.getClassesWithAnnotation(Controller.class);

        for (ClassInfo classInfo2 : cil) {
            // Obtén la clase
            Class<?> classe = classInfo2.loadClass();

            // Imprime la clase y el valor de la anotación
            //Controller controller = classe.getAnnotation(Controller.class);

            if (CommonBaseController.class.isAssignableFrom(classe) 
                    && !Modifier.isAbstract(classe.getModifiers())
                    && classe.getName().indexOf(".webdb.") == -1) {
                CommonBaseController<?, ?> cbc;

                // Cercam el tileName de la classe      
                
                System.out.println(" CLASSE = " + classe.getName());

                cbc = (CommonBaseController<?, ?>) classe.getDeclaredConstructor().newInstance();

                String parentClassTileListName;

                parentClassTileListName = (String) classe.getMethod("getTileList").invoke(cbc);
                String parentClassTileFormName;
                // Invocar al método getTileForm del objeto 'cbc' cuya clase es 'classe'
                parentClassTileFormName = (String) classe.getMethod("getTileForm").invoke(cbc);

                System.out.println(
                        classe.getName() + "[ " + parentClassTileListName + ", " + parentClassTileFormName + "]");
            }

        }

    }

}
