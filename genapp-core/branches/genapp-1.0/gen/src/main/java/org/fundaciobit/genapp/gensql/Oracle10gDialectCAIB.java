package org.fundaciobit.genapp.gensql;

import java.sql.Types;

/**
 * 
 * @author anadal
 *
 */
public class Oracle10gDialectCAIB extends org.hibernate.dialect.Oracle10gDialect {

  
  
  protected void registerNumericTypeMappings() {
       super.registerNumericTypeMappings();  
       registerColumnType(Types.NUMERIC, "number" );
  }
  
  
}
