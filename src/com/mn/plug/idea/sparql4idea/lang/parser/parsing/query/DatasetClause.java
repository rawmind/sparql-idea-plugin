package com.mn.plug.idea.sparql4idea.lang.parser.parsing.query;

import com.intellij.lang.PsiBuilder;
import com.mn.plug.idea.sparql4idea.lang.parser.parsing.lit.Literals;
import com.mn.plug.idea.sparql4idea.lang.parser.parsing.util.ParserUtils;

import static com.mn.plug.idea.sparql4idea.lang.Sparql.DATASET_CLAUSE;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.KW_FROM;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.KW_NAMED;

/**
 * Dataset clasue parser
 *
 * @author Matt Nathan
 */
public class DatasetClause {

  public static boolean parse(PsiBuilder builder) {
    if (ParserUtils.lookAhead(builder, KW_FROM)) {
      final PsiBuilder.Marker datasetClause = builder.mark();
      if (ParserUtils.getToken(builder, KW_FROM, "Expecting \"FROM\"")) {
        parseDefaultGraphClause(builder);
      }

      datasetClause.done(DATASET_CLAUSE);
      return true;
    }
    return false;
  }

  private static void parseDefaultGraphClause(PsiBuilder builder) {
    ParserUtils.getToken(builder, KW_NAMED); // 'NAMED'?

    if (!Literals.parseIriRef(builder)) {
      builder.error("Expecting IRIref");
    }
  }
}
