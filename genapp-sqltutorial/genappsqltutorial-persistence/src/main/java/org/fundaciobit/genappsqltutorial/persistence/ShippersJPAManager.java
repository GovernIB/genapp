
package org.fundaciobit.genappsqltutorial.persistence;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.fundaciobit.genappsqltutorial.model.entity.*;
import org.fundaciobit.genappsqltutorial.model.fields.*;
import org.fundaciobit.genappsqltutorial.model.dao.*;
import org.fundaciobit.genapp.common.query.TableName;
import org.fundaciobit.genapp.common.i18n.I18NException;


public class ShippersJPAManager
         extends AbstractJPAManager<Shippers, Long>
         implements ShippersIJPAManager, IShippersManager, ShippersFields {



    public static final TableName<Shippers> _TABLENAME =  new TableName<Shippers>("ShippersJPA");


    @PersistenceContext
    protected EntityManager __em;

    public ShippersJPAManager() {
    }

    protected ShippersJPAManager(EntityManager __em) {
      this.__em = __em;
    }

    @Override
    public EntityManager getEntityManager() {
      return this.__em;
    }
    public Class<?> getJPAClass() {
        return ShippersJPA. class;
    }



    public TableName<Shippers> getTableName() {
        return _TABLENAME;
    }


    @Override
    protected String getTableNameVariable() {
        return _TABLE_MODEL;
    }


    public Shippers[] listToArray(List<Shippers> list)  {
        if(list == null) { return null; };
        return list.toArray(new Shippers[list.size()]);
    };

    public Shippers create( java.lang.String _shippername_, java.lang.String _phone_) throws I18NException {
        ShippersJPA __bean =  new ShippersJPA(_shippername_,_phone_);
        return create(__bean);
    }



 public void delete(long _shipperid_) {
   delete(findByPrimaryKey(_shipperid_));
 }




    public Shippers findByPrimaryKey(long _shipperid_) {
        return __em.find(ShippersJPA.class, _shipperid_);  
    }
    @Override
    protected Shippers getJPAInstance(Shippers __bean) {
        return convertToJPA(__bean);
    }


    public static ShippersJPA convertToJPA(Shippers __bean) {
      if (__bean == null) {
        return null;
      }
      if(__bean instanceof ShippersJPA) {
        return (ShippersJPA)__bean;
      }
      
      return ShippersJPA.toJPA(__bean);
    }


}