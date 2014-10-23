package com.mn.plug.idea.sparql4idea.lang.parser.parsing.query;

import com.intellij.lang.PsiBuilder;
import com.mn.plug.idea.sparql4idea.lang.parser.parsing.common.SolutionModifiers;
import com.mn.plug.idea.sparql4idea.lang.parser.parsing.graph.Triples;
import com.mn.plug.idea.sparql4idea.lang.parser.parsing.util.ParserUtils;

import static com.mn.plug.idea.sparql4idea.lang.Sparql.CONSTRUCT_QUERY;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.CONSTRUCT_TEMPLATE;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.KW_CONSTRUCT;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.OP_LCURLY;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.OP_RCURLY;

/**
 * Construct query parsing
 *
 * @author Matt Nathan
 */
public class ConstructQuery {

  public static boolean parse(PsiBuilder builder) {
    if (ParserUtils.lookAhead(builder, KW_CONSTRUCT)) {
      final PsiBuilder.Marker constructQuery = builder.mark();
      if (ParserUtils.getToken(builder, KW_CONSTRUCT, "Expecting 'CONSTRUCT'")) {
        if (parseConstructTemplate(builder)) {
          //noinspection StatementWithEmptyBody
          while (DatasetClause.parse(builder)) ;
          if (!WhereClause.parse(builder)) {
            builder.error("Expecting WhereClause");
          } else {
            SolutionModifiers.parse(builder);
          }
        }
      }
      constructQuery.done(CONSTRUCT_QUERY);
      return true;
    }
    return false;
  }

  private static boolean parseConstructTemplate(PsiBuilder builder) {
    final PsiBuilder.Marker constructTemplate = builder.mark();
    if (ParserUtils.getToken(builder, OP_LCURLY, "Expecting '{'")) {

      Triples.parseTriplesBlock(builder);

      ParserUtils.getToken(builder, OP_RCURLY, "Expecting '}'");
      constructTemplate.done(CONSTRUCT_TEMPLATE);
      return true;
    }
    return false;
  }
}
