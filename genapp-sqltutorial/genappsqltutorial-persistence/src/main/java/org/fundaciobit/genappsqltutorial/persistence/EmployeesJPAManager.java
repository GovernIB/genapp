
package org.fundaciobit.genappsqltutorial.persistence;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.fundaciobit.genappsqltutorial.model.entity.*;
import org.fundaciobit.genappsqltutorial.model.fields.*;
import org.fundaciobit.genappsqltutorial.model.dao.*;
import org.fundaciobit.genapp.common.query.TableName;
import org.fundaciobit.genapp.common.i18n.I18NException;


public class EmployeesJPAManager
         extends AbstractJPAManager<Employees, Long>
         implements EmployeesIJPAManager, IEmployeesManager, EmployeesFields {




    private static final long serialVersionUID = 975914135L;

    public static final TableName<Employees> _TABLENAME =  new TableName<Employees>("EmployeesJPA");


    @PersistenceContext
    protected EntityManager __em;

    public EmployeesJPAManager() {
    }

    protected EmployeesJPAManager(EntityManager __em) {
      this.__em = __em;
    }

    @Override
    public EntityManager getEntityManager() {
      return this.__em;
    }
    public Class<?> getJPAClass() {
        return EmployeesJPA. class;
    }



    public TableName<Employees> getTableName() {
        return _TABLENAME;
    }


    @Override
    protected String getTableNameVariable() {
        return _TABLE_MODEL;
    }


    public Employees[] listToArray(List<Employees> list)  {
        if(list == null) { return null; };
        return list.toArray(new Employees[list.size()]);
    };

    public synchronized Employees create( java.lang.String _lastname_, java.lang.String _firstname_, java.sql.Date _birthdate_, java.lang.String _photo_, java.lang.String _notes_) throws I18NException {
        EmployeesJPA __bean =  new EmployeesJPA(_lastname_,_firstname_,_birthdate_,_photo_,_notes_);
        return create(__bean);
    }



 public void delete(long _employeeid_) {
   delete(findByPrimaryKey(_employeeid_));
 }




    public Employees findByPrimaryKey(long _employeeid_) {
        return __em.find(EmployeesJPA.class, _employeeid_);  
    }
    @Override
    protected Employees getJPAInstance(Employees __bean) {
        return convertToJPA(__bean);
    }


    public static EmployeesJPA convertToJPA(Employees __bean) {
      if (__bean == null) {
        return null;
      }
      if(__bean instanceof EmployeesJPA) {
        return (EmployeesJPA)__bean;
      }
      
      return EmployeesJPA.toJPA(__bean);
    }


}