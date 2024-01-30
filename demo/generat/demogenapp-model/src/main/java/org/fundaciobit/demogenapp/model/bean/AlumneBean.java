
package org.fundaciobit.demogenapp.model.bean;

import org.fundaciobit.demogenapp.model.entity.Alumne;


public class AlumneBean implements Alumne {



	long alumneID;// PK
	java.lang.String nom;
	java.lang.String idiomaID;
	java.sql.Date dataNaixement;
	java.lang.Boolean sexe;
	boolean actiu;
	java.sql.Timestamp darrerAcces;
	java.lang.Long fotoID;
	java.lang.Long titolAcademicID;
	java.sql.Time despertador;
	java.lang.String paginaWeb;
	java.lang.String descripcio;


  /** Constructor Buit */
  public AlumneBean() {
  }

  /** Constructor amb tots els camps  */
  public AlumneBean(long alumneID , java.lang.String nom , java.lang.String idiomaID , java.sql.Date dataNaixement , java.lang.Boolean sexe , boolean actiu , java.sql.Timestamp darrerAcces , java.lang.Long fotoID , java.lang.Long titolAcademicID , java.sql.Time despertador , java.lang.String paginaWeb , java.lang.String descripcio) {
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
  public AlumneBean(java.lang.String nom , java.lang.String idiomaID , java.sql.Date dataNaixement , java.lang.Boolean sexe , boolean actiu , java.sql.Timestamp darrerAcces , java.lang.Long fotoID , java.lang.Long titolAcademicID , java.sql.Time despertador , java.lang.String paginaWeb , java.lang.String descripcio) {
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
  public AlumneBean(long alumneID , java.lang.String nom , java.lang.String idiomaID , java.sql.Date dataNaixement , boolean actiu) {
    this.alumneID=alumneID;
    this.nom=nom;
    this.idiomaID=idiomaID;
    this.dataNaixement=dataNaixement;
    this.actiu=actiu;
}
  public AlumneBean(Alumne __bean) {
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
    this.setFoto(FitxerBean.toBean(__bean.getFoto()));
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



  // ======================================

  public static AlumneBean toBean(Alumne __bean) {
    if (__bean == null) { return null;}
    AlumneBean __tmp = new AlumneBean();
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
    __tmp.setFoto(FitxerBean.toBean(__bean.getFoto()));
		return __tmp;
	}

  protected FitxerBean foto;
  public FitxerBean getFoto() {
    return foto;
  }
  public void setFoto(FitxerBean __field) {
    this. foto = __field;
  }


}
