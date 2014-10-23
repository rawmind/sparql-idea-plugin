package com.mn.plug.idea.sparql4idea.client;

import javax.swing.table.TableModel;

/**
 * @author Andrey Kovrov
 */
public class EmptyResult extends AbstractResult {

  public EmptyResult(long queryTime, long connectionTime) {
    super(queryTime, connectionTime);
  }

  @Override
  public TableModel getTableDataModel() {
    return EMPTY_MODEL;
  }

}
