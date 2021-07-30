
package org.fundaciobit.genappsqltutorial.model.fields;
import org.fundaciobit.genapp.common.query.*;

public class CategoriesQueryPath extends org.fundaciobit.genapp.common.query.QueryPath {

  public CategoriesQueryPath() {
  }

  protected CategoriesQueryPath(QueryPath parentQueryPath) {
    super(parentQueryPath);
  }

  public LongField CATEGORYID() {
    return new LongField(getQueryPath(), CategoriesFields.CATEGORYID);
  }

  public StringField CATEGORYNAME() {
    return new StringField(getQueryPath(), CategoriesFields.CATEGORYNAME);
  }

  public StringField DESCRIPTION() {
    return new StringField(getQueryPath(), CategoriesFields.DESCRIPTION);
  }



  @Override
  public String getQueryPath() {
    return ((this.parentQueryPath == null) ? (CategoriesFields._TABLE_MODEL + ".")
        : this.parentQueryPath.getQueryPath());
  }


}
