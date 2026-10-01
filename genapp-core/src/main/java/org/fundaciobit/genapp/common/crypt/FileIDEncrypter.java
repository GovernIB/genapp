package org.fundaciobit.genapp.common.crypt;

import java.security.Key;

/**
 * 
 * @author anadal
 *
 */
public class FileIDEncrypter extends AlgorithmEncrypter {

  /**
   * 
   * @param keyValue
   * @param algorithm
   * @throws Exception
   */
  public FileIDEncrypter(byte[] keyValue, String algorithm) throws Exception {
    super(keyValue, algorithm);    
  }
  
  @Override
  protected String encrypt(Key key, String data) throws Exception {
    String encryptedData = super.encrypt(key, data);
    return encryptedData; //.replace('/', '_');
  }

  @Override
  protected String decrypt(Key key, String encryptedData) throws Exception {
    String idFileEncrypted = encryptedData; //.replace('_', '/');
    return super.decrypt(key, idFileEncrypted);
  }
  

}
