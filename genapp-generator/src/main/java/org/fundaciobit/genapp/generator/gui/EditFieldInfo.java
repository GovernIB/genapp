package org.fundaciobit.genapp.generator.gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.fundaciobit.genapp.FieldInfo;


/**
 * GenApp
*/
public class EditFieldInfo extends javax.swing.JDialog {
  
  /**
   * 
   */
  private static final long serialVersionUID = 7757800816477474814L;

  private final FieldInfo fieldInfo;
  
  private JPanel totPanell;
  private JPanel campsPanell;
  private JPanel botonsPanell;
  
  private JTextField sqlName;
  private JTextField javaName;
  
  private Map<String, JTextField> labels;
  
  private JTextArea descripcio = null;
  private JTextField defaultValue;
  private JTextField javaType;

  private JTextField isPK;
  private JTextField isUnique;
  private JTextField index;
  private JCheckBox isAutoIncrement;
  private JCheckBox isNotNullable;

  private JCheckBox traduible;
  private JCheckBox showInWebList;
  private JCheckBox showInReferenceList;
  private JCheckBox transientField;

  private JTextField sqlType; // read
  private JTextField size; // read
  private JTextField digits; // read

  //Validació, Valor menor
  private JTextField minAllowedValue;

  //Validació, Valor major
  private JTextField maxAllowedValue;
  
  // Anotacions adicionals
  private JTextArea additonalAnnotations;
  
  // Aplicar filter per aquest camp
  private JCheckBox filter;
  
  // Unic per Grup de camps
  //private JCheckBox uniqueGroup;

  // 0 = No ordernat || >0 = Ordre ascendent || <0 Ordre descendent
  private JComboBox<JComboBoxItem> orderBy;
  
  // Agrupar per aquest camp
  private JCheckBox groupBy;
  
  public boolean clickOK = false;
  
  public String[] idiomes;

  /**
  * Auto-generated main method to display this JDialog
  */
  /*
  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        JFrame frame = new JFrame();
        EditFieldInfo inst = new EditFieldInfo(frame, null);
        inst.setVisible(true);
      }
    });
  }
  */
  
  

  public EditFieldInfo(JFrame frame, FieldInfo fieldInfo, String[] idiomes) {
    super(frame);
    this.setModal(true);
    this.setTitle("Editar camp " + fieldInfo.getJavaName());
    this.fieldInfo = fieldInfo;
    this.idiomes = idiomes;
    
    sqlName= new JTextField(this.fieldInfo.sqlName);
    sqlName.setEditable(false);
    javaName= new JTextField(this.fieldInfo.javaName);
    
    
    labels = new HashMap<String, JTextField>();
    for (String lang : idiomes) {
      labels.put(lang, new JTextField(this.fieldInfo.getLabels().get(lang)));
    }

    descripcio =  new JTextArea(this.fieldInfo.descripcio);
    descripcio.setRows(2);
    descripcio.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    defaultValue =  new JTextField(this.fieldInfo.defaultValue);
    javaType= new JTextField(this.fieldInfo.getJavaType().getCanonicalName());

    isPK = new  JTextField(this.fieldInfo.getPK() == null? "" : this.fieldInfo.getPK());
    isPK.setEditable(false);
    isUnique = new  JTextField(this.fieldInfo.getUnique() == null ? "" : this.fieldInfo.getUnique());
    isUnique.setEditable(false);
    index = new  JTextField(this.fieldInfo.getIndex() == null ? "" : this.fieldInfo.getIndex());
    index.setEditable(false);
    isAutoIncrement = new  JCheckBox("",this.fieldInfo.isAutoIncrement);
    isNotNullable = new  JCheckBox("",this.fieldInfo.isNotNullable);
    traduible = new  JCheckBox("",this.fieldInfo.traduible);
    showInWebList  = new  JCheckBox("",this.fieldInfo.showInWebList);
    showInReferenceList = new  JCheckBox("",this.fieldInfo.showInReferenceList);
    transientField  = new  JCheckBox("",this.fieldInfo.transientField);

    sqlType = new JTextField("" + this.fieldInfo.sqlType);
    sqlType.setEditable(false);
    size = new JTextField("" + this.fieldInfo.size);
    size.setEditable(false);
    digits = new JTextField("" +this.fieldInfo.digits);
    digits.setEditable(false);
    
    
    //Validació, Valor menor
    minAllowedValue = new JTextField(this.fieldInfo.getMinAllowedValue());
    
    //Validació, Valor major
    maxAllowedValue = new JTextField(this.fieldInfo.getMaxAllowedValue());
    
    // Anotacions adicionals
    additonalAnnotations = new JTextArea(this.fieldInfo.getAdditonalAnnotations());
    
    // Aplicar filter per aquest camp
    filter = new  JCheckBox("",this.fieldInfo.isFilter());
    
    // Unic per Grup de camps
    //uniqueGroup = new  JCheckBox("",this.fieldInfo.isUniqueGroup());

    // 0 = No ordernat || >0 = Ordre ascendent || <0 Ordre descendent
    orderBy = new JComboBox<JComboBoxItem>();
    for(int x = 0; x <= 10; x++) {      
      orderBy.addItem(new JComboBoxItem(x));
      if (x != 0) {
        orderBy.addItem(new JComboBoxItem(-x));
      }
    }
    orderBy.setSelectedItem(new JComboBoxItem(this.fieldInfo.getOrderBy()));

    // Group By
    groupBy = new JCheckBox("", this.fieldInfo.isGroupBy());

    initGUI();
  }
  
