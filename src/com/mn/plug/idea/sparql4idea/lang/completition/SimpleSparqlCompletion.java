package com.mn.plug.idea.sparql4idea.lang.completition;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionResultSet;

/**
 * @author Andrey Kovrov
 */
public class SimpleSparqlCompletion extends CompletionContributor {


  @Override
  public void fillCompletionVariants(CompletionParameters parameters, CompletionResultSet result) {
    result.addAllElements(BasicDictionary.LOOKUP_ELEMENTS_SPARQL_1_1);
    super.fillCompletionVariants(parameters, result);
  }


}
