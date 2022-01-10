package org.fundaciobit.genapp.common.query.selectcolumn;

/**
 * 
 * @author anadal
 *
 * @param <A>
 */
public class Select1Values<A> implements ISelectNValues {

    protected A value1;

    
    public Select1Values() {
        super();
    }

    public Select1Values(A value1) {
        this.value1 = value1;            
    }

    public A getValue1() {
        return value1;
    }

    public void setValue1(A value1) {
        this.value1 = value1;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public SelectNValues getSelectNValues() {
        return new SelectNValues(value1);
    }

}