  public static class JComboBoxItem {
    
    public final int key;

    /**
     * @param key
     * @param value
     */
    public JComboBoxItem(int key) {
      super();
      this.key = key;
    }

    public boolean equals(Object obj) {
      if (obj instanceof JComboBoxItem) {
        if (key == ((JComboBoxItem)obj).key) {
          return true;
        }
      }
      return false;
    }

    public String toString() {
      if (key == 0) {
        return "Sense Ordre";
      }
      return  (int)Math.abs(key) + " " + (key < 0 ? "DESC" : "ASC" );
    }
    
    
  }
  
  private void initGUI() {
    try {
      {
        totPanell = new JPanel();
        BorderLayout totPanellLayout = new BorderLayout();
        totPanellLayout.setVgap(10);
        getContentPane().add(totPanell, BorderLayout.CENTER);
        totPanell.setLayout(totPanellLayout);
        {
          {
            JButton botoOK = new JButton("OK");
            botoOK.addActionListener(new ActionListener() {
              
              public void actionPerformed(ActionEvent e) {
                clickOK = true;
                if (checks()) {
                  setVisible(false);
                }
              }
            });
            
            JButton botoCancel = new JButton("Cancel");
            botoCancel.addActionListener(new ActionListener() {
              
              public void actionPerformed(ActionEvent e) {
                clickOK = false;
                setVisible(false);
              }
            });
            botonsPanell = new JPanel();
            botonsPanell.add(botoOK);
            botonsPanell.add(botoCancel);
            totPanell.add(botonsPanell, BorderLayout.SOUTH);
            
            
          }
          
          {
            campsPanell = new JPanel();
            GridBagLayout campsPanellLayout = new GridBagLayout();
            campsPanellLayout.columnWidths = new int[] {7, 7};
            campsPanellLayout.rowHeights = new int[] {7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7};
            campsPanellLayout.columnWeights = new double[] {0.25, 0.75};
            campsPanellLayout.rowWeights = new double[] {0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1};            
            campsPanell.setLayout(campsPanellLayout);
            
            // ----  1
            campsPanell.add(new JLabel("sqlName"), new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
            campsPanell.add(sqlName, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
            campsPanell.add(new JLabel("javaName"), new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
            campsPanell.add(javaName, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
      /*      
            campsPanell.add(new JLabel("label"), new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
            campsPanell.add(label, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
       */

            campsPanell.add(new JLabel("additonalAnnotations"), new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
            campsPanell.add(additonalAnnotations, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

            
            campsPanell.add(new JLabel("descripcio"), new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
            campsPanell.add(descripcio, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
            campsPanell.add(new JLabel("defaultValue"), new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
            campsPanell.add(defaultValue, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
            campsPanell.add(new JLabel("javaType"), new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
            campsPanell.add(javaType, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
            campsPanell.add(new JLabel("sqlType"), new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
            campsPanell.add(sqlType, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
            campsPanell.add(new JLabel("size"), new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
            campsPanell.add(size, new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
            campsPanell.add(new JLabel("digits"), new GridBagConstraints(0, 8, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
            campsPanell.add(digits, new GridBagConstraints(1, 8, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

            campsPanell.add(new JLabel("isPK"), new GridBagConstraints(0, 9, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
            campsPanell.add(isPK, new GridBagConstraints(1, 9, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

            campsPanell.add(new JLabel("isUnique"), new GridBagConstraints(0, 10, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
            campsPanell.add(isUnique, new GridBagConstraints(1, 10, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

            campsPanell.add(new JLabel("Index"), new GridBagConstraints(0, 11, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
            campsPanell.add(index, new GridBagConstraints(1, 11, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));


            int i = 12;
            
            
            
            campsPanell.add(new JLabel("isAutoIncrement"), new GridBagConstraints(0, i, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
            campsPanell.add(isAutoIncrement, new GridBagConstraints(1, i, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
            i++;


            campsPanell.add(new JLabel("isNotNullable"), new GridBagConstraints(0, i, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
            campsPanell.add(isNotNullable, new GridBagConstraints(1, i, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
            i++;
  

            campsPanell.add(new JLabel("Aquest camp conte un codi de traduccio?"), new GridBagConstraints(0, i, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
            campsPanell.add(traduible, new GridBagConstraints(1, i, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
            i++;

            campsPanell.add(new JLabel("showInWebList"), new GridBagConstraints(0, i, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
            campsPanell.add(showInWebList, new GridBagConstraints(1, i, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
            i++;

            campsPanell.add(new JLabel("showInReferenceList"), new GridBagConstraints(0, i, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
            campsPanell.add(showInReferenceList, new GridBagConstraints(1, i, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
            i++;

            campsPanell.add(new JLabel("transientField"), new GridBagConstraints(0, i, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
            campsPanell.add(transientField, new GridBagConstraints(1, i, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
            i++;
            
            // XX
            
            campsPanell.add(new JLabel("minAllowedValue"), new GridBagConstraints(0, i, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
            campsPanell.add(minAllowedValue, new GridBagConstraints(1, i, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
            i++;
            campsPanell.add(new JLabel("maxAllowedValue"), new GridBagConstraints(0, i, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
            campsPanell.add(maxAllowedValue, new GridBagConstraints(1, i, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
            i++;
            campsPanell.add(new JLabel("filterBy"), new GridBagConstraints(0, i, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
            campsPanell.add(filter, new GridBagConstraints(1, i, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
            i++;
            campsPanell.add(new JLabel("default orderBy"), new GridBagConstraints(0, i, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
            campsPanell.add(orderBy, new GridBagConstraints(1, i, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
            i++;
            campsPanell.add(new JLabel("groupBy"), new GridBagConstraints(0, i, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
            campsPanell.add(groupBy, new GridBagConstraints(1, i, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
            i++;
            //campsPanell.add(new JLabel("uniqueGroup"), new GridBagConstraints(0, 24, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
            //campsPanell.add(uniqueGroup, new GridBagConstraints(1, 24, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

            //int i = 22;
            for (String  lang : idiomes) {
              campsPanell.add(new JLabel("Label[" + lang.toUpperCase()+ "]"), new GridBagConstraints(0, i, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
              campsPanell.add(labels.get(lang), new GridBagConstraints(1, i, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
              i++;
            }

            totPanell.add(campsPanell, BorderLayout.CENTER);
          }
        }
      }
      
      this.setSize(450, 600);
      
      this.pack();
      
      if (this.getWidth() < 450) {
        this.setSize(450, this.getHeight());
      }
      
      final Toolkit toolkit = Toolkit.getDefaultToolkit();
      final Dimension screenSize = toolkit.getScreenSize();
      final int x = (screenSize.width - this.getWidth()) / 2;
      final int y = (screenSize.height - this.getHeight()) / 2;
      this.setLocation(x, y);
      
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  
  public boolean checks() {
    
    if (null == TableModelColumnList.checkJavaClass(javaType.getText())) {
      return false;
    }
    
    
    
    // TODO Mirar tots els checks de la resta d'entrades
    
    return true;
  }
  
  public void updateFieldInfoWithNewValues() {
    // TODO ReadOnly  public String sqlName;
    this.fieldInfo.setJavaName(javaName.getText());
    
    
    Map<String, String> labelsStr = this.fieldInfo.getLabels();
    for (String  lang : idiomes) {
      labelsStr.put(lang, this.labels.get(lang).getText());
    }
    
    this.fieldInfo.setDescripcio(checkNull(descripcio.getText()));
    this.fieldInfo.setDefaultValue(checkNull(defaultValue.getText()));
    this.fieldInfo.setJavaType(TableModelColumnList.checkJavaClass(javaType.getText()));

   
    this.fieldInfo.setPK(checkNull2(isPK.getText()));
    this.fieldInfo.setUnique(checkNull2(isUnique.getText()));
    this.fieldInfo.setIndex(checkNull2(index.getText()));
    this.fieldInfo.setAutoIncrement(isAutoIncrement.isSelected());
    this.fieldInfo.setNotNullable(isNotNullable.isSelected());
    this.fieldInfo.setTraduible(traduible.isSelected());
    this.fieldInfo.setShowInWebList(showInWebList.isSelected());
    this.fieldInfo.setShowInReferenceList(showInReferenceList.isSelected());
    this.fieldInfo.setTransientField(transientField.isSelected());
    
    
  //Validació, Valor menor
    this.fieldInfo.setMinAllowedValue(checkNull(minAllowedValue.getText()));
    
    //Validació, Valor major
    this.fieldInfo.setMaxAllowedValue(checkNull(maxAllowedValue.getText()));
    
    // Anotacions adicionals
    this.fieldInfo.setAdditonalAnnotations(checkNull(additonalAnnotations.getText()));
    
    // Aplicar filter per aquest camp
    this.fieldInfo.setFilter(filter.isSelected());
    
    // Unic per Grup de camps
    //this.fieldInfo.setUniqueGroup(uniqueGroup.isSelected());

    // 0 = No ordernat || >0 = Ordre ascendent || <0 Ordre descendent
    this.fieldInfo.setOrderBy(((JComboBoxItem)orderBy.getSelectedItem()).key);
    
    
    this.fieldInfo.setGroupBy(groupBy.isSelected());

     // TODO ReadOnly sqlType; // read
     // TODO ReadOnly size; // read
     // TODO ReadOnly digits; // read
    
 }
  
  public String checkNull(String value) {
    if (value == null || value.length() == 0) {
      return null;
    }
    return value;
  }
  
  public String checkNull2(String value) {
    if (value == null || value.trim().length() == 0) {
      return null;
    }
    return value;
  }

}
