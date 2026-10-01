package org.fundaciobit.genappsqltutorial.model.entity;

public interface Orders extends org.fundaciobit.genapp.common.IGenAppEntity {

	public long getOrderid();
	public void setOrderid(long _orderid_);

	public java.lang.Long getCustomerid();
	public void setCustomerid(java.lang.Long _customerid_);

	public java.lang.Long getEmployeeid();
	public void setEmployeeid(java.lang.Long _employeeid_);

	public java.sql.Date getOrderdate();
	public void setOrderdate(java.sql.Date _orderdate_);

	public java.lang.Long getShipperid();
	public void setShipperid(java.lang.Long _shipperid_);



  // ======================================

}
