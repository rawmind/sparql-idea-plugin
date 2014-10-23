package com.mn.plug.idea.sparql4idea.lang.parser.parsing.func;

import com.intellij.lang.PsiBuilder;
import com.mn.plug.idea.sparql4idea.lang.parser.parsing.expr.Expressions;
import com.mn.plug.idea.sparql4idea.lang.parser.parsing.lit.Literals;
import com.mn.plug.idea.sparql4idea.lang.parser.parsing.util.ParserUtils;

import static com.mn.plug.idea.sparql4idea.lang.Sparql.KW_BOUND;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.KW_DATATYPE;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.KW_IS_BLANK;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.KW_IS_IRI;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.KW_IS_LITERAL;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.KW_IS_URI;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.KW_LANG;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.KW_LANGMATCHES;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.KW_REGEX;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.KW_SAME_TERM;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.KW_STR;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.LIT_NIL;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.OP_COMMA;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.OP_LROUND;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.OP_RROUND;

/**
 * Generated JavaDoc Comment.
 *
 * @author Matt Nathan
 */
public class Functions {

  public static boolean parseIriRefOrFunction(PsiBuilder builder) {
    if (Literals.parseIriRef(builder)) {
      parseArgList(builder);
      return true;
    }
    return false;
  }

  public static boolean parseFunction(PsiBuilder builder) {
    if (Literals.parseIriRef(builder)) {
      if (!parseArgList(builder)) {
        builder.error("Expecting ArgList");
      }
      return true;
    }
    return false;
  }

  private static boolean parseArgList(PsiBuilder builder) {
    if (ParserUtils.getToken(builder, LIT_NIL)) {
      return true;
    } else if (ParserUtils.getToken(builder, OP_LROUND)) {
      Expressions.parseExpression(builder);
      while (ParserUtils.getToken(builder, OP_COMMA)) {
        Expressions.parseExpression(builder);
      }
      ParserUtils.getToken(builder, OP_RROUND, "Expecting ')'");
      return true;
    }
    return false;
  }

  public static boolean parseBuiltInCall(PsiBuilder builder) {
    // expr functions
    if (ParserUtils.getToken(builder, KW_STR) ||
            ParserUtils.getToken(builder, KW_LANG) ||
            ParserUtils.getToken(builder, KW_DATATYPE) ||
            ParserUtils.getToken(builder, KW_IS_IRI) ||
            ParserUtils.getToken(builder, KW_IS_URI) ||
            ParserUtils.getToken(builder, KW_IS_BLANK) ||
            ParserUtils.getToken(builder, KW_IS_LITERAL)) {
      if (!Expressions.parseBracketedExpr(builder)) {
        builder.error("Expecting '('");
      }
      return true;

      // two expr functions
    } else if (ParserUtils.getToken(builder, KW_LANGMATCHES) ||
            ParserUtils.getToken(builder, KW_SAME_TERM)) {
      if (ParserUtils.getToken(builder, OP_LROUND, "Expecting '('")) {
        Expressions.parseExpression(builder);
        ParserUtils.getToken(builder, OP_COMMA, "Expecting ','");
        Expressions.parseExpression(builder);
        ParserUtils.getToken(builder, OP_RROUND, "Expecting ')'");
      }
      return true;
    } else if (ParserUtils.getToken(builder, KW_BOUND)) {
      if (ParserUtils.getToken(builder, OP_LROUND, "Expecting '('")) {
        Literals.parseVar(builder);
        ParserUtils.getToken(builder, OP_RROUND, "Expecting ')'");
      }
      return true;
    } else if (ParserUtils.getToken(builder, KW_REGEX)) {
      if (ParserUtils.getToken(builder, OP_LROUND, "Expecting '('")) {
        Expressions.parseExpression(builder);
        ParserUtils.getToken(builder, OP_COMMA, "Expecting ','");
        Expressions.parseExpression(builder);
        if (ParserUtils.getToken(builder, OP_COMMA)) {
          Expressions.parseExpression(builder);
        }
        ParserUtils.getToken(builder, OP_RROUND, "Expecting ')'");
      }
      return true;
    }
    return false;
  }
}
