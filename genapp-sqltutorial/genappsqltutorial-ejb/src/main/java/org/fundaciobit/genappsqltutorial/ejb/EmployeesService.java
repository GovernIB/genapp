
package org.fundaciobit.genappsqltutorial.ejb;

// NO MODIFICAR - DO NOT MODIFY;
import javax.ejb.Local;

import org.fundaciobit.genappsqltutorial.persistence.EmployeesJPA;
import org.fundaciobit.genappsqltutorial.persistence.EmployeesIJPAManager;
import org.fundaciobit.genappsqltutorial.model.dao.IEmployeesManager;

@Local
public interface EmployeesService extends EmployeesIJPAManager,IEmployeesManager {

    public static final String JNDI_NAME = "java:app/genappsqltutorial-ejb/EmployeesEJB!org.fundaciobit.genappsqltutorial.ejb.EmployeesService";

    public EmployeesJPA findByPrimaryKey(Long _ID_);
}
