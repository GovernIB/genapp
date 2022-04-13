package org.fundaciobit.genapp.common.query;

import org.fundaciobit.genapp.common.i18n.I18NException;

/**
 * 
 * @author anadal
 *
 */
public class SelectMin<R> extends Select<R> {

	protected final Field<R> field;

	public SelectMin(Field<R> field) {
		this.field = field;
	}

	@Override
	public String getSelectString() {
		return "MIN( " + field.fullName + ")";
	}

	@Override
	public R getFromObject(Object obj) throws I18NException {
		return ((R) obj);
	}

}
