
package org.fundaciobit.demogenapp.persistence;
import org.fundaciobit.demogenapp.model.entity.*;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.GenerationType;
import javax.persistence.Index;
import javax.persistence.GeneratedValue;
import javax.persistence.SequenceGenerator;
import javax.persistence.FetchType;
import javax.persistence.Id;


@Entity(name = "AlumneJPA")
@Table(name = "dem_alumne" , indexes = { 
        @Index(name="dem_alumne_pk_i", columnList = "alumneid"),
        @Index(name="dem_alumne_idiomaid_fk_i", columnList = "idiomaid"),
        @Index(name="dem_alumne_fotoid_fk_i", columnList = "fotoid"),
        @Index(name="dem_alumne_titolaca_fk_i", columnList = "titolacademicid")})
@SequenceGenerator(name="ALUMNE_SEQ", sequenceName="dem_alumne_seq", allocationSize=1, initialValue=1000)
@javax.xml.bind.annotation.XmlRootElement
public class AlumneJPA implements Alumne {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="ALUMNE_SEQ")
    @Column(name="alumneid",nullable = false,length = 19)
    long alumneID;

    @Column(name="nom",nullable = false,length = 100)
    java.lang.String nom;

    @Column(name="idiomaid",nullable = false,length = 2)
    java.lang.String idiomaID;

    @Column(name="datanaixement",nullable = false,length = 13)
    java.sql.Date dataNaixement;

    @Column(name="sexe",length = 1)
    java.lang.Boolean sexe;

    @Column(name="actiu",nullable = false,length = 1)
    boolean actiu = true;

    @Column(name="darreracces",length = 29,precision = 6)
    java.sql.Timestamp darrerAcces;

    @Column(name="fotoid",length = 19)
    java.lang.Long fotoID;

    @Column(name="titolacademicid",length = 19)
    java.lang.Long titolAcademicID;

    @Column(name="despertador",length = 15,precision = 6)
    java.sql.Time despertador;

    @Column(name="paginaweb",length = 255)
    java.lang.String paginaWeb;

    @Column(name="descripcio",length = 4000)
    java.lang.String descripcio;



  /** Constructor Buit */
  public AlumneJPA() {
  }

  /** Constructor amb tots els camps  */
  public AlumneJPA(long alumneID , java.lang.String nom , java.lang.String idiomaID , java.sql.Date dataNaixement , java.lang.Boolean sexe , boolean actiu , java.sql.Timestamp darrerAcces , java.lang.Long fotoID , java.lang.Long titolAcademicID , java.sql.Time despertador , java.lang.String paginaWeb , java.lang.String descripcio) {
    this.alumneID=alumneID;
    this.nom=nom;
    this.idiomaID=idiomaID;
    this.dataNaixement=dataNaixement;
    this.sexe=sexe;
    this.actiu=actiu;
    this.darrerAcces=darrerAcces;
    this.fotoID=fotoID;
    this.titolAcademicID=titolAcademicID;
    this.despertador=despertador;
    this.paginaWeb=paginaWeb;
    this.descripcio=descripcio;
}
  /** Constructor sense valors autoincrementals */
  public AlumneJPA(java.lang.String nom , java.lang.String idiomaID , java.sql.Date dataNaixement , java.lang.Boolean sexe , boolean actiu , java.sql.Timestamp darrerAcces , java.lang.Long fotoID , java.lang.Long titolAcademicID , java.sql.Time despertador , java.lang.String paginaWeb , java.lang.String descripcio) {
    this.nom=nom;
    this.idiomaID=idiomaID;
    this.dataNaixement=dataNaixement;
    this.sexe=sexe;
    this.actiu=actiu;
    this.darrerAcces=darrerAcces;
    this.fotoID=fotoID;
    this.titolAcademicID=titolAcademicID;
    this.despertador=despertador;
    this.paginaWeb=paginaWeb;
    this.descripcio=descripcio;
}
  /** Constructor dels valors Not Null */
  public AlumneJPA(long alumneID , java.lang.String nom , java.lang.String idiomaID , java.sql.Date dataNaixement , boolean actiu) {
    this.alumneID=alumneID;
    this.nom=nom;
    this.idiomaID=idiomaID;
    this.dataNaixement=dataNaixement;
    this.actiu=actiu;
}
  public AlumneJPA(Alumne __bean) {
    this.setAlumneID(__bean.getAlumneID());
    this.setNom(__bean.getNom());
    this.setIdiomaID(__bean.getIdiomaID());
    this.setDataNaixement(__bean.getDataNaixement());
    this.setSexe(__bean.getSexe());
    this.setActiu(__bean.isActiu());
    this.setDarrerAcces(__bean.getDarrerAcces());
    this.setFotoID(__bean.getFotoID());
    this.setTitolAcademicID(__bean.getTitolAcademicID());
    this.setDespertador(__bean.getDespertador());
    this.setPaginaWeb(__bean.getPaginaWeb());
    this.setDescripcio(__bean.getDescripcio());
    // Fitxer
    this.setFoto(FitxerJPA.toJPA(__bean.getFoto()));
	}

	public long getAlumneID() {
		return(alumneID);
	};
	public void setAlumneID(long _alumneID_) {
		this.alumneID = _alumneID_;
	};

	public java.lang.String getNom() {
		return(nom);
	};
	public void setNom(java.lang.String _nom_) {
		this.nom = _nom_;
	};

	public java.lang.String getIdiomaID() {
		return(idiomaID);
	};
	public void setIdiomaID(java.lang.String _idiomaID_) {
		this.idiomaID = _idiomaID_;
	};

	public java.sql.Date getDataNaixement() {
		return(dataNaixement);
	};
	public void setDataNaixement(java.sql.Date _dataNaixement_) {
		this.dataNaixement = _dataNaixement_;
	};

	public java.lang.Boolean getSexe() {
		return(sexe);
	};
	public void setSexe(java.lang.Boolean _sexe_) {
		this.sexe = _sexe_;
	};

	public boolean isActiu() {
		return(actiu);
	};
	public void setActiu(boolean _actiu_) {
		this.actiu = _actiu_;
	};

	public java.sql.Timestamp getDarrerAcces() {
		return(darrerAcces);
	};
	public void setDarrerAcces(java.sql.Timestamp _darrerAcces_) {
		this.darrerAcces = _darrerAcces_;
	};

	public java.lang.Long getFotoID() {
		return(fotoID);
	};
	public void setFotoID(java.lang.Long _fotoID_) {
		this.fotoID = _fotoID_;
	};

	public java.lang.Long getTitolAcademicID() {
		return(titolAcademicID);
	};
	public void setTitolAcademicID(java.lang.Long _titolAcademicID_) {
		this.titolAcademicID = _titolAcademicID_;
	};

	public java.sql.Time getDespertador() {
		return(despertador);
	};
	public void setDespertador(java.sql.Time _despertador_) {
		this.despertador = _despertador_;
	};

	public java.lang.String getPaginaWeb() {
		return(paginaWeb);
	};
	public void setPaginaWeb(java.lang.String _paginaWeb_) {
		this.paginaWeb = _paginaWeb_;
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
    if (__obj != null && __obj instanceof Alumne) {
      Alumne __instance = (Alumne)__obj;
      __result = true;
      __result = __result && (this.getAlumneID() == __instance.getAlumneID()) ;
    } else {
      __result = false;
    }
    return __result;
  }

