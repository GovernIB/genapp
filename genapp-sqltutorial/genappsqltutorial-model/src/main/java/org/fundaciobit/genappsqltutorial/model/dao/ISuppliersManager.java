package org.fundaciobit.genappsqltutorial.model.dao;

import org.fundaciobit.genappsqltutorial.model.entity.*;
import org.fundaciobit.genapp.common.i18n.I18NException;


public interface ISuppliersManager extends org.fundaciobit.genapp.common.query.ITableManager<Suppliers, Long> {


	public Suppliers create( java.lang.String _suppliername_, java.lang.String _contactname_, java.lang.String _address_, java.lang.String _city_, java.lang.String _postalcode_, java.lang.String _country_, java.lang.String _phone_) throws I18NException;

	public Suppliers findByPrimaryKey(long _supplierid_);

	public void delete(long _supplierid_);

}
