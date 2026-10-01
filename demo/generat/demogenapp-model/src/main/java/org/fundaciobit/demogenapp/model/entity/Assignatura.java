package org.fundaciobit.demogenapp.model.entity;

public interface Assignatura extends org.fundaciobit.genapp.common.IGenAppEntity {

	public long getAssignaturaID();
	public void setAssignaturaID(long _assignaturaID_);

	public java.lang.String getNom();
	public void setNom(java.lang.String _nom_);

	public int getCredits();
	public void setCredits(int _credits_);

	public java.lang.Integer getDiaSetmana();
	public void setDiaSetmana(java.lang.Integer _diaSetmana_);

	public java.sql.Time getHora();
	public void setHora(java.sql.Time _hora_);

	public java.lang.String getDescripcio();
	public void setDescripcio(java.lang.String _descripcio_);



  // ======================================

}
