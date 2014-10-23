package com.mn.plug.idea.sparql4idea.lang.parser.parsing.expr;

import com.intellij.lang.PsiBuilder;
import com.mn.plug.idea.sparql4idea.lang.parser.parsing.func.Functions;
import com.mn.plug.idea.sparql4idea.lang.parser.parsing.lit.Literals;
import com.mn.plug.idea.sparql4idea.lang.parser.parsing.util.ParserUtils;

import static com.mn.plug.idea.sparql4idea.lang.Sparql.*;

/**
 * Generated JavaDoc Comment.
 *
 * @author Matt Nathan
 */
public class Expressions {

  public static void parseExpression(PsiBuilder builder) {
    parseConditionalOrExpr(builder);
  }

  public static void parseConditionalOrExpr(PsiBuilder builder) {
    final PsiBuilder.Marker expr = builder.mark();
    parseConditionalAndExpr(builder);
    while (ParserUtils.getToken(builder, OP_PIPEPIPE)) {
      parseConditionalAndExpr(builder);
    }
    expr.done(OR_EXPR);
  }

  private static void parseConditionalAndExpr(PsiBuilder builder) {
    final PsiBuilder.Marker expr = builder.mark();
    parseRelationalExpr(builder);
    while (ParserUtils.getToken(builder, OP_ANDAND)) {
      parseRelationalExpr(builder);
    }
    expr.done(AND_EXPR);
  }

  private static void parseRelationalExpr(PsiBuilder builder) {
    final PsiBuilder.Marker expr = builder.mark();
    parseAddExpr(builder);
    if (ParserUtils.getToken(builder, OP_EQ) ||
            ParserUtils.getToken(builder, OP_NE) ||
            ParserUtils.getToken(builder, OP_LT) ||
            ParserUtils.getToken(builder, OP_GT) ||
            ParserUtils.getToken(builder, OP_LE) ||
            ParserUtils.getToken(builder, OP_GE)) {
      parseAddExpr(builder);
    }
    expr.done(RELATIONAL_EXPR);
  }

  private static void parseAddExpr(PsiBuilder builder) {
    final PsiBuilder.Marker expr = builder.mark();
    parseMultExpr(builder);
    while (true) {
      if (ParserUtils.getToken(builder, OP_PLUS) ||
              ParserUtils.getToken(builder, OP_MINUS)) {
        parseMultExpr(builder);
      } else if (Literals.parseNumericLiteralPos(builder) ||
              Literals.parseNumericLiteralNeg(builder)) {
      } else {
        break;
      }
    }
    expr.done(ADDITIVE_EXPR);
  }

  private static void parseMultExpr(PsiBuilder builder) {
    final PsiBuilder.Marker expr = builder.mark();
    parseUnaryExpr(builder);
    while (ParserUtils.getToken(builder, OP_MULT) ||
            ParserUtils.getToken(builder, OP_DIV)) {
      parseUnaryExpr(builder);
    }
    expr.done(MULTIPLICATIVE_EXPR);
  }

  private static void parseUnaryExpr(PsiBuilder builder) {
    final PsiBuilder.Marker expr = builder.mark();
    if (ParserUtils.getToken(builder, OP_NOT) ||
            ParserUtils.getToken(builder, OP_PLUS) ||
            ParserUtils.getToken(builder, OP_MINUS)) {
      // do the same as before
      //todo: ??
    }
    parsePrimaryExpr(builder);
    expr.done(UNARY_EXPR);
  }

  private static void parsePrimaryExpr(PsiBuilder builder) {
    if (!parseBracketedExpr(builder) &&
            !Functions.parseBuiltInCall(builder) &&
            !Functions.parseIriRefOrFunction(builder) &&
            !Literals.parseRdfLiteral(builder) &&
            !Literals.parseNumericLiteral(builder) &&
            !Literals.parseBooleanLiteral(builder) &&
            !Literals.parseVar(builder)) {
      builder.error("Expecting one of BracketedExpression, BuiltInCall, IRIrefOrFunction, RDFLiteral, NumericLiteral, BooleanLiteral or Var");
    }
  }

  public static boolean parseBracketedExpr(PsiBuilder builder) {
    if (ParserUtils.getToken(builder, OP_LROUND)) {
      parseExpression(builder);
      ParserUtils.getToken(builder, OP_RROUND, "Expecting ')'");
      return true;
    }
    return false;
  }
}
