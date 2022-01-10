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
public class Select13Values<A, B, C, D, E, F, G, H, I, J, K, L, M>
        extends Select12Values<A, B, C, D, E, F, G, H, I, J, K, L> implements ISelectNValues {

    protected M value13;

    public Select13Values(A value1, B value2, C value3, D value4, E value5, F value6, G value7,
            H value8, I value9, J value10, K value11, L value12, M value13) {
        super(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10,
                value11, value12);
        this.value13 = value13;
    }

    public Select13Values(
            SelectNValues<A, B, C, D, E, F, G, H, I, J, K, L, M, Object, Object> values) {
        this(values.getValue1(), values.getValue2(), values.getValue3(), values.getValue4(),
                values.getValue5(), values.getValue6(), values.getValue7(), values.getValue8(),
                values.getValue9(), values.getValue10(), values.getValue11(), values.getValue12(),
                values.getValue13());
    }



    public M getValue13() {
        return value13;
    }

    public void setValue13(M value13) {
        this.value13 = value13;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public SelectNValues getSelectNValues() {
        return new SelectNValues(value1, value2, value3, value4, value5, value6, value7, value8,
                value9, value10, value11, value12, value13);
    }

}