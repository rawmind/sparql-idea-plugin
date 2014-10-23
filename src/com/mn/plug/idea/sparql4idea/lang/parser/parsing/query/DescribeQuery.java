package com.mn.plug.idea.sparql4idea.lang.parser.parsing.query;

import com.intellij.lang.PsiBuilder;
import com.mn.plug.idea.sparql4idea.lang.parser.parsing.common.SolutionModifiers;
import com.mn.plug.idea.sparql4idea.lang.parser.parsing.lit.Literals;
import com.mn.plug.idea.sparql4idea.lang.parser.parsing.util.ParserUtils;

import static com.mn.plug.idea.sparql4idea.lang.Sparql.DESCRIBE_QUERY;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.KW_DESCRIBE;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.OP_MULT;

/**
 * Generated JavaDoc Comment.
 *
 * @author Matt Nathan
 */
public class DescribeQuery {

  public static boolean parse(PsiBuilder builder) {
    if (ParserUtils.lookAhead(builder, KW_DESCRIBE)) {
      final PsiBuilder.Marker describeQuery = builder.mark();
      if (ParserUtils.getToken(builder, KW_DESCRIBE, "Expecting 'DESCRIBE'")) {
        boolean valid;
        if (ParserUtils.getToken(builder, OP_MULT)) {
          // expected
          valid = true;
        } else if (Literals.parseVarOrIriRef(builder)) {
          //noinspection StatementWithEmptyBody
          while (Literals.parseVarOrIriRef(builder)) ;
          valid = true;
        } else {
          builder.error("Expecting '*' or VarOrIRIref");
          valid = false;
        }

        if (valid) {
          //noinspection StatementWithEmptyBody
          while (DatasetClause.parse(builder)) ;

          WhereClause.parse(builder);

          SolutionModifiers.parse(builder);
        }
      }

      describeQuery.done(DESCRIBE_QUERY);
      return true;
    }
    return false;
  }
}
