package org.fundaciobit.genappsqltutorial.tutorial.utils.annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.fundaciobit.genappsqltutorial.tutorial.units.SQL01Select;
import org.fundaciobit.genappsqltutorial.tutorial.utils.PartInfo;
import org.fundaciobit.genappsqltutorial.tutorial.utils.UnitInfo;
import org.fundaciobit.genappsqltutorial.tutorial.utils.javaparser.JavaParser;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import com.google.common.base.Predicate;

/**
 * https://github.com/ronmamo/reflections
 * 
 * @author anadal
 *
 */

public class AnnotationManager {

    public static final String packbase = SQL01Select.class.getPackageName(); // =
                                                                              // "org.fundaciobit.genappsqltutorial.tutorial.units";

    /**
     * 
     * @param pack
     * @return
     */
    private static Map<Class<?>, TutorialUnit> getUnits(String pack) {
        Map<Class<?>, TutorialUnit> units = new HashMap<Class<?>, TutorialUnit>();
        // Search classes with annotation @SqlClass

        Reflections reflections = new Reflections(pack);

        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(TutorialUnit.class);

        for (Class<?> unit : annotated) {
            // System.out.println(class1.getName());

            Annotation[] annotations = unit.getAnnotations();

            for (Annotation a : annotations) {
                if (a instanceof TutorialUnit) {
                    TutorialUnit sql = (TutorialUnit) a;
                    units.put(unit, sql);
                }
            }

        }
        return units;
    }

    /**
     * 
     * @return
     */
    public static List<UnitInfo> getAllUnits() {

        Map<Class<?>, TutorialUnit> units = AnnotationManager.getUnits(packbase);

        List<UnitInfo> list = new ArrayList<UnitInfo>();

        for (Entry<Class<?>, TutorialUnit> entry : units.entrySet()) {

            list.add(new UnitInfo(entry.getKey(), entry.getValue().title(),
                    entry.getValue().description(), entry.getValue().url()));
        }

        Collections.sort(list);

        return list;
    }

    /**
     * 
     * @param unitClass
     * @return
     */
    public static List<PartInfo> getPartsOfUnit(Class<?> unitClass) {

        final String className = unitClass.getCanonicalName();
        final Predicate<String> filter = new Predicate<String>() {
            public boolean apply(String arg0) {
                return arg0.startsWith(className);
            }
        };

        Reflections reflections = new Reflections(
                new ConfigurationBuilder().setUrls(ClasspathHelper.forClass(unitClass))
                        .filterInputsBy(filter).setScanners(new MethodAnnotationsScanner()));

        Set<Method> resources = reflections.getMethodsAnnotatedWith(TutorialPart.class);

        Map<String, String> bodymethodsByName = JavaParser.getMethods(unitClass.getName());

        List<PartInfo> parts = new ArrayList<PartInfo>();

        for (Method method : resources) {
            System.out.println("============== " + method.getName() + " ================");

            String metode = method.getName();

            TutorialPart[] part = method.getAnnotationsByType(TutorialPart.class);

            String descripcio = part[0].description();
            String sql = part[0].sql();
            int order = part[0].order();
            String title = part[0].title();

            String sourcecode = bodymethodsByName.get(metode);

            PartInfo p = new PartInfo(title, descripcio, metode, sql, sourcecode, order);

            parts.add(p);

        }

        Collections.sort(parts);

        return parts;

    }

}
