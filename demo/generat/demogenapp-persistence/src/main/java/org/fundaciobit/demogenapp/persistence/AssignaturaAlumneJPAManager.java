
package org.fundaciobit.demogenapp.persistence;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.fundaciobit.demogenapp.model.entity.*;
import org.fundaciobit.demogenapp.model.fields.*;
import org.fundaciobit.demogenapp.model.dao.*;
import org.fundaciobit.genapp.common.query.TableName;
import org.fundaciobit.genapp.common.i18n.I18NException;


public class AssignaturaAlumneJPAManager
         extends AbstractJPAManager<AssignaturaAlumne, Long>
         implements AssignaturaAlumneIJPAManager, IAssignaturaAlumneManager, AssignaturaAlumneFields {



    public static final TableName<AssignaturaAlumne> _TABLENAME =  new TableName<AssignaturaAlumne>("AssignaturaAlumneJPA");


    @PersistenceContext
    protected EntityManager __em;

    public AssignaturaAlumneJPAManager() {
    }

    protected AssignaturaAlumneJPAManager(EntityManager __em) {
      this.__em = __em;
    }

    @Override
    public EntityManager getEntityManager() {
      return this.__em;
    }
    public Class<?> getJPAClass() {
        return AssignaturaAlumneJPA. class;
    }



    public TableName<AssignaturaAlumne> getTableName() {
        return _TABLENAME;
    }


    @Override
    protected String getTableNameVariable() {
        return _TABLE_MODEL;
    }


    public AssignaturaAlumne[] listToArray(List<AssignaturaAlumne> list)  {
        if(list == null) { return null; };
        return list.toArray(new AssignaturaAlumne[list.size()]);
    };

    public AssignaturaAlumne create( java.lang.Long _alumneID_, long _assignaturaID_, java.lang.Double _nota_) throws I18NException {
        AssignaturaAlumneJPA __bean =  new AssignaturaAlumneJPA(_alumneID_,_assignaturaID_,_nota_);
        return create(__bean);
    }



 public void delete(long _id_) {
   delete(findByPrimaryKey(_id_));
 }




    public AssignaturaAlumne findByPrimaryKey(long _id_) {
        return __em.find(AssignaturaAlumneJPA.class, _id_);  
    }
    @Override
    protected AssignaturaAlumne getJPAInstance(AssignaturaAlumne __bean) {
        return convertToJPA(__bean);
    }


    public static AssignaturaAlumneJPA convertToJPA(AssignaturaAlumne __bean) {
      if (__bean == null) {
        return null;
      }
      if(__bean instanceof AssignaturaAlumneJPA) {
        return (AssignaturaAlumneJPA)__bean;
      }
      
      return AssignaturaAlumneJPA.toJPA(__bean);
    }


}