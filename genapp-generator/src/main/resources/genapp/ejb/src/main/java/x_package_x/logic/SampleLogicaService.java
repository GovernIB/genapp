package ${package}.logic;

import java.util.Set;

import javax.ejb.Local;
/*
import ${package}.ejb.AnnexService;
import ${package}.jpa.AnnexJPA;

import org.fundaciobit.genapp.common.i18n.I18NException;
*/
/**
 * 
 * @author anadal
 *
 */
@Local
public interface SampleLogicaService /* extends AnnexService */ {
	
	public static final String JNDI_NAME = "java:app/${name}-logic/SampleLogicaEJB!${package}.logic.SampleLogicaService";

/*
  public Set<Long> deleteFull(AnnexJPA annex) throws I18NException;
  
  public AnnexJPA createFull(AnnexJPA annex) throws I18NException;
  */
}

