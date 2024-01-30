package org.fundaciobit.demogenapp.logic;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;
import javax.ejb.EJB;

/*
import org.fundaciobit.demogenapp.ejb.AnnexEJB;
import org.fundaciobit.demogenapp.ejb.FitxerService;
import org.fundaciobit.demogenapp.persistence.AnnexJPA;
import org.fundaciobit.demogenapp.model.entity.AnnexFirmat;
import org.fundaciobit.demogenapp.model.fields.AnnexFields;
import org.fundaciobit.demogenapp.model.fields.AnnexFirmatFields;

import org.fundaciobit.genapp.common.i18n.I18NException;

*/

//import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.transaction.TransactionSynchronizationRegistry;

/**
 * 
 * @author anadal
 *
 */
@Stateless(name = "SampleLogicaEJB")
public class SampleLogicaEJB implements SampleLogicaService {

//    @EJB(mappedName=org.fundaciobit.demogenapp.ejb.FitxerService.JNDI_NAME)protected org.fundaciobit.demogenapp.
//    ejb.FitxerService fitxerEjb;
//
//    @Resource(mappedName = "java:comp/TransactionSynchronizationRegistry")
//    protected TransactionSynchronizationRegistry transactionSynchronizationRegistry;
//
//    @Override
//    public void deleteFull(AnnexJPA annex) throws I18NException {
//        log.info("Comença deleteFull");
//
//        // Borram Annex a BD
//        delete(annex);
//
//        Long[] fitxers = new Long[] { annex.getFitxer1ID(), annex.getFitxer2ID() };
//
//        Set<Long> filesToDelete = new HashSet<Long>();
//
//        // Borram fitxers a BD
//        for (Long f : fitxers) {
//            if (f != null) {
//                fitxerEjb.delete(f);
//                filesToDelete.add(f);
//            }
//        }
//
//        tsRegistry.registerInterposedSynchronization(new PreCommitFiles(filesToDelete));
//
//        log.info("Final deleteFull");
//    }

};

//public class PreCommitFiles implements Synchronization {
//    public final Set<Long> files;
//
//    public PreCommitFiles(Set<Long> filesToDelete) {
//        this.files = filesToDelete;
//    }
//
//    @Override
//    public void beforeCompletion() {
//    }
//
//    @Override
//    public void afterCompletion(int status) {
//         if ( status == Status.STATUS_COMMITTED) {
//                  FileSystemManager.eliminarArxius(files);
//         }
//    }
//};

/**
 * @Stateless(name = "AnnexLogicaEJB") public class AnnexLogicaEJB extends
 *                 AnnexEJB implements AnnexLogicaService, AnnexFields {
 * 
 * 
 * @EJB(mappedName = FitxerService.JNDI_NAME) private FitxerService fitxerEjb;
 * 
 * @EJB(mappedName = org.fundaciobit.demogenapp.ejb.AnnexFirmatService.JNDI_NAME) protected
 *                 org.fundaciobit.demogenapp.ejb.AnnexFirmatServiceannexFirmatEjb;
 * 
 * 
 * @Override public AnnexJPA createFull(AnnexJPA annex) throws I18NException {
 *           // TODO Validar !!!
 * 
 *           return (AnnexJPA)create(annex); }
 * 
 * 
 * 
 * @Override public Set<Long> deleteFull(AnnexJPA annex) throws I18NException {
 * 
 *           Set<Long> files = new HashSet<Long>();
 * 
 *           if (annex == null) { return files; }
 * 
 *           // Annex delete(annex);
 * 
 *           return files; }
 * 
 *           }
 */
