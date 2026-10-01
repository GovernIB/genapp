
package org.fundaciobit.genappsqltutorial.model.fields;
import org.fundaciobit.genapp.common.query.*;

public class CustomersQueryPath extends org.fundaciobit.genapp.common.query.QueryPath {

  public CustomersQueryPath() {
  }

  protected CustomersQueryPath(QueryPath parentQueryPath) {
    super(parentQueryPath);
  }

  public LongField CUSTOMERID() {
    return new LongField(getQueryPath(), CustomersFields.CUSTOMERID);
  }

  public StringField CUSTOMERNAME() {
    return new StringField(getQueryPath(), CustomersFields.CUSTOMERNAME);
  }

  public StringField CONTACTNAME() {
    return new StringField(getQueryPath(), CustomersFields.CONTACTNAME);
  }

  public StringField ADDRESS() {
    return new StringField(getQueryPath(), CustomersFields.ADDRESS);
  }

  public StringField CITY() {
    return new StringField(getQueryPath(), CustomersFields.CITY);
  }

  public StringField COUNTRY() {
    return new StringField(getQueryPath(), CustomersFields.COUNTRY);
  }

  public StringField POSTALCODE() {
    return new StringField(getQueryPath(), CustomersFields.POSTALCODE);
  }



  @Override
  public String getQueryPath() {
    return ((this.parentQueryPath == null) ? (CustomersFields._TABLE_MODEL + ".")
        : this.parentQueryPath.getQueryPath());
  }


/* L'ús d'aquest camp (OneToMany) llança una exception:
 [Illegal attempt to dereference a collection]

 // TODO Solució dins el mètode testOneByOneDirect de la classe TestJPA 

  public OrdersQueryPath ORDERSS() {
    return new OrdersQueryPath(new QueryPath() {
      public String getQueryPath() {
          return CustomersQueryPath.this.getQueryPath() + "orderss" + ".";
      }
    });
  }
*/

}
