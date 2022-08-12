
package org.fundaciobit.genappsqltutorial.persistence;
import org.fundaciobit.genappsqltutorial.model.entity.*;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.Index;
import javax.persistence.GeneratedValue;
import javax.persistence.SequenceGenerator;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import javax.persistence.Id;


@Entity(name = "OrderDetailsJPA")
@Table(name = "gas_orderdetails" , indexes = { 
        @Index(name="gas_orderdetails_pk_i", columnList = "orderdetailid"),
        @Index(name="gas_orderdetails_orderid_fk_i", columnList = "orderid"),
        @Index(name="gas_orderdetai_productid_fk_i", columnList = "productid")})
@SequenceGenerator(name="ORDERDETAILS_SEQ", sequenceName="gas_orderdetails_seq", allocationSize=1, initialValue=1000)
@javax.xml.bind.annotation.XmlRootElement
public class OrderDetailsJPA implements OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="ORDERDETAILS_SEQ")
    @Column(name="orderdetailid",nullable = false,length = 19)
    long orderdetailid;

    @Column(name="orderid",length = 19)
    java.lang.Long orderid;

    @Column(name="productid",length = 19)
    java.lang.Long productid;

    @Column(name="quantity",length = 10)
    java.lang.Integer quantity;



  /** Constructor Buit */
  public OrderDetailsJPA() {
  }

  /** Constructor amb tots els camps  */
  public OrderDetailsJPA(long orderdetailid , java.lang.Long orderid , java.lang.Long productid , java.lang.Integer quantity) {
    this.orderdetailid=orderdetailid;
    this.orderid=orderid;
    this.productid=productid;
    this.quantity=quantity;
}
  /** Constructor sense valors autoincrementals */
  public OrderDetailsJPA(java.lang.Long orderid , java.lang.Long productid , java.lang.Integer quantity) {
    this.orderid=orderid;
    this.productid=productid;
    this.quantity=quantity;
}
  /** Constructor dels valors Not Null */
  public OrderDetailsJPA(long orderdetailid) {
    this.orderdetailid=orderdetailid;
}
  public OrderDetailsJPA(OrderDetails __bean) {
    this.setOrderdetailid(__bean.getOrderdetailid());
    this.setOrderid(__bean.getOrderid());
    this.setProductid(__bean.getProductid());
    this.setQuantity(__bean.getQuantity());
	}

	public long getOrderdetailid() {
		return(orderdetailid);
	};
	public void setOrderdetailid(long _orderdetailid_) {
		this.orderdetailid = _orderdetailid_;
	};

	public java.lang.Long getOrderid() {
		return(orderid);
	};
	public void setOrderid(java.lang.Long _orderid_) {
		this.orderid = _orderid_;
	};

	public java.lang.Long getProductid() {
		return(productid);
	};
	public void setProductid(java.lang.Long _productid_) {
		this.productid = _productid_;
	};

	public java.lang.Integer getQuantity() {
		return(quantity);
	};
	public void setQuantity(java.lang.Integer _quantity_) {
		this.quantity = _quantity_;
	};



  @Override
  public boolean equals(Object __obj) {
  boolean __result;
    if (__obj != null && __obj instanceof OrderDetails) {
      OrderDetails __instance = (OrderDetails)__obj;
      __result = true;
      __result = __result && (this.getOrderdetailid() == __instance.getOrderdetailid()) ;
    } else {
      __result = false;
    }
    return __result;
  }

// IMP Field:orderid | Table: gas_orders | Type: 1  

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderid", referencedColumnName ="orderid", nullable = true, insertable=false, updatable=false, foreignKey=@ForeignKey(name="gas_orderdetai_orders_fk"))
    private OrdersJPA orders;

    public OrdersJPA getOrders() {
    return this.orders;
  }

    public  void setOrders(OrdersJPA orders) {
    this.orders = orders;
  }

// IMP Field:productid | Table: gas_products | Type: 1  

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productid", referencedColumnName ="productid", nullable = true, insertable=false, updatable=false, foreignKey=@ForeignKey(name="gas_orderdetai_products_fk"))
    private ProductsJPA products;

    public ProductsJPA getProducts() {
    return this.products;
  }

    public  void setProducts(ProductsJPA products) {
    this.products = products;
  }


 // ---------------  STATIC METHODS ------------------
  public static OrderDetailsJPA toJPA(OrderDetails __bean) {
    if (__bean == null) { return null;}
    OrderDetailsJPA __tmp = new OrderDetailsJPA();
    __tmp.setOrderdetailid(__bean.getOrderdetailid());
    __tmp.setOrderid(__bean.getOrderid());
    __tmp.setProductid(__bean.getProductid());
    __tmp.setQuantity(__bean.getQuantity());
		return __tmp;
	}


  public static OrderDetailsJPA copyJPA(OrderDetailsJPA __jpa) {
    return copyJPA(__jpa,new java.util.HashMap<Object,Object>(), null);
  }

  static java.util.Set<OrderDetailsJPA> copyJPA(java.util.Set<OrderDetailsJPA> __jpaSet,
    java.util.Map<Object,Object> __alreadyCopied, String origenJPA) {
    if (__jpaSet == null) { return null; }
    java.util.Set<OrderDetailsJPA> __tmpSet = (java.util.Set<OrderDetailsJPA>) __alreadyCopied.get(__jpaSet);
    if (__tmpSet != null) { return __tmpSet; };
    __tmpSet = new java.util.HashSet<OrderDetailsJPA>(__jpaSet.size());
    __alreadyCopied.put(__jpaSet, __tmpSet);
    for (OrderDetailsJPA __jpa : __jpaSet) {
      __tmpSet.add(copyJPA(__jpa, __alreadyCopied, origenJPA));
    }
    return __tmpSet;
  }

  static OrderDetailsJPA copyJPA(OrderDetailsJPA __jpa,
    java.util.Map<Object,Object> __alreadyCopied, String origenJPA) {
    if (__jpa == null) { return null; }
    OrderDetailsJPA __tmp = (OrderDetailsJPA) __alreadyCopied.get(__jpa);
    if (__tmp != null) { return __tmp; };
    __tmp = toJPA(__jpa);
    __alreadyCopied.put(__jpa, __tmp);
    // Copia de beans complexes (EXP)
    // Copia de beans complexes (IMP)
    if(!"OrdersJPA".equals(origenJPA) && 
       (!org.fundaciobit.genapp.common.utils.Utils.isEmpty(__jpa.orders) || org.hibernate.Hibernate.isInitialized(__jpa.getOrders()) ) ) {
      __tmp.setOrders(OrdersJPA.copyJPA(__jpa.getOrders(), __alreadyCopied,"OrderDetailsJPA"));
    }
    if(!"ProductsJPA".equals(origenJPA) && 
       (!org.fundaciobit.genapp.common.utils.Utils.isEmpty(__jpa.products) || org.hibernate.Hibernate.isInitialized(__jpa.getProducts()) ) ) {
      __tmp.setProducts(ProductsJPA.copyJPA(__jpa.getProducts(), __alreadyCopied,"OrderDetailsJPA"));
    }

    return __tmp;
  }




}
