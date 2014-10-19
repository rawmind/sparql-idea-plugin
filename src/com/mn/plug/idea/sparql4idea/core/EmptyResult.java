package com.mn.plug.idea.sparql4idea.core;

import org.openrdf.query.TupleQueryResult;

/**
 * @author Andrey Kovrov
 */
public class EmptyResult extends AbstractResult {

  public EmptyResult(long requestTime) {
    super(requestTime);
  }

  @Override
  public TupleQueryResult getTupleQueryResult() {
    return null;
  }

}
