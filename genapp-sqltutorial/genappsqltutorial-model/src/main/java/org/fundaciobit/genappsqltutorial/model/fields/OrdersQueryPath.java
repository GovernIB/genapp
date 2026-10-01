
package org.fundaciobit.genappsqltutorial.model.fields;
import org.fundaciobit.genapp.common.query.*;

public class OrdersQueryPath extends org.fundaciobit.genapp.common.query.QueryPath {

  public OrdersQueryPath() {
  }

  protected OrdersQueryPath(QueryPath parentQueryPath) {
    super(parentQueryPath);
  }

  public LongField ORDERID() {
    return new LongField(getQueryPath(), OrdersFields.ORDERID);
  }

  public LongField CUSTOMERID() {
    return new LongField(getQueryPath(), OrdersFields.CUSTOMERID);
  }

  public LongField EMPLOYEEID() {
    return new LongField(getQueryPath(), OrdersFields.EMPLOYEEID);
  }

  public DateField ORDERDATE() {
    return new DateField(getQueryPath(), OrdersFields.ORDERDATE);
  }

  public LongField SHIPPERID() {
    return new LongField(getQueryPath(), OrdersFields.SHIPPERID);
  }



  @Override
  public String getQueryPath() {
    return ((this.parentQueryPath == null) ? (OrdersFields._TABLE_MODEL + ".")
        : this.parentQueryPath.getQueryPath());
  }


/* L'ús d'aquest camp (OneToMany) llança una exception:
 [Illegal attempt to dereference a collection]

 // TODO Solució dins el mètode testOneByOneDirect de la classe TestJPA 

  public OrderDetailsQueryPath ORDERDETAILSS() {
    return new OrderDetailsQueryPath(new QueryPath() {
      public String getQueryPath() {
          return OrdersQueryPath.this.getQueryPath() + "orderDetailss" + ".";
      }
    });
  }
*/

  public CustomersQueryPath CUSTOMERS() {
    return new CustomersQueryPath(new QueryPath() {
      public String getQueryPath() {
          return OrdersQueryPath.this.getQueryPath() + "customers" + ".";
      }
    });
  }

  public EmployeesQueryPath EMPLOYEES() {
    return new EmployeesQueryPath(new QueryPath() {
      public String getQueryPath() {
          return OrdersQueryPath.this.getQueryPath() + "employees" + ".";
      }
    });
  }

  public ShippersQueryPath SHIPPERS() {
    return new ShippersQueryPath(new QueryPath() {
      public String getQueryPath() {
          return OrdersQueryPath.this.getQueryPath() + "shippers" + ".";
      }
    });
  }

}
