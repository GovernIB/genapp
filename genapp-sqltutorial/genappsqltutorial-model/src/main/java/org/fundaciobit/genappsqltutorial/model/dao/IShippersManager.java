package org.fundaciobit.genappsqltutorial.model.dao;

import org.fundaciobit.genappsqltutorial.model.entity.*;
import org.fundaciobit.genapp.common.i18n.I18NException;


public interface IShippersManager extends org.fundaciobit.genapp.common.query.ITableManager<Shippers, Long> {


	public Shippers create( java.lang.String _shippername_, java.lang.String _phone_) throws I18NException;

	public Shippers findByPrimaryKey(long _shipperid_);

	public void delete(long _shipperid_);

}
