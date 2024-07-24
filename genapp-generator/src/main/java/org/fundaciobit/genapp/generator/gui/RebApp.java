package org.fundaciobit.genapp.generator.gui;

import java.awt.*;
import java.awt.event.*;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Modifier;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.fundaciobit.genapp.FieldInfo;
import org.fundaciobit.genapp.Project;
import org.fundaciobit.genapp.generator.CodeGenerator;
import org.fundaciobit.genapp.generator.SQL2Java;
import org.fundaciobit.genapp.generator.gui.SharedData.ProjectType;
import org.fundaciobit.genapp.traductor.Traductor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * Title: Rapit Entity Bean 2010 Description: Copyright: Copyright (c) 2014
 * Company: XmasSoft
 * 
 * @author anadal
 */

public class RebApp extends JFrame {

    private static final Logger log = Logger.getLogger(RebApp.class.getSimpleName());

    /** IU */
    JPanel contentPane;
    BorderLayout borderLayout1 = new BorderLayout();
    JPanel vista = new JPanel();
    JPanel cursor = new JPanel();
    JButton next = new JButton();
    JButton previus = new JButton();
    CardLayout card = new CardLayout();
    JButton finish = new JButton();
    JButton cancel = new JButton();

    Panel_1_SelectDB selectDB = new Panel_1_SelectDB();
    // Panel_2_TableList selectTable = new Panel_2_TableList(selectDB);
    Panel_2_TableFields columnList = new Panel_2_TableFields(this);

    // Panel_5_CodeGenerator codeGenerator = new
    // Panel_5_CodeGenerator(columnList,connectionManager);
    Panel_5_StoreProject finalPan = new Panel_5_StoreProject();

    Paneable pannels[];
    String cardNames[];
    int pos = 0;

    public static final String LAST_FILE = "lastFile";

    public static final String LAST_GEN_DIR = "lastGenenationDir";

    public static final File lastConfigFile = new File("lastconfig.properties");
    public static final Properties lastConfig = new Properties();

    protected static File dstDir = null;

