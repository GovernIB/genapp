package org.fundaciobit.genappsqltutorial.model.dao;

import org.fundaciobit.genappsqltutorial.model.entity.*;
import org.fundaciobit.genapp.common.i18n.I18NException;


public interface IEmployeesManager extends org.fundaciobit.genapp.common.query.ITableManager<Employees, Long> {


	public Employees create( java.lang.String _lastname_, java.lang.String _firstname_, java.sql.Date _birthdate_, java.lang.String _photo_, java.lang.String _notes_) throws I18NException;

	public Employees findByPrimaryKey(long _employeeid_);

	public void delete(long _employeeid_);

}
