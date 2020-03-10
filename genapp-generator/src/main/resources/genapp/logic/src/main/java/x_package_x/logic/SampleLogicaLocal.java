package ${package}.logic;

import java.util.Set;

import javax.ejb.Local;
/*
import ${package}.ejb.AnnexLocal;
import ${package}.jpa.AnnexJPA;

import org.fundaciobit.genapp.common.i18n.I18NException;
*/
/**
 * 
 * @author anadal
 *
 */
@Local
public interface SampleLogicaLocal /* extends AnnexLocal */ {
	
	public static final String JNDI_NAME = "java:app/${name}-logic/SampleLogicaEJB!${package}.logic.SampleLogicaLocal";

/*
  public Set<Long> deleteFull(AnnexJPA annex) throws I18NException;
  
  public AnnexJPA createFull(AnnexJPA annex) throws I18NException;
  */
}

