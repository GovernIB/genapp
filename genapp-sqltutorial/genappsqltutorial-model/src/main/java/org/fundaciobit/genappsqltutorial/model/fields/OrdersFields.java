
package org.fundaciobit.genappsqltutorial.model.fields;
import org.fundaciobit.genapp.common.query.*;
public interface OrdersFields extends java.io.Serializable {

  public static final String _TABLE_SQL = "gas_orders";


  public static final String _TABLE_MODEL = "orders";


  public static final String _TABLE_TRANSLATION = _TABLE_MODEL + "." + _TABLE_MODEL;


	 public static final LongField ORDERID = new LongField(_TABLE_MODEL, "orderid", "orderid");  // PK
	 public static final LongField CUSTOMERID = new LongField(_TABLE_MODEL, "customerid", "customerid");
	 public static final LongField EMPLOYEEID = new LongField(_TABLE_MODEL, "employeeid", "employeeid");
	 public static final DateField ORDERDATE = new DateField(_TABLE_MODEL, "orderdate", "orderdate");
	 public static final LongField SHIPPERID = new LongField(_TABLE_MODEL, "shipperid", "shipperid");


  public static final Field<?>[] ALL_ORDERS_FIELDS = {
    ORDERID,
    CUSTOMERID,
    EMPLOYEEID,
    ORDERDATE,
    SHIPPERID
  };


  public static final Field<?>[] PRIMARYKEY_FIELDS = {
ORDERID
  };
}
