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
      encrypter = new AlgorithmEncrypter("keyValuekeyValue".getBytes(),
          AlgorithmEncrypter.ALGORITHM_AES);

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
      encrypterInstance = new AlgorithmEncrypter(keyValue,  AlgorithmEncrypter.ALGORITHM_AES);

      encryptedData = encrypterInstance.encrypt(txt);

      System.out.println("Encript: " + encryptedData);

      System.out.println("Decript: " + encrypterInstance.decrypt(encryptedData));
      
      
      System.out.println("\n ======== TEST 3 instance manager =========");
      
      EncrypterManager.setEncrypter(encrypterInstance);
      
      encryptedData = EncrypterManager.encrypt(txt);
      System.out.println("Encript: " + encryptedData);

      System.out.println("Decript: " + EncrypterManager.decrypt(encryptedData));

    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
    }

  }

}
