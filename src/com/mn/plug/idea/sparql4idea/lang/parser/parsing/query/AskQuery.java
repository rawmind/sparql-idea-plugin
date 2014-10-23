package com.mn.plug.idea.sparql4idea.lang.parser.parsing.query;

import com.intellij.lang.PsiBuilder;
import com.mn.plug.idea.sparql4idea.lang.parser.parsing.util.ParserUtils;

import static com.mn.plug.idea.sparql4idea.lang.Sparql.ASK_QUERY;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.KW_ASK;


/**
 * Ask parser
 *
 * @author Matt Nathan
 */
public class AskQuery {
  public static boolean parse(PsiBuilder builder) {
    if (ParserUtils.lookAhead(builder, KW_ASK)) {
      final PsiBuilder.Marker askQuery = builder.mark();
      if (ParserUtils.getToken(builder, KW_ASK, "Expecting 'ASK'")) {
        //noinspection StatementWithEmptyBody
        while (DatasetClause.parse(builder));

        if (!WhereClause.parse(builder)) {
          builder.error("Expecting WhereClause");
        }
      }
      askQuery.done(ASK_QUERY);
      return true;
    }
    return false;
  }
}
