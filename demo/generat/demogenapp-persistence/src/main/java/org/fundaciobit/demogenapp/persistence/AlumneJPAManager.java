
package org.fundaciobit.demogenapp.persistence;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.fundaciobit.demogenapp.model.entity.*;
import org.fundaciobit.demogenapp.model.fields.*;
import org.fundaciobit.demogenapp.model.dao.*;
import org.fundaciobit.genapp.common.query.TableName;
import org.fundaciobit.genapp.common.i18n.I18NException;


public class AlumneJPAManager
         extends AbstractJPAManager<Alumne, Long>
         implements AlumneIJPAManager, IAlumneManager, AlumneFields {



    public static final TableName<Alumne> _TABLENAME =  new TableName<Alumne>("AlumneJPA");


    @PersistenceContext
    protected EntityManager __em;

    public AlumneJPAManager() {
    }

    protected AlumneJPAManager(EntityManager __em) {
      this.__em = __em;
    }

    @Override
    public EntityManager getEntityManager() {
      return this.__em;
    }
    public Class<?> getJPAClass() {
        return AlumneJPA. class;
    }



    public TableName<Alumne> getTableName() {
        return _TABLENAME;
    }


    @Override
    protected String getTableNameVariable() {
        return _TABLE_MODEL;
    }


    public Alumne[] listToArray(List<Alumne> list)  {
        if(list == null) { return null; };
        return list.toArray(new Alumne[list.size()]);
    };

    public Alumne create( java.lang.String _nom_, java.lang.String _idiomaID_, java.sql.Date _dataNaixement_, java.lang.Boolean _sexe_, boolean _actiu_, java.sql.Timestamp _darrerAcces_, java.lang.Long _fotoID_, java.lang.Long _titolAcademicID_, java.sql.Time _despertador_, java.lang.String _paginaWeb_, java.lang.String _descripcio_) throws I18NException {
        AlumneJPA __bean =  new AlumneJPA(_nom_,_idiomaID_,_dataNaixement_,_sexe_,_actiu_,_darrerAcces_,_fotoID_,_titolAcademicID_,_despertador_,_paginaWeb_,_descripcio_);
        return create(__bean);
    }



 public void delete(long _alumneID_) {
   delete(findByPrimaryKey(_alumneID_));
 }




    public Alumne findByPrimaryKey(long _alumneID_) {
        return __em.find(AlumneJPA.class, _alumneID_);  
    }
    @Override
    protected Alumne getJPAInstance(Alumne __bean) {
        return convertToJPA(__bean);
    }


    public static AlumneJPA convertToJPA(Alumne __bean) {
      if (__bean == null) {
        return null;
      }
      if(__bean instanceof AlumneJPA) {
        return (AlumneJPA)__bean;
      }
      
      return AlumneJPA.toJPA(__bean);
    }

  @Override
  public Alumne create(Alumne transientInstance) throws I18NException {
    processTranslations(transientInstance);
    return super.create(transientInstance);
  }


  @Override
  public Alumne update(Alumne transientInstance) throws I18NException {
    processTranslations(transientInstance);
    return super.update(transientInstance);
  }


  private void processTranslations(Alumne transientInstance) {
    if (transientInstance != null) {
      if (transientInstance.getTitolAcademicID() == null) {
        if (transientInstance instanceof AlumneJPA) {
          AlumneJPA _jpa = (AlumneJPA)transientInstance;
          TraduccioJPA _trad = _jpa.getTitolAcademic();
           if (_trad != null) {
            if (_trad.getTraduccioID() == 0) {
              getEntityManager().persist(_trad);
            } 
            transientInstance.setTitolAcademicID(_trad.getTraduccioID());
          }
        }
      }
    }
  }


}