package org.fundaciobit.genapp.common.crypt;

/**
 * 
 * @author anadal
 * 
 */
public class EncrypterManager {

    private static AbstractEncrypter encrypter = null;

    static {
        try {
            encrypter = new AlgorithmEncrypter("keyValuekeyValue".getBytes(), AlgorithmEncrypter.ALGORITHM_AES);

        } catch (Exception e) {
            System.err.print("Error instanciant Encrypter per defecte: " + e.getMessage());
            e.printStackTrace(System.err);
        }
    }

    public static AbstractEncrypter getEncrypter() {
        return encrypter;
    }

    public static void setEncrypter(AbstractEncrypter encrypter) {
        EncrypterManager.encrypter = encrypter;
    }

    public static String encrypt(String data) throws Exception {
        return encrypter.encrypt(data);
    }

    public static String decrypt(String encryptedData) throws Exception {
        return encrypter.decrypt(encryptedData);
    }

    public static String encrypt(byte[] keyValue, String data) throws Exception {
        return encrypter.encrypt(keyValue, data);
    }

    public static String decrypt(byte[] keyValue, String encryptedData) throws Exception {
        return encrypter.decrypt(keyValue, encryptedData);
    }

}
