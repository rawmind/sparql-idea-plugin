package com.mn.plug.idea.sparql4idea.core;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 * @author Andrey Kovrov
 */
public abstract class AbstractResult implements Result {

  private String errorMessage;
  private boolean errorOccurred;
  private long requestTime;

  protected static final TableModel EMPTY_MODEL = new DefaultTableModel();

  protected AbstractResult(String errorMessage) {
    this.errorMessage = errorMessage;
    errorOccurred = true;
  }

  protected AbstractResult(long requestTime) {
    this.requestTime = requestTime;
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
    return String.format("Request time: %d", requestTime);
  }

}
