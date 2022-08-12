
package org.fundaciobit.genappsqltutorial.persistence;
import org.fundaciobit.genappsqltutorial.model.entity.*;
import javax.persistence.Table;
import javax.persistence.Column;
import java.util.HashSet;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.Index;
import javax.persistence.GeneratedValue;
import javax.persistence.SequenceGenerator;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Set;
import javax.persistence.Id;


@Entity(name = "CustomersJPA")
@Table(name = "gas_customers" , indexes = { 
        @Index(name="gas_customers_pk_i", columnList = "customerid")})
@SequenceGenerator(name="CUSTOMERS_SEQ", sequenceName="gas_customers_seq", allocationSize=1, initialValue=1000)
@javax.xml.bind.annotation.XmlRootElement
public class CustomersJPA implements Customers {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="CUSTOMERS_SEQ")
    @Column(name="customerid",nullable = false,length = 19)
    long customerid;

    @Column(name="customername",length = 255)
    java.lang.String customername;

    @Column(name="contactname",length = 255)
    java.lang.String contactname;

    @Column(name="address",length = 255)
    java.lang.String address;

    @Column(name="city",length = 255)
    java.lang.String city;

    @Column(name="country",length = 255)
    java.lang.String country;

    @Column(name="postalcode",length = 255)
    java.lang.String postalcode;



  /** Constructor Buit */
  public CustomersJPA() {
  }

  /** Constructor amb tots els camps  */
  public CustomersJPA(long customerid , java.lang.String customername , java.lang.String contactname , java.lang.String address , java.lang.String city , java.lang.String country , java.lang.String postalcode) {
    this.customerid=customerid;
    this.customername=customername;
    this.contactname=contactname;
    this.address=address;
    this.city=city;
    this.country=country;
    this.postalcode=postalcode;
}
  /** Constructor sense valors autoincrementals */
  public CustomersJPA(java.lang.String customername , java.lang.String contactname , java.lang.String address , java.lang.String city , java.lang.String country , java.lang.String postalcode) {
    this.customername=customername;
    this.contactname=contactname;
    this.address=address;
    this.city=city;
    this.country=country;
    this.postalcode=postalcode;
}
  /** Constructor dels valors Not Null */
  public CustomersJPA(long customerid) {
    this.customerid=customerid;
}
  public CustomersJPA(Customers __bean) {
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



  @Override
  public boolean equals(Object __obj) {
  boolean __result;
    if (__obj != null && __obj instanceof Customers) {
      Customers __instance = (Customers)__obj;
      __result = true;
      __result = __result && (this.getCustomerid() == __instance.getCustomerid()) ;
    } else {
      __result = false;
    }
    return __result;
  }

// EXP  Field:customerid | Table: gas_orders | Type: 0  

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customers")
    private Set<OrdersJPA> orderss = new HashSet<OrdersJPA>(0);
    public  Set<OrdersJPA> getOrderss() {
    return this.orderss;
  }

    public void setOrderss(Set<OrdersJPA> orderss) {
      this.orderss = orderss;
    }



 // ---------------  STATIC METHODS ------------------
  public static CustomersJPA toJPA(Customers __bean) {
    if (__bean == null) { return null;}
    CustomersJPA __tmp = new CustomersJPA();
    __tmp.setCustomerid(__bean.getCustomerid());
    __tmp.setCustomername(__bean.getCustomername());
    __tmp.setContactname(__bean.getContactname());
    __tmp.setAddress(__bean.getAddress());
    __tmp.setCity(__bean.getCity());
    __tmp.setCountry(__bean.getCountry());
    __tmp.setPostalcode(__bean.getPostalcode());
		return __tmp;
	}


  public static CustomersJPA copyJPA(CustomersJPA __jpa) {
    return copyJPA(__jpa,new java.util.HashMap<Object,Object>(), null);
  }

  static java.util.Set<CustomersJPA> copyJPA(java.util.Set<CustomersJPA> __jpaSet,
    java.util.Map<Object,Object> __alreadyCopied, String origenJPA) {
    if (__jpaSet == null) { return null; }
    java.util.Set<CustomersJPA> __tmpSet = (java.util.Set<CustomersJPA>) __alreadyCopied.get(__jpaSet);
    if (__tmpSet != null) { return __tmpSet; };
    __tmpSet = new java.util.HashSet<CustomersJPA>(__jpaSet.size());
    __alreadyCopied.put(__jpaSet, __tmpSet);
    for (CustomersJPA __jpa : __jpaSet) {
      __tmpSet.add(copyJPA(__jpa, __alreadyCopied, origenJPA));
    }
    return __tmpSet;
  }

  static CustomersJPA copyJPA(CustomersJPA __jpa,
    java.util.Map<Object,Object> __alreadyCopied, String origenJPA) {
    if (__jpa == null) { return null; }
    CustomersJPA __tmp = (CustomersJPA) __alreadyCopied.get(__jpa);
    if (__tmp != null) { return __tmp; };
    __tmp = toJPA(__jpa);
    __alreadyCopied.put(__jpa, __tmp);
    // Copia de beans complexes (EXP)
    if(!"OrdersJPA".equals(origenJPA) 
       && ( !org.fundaciobit.genapp.common.utils.Utils.isEmpty(__jpa.orderss) || org.hibernate.Hibernate.isInitialized(__jpa.getOrderss())) ) {
      __tmp.setOrderss(OrdersJPA.copyJPA(__jpa.getOrderss(), __alreadyCopied,"CustomersJPA"));
    }
    // Copia de beans complexes (IMP)

    return __tmp;
  }




}
