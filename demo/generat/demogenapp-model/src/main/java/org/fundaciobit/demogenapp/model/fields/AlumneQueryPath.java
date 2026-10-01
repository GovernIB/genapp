
package org.fundaciobit.demogenapp.model.fields;
import org.fundaciobit.genapp.common.query.*;

public class AlumneQueryPath extends org.fundaciobit.genapp.common.query.QueryPath {

  public AlumneQueryPath() {
  }

  protected AlumneQueryPath(QueryPath parentQueryPath) {
    super(parentQueryPath);
  }

  public LongField ALUMNEID() {
    return new LongField(getQueryPath(), AlumneFields.ALUMNEID);
  }

  public StringField NOM() {
    return new StringField(getQueryPath(), AlumneFields.NOM);
  }

  public StringField IDIOMAID() {
    return new StringField(getQueryPath(), AlumneFields.IDIOMAID);
  }

  public DateField DATANAIXEMENT() {
    return new DateField(getQueryPath(), AlumneFields.DATANAIXEMENT);
  }

  public BooleanField SEXE() {
    return new BooleanField(getQueryPath(), AlumneFields.SEXE);
  }

  public BooleanField ACTIU() {
    return new BooleanField(getQueryPath(), AlumneFields.ACTIU);
  }

  public TimestampField DARRERACCES() {
    return new TimestampField(getQueryPath(), AlumneFields.DARRERACCES);
  }

  public LongField FOTOID() {
    return new LongField(getQueryPath(), AlumneFields.FOTOID);
  }

  public LongField TITOLACADEMICID() {
    return new LongField(getQueryPath(), AlumneFields.TITOLACADEMICID);
  }

  public TimeField DESPERTADOR() {
    return new TimeField(getQueryPath(), AlumneFields.DESPERTADOR);
  }

  public StringField PAGINAWEB() {
    return new StringField(getQueryPath(), AlumneFields.PAGINAWEB);
  }

  public StringField DESCRIPCIO() {
    return new StringField(getQueryPath(), AlumneFields.DESCRIPCIO);
  }



  @Override
  public String getQueryPath() {
    return ((this.parentQueryPath == null) ? (AlumneFields._TABLE_MODEL + ".")
        : this.parentQueryPath.getQueryPath());
  }


/* L'ús d'aquest camp (OneToMany) llança una exception:
 [Illegal attempt to dereference a collection]

 // TODO Solució dins el mètode testOneByOneDirect de la classe TestJPA 

  public AssignaturaAlumneQueryPath ASSIGNATURAALUMNES() {
    return new AssignaturaAlumneQueryPath(new QueryPath() {
      public String getQueryPath() {
          return AlumneQueryPath.this.getQueryPath() + "assignaturaAlumnes" + ".";
      }
    });
  }
*/

  public IdiomaQueryPath IDIOMA() {
    return new IdiomaQueryPath(new QueryPath() {
      public String getQueryPath() {
          return AlumneQueryPath.this.getQueryPath() + "idioma" + ".";
      }
    });
  }

  public FitxerQueryPath FOTO() {
    return new FitxerQueryPath(new QueryPath() {
      public String getQueryPath() {
          return AlumneQueryPath.this.getQueryPath() + "foto" + ".";
      }
    });
  }

  public TraduccioQueryPath TITOLACADEMIC() {
    return new TraduccioQueryPath(new QueryPath() {
      public String getQueryPath() {
          return AlumneQueryPath.this.getQueryPath() + "titolAcademic" + ".";
      }
    });
  }

}
