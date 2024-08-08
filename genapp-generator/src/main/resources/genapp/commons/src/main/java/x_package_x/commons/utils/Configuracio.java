package ${package}.commons.utils;

import org.apache.log4j.Logger;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.Properties;

/**
 * 
 * @author GenApp
 * 
 */
public class Configuracio implements Constants {

    private static final Logger LOG = Logger.getLogger(Configuracio.class);

    private static Properties appProperties;

    private static Properties appSystemProperties;

    public static Properties getAppProperties() {
        if (appProperties == null) {
            appProperties = loadPropertiesFromKey(Constants.${name_uppercase}_PROPERTY_BASE + "properties");
        }
        return appProperties;
    }

    public static Properties getAppSystemProperties() {
        if (appSystemProperties == null) {
            appSystemProperties = loadPropertiesFromKey(Constants.${name_uppercase}_PROPERTY_BASE + "system.properties");
        }
        return appSystemProperties;
    }

    private static Properties loadPropertiesFromKey(String key) {
        String propertyFileName = System.getProperty(key);

        if (propertyFileName == null) {
            String msg = "No existeix la propietat: " + key
                    + " al fitxer standalone. S'hauria d'incloure aquesta propietat a l'etiqueta <system-properties> del fitxer standalone.";
            throw new RuntimeException(msg);
        }

        if (propertyFileName.trim().length() == 0) {
            String msg = "La propietat: " + key
                    + " del fitxer standalone no té valor. Se li ha de posar el fitxer corresponent a la propietat al fitxer standalone";
            throw new RuntimeException(msg);
        }

        File file = new File(propertyFileName);

        if (!file.exists()) {
            throw new RuntimeException("La propietat: " + key
                    + " del fitxer standalone apunta a un fitxer que no existeix (" + propertyFileName + ")");
        }

        try (Reader reader = new FileReader(file)) {
            Properties prop = new Properties();
            prop.load(reader);
            return prop;
        } catch (Exception e) {
            throw new RuntimeException("La propietat: " + key + " del fitxer standalone apunta a un fitxer("
                    + propertyFileName + ") que no es pot llegir:" + e.getMessage(), e);
        }
    }

    private static Long getLongAppProperty(String key) {
        String value = getAppProperties().getProperty(key);
        Long valueLong = null;
        if (value != null) {
            try {
                valueLong = Long.parseLong(value);
            } catch (Exception e) {
                LOG.error("Error parsing long value for key " + key, e);
            }
        }

        return valueLong;

    }

    public static Properties getSystemAndFileProperties() {
        Properties properties = new Properties();
        properties.putAll(System.getProperties());
        properties.putAll(getAppSystemProperties());
        properties.putAll(getAppProperties());
        return properties;
    }

    public static boolean isDesenvolupament() {

        return "true".equalsIgnoreCase(getAppProperties().getProperty(${name_uppercase}_PROPERTY_BASE + "development"));
    }

    public static boolean isCAIB() {
        return "true".equalsIgnoreCase(getAppProperties().getProperty(${name_uppercase}_PROPERTY_BASE + "iscaib"));
    }

    public static String getAppEmail() {
        return getAppProperties().getProperty(${name_uppercase}_PROPERTY_BASE + "email.from");
    }

    public static String getAppName() {
        return getAppProperties().getProperty(${name_uppercase}_PROPERTY_BASE + "name", "${fullname}");
    }

    public static String getFrontUrl() {
        return getAppProperties().getProperty(${name_uppercase}_PROPERTY_BASE + "url.front");
    }

    public static String getBackUrl() {
        return getAppProperties().getProperty(${name_uppercase}_PROPERTY_BASE + "url.back");
    }

    public static String getAjudaViaTelefon() {
        return getAppProperties().getProperty(${name_uppercase}_PROPERTY_BASE + "ajuda.viatelefon");
    }

    public static String getAjudaViaWeb() {
        return getAppProperties().getProperty(${name_uppercase}_PROPERTY_BASE + "ajuda.viaweb");
    }

