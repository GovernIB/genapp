
package org.fundaciobit.genappsqltutorial.model.fields;
import org.fundaciobit.genapp.common.query.*;
public interface SuppliersFields extends java.io.Serializable {

  public static final String _TABLE_SQL = "gas_suppliers";


  public static final String _TABLE_MODEL = "suppliers";


  public static final String _TABLE_TRANSLATION = _TABLE_MODEL + "." + _TABLE_MODEL;


	 public static final LongField SUPPLIERID = new LongField(_TABLE_MODEL, "supplierid", "supplierid");  // PK
	 public static final StringField SUPPLIERNAME = new StringField(_TABLE_MODEL, "suppliername", "suppliername");
	 public static final StringField CONTACTNAME = new StringField(_TABLE_MODEL, "contactname", "contactname");
	 public static final StringField ADDRESS = new StringField(_TABLE_MODEL, "address", "address");
	 public static final StringField CITY = new StringField(_TABLE_MODEL, "city", "city");
	 public static final StringField POSTALCODE = new StringField(_TABLE_MODEL, "postalcode", "postalcode");
	 public static final StringField COUNTRY = new StringField(_TABLE_MODEL, "country", "country");
	 public static final StringField PHONE = new StringField(_TABLE_MODEL, "phone", "phone");


  public static final Field<?>[] ALL_SUPPLIERS_FIELDS = {
    SUPPLIERID,
    SUPPLIERNAME,
    CONTACTNAME,
    ADDRESS,
    CITY,
    POSTALCODE,
    COUNTRY,
    PHONE
  };


  public static final Field<?>[] PRIMARYKEY_FIELDS = {
SUPPLIERID
  };
}
