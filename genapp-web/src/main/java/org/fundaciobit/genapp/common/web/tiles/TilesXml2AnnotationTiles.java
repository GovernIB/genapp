package org.fundaciobit.genapp.common.web.tiles;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.tiles.Attribute;
import org.apache.tiles.Definition;
import org.fundaciobit.genapp.common.web.controller.CommonBaseController;
import org.springframework.stereotype.Controller;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;

/**
 * 
 * @author anadal
 * 25 mar 2025 8:12:14
 */
public class TilesXml2AnnotationTiles {

    public static void tileXml2ProgrammaticTiles(String packageBase, Map<String, String> tabs, String filePath)
            throws Exception, FileNotFoundException, IOException {
        File xmlFile = new File(filePath);

        if (!xmlFile.exists()) {
            throw new Exception("No s'ha trobat el fitxer tiles.xml a " + xmlFile.getAbsolutePath());
        }

        File srcDir = new File(xmlFile.getParentFile().getParentFile().getParentFile(), "java");
        if (!srcDir.exists()) {
            throw new Exception("No s'ha trobat el directori src/main/java a " + xmlFile.getAbsolutePath());
        }

        Map<String, Definition> definitions = getDefinitionsOfTilesXml(xmlFile);

        Map<String, Class<?>> classByTileName = getClassByTileName(packageBase);

        Set<String> ignorar = new HashSet<String>(Arrays.asList("base.definition.contingut", "base.menu_i_contingut",
                "base.cap", "webdb", "base.definition.cap_contingut_peu", "base.definition", "desenvolupament",
                "base.menu", "base.contingut", "principal", "base.peu"));

        System.out.println(" ----------------------------------------------------------------");
        System.out.println(" - Ha d'afegir el següent codi a la classe TilesFactoryApp.java -");
        System.out.println(" ----------------------------------------------------------------");

        for (String defName : definitions.keySet()) {
            if (ignorar.contains(defName)) {
                continue;
            }
            Class<?> c = classByTileName.get(defName);
            if (c == null) {
                System.out.println("        // " + defName);
                Definition def = definitions.get(defName);
                System.out.println(definitionName2TileDefinition(definitions, def));
            }
        }

        Map<File, String> tileAnnotationsByFile = new HashMap<File, String>();
        for (String defName : definitions.keySet()) {
            if (ignorar.contains(defName)) {
                continue;
            }
            Class<?> c = classByTileName.get(defName);
            if (c != null) {
                //System.err.println("        // " + defName);
                Definition def = definitions.get(defName);
                if (!"webdb".equals(def.getExtends())) {
                    definitionName2TileAnnotation(tileAnnotationsByFile, def, classByTileName, tabs, srcDir);
                }
            }
        }

        for (Map.Entry<File, String> entry : tileAnnotationsByFile.entrySet()) {
            File key = entry.getKey();
            String val = entry.getValue();

            String java = new String(Files.readAllBytes(Paths.get(key.getPath())), StandardCharsets.UTF_8);

            int publicClass = java.indexOf("public class ");

            if (publicClass == -1) {
                publicClass = java.indexOf("public final class ");
            }
            if (publicClass == -1) {
                System.err.println("--------------------------------------------------------------------");
                System.err.println(
                        " No s'ha trobat 'public class' o 'public final class' al fitxer " + key.getAbsolutePath());
                System.err.println(" Afegeix manualment a aquesta classe el següent codi: " + val + "\n");
            } else {
                String java2 = java.substring(0, publicClass) + val + java.substring(publicClass);
                FileOutputStream fos = new FileOutputStream(key);
                fos.write(java2.getBytes());
                fos.close();
            }
        }

        System.out.println("\n\n Afegides anotacions @Tile a les classes Controladores del Back. ");
    }

    protected static String definitionName2TileAnnotation(Map<File, String> tileAnnotationsByFile, Definition def,
            Map<String, Class<?>> classByTileName, Map<String, String> tabs, File srcDir) throws Exception {

        String tab = tabs.get(def.getExtends());
        String nom = def.getName();
        String type;
        if (tab == null) {
            tab = "\"" + def.getExtends() + "\"";
            type = "ANOTHER";
        } else {
            if (nom.toLowerCase().indexOf("list") != -1) {
                type = "WEBDB_LIST";
            } else if (nom.toLowerCase().indexOf("form") != -1) {
                type = "WEBDB_FORM";
            } else {
                type = "ANOTHER_or_WEBDB_LIST_or_WEBDB_FORM";
            }
        }

        String tile = "// " + def + "\n" + "@org.fundaciobit.genapp.common.web.tiles.Tile(name = \"" + nom
                + "\", type = org.fundaciobit.genapp.common.web.tiles.TileType." + type + ", extendsTile = " + tab
                + ")\n";

        File java = new File(srcDir, classByTileName.get(nom).getName().replace('.', '/') + ".java");

        if (!java.exists()) {
            throw new Exception(
                    "No es troba la classe " + classByTileName.get(nom).getName() + " a " + java.getAbsolutePath());
        }

        tileAnnotationsByFile.put(java, tile.toString());

        return tile.toString();
    }

