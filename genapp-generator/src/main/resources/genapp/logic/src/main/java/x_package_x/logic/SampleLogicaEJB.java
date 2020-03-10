package ${package}.logic;


/*
import ${package}.ejb.AnnexEJB;
import ${package}.ejb.FitxerLocal;
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
public class SampleLogicaEJB implements SampleLogicaLocal {


}
 
 /**
@Stateless(name = "AnnexLogicaEJB")
public class AnnexLogicaEJB extends AnnexEJB implements AnnexLogicaLocal,
    AnnexFields {


  @EJB(mappedName = FitxerLocal.JNDI_NAME)
  private FitxerLocal fitxerEjb;

  @EJB(mappedName = ${package}.ejb.AnnexFirmatLocal.JNDI_NAME)
  protected ${package}.ejb.AnnexFirmatLocal annexFirmatEjb;
  
  
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
