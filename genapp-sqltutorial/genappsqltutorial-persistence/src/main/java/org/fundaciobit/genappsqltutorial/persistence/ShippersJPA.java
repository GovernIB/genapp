
package org.fundaciobit.genappsqltutorial.persistence;
import org.fundaciobit.genappsqltutorial.model.entity.*;
import javax.persistence.Table;
import javax.persistence.Column;
import java.util.HashSet;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import org.hibernate.annotations.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Set;
import javax.persistence.Id;


@Entity
@Table(name = "gas_shippers" )
@SequenceGenerator(name="SHIPPERS_SEQ", sequenceName="gas_shippers_seq", allocationSize=1, initialValue=1000)
@javax.xml.bind.annotation.XmlRootElement
public class ShippersJPA implements Shippers {



private static final long serialVersionUID = -1642048727L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SHIPPERS_SEQ")
    @Index(name="gas_shippers_pk_i")
    @Column(name="shipperid",nullable = false,length = 19)
    long shipperid;

    @Column(name="shippername",length = 255)
    java.lang.String shippername;

    @Column(name="phone",length = 255)
    java.lang.String phone;



  /** Constructor Buit */
  public ShippersJPA() {
  }

  /** Constructor amb tots els camps  */
  public ShippersJPA(long shipperid , java.lang.String shippername , java.lang.String phone) {
    this.shipperid=shipperid;
    this.shippername=shippername;
    this.phone=phone;
}
  /** Constructor sense valors autoincrementals */
  public ShippersJPA(java.lang.String shippername , java.lang.String phone) {
    this.shippername=shippername;
    this.phone=phone;
}
  /** Constructor dels valors Not Null */
  public ShippersJPA(long shipperid) {
    this.shipperid=shipperid;
}
  public ShippersJPA(Shippers __bean) {
    this.setShipperid(__bean.getShipperid());
    this.setShippername(__bean.getShippername());
    this.setPhone(__bean.getPhone());
	}

	public long getShipperid() {
		return(shipperid);
	};
	public void setShipperid(long _shipperid_) {
		this.shipperid = _shipperid_;
	};

	public java.lang.String getShippername() {
		return(shippername);
	};
	public void setShippername(java.lang.String _shippername_) {
		this.shippername = _shippername_;
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
    if (__obj != null && __obj instanceof Shippers) {
      Shippers __instance = (Shippers)__obj;
      __result = true;
      __result = __result && (this.getShipperid() == __instance.getShipperid()) ;
    } else {
      __result = false;
    }
    return __result;
  }

// EXP  Field:shipperid | Table: gas_orders | Type: 0  

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shippers")
    private Set<OrdersJPA> orderss = new HashSet<OrdersJPA>(0);
    public  Set<OrdersJPA> getOrderss() {
    return this.orderss;
  }

    public void setOrderss(Set<OrdersJPA> orderss) {
      this.orderss = orderss;
    }



 // ---------------  STATIC METHODS ------------------
  public static ShippersJPA toJPA(Shippers __bean) {
    if (__bean == null) { return null;}
    ShippersJPA __tmp = new ShippersJPA();
    __tmp.setShipperid(__bean.getShipperid());
    __tmp.setShippername(__bean.getShippername());
    __tmp.setPhone(__bean.getPhone());
		return __tmp;
	}


  public static ShippersJPA copyJPA(ShippersJPA __jpa) {
    return copyJPA(__jpa,new java.util.HashMap<Object,Object>(), null);
  }

  static java.util.Set<ShippersJPA> copyJPA(java.util.Set<ShippersJPA> __jpaSet,
    java.util.Map<Object,Object> __alreadyCopied, String origenJPA) {
    if (__jpaSet == null) { return null; }
    java.util.Set<ShippersJPA> __tmpSet = (java.util.Set<ShippersJPA>) __alreadyCopied.get(__jpaSet);
    if (__tmpSet != null) { return __tmpSet; };
    __tmpSet = new java.util.HashSet<ShippersJPA>(__jpaSet.size());
    __alreadyCopied.put(__jpaSet, __tmpSet);
    for (ShippersJPA __jpa : __jpaSet) {
      __tmpSet.add(copyJPA(__jpa, __alreadyCopied, origenJPA));
    }
    return __tmpSet;
  }

  static ShippersJPA copyJPA(ShippersJPA __jpa,
    java.util.Map<Object,Object> __alreadyCopied, String origenJPA) {
    if (__jpa == null) { return null; }
    ShippersJPA __tmp = (ShippersJPA) __alreadyCopied.get(__jpa);
    if (__tmp != null) { return __tmp; };
    __tmp = toJPA(__jpa);
    __alreadyCopied.put(__jpa, __tmp);
    // Copia de beans complexes (EXP)
    if(!"OrdersJPA".equals(origenJPA) 
       && ( !org.fundaciobit.genapp.common.utils.Utils.isEmpty(__jpa.orderss) || org.hibernate.Hibernate.isInitialized(__jpa.getOrderss())) ) {
      __tmp.setOrderss(OrdersJPA.copyJPA(__jpa.getOrderss(), __alreadyCopied,"ShippersJPA"));
    }
    // Copia de beans complexes (IMP)

    return __tmp;
  }




}
