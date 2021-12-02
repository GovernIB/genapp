
package org.fundaciobit.genappsqltutorial.model.fields;
import org.fundaciobit.genapp.common.query.*;

public class ProductsQueryPath extends org.fundaciobit.genapp.common.query.QueryPath {

  public ProductsQueryPath() {
  }

  protected ProductsQueryPath(QueryPath parentQueryPath) {
    super(parentQueryPath);
  }

  public LongField PRODUCTID() {
    return new LongField(getQueryPath(), ProductsFields.PRODUCTID);
  }

  public StringField PRODUCTNAME() {
    return new StringField(getQueryPath(), ProductsFields.PRODUCTNAME);
  }

  public LongField SUPPLIERID() {
    return new LongField(getQueryPath(), ProductsFields.SUPPLIERID);
  }

  public LongField CATEGORYID() {
    return new LongField(getQueryPath(), ProductsFields.CATEGORYID);
  }

  public StringField UNIT() {
    return new StringField(getQueryPath(), ProductsFields.UNIT);
  }

  public DoubleField PRICE() {
    return new DoubleField(getQueryPath(), ProductsFields.PRICE);
  }



  @Override
  public String getQueryPath() {
    return ((this.parentQueryPath == null) ? (ProductsFields._TABLE_MODEL + ".")
        : this.parentQueryPath.getQueryPath());
  }


/* L'ús d'aquest camp (OneToMany) llança una exception:
 [Illegal attempt to dereference a collection]

 // TODO Solució dins el mètode testOneByOneDirect de la classe TestJPA 

  public OrderDetailsQueryPath ORDERDETAILSS() {
    return new OrderDetailsQueryPath(new QueryPath() {
      public String getQueryPath() {
          return ProductsQueryPath.this.getQueryPath() + "orderDetailss" + ".";
      }
    });
  }
*/

}
