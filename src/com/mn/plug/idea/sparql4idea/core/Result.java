package com.mn.plug.idea.sparql4idea.core;

import javax.swing.table.TableModel;

/**
 * @author Andrey Kovrov
 */
public interface Result {

  TableModel getTableDataModel();

  boolean hasError();

  String getErrorMessage();

  String getMessage();

}
