
package org.fundaciobit.genappsqltutorial.model.fields;
import org.fundaciobit.genapp.common.query.*;
public interface EmployeesFields extends java.io.Serializable {

  public static final String _TABLE_SQL = "gas_employees";


  public static final String _TABLE_MODEL = "employees";


  public static final String _TABLE_TRANSLATION = _TABLE_MODEL + "." + _TABLE_MODEL;


	 public static final LongField EMPLOYEEID = new LongField(_TABLE_MODEL, "employeeid", "employeeid");  // PK
	 public static final StringField LASTNAME = new StringField(_TABLE_MODEL, "lastname", "lastname");
	 public static final StringField FIRSTNAME = new StringField(_TABLE_MODEL, "firstname", "firstname");
	 public static final DateField BIRTHDATE = new DateField(_TABLE_MODEL, "birthdate", "birthdate");
	 public static final StringField PHOTO = new StringField(_TABLE_MODEL, "photo", "photo");
	 public static final StringField NOTES = new StringField(_TABLE_MODEL, "notes", "notes");


  public static final Field<?>[] ALL_EMPLOYEES_FIELDS = {
    EMPLOYEEID,
    LASTNAME,
    FIRSTNAME,
    BIRTHDATE,
    PHOTO,
    NOTES
  };


  public static final Field<?>[] PRIMARYKEY_FIELDS = {
EMPLOYEEID
  };
}
