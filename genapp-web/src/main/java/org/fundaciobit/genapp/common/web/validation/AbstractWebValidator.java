package org.fundaciobit.genapp.common.web.validation;

import org.fundaciobit.genapp.common.web.form.BaseForm;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * 
 * @author anadal
 *
 */
public abstract class AbstractWebValidator<F extends BaseForm, B> implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return getClassOfForm().isAssignableFrom(clazz);
	}

	public abstract void validate(F form, B bean, Errors errors);

	public abstract B getBeanOfForm(F form);

	public abstract Class<F> getClassOfForm();

	@Override
	public void validate(Object formObj, Errors errors) {

		F form = (F) formObj;

		validate(form, getBeanOfForm(form), errors);

	}

}