// EXP  Field:alumneid | Table: dem_assignaturaalumne | Type: 0  

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "alumne")
    private Set<AssignaturaAlumneJPA> assignaturaAlumnes = new HashSet<AssignaturaAlumneJPA>(0);
    public  Set<AssignaturaAlumneJPA> getAssignaturaAlumnes() {
    return this.assignaturaAlumnes;
  }

    public void setAssignaturaAlumnes(Set<AssignaturaAlumneJPA> assignaturaAlumnes) {
      this.assignaturaAlumnes = assignaturaAlumnes;
    }


// IMP Field:idiomaid | Table: dem_idioma | Type: 1  

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idiomaid", referencedColumnName ="idiomaID", nullable = false, insertable=false, updatable=false, foreignKey=@ForeignKey(name="dem_alumne_idioma_idiomaid_fk"))
    private IdiomaJPA idioma;

    public IdiomaJPA getIdioma() {
    return this.idioma;
  }

    public  void setIdioma(IdiomaJPA idioma) {
    this.idioma = idioma;
  }

// IMP Field:fitxerid | Table: dem_fitxer | Type: 1  

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fotoid", referencedColumnName ="fitxerID", nullable = true, insertable=false, updatable=false, foreignKey=@ForeignKey(name="dem_alumne_fitxer_fotoid_fk"))
    private FitxerJPA foto;

    public FitxerJPA getFoto() {
    return this.foto;
  }

    public  void setFoto(FitxerJPA foto) {
    this.foto = foto;
  }

