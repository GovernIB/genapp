package org.fundaciobit.genappsqltutorial.back.utils;

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
import org.fundaciobit.genappsqltutorial.tutorial.printer.IPrinterResults;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 
 * @author anadal
 *
 */
public class WebFormatPrinterResultsImpl implements IPrinterResults {

    // private StringBuilder str = new StringBuilder();

    // ================== METODES DE IPRINTRESULTS INTERFACE =====================

    @Override
    public <T> void print(List<T> list) {
        String titol = " LIST ";
        print(list, titol);
    }

    @Override
    public <T> void print(List<T> list, String titol) {

        if (list.isEmpty()) {
            return;
        }

        Class<?> type = list.get(0).getClass();

        if (type.equals(CustomersJPA.class)) {
            printCustomers((List<Customers>) list);
        } else if (type.equals(ProductsJPA.class)) {
            printProducts((List<Products>) list);
        } else {

            String[] titols = new String[] { titol };
            String data[][] = new String[list.size()][1];
            int count = 0;
            for (T c : list) {
                data[count][0] = String.valueOf(c);
                count++;
            }

            printTableWeb(titols, data);
        }
    }

    @Override
    public <T> void print(T value, String titol) {

        String[] titols = new String[] { titol };
        String data[][] = new String[1][1];
        data[0][0] = String.valueOf(value);
        printTableWeb(titols, data);
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

        String[][] data = new String[list.size()][];

        int count = 0;

        for (Customers c : list) {

            String[] row = new String[titlesGA.length];

            row[0] = String.valueOf(c.getCustomerid());
            row[1] = String.valueOf(c.getCustomername());
            row[2] = String.valueOf(c.getContactname());
            row[3] = String.valueOf(c.getAddress());
            row[4] = String.valueOf(c.getCity());
            row[5] = String.valueOf(c.getCountry());
            row[6] = String.valueOf(c.getPostalcode());

            data[count] = row;
            count++;

        }

        printTableWeb(titles, data);

    }

    @Override
    public void printProducts(List<Products> list) {

        Field<?>[] titlesGA = ProductsFields.ALL_PRODUCTS_FIELDS;

        String[] titles = new String[titlesGA.length];

        for (int i = 0; i < titlesGA.length; i++) {
            titles[i] = titlesGA[i].getJavaName().toUpperCase();
        }

        String[][] data = new String[list.size()][];

        int count = 0;

        for (Products c : list) {

            String[] row = new String[titlesGA.length];

            row[0] = String.valueOf(c.getProductid());
            row[1] = String.valueOf(c.getProductname());
            row[2] = String.valueOf(c.getSupplierid());
            row[3] = String.valueOf(c.getCategoryid());
            row[4] = String.valueOf(c.getUnit());
            row[5] = String.valueOf(c.getPrice());

            data[count] = row;
            count++;

        }

        printTableWeb(titles, data);

    }

    @Override
    public <T> void print(StringField columnTitle1, StringField columnTitle2,
            List<KeyValue<T>> values) {
        String[] titles = new String[] { columnTitle1.javaName, columnTitle2.javaName };

        String[][] data = new String[2][];

        int count = 0;

        for (KeyValue<T> kv : values) {

            String[] row = new String[titles.length];

            row[0] = String.valueOf(kv.getKey());
            row[1] = String.valueOf(kv.getValue());

            data[count] = row;
            count++;

        }

        printTableWeb(titles, data);
    }

    private void printTableWeb(String[] titles, String[][] data) {

        StringBuilder str = getStringBuilderBySession();

        str.append("<table class=\"table table-sm table-bordered\">").append("\n");

        str.append("<thead>").append("\n");
        printRow(titles, "th");
        str.append("</thead>").append("\n");

        str.append("<tbody>").append("\n");
        for (String[] cols : data) {
            str.append("<tr>").append("\n");
            printRow(cols, "td");
            str.append("</tr>").append("\n");
        }
        str.append("</tbody>").append("\n");

        str.append("</table>").append("\n");

    }

    private void printRow(String[] titles, String columntag) {
        StringBuilder str = getStringBuilderBySession();
        for (String t : titles) {
            str.append("<").append(columntag).append(">");
            str.append(t);
            str.append("</").append(columntag).append(">").append("\n");
        }
    }

    private StringBuilder getStringBuilderBySession() {

        HttpServletRequest request;
        request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();

        HttpSession session = request.getSession();

        StringBuilder str = (StringBuilder) session.getAttribute("STRINGBUILDER_PRINTER_RESULTS");

        if (str == null) {
            str = new StringBuilder();
            session.setAttribute("STRINGBUILDER_PRINTER_RESULTS", str);
        }

        return str;

    }

    @Override
    public void reset() {
        StringBuilder str = getStringBuilderBySession();
        str.setLength(0);

    }
    
    public String getResult() {

        HttpServletRequest request;
        request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();

        HttpSession session = request.getSession();

        StringBuilder str = (StringBuilder) session.getAttribute("STRINGBUILDER_PRINTER_RESULTS");

        if (str == null) {
            return "<b> STRINGBUILDER ES BUIT</b>";
        }

        return str.toString();
    }

}
