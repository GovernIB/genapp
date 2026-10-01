package org.fundaciobit.genappsqltutorial.model;

import org.fundaciobit.genappsqltutorial.model.dao.*;

public interface IGenAppSqlTutorialDaoManagers {
	public ICategoriesManager getCategoriesManager();
	public ICustomersManager getCustomersManager();
	public IEmployeesManager getEmployeesManager();
	public IFitxerManager getFitxerManager();
	public IIdiomaManager getIdiomaManager();
	public IOrderDetailsManager getOrderDetailsManager();
	public IOrdersManager getOrdersManager();
	public IProductsManager getProductsManager();
	public IShippersManager getShippersManager();
	public ISuppliersManager getSuppliersManager();
	public ITraduccioManager getTraduccioManager();

}