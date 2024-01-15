package org.fundaciobit.genapp.common.web.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.fundaciobit.genapp.common.query.Field;
import org.fundaciobit.genapp.common.validation.BeanValidatorResult;

/**
 * 
 * @author anadal
 * 
 */
public abstract class BaseForm extends CommonBaseForm {

	/**
	 * Conte la llista de javaName de Field que volem que siguin només de lectura
	 */
	private Set<Field<?>> readOnlyFields = new HashSet<Field<?>>();

	private boolean cancelButtonVisible = true;

	private boolean saveButtonVisible = true;

	private Map<Field<?>, String> help = new HashMap<Field<?>, String>();

	private boolean view = false;

	private List<Section> sections = null;

	public BaseForm() {
	}

	public BaseForm(boolean nou) {
		super(nou);
	}

	public BaseForm(BaseForm toClone) {
		super(toClone);

		if (toClone == null) {
			return;
		}

		this.readOnlyFields = toClone.getReadOnlyFields();
		this.cancelButtonVisible = toClone.cancelButtonVisible;
		this.saveButtonVisible = toClone.saveButtonVisible;
		this.help = toClone.help;
		this.view = toClone.view;
		this.sections = toClone.sections;
	}

	public Set<Field<?>> getReadOnlyFields() {
		return readOnlyFields;
	}

	public void setReadOnlyFields(Set<Field<?>> readOnlyFields) {
		this.readOnlyFields = readOnlyFields;
	}

	public void addReadOnlyField(Field<?> field) {
		if (field != null) {
			this.readOnlyFields.add(field);
		}
	}

	public boolean isReadOnlyField(Field<?> field) {
		return this.readOnlyFields.contains(field);
	}

	// XYZ ZZZ TODO Eliminar en propera versió
	@Deprecated
	public boolean isReadOnlyField(String javaName) {
		for (Field<?> readField : this.readOnlyFields) {
			String jn = getStringOfField(readField);
			if (jn.equals(javaName)) {
				return true;
			}
		}
		return false;
	}

	public void setAllFieldsReadOnly(Field<?>... fieldToSetReadOnly) {
		if (fieldToSetReadOnly == null || fieldToSetReadOnly.length == 0) {
			return;
		}
		for (Field<?> field : fieldToSetReadOnly) {
			addReadOnlyField(field);
		}
	}

	public boolean isCancelButtonVisible() {
		return cancelButtonVisible;
	}

	public void setCancelButtonVisible(boolean cancelButtonVisible) {
		this.cancelButtonVisible = cancelButtonVisible;
	}

	public boolean isSaveButtonVisible() {
		return saveButtonVisible;
	}

	public void setSaveButtonVisible(boolean saveButtonVisible) {
		this.saveButtonVisible = saveButtonVisible;
	}

	public Map<Field<?>, String> getHelp() {
		return help;
	}

	public void setHelp(Map<Field<?>, String> help) {
		this.help = help;
	}

	public void addHelpToField(Field<?> field, String message) {
		if (field != null && message != null) {
			help.put(field, message);
		}
	}

	public boolean isView() {
		return view;
	}

	public void setView(boolean view) {
		this.view = view;
	}

	public List<Section> getSections() {
		return this.sections;
	}

	public void setSections(List<Section> sections) {
		this.sections = sections;
	}

	public void addSection(Section section) {
		if (section == null) {
			return;
		}

		if (this.sections == null) {
			this.sections = new ArrayList<Section>();
		}
		this.sections.add(section);
	}
	
	/**
     * 
     * @param <T>
     * @param _jpa
     * @param form
     * @param allFields
     */
    public static <T> void hideNullFields(T _jpa, BaseForm form, Field<?>[] allFields) {
        BeanValidatorResult<T> _bvr_ = new BeanValidatorResult<T>();

        for (Field<?> field : allFields) {
            Object obj = _bvr_.getFieldValue(_jpa, field);
            if (obj == null) {
                form.addHiddenField(field);
            }
        }
    }

}
