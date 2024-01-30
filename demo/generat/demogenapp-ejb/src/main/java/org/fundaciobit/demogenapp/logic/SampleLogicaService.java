package org.fundaciobit.demogenapp.logic;

//import java.util.Set;

import javax.ejb.Local;

/*
import org.fundaciobit.demogenapp.ejb.AnnexService;
import org.fundaciobit.demogenapp.persistence.AnnexJPA;

import org.fundaciobit.genapp.common.i18n.I18NException;
*/
/**
 * 
 * @author anadal
 *
 */
@Local
public interface SampleLogicaService /* extends AnnexService */ {

    public static final String JNDI_NAME = "java:app/demogenapp-ejb/SampleLogicaEJB!org.fundaciobit.demogenapp.logic.SampleLogicaService";
/*
    public void deleteFull(AnnexJPA annex) throws I18NException;

    
     * public AnnexJPA createFull(AnnexJPA annex) throws I18NException;
     */
}
