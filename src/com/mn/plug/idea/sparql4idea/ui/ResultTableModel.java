package com.mn.plug.idea.sparql4idea.ui;

import javax.swing.table.DefaultTableModel;

/**
 * @author Andrey Kovrov
 */
public class ResultTableModel extends DefaultTableModel {

  public ResultTableModel() {
  }

  @Override
  public boolean isCellEditable(int row, int column) {
    return false;
  }

}
