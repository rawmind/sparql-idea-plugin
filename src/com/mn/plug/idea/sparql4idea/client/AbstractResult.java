package com.mn.plug.idea.sparql4idea.client;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 * @author Andrey Kovrov
 */
public abstract class AbstractResult implements Result {

  private long connectionTime;
  private String errorMessage;
  private boolean errorOccurred;
  private long queryTime;

  protected static final TableModel EMPTY_MODEL = new DefaultTableModel();

  protected AbstractResult(String errorMessage) {
    this.errorMessage = errorMessage;
    errorOccurred = true;
  }

  protected AbstractResult(long queryTime, long connectionTime) {
    this.queryTime = queryTime;
    this.connectionTime = connectionTime;
  }

  public void setErrorMessage(String errorMessage) {
    errorOccurred = true;
    this.errorMessage = errorMessage;
  }

  @Override
  public boolean hasError() {
    return errorOccurred;
  }

  @Override
  public String getErrorMessage() {
    return errorMessage;
  }

  @Override
  public String getMessage() {
    return String.format("Connection time: %d, query time: %d ms", connectionTime, queryTime);
  }

}
