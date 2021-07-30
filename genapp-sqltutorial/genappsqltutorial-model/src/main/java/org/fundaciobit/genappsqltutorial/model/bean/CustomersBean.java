
package org.fundaciobit.genappsqltutorial.model.bean;

import org.fundaciobit.genappsqltutorial.model.entity.Customers;


public class CustomersBean implements Customers {



private static final long serialVersionUID = -1327693435L;

	long customerid;// PK
	java.lang.String customername;
	java.lang.String contactname;
	java.lang.String address;
	java.lang.String city;
	java.lang.String country;
	java.lang.String postalcode;


  /** Constructor Buit */
  public CustomersBean() {
  }

  /** Constructor amb tots els camps  */
  public CustomersBean(long customerid , java.lang.String customername , java.lang.String contactname , java.lang.String address , java.lang.String city , java.lang.String country , java.lang.String postalcode) {
    this.customerid=customerid;
    this.customername=customername;
    this.contactname=contactname;
    this.address=address;
    this.city=city;
    this.country=country;
    this.postalcode=postalcode;
}
  /** Constructor sense valors autoincrementals */
  public CustomersBean(java.lang.String customername , java.lang.String contactname , java.lang.String address , java.lang.String city , java.lang.String country , java.lang.String postalcode) {
    this.customername=customername;
    this.contactname=contactname;
    this.address=address;
    this.city=city;
    this.country=country;
    this.postalcode=postalcode;
}
  /** Constructor dels valors Not Null */
  public CustomersBean(long customerid) {
    this.customerid=customerid;
}
  public CustomersBean(Customers __bean) {
    this.setCustomerid(__bean.getCustomerid());
    this.setCustomername(__bean.getCustomername());
    this.setContactname(__bean.getContactname());
    this.setAddress(__bean.getAddress());
    this.setCity(__bean.getCity());
    this.setCountry(__bean.getCountry());
    this.setPostalcode(__bean.getPostalcode());
	}

	public long getCustomerid() {
		return(customerid);
	};
	public void setCustomerid(long _customerid_) {
		this.customerid = _customerid_;
	};

	public java.lang.String getCustomername() {
		return(customername);
	};
	public void setCustomername(java.lang.String _customername_) {
		this.customername = _customername_;
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

	public java.lang.String getCountry() {
		return(country);
	};
	public void setCountry(java.lang.String _country_) {
		this.country = _country_;
	};

	public java.lang.String getPostalcode() {
		return(postalcode);
	};
	public void setPostalcode(java.lang.String _postalcode_) {
		this.postalcode = _postalcode_;
	};



  // ======================================

  public static CustomersBean toBean(Customers __bean) {
    if (__bean == null) { return null;}
    CustomersBean __tmp = new CustomersBean();
    __tmp.setCustomerid(__bean.getCustomerid());
    __tmp.setCustomername(__bean.getCustomername());
    __tmp.setContactname(__bean.getContactname());
    __tmp.setAddress(__bean.getAddress());
    __tmp.setCity(__bean.getCity());
    __tmp.setCountry(__bean.getCountry());
    __tmp.setPostalcode(__bean.getPostalcode());
		return __tmp;
	}



}
