package org.fundaciobit.genapp.generator.gui;

import javax.swing.*;
/**
 * Title:        Rapit Entity Bean 2010
 * Description:
 * Copyright:    Copyright (c) 2014
 * Company:      XmasSoft
 * @author anadal
 */
public class TextAreaEditor extends DefaultCellEditor {
  /**
   * 
   */
  private static final long serialVersionUID = 8991611039874282415L;

  public TextAreaEditor() {
    super(new JTextField());
    final JTextArea textArea = new JTextArea();
    textArea.setWrapStyleWord(true);
    textArea.setLineWrap(true);
    JScrollPane scrollPane = new JScrollPane(textArea);
    scrollPane.setBorder(null);
    editorComponent = scrollPane;

    delegate = new DefaultCellEditor.EditorDelegate() {
      /**
       * 
       */
      private static final long serialVersionUID = -1724549100456809365L;
      public void setValue(Object value) {
        textArea.setText((value != null) ? value.toString() : "");
      }
      public Object getCellEditorValue() {
        return textArea.getText();
      }
    };
  }
}
  
