
package org.fundaciobit.genappsqltutorial.model.bean;

import org.fundaciobit.genappsqltutorial.model.entity.Orders;


public class OrdersBean implements Orders {



	long orderid;// PK
	java.lang.Long customerid;
	java.lang.Long employeeid;
	java.sql.Date orderdate;
	java.lang.Long shipperid;


  /** Constructor Buit */
  public OrdersBean() {
  }

  /** Constructor amb tots els camps  */
  public OrdersBean(long orderid , java.lang.Long customerid , java.lang.Long employeeid , java.sql.Date orderdate , java.lang.Long shipperid) {
    this.orderid=orderid;
    this.customerid=customerid;
    this.employeeid=employeeid;
    this.orderdate=orderdate;
    this.shipperid=shipperid;
}
  /** Constructor sense valors autoincrementals */
  public OrdersBean(java.lang.Long customerid , java.lang.Long employeeid , java.sql.Date orderdate , java.lang.Long shipperid) {
    this.customerid=customerid;
    this.employeeid=employeeid;
    this.orderdate=orderdate;
    this.shipperid=shipperid;
}
  /** Constructor dels valors Not Null */
  public OrdersBean(long orderid) {
    this.orderid=orderid;
}
  public OrdersBean(Orders __bean) {
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



  // ======================================

  public static OrdersBean toBean(Orders __bean) {
    if (__bean == null) { return null;}
    OrdersBean __tmp = new OrdersBean();
    __tmp.setOrderid(__bean.getOrderid());
    __tmp.setCustomerid(__bean.getCustomerid());
    __tmp.setEmployeeid(__bean.getEmployeeid());
    __tmp.setOrderdate(__bean.getOrderdate());
    __tmp.setShipperid(__bean.getShipperid());
		return __tmp;
	}



}
