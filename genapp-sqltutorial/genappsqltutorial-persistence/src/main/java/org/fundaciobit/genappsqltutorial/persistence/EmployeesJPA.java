
package org.fundaciobit.genappsqltutorial.persistence;
import org.fundaciobit.genappsqltutorial.model.entity.*;
import javax.persistence.Table;
import javax.persistence.Column;
import java.util.HashSet;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.Lob;
import javax.persistence.Index;
import javax.persistence.GeneratedValue;
import javax.persistence.SequenceGenerator;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Set;
import org.hibernate.annotations.Type;
import javax.persistence.Id;


@Entity(name = "EmployeesJPA")
@Table(name = "gas_employees" , indexes = { 
        @Index(name="gas_employees_pk_i", columnList = "employeeid")})
@SequenceGenerator(name="EMPLOYEES_SEQ", sequenceName="gas_employees_seq", allocationSize=1, initialValue=1000)
@javax.xml.bind.annotation.XmlRootElement
public class EmployeesJPA implements Employees {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="EMPLOYEES_SEQ")
    @Column(name="employeeid",nullable = false,length = 19)
    long employeeid;

    @Column(name="lastname",length = 255)
    java.lang.String lastname;

    @Column(name="firstname",length = 255)
    java.lang.String firstname;

    @Column(name="birthdate",length = 13)
    java.sql.Date birthdate;

    @Column(name="photo",length = 255)
    java.lang.String photo;

    @Column(name="notes",length = 2147483647)
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    java.lang.String notes;



  /** Constructor Buit */
  public EmployeesJPA() {
  }

  /** Constructor amb tots els camps  */
  public EmployeesJPA(long employeeid , java.lang.String lastname , java.lang.String firstname , java.sql.Date birthdate , java.lang.String photo , java.lang.String notes) {
    this.employeeid=employeeid;
    this.lastname=lastname;
    this.firstname=firstname;
    this.birthdate=birthdate;
    this.photo=photo;
    this.notes=notes;
}
  /** Constructor sense valors autoincrementals */
  public EmployeesJPA(java.lang.String lastname , java.lang.String firstname , java.sql.Date birthdate , java.lang.String photo , java.lang.String notes) {
    this.lastname=lastname;
    this.firstname=firstname;
    this.birthdate=birthdate;
    this.photo=photo;
    this.notes=notes;
}
  /** Constructor dels valors Not Null */
  public EmployeesJPA(long employeeid) {
    this.employeeid=employeeid;
}
  public EmployeesJPA(Employees __bean) {
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



  @Override
  public boolean equals(Object __obj) {
  boolean __result;
    if (__obj != null && __obj instanceof Employees) {
      Employees __instance = (Employees)__obj;
      __result = true;
      __result = __result && (this.getEmployeeid() == __instance.getEmployeeid()) ;
    } else {
      __result = false;
    }
    return __result;
  }

// EXP  Field:employeeid | Table: gas_orders | Type: 0  

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employees")
    private Set<OrdersJPA> orderss = new HashSet<OrdersJPA>(0);
    public  Set<OrdersJPA> getOrderss() {
    return this.orderss;
  }

    public void setOrderss(Set<OrdersJPA> orderss) {
      this.orderss = orderss;
    }



 // ---------------  STATIC METHODS ------------------
  public static EmployeesJPA toJPA(Employees __bean) {
    if (__bean == null) { return null;}
    EmployeesJPA __tmp = new EmployeesJPA();
    __tmp.setEmployeeid(__bean.getEmployeeid());
    __tmp.setLastname(__bean.getLastname());
    __tmp.setFirstname(__bean.getFirstname());
    __tmp.setBirthdate(__bean.getBirthdate());
    __tmp.setPhoto(__bean.getPhoto());
    __tmp.setNotes(__bean.getNotes());
		return __tmp;
	}


  public static EmployeesJPA copyJPA(EmployeesJPA __jpa) {
    return copyJPA(__jpa,new java.util.HashMap<Object,Object>(), null);
  }

  static java.util.Set<EmployeesJPA> copyJPA(java.util.Set<EmployeesJPA> __jpaSet,
    java.util.Map<Object,Object> __alreadyCopied, String origenJPA) {
    if (__jpaSet == null) { return null; }
    java.util.Set<EmployeesJPA> __tmpSet = (java.util.Set<EmployeesJPA>) __alreadyCopied.get(__jpaSet);
    if (__tmpSet != null) { return __tmpSet; };
    __tmpSet = new java.util.HashSet<EmployeesJPA>(__jpaSet.size());
    __alreadyCopied.put(__jpaSet, __tmpSet);
    for (EmployeesJPA __jpa : __jpaSet) {
      __tmpSet.add(copyJPA(__jpa, __alreadyCopied, origenJPA));
    }
    return __tmpSet;
  }

  static EmployeesJPA copyJPA(EmployeesJPA __jpa,
    java.util.Map<Object,Object> __alreadyCopied, String origenJPA) {
    if (__jpa == null) { return null; }
    EmployeesJPA __tmp = (EmployeesJPA) __alreadyCopied.get(__jpa);
    if (__tmp != null) { return __tmp; };
    __tmp = toJPA(__jpa);
    __alreadyCopied.put(__jpa, __tmp);
    // Copia de beans complexes (EXP)
    if(!"OrdersJPA".equals(origenJPA) 
       && ( !org.fundaciobit.genapp.common.utils.Utils.isEmpty(__jpa.orderss) || org.hibernate.Hibernate.isInitialized(__jpa.getOrderss())) ) {
      __tmp.setOrderss(OrdersJPA.copyJPA(__jpa.getOrderss(), __alreadyCopied,"EmployeesJPA"));
    }
    // Copia de beans complexes (IMP)

    return __tmp;
  }




}
