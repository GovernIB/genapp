package org.fundaciobit.genapp.common.query;

/**
 * Interface per representar Funciona Agregades:     AVG, COUNT, MIN, MAX i SUM
 * @author anadal
 *
 */
public interface AggregateFunction<C> {
    
    Having<C> having();

}
