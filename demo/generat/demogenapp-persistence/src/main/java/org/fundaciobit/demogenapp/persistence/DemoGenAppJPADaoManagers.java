package org.fundaciobit.demogenapp.persistence;

import org.fundaciobit.demogenapp.model.*;
import org.fundaciobit.demogenapp.model.dao.*;
import javax.persistence.EntityManager;

public final class DemoGenAppJPADaoManagers implements IDemoGenAppDaoManagers{

   private final AlumneJPAManager dem_alumne;
   private final AssignaturaJPAManager dem_assignatura;
   private final AssignaturaAlumneJPAManager dem_assignaturaalumne;
   private final FitxerJPAManager dem_fitxer;
   private final IdiomaJPAManager dem_idioma;
   private final TraduccioJPAManager dem_traduccio;

  public  DemoGenAppJPADaoManagers(EntityManager __em) {
    this.dem_alumne = new AlumneJPAManager(__em);
    this.dem_assignatura = new AssignaturaJPAManager(__em);
    this.dem_assignaturaalumne = new AssignaturaAlumneJPAManager(__em);
    this.dem_fitxer = new FitxerJPAManager(__em);
    this.dem_idioma = new IdiomaJPAManager(__em);
    this.dem_traduccio = new TraduccioJPAManager(__em);
  }

    public IAlumneManager getAlumneManager() {
        return this.dem_alumne;
    };

    public IAssignaturaManager getAssignaturaManager() {
        return this.dem_assignatura;
    };

    public IAssignaturaAlumneManager getAssignaturaAlumneManager() {
        return this.dem_assignaturaalumne;
    };

    public IFitxerManager getFitxerManager() {
        return this.dem_fitxer;
    };

    public IIdiomaManager getIdiomaManager() {
        return this.dem_idioma;
    };

    public ITraduccioManager getTraduccioManager() {
        return this.dem_traduccio;
    };


}