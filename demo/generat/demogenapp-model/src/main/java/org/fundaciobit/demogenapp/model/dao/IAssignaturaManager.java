package org.fundaciobit.demogenapp.model.dao;

import org.fundaciobit.demogenapp.model.entity.*;
import org.fundaciobit.genapp.common.i18n.I18NException;


public interface IAssignaturaManager extends org.fundaciobit.genapp.common.query.ITableManager<Assignatura, Long> {


	public Assignatura create( java.lang.String _nom_, int _credits_, java.lang.Integer _diaSetmana_, java.sql.Time _hora_, java.lang.String _descripcio_) throws I18NException;

	public Assignatura findByPrimaryKey(long _assignaturaID_);

	public void delete(long _assignaturaID_);

}
