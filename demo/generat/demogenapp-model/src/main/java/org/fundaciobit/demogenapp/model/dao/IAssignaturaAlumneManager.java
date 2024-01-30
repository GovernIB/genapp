package org.fundaciobit.demogenapp.model.dao;

import org.fundaciobit.demogenapp.model.entity.*;
import org.fundaciobit.genapp.common.i18n.I18NException;


public interface IAssignaturaAlumneManager extends org.fundaciobit.genapp.common.query.ITableManager<AssignaturaAlumne, Long> {


	public AssignaturaAlumne create( java.lang.Long _alumneID_, long _assignaturaID_, java.lang.Double _nota_) throws I18NException;

	public AssignaturaAlumne findByPrimaryKey(long _id_);

	public void delete(long _id_);

}
