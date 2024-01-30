
package org.fundaciobit.demogenapp.model.fields;
import org.fundaciobit.genapp.common.query.*;

public class AssignaturaAlumneQueryPath extends org.fundaciobit.genapp.common.query.QueryPath {

  public AssignaturaAlumneQueryPath() {
  }

  protected AssignaturaAlumneQueryPath(QueryPath parentQueryPath) {
    super(parentQueryPath);
  }

  public LongField ID() {
    return new LongField(getQueryPath(), AssignaturaAlumneFields.ID);
  }

  public LongField ALUMNEID() {
    return new LongField(getQueryPath(), AssignaturaAlumneFields.ALUMNEID);
  }

  public LongField ASSIGNATURAID() {
    return new LongField(getQueryPath(), AssignaturaAlumneFields.ASSIGNATURAID);
  }

  public DoubleField NOTA() {
    return new DoubleField(getQueryPath(), AssignaturaAlumneFields.NOTA);
  }



  @Override
  public String getQueryPath() {
    return ((this.parentQueryPath == null) ? (AssignaturaAlumneFields._TABLE_MODEL + ".")
        : this.parentQueryPath.getQueryPath());
  }


  public AlumneQueryPath ALUMNE() {
    return new AlumneQueryPath(new QueryPath() {
      public String getQueryPath() {
          return AssignaturaAlumneQueryPath.this.getQueryPath() + "alumne" + ".";
      }
    });
  }

  public AssignaturaQueryPath ASSIGNATURA() {
    return new AssignaturaQueryPath(new QueryPath() {
      public String getQueryPath() {
          return AssignaturaAlumneQueryPath.this.getQueryPath() + "assignatura" + ".";
      }
    });
  }

}
