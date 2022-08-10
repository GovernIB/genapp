package org.fundaciobit.genapp.generator.gui;

import javax.swing.*;

/**
 * Title: Rapit Entity Bean 2010 Description: Copyright: Copyright (c) 2014
 * Company: XmasSoft
 * 
 * @author anadal
 */
public class TextAreaEditor extends DefaultCellEditor {

    public TextAreaEditor() {
        super(new JTextField());
        final JTextArea textArea = new JTextArea();
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(null);
        editorComponent = scrollPane;

        delegate = new DefaultCellEditor.EditorDelegate() {

            public void setValue(Object value) {
                textArea.setText((value != null) ? value.toString() : "");
            }

            public Object getCellEditorValue() {
                return textArea.getText();
            }
        };
    }
}
