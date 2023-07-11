package org.fundaciobit.genapp.core.test;

import org.fundaciobit.genapp.common.crypt.AlgorithmEncrypter;
import org.fundaciobit.genapp.common.crypt.EncrypterManager;

/**
 * 
 * @author anadal
 *
 */
public class EncrypterManagerTester {

    public static void main(String[] args) {

        try {
            final byte[] keyValue = "portafibportafib".getBytes();
            final String txt = "HOLA12345";
            String encryptedData;

            System.out.println(" ======== TEST 1 default manager =========");

            encryptedData = EncrypterManager.encrypt(txt);
            System.out.println("Encript: " + encryptedData);

            System.out.println("Decript: " + EncrypterManager.decrypt(encryptedData));

            System.out.println("\n ======== TEST 2 instance =========");

            AlgorithmEncrypter encrypterInstance;
            encrypterInstance = new AlgorithmEncrypter(keyValue, AlgorithmEncrypter.ALGORITHM_AES);

            encryptedData = encrypterInstance.encrypt(txt);

            System.out.println("Encript: " + encryptedData);

            System.out.println("Decript: " + encrypterInstance.decrypt(encryptedData));

            System.out.println("\n ======== TEST 3 instance manager =========");

            EncrypterManager.setEncrypter(encrypterInstance);

            encryptedData = EncrypterManager.encrypt(txt);
            System.out.println("Encript: " + encryptedData);

            System.out.println("Decript: " + EncrypterManager.decrypt(encryptedData));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