    static {
        if (lastConfigFile.exists()) {
            try {
                lastConfig.load(new FileInputStream(lastConfigFile));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static File getDirectoryOfLastFile() {
        if (lastConfig != null && lastConfig.getProperty(LAST_FILE) != null) {
            File f = new File(lastConfig.getProperty(LAST_FILE));
            if (f.exists()) {
                return f.getParentFile();
            } else {
                if (f.getParentFile().exists()) {
                    return f.getParentFile();
                }
            }
        }
        return new File(".");
    }

    public static void saveLastFile(File selectedFile) {
        lastConfig.setProperty(LAST_FILE, selectedFile.getAbsolutePath());
    }

    @SuppressWarnings("deprecation")
    public static void saveLastConfig() {
        try {
            lastConfig.save(new FileOutputStream(lastConfigFile), "GenApp File");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void saveDirectoryOfLastGeneration(File selectedDir) {
        lastConfig.setProperty(LAST_GEN_DIR, selectedDir.getAbsolutePath());
    }

    public static File getDirectoryOfLastGeneration() {
        if (lastConfig != null && lastConfig.getProperty(LAST_GEN_DIR) != null) {
            File f = new File(lastConfig.getProperty(LAST_GEN_DIR));
            if (f.exists()) {
                return f;
            }
        }
        File defaultDir = getDirectoryOfLastFile();
        if (defaultDir != null && defaultDir.exists()) {
            return defaultDir;
        } else {
            return new File(".");
        }
    }

    /** Construct the frame */
    public RebApp() {
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        try {

            if (SharedData.project == ProjectType.GENERATE) {

                boolean showMessage = false;
                if (dstDir == null) {

                    showMessage = true;

                    JFileChooser dstDirDlg = new JFileChooser(getDirectoryOfLastGeneration());

                    dstDirDlg.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                    dstDirDlg.setDialogTitle("Seleccioni el directori on generar el codi del projecte.");

                    // opens dialog if button clicked
                    if (dstDirDlg.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

                        dstDir = dstDirDlg.getSelectedFile();
                    }
                }

                if (dstDir != null) {
                    try {
                        File project = CodeGenerator.generateCode(SharedData.data, dstDir);

                        String msg = "Generat el codi dins " + project.getAbsolutePath();

                        if (showMessage) {
                            JOptionPane.showMessageDialog(null, msg, "Info", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            System.out.println();
                            System.out.println(msg);
                            System.out.println();
                        }

                        saveDirectoryOfLastGeneration(dstDir);
                        saveLastConfig();

                        this.setVisible(false);
                        System.exit(0);
                    } catch (Throwable th) {
                        th.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error generant el codi: " + th.getMessage(), "Error",
                                JOptionPane.ERROR_MESSAGE);
                        this.setVisible(false);
                        System.exit(1);
                    }
                }

            } else {

                vista.setLayout(card);
                vista.setToolTipText("");

                switch (SharedData.project) {

                    case OPEN:
                        // if (SharedData.project == ProjectType.LOAD) {
                        pannels = new Paneable[] { columnList, finalPan };
                        cardNames = new String[] { "ColumnList", "Final" };
                        columnList.initPanel();
                        saveLastConfig();
                    break;

                    case UPDATE:
                        saveLastConfig();
                    default:
                    case NEW:
                        pannels = new Paneable[] { selectDB, columnList, finalPan };
                        cardNames = new String[] { "SelectDB", "ColumnList", "Final" };
                        vista.add(selectDB, "SelectDB");
                        selectDB.initPanel();
                    break;
                }

                vista.add(columnList, "ColumnList");
                vista.add(finalPan, "Final");

                cursor.add(cancel, null);
                cursor.add(previus, null);
                cursor.add(next, null);
                cursor.add(finish, null);

                contentPane = (JPanel) this.getContentPane();
                contentPane.setLayout(borderLayout1);
                this.setSize(new Dimension(1024, 800));
                this.setLocation(100, 100);
                this.setTitle(getGenAppTitle());
                next.setText("Next >>");
                next.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        next_actionPerformed();
                    }
                });
                previus.setText("<< Back");
                previus.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        previus_actionPerformed(e);
                    }
                });

                finish.setEnabled(false);
                finish.setText("Finalizar");
                finish.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        finish_actionPerformed(e);
                    }
                });

                cancel.setText("Cancelar");
                cancel.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        cancel_actionPerformed(e);
                    }
                });
                contentPane.setMinimumSize(new Dimension(512, 354));
                contentPane.setPreferredSize(new Dimension(555, 400));
                contentPane.add(vista, BorderLayout.CENTER);

                contentPane.add(cursor, BorderLayout.SOUTH);

                log.info(" CARD NAME: " + cardNames[pos]);

                card.show(vista, cardNames[pos]);

                initButtonStatus(pos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /** Overridden so we can exit when window is closed */
    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {

        if (!"UTF-8".equalsIgnoreCase(System.getProperty("file.encoding"))) {
            System.err.println("\n\nfile.encoding=" + System.getProperty("file.encoding"));
            System.err.println(
                    "Editi el genapp.bat/.sh i abans de la cridada a 'mvn exec:java' inclogui el següent codi:\n"
                            + " + Windows: set MAVEN_OPTS=-Dfile.encoding=UTF-8\n"
                            + " + Linux: export MAVEN_OPTS=-Dfile.encoding=UTF-8\n\n");
            System.exit(-1);
        }

        /*
        Properties prop = System.getProperties();
        
        for (Object key: prop.keySet()) {
            System.out.println(key + ": " + prop.getProperty(key.toString()));
        }
        */

        try {

            int n = -2;
            File selectedFile = null;

            if (args == null || args.length == 0 || args.length > 2) {

            } else {

                selectedFile = new File(args[0]);

                if (selectedFile.exists()) {

                    if (args.length == 2) {
                        // Generar Codi Font
                        n = 3;
                        dstDir = new File(args[1]);

                        dstDir.mkdirs();

                        if (!dstDir.exists()) {
                            dstDir = null;
                        }
                    }

                    if (dstDir == null) {
                        // Actualitzar BBDD
                        n = 1;
                    }
                }

            }

            if (n == -2) {

                /*  == 0 */
                JButton nouProjecte = new JButton("Nou projecte i SQL generació de taules base");
                nouProjecte.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        Window w = SwingUtilities.getWindowAncestor(nouProjecte);
                        w.setVisible(false);
                        try {
                            final String nomCamp = "Prefix";
                            final Integer expectedSize = 3;
                            String prefix = readField(nomCamp, "pfx", expectedSize);
                            prefix = prefix.toLowerCase();

                            final String nomCamp2 = "Nom Projecte";
                            final Integer expectedSize2 = null;
                            String nomAppUp = readField(nomCamp2, "NomApp", expectedSize2);

                            String nomApp = nomAppUp.toLowerCase();

                            final String[][] sourceDest = {
                                    { "genapp_required_tables.sql", prefix + "_" + nomApp + "_SCHEMA_BASE.sql" },
                                    { "template.genappjson", nomAppUp + ".genappjson" } };

                            File parent = null;

                            for (int j = 0; j < sourceDest.length; j++) {

                                String src = sourceDest[j][0];
                                String dest = sourceDest[j][1];

                                ClassLoader classLoader = RebApp.class.getClassLoader();
                                InputStream is = classLoader.getResourceAsStream(src);

                                String content = IOUtils.toString(is, "UTF-8");

                                content = content.replace("PFX", prefix);

                                content = content.replace("APPNAMEUP", nomApp);

                                content = content.replace("APPNAME", nomApp);

                                File f = new File(dest);
                                File tmp = new File(f.getCanonicalPath());
                                System.out.println("tmp = " + tmp.getAbsolutePath());
                                parent = tmp.getParentFile();
                                System.out.println("PARENT = " + parent.getAbsolutePath());

                                FileUtils.write(f, content, "UTF8");
                            }

                            JOptionPane.showMessageDialog(null, "Fitxer de SQL i plantilla GenApp creats a: "
                                    + parent.getAbsolutePath() + "\n Ara ha de fer el següent:\n"
                                    + "(1) Crear una BBDD a partir del fitxer SQL generat (" + sourceDest[0][1] + ")\n"
                                    + "(2) Executar genapp de nou i pitjar sobre el boto d'Actualitzar Projecte\n"
                                    + "(3) Elegir el fitxer " + sourceDest[1][1] + "\n"
                                    + "(4) Actualitzar els camps de connexio amb la BBBD i d'altres", "Info",
                                    JOptionPane.INFORMATION_MESSAGE);

                            System.exit(0);

                        } catch (Exception eee) {
                            // TODO Mostrar en un dialeg
                            eee.printStackTrace();
                            System.exit(-1);
                        }
                    }
                });

                /*  == 1 */
                JButton actualitzarProjecte = new JButton("Actualitzar Projecte (Canvis BBDD)");
                actualitzarProjecte.addActionListener(
                        new ActionListenerForUpdateOpenGenerate(1, selectedFile, actualitzarProjecte));
                /*  == 2 */
                JButton editarProjecte = new JButton("Editar Projecte");
                editarProjecte
                        .addActionListener(new ActionListenerForUpdateOpenGenerate(2, selectedFile, editarProjecte));
                /*  == 3 */
                JButton generarCodiFont = new JButton("Generar Codi Font");
                generarCodiFont
                        .addActionListener(new ActionListenerForUpdateOpenGenerate(3, selectedFile, generarCodiFont));

                /*  == 4 */
                JButton traductor = new JButton("Traduccions");
                traductor.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        Window w = SwingUtilities.getWindowAncestor(traductor);
                        w.setVisible(false);
                        Traductor.main(null);
                    }
                });

                final Object[] options = { nouProjecte, /*  == 0 */
                        actualitzarProjecte, /*  == 1 */
                        editarProjecte, /*  == 2 */
                        generarCodiFont, /*  == 3 */
                        traductor /*  == 4 */
                };
                Icon icon = new ImageIcon(RebApp.class.getResource("/genapp_icon.png"));
                //n = JOptionPane.showOptionDialog(null, "Seleccioni una opció ", getGenAppTitle(),
                //        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                n = JOptionPane.showOptionDialog(null, options, getGenAppTitle(), JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE, icon, new String[] { "Sortir" }, null);
            }

            log.info(" NUMERO -> " + n);

            if (n == 0) {
                log.info(" SORTIM !!!!! ");
                System.exit(0);
            }

        } catch (Exception e) {
            // TODO Mostrar en un dialeg
            e.printStackTrace();
            System.exit(-1);
        }

    }

    public static class ActionListenerForUpdateOpenGenerate implements java.awt.event.ActionListener {

        public final int nn;

        public File selectedFile;

        public final JButton button;

        public ActionListenerForUpdateOpenGenerate(int nn, File selectedFile, JButton button) {
            this.nn = nn;
            this.selectedFile = selectedFile;
            this.button = button;
        }

        public void actionPerformed(ActionEvent e) {
            Window w = SwingUtilities.getWindowAncestor(this.button);
            w.setVisible(false);
            if (selectedFile == null) {
                // new dialog
                JFileChooser loadEmp = new JFileChooser(getDirectoryOfLastFile());

                FileNameExtensionFilter filterm3u = new FileNameExtensionFilter("GenApp file (.genappjson)",
                        "genappjson");
                loadEmp.addChoosableFileFilter(filterm3u);
                loadEmp.setFileFilter(filterm3u);

                loadEmp.setDialogTitle("Seleccioni un arxiu .genappjson");

                // opens dialog if button clicked
                if (loadEmp.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    // gets file from dialog
                    selectedFile = loadEmp.getSelectedFile();
                }
            }
            if (selectedFile != null) {

                Project project;
                try {
                    project = readProjectFromFile(selectedFile);

                    saveLastFile(selectedFile);

                    // XXX
                    if (project.getLanguages() == null) {
                        project.setLanguages(new String[] { "ca", "es" });
                    }

                    SharedData.projectFile = selectedFile;
                    if (nn == 1) {
                        SharedData.project = ProjectType.UPDATE;
                    } else {
                        SharedData.project = ((nn == 2) ? ProjectType.OPEN : ProjectType.GENERATE);

                        cleanProject(project);

                    }
                    SharedData.data = project;
                    (new RebApp()).setVisible(true);
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                    System.exit(-1);
                }
            }
        }
    };

    public static String getGenAppTitle() {
        return "GenApp v2 2015-" + Calendar.getInstance().get(Calendar.YEAR);
    }

    public static Project readProjectFromFile(File selectedFile) throws Exception {

        Project project;
        if (selectedFile.getName().endsWith(".genapp")) {
            FileInputStream fis = new FileInputStream(selectedFile);
            XMLDecoder dec = new XMLDecoder(fis);
            project = (Project) dec.readObject();
            dec.close();

        } else {
            Gson gson = RebApp.instantiateGSon();
            FileReader fr = new FileReader(selectedFile);
            project = gson.fromJson(fr, Project.class);
            fr.close();
        }

        checkGenerateWs(project);

        return project;
    }

    @SuppressWarnings("deprecation")
    protected static void checkGenerateWs(Project project) {
        Boolean generateWs = project.isGenerateWS();
        if (generateWs != null) {
            project.setGenerateApiExterna(generateWs);
            project.setGenerateApiInterna(generateWs);
        }
        project.setGenerateWS(null);
    }

    @Deprecated
    public static void saveProjectToFileXml(File file, Project project) throws Exception {

        FileOutputStream os = new FileOutputStream(file);
        XMLEncoder encoder = new XMLEncoder(os);
        encoder.writeObject(project);
        encoder.flush();
        encoder.close();

    }

    public static void saveProjectToFileJson(File file, Project project) throws Exception {

        final File f;

        if (file.getName().endsWith(".genapp")) {
            f = new File(file.getParentFile(), file.getName() + "json");
        } else {
            f = file;
        }

        Gson gson = instantiateGSon();

        String dest;
        {
            dest = gson.toJson(project);
            FileWriter gdest = new FileWriter(f);
            gdest.write(dest);
            gdest.close();
        }

    }

    public static Gson instantiateGSon() {
        GsonBuilder builder = new GsonBuilder();

        // builder.addDeserializationExclusionStrategy(new
        // SuperclassExclusionStrategy());
        // builder.addSerializationExclusionStrategy(new SuperclassExclusionStrategy());
        builder.excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC);
        builder.setPrettyPrinting();

        java.lang.reflect.Type typeToken = new TypeToken<Class<?>>() {
        }.getType();

        builder.registerTypeAdapter(typeToken, new ClassTypeAdapter());

        Gson gson = builder.create();
        return gson;
    }

    public static class ClassTypeAdapter extends TypeAdapter<Class<?>> {

        @Override
        public void write(JsonWriter out, Class<?> value) throws IOException {

            if (value == null) {
                out.nullValue();
            } else {
                out.value(value.getName());
            }

        }

        @Override
        public Class<?> read(JsonReader in) throws IOException {
            String name = in.nextString();

            Class<?> classe = SQL2Java.STRINGTOCLASS.get(name);

            if (classe == null) {
                System.err.println(" ================= ");
                System.err.println(" String => " + name);
                System.err.println(" Class<?> => " + classe);
                throw new IOException("No es troba classe per la cadena ]" + name
                        + "[. Revisar el map STRINGTOCLASS de " + SQL2Java.class.getName());
            }

            return classe;
        }

    }

    public static void cleanProject(Project project) {
        for (int i = 0; i < project.tables.length; i++) {

            if (project.tables[i].getNameJava() == null) {
                project.tables[i].setNameJava(project.tables[i].getName());
            }
            String[] idiomes = project.getLanguages();
            Map<String, String> labelsTaula = project.tables[i].getLabels();
            if (labelsTaula == null || labelsTaula.size() == 0) {
                labelsTaula = new HashMap<String, String>();
                for (String lang : idiomes) {
                    labelsTaula.put(lang, project.tables[i].getName());
                }
                project.tables[i].setLabels(labelsTaula);
            }

            log.info(project.tables[i].getNameJava() + "\t" + project.tables[i].getName());

            for (FieldInfo fi : project.tables[i].fields) {
                Map<String, String> labels = fi.getLabels();
                for (String lang : idiomes) {
                    if (labels.get(lang) == null) {
                        labels.put(lang, fi.getJavaName() + "_XX_" + lang);
                    }
                }
            }
            /*
             * for(int f=0;f< project.tables[i].fields.length; f++) { if
             * (project.tables[i].fields[f].getLabel() == null) {
             * project.tables[i].fields[f].setLabel(project.tables[i].fields[f].getJavaName(
             * )); }
             * 
             * }
             */

        }
    }

    public static String readField(String nomCamp, String sample, Integer expectedSize) {
        String prefix;
        do {
            prefix = (String) JOptionPane.showInputDialog(null, nomCamp + " de l'aplicació:\n",
                    nomCamp + " de l'aplicació", JOptionPane.PLAIN_MESSAGE, null, null, sample);

            if (prefix == null) {
                System.exit(0);
            }
            if ((expectedSize == null && prefix.trim().length() == 0)
                    || (expectedSize != null && prefix.trim().length() != expectedSize)) {

                String msg;
                if (expectedSize == null) {
                    msg = "El " + nomCamp + " no ha d'estar buit";
                } else {
                    msg = "El " + nomCamp + " ha d'estar format per una cadema de tamany " + expectedSize;
                }
                JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                return prefix;
            }

        } while (true);
    }

    /**
     * 
     */
    public void next_actionPerformed() {

        if (this.pannels[pos].next() == false) {
            return;
        }

        if ((pos + 1) == cardNames.length) {
            return;
        }

        if (this.pannels[pos + 1].initPanel() == false) {
            return;
        }

        pos++;

        initButtonStatus(pos);

        this.card.show(vista, cardNames[pos]);
    }

    /**
     * 
     * @param e
     */
    void previus_actionPerformed(ActionEvent e) {

        if (pannels[pos].previus()) {
            pos--;

            initButtonStatus(pos);

            this.card.show(vista, cardNames[pos]);
        }
    }

    void cancel_actionPerformed(ActionEvent e) {
        System.exit(0);
    }

    void finish_actionPerformed(ActionEvent e) {
        if (pannels[pos].finish()) {
            System.exit(0);
        }
    }

    void initButtonStatus(int pos) {

        log.info("INIT BUTTON STATUS: " + pannels[pos].isFinalizable());

        if (pannels[pos].isFinalizable()) {
            this.finish.setEnabled(true);
            this.finish.setVisible(true);
        } else {
            this.finish.setEnabled(false);
            this.finish.setVisible(false);
        }

        /* if(pannels[pos].next()) */
        if ((pos + 1) == cardNames.length)
            this.next.setEnabled(false);
        else
            this.next.setEnabled(true);
    }

}