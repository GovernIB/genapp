package org.fundaciobit.genappsqltutorial.model.dao;

import org.fundaciobit.genappsqltutorial.model.entity.*;
import org.fundaciobit.genapp.common.i18n.I18NException;


public interface IOrderDetailsManager extends org.fundaciobit.genapp.common.query.ITableManager<OrderDetails, Long> {


	public OrderDetails create( java.lang.Long _orderid_, java.lang.Long _productid_, java.lang.Integer _quantity_) throws I18NException;

	public OrderDetails findByPrimaryKey(long _orderdetailid_);

	public void delete(long _orderdetailid_);

}
