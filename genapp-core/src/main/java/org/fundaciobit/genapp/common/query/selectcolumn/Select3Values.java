package org.fundaciobit.genapp.common.query.selectcolumn;

/**
 * 
 * @author anadal
 *
 * @param <A>
 * @param <B>
 * @param <C>
 */
public class Select3Values<A, B, C> extends Select2Values<A, B> implements ISelectNValues {

    protected C value3;

    public Select3Values() {
        super();
    }

    public Select3Values(A value1, B value2, C value3) {
        super(value1, value2);
        this.value3 = value3;
    }

    public Select3Values(
            SelectNValues<A, B, C, ?, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object> values) {
        this(values.getValue1(), values.getValue2(), values.getValue3());
    }

    public C getValue3() {
        return value3;
    }

    public void setValue3(C value3) {
        this.value3 = value3;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public SelectNValues getSelectNValues() {
        return new SelectNValues(value1, value2, value3);
    }

}