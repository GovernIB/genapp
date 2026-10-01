package org.fundaciobit.genapp.common.query;

import org.fundaciobit.genapp.common.i18n.I18NException;

/**
 * 
 * @author anadal
 * 
 */
public class ByteField extends AbstractIntegerField<Byte> {

  private static final long serialVersionUID = 529835444916614L;

  public ByteField(String table, String javaName, String sqlName) {
    super(table, javaName, sqlName, new ByteSelect(table, javaName));
  }
  
  public ByteField(String path, ByteField field) {
    super(null, path + field.javaName, null, new ByteSelect(null,path + field.javaName), field.codeLabel);
  }

  public static class ByteSelect extends SelectField<Byte> {

    public ByteSelect(String table, String sqlName) {
      super(table, sqlName);
    }

    @Override
    public Byte getFromObject(Object obj) throws I18NException {
      return (Byte) obj;
    }

  };
}
