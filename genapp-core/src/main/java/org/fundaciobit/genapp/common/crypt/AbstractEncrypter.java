package org.fundaciobit.genapp.common.crypt;

/**
 * 
 * @author anadal
 *
 */
public abstract class AbstractEncrypter {
  
  private final byte[] keyValue;

  /**
   * @param keyValue
   */
  public AbstractEncrypter(byte[] keyValue) {
    super();
    this.keyValue = keyValue;
  }


  public byte[] getKeyValue() {
    return keyValue;
  }


  public String encrypt(String data) throws Exception {
    return encrypt(this.keyValue, data);
  }

  public String decrypt(String encryptedData) throws Exception {
    return decrypt(this.keyValue, encryptedData);
  }
  
  
  public abstract String encrypt(byte[] keyValue, String data) throws Exception;

  public abstract String decrypt(byte[] keyValue, String encryptedData) throws Exception;
  
  
}
