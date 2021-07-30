
package org.fundaciobit.genappsqltutorial.model.fields;
import org.fundaciobit.genapp.common.query.*;
public interface ShippersFields extends java.io.Serializable {

  public static final String _TABLE_SQL = "gas_shippers";


  public static final String _TABLE_MODEL = "shippers";


  public static final String _TABLE_TRANSLATION = _TABLE_MODEL + "." + _TABLE_MODEL;


	 public static final LongField SHIPPERID = new LongField(_TABLE_MODEL, "shipperid", "shipperid");  // PK
	 public static final StringField SHIPPERNAME = new StringField(_TABLE_MODEL, "shippername", "shippername");
	 public static final StringField PHONE = new StringField(_TABLE_MODEL, "phone", "phone");


  public static final Field<?>[] ALL_SHIPPERS_FIELDS = {
    SHIPPERID,
    SHIPPERNAME,
    PHONE
  };


  public static final Field<?>[] PRIMARYKEY_FIELDS = {
SHIPPERID
  };
}
