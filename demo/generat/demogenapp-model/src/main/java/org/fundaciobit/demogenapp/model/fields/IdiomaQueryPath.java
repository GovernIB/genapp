
package org.fundaciobit.demogenapp.model.fields;
import org.fundaciobit.genapp.common.query.*;

public class IdiomaQueryPath extends org.fundaciobit.genapp.common.query.QueryPath {

  public IdiomaQueryPath() {
  }

  protected IdiomaQueryPath(QueryPath parentQueryPath) {
    super(parentQueryPath);
  }

  public StringField IDIOMAID() {
    return new StringField(getQueryPath(), IdiomaFields.IDIOMAID);
  }

  public StringField NOM() {
    return new StringField(getQueryPath(), IdiomaFields.NOM);
  }

  public BooleanField SUPORTAT() {
    return new BooleanField(getQueryPath(), IdiomaFields.SUPORTAT);
  }

  public IntegerField ORDRE() {
    return new IntegerField(getQueryPath(), IdiomaFields.ORDRE);
  }



  @Override
  public String getQueryPath() {
    return ((this.parentQueryPath == null) ? (IdiomaFields._TABLE_MODEL + ".")
        : this.parentQueryPath.getQueryPath());
  }


/* L'ús d'aquest camp (OneToMany) llança una exception:
 [Illegal attempt to dereference a collection]

 // TODO Solució dins el mètode testOneByOneDirect de la classe TestJPA 

  public AlumneQueryPath ALUMNES() {
    return new AlumneQueryPath(new QueryPath() {
      public String getQueryPath() {
          return IdiomaQueryPath.this.getQueryPath() + "alumnes" + ".";
      }
    });
  }
*/

}
