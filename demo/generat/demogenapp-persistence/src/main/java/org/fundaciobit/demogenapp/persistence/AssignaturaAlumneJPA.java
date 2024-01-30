
package org.fundaciobit.demogenapp.persistence;
import org.fundaciobit.demogenapp.model.entity.*;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.Index;
import javax.persistence.UniqueConstraint;
import javax.persistence.GeneratedValue;
import javax.persistence.SequenceGenerator;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import javax.persistence.Id;


@Entity(name = "AssignaturaAlumneJPA")
@Table(name = "dem_assignaturaalumne" , indexes = { 
        @Index(name="dem_assignaturaalumne_pk_i", columnList = "id"),
        @Index(name="dem_assigalumn_alumneid_fk_i", columnList = "alumneid"),
        @Index(name="dem_assigalumn_assigna_fk_i", columnList = "assignaturaid")},
           uniqueConstraints = {
            @UniqueConstraint(name="dem_assigalumn_multiple_uk", columnNames={"alumneid","assignaturaid"}) } )
@SequenceGenerator(name="ASSIGNATURAALUMNE_SEQ", sequenceName="dem_assignaturaalumne_seq", allocationSize=1, initialValue=1000)
@javax.xml.bind.annotation.XmlRootElement
public class AssignaturaAlumneJPA implements AssignaturaAlumne {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="ASSIGNATURAALUMNE_SEQ")
    @Column(name="id",nullable = false,length = 19)
    long id;

    @Column(name="alumneid",nullable = false,length = 19)
    java.lang.Long alumneID;

    @Column(name="assignaturaid",nullable = false,length = 19)
    long assignaturaID;

    @Column(name="nota",length = 17,precision = 17)
    java.lang.Double nota;



  /** Constructor Buit */
  public AssignaturaAlumneJPA() {
  }

  /** Constructor amb tots els camps  */
  public AssignaturaAlumneJPA(long id , java.lang.Long alumneID , long assignaturaID , java.lang.Double nota) {
    this.id=id;
    this.alumneID=alumneID;
    this.assignaturaID=assignaturaID;
    this.nota=nota;
}
  /** Constructor sense valors autoincrementals */
  public AssignaturaAlumneJPA(java.lang.Long alumneID , long assignaturaID , java.lang.Double nota) {
    this.alumneID=alumneID;
    this.assignaturaID=assignaturaID;
    this.nota=nota;
}
  public AssignaturaAlumneJPA(AssignaturaAlumne __bean) {
    this.setId(__bean.getId());
    this.setAlumneID(__bean.getAlumneID());
    this.setAssignaturaID(__bean.getAssignaturaID());
    this.setNota(__bean.getNota());
	}

	public long getId() {
		return(id);
	};
	public void setId(long _id_) {
		this.id = _id_;
	};

	public java.lang.Long getAlumneID() {
		return(alumneID);
	};
	public void setAlumneID(java.lang.Long _alumneID_) {
		this.alumneID = _alumneID_;
	};

	public long getAssignaturaID() {
		return(assignaturaID);
	};
	public void setAssignaturaID(long _assignaturaID_) {
		this.assignaturaID = _assignaturaID_;
	};

	public java.lang.Double getNota() {
		return(nota);
	};
	public void setNota(java.lang.Double _nota_) {
		this.nota = _nota_;
	};



  @Override
  public boolean equals(Object __obj) {
  boolean __result;
    if (__obj != null && __obj instanceof AssignaturaAlumne) {
      AssignaturaAlumne __instance = (AssignaturaAlumne)__obj;
      __result = true;
      __result = __result && (this.getId() == __instance.getId()) ;
    } else {
      __result = false;
    }
    return __result;
  }

