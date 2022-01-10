package org.fundaciobit.genapp.common.query.selectcolumn;

/**
 * 
 * @author anadal
 *
 * @param <A>
 * @param <B>
 * @param <C>
 * @param <D>
 */
public class Select6Values<A, B, C, D, E, F> extends Select5Values<A, B, C, D, E>
        implements ISelectNValues {

    protected F value6;

    public Select6Values(A value1, B value2, C value3, D value4, E value5, F value6) {
        super(value1, value2, value3, value4, value5);
        this.value6 = value6;
    }


    public Select6Values(SelectNValues<A, B, C, D, E, F, Object, Object, Object, Object, Object, Object, Object, Object, Object> values) {
        this(values.getValue1(), values.getValue2(), values.getValue3(), values.getValue4(), values.getValue5(), values.getValue6());
    }

    public F getValue6() {
        return value6;
    }

    public void setValue6(F value6) {
        this.value6 = value6;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public SelectNValues getSelectNValues() {
        return new SelectNValues(value1, value2, value3, value4, value5, value6);
    }
    
    

}