package com.mn.plug.idea.sparql4idea.lang.parser.parsing.query;

import com.intellij.lang.PsiBuilder;
import com.mn.plug.idea.sparql4idea.lang.parser.parsing.common.SolutionModifiers;
import com.mn.plug.idea.sparql4idea.lang.parser.parsing.lit.Literals;
import com.mn.plug.idea.sparql4idea.lang.parser.parsing.util.ParserUtils;

import static com.mn.plug.idea.sparql4idea.lang.Sparql.KW_DISTINCT;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.KW_REDUCED;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.KW_SELECT;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.OP_MULT;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.PROJECTION_VARIABLES;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.SELECT_QUERY;


/**
 * Select query parser.
 *
 * @author Matt Nathan
 */
public class SelectQuery {

  public static boolean parse(PsiBuilder builder) {
    if (ParserUtils.lookAhead(builder, KW_SELECT)) {
      final PsiBuilder.Marker selectQuery = builder.mark();

      if (ParserUtils.getToken(builder, KW_SELECT, "Expecting \"SELECT\"")) {

        if (!ParserUtils.getToken(builder, KW_DISTINCT)) {
          ParserUtils.getToken(builder, KW_REDUCED);
        }

        parseProjectionVariables(builder);

        //noinspection StatementWithEmptyBody
        while (DatasetClause.parse(builder)) ;

        WhereClause.parse(builder);

        SolutionModifiers.parse(builder);
      }
      selectQuery.done(SELECT_QUERY);
      return true;
    }
    return false;
  }

  private static void parseProjectionVariables(PsiBuilder builder) {
    final PsiBuilder.Marker vars = builder.mark();

    if (ParserUtils.getToken(builder, OP_MULT)) {
      // we have parsed all needed tokens
    } else if (Literals.parseVar(builder, true)) {
      //noinspection StatementWithEmptyBody
      while (Literals.parseVar(builder, true)) ;
    } else {
      builder.error("Expecting Variable or *");
    }

    vars.done(PROJECTION_VARIABLES);
  }
}
