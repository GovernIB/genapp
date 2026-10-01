package org.fundaciobit.demogenapp.logic;

import javax.ejb.Local;

import org.fundaciobit.demogenapp.ejb.AssignaturaService;
import org.fundaciobit.demogenapp.persistence.AssignaturaJPA;
import org.fundaciobit.genapp.common.i18n.I18NException;

/**
 * Interfície del delegat que executa crides a {@link AssignaturaService}
 * simulant el rol DEM_USER (veure {@code @RunAs} a l'EJB).
 *
 * @author anadal
 */
@Local
public interface AssignaturaRunAsUserLogicaService {

    public static final String JNDI_NAME = "java:app/demogenapp-ejb/AssignaturaRunAsUserLogicaEJB!org.fundaciobit.demogenapp.logic.AssignaturaRunAsUserLogicaService";

    public AssignaturaJPA findByPrimaryKey(Long assignaturaID) throws I18NException;

}
