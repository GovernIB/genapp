
package org.fundaciobit.genappsqltutorial.model.fields;
import org.fundaciobit.genapp.common.query.*;

public class ShippersQueryPath extends org.fundaciobit.genapp.common.query.QueryPath {

  public ShippersQueryPath() {
  }

  protected ShippersQueryPath(QueryPath parentQueryPath) {
    super(parentQueryPath);
  }

  public LongField SHIPPERID() {
    return new LongField(getQueryPath(), ShippersFields.SHIPPERID);
  }

  public StringField SHIPPERNAME() {
    return new StringField(getQueryPath(), ShippersFields.SHIPPERNAME);
  }

  public StringField PHONE() {
    return new StringField(getQueryPath(), ShippersFields.PHONE);
  }



  @Override
  public String getQueryPath() {
    return ((this.parentQueryPath == null) ? (ShippersFields._TABLE_MODEL + ".")
        : this.parentQueryPath.getQueryPath());
  }


/* L'ús d'aquest camp (OneToMany) llança una exception:
 [Illegal attempt to dereference a collection]

 // TODO Solució dins el mètode testOneByOneDirect de la classe TestJPA 

  public OrdersQueryPath ORDERSS() {
    return new OrdersQueryPath(new QueryPath() {
      public String getQueryPath() {
          return ShippersQueryPath.this.getQueryPath() + "orderss" + ".";
      }
    });
  }
*/

}
