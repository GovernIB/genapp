package org.fundaciobit.genapp.generator;

import java.util.ArrayList;
/**
 * Title:        Rapit Entity Bean 2010
 * Description:
 * Copyright:    Copyright (c) 2014
 * Company:      XmasSoft
 * @author anadal
 */
public class MultiColumnMatrixTableGenerator extends TableGenerator {

  final String table;

  public MultiColumnMatrixTableGenerator(String name, ConditionalColumn[] columns,
      String[][] contentTable) {
    super(name, null);

    int numColumns = 3;

    ArrayList<ArrayList<String[]>> tables = new ArrayList<ArrayList<String[]>>();
    for (int i = 0; i < numColumns; i++) {
      tables.add(new ArrayList<String[]>());
    }
    for (int i = 0; i < contentTable.length; i++) {
      ArrayList<String[]> tableContent = tables.get(i % numColumns);
      tableContent.add(contentTable[i]);
    }

    StringBuffer simpleTable = new StringBuffer();
    simpleTable.append("<table><tr>");

    for (int i = 0; i < tables.size(); i++) {
      if (i > 0) {
        simpleTable.append("<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>");
      }

      ArrayList<String[]> adata = tables.get(i);
      String[][] data = adata.toArray(new String[adata.size()][]);
      simpleTable.append("<td>");
      simpleTable.append(new SimpleMatrixTableGenerator(name, columns, data)
          .generateTableCode());
      simpleTable.append("</td>");
    }
    simpleTable.append("</tr></table>");

    table = new MatrixTableGenerator(name, null,
        new String[][] { { simpleTable.toString() } }).getTableContent();

  }

  @Override
  public String getTableContent() {
    // TODO Auto-generated method stub
    return table.toString();
  }

  public class SimpleMatrixTableGenerator extends MatrixTableGenerator {

    public SimpleMatrixTableGenerator(String name, ConditionalColumn[] columns,
        String[][] contentTable) {
      super(name, columns, contentTable);
    }

    protected String getCenterTableHeader() {
      return "";
    }

    protected String getCenterTableFooter() {
      return "";
    }

    protected String getTitleHeader(String name) {
      return "";
    }

    protected String getTitleFooter() {
      return "";
    }

  }

}
