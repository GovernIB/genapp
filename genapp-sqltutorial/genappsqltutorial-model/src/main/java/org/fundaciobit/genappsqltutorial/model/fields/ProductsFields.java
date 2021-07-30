
package org.fundaciobit.genappsqltutorial.model.fields;
import org.fundaciobit.genapp.common.query.*;
public interface ProductsFields extends java.io.Serializable {

  public static final String _TABLE_SQL = "gas_products";


  public static final String _TABLE_MODEL = "products";


  public static final String _TABLE_TRANSLATION = _TABLE_MODEL + "." + _TABLE_MODEL;


	 public static final LongField PRODUCTID = new LongField(_TABLE_MODEL, "productid", "productid");  // PK
	 public static final StringField PRODUCTNAME = new StringField(_TABLE_MODEL, "productname", "productname");
	 public static final LongField SUPPLIERID = new LongField(_TABLE_MODEL, "supplierid", "supplierid");
	 public static final LongField CATEGORYID = new LongField(_TABLE_MODEL, "categoryid", "categoryid");
	 public static final StringField UNIT = new StringField(_TABLE_MODEL, "unit", "unit");
	 public static final IntegerField PRICE = new IntegerField(_TABLE_MODEL, "price", "price");


  public static final Field<?>[] ALL_PRODUCTS_FIELDS = {
    PRODUCTID,
    PRODUCTNAME,
    SUPPLIERID,
    CATEGORYID,
    UNIT,
    PRICE
  };


  public static final Field<?>[] PRIMARYKEY_FIELDS = {
PRODUCTID
  };
}
