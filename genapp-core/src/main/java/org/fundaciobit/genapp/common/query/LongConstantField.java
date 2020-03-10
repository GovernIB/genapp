package org.fundaciobit.genapp.common.query;

/**
 *
 * @author anadal
 * 
 */
public class LongConstantField extends Field<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5503200808275762006L;

	public LongConstantField(Long value) {
		super(null, String.valueOf(value), String.valueOf(value), null);
	}

}
