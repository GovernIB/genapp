
package org.fundaciobit.genappsqltutorial.model.bean;

import org.fundaciobit.genappsqltutorial.model.entity.Products;


public class ProductsBean implements Products {



private static final long serialVersionUID = 1195858772L;

	long productid;// PK
	java.lang.String productname;
	java.lang.Long supplierid;
	java.lang.Long categoryid;
	java.lang.String unit;
	java.lang.Integer price;


  /** Constructor Buit */
  public ProductsBean() {
  }

  /** Constructor amb tots els camps  */
  public ProductsBean(long productid , java.lang.String productname , java.lang.Long supplierid , java.lang.Long categoryid , java.lang.String unit , java.lang.Integer price) {
    this.productid=productid;
    this.productname=productname;
    this.supplierid=supplierid;
    this.categoryid=categoryid;
    this.unit=unit;
    this.price=price;
}
  /** Constructor sense valors autoincrementals */
  public ProductsBean(java.lang.String productname , java.lang.Long supplierid , java.lang.Long categoryid , java.lang.String unit , java.lang.Integer price) {
    this.productname=productname;
    this.supplierid=supplierid;
    this.categoryid=categoryid;
    this.unit=unit;
    this.price=price;
}
  /** Constructor dels valors Not Null */
  public ProductsBean(long productid) {
    this.productid=productid;
}
  public ProductsBean(Products __bean) {
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

	public java.lang.Integer getPrice() {
		return(price);
	};
	public void setPrice(java.lang.Integer _price_) {
		this.price = _price_;
	};



  // ======================================

  public static ProductsBean toBean(Products __bean) {
    if (__bean == null) { return null;}
    ProductsBean __tmp = new ProductsBean();
    __tmp.setProductid(__bean.getProductid());
    __tmp.setProductname(__bean.getProductname());
    __tmp.setSupplierid(__bean.getSupplierid());
    __tmp.setCategoryid(__bean.getCategoryid());
    __tmp.setUnit(__bean.getUnit());
    __tmp.setPrice(__bean.getPrice());
		return __tmp;
	}



}
