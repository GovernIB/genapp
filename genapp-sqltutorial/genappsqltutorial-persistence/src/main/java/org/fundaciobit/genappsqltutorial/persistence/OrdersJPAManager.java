
package org.fundaciobit.genappsqltutorial.persistence;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.fundaciobit.genappsqltutorial.model.entity.*;
import org.fundaciobit.genappsqltutorial.model.fields.*;
import org.fundaciobit.genappsqltutorial.model.dao.*;
import org.fundaciobit.genapp.common.query.TableName;
import org.fundaciobit.genapp.common.i18n.I18NException;


public class OrdersJPAManager
         extends AbstractJPAManager<Orders, Long>
         implements OrdersIJPAManager, IOrdersManager, OrdersFields {




    private static final long serialVersionUID = 732986423L;

    public static final TableName<Orders> _TABLENAME =  new TableName<Orders>("OrdersJPA");


    @PersistenceContext
    protected EntityManager __em;

    public OrdersJPAManager() {
    }

    protected OrdersJPAManager(EntityManager __em) {
      this.__em = __em;
    }

    @Override
    public EntityManager getEntityManager() {
      return this.__em;
    }
    public Class<?> getJPAClass() {
        return OrdersJPA. class;
    }



    public TableName<Orders> getTableName() {
        return _TABLENAME;
    }


    @Override
    protected String getTableNameVariable() {
        return _TABLE_MODEL;
    }


    public Orders[] listToArray(List<Orders> list)  {
        if(list == null) { return null; };
        return list.toArray(new Orders[list.size()]);
    };

    public synchronized Orders create( java.lang.Long _customerid_, java.lang.Long _employeeid_, java.sql.Date _orderdate_, java.lang.Long _shipperid_) throws I18NException {
        OrdersJPA __bean =  new OrdersJPA(_customerid_,_employeeid_,_orderdate_,_shipperid_);
        return create(__bean);
    }



 public void delete(long _orderid_) {
   delete(findByPrimaryKey(_orderid_));
 }




    public Orders findByPrimaryKey(long _orderid_) {
        return __em.find(OrdersJPA.class, _orderid_);  
    }
    @Override
    protected Orders getJPAInstance(Orders __bean) {
        return convertToJPA(__bean);
    }


    public static OrdersJPA convertToJPA(Orders __bean) {
      if (__bean == null) {
        return null;
      }
      if(__bean instanceof OrdersJPA) {
        return (OrdersJPA)__bean;
      }
      
      return OrdersJPA.toJPA(__bean);
    }


}