package org.fundaciobit.genappsqltutorial.tutorial.jpatest;

import org.fundaciobit.genappsqltutorial.tutorial.dao.DAOManager;
import org.fundaciobit.genappsqltutorial.tutorial.dao.JPADAOProvider;
import org.fundaciobit.genappsqltutorial.tutorial.printer.ConsoleTableFormatPrinterResultsImpl;
import org.fundaciobit.genappsqltutorial.tutorial.printer.PrinterResultsManager;
import org.fundaciobit.genappsqltutorial.tutorial.units.SQL01Select;
import org.fundaciobit.genappsqltutorial.tutorial.units.SQL02SelectDistinct;
import org.fundaciobit.genappsqltutorial.tutorial.units.SQL03Where;
import org.fundaciobit.genappsqltutorial.tutorial.units.SQL04AndOrNot;
import org.fundaciobit.genappsqltutorial.tutorial.units.SQL06Insert;
import org.fundaciobit.genappsqltutorial.tutorial.units.SQL08Update;
import org.fundaciobit.genappsqltutorial.tutorial.units.SQL09Delete;
import org.fundaciobit.genappsqltutorial.tutorial.units.SQL11CountAvgSum;
import org.fundaciobit.genappsqltutorial.tutorial.units.SQL15Joins;
import org.fundaciobit.genappsqltutorial.tutorial.units.SQL17Union;
import org.fundaciobit.genappsqltutorial.tutorial.units.SQL18GroupBy;
import org.fundaciobit.genappsqltutorial.tutorial.units.SQL19Having;

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

            // unitSQL01Select(args);

            // unitSQL02SelectDistinct(args);

            // unitSQL03Where(args);

            // unitSQL04AndOrNot(args);

            // unitSQL06Insert(args);

            // unitSQL08Update(args);

            // unitSQL09Delete(args);

            // unitSQL11CountAvgSum(args);

            //unitSQL15Joins(args);
            
            //unitSQL17Union(args);
            
            //unitSQL18GroupBy(args);
            
            unitSQL19Having(args);

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

    public static void unitSQL06Insert(String[] args) throws Exception {
        SQL06Insert s = null;

        s = new SQL06Insert();

        s.insertCustomer();

        s.insertPartialCustomer();

    }

    public static void unitSQL08Update(String[] args) throws Exception {
        SQL08Update s = null;

        s = new SQL08Update();

        // s.updateMultipleRecordsNative();

        s.updateMultipleRecordsGenQL();

    }

    public static void unitSQL09Delete(String[] args) throws Exception {
        SQL09Delete d = new SQL09Delete();

        d.deleteAllRecords();

    }

    public static void unitSQL11CountAvgSum(String[] args) throws Exception {
        SQL11CountAvgSum s = new SQL11CountAvgSum();

        // s.updateMultipleRecordsNative();

        s.avgExample();

    }

    public static void unitSQL15Joins(String[] args) throws Exception {
        SQL15Joins s = new SQL15Joins();

        //s.joinKeyValue();

        s.joinSelect3Columns();
        
        //s.joinSelect4Columns();

    }
    
    
    public static void unitSQL17Union(String[] args) throws Exception {
        SQL17Union s = new SQL17Union();

        s.anotherUnionExample();

    }
    
    
    public static void unitSQL18GroupBy(String[] args) throws Exception {
        SQL18GroupBy s = new SQL18GroupBy();

        //s.exampleGroupBy2();
        
        s.groupByWithJoin();

    }
    
    
    public static void unitSQL19Having(String[] args) throws Exception {
        SQL19Having s = new SQL19Having();

        //s.exampleGroupBy2();
        
        s.exampleHaving4();

    }

}
