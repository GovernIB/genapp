package org.fundaciobit.genapp.generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
/**
 * Title:        Rapit Entity Bean 2010
 * Description:
 * Copyright:    Copyright (c) 2014
 * Company:      XmasSoft
 * @author anadal
 * @version 1.0
 */
public class SourceFile {
  
  private final Charset UTF8_CHARSET = Charset.forName("UTF-8");
  
  boolean utf8 = false;

  String fileName;
  
  String sourceCode;
  
  /**
   * 
   * @param fileName
   * @param sourceCode
   */
  public SourceFile(String fileName, String sourceCode, boolean utf8) {
    super();
    this.utf8 = utf8;
    this.fileName = fileName;
    this.sourceCode = sourceCode;
  }

  /**
   * 
   * @param fileName
   * @param sourceCode
   */
  public SourceFile(String fileName, String sourceCode) {
    super();
    this.fileName = fileName;
    this.sourceCode = sourceCode;
  }

/**
 * 
 * @param parentDir
 * @return
 * @throws FileNotFoundException
 * @throws IOException
 */
  public File saveToPath(File parentDir) throws FileNotFoundException, IOException {
    File file = new File(parentDir, this.fileName);
    FileOutputStream fos = new FileOutputStream(file);
    if (utf8) {
      fos.write(sourceCode.getBytes(UTF8_CHARSET));
    } else {
      fos.write(sourceCode.getBytes());
    }
    /*
    Writer out = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
    out.write(this.sourceCode);
    */
    fos.flush();
    fos.close();
    /*
    out.flush();
    out.close();
    */
    return file;
  }
  
  /**
   * 
   * @param parentDir
   * @return
   * @throws FileNotFoundException
   * @throws IOException
   */
    public File saveToPathIfNotExists(File parentDir) 
      throws FileNotFoundException, IOException {
      File file = new File(parentDir, this.fileName);
      if (!file.exists()) {
        return saveToPath(parentDir);
      } else {
        return null;
      }
    }
  
}
