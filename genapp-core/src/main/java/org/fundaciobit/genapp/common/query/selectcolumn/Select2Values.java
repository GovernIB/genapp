package org.fundaciobit.genapp.common.query.selectcolumn;

/**
 * 
 * @author anadal
 *
 * @param <A>
 * @param <B>
 */
public class Select2Values<A, B> extends Select1Values<A> implements ISelectNValues {

    protected B value2;

    public Select2Values() {
        super();
    }

    public Select2Values(A value1, B value2) {
        super(value1);
        this.value2 = value2;
    }

    public Select2Values(
            SelectNValues<A, B, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object> values) {
        this(values.getValue1(), values.getValue2());
    }

    public B getValue2() {
        return value2;
    }

    public void setValue2(B value2) {
        this.value2 = value2;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public SelectNValues getSelectNValues() {
        return new SelectNValues(value1, value2);
    }
}