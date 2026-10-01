package org.fundaciobit.demogenapp.logic;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RunAs;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.fundaciobit.demogenapp.commons.utils.Constants;
import org.fundaciobit.demogenapp.ejb.AssignaturaService;
import org.fundaciobit.demogenapp.persistence.AssignaturaJPA;
import org.fundaciobit.genapp.common.i18n.I18NException;

/**
 * Delegat que executa la crida a {@link AssignaturaService} simulant el rol
 * DEM_USER.
 *
 * L'anotació {@link RunAs} substitueix la identitat de sortida cap a l'EJB
 * protegit ({@code AssignaturaEJB.findByPrimaryKey}) pel rol DEM_USER, que està
 * permès pels {@code @RolesAllowed} de l'EJB.
 *
 * {@link PermitAll} permet que la crida entrant (per exemple des d'una pàgina
 * pública sense usuari autenticat) pugui arribar fins aquí.
 *
 * IMPORTANT: aquest patró només funciona en un EJB gestionat pel contenidor
 * (dins el domini de seguretat 'keycloak' definit a META-INF/jboss-ejb3.xml).
 * En un {@code @Component} de Spring el {@code @RunAs} s'ignora.
 *
 * @author anadal
 */
@Stateless
@RunAs(Constants.DEM_USER)
@PermitAll
public class AssignaturaRunAsUserLogicaEJB implements AssignaturaRunAsUserLogicaService {

    @EJB(mappedName = AssignaturaService.JNDI_NAME)
    protected AssignaturaService assignaturaEjb;

    @Override
    public AssignaturaJPA findByPrimaryKey(Long assignaturaID) throws I18NException {
        return (AssignaturaJPA) assignaturaEjb.findByPrimaryKey(assignaturaID);
    }

}
