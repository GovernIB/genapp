package org.fundaciobit.genappsqltutorial.logic;


/*
import org.fundaciobit.genappsqltutorial.ejb.AnnexEJB;
import org.fundaciobit.genappsqltutorial.ejb.FitxerService;
import org.fundaciobit.genappsqltutorial.jpa.AnnexJPA;
import org.fundaciobit.genappsqltutorial.model.entity.AnnexFirmat;
import org.fundaciobit.genappsqltutorial.model.fields.AnnexFields;
import org.fundaciobit.genappsqltutorial.model.fields.AnnexFirmatFields;

import org.fundaciobit.genapp.common.i18n.I18NException;

*/

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

  @EJB(mappedName = org.fundaciobit.genappsqltutorial.ejb.AnnexFirmatService.JNDI_NAME)
  protected org.fundaciobit.genappsqltutorial.ejb.AnnexFirmatServiceannexFirmatEjb;
  
  
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
