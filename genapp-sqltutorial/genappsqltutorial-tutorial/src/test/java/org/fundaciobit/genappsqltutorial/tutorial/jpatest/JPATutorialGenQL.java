package org.fundaciobit.genappsqltutorial.tutorial.jpatest;

import org.fundaciobit.genappsqltutorial.tutorial.dao.DAOManager;
import org.fundaciobit.genappsqltutorial.tutorial.dao.JPADAOProvider;
import org.fundaciobit.genappsqltutorial.tutorial.printer.ConsoleTableFormatPrinterResultsImpl;
import org.fundaciobit.genappsqltutorial.tutorial.printer.PrinterResultsManager;
import org.fundaciobit.genappsqltutorial.tutorial.units.SQL01Select;
import org.fundaciobit.genappsqltutorial.tutorial.units.SQL02SelectDistinct;
import org.fundaciobit.genappsqltutorial.tutorial.units.SQL03Where;
import org.fundaciobit.genappsqltutorial.tutorial.units.SQL04AndOrNot;

/**
 * 
 * @author anadal
 *
 */
public class JPATutorialGenQL {

    public static void main(String[] args) {

        JPADAOProvider dao = new JPADAOProvider();
        try {
            DAOManager.setDAOProvider(dao);
            PrinterResultsManager.setPrinterResults(new ConsoleTableFormatPrinterResultsImpl());

            unitSQL01Select(args);

            // unitSQL02SelectDistinct(args);

            // unitSQL03Where(args);

            // unitSQL04AndOrNot(args);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dao != null) {
                try {
                    dao.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void unitSQL01Select(String[] args) throws Exception {

        SQL01Select s = new SQL01Select();

        s.fullSelect();

        s.fieldsSelect();

    }

    public static void unitSQL02SelectDistinct(String[] args) throws Exception {

        SQL02SelectDistinct s = new SQL02SelectDistinct();
        s.selectDistinct();

    }

    public static void unitSQL03Where(String[] args) throws Exception {

        SQL03Where s = new SQL03Where();

        s.where1();

        s.whereOperatorsEqual();

        // TODO Falten mètodes

    }

    public static void unitSQL04AndOrNot(String[] args) throws Exception {

        SQL04AndOrNot s = null;

        s = new SQL04AndOrNot();

        s.and();

        s.or1();
        s.or2();

        s.not1();
        s.not2();

        s.combiningAndOrNot1();
        s.combiningAndOrNot2();

    }

}
