package com.mn.plug.idea.sparql4idea.lang.parser.parsing.query;

import com.intellij.lang.PsiBuilder;
import com.mn.plug.idea.sparql4idea.lang.parser.parsing.graph.GroupGraphPattern;
import com.mn.plug.idea.sparql4idea.lang.parser.parsing.util.ParserUtils;

import static com.mn.plug.idea.sparql4idea.lang.Sparql.KW_WHERE;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.WHERE_CLAUSE;

/**
 * Where clause parser
 *
 * @author Matt Nathan
 */
public class WhereClause {

  public static boolean parse(PsiBuilder builder) {
    final PsiBuilder.Marker whereClause = builder.mark();
    // (WHERE)?
    ParserUtils.getToken(builder, KW_WHERE);

    if (!GroupGraphPattern.parse(builder)) {
      builder.error("Expecting GroupGraphPattern");
    }

    whereClause.done(WHERE_CLAUSE);
    return true;
  }
}
