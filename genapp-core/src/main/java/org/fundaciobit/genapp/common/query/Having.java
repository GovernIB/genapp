package org.fundaciobit.genapp.common.query;

/**
 * 
 * @author anadal
 *
 * @param <C>
 */
public class Having<C> extends Whereable<C> {

    public Having(String aggregateSql) {
        
        super(aggregateSql);
    }
    
    
}