// IMP Field:traduccioid | Table: dem_traduccio | Type: 1  

    @ManyToOne(fetch = FetchType.EAGER, cascade=javax.persistence.CascadeType.ALL)
    @JoinColumn(name = "titolacademicid", referencedColumnName ="traduccioID", nullable = true, insertable=false, updatable=false, foreignKey=@ForeignKey(name="dem_alumne_traduccio_titola_fk"))
    private TraduccioJPA titolAcademic;

    public TraduccioJPA getTitolAcademic() {
    return this.titolAcademic;
  }

    public  void setTitolAcademic(TraduccioJPA titolAcademic) {
    this.titolAcademic = titolAcademic;
  }

  @javax.xml.bind.annotation.XmlTransient
  public java.util.Map<String, org.fundaciobit.demogenapp.persistence.TraduccioMapJPA> getTitolAcademicTraduccions() {
    return this.titolAcademic.getTraduccions();
  }

  public void setTitolAcademicTraduccions(java.util.Map<String, org.fundaciobit.demogenapp.persistence.TraduccioMapJPA> __traduccions__) {
    this.titolAcademic.setTraduccions(__traduccions__);
  }



 // ---------------  STATIC METHODS ------------------
  public static AlumneJPA toJPA(Alumne __bean) {
    if (__bean == null) { return null;}
    AlumneJPA __tmp = new AlumneJPA();
    __tmp.setAlumneID(__bean.getAlumneID());
    __tmp.setNom(__bean.getNom());
    __tmp.setIdiomaID(__bean.getIdiomaID());
    __tmp.setDataNaixement(__bean.getDataNaixement());
    __tmp.setSexe(__bean.getSexe());
    __tmp.setActiu(__bean.isActiu());
    __tmp.setDarrerAcces(__bean.getDarrerAcces());
    __tmp.setFotoID(__bean.getFotoID());
    __tmp.setTitolAcademicID(__bean.getTitolAcademicID());
    __tmp.setDespertador(__bean.getDespertador());
    __tmp.setPaginaWeb(__bean.getPaginaWeb());
    __tmp.setDescripcio(__bean.getDescripcio());
    // Fitxer
    __tmp.setFoto(FitxerJPA.toJPA(__bean.getFoto()));
		return __tmp;
	}


  public static AlumneJPA copyJPA(AlumneJPA __jpa) {
    return copyJPA(__jpa,new java.util.HashMap<Object,Object>(), null);
  }

  static java.util.Set<AlumneJPA> copyJPA(java.util.Set<AlumneJPA> __jpaSet,
    java.util.Map<Object,Object> __alreadyCopied, String origenJPA) {
    if (__jpaSet == null) { return null; }
    java.util.Set<AlumneJPA> __tmpSet = (java.util.Set<AlumneJPA>) __alreadyCopied.get(__jpaSet);
    if (__tmpSet != null) { return __tmpSet; };
    __tmpSet = new java.util.HashSet<AlumneJPA>(__jpaSet.size());
    __alreadyCopied.put(__jpaSet, __tmpSet);
    for (AlumneJPA __jpa : __jpaSet) {
      __tmpSet.add(copyJPA(__jpa, __alreadyCopied, origenJPA));
    }
    return __tmpSet;
  }

  static AlumneJPA copyJPA(AlumneJPA __jpa,
    java.util.Map<Object,Object> __alreadyCopied, String origenJPA) {
    if (__jpa == null) { return null; }
    AlumneJPA __tmp = (AlumneJPA) __alreadyCopied.get(__jpa);
    if (__tmp != null) { return __tmp; };
    __tmp = toJPA(__jpa);
    __alreadyCopied.put(__jpa, __tmp);
    // Copia de beans complexes (EXP)
    if(!"AssignaturaAlumneJPA".equals(origenJPA) 
       && ( !org.fundaciobit.genapp.common.utils.Utils.isEmpty(__jpa.assignaturaAlumnes) || org.hibernate.Hibernate.isInitialized(__jpa.getAssignaturaAlumnes())) ) {
      __tmp.setAssignaturaAlumnes(AssignaturaAlumneJPA.copyJPA(__jpa.getAssignaturaAlumnes(), __alreadyCopied,"AlumneJPA"));
    }
    // Copia de beans complexes (IMP)
    if(!"IdiomaJPA".equals(origenJPA) && 
       (!org.fundaciobit.genapp.common.utils.Utils.isEmpty(__jpa.idioma) || org.hibernate.Hibernate.isInitialized(__jpa.getIdioma()) ) ) {
      __tmp.setIdioma(IdiomaJPA.copyJPA(__jpa.getIdioma(), __alreadyCopied,"AlumneJPA"));
    }
    if(!"TraduccioJPA".equals(origenJPA) && 
       (!org.fundaciobit.genapp.common.utils.Utils.isEmpty(__jpa.titolAcademic) || org.hibernate.Hibernate.isInitialized(__jpa.getTitolAcademic()) ) ) {
      __tmp.setTitolAcademic(TraduccioJPA.copyJPA(__jpa.getTitolAcademic(), __alreadyCopied,"AlumneJPA"));
    }

    return __tmp;
  }




}
