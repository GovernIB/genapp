package org.fundaciobit.genapp.common.web.tiles;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileOutputStream;

/**
 * 
 * @author anadal
 * 24 mar 2025 14:31:56
 */
public class TilesXmlParser {
    public static String generateJavaCode(String filePath) {
        StringBuilder javaCode = new StringBuilder();

        javaCode.append("import org.apache.tiles.Definition;\n");
        javaCode.append("import org.apache.tiles.Attribute;\n");
        javaCode.append("import java.util.HashMap;\n");
        javaCode.append("import java.util.Map;\n\n");
        javaCode.append("public class TilesDefinitions {\n");
        javaCode.append("    public static final Map<String, Definition> map = new HashMap<>();\n\n");
        javaCode.append("    static {\n");

        try {
            File xmlFile = new File(filePath);
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

                    String extendsDefinition;
                    if (extendsAttr == null || extendsAttr.trim().length() == 0) {
                        extendsDefinition = "";
                    } else {

                        if (extendsAttr.trim().equals("webdb")) {
                            continue;
                        }

                        extendsDefinition = "def_" + extendsAttr.trim().replace('.', '_');
                    }

                    String n = "def_" + name.replace('.', '_');
                    javaCode.append("        Definition " + n + " = new Definition(" + extendsDefinition + ");\n");
                    javaCode.append("        " + n + ".setName(\"").append(name).append("\");\n");
                    if (!template.isEmpty()) {
                        javaCode.append("        " + n + ".setTemplateAttribute(new Attribute(\"").append(template)
                                .append("\"));\n");
                    }
                    if (!preparer.isEmpty()) {
                        javaCode.append("        " + n + ".setPreparer(\"").append(preparer).append("\");\n");
                    }
                    if (!extendsAttr.isEmpty()) {
                        javaCode.append("        " + n + ".setExtends(\"").append(extendsAttr).append("\");\n");
                    }

                    NodeList putNodes = element.getElementsByTagName("put-attribute");
                    for (int j = 0; j < putNodes.getLength(); j++) {
                        Node putNode = putNodes.item(j);
                        if (putNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element putElement = (Element) putNode;
                            String attrName = putElement.getAttribute("name");
                            String attrValue = putElement.getAttribute("value");
                            javaCode.append("        " + n + ".putAttribute(\"").append(attrName)
                                    .append("\", new Attribute(\"").append(attrValue).append("\"));\n");
                        }
                    }
                    javaCode.append("        map.put(" + n + ".getName()").append(", " + n + ");\n\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        javaCode.append("    }\n");
        javaCode.append("}\n");
        return javaCode.toString();
    }

    public static void main(String[] args) {
        String filePath = "D:\\dades\\dades\\CarpetesPersonals\\ProgramacioPortaFIB3\\portafib-3.0\\\\back\\src\\main\\webapp\\WEB-INF\\tiles.xml"; // Cambiar por la ruta real del archivo
        String javaCode = generateJavaCode(filePath);

        System.out.println(javaCode);
        try {

            FileOutputStream fos = new FileOutputStream(new File(
                    ".\\src\\main\\java\\org\\fundaciobit\\genapp\\common\\web\\tiles\\TilesDefinitions.java"));
            fos.write("package org.fundaciobit.genapp.common.web.tiles;\n".getBytes());
            fos.write(javaCode.getBytes());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
