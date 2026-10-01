package org.fundaciobit.genappsqltutorial.model.entity;

public interface Products extends org.fundaciobit.genapp.common.IGenAppEntity {

	public long getProductid();
	public void setProductid(long _productid_);

	public java.lang.String getProductname();
	public void setProductname(java.lang.String _productname_);

	public java.lang.Long getSupplierid();
	public void setSupplierid(java.lang.Long _supplierid_);

	public java.lang.Long getCategoryid();
	public void setCategoryid(java.lang.Long _categoryid_);

	public java.lang.String getUnit();
	public void setUnit(java.lang.String _unit_);

	public java.lang.Double getPrice();
	public void setPrice(java.lang.Double _price_);



  // ======================================

}
