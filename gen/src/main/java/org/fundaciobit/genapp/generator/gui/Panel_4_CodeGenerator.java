package org.fundaciobit.genapp.generator.gui;

import java.awt.*;
import javax.swing.*;

import org.apache.log4j.Logger;

import java.awt.event.*;

/**
 * Title:        Rapit Entity Bean 2010
 * Description:
 * Copyright:    Copyright (c) 2014
 * Company:      XmasSoft
 * @author anadal
 * @version 1.0
 */

public class Panel_4_CodeGenerator extends Paneable {
  
  private final Logger log = Logger.getLogger(getClass());
  
	/**
   * 
   */
  private static final long serialVersionUID = -1173576570920438559L;



  BorderLayout borderLayout1 = new BorderLayout();


	
	JPanel jPanel1 = new JPanel();
	JLabel etiTitulo = new JLabel();
	BorderLayout borderLayout2 = new BorderLayout();
	JButton saveButton = new JButton();
	JTabbedPane pipelles = new JTabbedPane();
	JPanel pipellaManager = new JPanel();
	JScrollPane jScrollPane1 = new JScrollPane();
	JTextArea codeManager = new JTextArea();
	BorderLayout borderLayout3 = new BorderLayout();
	JPanel pipellaInterface = new JPanel();
	JPanel pipellaReadDB = new JPanel();
	JPanel pipellaNoReadDB = new JPanel();
	JScrollPane jScrollPane2 = new JScrollPane();
	BorderLayout borderLayout4 = new BorderLayout();
	JScrollPane jScrollPane3 = new JScrollPane();
	JScrollPane jScrollPane4 = new JScrollPane();
	BorderLayout borderLayout5 = new BorderLayout();
	BorderLayout borderLayout6 = new BorderLayout();
	JTextArea codeReadBD = new JTextArea();
	JTextArea codeNoReadDB = new JTextArea();
	JTextArea codeInterface = new JTextArea();

	public Panel_4_CodeGenerator() {

		//this.connection = SharedData.data.databaseinfo.getConnection();
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	void jbInit() throws Exception {
		this.setLayout(borderLayout1);
		etiTitulo.setText("Codigo generado:");
		jPanel1.setLayout(borderLayout2);
		saveButton.setToolTipText("");
		saveButton.setText("Guardar");
		saveButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveButton_actionPerformed(e);
			}
		});
		pipellaManager.setLayout(borderLayout3);
		pipellaInterface.setLayout(borderLayout4);
		pipellaNoReadDB.setLayout(borderLayout5);
		pipellaReadDB.setLayout(borderLayout6);
		this.add(jPanel1, BorderLayout.NORTH);
		jPanel1.add(etiTitulo, BorderLayout.CENTER);
		jPanel1.add(saveButton, BorderLayout.EAST);
		this.add(pipelles, BorderLayout.CENTER);
		pipelles.add(pipellaManager, "Manager");
		pipellaManager.add(jScrollPane1, BorderLayout.CENTER);
		pipelles.add(pipellaInterface, "Interface");
		pipellaInterface.add(jScrollPane2, BorderLayout.CENTER);
		jScrollPane2.getViewport().add(codeInterface, null);
		pipelles.add(pipellaReadDB, "Read BD");
		pipellaReadDB.add(jScrollPane3, BorderLayout.CENTER);
		jScrollPane3.getViewport().add(codeReadBD, null);
		pipelles.add(pipellaNoReadDB, "No read DB");
		pipellaNoReadDB.add(jScrollPane4, BorderLayout.CENTER);
		jScrollPane4.getViewport().add(codeNoReadDB, null);
		jScrollPane1.getViewport().add(codeManager, null);
	}

	public boolean initPanel() {
		return (true);
	}
	


	/** Indica si desde el siguienete panel podemos volver otra vez a este */
	public boolean previus() {
		return (true);
	}

	public boolean next() {
		return (true);
	}

	public boolean isFinalizable() {
		return (false);
	}

	void saveButton_actionPerformed(ActionEvent e)
  {
    javax.swing.JFileChooser fc=new javax.swing.JFileChooser();
    if(fc.showSaveDialog(this)==JFileChooser.APPROVE_OPTION) {
        log.info("Name: "+fc.getSelectedFile().getAbsolutePath());
    }
  }

}