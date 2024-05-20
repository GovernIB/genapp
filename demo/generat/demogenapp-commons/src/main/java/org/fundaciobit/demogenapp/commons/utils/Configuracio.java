package org.fundaciobit.demogenapp.commons.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

/**
 * 
 * @author GenApp
 * 
 */
public class Configuracio implements Constants {

    private static final Logger LOG = LoggerFactory.getLogger(Configuracio.class);

    private static Properties portafibProperties;

    private static Properties portafibSystemProperties;

    public static Properties getPortaFIBProperties() {
        if (portafibProperties == null) {
            portafibProperties = loadPropertiesFromKey(Constants.DEMOGENAPP_PROPERTY_BASE + ".properties");
        }
        return portafibProperties;
    }

    public static Properties getPortaFIBSystemProperties() {
        if (portafibSystemProperties == null) {
            portafibSystemProperties = loadPropertiesFromKey(Constants.DEMOGENAPP_PROPERTY_BASE + "system.properties");
        }
        return portafibSystemProperties;
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

    private static Long getLongPortaFIBProperty(String key) {
        String value = getPortaFIBProperties().getProperty(key);
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
        properties.putAll(getPortaFIBSystemProperties());
        properties.putAll(getPortaFIBProperties());
        return properties;
    }

    public static boolean isDesenvolupament() {

        return "true".equalsIgnoreCase(getPortaFIBProperties().getProperty(DEMOGENAPP_PROPERTY_BASE + "development"));
    }

    public static boolean isCAIB() {
        return "true".equalsIgnoreCase(getPortaFIBProperties().getProperty(DEMOGENAPP_PROPERTY_BASE + "iscaib"));
    }

    public static String getAppEmail() {
        return getPortaFIBProperties().getProperty(DEMOGENAPP_PROPERTY_BASE + "email.from");
    }

    public static String getAppName() {
        return getPortaFIBProperties().getProperty(DEMOGENAPP_PROPERTY_BASE + "name", "DemoGenApp");
    }

    public static String getFrontUrl() {
        return getPortaFIBProperties().getProperty(DEMOGENAPP_PROPERTY_BASE + "url.front");
    }

    public static String getBackUrl() {
        return getPortaFIBProperties().getProperty(DEMOGENAPP_PROPERTY_BASE + "url.back");
    }

    public static String getAjudaViaTelefon() {
        return getPortaFIBProperties().getProperty(DEMOGENAPP_PROPERTY_BASE + "ajuda.viatelefon");
    }

    public static String getAjudaViaWeb() {
        return getPortaFIBProperties().getProperty(DEMOGENAPP_PROPERTY_BASE + "ajuda.viaweb");
    }

    public static String getAjudaViaEmail() {
        return getPortaFIBProperties().getProperty(DEMOGENAPP_PROPERTY_BASE + "ajuda.viaemail");
    }

    public static String getDefaultLanguage() {
        return getPortaFIBProperties().getProperty(DEMOGENAPP_PROPERTY_BASE + "defaultlanguage", "ca");
    }

    public static byte[] getEncryptKey() {
        return getPortaFIBProperties().getProperty(DEMOGENAPP_PROPERTY_BASE + "encryptkey", "0123456789123456").getBytes();
    }

    public static Long getMaxUploadSizeInBytes() {
        return getLongPortaFIBProperty(DEMOGENAPP_PROPERTY_BASE + "maxuploadsizeinbytes");
    }

    public static Long getMaxFitxerAdaptatSizeInBytes() {
        return getLongPortaFIBProperty(DEMOGENAPP_PROPERTY_BASE + "maxfitxeradaptatsizeinbytes");
    }

    public static File getFilesDirectory() {
        String path = getPortaFIBSystemProperties().getProperty(DEMOGENAPP_PROPERTY_BASE + "filesdirectory");
        if (path == null) {
            throw new RuntimeException("No existeix la propietat '" + DEMOGENAPP_PROPERTY_BASE + "filesdirectory'"
                    + " al fitxer " + System.getProperty(DEMOGENAPP_PROPERTY_BASE + "system.properties")
                    + ". S'hauria d'anar al fitxer " + System.getProperty(DEMOGENAPP_PROPERTY_BASE + "system.properties")
                    + " i incloure la propietat '" + DEMOGENAPP_PROPERTY_BASE
                    + "filesdirectory'" + " amb una ruta al directori on l'aplició gestionara els fitxers.");
        }

        if (path.isEmpty()) {
            throw new RuntimeException("No s'ha definit la propietat '" + DEMOGENAPP_PROPERTY_BASE + "filesdirectory'"
                    + " al fitxer " + System.getProperty(DEMOGENAPP_PROPERTY_BASE + "system.properties")
                    + ". S'hauria d'anar al fitxer " + System.getProperty(DEMOGENAPP_PROPERTY_BASE + "system.properties")
                    + " i donar valor a la propietat '" + DEMOGENAPP_PROPERTY_BASE +"filesdirectory'"
                    + " amb una ruta al directori on l'aplició gestionara els fitxers.");
        }
        
        File filesFolder = new File(path);
        
        if (!filesFolder.exists()) {
            throw new RuntimeException("El directori indicat a la propietat '" + DEMOGENAPP_PROPERTY_BASE
                    + ".filesdirectory'" + " del fitxer "
                    + System.getProperty(DEMOGENAPP_PROPERTY_BASE + "system.properties")
                    + " no existeix. S'hauria de modificar la ruta indicada per la d'un directori existent, o crear un directori amb la ruta: "
                    + path);
        }

        if (!filesFolder.isDirectory()) {
            throw new RuntimeException("El directori indicat a la propietat '" + DEMOGENAPP_PROPERTY_BASE
                    + ".filesdirectory'" + " del fitxer "
                    + System.getProperty(DEMOGENAPP_PROPERTY_BASE + "system.properties")
                    + " no es un directori, probablement es tracti d'un fitxer. S'hauria de modificar la ruta indicada per la d'un directori existent.");
        }

        if (!filesFolder.canWrite()) {
            throw new RuntimeException("El directori indicat a la propietat '" + DEMOGENAPP_PROPERTY_BASE
                    + ".filesdirectory'" + " del fitxer "
                    + System.getProperty(DEMOGENAPP_PROPERTY_BASE + "system.properties")
                    + " es un directori sense permisos d'escriptura. S'haurien de donar permisos d'escriptura al directori, o canviar la ruta a un directori amb permisos.");
        }
        return new File(path);

    }

    public static String getFileSystemManager() {
        return getPortaFIBProperties().getProperty(DEMOGENAPP_PROPERTY_BASE + "filesystemmanagerclass");
    }

}
