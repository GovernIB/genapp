
package org.fundaciobit.genappsqltutorial.persistence;
import org.fundaciobit.genappsqltutorial.model.entity.*;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import org.hibernate.annotations.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.FetchType;
import javax.persistence.Id;


@SuppressWarnings("deprecation")
@Entity
@Table(name = "gas_orders" )
@SequenceGenerator(name="ORDERS_SEQ", sequenceName="gas_orders_seq", allocationSize=1, initialValue=1000)
@javax.xml.bind.annotation.XmlRootElement
public class OrdersJPA implements Orders {



private static final long serialVersionUID = 1613280918L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="ORDERS_SEQ")
    @Index(name="gas_orders_pk_i")
    @Column(name="orderid",nullable = false,length = 19)
    long orderid;

    @Index(name="gas_orders_customerid_fk_i")
    @Column(name="customerid",length = 19)
    java.lang.Long customerid;

    @Index(name="gas_orders_employeeid_fk_i")
    @Column(name="employeeid",length = 19)
    java.lang.Long employeeid;

    @Column(name="orderdate",length = 13)
    java.sql.Date orderdate;

    @Index(name="gas_orders_shipperid_fk_i")
    @Column(name="shipperid",length = 19)
    java.lang.Long shipperid;



  /** Constructor Buit */
  public OrdersJPA() {
  }

  /** Constructor amb tots els camps  */
  public OrdersJPA(long orderid , java.lang.Long customerid , java.lang.Long employeeid , java.sql.Date orderdate , java.lang.Long shipperid) {
    this.orderid=orderid;
    this.customerid=customerid;
    this.employeeid=employeeid;
    this.orderdate=orderdate;
    this.shipperid=shipperid;
}
  /** Constructor sense valors autoincrementals */
  public OrdersJPA(java.lang.Long customerid , java.lang.Long employeeid , java.sql.Date orderdate , java.lang.Long shipperid) {
    this.customerid=customerid;
    this.employeeid=employeeid;
    this.orderdate=orderdate;
    this.shipperid=shipperid;
}
  /** Constructor dels valors Not Null */
  public OrdersJPA(long orderid) {
    this.orderid=orderid;
}
  public OrdersJPA(Orders __bean) {
    this.setOrderid(__bean.getOrderid());
    this.setCustomerid(__bean.getCustomerid());
    this.setEmployeeid(__bean.getEmployeeid());
    this.setOrderdate(__bean.getOrderdate());
    this.setShipperid(__bean.getShipperid());
	}

	public long getOrderid() {
		return(orderid);
	};
	public void setOrderid(long _orderid_) {
		this.orderid = _orderid_;
	};

	public java.lang.Long getCustomerid() {
		return(customerid);
	};
	public void setCustomerid(java.lang.Long _customerid_) {
		this.customerid = _customerid_;
	};

	public java.lang.Long getEmployeeid() {
		return(employeeid);
	};
	public void setEmployeeid(java.lang.Long _employeeid_) {
		this.employeeid = _employeeid_;
	};

	public java.sql.Date getOrderdate() {
		return(orderdate);
	};
	public void setOrderdate(java.sql.Date _orderdate_) {
		this.orderdate = _orderdate_;
	};

	public java.lang.Long getShipperid() {
		return(shipperid);
	};
	public void setShipperid(java.lang.Long _shipperid_) {
		this.shipperid = _shipperid_;
	};



  @Override
  public boolean equals(Object __obj) {
  boolean __result;
    if (__obj != null && __obj instanceof Orders) {
      Orders __instance = (Orders)__obj;
      __result = true;
      __result = __result && (this.getOrderid() == __instance.getOrderid()) ;
    } else {
      __result = false;
    }
    return __result;
  }

// EXP  Field:orderid | Table: gas_orderdetails | Type: 0  

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orders")
    private Set<OrderDetailsJPA> orderDetailss = new HashSet<OrderDetailsJPA>(0);
    public  Set<OrderDetailsJPA> getOrderDetailss() {
    return this.orderDetailss;
  }

    public void setOrderDetailss(Set<OrderDetailsJPA> orderDetailss) {
      this.orderDetailss = orderDetailss;
    }


// IMP Field:customerid | Table: gas_customers | Type: 1  

    @ManyToOne(fetch = FetchType.LAZY)
    @ForeignKey(name="gas_orders_customers_fk")
    @JoinColumn(name = "customerid", referencedColumnName ="customerid", nullable = true, insertable=false, updatable=false)
    private CustomersJPA customers;

    public CustomersJPA getCustomers() {
    return this.customers;
  }

    public  void setCustomers(CustomersJPA customers) {
    this.customers = customers;
  }

