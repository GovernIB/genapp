package org.fundaciobit.genappsqltutorial.tutorial.otherstests;

import org.fundaciobit.genappsqltutorial.tutorial.dao.DAOManager;
import org.fundaciobit.genappsqltutorial.tutorial.dao.JPADAOProvider;
import org.fundaciobit.genappsqltutorial.tutorial.printer.ConsoleTableFormatPrinterResultsImpl;
import org.fundaciobit.genappsqltutorial.tutorial.printer.PrinterResultsManager;
import org.fundaciobit.genappsqltutorial.tutorial.units.SQL01Select;
import org.fundaciobit.genappsqltutorial.tutorial.utils.compiler.JavaCompileAndExecute;

public class TestCompile {

    public static void main(String[] args) {
        try {
        
            JPADAOProvider dao = new JPADAOProvider();
        
                DAOManager.setDAOProvider(dao);
                PrinterResultsManager.setPrinterResults(new ConsoleTableFormatPrinterResultsImpl());
            
            String paquet = "genappsqltutorial";
            String className = "Test123124124";
            String methodName = "executeTest123413241234";
            
         // Prepare source somehow.
            String source = "package " + paquet +";"
                    + "import org.fundaciobit.genapp.common.KeyValue;"
                    + "import org.fundaciobit.genapp.common.query.SelectMultipleKeyValue;\r\n"
                    + "import org.fundaciobit.genappsqltutorial.model.fields.CustomersFields;\r\n"
                    + "import org.fundaciobit.genappsqltutorial.tutorial.utils.AbstractUnit;"
                    + "public class " + className + " extends AbstractUnit {"
                    + "  static { "
                    + "    System.out.print(\"hello \");"
                    + "  }"                    
                    + "  public " + className + "() {"
                    + "    System.out.print(\"world \");"
                    + "  } "
                    + "  public void " + methodName + "() throws Exception {"
                    + "    System.out.println(\"!!!!!\");"
                    + "    " + SQL01Select.class.getName() + " test;"
                    + "    test = new " + SQL01Select.class.getName() + "();"
                    + "    test.fullSelect();"
                    + "    test.close();"
                    
                    + "  }"
                    + "}";
            
        

            Object instance = JavaCompileAndExecute.compileAndExecute(paquet, className, methodName, source, TestCompile.class.getClassLoader());
            
            System.out.println("INSTANCE: => " + instance); // Should print "test.Test@hashcode".



        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
