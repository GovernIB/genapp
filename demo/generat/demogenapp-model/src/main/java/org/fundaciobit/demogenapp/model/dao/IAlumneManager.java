package org.fundaciobit.demogenapp.model.dao;

import org.fundaciobit.demogenapp.model.entity.*;
import org.fundaciobit.genapp.common.i18n.I18NException;


public interface IAlumneManager extends org.fundaciobit.genapp.common.query.ITableManager<Alumne, Long> {


	public Alumne create( java.lang.String _nom_, java.lang.String _idiomaID_, java.sql.Date _dataNaixement_, java.lang.Boolean _sexe_, boolean _actiu_, java.sql.Timestamp _darrerAcces_, java.lang.Long _fotoID_, java.lang.Long _titolAcademicID_, java.sql.Time _despertador_, java.lang.String _paginaWeb_, java.lang.String _descripcio_) throws I18NException;

	public Alumne findByPrimaryKey(long _alumneID_);

	public void delete(long _alumneID_);

}
