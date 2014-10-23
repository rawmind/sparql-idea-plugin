package com.mn.plug.idea.sparql4idea.lang.parser.parsing.common;

import com.intellij.lang.PsiBuilder;
import com.mn.plug.idea.sparql4idea.lang.parser.parsing.util.ParserUtils;

import static com.mn.plug.idea.sparql4idea.lang.Sparql.FILTER;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.KW_FILTER;

/**
 * Generated JavaDoc Comment.
 *
 * @author Matt Nathan
 */
public class Filter {

  public static boolean parse(PsiBuilder builder) {
    if (ParserUtils.lookAhead(builder, KW_FILTER)) {
      final PsiBuilder.Marker filter = builder.mark();
      if (ParserUtils.getToken(builder, KW_FILTER)) {
        if (!Constraint.parse(builder)) {
          builder.error("Expecting Constraint");
        }
      }
      filter.done(FILTER);
      return true;
    }
    return false;
  }
}
