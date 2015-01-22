package org.fundaciobit.genapp.gensql;

import java.sql.Types;

/**
 * 
 * @author anadal
 *
 */
public class Oracle9iDialectCAIB extends org.hibernate.dialect.Oracle9iDialect {

  
  
  protected void registerNumericTypeMappings() {
       super.registerNumericTypeMappings();  
       registerColumnType(Types.NUMERIC, "number" );
  }
  
  
}
