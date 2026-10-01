
package org.fundaciobit.genappsqltutorial.model.fields;
import org.fundaciobit.genapp.common.query.*;
public interface CategoriesFields extends java.io.Serializable {

  public static final String _TABLE_SQL = "gas_categories";


  public static final String _TABLE_MODEL = "categories";


  public static final String _TABLE_TRANSLATION = _TABLE_MODEL + "." + _TABLE_MODEL;


	 public static final LongField CATEGORYID = new LongField(_TABLE_MODEL, "categoryid", "categoryid");  // PK
	 public static final StringField CATEGORYNAME = new StringField(_TABLE_MODEL, "categoryName", "categoryname");
	 public static final StringField DESCRIPTION = new StringField(_TABLE_MODEL, "description", "description");


  public static final Field<?>[] ALL_CATEGORIES_FIELDS = {
    CATEGORYID,
    CATEGORYNAME,
    DESCRIPTION
  };


  public static final Field<?>[] PRIMARYKEY_FIELDS = {
CATEGORYID
  };
}
