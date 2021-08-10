package org.fundaciobit.genappsqltutorial.tutorial.printer;

import java.io.PrintStream;
import java.util.ArrayList;

import org.fundaciobit.genapp.common.KeyValue;
import org.fundaciobit.genapp.common.query.Field;
import org.fundaciobit.genapp.common.query.StringField;
import org.fundaciobit.genappsqltutorial.model.entity.Customers;
import org.fundaciobit.genappsqltutorial.model.entity.Products;
import org.fundaciobit.genappsqltutorial.model.fields.CustomersFields;
import org.fundaciobit.genappsqltutorial.model.fields.ProductsFields;
import org.fundaciobit.genappsqltutorial.persistence.CustomersJPA;
import org.fundaciobit.genappsqltutorial.persistence.ProductsJPA;
import org.fundaciobit.genappsqltutorial.tutorial.printer.utils.MultiColumnPrinter;

import java.util.List;


/**
 * 
 * @author anadal
 *
 */
public class ConsoleTableFormatPrinterResultsImpl implements IPrinterResults {

    // ================== METODES DE IPRINTRESULTS INTERFACE =====================
    
    @Override
    public <T> void print(List<T> list) {
        String titol = " LIST ";
        print(list, titol);
    }
    
    
    
    @Override
    public <T> void print(List<T> list, String titol) {
        System.out.println(" ========== " + titol + " =============");
        
        if (list.isEmpty()) {
            return;
        }
        
        //System.out.println(" list.get(0).getClass() => " +  list.get(0).getClass());
        
        Class<?> type = list.get(0).getClass();
       
        if (type.equals(CustomersJPA.class)) {
            printCustomers((List<Customers>)list);
        } else if (type.equals(ProductsJPA.class)) {
            printProducts((List<Products>)list);
        } else {
            for (T c : list) {
                System.out.println(String.valueOf(c));
            }
        }
    }

    @Override
    public <T> void print(T value, String titol) {
        System.out.println(" ========== " + titol + " =============");
        System.out.println(String.valueOf(value));
    }

    @Override
    public void print(Customers c) {
        List<Customers> list = new ArrayList<Customers>();
        list.add(c);
        printCustomers(list);
    }

    @Override
    public void printCustomers(List<Customers> list) {

        Field<?>[] titlesGA = CustomersFields.ALL_CUSTOMERS_FIELDS;

        String[] titles = new String[titlesGA.length];

        for (int i = 0; i < titlesGA.length; i++) {
            titles[i] = titlesGA[i].getJavaName().toUpperCase();
        }

        PrintStream printStream = System.out;

        MultiColumnPrinter mfc = new MultiColumnPrinter(printStream, titles);

        for (Customers c : list) {

            String[] row = new String[titlesGA.length];

            row[0] = String.valueOf(c.getCustomerid());
            row[1] = String.valueOf(c.getCustomername());
            row[2] = String.valueOf(c.getContactname());
            row[3] = String.valueOf(c.getAddress());
            row[4] = String.valueOf(c.getCity());
            row[5] = String.valueOf(c.getCountry());
            row[6] = String.valueOf(c.getPostalcode());

            mfc.add(row);
        }

        mfc.println();

    }

    @Override
    public void printProducts(List<Products> list) {

        Field<?>[] titlesGA = ProductsFields.ALL_PRODUCTS_FIELDS;

        String[] titles = new String[titlesGA.length];

        for (int i = 0; i < titlesGA.length; i++) {
            titles[i] = titlesGA[i].getJavaName().toUpperCase();
        }

        PrintStream printStream = System.out;

        MultiColumnPrinter mfc = new MultiColumnPrinter(printStream, titles);

        for (Products c : list) {

            String[] row = new String[titlesGA.length];

            row[0] = String.valueOf(c.getProductid());
            row[1] = String.valueOf(c.getProductname());
            row[2] = String.valueOf(c.getSupplierid());
            row[3] = String.valueOf(c.getCategoryid());
            row[4] = String.valueOf(c.getUnit());
            row[5] = String.valueOf(c.getPrice());

            mfc.add(row);
        }

        mfc.println();

    }

    @Override
    public <T> void print(StringField columnTitle1, StringField columnTitle2, List<KeyValue<T>> values) {
        String[] titles = new String[] { columnTitle1.javaName, columnTitle2.javaName };

        PrintStream printStream = System.out;

        MultiColumnPrinter mfc = new MultiColumnPrinter(printStream, titles);

        for (KeyValue<T> kv : values) {

            String[] row = new String[titles.length];

            row[0] = String.valueOf(kv.getKey());
            row[1] = String.valueOf(kv.getValue());

            mfc.add(row);
        }

        mfc.print();
    }



    @Override
    public void reset() {
        // No fa res
    }
    
    
}
