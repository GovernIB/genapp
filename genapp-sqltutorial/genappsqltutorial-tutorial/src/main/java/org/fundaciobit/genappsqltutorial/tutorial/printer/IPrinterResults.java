package org.fundaciobit.genappsqltutorial.tutorial.printer;

import java.util.List;

import org.fundaciobit.genapp.common.KeyValue;
import org.fundaciobit.genapp.common.query.StringField;
import org.fundaciobit.genappsqltutorial.model.entity.Customers;
import org.fundaciobit.genappsqltutorial.model.entity.Products;

/**
 * 
 * @author anadal
 *
 */
public interface IPrinterResults {
    
   
    @Deprecated
    public <T> void print(List<T> listString) throws Exception;

    public <T> void print(List<T> list, String titol) throws Exception;

    public <T> void print(T value, String titol) throws Exception;

    public void print(Customers c) throws Exception;

    public void printCustomers(List<Customers> list) throws Exception;

    public void printProducts(List<Products> list) throws Exception;

    public <T> void print(StringField columnTitle1, StringField columnTitle2, List<KeyValue<T>> values)
            throws Exception;
    
    public void reset();

}
