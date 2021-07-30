package org.fundaciobit.genappsqltutorial.model.dao;

import org.fundaciobit.genappsqltutorial.model.entity.*;
import org.fundaciobit.genapp.common.i18n.I18NException;


public interface IProductsManager extends org.fundaciobit.genapp.common.query.ITableManager<Products, Long> {


	public Products create( java.lang.String _productname_, java.lang.Long _supplierid_, java.lang.Long _categoryid_, java.lang.String _unit_, java.lang.Integer _price_) throws I18NException;

	public Products findByPrimaryKey(long _productid_);

	public void delete(long _productid_);

}
