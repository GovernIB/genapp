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
public class Select11Values<A, B, C, D, E, F, G, H, I, J, K>
        extends Select10Values<A, B, C, D, E, F, G, H, I, J> implements ISelectNValues {

    protected K value11;

    public Select11Values(A value1, B value2, C value3, D value4, E value5, F value6, G value7,
            H value8, I value9, J value10, K value11) {
        super(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10);
        this.value11 = value11;
    }

    public Select11Values(
            SelectNValues<A, B, C, D, E, F, G, H, I, J, K, Object, Object, Object, Object> values) {
        this(values.getValue1(), values.getValue2(), values.getValue3(), values.getValue4(),
                values.getValue5(), values.getValue6(), values.getValue7(), values.getValue8(),
                values.getValue9(), values.getValue10(), values.getValue11());
    }

    public K getValue11() {
        return value11;
    }

    public void setValue11(K value11) {
        this.value11 = value11;
    }

    @SuppressWarnings({ "rawtypes" })
    @Override
    public SelectNValues getSelectNValues() {
        return new SelectNValues(value1, value2, value3, value4, value5, value6, value7, value8,
                value9, value10, value11);
    }

}