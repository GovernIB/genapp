
package org.fundaciobit.demogenapp.model.fields;
import org.fundaciobit.genapp.common.query.*;
public interface AssignaturaAlumneFields extends java.io.Serializable {

  public static final String _TABLE_SQL = "dem_assignaturaalumne";


  public static final String _TABLE_MODEL = "assignaturaAlumne";


  public static final String _TABLE_TRANSLATION = _TABLE_MODEL + "." + _TABLE_MODEL;


	 public static final LongField ID = new LongField(_TABLE_MODEL, "id", "id");  // PK
	 public static final LongField ALUMNEID = new LongField(_TABLE_MODEL, "alumneID", "alumneid");
	 public static final LongField ASSIGNATURAID = new LongField(_TABLE_MODEL, "assignaturaID", "assignaturaid");
	 public static final DoubleField NOTA = new DoubleField(_TABLE_MODEL, "nota", "nota");


  public static final Field<?>[] ALL_ASSIGNATURAALUMNE_FIELDS = {
    ID,
    ALUMNEID,
    ASSIGNATURAID,
    NOTA
  };


  public static final Field<?>[] PRIMARYKEY_FIELDS = {
ID
  };
}
