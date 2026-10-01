
package org.fundaciobit.genappsqltutorial.model.fields;
import org.fundaciobit.genapp.common.query.*;

public class OrderDetailsQueryPath extends org.fundaciobit.genapp.common.query.QueryPath {

  public OrderDetailsQueryPath() {
  }

  protected OrderDetailsQueryPath(QueryPath parentQueryPath) {
    super(parentQueryPath);
  }

  public LongField ORDERDETAILID() {
    return new LongField(getQueryPath(), OrderDetailsFields.ORDERDETAILID);
  }

  public LongField ORDERID() {
    return new LongField(getQueryPath(), OrderDetailsFields.ORDERID);
  }

  public LongField PRODUCTID() {
    return new LongField(getQueryPath(), OrderDetailsFields.PRODUCTID);
  }

  public IntegerField QUANTITY() {
    return new IntegerField(getQueryPath(), OrderDetailsFields.QUANTITY);
  }



  @Override
  public String getQueryPath() {
    return ((this.parentQueryPath == null) ? (OrderDetailsFields._TABLE_MODEL + ".")
        : this.parentQueryPath.getQueryPath());
  }


  public OrdersQueryPath ORDERS() {
    return new OrdersQueryPath(new QueryPath() {
      public String getQueryPath() {
          return OrderDetailsQueryPath.this.getQueryPath() + "orders" + ".";
      }
    });
  }

  public ProductsQueryPath PRODUCTS() {
    return new ProductsQueryPath(new QueryPath() {
      public String getQueryPath() {
          return OrderDetailsQueryPath.this.getQueryPath() + "products" + ".";
      }
    });
  }

}
