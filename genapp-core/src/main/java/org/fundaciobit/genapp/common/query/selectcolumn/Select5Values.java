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
public class Select5Values<A, B, C, D, E> extends Select4Values<A, B, C, D>
        implements ISelectNValues {

    protected E value5;

    public Select5Values(A value1, B value2, C value3, D value4, E value5) {
        super(value1, value2, value3, value4);
        this.value5 = value5;
    }


    public Select5Values(SelectNValues<A, B, C, D, E, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object> values) {
        this(values.getValue1(), values.getValue2(), values.getValue3(), values.getValue4(), values.getValue5());
    }

    public E getValue5() {
        return value5;
    }

    public void setValue5(E value5) {
        this.value5 = value5;
    }
    
    @SuppressWarnings({ "rawtypes" })
    @Override
    public SelectNValues getSelectNValues() {
        return new SelectNValues(value1, value2, value3, value4, value5);
    }
    
    

}