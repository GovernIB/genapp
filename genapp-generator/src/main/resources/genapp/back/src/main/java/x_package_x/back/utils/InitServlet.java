package ${package}.back.utils;

import javax.annotation.security.RunAs;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import org.fundaciobit.genapp.common.crypt.AlgorithmEncrypter;
import org.fundaciobit.genapp.common.crypt.FileIDEncrypter;
import org.fundaciobit.genapp.common.filesystem.FileSystemManager;
import org.fundaciobit.genapp.common.web.exportdata.DataExporterManager;
import org.fundaciobit.genapp.common.web.i18n.I18NUtils;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import ${package}.hibernate.HibernateFileUtil;
import ${package}.logic.utils.I18NLogicUtils;
import ${package}.logic.utils.LogicUtils;
import ${package}.commons.utils.Configuracio;


/**
 * Servlet emprat per inicialitzar el Back
 * 
 * @author anadal
 * 
 */
@RunAs("${prefix}_USER")
public class InitServlet extends HttpServlet {

  protected final Logger log = Logger.getLogger(getClass());

  @Override
  public void init(ServletConfig config) throws ServletException {

    // Sistema de Fitxers
    // TODO Moure a logic
    try {      
      FileSystemManager.setFilesPath(Configuracio.getFilesDirectory());
      log.info("FileSystemManager path = " + FileSystemManager.getFilesPath().getAbsolutePath());
    } catch (Throwable th) {
      log.error("Error inicialitzant el sistema de sistema de fitxers: " + th.getMessage(), th);
    }

    // Sistema de Traduccions WEB
    try {
      ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
      String[] basenames = { 
          "missatges", // /WEB-INF/classes/
          "logicmissatges",
          "genapp",
          "${name}_genapp"};
      ms.setDefaultEncoding("UTF-8");
      ms.setBasenames(basenames);
      I18NUtils.setMessageSource(ms);
    } catch (Throwable th) {
      log.error("Error inicialitzant el sistema de traduccions web: " + th.getMessage(), th);
    }

    // Sistema de Traduccions LOGIC
    // TODO Moure a logic
    try {
      Class.forName(I18NLogicUtils.class.getName());
    } catch (Throwable th) {
      log.error("Error inicialitzant el sistema de traduccions logic: " + th.getMessage(), th);
    }

    // Encriptador d'identificador de Fitxer
    try {
      FileIDEncrypter encrypter = new FileIDEncrypter(Configuracio.getEncryptKey(),
          AlgorithmEncrypter.ALGORITHM_AES);
      HibernateFileUtil.setEncrypter(encrypter);
    } catch (Exception e) {
      log.error("Error instanciant File Encrypter: " + e.getMessage(), e);
    }

    // Inicialitzar els DataExporters
    /*
    try {
      Set<Class<? extends IExportDataPlugin>> plugins;
      
      if (Configuracio.isDesenvolupament()) {
        String [] classes = new String[] {
           "org.fundaciobit.plugins.exportdata.cvs.CSVPlugin",
           "org.fundaciobit.plugins.exportdata.ods.ODSPlugin",
           "org.fundaciobit.plugins.exportdata.excel.ExcelPlugin"  
        };
        plugins = new HashSet<Class<? extends IExportDataPlugin>>();
        for (String str : classes) {
          
          try {
            Class<?> cls = Class.forName(str);
            plugins.add((Class<? extends IExportDataPlugin>)cls);
          } catch (Throwable e) {            
          }
        }
        
        
      } else {
        plugins = PluginsManager.getPluginsByInterface(IExportDataPlugin.class);  
      }
      
      
      
      if (plugins == null || plugins.size() == 0) {
         log.warn("No existeixen Plugins de ExportData !!!!!");
      } else {
      
        for (Class<? extends IExportDataPlugin> class1 : plugins) {
          IExportDataPlugin edp = (IExportDataPlugin)PluginsManager.instancePluginByClass(class1);
          if (edp == null) {
            log.warn("No s'ha pogut instanciar Plugin associat a la classe " + class1.getName());
          } else {
            log.warn("Registrant DataExporter: " + class1.getName());
            DataExporterManager.addDataExporter(new DataExporter${fullname}(edp));
          } 
        }
      }

    } catch(Throwable e) {
      log.error("Error inicialitzant els DataExporters: " + e.getMessage(), e);
    }
    */

    // Mostrar Versió
    String ver = LogicUtils.getVersio();
    try {
      log.info("${fullname} Version: " + ver);
    } catch (Throwable e) {
      log.info("${fullname} Version: " + ver);
    }

  }

}
