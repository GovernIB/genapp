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
public class Select9Values<A, B, C, D, E, F, G, H, I> extends Select8Values<A, B, C, D, E, F, G, H>
        implements ISelectNValues {

    protected I value9;

    public Select9Values(A value1, B value2, C value3, D value4, E value5, F value6, G value7, H value8, I value9) {
        super(value1, value2, value3, value4, value5, value6, value7, value8);
        this.value9 = value9;
    }


    public Select9Values(SelectNValues<A, B, C, D, E, F, G, H, I, Object, Object, Object, Object, Object, Object> values) {
        this(values.getValue1(), values.getValue2(), values.getValue3(), values.getValue4(),
                values.getValue5(), values.getValue6(), values.getValue7(), values.getValue8(), values.getValue9());
    }

    public I getValue9() {
        return value9;
    }

    public void setValue9(I value9) {
        this.value9 = value9;
    }
    
    @SuppressWarnings({ "rawtypes" })
    @Override
    public SelectNValues getSelectNValues() {
        return new SelectNValues(value1, value2, value3, value4, value5, value6, value7, value8, value9);
    }
    
    

}