package ${package}.logic;


/*
import ${package}.ejb.AnnexEJB;
import ${package}.ejb.FitxerService;
import ${package}.jpa.AnnexJPA;
import ${package}.model.entity.AnnexFirmat;
import ${package}.model.fields.AnnexFields;
import ${package}.model.fields.AnnexFirmatFields;

import org.fundaciobit.genapp.common.i18n.I18NException;

*/

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * 
 * @author anadal
 *
 */
@Stateless(name = "SampleLogicaEJB")
public class SampleLogicaEJB implements SampleLogicaService {


}
 
 /**
@Stateless(name = "AnnexLogicaEJB")
public class AnnexLogicaEJB extends AnnexEJB implements AnnexLogicaService,
    AnnexFields {


  @EJB(mappedName = FitxerService.JNDI_NAME)
  private FitxerService fitxerEjb;

  @EJB(mappedName = ${package}.ejb.AnnexFirmatService.JNDI_NAME)
  protected ${package}.ejb.AnnexFirmatServiceannexFirmatEjb;
  
  
  @Override
  public AnnexJPA createFull(AnnexJPA annex) throws I18NException {
    // TODO Validar !!!
    
    return (AnnexJPA)create(annex);
  }
  


  @Override
  public Set<Long> deleteFull(AnnexJPA annex) throws I18NException {
    
    Set<Long> files = new HashSet<Long>();
    
    if (annex == null) {
      return files;
    }
    
    // Annex
    delete(annex);
    
    return files;
  }

}
*/
