package org.fundaciobit.genappsqltutorial.model.entity;

public interface Employees extends org.fundaciobit.genapp.common.IGenAppEntity {

	public long getEmployeeid();
	public void setEmployeeid(long _employeeid_);

	public java.lang.String getLastname();
	public void setLastname(java.lang.String _lastname_);

	public java.lang.String getFirstname();
	public void setFirstname(java.lang.String _firstname_);

	public java.sql.Date getBirthdate();
	public void setBirthdate(java.sql.Date _birthdate_);

	public java.lang.String getPhoto();
	public void setPhoto(java.lang.String _photo_);

	public java.lang.String getNotes();
	public void setNotes(java.lang.String _notes_);



  // ======================================

}