    public static String getAjudaViaEmail() {
        return getAppProperties().getProperty(${name_uppercase}_PROPERTY_BASE + "ajuda.viaemail");
    }

    public static String getDefaultLanguage() {
        return getAppProperties().getProperty(${name_uppercase}_PROPERTY_BASE + "defaultlanguage", "ca");
    }

    public static byte[] getEncryptKey() {
        return getAppProperties().getProperty(${name_uppercase}_PROPERTY_BASE + "encryptkey", "0123456789123456").getBytes();
    }

    public static Long getMaxUploadSizeInBytes() {
        return getLongAppProperty(${name_uppercase}_PROPERTY_BASE + "maxuploadsizeinbytes");
    }

    public static Long getMaxFitxerAdaptatSizeInBytes() {
        return getLongAppProperty(${name_uppercase}_PROPERTY_BASE + "maxfitxeradaptatsizeinbytes");
    }

    public static File getFilesDirectory() {
        String path = getAppSystemProperties().getProperty(${name_uppercase}_PROPERTY_BASE + "filesdirectory");
        if (path == null) {
            throw new RuntimeException("No existeix la propietat '" + ${name_uppercase}_PROPERTY_BASE + "filesdirectory'"
                    + " al fitxer " + System.getProperty(${name_uppercase}_PROPERTY_BASE + "system.properties")
                    + ". S'hauria d'anar al fitxer " + System.getProperty(${name_uppercase}_PROPERTY_BASE + "system.properties")
                    + " i incloure la propietat '" + ${name_uppercase}_PROPERTY_BASE
                    + "filesdirectory'" + " amb una ruta al directori on l'aplició gestionara els fitxers.");
        }

        if (path.isEmpty()) {
            throw new RuntimeException("No s'ha definit la propietat '" + ${name_uppercase}_PROPERTY_BASE + "filesdirectory'"
                    + " al fitxer " + System.getProperty(${name_uppercase}_PROPERTY_BASE + "system.properties")
                    + ". S'hauria d'anar al fitxer " + System.getProperty(${name_uppercase}_PROPERTY_BASE + "system.properties")
                    + " i donar valor a la propietat '" + ${name_uppercase}_PROPERTY_BASE +"filesdirectory'"
                    + " amb una ruta al directori on l'aplició gestionara els fitxers.");
        }
        
        File filesFolder = new File(path);
        
        if (!filesFolder.exists()) {
            throw new RuntimeException("El directori indicat a la propietat '" + ${name_uppercase}_PROPERTY_BASE
                    + ".filesdirectory'" + " del fitxer "
                    + System.getProperty(${name_uppercase}_PROPERTY_BASE + "system.properties")
                    + " no existeix. S'hauria de modificar la ruta indicada per la d'un directori existent, o crear un directori amb la ruta: "
                    + path);
        }

        if (!filesFolder.isDirectory()) {
            throw new RuntimeException("El directori indicat a la propietat '" + ${name_uppercase}_PROPERTY_BASE
                    + ".filesdirectory'" + " del fitxer "
                    + System.getProperty(${name_uppercase}_PROPERTY_BASE + "system.properties")
                    + " no es un directori, probablement es tracti d'un fitxer. S'hauria de modificar la ruta indicada per la d'un directori existent.");
        }

        if (!filesFolder.canWrite()) {
            throw new RuntimeException("El directori indicat a la propietat '" + ${name_uppercase}_PROPERTY_BASE
                    + ".filesdirectory'" + " del fitxer "
                    + System.getProperty(${name_uppercase}_PROPERTY_BASE + "system.properties")
                    + " es un directori sense permisos d'escriptura. S'haurien de donar permisos d'escriptura al directori, o canviar la ruta a un directori amb permisos.");
        }
        return new File(path);

    }

    public static String getFileSystemManager() {
        return getAppProperties().getProperty(${name_uppercase}_PROPERTY_BASE + "filesystemmanagerclass");
    }

}
