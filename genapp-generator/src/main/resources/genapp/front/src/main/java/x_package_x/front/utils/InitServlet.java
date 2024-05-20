package ${package}.front.utils;

import ${package}.commons.utils.Constants;
import org.apache.log4j.Logger;

import org.fundaciobit.genapp.common.web.i18n.I18NUtils;
import org.fundaciobit.pluginsib.core.utils.PluginsManager;
import org.fundaciobit.pluginsib.login.api.IPluginLogin;
import org.fundaciobit.pluginsib.login.springutils.PluginLoginManager;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import ${package}.commons.utils.Configuracio;

import javax.annotation.security.RunAs;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import java.util.Properties;

/**
 * Servlet emprat per inicialitzar el Front
 * 
 * @author anadal
 * 
 */
@RunAs(Constants.${prefix}_ADMIN)
public class InitServlet extends HttpServlet {

    protected final Logger log = Logger.getLogger(getClass());

    @Override
    public void init(ServletConfig config) throws ServletException {

        // Sistema de Traduccions WEB
        try {
            ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
            String[] basenames = { "classpath:/missatges" };
            ms.setDefaultEncoding("UTF-8");
            ms.setBasenames(basenames);
            I18NUtils.setMessageSource(ms);
        } catch (Throwable th) {
            log.error("Error inicialitzant el sistema de traduccions web: " + th.getMessage(), th);
        }

        IPluginLogin pl = (IPluginLogin) getPluginLogin();
        PluginLoginManager.setPluginLogin(pl);

    }

    /**
     * 
     * @return
     * @throws Exception
     */
    private IPluginLogin getPluginLogin() throws ServletException {

        Properties properties = new Properties();
        properties.putAll(Configuracio.getSystemAndFileProperties());

        final String baseProps = Constants.${name_uppercase}_PROPERTY_BASE;

        final String propClass = baseProps + IPluginLogin.PLUGIN_LOGIN_PROPERTY_BASE + "class";

        String pluginClassName = properties.getProperty(propClass);

        // Carregant la classe ja que els plugins es troben en el WAR de FRONT
        log.info("Carregant classe " + pluginClassName + " ...");
        Class<?> pluginClass;
        try {
            pluginClass = Class.forName(pluginClassName.trim());
        } catch (Exception ex) {
            final String msg = "Error carregant la classe " + pluginClassName + " associada a un plugin:"
                    + ex.getMessage();
            log.error(msg, ex);
            throw new ServletException(msg, ex);
        }

        if (pluginClass == null) {
            throw new ServletException(
                    "No s'ha definit la propietat ´" + propClass + "´ dins dels fitxers de propietats de l'aplicació.");
        } else {
            Object obj = PluginsManager.instancePluginByClass(pluginClass, baseProps, properties);

            if (obj == null) {
                throw new ServletException("Per alguna raó desconeguda no s'ha pogut carregar la classe " + pluginClass
                        + ". Consulti el log de l'aplicació per obtenir més detalls del problema.");
            }

            return (IPluginLogin) obj;
        }

    }

}
