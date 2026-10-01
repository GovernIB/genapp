
package org.fundaciobit.genappsqltutorial.persistence;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.fundaciobit.genappsqltutorial.model.entity.*;
import org.fundaciobit.genappsqltutorial.model.fields.*;
import org.fundaciobit.genappsqltutorial.model.dao.*;
import org.fundaciobit.genapp.common.query.TableName;
import org.fundaciobit.genapp.common.i18n.I18NException;


public class ProductsJPAManager
         extends AbstractJPAManager<Products, Long>
         implements ProductsIJPAManager, IProductsManager, ProductsFields {



    public static final TableName<Products> _TABLENAME =  new TableName<Products>("ProductsJPA");


    @PersistenceContext
    protected EntityManager __em;

    public ProductsJPAManager() {
    }

    protected ProductsJPAManager(EntityManager __em) {
      this.__em = __em;
    }

    @Override
    public EntityManager getEntityManager() {
      return this.__em;
    }
    public Class<?> getJPAClass() {
        return ProductsJPA. class;
    }



    public TableName<Products> getTableName() {
        return _TABLENAME;
    }


    @Override
    protected String getTableNameVariable() {
        return _TABLE_MODEL;
    }


    public Products[] listToArray(List<Products> list)  {
        if(list == null) { return null; };
        return list.toArray(new Products[list.size()]);
    };

    public Products create( java.lang.String _productname_, java.lang.Long _supplierid_, java.lang.Long _categoryid_, java.lang.String _unit_, java.lang.Double _price_) throws I18NException {
        ProductsJPA __bean =  new ProductsJPA(_productname_,_supplierid_,_categoryid_,_unit_,_price_);
        return create(__bean);
    }



 public void delete(long _productid_) {
   delete(findByPrimaryKey(_productid_));
 }




    public Products findByPrimaryKey(long _productid_) {
        return __em.find(ProductsJPA.class, _productid_);  
    }
    @Override
    protected Products getJPAInstance(Products __bean) {
        return convertToJPA(__bean);
    }


    public static ProductsJPA convertToJPA(Products __bean) {
      if (__bean == null) {
        return null;
      }
      if(__bean instanceof ProductsJPA) {
        return (ProductsJPA)__bean;
      }
      
      return ProductsJPA.toJPA(__bean);
    }


}