package org.fundaciobit.genappsqltutorial.tutorial.printer;

public class PrinterResultsManager {

    

    
    private static IPrinterResults printerResults = null;
    
    
    
    public static IPrinterResults getPrinterResults() throws Exception {
        if (printerResults == null) {
            throw new Exception("Abans ha de cridar a PrinterResultsManager.setPrinterResults(...).");
        }
        return printerResults;
    }
    
    
    
    public static void setPrinterResults(IPrinterResults pr) throws Exception {
        printerResults = pr;
    }
    
}
