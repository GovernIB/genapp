
package org.fundaciobit.genappsqltutorial.ejb;

// NO MODIFICAR - DO NOT MODIFY;
import javax.ejb.Local;

import org.fundaciobit.genappsqltutorial.persistence.EmployeesJPA;
import org.fundaciobit.genappsqltutorial.persistence.EmployeesIJPAManager;
import org.fundaciobit.genappsqltutorial.model.dao.IEmployeesManager;

import org.fundaciobit.genappsqltutorial.model.entity.Employees;
import org.fundaciobit.genapp.common.i18n.I18NException;

@Local
public interface EmployeesService extends EmployeesIJPAManager,IEmployeesManager {

    public static final String JNDI_NAME = "java:app/genappsqltutorial-ejb/EmployeesEJB!org.fundaciobit.genappsqltutorial.ejb.EmployeesService";

    public EmployeesJPA findByPrimaryKey(Long _ID_);

    public void deleteIncludingFiles(Employees instance, FitxerService fitxerEjb) throws I18NException;
}
