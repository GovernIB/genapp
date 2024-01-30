
package org.fundaciobit.demogenapp.model.fields;
import org.fundaciobit.genapp.common.query.*;
public interface AssignaturaFields extends java.io.Serializable {

  public static final String _TABLE_SQL = "dem_assignatura";


  public static final String _TABLE_MODEL = "assignatura";


  public static final String _TABLE_TRANSLATION = _TABLE_MODEL + "." + _TABLE_MODEL;


	 public static final LongField ASSIGNATURAID = new LongField(_TABLE_MODEL, "assignaturaID", "assignaturaid");  // PK
	 public static final StringField NOM = new StringField(_TABLE_MODEL, "nom", "nom");
	 public static final IntegerField CREDITS = new IntegerField(_TABLE_MODEL, "credits", "credits");
	 public static final IntegerField DIASETMANA = new IntegerField(_TABLE_MODEL, "diaSetmana", "diasetmana");
	 public static final TimeField HORA = new TimeField(_TABLE_MODEL, "hora", "hora");
	 public static final StringField DESCRIPCIO = new StringField(_TABLE_MODEL, "descripcio", "descripcio");


  public static final Field<?>[] ALL_ASSIGNATURA_FIELDS = {
    ASSIGNATURAID,
    NOM,
    CREDITS,
    DIASETMANA,
    HORA,
    DESCRIPCIO
  };


  public static final Field<?>[] PRIMARYKEY_FIELDS = {
ASSIGNATURAID
  };
}
