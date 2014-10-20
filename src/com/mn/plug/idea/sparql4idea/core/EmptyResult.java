package com.mn.plug.idea.sparql4idea.core;

import javax.swing.table.TableModel;

/**
 * @author Andrey Kovrov
 */
public class EmptyResult extends AbstractResult {

  public EmptyResult(long requestTime) {
    super(requestTime);
  }

  @Override
  public TableModel getTableDataModel() {
    return EMPTY_MODEL;
  }

}
