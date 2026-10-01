
package org.fundaciobit.genappsqltutorial.persistence;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.fundaciobit.genappsqltutorial.model.entity.*;
import org.fundaciobit.genappsqltutorial.model.fields.*;
import org.fundaciobit.genappsqltutorial.model.dao.*;
import org.fundaciobit.genapp.common.query.TableName;
import org.fundaciobit.genapp.common.i18n.I18NException;


public class CategoriesJPAManager
         extends AbstractJPAManager<Categories, Long>
         implements CategoriesIJPAManager, ICategoriesManager, CategoriesFields {



    public static final TableName<Categories> _TABLENAME =  new TableName<Categories>("CategoriesJPA");


    @PersistenceContext
    protected EntityManager __em;

    public CategoriesJPAManager() {
    }

    protected CategoriesJPAManager(EntityManager __em) {
      this.__em = __em;
    }

    @Override
    public EntityManager getEntityManager() {
      return this.__em;
    }
    public Class<?> getJPAClass() {
        return CategoriesJPA. class;
    }



    public TableName<Categories> getTableName() {
        return _TABLENAME;
    }


    @Override
    protected String getTableNameVariable() {
        return _TABLE_MODEL;
    }


    public Categories[] listToArray(List<Categories> list)  {
        if(list == null) { return null; };
        return list.toArray(new Categories[list.size()]);
    };

    public Categories create( java.lang.String _categoryName_, java.lang.String _description_) throws I18NException {
        CategoriesJPA __bean =  new CategoriesJPA(_categoryName_,_description_);
        return create(__bean);
    }



 public void delete(long _categoryid_) {
   delete(findByPrimaryKey(_categoryid_));
 }




    public Categories findByPrimaryKey(long _categoryid_) {
        return __em.find(CategoriesJPA.class, _categoryid_);  
    }
    @Override
    protected Categories getJPAInstance(Categories __bean) {
        return convertToJPA(__bean);
    }


    public static CategoriesJPA convertToJPA(Categories __bean) {
      if (__bean == null) {
        return null;
      }
      if(__bean instanceof CategoriesJPA) {
        return (CategoriesJPA)__bean;
      }
      
      return CategoriesJPA.toJPA(__bean);
    }


}