package org.fundaciobit.genappsqltutorial.tutorial.printer;

/**
 * 
 * @author anadal
 *
 */
public class PrinterResultsManager {

    private static IPrinterResults printerResults = null;
    
    
    /**
     * 
     * @return
     * @throws Exception
     */
    public static IPrinterResults getPrinterResults() throws Exception {
        if (printerResults == null) {
            throw new Exception("Abans ha de cridar a PrinterResultsManager.setPrinterResults(...).");
        }
        return printerResults;
    }
    
    
    /**
     * 
     * @param pr
     * @throws Exception
     */
    public static void setPrinterResults(IPrinterResults pr) throws Exception {
        printerResults = pr;
    }
    
}
