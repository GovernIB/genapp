package org.fundaciobit.genappsqltutorial.tutorial.utils;

import java.util.Comparator;
import java.util.List;

/**
 * 
 * @author anadal
 *
 */
public class UnitInfo implements Comparator<UnitInfo>, Comparable<UnitInfo> {

    protected String nomCurt;

    protected String index;

    protected String classe;

    protected String classeNom;

    protected String titol;

    protected String descripcio;

    protected String url;

    protected Class<?> cls;

    List<PartInfo> parts = null;

    public UnitInfo(Class<?> cls, String titol, String descripcio, String url) {
        super();
        this.cls = cls;
        this.index = cls.getSimpleName().substring(0, 5);

        this.nomCurt = cls.getSimpleName().substring(5);
        this.classe = cls.getName();
        this.classeNom = cls.getSimpleName();
        this.titol = titol;
        this.descripcio = descripcio;
        this.url = url;
    }

    public String getNomCurt() {
        return nomCurt;
    }

    public void setNomCurt(String nomCurt) {
        this.nomCurt = nomCurt;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public String getClasseNom() {
        return classeNom;
    }

    public void setClasseNom(String classeNom) {
        this.classeNom = classeNom;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Class<?> getCls() {
        return cls;
    }

    public void setCls(Class<?> cls) {
        this.cls = cls;
    }

    public List<PartInfo> getParts() {
        return parts;
    }

    public void setParts(List<PartInfo> parts) {
        this.parts = parts;
    }

    public String getTitol() {
        return titol;
    }

    public void setTitol(String titol) {
        this.titol = titol;
    }

    @Override
    public int compare(UnitInfo o1, UnitInfo o2) {
        return o1.getClasseNom().compareTo(o2.getClasseNom());
    }

    @Override
    public int compareTo(UnitInfo o) {

        return compare(this, o);
    }

}
