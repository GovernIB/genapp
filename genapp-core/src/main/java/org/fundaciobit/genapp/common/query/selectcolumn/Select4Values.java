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
public class Select4Values<A, B, C, D> extends Select3Values<A, B, C>
        implements ISelectNValues {

    protected D value4;

    public Select4Values(A value1, B value2, C value3, D value4) {
        super(value1, value2, value3);
        this.value4 = value4;
    }


    public Select4Values(SelectNValues<A, B, C, D, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object> values) {
        this(values.getValue1(), values.getValue2(), values.getValue3(), values.getValue4());
    }

    public D getValue4() {
        return value4;
    }

    public void setValue4(D value4) {
        this.value4 = value4;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public SelectNValues getSelectNValues() {
        return new SelectNValues(value1, value2, value3, value4);
    }
    
    

}