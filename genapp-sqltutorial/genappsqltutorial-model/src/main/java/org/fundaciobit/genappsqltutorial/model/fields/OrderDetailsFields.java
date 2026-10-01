
package org.fundaciobit.genappsqltutorial.model.fields;
import org.fundaciobit.genapp.common.query.*;
public interface OrderDetailsFields extends java.io.Serializable {

  public static final String _TABLE_SQL = "gas_orderdetails";


  public static final String _TABLE_MODEL = "orderDetails";


  public static final String _TABLE_TRANSLATION = _TABLE_MODEL + "." + _TABLE_MODEL;


	 public static final LongField ORDERDETAILID = new LongField(_TABLE_MODEL, "orderdetailid", "orderdetailid");  // PK
	 public static final LongField ORDERID = new LongField(_TABLE_MODEL, "orderid", "orderid");
	 public static final LongField PRODUCTID = new LongField(_TABLE_MODEL, "productid", "productid");
	 public static final IntegerField QUANTITY = new IntegerField(_TABLE_MODEL, "quantity", "quantity");


  public static final Field<?>[] ALL_ORDERDETAILS_FIELDS = {
    ORDERDETAILID,
    ORDERID,
    PRODUCTID,
    QUANTITY
  };


  public static final Field<?>[] PRIMARYKEY_FIELDS = {
ORDERDETAILID
  };
}
