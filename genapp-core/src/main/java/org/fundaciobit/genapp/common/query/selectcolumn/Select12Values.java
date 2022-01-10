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
public class Select12Values<A, B, C, D, E, F, G, H, I, J, K, L>
        extends Select11Values<A, B, C, D, E, F, G, H, I, J, K> implements ISelectNValues {

    protected L value12;

    public Select12Values(A value1, B value2, C value3, D value4, E value5, F value6, G value7,
            H value8, I value9, J value10, K value11, L value12) {
        super(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10,
                value11);
        this.value12 = value12;
    }

    public Select12Values(
            SelectNValues<A, B, C, D, E, F, G, H, I, J, K, L, Object, Object, Object> values) {
        this(values.getValue1(), values.getValue2(), values.getValue3(), values.getValue4(),
                values.getValue5(), values.getValue6(), values.getValue7(), values.getValue8(),
                values.getValue9(), values.getValue10(), values.getValue11(), values.getValue12());
    }

    public L getValue12() {
        return value12;
    }

    public void setValue12(L value12) {
        this.value12 = value12;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public SelectNValues getSelectNValues() {
        return new SelectNValues(value1, value2, value3, value4, value5, value6, value7, value8,
                value9, value10, value11, value12);
    }

}