// IMP Field:alumneid | Table: dem_alumne | Type: 1  

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alumneid", referencedColumnName ="alumneID", nullable = false, insertable=false, updatable=false, foreignKey=@ForeignKey(name="dem_assigalumn_alumne_alumn_fk"))
    private AlumneJPA alumne;

    public AlumneJPA getAlumne() {
    return this.alumne;
  }

    public  void setAlumne(AlumneJPA alumne) {
    this.alumne = alumne;
  }

// IMP Field:assignaturaid | Table: dem_assignatura | Type: 1  

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignaturaid", referencedColumnName ="assignaturaID", nullable = false, insertable=false, updatable=false, foreignKey=@ForeignKey(name="dem_assigalumn_assign_assig_fk"))
    private AssignaturaJPA assignatura;

    public AssignaturaJPA getAssignatura() {
    return this.assignatura;
  }

    public  void setAssignatura(AssignaturaJPA assignatura) {
    this.assignatura = assignatura;
  }


 // ---------------  STATIC METHODS ------------------
  public static AssignaturaAlumneJPA toJPA(AssignaturaAlumne __bean) {
    if (__bean == null) { return null;}
    AssignaturaAlumneJPA __tmp = new AssignaturaAlumneJPA();
    __tmp.setId(__bean.getId());
    __tmp.setAlumneID(__bean.getAlumneID());
    __tmp.setAssignaturaID(__bean.getAssignaturaID());
    __tmp.setNota(__bean.getNota());
		return __tmp;
	}


  public static AssignaturaAlumneJPA copyJPA(AssignaturaAlumneJPA __jpa) {
    return copyJPA(__jpa,new java.util.HashMap<Object,Object>(), null);
  }

  static java.util.Set<AssignaturaAlumneJPA> copyJPA(java.util.Set<AssignaturaAlumneJPA> __jpaSet,
    java.util.Map<Object,Object> __alreadyCopied, String origenJPA) {
    if (__jpaSet == null) { return null; }
    java.util.Set<AssignaturaAlumneJPA> __tmpSet = (java.util.Set<AssignaturaAlumneJPA>) __alreadyCopied.get(__jpaSet);
    if (__tmpSet != null) { return __tmpSet; };
    __tmpSet = new java.util.HashSet<AssignaturaAlumneJPA>(__jpaSet.size());
    __alreadyCopied.put(__jpaSet, __tmpSet);
    for (AssignaturaAlumneJPA __jpa : __jpaSet) {
      __tmpSet.add(copyJPA(__jpa, __alreadyCopied, origenJPA));
    }
    return __tmpSet;
  }

  static AssignaturaAlumneJPA copyJPA(AssignaturaAlumneJPA __jpa,
    java.util.Map<Object,Object> __alreadyCopied, String origenJPA) {
    if (__jpa == null) { return null; }
    AssignaturaAlumneJPA __tmp = (AssignaturaAlumneJPA) __alreadyCopied.get(__jpa);
    if (__tmp != null) { return __tmp; };
    __tmp = toJPA(__jpa);
    __alreadyCopied.put(__jpa, __tmp);
    // Copia de beans complexes (EXP)
    // Copia de beans complexes (IMP)
    if(!"AlumneJPA".equals(origenJPA) && 
       (!org.fundaciobit.genapp.common.utils.Utils.isEmpty(__jpa.alumne) || org.hibernate.Hibernate.isInitialized(__jpa.getAlumne()) ) ) {
      __tmp.setAlumne(AlumneJPA.copyJPA(__jpa.getAlumne(), __alreadyCopied,"AssignaturaAlumneJPA"));
    }
    if(!"AssignaturaJPA".equals(origenJPA) && 
       (!org.fundaciobit.genapp.common.utils.Utils.isEmpty(__jpa.assignatura) || org.hibernate.Hibernate.isInitialized(__jpa.getAssignatura()) ) ) {
      __tmp.setAssignatura(AssignaturaJPA.copyJPA(__jpa.getAssignatura(), __alreadyCopied,"AssignaturaAlumneJPA"));
    }

    return __tmp;
  }




}
