package org.fundaciobit.demogenapp.ejb;

import javax.ejb.Local;

import org.fundaciobit.demogenapp.persistence.AssignaturaJPA;
import org.fundaciobit.genapp.common.i18n.I18NException;

/**
 * Interfície del delegat que executa crides a {@link AssignaturaService}
 * simulant el rol DEM_USER (veure {@code @RunAs} a l'EJB).
 *
 * @author anadal
 */
@Local
public interface AssignaturaRunAsUserService {

    public static final String JNDI_NAME = "java:app/demogenapp-ejb/AssignaturaRunAsUserEJB!org.fundaciobit.demogenapp.ejb.AssignaturaRunAsUserService";

    public AssignaturaJPA findByPrimaryKey(Long assignaturaID) throws I18NException;

}
