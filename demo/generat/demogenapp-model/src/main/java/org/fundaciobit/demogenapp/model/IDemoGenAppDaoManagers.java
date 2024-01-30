package org.fundaciobit.demogenapp.model;

import org.fundaciobit.demogenapp.model.dao.*;

public interface IDemoGenAppDaoManagers {
	public IAlumneManager getAlumneManager();
	public IAssignaturaManager getAssignaturaManager();
	public IAssignaturaAlumneManager getAssignaturaAlumneManager();
	public IFitxerManager getFitxerManager();
	public IIdiomaManager getIdiomaManager();
	public ITraduccioManager getTraduccioManager();

}