
package org.fundaciobit.demogenapp.persistence;
import org.fundaciobit.demogenapp.model.entity.*;
import javax.persistence.Table;
import javax.persistence.Column;
import java.util.HashSet;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.Index;
import javax.persistence.GeneratedValue;
import javax.persistence.SequenceGenerator;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Set;
import javax.persistence.Id;


@Entity(name = "AssignaturaJPA")
@Table(name = "dem_assignatura" , indexes = { 
        @Index(name="dem_assignatura_pk_i", columnList = "assignaturaid")})
@SequenceGenerator(name="ASSIGNATURA_SEQ", sequenceName="dem_assignatura_seq", allocationSize=1, initialValue=1000)
@javax.xml.bind.annotation.XmlRootElement
public class AssignaturaJPA implements Assignatura {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="ASSIGNATURA_SEQ")
    @Column(name="assignaturaid",nullable = false,length = 19)
    long assignaturaID;

    @Column(name="nom",nullable = false,length = 100)
    java.lang.String nom;

    @Column(name="credits",nullable = false,length = 10)
    int credits;

    @Column(name="diasetmana",length = 10)
    java.lang.Integer diaSetmana;

    @Column(name="hora",length = 15,precision = 6)
    java.sql.Time hora;

    @Column(name="descripcio",length = 4000)
    java.lang.String descripcio;



  /** Constructor Buit */
  public AssignaturaJPA() {
  }

  /** Constructor amb tots els camps  */
  public AssignaturaJPA(long assignaturaID , java.lang.String nom , int credits , java.lang.Integer diaSetmana , java.sql.Time hora , java.lang.String descripcio) {
    this.assignaturaID=assignaturaID;
    this.nom=nom;
    this.credits=credits;
    this.diaSetmana=diaSetmana;
    this.hora=hora;
    this.descripcio=descripcio;
}
  /** Constructor sense valors autoincrementals */
  public AssignaturaJPA(java.lang.String nom , int credits , java.lang.Integer diaSetmana , java.sql.Time hora , java.lang.String descripcio) {
    this.nom=nom;
    this.credits=credits;
    this.diaSetmana=diaSetmana;
    this.hora=hora;
    this.descripcio=descripcio;
}
  /** Constructor dels valors Not Null */
  public AssignaturaJPA(long assignaturaID , java.lang.String nom , int credits) {
    this.assignaturaID=assignaturaID;
    this.nom=nom;
    this.credits=credits;
}
  public AssignaturaJPA(Assignatura __bean) {
    this.setAssignaturaID(__bean.getAssignaturaID());
    this.setNom(__bean.getNom());
    this.setCredits(__bean.getCredits());
    this.setDiaSetmana(__bean.getDiaSetmana());
    this.setHora(__bean.getHora());
    this.setDescripcio(__bean.getDescripcio());
	}

	public long getAssignaturaID() {
		return(assignaturaID);
	};
	public void setAssignaturaID(long _assignaturaID_) {
		this.assignaturaID = _assignaturaID_;
	};

	public java.lang.String getNom() {
		return(nom);
	};
	public void setNom(java.lang.String _nom_) {
		this.nom = _nom_;
	};

	public int getCredits() {
		return(credits);
	};
	public void setCredits(int _credits_) {
		this.credits = _credits_;
	};

	public java.lang.Integer getDiaSetmana() {
		return(diaSetmana);
	};
	public void setDiaSetmana(java.lang.Integer _diaSetmana_) {
		this.diaSetmana = _diaSetmana_;
	};

	public java.sql.Time getHora() {
		return(hora);
	};
	public void setHora(java.sql.Time _hora_) {
		this.hora = _hora_;
	};

	public java.lang.String getDescripcio() {
		return(descripcio);
	};
	public void setDescripcio(java.lang.String _descripcio_) {
		this.descripcio = _descripcio_;
	};



  @Override
  public boolean equals(Object __obj) {
  boolean __result;
    if (__obj != null && __obj instanceof Assignatura) {
      Assignatura __instance = (Assignatura)__obj;
      __result = true;
      __result = __result && (this.getAssignaturaID() == __instance.getAssignaturaID()) ;
    } else {
      __result = false;
    }
    return __result;
  }

// EXP  Field:assignaturaid | Table: dem_assignaturaalumne | Type: 0  

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "assignatura")
    private Set<AssignaturaAlumneJPA> assignaturaAlumnes = new HashSet<AssignaturaAlumneJPA>(0);
    public  Set<AssignaturaAlumneJPA> getAssignaturaAlumnes() {
    return this.assignaturaAlumnes;
  }

    public void setAssignaturaAlumnes(Set<AssignaturaAlumneJPA> assignaturaAlumnes) {
      this.assignaturaAlumnes = assignaturaAlumnes;
    }



 // ---------------  STATIC METHODS ------------------
  public static AssignaturaJPA toJPA(Assignatura __bean) {
    if (__bean == null) { return null;}
    AssignaturaJPA __tmp = new AssignaturaJPA();
    __tmp.setAssignaturaID(__bean.getAssignaturaID());
    __tmp.setNom(__bean.getNom());
    __tmp.setCredits(__bean.getCredits());
    __tmp.setDiaSetmana(__bean.getDiaSetmana());
    __tmp.setHora(__bean.getHora());
    __tmp.setDescripcio(__bean.getDescripcio());
		return __tmp;
	}


  public static AssignaturaJPA copyJPA(AssignaturaJPA __jpa) {
    return copyJPA(__jpa,new java.util.HashMap<Object,Object>(), null);
  }

  static java.util.Set<AssignaturaJPA> copyJPA(java.util.Set<AssignaturaJPA> __jpaSet,
    java.util.Map<Object,Object> __alreadyCopied, String origenJPA) {
    if (__jpaSet == null) { return null; }
    java.util.Set<AssignaturaJPA> __tmpSet = (java.util.Set<AssignaturaJPA>) __alreadyCopied.get(__jpaSet);
    if (__tmpSet != null) { return __tmpSet; };
    __tmpSet = new java.util.HashSet<AssignaturaJPA>(__jpaSet.size());
    __alreadyCopied.put(__jpaSet, __tmpSet);
    for (AssignaturaJPA __jpa : __jpaSet) {
      __tmpSet.add(copyJPA(__jpa, __alreadyCopied, origenJPA));
    }
    return __tmpSet;
  }

  static AssignaturaJPA copyJPA(AssignaturaJPA __jpa,
    java.util.Map<Object,Object> __alreadyCopied, String origenJPA) {
    if (__jpa == null) { return null; }
    AssignaturaJPA __tmp = (AssignaturaJPA) __alreadyCopied.get(__jpa);
    if (__tmp != null) { return __tmp; };
    __tmp = toJPA(__jpa);
    __alreadyCopied.put(__jpa, __tmp);
    // Copia de beans complexes (EXP)
    if(!"AssignaturaAlumneJPA".equals(origenJPA) 
       && ( !org.fundaciobit.genapp.common.utils.Utils.isEmpty(__jpa.assignaturaAlumnes) || org.hibernate.Hibernate.isInitialized(__jpa.getAssignaturaAlumnes())) ) {
      __tmp.setAssignaturaAlumnes(AssignaturaAlumneJPA.copyJPA(__jpa.getAssignaturaAlumnes(), __alreadyCopied,"AssignaturaJPA"));
    }
    // Copia de beans complexes (IMP)

    return __tmp;
  }




}
