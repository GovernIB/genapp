
package org.fundaciobit.genappsqltutorial.model.fields;
import org.fundaciobit.genapp.common.query.*;
public interface CustomersFields extends java.io.Serializable {

  public static final String _TABLE_SQL = "gas_customers";


  public static final String _TABLE_MODEL = "customers";


  public static final String _TABLE_TRANSLATION = _TABLE_MODEL + "." + _TABLE_MODEL;


	 public static final LongField CUSTOMERID = new LongField(_TABLE_MODEL, "customerid", "customerid");  // PK
	 public static final StringField CUSTOMERNAME = new StringField(_TABLE_MODEL, "customername", "customername");
	 public static final StringField CONTACTNAME = new StringField(_TABLE_MODEL, "contactname", "contactname");
	 public static final StringField ADDRESS = new StringField(_TABLE_MODEL, "address", "address");
	 public static final StringField CITY = new StringField(_TABLE_MODEL, "city", "city");
	 public static final StringField COUNTRY = new StringField(_TABLE_MODEL, "country", "country");
	 public static final StringField POSTALCODE = new StringField(_TABLE_MODEL, "postalcode", "postalcode");


  public static final Field<?>[] ALL_CUSTOMERS_FIELDS = {
    CUSTOMERID,
    CUSTOMERNAME,
    CONTACTNAME,
    ADDRESS,
    CITY,
    COUNTRY,
    POSTALCODE
  };


  public static final Field<?>[] PRIMARYKEY_FIELDS = {
CUSTOMERID
  };
}
