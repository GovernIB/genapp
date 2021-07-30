
package org.fundaciobit.genappsqltutorial.model.fields;
import org.fundaciobit.genapp.common.query.*;

public class SuppliersQueryPath extends org.fundaciobit.genapp.common.query.QueryPath {

  public SuppliersQueryPath() {
  }

  protected SuppliersQueryPath(QueryPath parentQueryPath) {
    super(parentQueryPath);
  }

  public LongField SUPPLIERID() {
    return new LongField(getQueryPath(), SuppliersFields.SUPPLIERID);
  }

  public StringField SUPPLIERNAME() {
    return new StringField(getQueryPath(), SuppliersFields.SUPPLIERNAME);
  }

  public StringField CONTACTNAME() {
    return new StringField(getQueryPath(), SuppliersFields.CONTACTNAME);
  }

  public StringField ADDRESS() {
    return new StringField(getQueryPath(), SuppliersFields.ADDRESS);
  }

  public StringField CITY() {
    return new StringField(getQueryPath(), SuppliersFields.CITY);
  }

  public StringField POSTALCODE() {
    return new StringField(getQueryPath(), SuppliersFields.POSTALCODE);
  }

  public StringField COUNTRY() {
    return new StringField(getQueryPath(), SuppliersFields.COUNTRY);
  }

  public StringField PHONE() {
    return new StringField(getQueryPath(), SuppliersFields.PHONE);
  }



  @Override
  public String getQueryPath() {
    return ((this.parentQueryPath == null) ? (SuppliersFields._TABLE_MODEL + ".")
        : this.parentQueryPath.getQueryPath());
  }


}
