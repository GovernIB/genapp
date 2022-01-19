
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
@Table(name = "gas_products" )
@SequenceGenerator(name="PRODUCTS_SEQ", sequenceName="gas_products_seq", allocationSize=1, initialValue=1000)
@javax.xml.bind.annotation.XmlRootElement
public class ProductsJPA implements Products {



private static final long serialVersionUID = 177130423L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="PRODUCTS_SEQ")
    @Index(name="gas_products_pk_i")
    @Column(name="productid",nullable = false,length = 19)
    long productid;

    @Column(name="productname",length = 255)
    java.lang.String productname;

    @Column(name="supplierid",length = 19)
    java.lang.Long supplierid;

    @Column(name="categoryid",length = 19)
    java.lang.Long categoryid;

    @Column(name="unit",length = 255)
    java.lang.String unit;

    @Column(name="price",length = 17,precision = 17)
    java.lang.Double price;



  /** Constructor Buit */
  public ProductsJPA() {
  }

  /** Constructor amb tots els camps  */
  public ProductsJPA(long productid , java.lang.String productname , java.lang.Long supplierid , java.lang.Long categoryid , java.lang.String unit , java.lang.Double price) {
    this.productid=productid;
    this.productname=productname;
    this.supplierid=supplierid;
    this.categoryid=categoryid;
    this.unit=unit;
    this.price=price;
}
  /** Constructor sense valors autoincrementals */
  public ProductsJPA(java.lang.String productname , java.lang.Long supplierid , java.lang.Long categoryid , java.lang.String unit , java.lang.Double price) {
    this.productname=productname;
    this.supplierid=supplierid;
    this.categoryid=categoryid;
    this.unit=unit;
    this.price=price;
}
  /** Constructor dels valors Not Null */
  public ProductsJPA(long productid) {
    this.productid=productid;
}
  public ProductsJPA(Products __bean) {
    this.setProductid(__bean.getProductid());
    this.setProductname(__bean.getProductname());
    this.setSupplierid(__bean.getSupplierid());
    this.setCategoryid(__bean.getCategoryid());
    this.setUnit(__bean.getUnit());
    this.setPrice(__bean.getPrice());
	}

	public long getProductid() {
		return(productid);
	};
	public void setProductid(long _productid_) {
		this.productid = _productid_;
	};

	public java.lang.String getProductname() {
		return(productname);
	};
	public void setProductname(java.lang.String _productname_) {
		this.productname = _productname_;
	};

	public java.lang.Long getSupplierid() {
		return(supplierid);
	};
	public void setSupplierid(java.lang.Long _supplierid_) {
		this.supplierid = _supplierid_;
	};

	public java.lang.Long getCategoryid() {
		return(categoryid);
	};
	public void setCategoryid(java.lang.Long _categoryid_) {
		this.categoryid = _categoryid_;
	};

	public java.lang.String getUnit() {
		return(unit);
	};
	public void setUnit(java.lang.String _unit_) {
		this.unit = _unit_;
	};

	public java.lang.Double getPrice() {
		return(price);
	};
	public void setPrice(java.lang.Double _price_) {
		this.price = _price_;
	};



  @Override
  public boolean equals(Object __obj) {
  boolean __result;
    if (__obj != null && __obj instanceof Products) {
      Products __instance = (Products)__obj;
      __result = true;
      __result = __result && (this.getProductid() == __instance.getProductid()) ;
    } else {
      __result = false;
    }
    return __result;
  }

// EXP  Field:productid | Table: gas_orderdetails | Type: 0  

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "products")
    private Set<OrderDetailsJPA> orderDetailss = new HashSet<OrderDetailsJPA>(0);
    public  Set<OrderDetailsJPA> getOrderDetailss() {
    return this.orderDetailss;
  }

    public void setOrderDetailss(Set<OrderDetailsJPA> orderDetailss) {
      this.orderDetailss = orderDetailss;
    }



 // ---------------  STATIC METHODS ------------------
  public static ProductsJPA toJPA(Products __bean) {
    if (__bean == null) { return null;}
    ProductsJPA __tmp = new ProductsJPA();
    __tmp.setProductid(__bean.getProductid());
    __tmp.setProductname(__bean.getProductname());
    __tmp.setSupplierid(__bean.getSupplierid());
    __tmp.setCategoryid(__bean.getCategoryid());
    __tmp.setUnit(__bean.getUnit());
    __tmp.setPrice(__bean.getPrice());
		return __tmp;
	}


  public static ProductsJPA copyJPA(ProductsJPA __jpa) {
    return copyJPA(__jpa,new java.util.HashMap<Object,Object>(), null);
  }

  static java.util.Set<ProductsJPA> copyJPA(java.util.Set<ProductsJPA> __jpaSet,
    java.util.Map<Object,Object> __alreadyCopied, String origenJPA) {
    if (__jpaSet == null) { return null; }
    java.util.Set<ProductsJPA> __tmpSet = (java.util.Set<ProductsJPA>) __alreadyCopied.get(__jpaSet);
    if (__tmpSet != null) { return __tmpSet; };
    __tmpSet = new java.util.HashSet<ProductsJPA>(__jpaSet.size());
    __alreadyCopied.put(__jpaSet, __tmpSet);
    for (ProductsJPA __jpa : __jpaSet) {
      __tmpSet.add(copyJPA(__jpa, __alreadyCopied, origenJPA));
    }
    return __tmpSet;
  }

  static ProductsJPA copyJPA(ProductsJPA __jpa,
    java.util.Map<Object,Object> __alreadyCopied, String origenJPA) {
    if (__jpa == null) { return null; }
    ProductsJPA __tmp = (ProductsJPA) __alreadyCopied.get(__jpa);
    if (__tmp != null) { return __tmp; };
    __tmp = toJPA(__jpa);
    __alreadyCopied.put(__jpa, __tmp);
    // Copia de beans complexes (EXP)
    if(!"OrderDetailsJPA".equals(origenJPA) 
       && ( !org.fundaciobit.genapp.common.utils.Utils.isEmpty(__jpa.orderDetailss) || org.hibernate.Hibernate.isInitialized(__jpa.getOrderDetailss())) ) {
      __tmp.setOrderDetailss(OrderDetailsJPA.copyJPA(__jpa.getOrderDetailss(), __alreadyCopied,"ProductsJPA"));
    }
    // Copia de beans complexes (IMP)

    return __tmp;
  }




}
