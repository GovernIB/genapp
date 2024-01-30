
package org.fundaciobit.demogenapp.persistence;
import org.fundaciobit.demogenapp.model.entity.*;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.Set;
import java.util.HashMap;
import org.hibernate.annotations.Cascade;
import java.util.HashSet;
import javax.persistence.GenerationType;
import javax.persistence.Index;
import javax.persistence.GeneratedValue;
import javax.persistence.SequenceGenerator;
import java.util.Map;
import javax.persistence.FetchType;
import javax.persistence.ElementCollection;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import javax.persistence.Id;


@Entity(name = "TraduccioJPA")
@Table(name = "dem_traduccio" , indexes = { 
        @Index(name="dem_traduccio_pk_i", columnList = "traduccioid")})
@SequenceGenerator(name="TRADUCCIO_SEQ", sequenceName="dem_traduccio_seq", allocationSize=1, initialValue=1000)
@javax.xml.bind.annotation.XmlRootElement
public class TraduccioJPA implements Traduccio {

  /**  */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="TRADUCCIO_SEQ")
    @Column(name="traduccioid",nullable = false,length = 19)
    long traduccioID;



  /** Constructor Buit */
  public TraduccioJPA() {
  }

  /** Constructor amb tots els camps  */
  public TraduccioJPA(long traduccioID) {
    this.traduccioID=traduccioID;
}
  public TraduccioJPA(Traduccio __bean) {
    this.setTraduccioID(__bean.getTraduccioID());
	}

	public long getTraduccioID() {
		return(traduccioID);
	};
	public void setTraduccioID(long _traduccioID_) {
		this.traduccioID = _traduccioID_;
	};



  @Override
  public boolean equals(Object __obj) {
  boolean __result;
    if (__obj != null && __obj instanceof Traduccio) {
      Traduccio __instance = (Traduccio)__obj;
      __result = true;
      __result = __result && (this.getTraduccioID() == __instance.getTraduccioID()) ;
    } else {
      __result = false;
    }
    return __result;
  }

// EXP  Field:titolacademicid | Table: dem_alumne | Type: 0  

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "titolAcademicID")
    private Set<AlumneJPA> alumnes = new HashSet<AlumneJPA>(0);
    public  Set<AlumneJPA> getAlumnes() {
    return this.alumnes;
  }

    public void setAlumnes(Set<AlumneJPA> alumnes) {
      this.alumnes = alumnes;
    }


  @ElementCollection(fetch= FetchType.EAGER, targetClass = org.fundaciobit.demogenapp.persistence.TraduccioMapJPA.class)
  @Cascade(value=org.hibernate.annotations.CascadeType.ALL)
  @LazyCollection(value= LazyCollectionOption.FALSE)
  @JoinTable(name="dem_traducciomap",joinColumns={@JoinColumn(name="traducciomapid")}, foreignKey=@ForeignKey(name="dem_traducmap_traduccio_fk"))
  @javax.persistence.MapKeyColumn(name="idiomaid")
  private Map<String, org.fundaciobit.demogenapp.persistence.TraduccioMapJPA> traduccions =  new HashMap<String, org.fundaciobit.demogenapp.persistence.TraduccioMapJPA>();

  public Map<String, org.fundaciobit.demogenapp.persistence.TraduccioMapJPA> getTraduccions() {
    return this.traduccions;
  }

  public void setTraduccions(Map<String, org.fundaciobit.demogenapp.persistence.TraduccioMapJPA> _traduccions_) {
    this.traduccions = _traduccions_;
  }

  public java.util.Set<String> getIdiomes() {
    return this.traduccions.keySet();
  }
  
  public org.fundaciobit.demogenapp.persistence.TraduccioMapJPA getTraduccio(String idioma) {
    return (org.fundaciobit.demogenapp.persistence.TraduccioMapJPA) traduccions.get(idioma);
  }
  
  public void addTraduccio(String idioma, org.fundaciobit.demogenapp.persistence.TraduccioMapJPA traduccio) {
    if (traduccio == null) {
      traduccions.remove(idioma);
    } else {
      traduccions.put(idioma, traduccio);
    }
  }

 // ---------------  STATIC METHODS ------------------
  public static TraduccioJPA toJPA(Traduccio __bean) {
    if (__bean == null) { return null;}
    TraduccioJPA __tmp = new TraduccioJPA();
    __tmp.setTraduccioID(__bean.getTraduccioID());
		return __tmp;
	}


  public static TraduccioJPA copyJPA(TraduccioJPA __jpa) {
    return copyJPA(__jpa,new java.util.HashMap<Object,Object>(), null);
  }

  static java.util.Set<TraduccioJPA> copyJPA(java.util.Set<TraduccioJPA> __jpaSet,
    java.util.Map<Object,Object> __alreadyCopied, String origenJPA) {
    if (__jpaSet == null) { return null; }
    java.util.Set<TraduccioJPA> __tmpSet = (java.util.Set<TraduccioJPA>) __alreadyCopied.get(__jpaSet);
    if (__tmpSet != null) { return __tmpSet; };
    __tmpSet = new java.util.HashSet<TraduccioJPA>(__jpaSet.size());
    __alreadyCopied.put(__jpaSet, __tmpSet);
    for (TraduccioJPA __jpa : __jpaSet) {
      __tmpSet.add(copyJPA(__jpa, __alreadyCopied, origenJPA));
    }
    return __tmpSet;
  }

  static TraduccioJPA copyJPA(TraduccioJPA __jpa,
    java.util.Map<Object,Object> __alreadyCopied, String origenJPA) {
    if (__jpa == null) { return null; }
    TraduccioJPA __tmp = (TraduccioJPA) __alreadyCopied.get(__jpa);
    if (__tmp != null) { return __tmp; };
    __tmp = toJPA(__jpa);
    __alreadyCopied.put(__jpa, __tmp);
    // Copia de beans complexes (EXP)
    if(!"AlumneJPA".equals(origenJPA) 
       && ( !org.fundaciobit.genapp.common.utils.Utils.isEmpty(__jpa.alumnes) || org.hibernate.Hibernate.isInitialized(__jpa.getAlumnes())) ) {
      __tmp.setAlumnes(AlumneJPA.copyJPA(__jpa.getAlumnes(), __alreadyCopied,"TraduccioJPA"));
    }
    // Copia de beans complexes (IMP)
    // Aquesta linia s'afeix de forma manual
    __tmp.setTraduccions(new HashMap<String, TraduccioMapJPA>(__jpa.getTraduccions()));

    return __tmp;
  }




}
