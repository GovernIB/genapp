package org.fundaciobit.genapp.traductor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.fundaciobit.genapp.Project;
import org.fundaciobit.genapp.generator.gui.RebApp;
import org.fundaciobit.genapp.generator.gui.SharedData;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 
 * @author anadal
 *
 */
public class Traductor extends JTable {

    public final ITraduccioTipus[] traduccioTipusList;

    

    final Map<Integer, ITraduccioItem> traduccionsPerHashDeClau = new HashMap<Integer, ITraduccioItem>();
    
    final File projectDir;

    public Traductor(File projectDir, String prefix) throws Exception {
        super();
        
        
        this.projectDir = projectDir;
        
        System.out.println(
                "Traductor():: Inicialitzant Traductor[ this.projectDir=" + this.projectDir + " ]");
        
        
        {
            
        }
        
        
        
        traduccioTipusList = new ITraduccioTipus[] {
                new TraduccioTipusGenApp(this.projectDir),
                new TraduccioTipusBundleFile(this.projectDir,
                        prefix + "ejb/src/main/resources/genapp"),
                new TraduccioTipusBundleFile(this.projectDir,
                        prefix + "ejb/src/main/resources/logicmissatges"),
                new TraduccioTipusBundleFile(this.projectDir,
                        prefix + "back/src/main/resources/missatges")
        };
                

        final List<ITraduccioItem> llista = new ArrayList<ITraduccioItem>();

        for (ITraduccioTipus tipus : traduccioTipusList) {
            llista.addAll(tipus.read());
        }

        Set<String> clausDuplicades = new HashSet<String>();

        for (ITraduccioItem tra : llista) {
            if (traduccionsPerHashDeClau.containsKey(tra.getKey().hashCode())) {
                ITraduccioItem item = traduccionsPerHashDeClau.get(tra.getKey().hashCode());
                
                System.err.println("CLAU '" + tra.getKey() + "' esta duplicada:\n  + " + tra.getType() + "\n  + " + item.getType() + "\n");
                clausDuplicades.add(tra.getKey());
            } else {
                traduccionsPerHashDeClau.put(tra.getKey().hashCode(), tra);
            }
        }

        Collections.sort(llista, new Comparator<ITraduccioItem>() {
            @Override
            public int compare(ITraduccioItem tra2, ITraduccioItem tra1) {
                return tra2.getKey().compareTo(tra1.getKey());
            }
        });

        TraduccioItemTableModel dataModel = new TraduccioItemTableModel(
                llista.toArray(new ITraduccioItem[llista.size()]), traduccionsPerHashDeClau);

        setModel(dataModel);
        this.setDefaultRenderer(String.class, new TraduccioItemRenderer(clausDuplicades));
        setAutoResizeMode(AUTO_RESIZE_ALL_COLUMNS);
    }

