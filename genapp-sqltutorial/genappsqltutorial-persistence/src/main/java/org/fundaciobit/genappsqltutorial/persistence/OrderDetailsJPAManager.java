
package org.fundaciobit.genappsqltutorial.persistence;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.fundaciobit.genappsqltutorial.model.entity.*;
import org.fundaciobit.genappsqltutorial.model.fields.*;
import org.fundaciobit.genappsqltutorial.model.dao.*;
import org.fundaciobit.genapp.common.query.TableName;
import org.fundaciobit.genapp.common.i18n.I18NException;


public class OrderDetailsJPAManager
         extends AbstractJPAManager<OrderDetails, Long>
         implements OrderDetailsIJPAManager, IOrderDetailsManager, OrderDetailsFields {




    private static final long serialVersionUID = -585031258L;

    public static final TableName<OrderDetails> _TABLENAME =  new TableName<OrderDetails>("OrderDetailsJPA");


    @PersistenceContext
    protected EntityManager __em;

    public OrderDetailsJPAManager() {
    }

    protected OrderDetailsJPAManager(EntityManager __em) {
      this.__em = __em;
    }

    @Override
    public EntityManager getEntityManager() {
      return this.__em;
    }
    public Class<?> getJPAClass() {
        return OrderDetailsJPA. class;
    }



    public TableName<OrderDetails> getTableName() {
        return _TABLENAME;
    }


    @Override
    protected String getTableNameVariable() {
        return _TABLE_MODEL;
    }


    public OrderDetails[] listToArray(List<OrderDetails> list)  {
        if(list == null) { return null; };
        return list.toArray(new OrderDetails[list.size()]);
    };

    public synchronized OrderDetails create( java.lang.Long _orderid_, java.lang.Long _productid_, java.lang.Integer _quantity_) throws I18NException {
        OrderDetailsJPA __bean =  new OrderDetailsJPA(_orderid_,_productid_,_quantity_);
        return create(__bean);
    }



 public void delete(long _orderdetailid_) {
   delete(findByPrimaryKey(_orderdetailid_));
 }




    public OrderDetails findByPrimaryKey(long _orderdetailid_) {
        return __em.find(OrderDetailsJPA.class, _orderdetailid_);  
    }
    @Override
    protected OrderDetails getJPAInstance(OrderDetails __bean) {
        return convertToJPA(__bean);
    }


    public static OrderDetailsJPA convertToJPA(OrderDetails __bean) {
      if (__bean == null) {
        return null;
      }
      if(__bean instanceof OrderDetailsJPA) {
        return (OrderDetailsJPA)__bean;
      }
      
      return OrderDetailsJPA.toJPA(__bean);
    }


}