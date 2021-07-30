
package org.fundaciobit.genappsqltutorial.persistence;
import org.fundaciobit.genappsqltutorial.model.entity.*;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import org.hibernate.annotations.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Id;


@SuppressWarnings("deprecation")
@Entity
@Table(name = "gas_suppliers" )
@SequenceGenerator(name="SUPPLIERS_SEQ", sequenceName="gas_suppliers_seq", allocationSize=1, initialValue=1000)
@javax.xml.bind.annotation.XmlRootElement
public class SuppliersJPA implements Suppliers {



private static final long serialVersionUID = -2123174316L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SUPPLIERS_SEQ")
    @Index(name="gas_suppliers_pk_i")
    @Column(name="supplierid",nullable = false,length = 19)
    long supplierid;

    @Column(name="suppliername",length = 255)
    java.lang.String suppliername;

    @Column(name="contactname",length = 255)
    java.lang.String contactname;

    @Column(name="address",length = 255)
    java.lang.String address;

    @Column(name="city",length = 255)
    java.lang.String city;

    @Column(name="postalcode",length = 255)
    java.lang.String postalcode;

    @Column(name="country",length = 255)
    java.lang.String country;

    @Column(name="phone",length = 255)
    java.lang.String phone;



  /** Constructor Buit */
  public SuppliersJPA() {
  }

  /** Constructor amb tots els camps  */
  public SuppliersJPA(long supplierid , java.lang.String suppliername , java.lang.String contactname , java.lang.String address , java.lang.String city , java.lang.String postalcode , java.lang.String country , java.lang.String phone) {
    this.supplierid=supplierid;
    this.suppliername=suppliername;
    this.contactname=contactname;
    this.address=address;
    this.city=city;
    this.postalcode=postalcode;
    this.country=country;
    this.phone=phone;
}
  /** Constructor sense valors autoincrementals */
  public SuppliersJPA(java.lang.String suppliername , java.lang.String contactname , java.lang.String address , java.lang.String city , java.lang.String postalcode , java.lang.String country , java.lang.String phone) {
    this.suppliername=suppliername;
    this.contactname=contactname;
    this.address=address;
    this.city=city;
    this.postalcode=postalcode;
    this.country=country;
    this.phone=phone;
}
  /** Constructor dels valors Not Null */
  public SuppliersJPA(long supplierid) {
    this.supplierid=supplierid;
}
  public SuppliersJPA(Suppliers __bean) {
    this.setSupplierid(__bean.getSupplierid());
    this.setSuppliername(__bean.getSuppliername());
    this.setContactname(__bean.getContactname());
    this.setAddress(__bean.getAddress());
    this.setCity(__bean.getCity());
    this.setPostalcode(__bean.getPostalcode());
    this.setCountry(__bean.getCountry());
    this.setPhone(__bean.getPhone());
	}

	public long getSupplierid() {
		return(supplierid);
	};
	public void setSupplierid(long _supplierid_) {
		this.supplierid = _supplierid_;
	};

	public java.lang.String getSuppliername() {
		return(suppliername);
	};
	public void setSuppliername(java.lang.String _suppliername_) {
		this.suppliername = _suppliername_;
	};

	public java.lang.String getContactname() {
		return(contactname);
	};
	public void setContactname(java.lang.String _contactname_) {
		this.contactname = _contactname_;
	};

	public java.lang.String getAddress() {
		return(address);
	};
	public void setAddress(java.lang.String _address_) {
		this.address = _address_;
	};

	public java.lang.String getCity() {
		return(city);
	};
	public void setCity(java.lang.String _city_) {
		this.city = _city_;
	};

	public java.lang.String getPostalcode() {
		return(postalcode);
	};
	public void setPostalcode(java.lang.String _postalcode_) {
		this.postalcode = _postalcode_;
	};

	public java.lang.String getCountry() {
		return(country);
	};
	public void setCountry(java.lang.String _country_) {
		this.country = _country_;
	};

	public java.lang.String getPhone() {
		return(phone);
	};
	public void setPhone(java.lang.String _phone_) {
		this.phone = _phone_;
	};



  @Override
  public boolean equals(Object __obj) {
  boolean __result;
    if (__obj != null && __obj instanceof Suppliers) {
      Suppliers __instance = (Suppliers)__obj;
      __result = true;
      __result = __result && (this.getSupplierid() == __instance.getSupplierid()) ;
    } else {
      __result = false;
    }
    return __result;
  }


 // ---------------  STATIC METHODS ------------------
  public static SuppliersJPA toJPA(Suppliers __bean) {
    if (__bean == null) { return null;}
    SuppliersJPA __tmp = new SuppliersJPA();
    __tmp.setSupplierid(__bean.getSupplierid());
    __tmp.setSuppliername(__bean.getSuppliername());
    __tmp.setContactname(__bean.getContactname());
    __tmp.setAddress(__bean.getAddress());
    __tmp.setCity(__bean.getCity());
    __tmp.setPostalcode(__bean.getPostalcode());
    __tmp.setCountry(__bean.getCountry());
    __tmp.setPhone(__bean.getPhone());
		return __tmp;
	}


  public static SuppliersJPA copyJPA(SuppliersJPA __jpa) {
    return copyJPA(__jpa,new java.util.HashMap<Object,Object>(), null);
  }

  static java.util.Set<SuppliersJPA> copyJPA(java.util.Set<SuppliersJPA> __jpaSet,
    java.util.Map<Object,Object> __alreadyCopied, String origenJPA) {
    if (__jpaSet == null) { return null; }
    @SuppressWarnings("unchecked")
    java.util.Set<SuppliersJPA> __tmpSet = (java.util.Set<SuppliersJPA>) __alreadyCopied.get(__jpaSet);
    if (__tmpSet != null) { return __tmpSet; };
    __tmpSet = new java.util.HashSet<SuppliersJPA>(__jpaSet.size());
    __alreadyCopied.put(__jpaSet, __tmpSet);
    for (SuppliersJPA __jpa : __jpaSet) {
      __tmpSet.add(copyJPA(__jpa, __alreadyCopied, origenJPA));
    }
    return __tmpSet;
  }

  static SuppliersJPA copyJPA(SuppliersJPA __jpa,
    java.util.Map<Object,Object> __alreadyCopied, String origenJPA) {
    if (__jpa == null) { return null; }
    SuppliersJPA __tmp = (SuppliersJPA) __alreadyCopied.get(__jpa);
    if (__tmp != null) { return __tmp; };
    __tmp = toJPA(__jpa);
    __alreadyCopied.put(__jpa, __tmp);
    // Copia de beans complexes (EXP)
    // Copia de beans complexes (IMP)

    return __tmp;
  }




}