    public static void main(String[] args) {

        try {

            // String r= callUrlAndParseResult("ca", "eu", "Bon dia");
            //
            // System.out.println("Resultat => " + r);
            //
            // if (true) return;
            //

            // XYZ ZZZ
            JFileChooser loadEmp = new JFileChooser(RebApp.getDirectoryOfLastFile());

            FileNameExtensionFilter filterm3u = new FileNameExtensionFilter("GenApp file (.genappjson)", "genappjson");
            loadEmp.addChoosableFileFilter(filterm3u);
            loadEmp.setFileFilter(filterm3u);

            loadEmp.setDialogTitle("Seleccioni un arxiu .genappjson");
            File selectedFile;

            // opens dialog if button clicked
            if (loadEmp.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                // gets file from dialog
                selectedFile = loadEmp.getSelectedFile();

                final Project project = RebApp.readProjectFromFile(selectedFile);

                RebApp.saveLastFile(selectedFile);
                RebApp.saveLastConfig();

                RebApp.cleanProject(project);

                SharedData.projectFile = selectedFile;

                SharedData.data = project;
                
                final String prefix = project.getPrefixDirectori();
                
                File projectDir = selectedFile.getParentFile();
                
                // Mirar si hi ha un projecte 
                 
                if (!checkIfFileIsProjectDir( projectDir, prefix)) {
                    projectDir = projectDir.getParentFile();
                    if (!checkIfFileIsProjectDir( projectDir, prefix)) {
                        JFileChooser dstDirDlg = new JFileChooser(RebApp.getDirectoryOfLastGeneration());

                        dstDirDlg.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                        dstDirDlg.setDialogTitle("Seleccioni el directori del projecte ...");

                        // opens dialog if button clicked
                        if (dstDirDlg.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

                            projectDir = dstDirDlg.getSelectedFile();
                        } else {
                            System.exit(-1);
                        }
                    }
                }
                
                
                final File projectDirFinal = projectDir;

                // Schedule a job for the event-dispatching thread:
                // creating and showing this application's GUI.
                javax.swing.SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        // Create and set up the window.
                        JFrame frame = new JFrame("Gestió Traduccions GenApp");
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                        // Create and set up the content pane.
                        final Traductor taulaDeTraduccions;
                        try {
                            taulaDeTraduccions = new Traductor(projectDirFinal, prefix);
                        } catch (Exception e) {
                            // TODO: handle exception
                            e.printStackTrace();
                            return;
                        }

                        taulaDeTraduccions.setOpaque(true); // content panes must be opaque
                        // frame.setContentPane(newContentPane);

                        // Create the scroll pane and add the table to it.
                        JScrollPane scrollPane = new JScrollPane(taulaDeTraduccions);

                        // Add the scroll pane to this panel.
                        // scrollPane.add(newContentPane);

                        // frame.setContentPane(scrollPane);

                        frame.setLayout(new BorderLayout());

                        frame.add(scrollPane, BorderLayout.CENTER);

                        JPanel top = new JPanel();

                        // JButton afegirIdioma = new JButton("Afegir Idioma");
                        // afegirIdioma.addActionListener(taulaDeTraduccions);

                        JButton guardar = new JButton("Guardar");
                        guardar.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent e) {
                                try {
                                    taulaDeTraduccions.guardar();
                                } catch (Exception e1) {
                                    // TODO Auto-generated catch block
                                    e1.printStackTrace();
                                }
                            }
                        });

