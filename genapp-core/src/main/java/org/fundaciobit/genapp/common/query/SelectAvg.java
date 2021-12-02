package org.fundaciobit.genapp.common.query;

import org.fundaciobit.genapp.common.i18n.I18NException;

/**
 * 
 * @author anadal
 *
 */
public class SelectAvg extends Select<Double> {

	protected final Field<? extends Object> field;

	public SelectAvg(Field<? extends Object> field) {
		this.field = field;
	}

	@Override
	public String getSelectString() {
		return "AVG( " + field.fullName + ")";
	}

	@Override
	public Double getFromObject(Object obj) throws I18NException {
		return (Double) obj;
	}

}
