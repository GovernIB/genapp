package org.fundaciobit.genapp.gensql;

import java.sql.Types;

/**
 * 
 * @author areus
 */
public class PostgreSQLDialectCAIB extends org.hibernate.dialect.PostgreSQLDialect {

    public PostgreSQLDialectCAIB() {
        registerColumnType( Types.NUMERIC, "numeric" );
    }

}
