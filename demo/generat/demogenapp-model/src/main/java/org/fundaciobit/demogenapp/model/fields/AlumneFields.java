
package org.fundaciobit.demogenapp.model.fields;
import org.fundaciobit.genapp.common.query.*;
public interface AlumneFields extends java.io.Serializable {

  public static final String _TABLE_SQL = "dem_alumne";


  public static final String _TABLE_MODEL = "alumne";


  public static final String _TABLE_TRANSLATION = _TABLE_MODEL + "." + _TABLE_MODEL;


	 public static final LongField ALUMNEID = new LongField(_TABLE_MODEL, "alumneID", "alumneid");  // PK
	 public static final StringField NOM = new StringField(_TABLE_MODEL, "nom", "nom");
	 public static final StringField IDIOMAID = new StringField(_TABLE_MODEL, "idiomaID", "idiomaid");
	 public static final DateField DATANAIXEMENT = new DateField(_TABLE_MODEL, "dataNaixement", "datanaixement");
	 public static final BooleanField SEXE = new BooleanField(_TABLE_MODEL, "sexe", "sexe");
	 public static final BooleanField ACTIU = new BooleanField(_TABLE_MODEL, "actiu", "actiu");
	 public static final TimestampField DARRERACCES = new TimestampField(_TABLE_MODEL, "darrerAcces", "darreracces");
	 public static final LongField FOTOID = new LongField(_TABLE_MODEL, "fotoID", "fotoid");
	 public static final LongField TITOLACADEMICID = new LongField(_TABLE_MODEL, "titolAcademicID", "titolacademicid");
	 public static final TimeField DESPERTADOR = new TimeField(_TABLE_MODEL, "despertador", "despertador");
	 public static final StringField PAGINAWEB = new StringField(_TABLE_MODEL, "paginaWeb", "paginaweb");
	 public static final StringField DESCRIPCIO = new StringField(_TABLE_MODEL, "descripcio", "descripcio");


  public static final Field<?>[] ALL_ALUMNE_FIELDS = {
    ALUMNEID,
    NOM,
    IDIOMAID,
    DATANAIXEMENT,
    SEXE,
    ACTIU,
    DARRERACCES,
    FOTOID,
    TITOLACADEMICID,
    DESPERTADOR,
    PAGINAWEB,
    DESCRIPCIO
  };


  public static final Field<?>[] PRIMARYKEY_FIELDS = {
ALUMNEID
  };
}