    protected static String definitionName2TileDefinition(Map<String, Definition> definitions, Definition def) {
        StringBuffer javaCode = new StringBuffer();
        String name = def.getName();
        String template = def.getTemplateAttribute() == null ? null : def.getTemplateAttribute().toString();
        String preparer = def.getPreparer();

        String extendsAttr = def.getExtends();

        String extendsDefinition;
        if (extendsAttr == null || extendsAttr.trim().length() == 0) {
            extendsDefinition = null;
        } else {

            //            if (extendsAttr.trim().equals("webdb")) {
            //                continue;
            //            }

            extendsDefinition = "def_" + extendsAttr.trim().replace('.', '_');
        }

        String n = "def_" + name.replace('.', '_');
        if (extendsDefinition == null) {
            String templateStr = template == null ? "null" : ("\"" + template + "\"");
            javaCode.append("        Definition " + n + " = new Definition(\"" + name + "\"," + templateStr
                    + ", new HashMap<String, Attribute>());\n");
        } else {
            javaCode.append("        Definition " + n + " = new Definition(" + extendsDefinition + ");\n");
            javaCode.append("        " + n + ".setName(\"").append(name).append("\");\n");
            if (template != null && !template.trim().isEmpty()) {
                javaCode.append("        " + n + ".setTemplateAttribute(new Attribute(\"").append(template)
                        .append("\"));\n");
            }
        }
        if (preparer != null && !preparer.trim().isEmpty()) {
            javaCode.append("        " + n + ".setPreparer(\"").append(preparer).append("\");\n");
        }
        if (extendsAttr != null && !extendsAttr.trim().isEmpty()) {
            javaCode.append("        " + n + ".setExtends(\"").append(extendsAttr).append("\");\n");
        }

        Set<String> attributes = def.getLocalAttributeNames();
        if (attributes != null) {
            for (String a : attributes) {
                Attribute val = def.getAttribute(a);
                javaCode.append("        " + n + ".putAttribute(\"").append(a).append("\", new Attribute(\"")
                        .append(val.toString()).append("\"));\n");
            }
        }

        javaCode.append("        map.put(" + n + ".getName(), ").append(", " + n + ");\n\n");

        return javaCode.toString();
    }

    protected static Map<String, Class<?>> getClassByTileName(String packageToScan) throws Exception {

        Map<String, Class<?>> map = new HashMap<String, Class<?>>();

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

            if (CommonBaseController.class.isAssignableFrom(classe) && !Modifier.isAbstract(classe.getModifiers())
            /* && classe.getName().indexOf(".webdb.") == -1 */) {
                CommonBaseController<?, ?> cbc;

                // Cercam el tileName de la classe      

                //System.out.println(" CLASSE = " + classe.getName());

                cbc = (CommonBaseController<?, ?>) classe.getDeclaredConstructor().newInstance();

                String parentClassTileListName;

                parentClassTileListName = (String) classe.getMethod("getTileList").invoke(cbc);
                String parentClassTileFormName;
                // Invocar al método getTileForm del objeto 'cbc' cuya clase es 'classe'
                parentClassTileFormName = (String) classe.getMethod("getTileForm").invoke(cbc);

                map.put(parentClassTileListName, classe);
                map.put(parentClassTileFormName, classe);
                /*
                System.out.println(
                        classe.getName() + "[ " + parentClassTileListName + ", " + parentClassTileFormName + "]");
                        */
            }

        }
        return map;
    }

    protected static Map<String, Definition> getDefinitionsOfTilesXml(File xmlFile) throws Exception {

        Map<String, Definition> map = new HashMap<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(xmlFile);
        document.getDocumentElement().normalize();

        NodeList definitionNodes = document.getElementsByTagName("definition");
        for (int i = 0; i < definitionNodes.getLength(); i++) {
            Node node = definitionNodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String name = element.getAttribute("name");
                String template = element.getAttribute("template");
                String preparer = element.getAttribute("preparer");

                String extendsAttr = element.getAttribute("extends");

                Definition def = new Definition();
                def.setName(name);
                if (!template.isEmpty()) {
                    def.setTemplateAttribute(new Attribute(template));
                }
                if (!preparer.isEmpty()) {
                    def.setPreparer(preparer);
                }
                if (!extendsAttr.isEmpty()) {
                    def.setExtends(extendsAttr);
                }

                NodeList putNodes = element.getElementsByTagName("put-attribute");
                for (int j = 0; j < putNodes.getLength(); j++) {
                    Node putNode = putNodes.item(j);
                    if (putNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element putElement = (Element) putNode;
                        String attrName = putElement.getAttribute("name");
                        String attrValue = putElement.getAttribute("value");
                        def.putAttribute(attrName, new Attribute(attrValue));
                    }
                }
                map.put(def.getName(), def);
            }
        }

        return map;
    }

}
