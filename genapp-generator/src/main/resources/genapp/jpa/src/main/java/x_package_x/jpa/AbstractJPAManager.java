package  ${package_jpa};

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.fundaciobit.genapp.common.IGenAppEntity;
import org.fundaciobit.genapp.common.i18n.I18NArgumentString;
import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genapp.common.query.AbstractTableManager;
import org.fundaciobit.genapp.common.query.GroupBy;
import org.fundaciobit.genapp.common.query.OrderBy;
import org.fundaciobit.genapp.common.query.Select;
import org.fundaciobit.genapp.common.query.Where;

/**
 * XYZ ZZZ Fer que no es sobreescriquin
 * 
 * @author anadal
 *
 */
public abstract class AbstractJPAManager<E extends IGenAppEntity, PK extends Object> extends AbstractTableManager<E, PK>
		implements AbstractIJPAManager<E, PK> {

	public AbstractJPAManager() {
		super();
	}

}
