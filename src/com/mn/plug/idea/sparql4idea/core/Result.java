package com.mn.plug.idea.sparql4idea.core;

import org.openrdf.query.TupleQueryResult;

/**
 * @author Andrey Kovrov
 */
public interface Result {

  TupleQueryResult getTupleQueryResult();

  boolean hasError();

  String getErrorMessage();

}
