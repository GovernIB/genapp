
package org.fundaciobit.genappsqltutorial.model.bean;

import org.fundaciobit.genappsqltutorial.model.entity.Employees;


public class EmployeesBean implements Employees {



	long employeeid;// PK
	java.lang.String lastname;
	java.lang.String firstname;
	java.sql.Date birthdate;
	java.lang.String photo;
	java.lang.String notes;


  /** Constructor Buit */
  public EmployeesBean() {
  }

  /** Constructor amb tots els camps  */
  public EmployeesBean(long employeeid , java.lang.String lastname , java.lang.String firstname , java.sql.Date birthdate , java.lang.String photo , java.lang.String notes) {
    this.employeeid=employeeid;
    this.lastname=lastname;
    this.firstname=firstname;
    this.birthdate=birthdate;
    this.photo=photo;
    this.notes=notes;
}
  /** Constructor sense valors autoincrementals */
  public EmployeesBean(java.lang.String lastname , java.lang.String firstname , java.sql.Date birthdate , java.lang.String photo , java.lang.String notes) {
    this.lastname=lastname;
    this.firstname=firstname;
    this.birthdate=birthdate;
    this.photo=photo;
    this.notes=notes;
}
  /** Constructor dels valors Not Null */
  public EmployeesBean(long employeeid) {
    this.employeeid=employeeid;
}
  public EmployeesBean(Employees __bean) {
    this.setEmployeeid(__bean.getEmployeeid());
    this.setLastname(__bean.getLastname());
    this.setFirstname(__bean.getFirstname());
    this.setBirthdate(__bean.getBirthdate());
    this.setPhoto(__bean.getPhoto());
    this.setNotes(__bean.getNotes());
	}

	public long getEmployeeid() {
		return(employeeid);
	};
	public void setEmployeeid(long _employeeid_) {
		this.employeeid = _employeeid_;
	};

	public java.lang.String getLastname() {
		return(lastname);
	};
	public void setLastname(java.lang.String _lastname_) {
		this.lastname = _lastname_;
	};

	public java.lang.String getFirstname() {
		return(firstname);
	};
	public void setFirstname(java.lang.String _firstname_) {
		this.firstname = _firstname_;
	};

	public java.sql.Date getBirthdate() {
		return(birthdate);
	};
	public void setBirthdate(java.sql.Date _birthdate_) {
		this.birthdate = _birthdate_;
	};

	public java.lang.String getPhoto() {
		return(photo);
	};
	public void setPhoto(java.lang.String _photo_) {
		this.photo = _photo_;
	};

	public java.lang.String getNotes() {
		return(notes);
	};
	public void setNotes(java.lang.String _notes_) {
		this.notes = _notes_;
	};



  // ======================================

  public static EmployeesBean toBean(Employees __bean) {
    if (__bean == null) { return null;}
    EmployeesBean __tmp = new EmployeesBean();
    __tmp.setEmployeeid(__bean.getEmployeeid());
    __tmp.setLastname(__bean.getLastname());
    __tmp.setFirstname(__bean.getFirstname());
    __tmp.setBirthdate(__bean.getBirthdate());
    __tmp.setPhoto(__bean.getPhoto());
    __tmp.setNotes(__bean.getNotes());
		return __tmp;
	}



}
