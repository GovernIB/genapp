
package org.fundaciobit.demogenapp.persistence;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.fundaciobit.demogenapp.model.entity.*;
import org.fundaciobit.demogenapp.model.fields.*;
import org.fundaciobit.demogenapp.model.dao.*;
import org.fundaciobit.genapp.common.query.TableName;
import org.fundaciobit.genapp.common.i18n.I18NException;


public class AssignaturaJPAManager
         extends AbstractJPAManager<Assignatura, Long>
         implements AssignaturaIJPAManager, IAssignaturaManager, AssignaturaFields {



    public static final TableName<Assignatura> _TABLENAME =  new TableName<Assignatura>("AssignaturaJPA");


    @PersistenceContext
    protected EntityManager __em;

    public AssignaturaJPAManager() {
    }

    protected AssignaturaJPAManager(EntityManager __em) {
      this.__em = __em;
    }

    @Override
    public EntityManager getEntityManager() {
      return this.__em;
    }
    public Class<?> getJPAClass() {
        return AssignaturaJPA. class;
    }



    public TableName<Assignatura> getTableName() {
        return _TABLENAME;
    }


    @Override
    protected String getTableNameVariable() {
        return _TABLE_MODEL;
    }


    public Assignatura[] listToArray(List<Assignatura> list)  {
        if(list == null) { return null; };
        return list.toArray(new Assignatura[list.size()]);
    };

    public Assignatura create( java.lang.String _nom_, int _credits_, java.lang.Integer _diaSetmana_, java.sql.Time _hora_, java.lang.String _descripcio_) throws I18NException {
        AssignaturaJPA __bean =  new AssignaturaJPA(_nom_,_credits_,_diaSetmana_,_hora_,_descripcio_);
        return create(__bean);
    }



 public void delete(long _assignaturaID_) {
   delete(findByPrimaryKey(_assignaturaID_));
 }




    public Assignatura findByPrimaryKey(long _assignaturaID_) {
        return __em.find(AssignaturaJPA.class, _assignaturaID_);  
    }
    @Override
    protected Assignatura getJPAInstance(Assignatura __bean) {
        return convertToJPA(__bean);
    }


    public static AssignaturaJPA convertToJPA(Assignatura __bean) {
      if (__bean == null) {
        return null;
      }
      if(__bean instanceof AssignaturaJPA) {
        return (AssignaturaJPA)__bean;
      }
      
      return AssignaturaJPA.toJPA(__bean);
    }


}