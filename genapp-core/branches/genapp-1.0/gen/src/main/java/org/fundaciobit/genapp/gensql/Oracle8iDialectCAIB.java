package org.fundaciobit.genapp.gensql;

import java.sql.Types;

/**
 * 
 * @author anadal
 *
 */
public class Oracle8iDialectCAIB extends org.hibernate.dialect.Oracle8iDialect {

  
  
  protected void registerNumericTypeMappings() {
       super.registerNumericTypeMappings();  
       registerColumnType(Types.NUMERIC, "number" );
  }
  
  
}
