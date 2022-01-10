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
public class Select7Values<A, B, C, D, E, F, G> extends Select6Values<A, B, C, D, E, F>
        implements ISelectNValues {

    protected G value7;

    public Select7Values(A value1, B value2, C value3, D value4, E value5, F value6, G value7) {
        super(value1, value2, value3, value4, value5, value6);
        this.value7 = value7;
    }


    public Select7Values(SelectNValues<A, B, C, D, E, F, G, Object, Object, Object, Object, Object, Object, Object, Object> values) {
        this(values.getValue1(), values.getValue2(), values.getValue3(), values.getValue4(),
                values.getValue5(), values.getValue6(), values.getValue7());
    }

    public G getValue7() {
        return value7;
    }

    public void setValue7(G value7) {
        this.value7 = value7;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public SelectNValues getSelectNValues() {
        return new SelectNValues(value1, value2, value3, value4, value5, value6, value7);
    }
    
    

}