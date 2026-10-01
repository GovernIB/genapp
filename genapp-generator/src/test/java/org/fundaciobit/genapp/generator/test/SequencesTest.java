package org.fundaciobit.genapp.generator.test;

import java.io.File;

import org.fundaciobit.genapp.Project;
import org.fundaciobit.genapp.generator.ProjectValidator;
import org.fundaciobit.genapp.generator.gui.RebApp;

/**
 * 
 * @author anadal
 *
 */
public class SequencesTest {

    public static void main(String[] args) {
        try {
            File f = new File(
                    "D:\\dades\\dades\\CarpetesPersonals\\ProgramacioPortaFIB3\\enviafib\\rungenapp\\enviafib.genappjson");

            Project project = RebApp.readProjectFromFile(f);

            StringBuffer str = ProjectValidator.checkAll(project);
            str.trimToSize();

            if (str.length() == 0) {
                System.out.println(" TOT OK.");
            } else {
                System.err.println(str.toString());
            }

            System.out.println(" --- FINAL ---");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
