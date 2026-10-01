package org.fundaciobit.genappsqltutorial.tutorial.utils;

/**
 * 
 * @author anadal
 *
 */
public class PartInfo implements Comparable<PartInfo> {

    protected String titol;

    protected String metode;

    protected String descripcio;

    protected String sql;

    protected String sourcecode;

    protected int order;

    public PartInfo(String title, String descripcio, String metode, String sql, String sourcecode, int order) {
        super();
        this.titol = title;
        this.descripcio = descripcio;
        this.metode = metode;
        this.sql = sql;
        this.sourcecode = sourcecode;
        this.order = order;
    }

    public String getMetode() {
        return metode;
    }

    public void setMetode(String metode) {
        this.metode = metode;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getSourcecode() {
        return sourcecode;
    }

    public String getTitol() {
        return titol;
    }

    public void setTitol(String titol) {
        this.titol = titol;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setSourcecode(String sourcecode) {
        this.sourcecode = sourcecode;
    }

    @Override
    public int compareTo(PartInfo o) {
        return Integer.compare(this.order, o.order);
    }

}