                        JButton exportar = new JButton("Exportar");
                        exportar.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent e) {
                                taulaDeTraduccions.exporta();

                            }
                        });

                        JButton importar = new JButton("Importar");
                        importar.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent e) {
                                taulaDeTraduccions.importa();
                            }
                        });

                        top.add(exportar);
                        top.add(importar);
                        top.add(guardar);

                        frame.add(top, BorderLayout.NORTH);

                        // Display the window.
                        frame.pack();
                        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
                        frame.setVisible(true);
                    }
                });

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected static boolean checkIfFileIsProjectDir(File projectDir, final String prefix) {
        File file = new File(projectDir, prefix + "ejb");
        System.out.println("checkIfFileIsProjectDir():: Comprovant " + file.getAbsolutePath());
        return file.exists() && file.isDirectory(); 
    }

    public void guardar() throws Exception {
        System.out.println("guardar():: Entra a guardar traduccions");

        for (ITraduccioTipus tipus : traduccioTipusList) {
            System.out.println("guardar():: Guardant traduccions de " + tipus.getNom());
            final List<ITraduccioItem> llista = new ArrayList<ITraduccioItem>(this.traduccionsPerHashDeClau.values());
            tipus.save(llista, this.traduccionsPerHashDeClau);
        }
        
        JOptionPane.showMessageDialog(this, "Canvis guardats correctament.\n Es recomana fer Winmerge per ordenar les entrades en els diferents idiomes");

    }

    public void exporta() {

        try {

            JComboBox<String> optionList = new JComboBox<String>(SharedData.data.getLanguages());
            // optionList.setSelectedIndex(7);
            JOptionPane.showMessageDialog(this, optionList, "Seleccioni Idioma a Exportar",
                    JOptionPane.QUESTION_MESSAGE);

            String from = (String) optionList.getSelectedItem();

            int count = 1;
            JFileChooser fileChooser = new JFileChooser();

            fileChooser.setSelectedFile(new File("traduccions_" + from + "_" + count + ".txt"));

            fileChooser.setDialogTitle("Nom del fitxer exportat per idioma '" + from + "'");

            int userSelection = fileChooser.showSaveDialog(this);

            if (userSelection != JFileChooser.APPROVE_OPTION) {
                return;
            }

            File fileToSave = fileChooser.getSelectedFile();
            System.out.println("Save as file: " + fileToSave.getAbsolutePath());

            PrintWriter out = new PrintWriter(fileToSave);

            int size = 0;
            
            final List<ITraduccioItem> llista = new ArrayList<ITraduccioItem>(this.traduccionsPerHashDeClau.values());

            for (ITraduccioItem iTraduccioItem : llista) {
                String key = iTraduccioItem.getKey();
                String fromValue = iTraduccioItem.getStringValue(from);
                if (fromValue == null) {
                    System.err.println("No es troba la traducció per la clau '" + key + "' en l´idioma ]" + from + "[ ("
                            + iTraduccioItem.getType() + ")");
                } else {
                    String text = key.hashCode() + ". " + fromValue;
                    size = size + text.length();
                    out.println(text);
                    System.out.println(key + " => " + text + "  (" + iTraduccioItem.getType() + ")");
                }

                if (size > 35000) {

                    out.flush();

                    out.close();

                    String newName = fileToSave.getName();

                    newName = newName.replace("_" + count + ".", "_" + String.valueOf(count + 1) + ".");

                    count++;

                    fileToSave = new File(fileToSave.getParentFile(), newName);

                    out = new PrintWriter(fileToSave);

                    size = 0;
                }

            }

            Thread.sleep(1000);
            out.flush();

            out.close();

        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

    public void importa() {

        String codi = JOptionPane.showInputDialog(this, "Escriu el codi ISO 639-1 (en,fr,de,es,ca, ...):");

        if (codi == null) {
            return;
        }

        final JFileChooser fc = new JFileChooser();

        int returnVal = fc.showOpenDialog(this);

        if (returnVal != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File file = fc.getSelectedFile();

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));

            String line;
            do {

                line = br.readLine();
                if (line != null) {
                    // Parsejar

                    System.out.println("LINE => ]" + line + "[");

                    int pos = line.indexOf(". ");

                    String numero = line.substring(0, pos).replace(" ", "").replace(".", "");

                    Integer hash = Integer.parseInt(numero);
                    String trans = line.substring(pos + 2);

                    ITraduccioItem tra = traduccionsPerHashDeClau.get(hash);

                    if (tra != null) {
                        tra.setStringValue(codi, trans);
                    } else {
                        System.err.println(" No es troba la clau del HASH " + hash);
                    }

                }

            } while (line != null);

            JOptionPane.showMessageDialog(this,
                    "Per a que els canvis tenguin efecte ha picar sobre 'Guardar' i reiniciar aquesta l´aplicació");

        } catch (Exception ee) {
            ee.printStackTrace();
            return;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        }

    }
    /*
     * @Override public void actionPerformed(ActionEvent e) {
     * 
     * String codi = JOptionPane.showInputDialog(this,
     * "Escriu el codi ISO 639-1 (en,fr,de,es,ca, ...):");
     * 
     * if (codi == null) { return; }
     * 
     * int count = 0;
     * 
     * String from = "ca";
     * 
     * for (ITraduccioItem iTraduccioItem : llista) {
     * 
     * String cast = iTraduccioItem.getStringValue(from);
     * 
     * try { String res = callUrlAndParseResult(from, codi, cast);
     * 
     * System.out.println(from + "[" + cast + "] => " + codi + "[" + res + "]"); } catch
     * (Exception e1) { // TODO Auto-generated catch block e1.printStackTrace(); }
     * 
     * count++; if (count == 50) { return; }
     * 
     * }
     * 
     * }
     */

    /*
     * public String translate(String textToTranslate, String from, String to) throws Exception {
     * if(textToTranslate.isEmpty() || from.isEmpty() || to.isEmpty() ) { throw new
     * IllegalArgumentException("All params must be informed"); }
     * 
     * String result = traduccionsEspecials.get(textToTranslate);
     * 
     * if (textToTranslate.equals("ID")) { System.out.println("ES ID"); } else {
     * System.out.println("no es id"); }
     * 
     * 
     * if (result == null) { result = callUrlAndParseResult(from, to, textToTranslate);
     * //getTranslator().requestTranslation(textToTranslate, from, to); }
     * 
     * return result; }
     */

    public static Map<String, String> traduccionsEspecials = new HashMap<String, String>();

    static {

        traduccionsEspecials.put("ID", "ID");
        traduccionsEspecials.put("mime", "mime");

    }

}
