
package org.fundaciobit.genappsqltutorial.model.bean;

import org.fundaciobit.genappsqltutorial.model.entity.Shippers;


public class ShippersBean implements Shippers {



	long shipperid;// PK
	java.lang.String shippername;
	java.lang.String phone;


  /** Constructor Buit */
  public ShippersBean() {
  }

  /** Constructor amb tots els camps  */
  public ShippersBean(long shipperid , java.lang.String shippername , java.lang.String phone) {
    this.shipperid=shipperid;
    this.shippername=shippername;
    this.phone=phone;
}
  /** Constructor sense valors autoincrementals */
  public ShippersBean(java.lang.String shippername , java.lang.String phone) {
    this.shippername=shippername;
    this.phone=phone;
}
  /** Constructor dels valors Not Null */
  public ShippersBean(long shipperid) {
    this.shipperid=shipperid;
}
  public ShippersBean(Shippers __bean) {
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



  // ======================================

  public static ShippersBean toBean(Shippers __bean) {
    if (__bean == null) { return null;}
    ShippersBean __tmp = new ShippersBean();
    __tmp.setShipperid(__bean.getShipperid());
    __tmp.setShippername(__bean.getShippername());
    __tmp.setPhone(__bean.getPhone());
		return __tmp;
	}



}
