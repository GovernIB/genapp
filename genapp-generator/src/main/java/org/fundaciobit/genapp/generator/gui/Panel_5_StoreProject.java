package org.fundaciobit.genapp.generator.gui;

import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import org.apache.log4j.Logger;
import org.fundaciobit.genapp.Project;
import org.fundaciobit.genapp.TableInfo;
import org.fundaciobit.genapp.generator.ProjectValidator;
import org.fundaciobit.genapp.generator.gui.SharedData.ProjectType;

/**
 * Title: Rapit Entity Bean 2010 Description: Copyright: Copyright (c) 2014
 * Company: XmasSoft
 * 
 * @author anadal
 */

public class Panel_5_StoreProject extends Paneable {

    private final Logger log = Logger.getLogger(getClass().getSimpleName());

    BorderLayout borderLayout1 = new BorderLayout();
    JLabel etiFinal = new JLabel();

    public Panel_5_StoreProject() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void jbInit() throws Exception {
        etiFinal.setFont(new java.awt.Font("Dialog", 1, 18));
        etiFinal.setHorizontalAlignment(SwingConstants.CENTER);
        etiFinal.setText("Final. Push button <<Final>> to store project.");
        this.setBackground(UIManager.getColor("List.selectionBackground"));
        this.setLayout(borderLayout1);
        this.add(etiFinal, BorderLayout.CENTER);
    }

    public boolean next() {
        return (false);
    }

    /** Indica si desde el siguienete panel podemos volver otra vez a este */
    public boolean previus() {
        return (true);
    }

    public boolean isFinalizable() {
        return (true);
    }

    public boolean initPanel() {
        return (true);
    }

    public boolean finish() {

        if (SharedData.project == ProjectType.NEW) {

            JFileChooser fileChooser = new JFileChooser();

            fileChooser.setSelectedFile(new File(".\\" + SharedData.data.projectName + ".genappjson"));

            if (fileChooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
                return true;
            }

            SharedData.projectFile = fileChooser.getSelectedFile();

            RebApp.saveLastFile(SharedData.projectFile);
            RebApp.saveLastConfig();
        }

        Project project = SharedData.data;

        // Ordenar per nom java de les taules
        Arrays.sort(SharedData.data.tables, new Comparator<TableInfo>() {
            @Override
            public int compare(TableInfo o1, TableInfo o2) {
                // TODO Auto-generated method stub
                return o1.nameJava.compareToIgnoreCase(o2.nameJava);
            }
        });

        log.info("Fent Check de tot el projecte ...");
        long now = System.currentTimeMillis();

        // TODO Comprovar javaName duplicats o shortNames duplicats
        StringBuffer allErrors;
        try {
            allErrors = ProjectValidator.checkAll(project);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error desconegut validant projecte: " + e.getMessage());
            return false;
        }

        log.info("Executing Checks in " + (System.currentTimeMillis() - now) + "ms");

        if (allErrors.length() != 0) {
            log.error("\n\n =============  ERRORS ============ \n" + allErrors.toString() + "\n\n");
            JTextArea ta = new JTextArea(allErrors.toString());
            ta.setMaximumSize(new Dimension(800, 400));
            ta.setSize(new Dimension(800, 400));
            JScrollPane sp = new JScrollPane(ta);
            sp.setMaximumSize(new Dimension(800, 400));
            sp.setSize(new Dimension(800, 400));

            dialog = new JDialog();
            dialog.setTitle("Errors en el Model i dades del projecte");
            dialog.setLayout(new BorderLayout());
            dialog.add(sp, BorderLayout.CENTER);
            JButton botook = new JButton("OK");
            botook.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    Panel_5_StoreProject.this.dialog.setVisible(false);
                }
            });
            dialog.add(botook, BorderLayout.SOUTH);
            dialog.setMaximumSize(new Dimension(800, 400));
            dialog.pack();
            dialog.setSize(new Dimension(800, 400));

            final Toolkit toolkit = Toolkit.getDefaultToolkit();
            final Dimension screenSize = toolkit.getScreenSize();
            final int x = (screenSize.width - dialog.getWidth()) / 2;
            final int y = (screenSize.height - dialog.getHeight()) / 2;
            dialog.setLocation(x, y);

            dialog.setModalityType(ModalityType.DOCUMENT_MODAL);
            dialog.setVisible(true);

        }

        now = System.currentTimeMillis();

        try {

            File file = SharedData.projectFile;

            RebApp.saveProjectToFileJson(file, project);

            log.info("Saving File in " + (System.currentTimeMillis() - now) + "ms");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }

    protected static JDialog dialog;

}