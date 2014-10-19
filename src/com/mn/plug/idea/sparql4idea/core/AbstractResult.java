package com.mn.plug.idea.sparql4idea.core;

/**
 * @author Andrey Kovrov
 */
public abstract class AbstractResult implements Result{

  private String errorMessage;
  private boolean errorOccurred;
  private long requestTime;

  protected AbstractResult(String errorMessage) {
    this.errorMessage = errorMessage;
    errorOccurred = true;
  }

  protected AbstractResult(long requestTime) {
    this.requestTime = requestTime;
  }

  @Override
  public boolean hasError() {
    return errorOccurred;
  }

  @Override
  public String getErrorMessage() {
    return errorMessage;
  }

}
