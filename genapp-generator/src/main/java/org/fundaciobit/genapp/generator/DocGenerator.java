package org.fundaciobit.genapp.generator;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.fundaciobit.genapp.Project;

import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;

/**
 * 
 * @author anadal
 *
 */
public class DocGenerator {

    private static final Logger log = Logger.getLogger(DocGenerator.class);

    public static void generateDoc(Project project, File projectDir, String packagePath, List<String> moduls,
            String resourceBase, String appCurrentVersion) throws Exception, FileNotFoundException, IOException {
        
        
        log.info("--------  DOC ----------");

        File docDir = new File(projectDir, "doc");

        if (docDir.exists()) {
            return;
        }

        docDir.mkdirs();

        Map<String, Object> prop = new HashMap<String, Object>();

        prop.put("projectLowercase", project.projectName.toLowerCase());
        prop.put("project", project.projectName); // .toLowerCase()
        prop.put("projectUppercase", project.projectName.toUpperCase());
        
        prop.put("prefix", project.getPrefix().toUpperCase()); // .toLowerCase()
        prop.put("dollar", "$"); // .toLowerCase()
        prop.put("package", packagePath); // .toLowerCase()

        String resourceBaseDirs = resourceBase + "/doc";

        List<String> resources = CodeGenerator.llistatDeRecursos(resourceBaseDirs);
        for (String res : resources) {
            
            if (!res.endsWith(".odt")) {
                continue;
            }

            String fileName = res.substring(resourceBaseDirs.length()).replace("_x-name-x_", project.getProjectName().toLowerCase());
            fileName = fileName.replace("_x-fullname-x_", project.getProjectName());

            File dest = new File(docDir, fileName);
            if (res.endsWith("/")) {
                dest.mkdirs();
            } else {

                // TODO log.debug
                log.info("------DOCUMENTACIO[" + res + "] => " + dest.getAbsolutePath());

                dest.getParentFile().mkdirs();

                final byte[] data = CodeGenerator.getResource(res);

                mergeOdt(new ByteArrayInputStream(data), dest, prop);

            }
        }
    }

    public static void mergeOdt(InputStream in, File dst, Map<String, Object> mapping) throws Exception {

        IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Freemarker);

        // Create context Java model
        IContext context = report.createContext();
        context.putMap(mapping);

        // Generate report by merging Java model with the ODT

        java.io.OutputStream out = new FileOutputStream(dst);
        report.process(context, out);
    }

}
