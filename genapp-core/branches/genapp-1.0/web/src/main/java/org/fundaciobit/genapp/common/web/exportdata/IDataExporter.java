package org.fundaciobit.genapp.common.web.exportdata;

import java.util.List;
import java.util.Map;

import org.fundaciobit.genapp.common.IGenAppEntity;
import org.fundaciobit.genapp.common.query.Field;
import org.fundaciobit.genapp.common.web.form.BaseFilterForm;


/**
 * 
 * @author anadal
 */
public interface IDataExporter {
  
  public String getID();

  public String getName();

  public DataExported getIcon();

  public DataExported exportList(BaseFilterForm filterForm,
      List<? extends IGenAppEntity> list, Field<?>[] allFields,
      Map<Field<?>,Map<String,String>> mapValuesByField, Field<?>[] primaryKeys)  throws Exception;

}
