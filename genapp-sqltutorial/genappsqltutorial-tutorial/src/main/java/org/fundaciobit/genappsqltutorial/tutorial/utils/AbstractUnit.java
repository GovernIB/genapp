package org.fundaciobit.genappsqltutorial.tutorial.utils;

import java.util.List;

import org.fundaciobit.genapp.common.KeyValue;
import org.fundaciobit.genapp.common.query.StringField;
import org.fundaciobit.genappsqltutorial.model.dao.ICustomersManager;
import org.fundaciobit.genappsqltutorial.model.dao.IProductsManager;
import org.fundaciobit.genappsqltutorial.model.entity.Customers;
import org.fundaciobit.genappsqltutorial.model.entity.Products;
import org.fundaciobit.genappsqltutorial.tutorial.dao.DAOManager;
import org.fundaciobit.genappsqltutorial.tutorial.dao.IDAOProvider;
import org.fundaciobit.genappsqltutorial.tutorial.printer.IPrinterResults;
import org.fundaciobit.genappsqltutorial.tutorial.printer.PrinterResultsManager;

/**
 * 
 * @author anadal
 *
 */
public abstract class AbstractUnit implements IPrinterResults, IDAOProvider {

    
    public AbstractUnit() {
    }

    @Override
    public ICustomersManager getCustomerManager() throws Exception {
        return DAOManager.getDAOProvider().getCustomerManager();
    }

    @Override
    public IProductsManager getProductsManager() throws Exception {
        return DAOManager.getDAOProvider().getProductsManager();
    }
    
    @Override
    public void close() throws Exception {
        DAOManager.getDAOProvider().close();
    }
    
    
    // Printer Results
    
    @Override
    public <T> void print(List<T> listString) throws Exception {
        PrinterResultsManager.getPrinterResults().print(listString);
    }

    @Override
    public <T> void print(List<T> listString, String titol) throws Exception {
        PrinterResultsManager.getPrinterResults().print(listString, titol);
    }

    public <T> void print(T value, String titol) throws Exception {
        PrinterResultsManager.getPrinterResults().print(value, titol);
    }

    public  void print(Customers c)  throws Exception  {
        PrinterResultsManager.getPrinterResults().print(c);
    }

    public  void printCustomers(List<Customers> list)  throws Exception {
        PrinterResultsManager.getPrinterResults().printCustomers(list);
    }

    public  void printProducts(List<Products> list)  throws Exception  {
        PrinterResultsManager.getPrinterResults().printProducts(list);
    }

    public <T> void print(StringField columnTitle1, StringField columnTitle2, List<KeyValue<T>> values)  throws Exception  {
        PrinterResultsManager.getPrinterResults().print(columnTitle1, columnTitle2, values);        
    }

    @Override
    public void reset() {
    }

}
