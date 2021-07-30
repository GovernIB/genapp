
package org.fundaciobit.genappsqltutorial.model.bean;

import org.fundaciobit.genappsqltutorial.model.entity.Suppliers;


public class SuppliersBean implements Suppliers {



private static final long serialVersionUID = -1394111401L;

	long supplierid;// PK
	java.lang.String suppliername;
	java.lang.String contactname;
	java.lang.String address;
	java.lang.String city;
	java.lang.String postalcode;
	java.lang.String country;
	java.lang.String phone;


  /** Constructor Buit */
  public SuppliersBean() {
  }

  /** Constructor amb tots els camps  */
  public SuppliersBean(long supplierid , java.lang.String suppliername , java.lang.String contactname , java.lang.String address , java.lang.String city , java.lang.String postalcode , java.lang.String country , java.lang.String phone) {
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
  public SuppliersBean(java.lang.String suppliername , java.lang.String contactname , java.lang.String address , java.lang.String city , java.lang.String postalcode , java.lang.String country , java.lang.String phone) {
    this.suppliername=suppliername;
    this.contactname=contactname;
    this.address=address;
    this.city=city;
    this.postalcode=postalcode;
    this.country=country;
    this.phone=phone;
}
  /** Constructor dels valors Not Null */
  public SuppliersBean(long supplierid) {
    this.supplierid=supplierid;
}
  public SuppliersBean(Suppliers __bean) {
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



  // ======================================

  public static SuppliersBean toBean(Suppliers __bean) {
    if (__bean == null) { return null;}
    SuppliersBean __tmp = new SuppliersBean();
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



}
