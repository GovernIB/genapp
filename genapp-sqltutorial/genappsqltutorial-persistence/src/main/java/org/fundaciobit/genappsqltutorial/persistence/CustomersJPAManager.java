
package org.fundaciobit.genappsqltutorial.persistence;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.fundaciobit.genappsqltutorial.model.entity.*;
import org.fundaciobit.genappsqltutorial.model.fields.*;
import org.fundaciobit.genappsqltutorial.model.dao.*;
import org.fundaciobit.genapp.common.query.TableName;
import org.fundaciobit.genapp.common.i18n.I18NException;


public class CustomersJPAManager
         extends AbstractJPAManager<Customers, Long>
         implements CustomersIJPAManager, ICustomersManager, CustomersFields {




    private static final long serialVersionUID = -1886253721L;

    public static final TableName<Customers> _TABLENAME =  new TableName<Customers>("CustomersJPA");


    @PersistenceContext
    protected EntityManager __em;

    public CustomersJPAManager() {
    }

    protected CustomersJPAManager(EntityManager __em) {
      this.__em = __em;
    }

    @Override
    public EntityManager getEntityManager() {
      return this.__em;
    }
    public Class<?> getJPAClass() {
        return CustomersJPA. class;
    }



    public TableName<Customers> getTableName() {
        return _TABLENAME;
    }


    @Override
    protected String getTableNameVariable() {
        return _TABLE_MODEL;
    }


    public Customers[] listToArray(List<Customers> list)  {
        if(list == null) { return null; };
        return list.toArray(new Customers[list.size()]);
    };

    public synchronized Customers create( java.lang.String _customername_, java.lang.String _contactname_, java.lang.String _address_, java.lang.String _city_, java.lang.String _country_, java.lang.String _postalcode_) throws I18NException {
        CustomersJPA __bean =  new CustomersJPA(_customername_,_contactname_,_address_,_city_,_country_,_postalcode_);
        return create(__bean);
    }



 public void delete(long _customerid_) {
   delete(findByPrimaryKey(_customerid_));
 }




    public Customers findByPrimaryKey(long _customerid_) {
        return __em.find(CustomersJPA.class, _customerid_);  
    }
    @Override
    protected Customers getJPAInstance(Customers __bean) {
        return convertToJPA(__bean);
    }


    public static CustomersJPA convertToJPA(Customers __bean) {
      if (__bean == null) {
        return null;
      }
      if(__bean instanceof CustomersJPA) {
        return (CustomersJPA)__bean;
      }
      
      return CustomersJPA.toJPA(__bean);
    }


}