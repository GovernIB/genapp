package org.fundaciobit.genapp;

import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import org.fundaciobit.genapp.FieldInfo;
import org.fundaciobit.genapp.ForeignKey;

/**
 * Title:        Rapit Entity Bean 2010
 * Description:
 * Copyright:    Copyright (c) 2014
 * Company:      XmasSoft
 * @author anadal
 * @version 1.0
 */
public class WebFieldInfo implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -4281873218333106587L;

  public int webtype;

  public ForeignKey foreignKey; // Only for Select WebType

  private String[] label; // Only for PrimaryKey WebType

  public WebFieldInfo() {
  }


  public WebFieldInfo(int webtype) {
    super();
    this.webtype = webtype;
    this.foreignKey = null;
    this.label = null;
  }


  public WebFieldInfo(int webtype, String[] label) {
    super();
    this.webtype = webtype;
    this.foreignKey = null;
    this.label = label;
  }
  
  public WebFieldInfo(int webtype, ForeignKey key) {
    super();
    this.webtype = webtype;
    this.foreignKey = key;
    this.label = null;
  }


  public int getWebtype() {
    return webtype;
  }


  public ForeignKey getForeignKey() {
    return foreignKey;
  }


  public String[] getLabel() {
    return label;
  }


  public void setWebtype(int webtype) {
    this.webtype = webtype;
  }


  public void setForeignKey(ForeignKey foreignKey) {
    this.foreignKey = foreignKey;
  }


  public void setLabel(String[] label) {
    this.label = label;
  }
  
  
  public static WebFieldInfo getDefaultWebTypeFromFieldInfo(String table, FieldInfo fi)
    throws Exception {

    WebFieldInfo[] all = getWebTypesFromJavaType(table, fi);

    return all[0];
  }
  
  @Override
  public String toString() {
    
    try {
      return WebType.toString(webtype);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      return e.getMessage();
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof WebFieldInfo) {
      WebFieldInfo wfi = (WebFieldInfo)obj;
      return this.webtype == wfi.webtype;
    }
    return false;
  }
  

  /**
   * WebTypes compatible with 
   * @param fi
   * @return
   * @throws Exception
   */
  public static WebFieldInfo[] getWebTypesFromJavaType(String table,FieldInfo fi)
    throws Exception {

    Class<?> javaType = fi.getJavaType();

    // =================================
    
    if (fi.foreignKeys.length != 0) {
      ForeignKey exported = null;
      ForeignKey imported = null;
      for (int i = 0; i < fi.foreignKeys.length; i++) {
        if (fi.foreignKeys[i].getType() == ForeignKey.EXPORTED) {
          exported = fi.foreignKeys[i];
        } else {
          imported = fi.foreignKeys[i];
        }
      }

      if (imported != null) {
        // @todo Select Field of external table
        if (fi.isPrimaryKey() && fi.isAutoIncrement()) {
          return new WebFieldInfo[] { 
             new WebFieldInfo(WebType.PrimaryKey,  new String[]{ "nom"}), 
             new WebFieldInfo(WebType.Query, imported) };
        } else {
          return new WebFieldInfo[] { new WebFieldInfo(WebType.Query, imported) };
        }
      } else {
        // Exported
        // @TODO select not nullable fields of this table "!!!!!
        if (exported != null) {
          return new WebFieldInfo[] {              
              new WebFieldInfo(WebType.PrimaryKey,  new String[]{ "nom"}),
              //,new WebFieldInfo(WebType.UserID), new WebFieldInfo(WebType.RoleID),              
              new WebFieldInfo(WebType.Query, exported)
            };
        } else {
          return new WebFieldInfo[] {
            new WebFieldInfo(WebType.PrimaryKey,  new String[]{ "nom"})
            //,new WebFieldInfo(WebType.UserID), new WebFieldInfo(WebType.RoleID)
          };
        }
      }
    }
    
    if (javaType.equals(Date.class)) {
      return new WebFieldInfo[] { new WebFieldInfo(WebType.Date),
          new WebFieldInfo(WebType.ComboBox)};
    }

    if (javaType.equals(Time.class)) {
      return new WebFieldInfo[] { new WebFieldInfo(WebType.Time),
          new WebFieldInfo(WebType.ComboBox) };
    }

    if (javaType.equals(Timestamp.class)) {
      return new WebFieldInfo[] {
          new WebFieldInfo(WebType.DateTime),
          new WebFieldInfo(WebType.ComboBox) };
    }

    if (javaType.equals(Boolean.class) || javaType.equals(Boolean.TYPE)) {
      return new WebFieldInfo[] { 
          new WebFieldInfo(WebType.Checkbox),
          new WebFieldInfo(WebType.ComboBox)
      };
    }
    
    if (fi.isPrimaryKey()) {
      if (fi.getJavaType().equals(String.class)) {
        return new WebFieldInfo[] { new WebFieldInfo(WebType.PrimaryKey)
            //, new WebFieldInfo(WebType.UserID)
        };
      }
      if (javaType.equals(Integer.class) || javaType.equals(Integer.TYPE)) {
        return new WebFieldInfo[] {
            new WebFieldInfo(WebType.PrimaryKey)
            //, new WebFieldInfo(WebType.RoleID) 
          };
      }
      return new WebFieldInfo[] { new WebFieldInfo(WebType.PrimaryKey) };
    }
 
    if (javaType.isPrimitive()) {
      // @todo Completar LLista
      if (javaType.equals(Integer.TYPE) || javaType.equals(Short.TYPE)
          || javaType.equals(Long.TYPE) || javaType.equals(Byte.TYPE)) { 
         return new WebFieldInfo[] { new WebFieldInfo(WebType.Integer),
             new WebFieldInfo(WebType.Text),
             //new WebFieldInfo(WebType.RoleID),
             new WebFieldInfo(WebType.ComboBox)
         };
      }

      if (javaType.equals(Double.TYPE) || javaType.equals(Float.TYPE)) {
        return new WebFieldInfo[] { new WebFieldInfo(WebType.Decimal),
            new WebFieldInfo(WebType.Text),
            new WebFieldInfo(WebType.ComboBox) };
      }

      throw new Exception("getWebTypesFromJavaType(): Unknown primitive type "
          + javaType);
    }
    
    
    // @todo Completar Llista
    if (javaType.equals(Integer.class) || javaType.equals(Short.class)
        || javaType.equals(Long.class) || javaType.equals(Byte.class)
        || javaType.equals(BigInteger.class)) { 
       return new WebFieldInfo[] { new WebFieldInfo(WebType.Integer),
           new WebFieldInfo(WebType.Text),
           //new WebFieldInfo(WebType.RoleID),
           new WebFieldInfo(WebType.ComboBox)
       };
    }

    if (javaType.equals(Double.class) || javaType.equals(Float.class)
        || javaType.equals(BigDecimal.class)) {
      return new WebFieldInfo[] { new WebFieldInfo(WebType.Decimal),
          new WebFieldInfo(WebType.Text),
          new WebFieldInfo(WebType.ComboBox) };
    }

    WebFieldInfo webFile = new WebFieldInfo(WebType.File,
        new String[] {table + "_" + fi.javaName + "_files"});
    // //{ Text, TextArea,
    if (fi.getJavaType().equals(String.class) && fi.size > 250) {
        return new WebFieldInfo[] {
                 new WebFieldInfo(WebType.TextArea),
                 new WebFieldInfo(WebType.RichText),
                 new WebFieldInfo(WebType.Text),
                 webFile,
                 new WebFieldInfo(WebType.URL),
                 //new WebFieldInfo(WebType.UserID),
                 new WebFieldInfo(WebType.ComboBox)};
    }
    
    if (javaType.equals(InputStream.class)) {
      return new WebFieldInfo[] { webFile };
    }

    return new WebFieldInfo[] {
        new WebFieldInfo(WebType.Text),
        new WebFieldInfo(WebType.TextArea),
        new WebFieldInfo(WebType.RichText),        
        webFile,
        new WebFieldInfo(WebType.URL),
        //new WebFieldInfo(WebType.UserID),
        new WebFieldInfo(WebType.ComboBox)};
  }


}
