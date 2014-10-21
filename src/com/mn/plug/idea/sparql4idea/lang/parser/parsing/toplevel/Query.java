package com.mn.plug.idea.sparql4idea.lang.parser.parsing.toplevel;

import com.intellij.lang.PsiBuilder;

/**
 * Top level query. This is the entry point for the parsing
 *
 * @author Matt Nathan
 */
public class Query {
  public static void parse(PsiBuilder builder) {
    Prologue.parse(builder);
//todo: need to implement 1.1 protocol support
   /* if (!SelectQuery.parse(builder) &&
            !ConstructQuery.parse(builder) &&
            !DescribeQuery.parse(builder) &&
            !AskQuery.parse(builder)) {
      builder.error("Expecting one of SELECT, CONSTRUCT, DESCRIBE or ASK");
    }*/
  }
}
