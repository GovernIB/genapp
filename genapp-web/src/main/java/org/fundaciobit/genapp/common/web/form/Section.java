package org.fundaciobit.genapp.common.web.form;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.fundaciobit.genapp.common.query.Field;

/**
 * 
 * @author ptrias
 *
 */
public class Section {

	protected String id;
	protected String titleCode;
	protected List<Field<?>> fields;

	public Section() {
		super();
	}

	public Section(String id, String titleCode) {
		super();
		this.id = id;
		this.titleCode = titleCode;
		this.fields = new ArrayList<Field<?>>();
	}

	public Section(String id, String titleCode, List<Field<?>> fields) {
		super();
		this.id = id;
		this.titleCode = titleCode;
		this.fields = fields;
	}

	public Section(String id, String titleCode, Field<?>... fields) {
		super();
		this.id = id;
		this.titleCode = titleCode;
		this.fields = Arrays.asList(fields);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitleCode() {
		return titleCode;
	}

	public void setTitleCode(String titleCode) {
		this.titleCode = titleCode;
	}

	public List<Field<?>> getFields() {
		return fields;
	}

	public void setFields(List<Field<?>> fields) {
		this.fields = fields;
	}

}
