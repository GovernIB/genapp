package org.fundaciobit.genapp.generator.gui;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JScrollPane;

import org.fundaciobit.genapp.TableInfo;

public class SelectionTablesDialog extends JDialog implements ActionListener {
  
  /**
   * 
   */
  private static final long serialVersionUID = -8131583211854109680L;

  JScrollPane scroll;
  
  List<String> selected = new ArrayList<String>();
  
  //final TableInfo[] items2;
  
  final JList list;
  
  final JButton ok = new JButton("OK");
  
  public SelectionTablesDialog(TableInfo[] items) {
    this(items, null);
  }
  
  public SelectionTablesDialog(TableInfo[] items, String[] selectedItems) {
    super((Frame)null, "Select tables",true);
    //this.items = items;
    setSize(300, 600);
    
    String[] names = TableInfo.tableInfoToTableName(items);
    
    list = new JList(names);

    list.setSelectionModel(new DefaultListSelectionModel() {

      private static final long serialVersionUID = 4148764407241383977L;

      @Override
      public void setSelectionInterval(int index0, int index1) {
          if(super.isSelectedIndex(index0)) {
              super.removeSelectionInterval(index0, index1);
          }
          else {
              super.addSelectionInterval(index0, index1);
          }
      }
    });
    
    if (selectedItems == null) {
      int start = 0;
      int end = list.getModel().getSize() - 1;
      if (end >= 0) {
        list.setSelectionInterval(start, end);
      }
    } else {
      for (int i = 0; i < selectedItems.length; i++) {
        list.setSelectedValue(selectedItems[i], false);
      }
      
      
    }
    
    this.setLayout(new BorderLayout());
    
    
    //scroll.setLayout(new ScrollPaneLayout());
    
    scroll = new JScrollPane(list);
    //scroll.add(list); //, BorderLayout.CENTER);
    //scroll.setSize(300, 600);
    
    getContentPane().add(scroll,BorderLayout.CENTER);
    
    getContentPane().add(ok,BorderLayout.SOUTH);
    //this.pack();
    setLocationRelativeTo(null);
    ok.addActionListener(this);
  }



  


  public void actionPerformed(ActionEvent e) {
    this.setVisible(false);      
  }
  
  public String[] getSelecteds() {
    
    Object[] objs = list.getSelectedValues();
    
    String[] str = new String[objs.length];
    
    for (int i = 0; i < str.length; i++) {
      str[i] = (String)objs[i];
    }
    return str;
    
  }
  
}
