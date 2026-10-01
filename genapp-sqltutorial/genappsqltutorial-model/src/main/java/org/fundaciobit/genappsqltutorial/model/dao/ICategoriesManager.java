package org.fundaciobit.genappsqltutorial.model.dao;

import org.fundaciobit.genappsqltutorial.model.entity.*;
import org.fundaciobit.genapp.common.i18n.I18NException;


public interface ICategoriesManager extends org.fundaciobit.genapp.common.query.ITableManager<Categories, Long> {


	public Categories create( java.lang.String _categoryName_, java.lang.String _description_) throws I18NException;

	public Categories findByPrimaryKey(long _categoryid_);

	public void delete(long _categoryid_);

}
