package org.fundaciobit.genappsqltutorial.logic;


import javax.ejb.Local;
/*
import org.fundaciobit.genappsqltutorial.ejb.AnnexService;
import org.fundaciobit.genappsqltutorial.jpa.AnnexJPA;

import org.fundaciobit.genapp.common.i18n.I18NException;
*/
/**
 * 
 * @author anadal
 *
 */
@Local
public interface SampleLogicaService /* extends AnnexService */ {
	
	public static final String JNDI_NAME = "java:app/genappsqltutorial-ejb/SampleLogicaEJB!org.fundaciobit.genappsqltutorial.logic.SampleLogicaService";

/*
  public Set<Long> deleteFull(AnnexJPA annex) throws I18NException;
  
  public AnnexJPA createFull(AnnexJPA annex) throws I18NException;
  */
}

