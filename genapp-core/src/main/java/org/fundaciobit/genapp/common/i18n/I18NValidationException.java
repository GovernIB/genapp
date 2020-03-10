package org.fundaciobit.genapp.common.i18n;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author anadal
 * 
 */
public class I18NValidationException extends Throwable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9097054735719951753L;
	
	
	private final List<I18NFieldError> fieldErrorList;

	/**
	 * @param fieldErrorList
	 */
	public I18NValidationException(List<I18NFieldError> fieldErrorList) {
		super();
		this.fieldErrorList = fieldErrorList;
	}

	public I18NValidationException(I18NFieldError fieldError) {
		super();
		this.fieldErrorList = new ArrayList<I18NFieldError>();
		this.fieldErrorList.add(fieldError);
	}

	public List<I18NFieldError> getFieldErrorList() {
		return fieldErrorList;
	}

}
