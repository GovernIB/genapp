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
public class Select8Values<A, B, C, D, E, F, G, H> extends Select7Values<A, B, C, D, E, F, G>
        implements ISelectNValues {

    protected H value8;

    public Select8Values(A value1, B value2, C value3, D value4, E value5, F value6, G value7, H value8) {
        super(value1, value2, value3, value4, value5, value6, value7);
        this.value8 = value8;
    }


    public Select8Values(SelectNValues<A, B, C, D, E, F, G, H, Object, Object, Object, Object, Object, Object, Object> values) {
        this(values.getValue1(), values.getValue2(), values.getValue3(), values.getValue4(),
                values.getValue5(), values.getValue6(), values.getValue7(), values.getValue8());
    }

    public H getValue8() {
        return value8;
    }

    public void setValue8(H value8) {
        this.value8 = value8;
    }
    
    @SuppressWarnings({ "rawtypes"})
    @Override
    public SelectNValues getSelectNValues() {
        return new SelectNValues(value1, value2, value3, value4, value5, value6, value7, value8);
    }
    
    

}