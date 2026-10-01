package org.fundaciobit.genappsqltutorial.model.dao;

import org.fundaciobit.genappsqltutorial.model.entity.*;
import org.fundaciobit.genapp.common.i18n.I18NException;


public interface ICustomersManager extends org.fundaciobit.genapp.common.query.ITableManager<Customers, Long> {


	public Customers create( java.lang.String _customername_, java.lang.String _contactname_, java.lang.String _address_, java.lang.String _city_, java.lang.String _country_, java.lang.String _postalcode_) throws I18NException;

	public Customers findByPrimaryKey(long _customerid_);

	public void delete(long _customerid_);

}
