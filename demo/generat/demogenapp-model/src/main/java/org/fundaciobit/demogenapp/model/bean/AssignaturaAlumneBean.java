
package org.fundaciobit.demogenapp.model.bean;

import org.fundaciobit.demogenapp.model.entity.AssignaturaAlumne;


public class AssignaturaAlumneBean implements AssignaturaAlumne {



	long id;// PK
	java.lang.Long alumneID;
	long assignaturaID;
	java.lang.Double nota;


  /** Constructor Buit */
  public AssignaturaAlumneBean() {
  }

  /** Constructor amb tots els camps  */
  public AssignaturaAlumneBean(long id , java.lang.Long alumneID , long assignaturaID , java.lang.Double nota) {
    this.id=id;
    this.alumneID=alumneID;
    this.assignaturaID=assignaturaID;
    this.nota=nota;
}
  /** Constructor sense valors autoincrementals */
  public AssignaturaAlumneBean(java.lang.Long alumneID , long assignaturaID , java.lang.Double nota) {
    this.alumneID=alumneID;
    this.assignaturaID=assignaturaID;
    this.nota=nota;
}
  public AssignaturaAlumneBean(AssignaturaAlumne __bean) {
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



  // ======================================

  public static AssignaturaAlumneBean toBean(AssignaturaAlumne __bean) {
    if (__bean == null) { return null;}
    AssignaturaAlumneBean __tmp = new AssignaturaAlumneBean();
    __tmp.setId(__bean.getId());
    __tmp.setAlumneID(__bean.getAlumneID());
    __tmp.setAssignaturaID(__bean.getAssignaturaID());
    __tmp.setNota(__bean.getNota());
		return __tmp;
	}



}
