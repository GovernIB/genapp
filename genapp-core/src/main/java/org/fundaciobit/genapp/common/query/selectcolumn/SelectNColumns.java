package org.fundaciobit.genapp.common.query.selectcolumn;

import java.util.Arrays;
import java.util.List;

import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genapp.common.query.Select;

/**
 * 
 * @author anadal
 *
 * @param <A>
 * @param <B>
 * @param <C>
 * @param <D>
 * @param <E>
 */
public class SelectNColumns<A, B, C, D, E, F, G, H, I, J, K, L, M, N, P> {
    protected final int columns;
    protected Select<A> select1;

    protected Select<B> select2;

    protected Select<C> select3;

    protected Select<D> select4;

    protected Select<E> select5;

    protected Select<F> select6;

    protected Select<G> select7;

    protected Select<H> select8;

    protected Select<I> select9;

    protected Select<J> select10;

    protected Select<K> select11;

    protected Select<L> select12;

    protected Select<M> select13;

    protected Select<N> select14;

    protected Select<P> select15;

    public SelectNColumns(Select<A> select1) {
        this.columns = 1;
        this.select1 = select1;
    }

    public SelectNColumns(Select<A> select1, Select<B> select2) {
        this.columns = 2;
        this.select1 = select1;
        this.select2 = select2;
    }

    public SelectNColumns(Select<A> select1, Select<B> select2, Select<C> select3) {
        this.columns = 3;
        this.select1 = select1;
        this.select2 = select2;
        this.select3 = select3;
    }

    public SelectNColumns(Select<A> select1, Select<B> select2, Select<C> select3,
            Select<D> select4) {
        this.columns = 4;
        this.select1 = select1;
        this.select2 = select2;
        this.select3 = select3;
        this.select4 = select4;
    }

    public SelectNColumns(Select<A> select1, Select<B> select2, Select<C> select3,
            Select<D> select4, Select<E> select5) {
        this.columns = 5;
        this.select1 = select1;
        this.select2 = select2;
        this.select3 = select3;
        this.select4 = select4;
        this.select5 = select5;
    }

    public SelectNColumns(Select<A> select1, Select<B> select2, Select<C> select3,
            Select<D> select4, Select<E> select5, Select<F> select6) {
        super();
        this.columns = 6;
        this.select1 = select1;
        this.select2 = select2;
        this.select3 = select3;
        this.select4 = select4;
        this.select5 = select5;
        this.select6 = select6;
        this.select7 = null;
        this.select8 = null;
        this.select9 = null;
        this.select10 = null;
        this.select11 = null;
        this.select12 = null;
        this.select13 = null;
        this.select14 = null;
        this.select15 = null;
    }

    public SelectNColumns(Select<A> select1, Select<B> select2, Select<C> select3,
            Select<D> select4, Select<E> select5, Select<F> select6, Select<G> select7) {
        super();
        this.columns = 7;
        this.select1 = select1;
        this.select2 = select2;
        this.select3 = select3;
        this.select4 = select4;
        this.select5 = select5;
        this.select6 = select6;
        this.select7 = select7;
        this.select8 = null;
        this.select9 = null;
        this.select10 = null;
        this.select11 = null;
        this.select12 = null;
        this.select13 = null;
        this.select14 = null;
        this.select15 = null;
    }

    public SelectNColumns(Select<A> select1, Select<B> select2, Select<C> select3,
            Select<D> select4, Select<E> select5, Select<F> select6, Select<G> select7,
            Select<H> select8) {
        super();
        this.columns = 8;
        this.select1 = select1;
        this.select2 = select2;
        this.select3 = select3;
        this.select4 = select4;
        this.select5 = select5;
        this.select6 = select6;
        this.select7 = select7;
        this.select8 = select8;
        this.select9 = null;
        this.select10 = null;
        this.select11 = null;
        this.select12 = null;
        this.select13 = null;
        this.select14 = null;
        this.select15 = null;
    }

