package org.fundaciobit.genapp.traductor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;

import org.fundaciobit.genapp.FieldInfo;
import org.fundaciobit.genapp.TableInfo;
import org.fundaciobit.genapp.generator.gui.RebApp;
import org.fundaciobit.genapp.generator.gui.SharedData;

/**
 * 
 * @author anadal
 *
 */
public class TraduccioTipusGenApp implements ITraduccioTipus {

    protected final File projectDir;

    public TraduccioTipusGenApp(File projectDir) {
        super();
        this.projectDir = projectDir;
    }
    
    @Override
    public String getNom() {
        return "GenApp[" + projectDir.getAbsolutePath() + "]";
    }

    @Override
    public String getTipus() {
        return "GenApp";
    }

    @Override
    public List<ITraduccioItem> read() throws Exception {

        List<ITraduccioItem> llista = new ArrayList<ITraduccioItem>();
        for (TableInfo table : SharedData.data.tables) {

            TraduccioGenAppTableItem trat = new TraduccioGenAppTableItem(getTipus(), table,
                    table.nameJava.toLowerCase() + "." + table.nameJava.toLowerCase());

            llista.add(trat);

            TraduccioGenAppTablePluralItem trat2 = new TraduccioGenAppTablePluralItem(getTipus(), table,
                    table.nameJava.toLowerCase() + "." + table.nameJava.toLowerCase() + ".plural");

            llista.add(trat2);

            for (FieldInfo field : table.fields) {
                TraduccioGenAppFieldItem tra = new TraduccioGenAppFieldItem(getTipus(), field,
                        table.nameJava.toLowerCase() + "." + field.getJavaName().toLowerCase());
                llista.add(tra);
            }
        }
        return llista;
    }

    @Override
    public void save(List<ITraduccioItem> list, Map<Integer, ITraduccioItem> traduccionsPerHashDeClau)
            throws Exception {

        Set<String> languages = new HashSet<String>();

        for (ITraduccioItem tra : list) {
            languages.addAll(tra.getLanguages());
        }
        
        List<String> idiomesOrdenats = new ArrayList<String>(languages);
        Collections.sort(idiomesOrdenats);

        SharedData.data.setLanguages(idiomesOrdenats.toArray(new String[idiomesOrdenats.size()]));

        // GUARDAR  !!!!
        try {
            RebApp.saveProjectToFileJson(SharedData.projectFile, SharedData.data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error guardant fitxer: " + e.getMessage(), "Error",
                    JOptionPane.WARNING_MESSAGE);

        }

    }

}
