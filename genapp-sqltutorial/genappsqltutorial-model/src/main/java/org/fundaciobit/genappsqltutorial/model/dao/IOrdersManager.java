package org.fundaciobit.genappsqltutorial.model.dao;

import org.fundaciobit.genappsqltutorial.model.entity.*;
import org.fundaciobit.genapp.common.i18n.I18NException;


public interface IOrdersManager extends org.fundaciobit.genapp.common.query.ITableManager<Orders, Long> {


	public Orders create( java.lang.Long _customerid_, java.lang.Long _employeeid_, java.sql.Date _orderdate_, java.lang.Long _shipperid_) throws I18NException;

	public Orders findByPrimaryKey(long _orderid_);

	public void delete(long _orderid_);

}
