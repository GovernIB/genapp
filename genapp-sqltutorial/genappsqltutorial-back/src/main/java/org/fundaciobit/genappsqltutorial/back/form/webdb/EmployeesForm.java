package org.fundaciobit.genappsqltutorial.back.form.webdb;

import org.fundaciobit.genappsqltutorial.back.form.GenAppSqlTutorialBaseForm;
import org.fundaciobit.genappsqltutorial.persistence.EmployeesJPA;

/**
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * Created by GenApp. Do not modify.
 */
public class EmployeesForm extends GenAppSqlTutorialBaseForm {
  
  private EmployeesJPA employees;
  
  public EmployeesForm() {
  }
  
  public EmployeesForm(EmployeesForm __toClone) {
    super(__toClone);
      this.employees = __toClone.employees;
  }
  
  public EmployeesForm(EmployeesJPA employees, boolean nou) {
    super(nou);
    this.employees = employees;
  }
  
  public EmployeesJPA getEmployees() {
    return employees;
  }
  public void setEmployees(EmployeesJPA employees) {
    this.employees = employees;
  }
  
  
  
} // Final de Classe 