// IMP Field:employeeid | Table: gas_employees | Type: 1  

    @ManyToOne(fetch = FetchType.LAZY)
    @ForeignKey(name="gas_orders_employees_fk")
    @JoinColumn(name = "employeeid", referencedColumnName ="employeeid", nullable = true, insertable=false, updatable=false)
    private EmployeesJPA employees;

    public EmployeesJPA getEmployees() {
    return this.employees;
  }

    public  void setEmployees(EmployeesJPA employees) {
    this.employees = employees;
  }

// IMP Field:shipperid | Table: gas_shippers | Type: 1  

    @ManyToOne(fetch = FetchType.LAZY)
    @ForeignKey(name="gas_orders_shippers_fk")
    @JoinColumn(name = "shipperid", referencedColumnName ="shipperid", nullable = true, insertable=false, updatable=false)
    private ShippersJPA shippers;

    public ShippersJPA getShippers() {
    return this.shippers;
  }

    public  void setShippers(ShippersJPA shippers) {
    this.shippers = shippers;
  }


 // ---------------  STATIC METHODS ------------------
  public static OrdersJPA toJPA(Orders __bean) {
    if (__bean == null) { return null;}
    OrdersJPA __tmp = new OrdersJPA();
    __tmp.setOrderid(__bean.getOrderid());
    __tmp.setCustomerid(__bean.getCustomerid());
    __tmp.setEmployeeid(__bean.getEmployeeid());
    __tmp.setOrderdate(__bean.getOrderdate());
    __tmp.setShipperid(__bean.getShipperid());
		return __tmp;
	}


  public static OrdersJPA copyJPA(OrdersJPA __jpa) {
    return copyJPA(__jpa,new java.util.HashMap<Object,Object>(), null);
  }

  static java.util.Set<OrdersJPA> copyJPA(java.util.Set<OrdersJPA> __jpaSet,
    java.util.Map<Object,Object> __alreadyCopied, String origenJPA) {
    if (__jpaSet == null) { return null; }
    java.util.Set<OrdersJPA> __tmpSet = (java.util.Set<OrdersJPA>) __alreadyCopied.get(__jpaSet);
    if (__tmpSet != null) { return __tmpSet; };
    __tmpSet = new java.util.HashSet<OrdersJPA>(__jpaSet.size());
    __alreadyCopied.put(__jpaSet, __tmpSet);
    for (OrdersJPA __jpa : __jpaSet) {
      __tmpSet.add(copyJPA(__jpa, __alreadyCopied, origenJPA));
    }
    return __tmpSet;
  }

  static OrdersJPA copyJPA(OrdersJPA __jpa,
    java.util.Map<Object,Object> __alreadyCopied, String origenJPA) {
    if (__jpa == null) { return null; }
    OrdersJPA __tmp = (OrdersJPA) __alreadyCopied.get(__jpa);
    if (__tmp != null) { return __tmp; };
    __tmp = toJPA(__jpa);
    __alreadyCopied.put(__jpa, __tmp);
    // Copia de beans complexes (EXP)
    if(!"OrderDetailsJPA".equals(origenJPA) 
       && ( !org.fundaciobit.genapp.common.utils.Utils.isEmpty(__jpa.orderDetailss) || org.hibernate.Hibernate.isInitialized(__jpa.getOrderDetailss())) ) {
      __tmp.setOrderDetailss(OrderDetailsJPA.copyJPA(__jpa.getOrderDetailss(), __alreadyCopied,"OrdersJPA"));
    }
    // Copia de beans complexes (IMP)
    if(!"CustomersJPA".equals(origenJPA) && 
       (!org.fundaciobit.genapp.common.utils.Utils.isEmpty(__jpa.customers) || org.hibernate.Hibernate.isInitialized(__jpa.getCustomers()) ) ) {
      __tmp.setCustomers(CustomersJPA.copyJPA(__jpa.getCustomers(), __alreadyCopied,"OrdersJPA"));
    }
    if(!"ShippersJPA".equals(origenJPA) && 
       (!org.fundaciobit.genapp.common.utils.Utils.isEmpty(__jpa.shippers) || org.hibernate.Hibernate.isInitialized(__jpa.getShippers()) ) ) {
      __tmp.setShippers(ShippersJPA.copyJPA(__jpa.getShippers(), __alreadyCopied,"OrdersJPA"));
    }
    if(!"EmployeesJPA".equals(origenJPA) && 
       (!org.fundaciobit.genapp.common.utils.Utils.isEmpty(__jpa.employees) || org.hibernate.Hibernate.isInitialized(__jpa.getEmployees()) ) ) {
      __tmp.setEmployees(EmployeesJPA.copyJPA(__jpa.getEmployees(), __alreadyCopied,"OrdersJPA"));
    }

    return __tmp;
  }




}
