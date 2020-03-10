package  ${package_jpa};

import java.util.List;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;

import org.fundaciobit.genapp.common.IGenAppEntity;
import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genapp.common.query.Field;
import org.fundaciobit.genapp.common.query.ITableManager;
import org.fundaciobit.genapp.common.query.OrderBy;
import org.fundaciobit.genapp.common.query.Select;
import org.fundaciobit.genapp.common.query.SubQuery;
import org.fundaciobit.genapp.common.query.Where;

/**
 * Defineix les operacions dels Data Access Object per una entitat.
 * 
 * XYZ ZZZ Fer que no es sobreescriquin
 * 
 * @param <E>  Tipus de l'entitat.
 * @param <PK> Tipus de la clau primària de l'entitat.
 *
 * @author anadal
 * @author areus
 */
public interface AbstractIJPAManager<E extends IGenAppEntity, PK> extends ITableManager<E, PK> {

}