
package org.fundaciobit.demogenapp.model.bean;

import org.fundaciobit.demogenapp.model.entity.Assignatura;


public class AssignaturaBean implements Assignatura {



	long assignaturaID;// PK
	java.lang.String nom;
	int credits;
	java.lang.Integer diaSetmana;
	java.sql.Time hora;
	java.lang.String descripcio;


  /** Constructor Buit */
  public AssignaturaBean() {
  }

  /** Constructor amb tots els camps  */
  public AssignaturaBean(long assignaturaID , java.lang.String nom , int credits , java.lang.Integer diaSetmana , java.sql.Time hora , java.lang.String descripcio) {
    this.assignaturaID=assignaturaID;
    this.nom=nom;
    this.credits=credits;
    this.diaSetmana=diaSetmana;
    this.hora=hora;
    this.descripcio=descripcio;
}
  /** Constructor sense valors autoincrementals */
  public AssignaturaBean(java.lang.String nom , int credits , java.lang.Integer diaSetmana , java.sql.Time hora , java.lang.String descripcio) {
    this.nom=nom;
    this.credits=credits;
    this.diaSetmana=diaSetmana;
    this.hora=hora;
    this.descripcio=descripcio;
}
  /** Constructor dels valors Not Null */
  public AssignaturaBean(long assignaturaID , java.lang.String nom , int credits) {
    this.assignaturaID=assignaturaID;
    this.nom=nom;
    this.credits=credits;
}
  public AssignaturaBean(Assignatura __bean) {
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



  // ======================================

  public static AssignaturaBean toBean(Assignatura __bean) {
    if (__bean == null) { return null;}
    AssignaturaBean __tmp = new AssignaturaBean();
    __tmp.setAssignaturaID(__bean.getAssignaturaID());
    __tmp.setNom(__bean.getNom());
    __tmp.setCredits(__bean.getCredits());
    __tmp.setDiaSetmana(__bean.getDiaSetmana());
    __tmp.setHora(__bean.getHora());
    __tmp.setDescripcio(__bean.getDescripcio());
		return __tmp;
	}



}