    public SelectNColumns(Select<A> select1, Select<B> select2, Select<C> select3,
            Select<D> select4, Select<E> select5, Select<F> select6, Select<G> select7,
            Select<H> select8, Select<I> select9) {
        super();
        this.columns = 9;
        this.select1 = select1;
        this.select2 = select2;
        this.select3 = select3;
        this.select4 = select4;
        this.select5 = select5;
        this.select6 = select6;
        this.select7 = select7;
        this.select8 = select8;
        this.select9 = select9;
        this.select10 = null;
        this.select11 = null;
        this.select12 = null;
        this.select13 = null;
        this.select14 = null;
        this.select15 = null;
    }

    public SelectNColumns(Select<A> select1, Select<B> select2, Select<C> select3,
            Select<D> select4, Select<E> select5, Select<F> select6, Select<G> select7,
            Select<H> select8, Select<I> select9, Select<J> select10) {
        super();
        this.columns = 10;
        this.select1 = select1;
        this.select2 = select2;
        this.select3 = select3;
        this.select4 = select4;
        this.select5 = select5;
        this.select6 = select6;
        this.select7 = select7;
        this.select8 = select8;
        this.select9 = select9;
        this.select10 = select10;
        this.select11 = null;
        this.select12 = null;
        this.select13 = null;
        this.select14 = null;
        this.select15 = null;
    }

    public SelectNColumns(Select<A> select1, Select<B> select2, Select<C> select3,
            Select<D> select4, Select<E> select5, Select<F> select6, Select<G> select7,
            Select<H> select8, Select<I> select9, Select<J> select10, Select<K> select11) {
        super();
        this.columns = 11;
        this.select1 = select1;
        this.select2 = select2;
        this.select3 = select3;
        this.select4 = select4;
        this.select5 = select5;
        this.select6 = select6;
        this.select7 = select7;
        this.select8 = select8;
        this.select9 = select9;
        this.select10 = select10;
        this.select11 = select11;
        this.select12 = null;
        this.select13 = null;
        this.select14 = null;
        this.select15 = null;
    }

    public SelectNColumns(Select<A> select1, Select<B> select2, Select<C> select3,
            Select<D> select4, Select<E> select5, Select<F> select6, Select<G> select7,
            Select<H> select8, Select<I> select9, Select<J> select10, Select<K> select11,
            Select<L> select12) {
        super();
        this.columns = 12;
        this.select1 = select1;
        this.select2 = select2;
        this.select3 = select3;
        this.select4 = select4;
        this.select5 = select5;
        this.select6 = select6;
        this.select7 = select7;
        this.select8 = select8;
        this.select9 = select9;
        this.select10 = select10;
        this.select11 = select11;
        this.select12 = select12;
        this.select13 = null;
        this.select14 = null;
        this.select15 = null;
    }

    public SelectNColumns(Select<A> select1, Select<B> select2, Select<C> select3,
            Select<D> select4, Select<E> select5, Select<F> select6, Select<G> select7,
            Select<H> select8, Select<I> select9, Select<J> select10, Select<K> select11,
            Select<L> select12, Select<M> select13) {
        super();
        this.columns = 13;
        this.select1 = select1;
        this.select2 = select2;
        this.select3 = select3;
        this.select4 = select4;
        this.select5 = select5;
        this.select6 = select6;
        this.select7 = select7;
        this.select8 = select8;
        this.select9 = select9;
        this.select10 = select10;
        this.select11 = select11;
        this.select12 = select12;
        this.select13 = select13;
        this.select14 = null;
        this.select15 = null;
    }

    public SelectNColumns(Select<A> select1, Select<B> select2, Select<C> select3,
            Select<D> select4, Select<E> select5, Select<F> select6, Select<G> select7,
            Select<H> select8, Select<I> select9, Select<J> select10, Select<K> select11,
            Select<L> select12, Select<M> select13, Select<N> select14) {
        super();
        this.columns = 14;
        this.select1 = select1;
        this.select2 = select2;
        this.select3 = select3;
        this.select4 = select4;
        this.select5 = select5;
        this.select6 = select6;
        this.select7 = select7;
        this.select8 = select8;
        this.select9 = select9;
        this.select10 = select10;
        this.select11 = select11;
        this.select12 = select12;
        this.select13 = select13;
        this.select14 = select14;
        this.select15 = null;
    }

