
package org.fundaciobit.genappsqltutorial.model.bean;

import org.fundaciobit.genappsqltutorial.model.entity.OrderDetails;


public class OrderDetailsBean implements OrderDetails {



	long orderdetailid;// PK
	java.lang.Long orderid;
	java.lang.Long productid;
	java.lang.Integer quantity;


  /** Constructor Buit */
  public OrderDetailsBean() {
  }

  /** Constructor amb tots els camps  */
  public OrderDetailsBean(long orderdetailid , java.lang.Long orderid , java.lang.Long productid , java.lang.Integer quantity) {
    this.orderdetailid=orderdetailid;
    this.orderid=orderid;
    this.productid=productid;
    this.quantity=quantity;
}
  /** Constructor sense valors autoincrementals */
  public OrderDetailsBean(java.lang.Long orderid , java.lang.Long productid , java.lang.Integer quantity) {
    this.orderid=orderid;
    this.productid=productid;
    this.quantity=quantity;
}
  /** Constructor dels valors Not Null */
  public OrderDetailsBean(long orderdetailid) {
    this.orderdetailid=orderdetailid;
}
  public OrderDetailsBean(OrderDetails __bean) {
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



  // ======================================

  public static OrderDetailsBean toBean(OrderDetails __bean) {
    if (__bean == null) { return null;}
    OrderDetailsBean __tmp = new OrderDetailsBean();
    __tmp.setOrderdetailid(__bean.getOrderdetailid());
    __tmp.setOrderid(__bean.getOrderid());
    __tmp.setProductid(__bean.getProductid());
    __tmp.setQuantity(__bean.getQuantity());
		return __tmp;
	}



}
