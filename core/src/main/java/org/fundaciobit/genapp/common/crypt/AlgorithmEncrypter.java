package org.fundaciobit.genapp.common.crypt;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.fundaciobit.genapp.common.utils.Base64;


/**
 * 
 * @author anadal
 * 
 */
public class AlgorithmEncrypter extends AbstractEncrypter {

  public static final String ALGORITHM_AES = "AES";

  public static final String ALGORITHM_DES = "DES";

  public static final String ALGORITHM_RSA = "RSA";

  protected final String algorithm;

  protected final Key key;

  /**
   * @param keyValue
   * @param algorithm
   */
  public AlgorithmEncrypter(byte[] keyValue, String algorithm) throws Exception {
    super(keyValue);
    this.algorithm = algorithm;
    this.key = generateKey(keyValue, this.algorithm);
  }

  @Override
  public String encrypt(String data) throws Exception {
    return encrypt(this.key, data);
  }

  @Override
  public String decrypt(String encryptedData) throws Exception {
    return decrypt(this.key, encryptedData);
  }

  @Override
  public String encrypt(byte[] keyValue, String data) throws Exception {
    Key newkey = generateKey(keyValue, this.algorithm);
    return encrypt(newkey, data);
  }

  @Override
  public String decrypt(byte[] keyValue, String encryptedData) throws Exception {
    Key newkey = generateKey(keyValue, this.algorithm);
    return decrypt(newkey, encryptedData);
  }

  public String getAlgorithm() {
    return algorithm;
  }

  public Key getKey() {
    return key;
  }

  protected String encrypt(Key key, String data) throws Exception {
    Cipher c = Cipher.getInstance(this.algorithm);
    c.init(Cipher.ENCRYPT_MODE, key);
    byte[] encVal = c.doFinal(data.getBytes());
    String encryptedValue = Base64.encodeBytes(encVal, Base64.URL_SAFE);
    return encryptedValue;
  }

  protected String decrypt(Key key, String encryptedData)
      throws Exception {
    Cipher c = Cipher.getInstance(this.algorithm);
    c.init(Cipher.DECRYPT_MODE, key);
    byte[] decodedValue = Base64.decode(encryptedData, Base64.URL_SAFE);
    byte[] decValue = c.doFinal(decodedValue);
    String decryptedValue = new String(decValue);
    return decryptedValue;
  }

  private static Key generateKey(byte[] keyValue, String algorithm) throws Exception {
    Key key = new SecretKeySpec(keyValue, algorithm);
    return key;
  }

}