    public SelectNColumns(Select<A> select1, Select<B> select2, Select<C> select3,
            Select<D> select4, Select<E> select5, Select<F> select6, Select<G> select7,
            Select<H> select8, Select<I> select9, Select<J> select10, Select<K> select11,
            Select<L> select12, Select<M> select13, Select<N> select14, Select<P> select15) {
        super();
        this.columns = 15;
        this.select1 = select1;
        this.select2 = select2;
        this.select3 = select3;
        this.select4 = select4;
        this.select5 = select5;
        this.select6 = select6;
        this.select7 = select7;
        this.select8 = select8;
        this.select9 = select9;
        this.select10 = select10;
        this.select11 = select11;
        this.select12 = select12;
        this.select13 = select13;
        this.select14 = select14;
        this.select15 = select15;
    }

    protected static List<Select<?>> selectSelects(
            Select<?> ... selects 
            /*
            Select<?> select1, Select<?> select2, Select<?> select3, Select<?> select4,
            Select<?> select5 */) {
/*
        List<Select<?>> selects = new ArrayList<Select<?>>(count);

        switch (count) {

            case 15:
                selects.add(select5);
            case 14:
                selects.add(select4);
            case 13:
                selects.add(select3);
            case 12:
                selects.add(select2);
            case 11:
                selects.add(select1);
            case 10:
                selects.add(select5);
            case 9:
                selects.add(select4);
            case 8:
                selects.add(select3);
            case 7:
                selects.add(select2);
            case 6:
                selects.add(select1);
            case 5:
                selects.add(select5);
            case 4:
                selects.add(select4);
            case 3:
                selects.add(select3);
            case 2:
                selects.add(select2);
            case 1:
                selects.add(select1);

        }

        Collections.reverse(selects);
*/
        return Arrays.asList(selects);

    }

    protected SelectNValues<A, B, C, D, E, F, G, H, I, J, K, L, M, N, P> internalObjectArrayToI(
            Object[] objects) throws I18NException {

        A value1 = null;
        B value2 = null;
        C value3 = null;
        D value4 = null;
        E value5 = null;
        F value6 = null;
        G value7 = null;
        H value8 = null;
        I value9 = null;
        J value10 = null;
        K value11 = null;
        L value12 = null;
        M value13 = null;
        N value14 = null;
        P value15 = null;

        switch (columns) {
            case 15:
                value15 = select15.getFromObject(objects[14]);
            case 14:
                value14 = select14.getFromObject(objects[13]);
            case 13:
                value13 = select13.getFromObject(objects[12]);
            case 12:
                value12 = select12.getFromObject(objects[11]);
            case 11:
                value11 = select11.getFromObject(objects[10]);
            case 10:
                value10 = select10.getFromObject(objects[9]);
            case 9:
                value9 = select9.getFromObject(objects[8]);
            case 8:
                value8 = select8.getFromObject(objects[7]);
            case 7:
                value7 = select7.getFromObject(objects[6]);
            case 6:
                value6 = select6.getFromObject(objects[5]);
            case 5:
                value5 = select5.getFromObject(objects[4]);
            case 4:
                value4 = select4.getFromObject(objects[3]);
            case 3:
                value3 = select3.getFromObject(objects[2]);
            case 2:
                value2 = select2.getFromObject(objects[1]);
            case 1:
                value1 = select1.getFromObject(objects[0]);

        }

        return new SelectNValues<A, B, C, D, E, F, G, H, I, J, K, L, M, N, P>(columns, value1,
                value2, value3, value4, value5, value6, value7, value8, value9, value10, value11,
                value12, value13, value14, value15);
    }

}
