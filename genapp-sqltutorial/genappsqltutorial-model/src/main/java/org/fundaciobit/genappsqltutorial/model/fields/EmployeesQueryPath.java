
package org.fundaciobit.genappsqltutorial.model.fields;
import org.fundaciobit.genapp.common.query.*;

public class EmployeesQueryPath extends org.fundaciobit.genapp.common.query.QueryPath {

  public EmployeesQueryPath() {
  }

  protected EmployeesQueryPath(QueryPath parentQueryPath) {
    super(parentQueryPath);
  }

  public LongField EMPLOYEEID() {
    return new LongField(getQueryPath(), EmployeesFields.EMPLOYEEID);
  }

  public StringField LASTNAME() {
    return new StringField(getQueryPath(), EmployeesFields.LASTNAME);
  }

  public StringField FIRSTNAME() {
    return new StringField(getQueryPath(), EmployeesFields.FIRSTNAME);
  }

  public DateField BIRTHDATE() {
    return new DateField(getQueryPath(), EmployeesFields.BIRTHDATE);
  }

  public StringField PHOTO() {
    return new StringField(getQueryPath(), EmployeesFields.PHOTO);
  }

  public StringField NOTES() {
    return new StringField(getQueryPath(), EmployeesFields.NOTES);
  }



  @Override
  public String getQueryPath() {
    return ((this.parentQueryPath == null) ? (EmployeesFields._TABLE_MODEL + ".")
        : this.parentQueryPath.getQueryPath());
  }


/* L'ús d'aquest camp (OneToMany) llança una exception:
 [Illegal attempt to dereference a collection]

 // TODO Solució dins el mètode testOneByOneDirect de la classe TestJPA 

  public OrdersQueryPath ORDERSS() {
    return new OrdersQueryPath(new QueryPath() {
      public String getQueryPath() {
          return EmployeesQueryPath.this.getQueryPath() + "orderss" + ".";
      }
    });
  }
*/

}
