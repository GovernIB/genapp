
package org.fundaciobit.demogenapp.model.fields;
import org.fundaciobit.genapp.common.query.*;

public class AssignaturaQueryPath extends org.fundaciobit.genapp.common.query.QueryPath {

  public AssignaturaQueryPath() {
  }

  protected AssignaturaQueryPath(QueryPath parentQueryPath) {
    super(parentQueryPath);
  }

  public LongField ASSIGNATURAID() {
    return new LongField(getQueryPath(), AssignaturaFields.ASSIGNATURAID);
  }

  public StringField NOM() {
    return new StringField(getQueryPath(), AssignaturaFields.NOM);
  }

  public IntegerField CREDITS() {
    return new IntegerField(getQueryPath(), AssignaturaFields.CREDITS);
  }

  public IntegerField DIASETMANA() {
    return new IntegerField(getQueryPath(), AssignaturaFields.DIASETMANA);
  }

  public TimeField HORA() {
    return new TimeField(getQueryPath(), AssignaturaFields.HORA);
  }

  public StringField DESCRIPCIO() {
    return new StringField(getQueryPath(), AssignaturaFields.DESCRIPCIO);
  }



  @Override
  public String getQueryPath() {
    return ((this.parentQueryPath == null) ? (AssignaturaFields._TABLE_MODEL + ".")
        : this.parentQueryPath.getQueryPath());
  }


/* L'ús d'aquest camp (OneToMany) llança una exception:
 [Illegal attempt to dereference a collection]

 // TODO Solució dins el mètode testOneByOneDirect de la classe TestJPA 

  public AssignaturaAlumneQueryPath ASSIGNATURAALUMNES() {
    return new AssignaturaAlumneQueryPath(new QueryPath() {
      public String getQueryPath() {
          return AssignaturaQueryPath.this.getQueryPath() + "assignaturaAlumnes" + ".";
      }
    });
  }
*/

}
