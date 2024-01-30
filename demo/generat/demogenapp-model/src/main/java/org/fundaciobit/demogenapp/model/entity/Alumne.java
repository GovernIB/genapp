package org.fundaciobit.demogenapp.model.entity;

public interface Alumne extends org.fundaciobit.genapp.common.IGenAppEntity {

	public long getAlumneID();
	public void setAlumneID(long _alumneID_);

	public java.lang.String getNom();
	public void setNom(java.lang.String _nom_);

	public java.lang.String getIdiomaID();
	public void setIdiomaID(java.lang.String _idiomaID_);

	public java.sql.Date getDataNaixement();
	public void setDataNaixement(java.sql.Date _dataNaixement_);

	public java.lang.Boolean getSexe();
	public void setSexe(java.lang.Boolean _sexe_);

	public boolean isActiu();
	public void setActiu(boolean _actiu_);

	public java.sql.Timestamp getDarrerAcces();
	public void setDarrerAcces(java.sql.Timestamp _darrerAcces_);

	public java.lang.Long getFotoID();
	public void setFotoID(java.lang.Long _fotoID_);

	public java.lang.Long getTitolAcademicID();
	public void setTitolAcademicID(java.lang.Long _titolAcademicID_);

	public java.sql.Time getDespertador();
	public void setDespertador(java.sql.Time _despertador_);

	public java.lang.String getPaginaWeb();
	public void setPaginaWeb(java.lang.String _paginaWeb_);

	public java.lang.String getDescripcio();
	public void setDescripcio(java.lang.String _descripcio_);

  // Fitxer
  public <F extends Fitxer> F getFoto();


  // ======================================

}
