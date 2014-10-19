package com.mn.plug.idea.sparql4idea.core;

import org.openrdf.query.TupleQueryResult;

/**
 * @author Andrey Kovrov
 */
public class TupleResult extends AbstractResult{

  private TupleQueryResult tupleQueryResult;

  public TupleResult(TupleQueryResult tupleQueryResult, long requestTime) {
    super(requestTime);
    this.tupleQueryResult = tupleQueryResult;
  }

  public TupleResult(String error) {
    super(error);
  }

  @Override
  public TupleQueryResult getTupleQueryResult() {
    return tupleQueryResult;
  }

}
