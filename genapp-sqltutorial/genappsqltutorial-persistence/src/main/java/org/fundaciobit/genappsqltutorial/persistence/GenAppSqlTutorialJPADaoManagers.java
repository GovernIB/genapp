package org.fundaciobit.genappsqltutorial.persistence;

import org.fundaciobit.genappsqltutorial.model.*;
import org.fundaciobit.genappsqltutorial.model.dao.*;
import javax.persistence.EntityManager;

public final class GenAppSqlTutorialJPADaoManagers implements IGenAppSqlTutorialDaoManagers{

   private final CategoriesJPAManager gas_categories;
   private final CustomersJPAManager gas_customers;
   private final EmployeesJPAManager gas_employees;
   private final FitxerJPAManager gas_fitxer;
   private final IdiomaJPAManager gas_idioma;
   private final OrderDetailsJPAManager gas_orderdetails;
   private final OrdersJPAManager gas_orders;
   private final ProductsJPAManager gas_products;
   private final ShippersJPAManager gas_shippers;
   private final SuppliersJPAManager gas_suppliers;
   private final TraduccioJPAManager gas_traduccio;

  public  GenAppSqlTutorialJPADaoManagers(EntityManager __em) {
    this.gas_categories = new CategoriesJPAManager(__em);
    this.gas_customers = new CustomersJPAManager(__em);
    this.gas_employees = new EmployeesJPAManager(__em);
    this.gas_fitxer = new FitxerJPAManager(__em);
    this.gas_idioma = new IdiomaJPAManager(__em);
    this.gas_orderdetails = new OrderDetailsJPAManager(__em);
    this.gas_orders = new OrdersJPAManager(__em);
    this.gas_products = new ProductsJPAManager(__em);
    this.gas_shippers = new ShippersJPAManager(__em);
    this.gas_suppliers = new SuppliersJPAManager(__em);
    this.gas_traduccio = new TraduccioJPAManager(__em);
  }

    public ICategoriesManager getCategoriesManager() {
        return this.gas_categories;
    };

    public ICustomersManager getCustomersManager() {
        return this.gas_customers;
    };

    public IEmployeesManager getEmployeesManager() {
        return this.gas_employees;
    };

    public IFitxerManager getFitxerManager() {
        return this.gas_fitxer;
    };

    public IIdiomaManager getIdiomaManager() {
        return this.gas_idioma;
    };

    public IOrderDetailsManager getOrderDetailsManager() {
        return this.gas_orderdetails;
    };

    public IOrdersManager getOrdersManager() {
        return this.gas_orders;
    };

    public IProductsManager getProductsManager() {
        return this.gas_products;
    };

    public IShippersManager getShippersManager() {
        return this.gas_shippers;
    };

    public ISuppliersManager getSuppliersManager() {
        return this.gas_suppliers;
    };

    public ITraduccioManager getTraduccioManager() {
        return this.gas_traduccio;
    };


}