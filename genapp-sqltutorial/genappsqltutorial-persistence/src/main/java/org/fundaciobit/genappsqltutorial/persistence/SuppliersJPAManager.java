
package org.fundaciobit.genappsqltutorial.persistence;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.fundaciobit.genappsqltutorial.model.entity.*;
import org.fundaciobit.genappsqltutorial.model.fields.*;
import org.fundaciobit.genappsqltutorial.model.dao.*;
import org.fundaciobit.genapp.common.query.TableName;
import org.fundaciobit.genapp.common.i18n.I18NException;


public class SuppliersJPAManager
         extends AbstractJPAManager<Suppliers, Long>
         implements SuppliersIJPAManager, ISuppliersManager, SuppliersFields {




    private static final long serialVersionUID = 1558819513L;

    public static final TableName<Suppliers> _TABLENAME =  new TableName<Suppliers>("SuppliersJPA");


    @PersistenceContext
    protected EntityManager __em;

    public SuppliersJPAManager() {
    }

    protected SuppliersJPAManager(EntityManager __em) {
      this.__em = __em;
    }

    @Override
    public EntityManager getEntityManager() {
      return this.__em;
    }
    public Class<?> getJPAClass() {
        return SuppliersJPA. class;
    }



    public TableName<Suppliers> getTableName() {
        return _TABLENAME;
    }


    @Override
    protected String getTableNameVariable() {
        return _TABLE_MODEL;
    }


    public Suppliers[] listToArray(List<Suppliers> list)  {
        if(list == null) { return null; };
        return list.toArray(new Suppliers[list.size()]);
    };

    public synchronized Suppliers create( java.lang.String _suppliername_, java.lang.String _contactname_, java.lang.String _address_, java.lang.String _city_, java.lang.String _postalcode_, java.lang.String _country_, java.lang.String _phone_) throws I18NException {
        SuppliersJPA __bean =  new SuppliersJPA(_suppliername_,_contactname_,_address_,_city_,_postalcode_,_country_,_phone_);
        return create(__bean);
    }



 public void delete(long _supplierid_) {
   delete(findByPrimaryKey(_supplierid_));
 }




    public Suppliers findByPrimaryKey(long _supplierid_) {
        return __em.find(SuppliersJPA.class, _supplierid_);  
    }
    @Override
    protected Suppliers getJPAInstance(Suppliers __bean) {
        return convertToJPA(__bean);
    }


    public static SuppliersJPA convertToJPA(Suppliers __bean) {
      if (__bean == null) {
        return null;
      }
      if(__bean instanceof SuppliersJPA) {
        return (SuppliersJPA)__bean;
      }
      
      return SuppliersJPA.toJPA(__bean);
    }


}