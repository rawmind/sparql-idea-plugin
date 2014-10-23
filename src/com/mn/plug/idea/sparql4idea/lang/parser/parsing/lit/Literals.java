package com.mn.plug.idea.sparql4idea.lang.parser.parsing.lit;

import com.intellij.lang.PsiBuilder;
import com.mn.plug.idea.sparql4idea.lang.parser.parsing.graph.GraphNode;
import com.mn.plug.idea.sparql4idea.lang.parser.parsing.graph.Graphs;
import com.mn.plug.idea.sparql4idea.lang.parser.parsing.util.ParserUtils;

import static com.mn.plug.idea.sparql4idea.lang.Sparql.*;

/**
 * Set of literal parsers
 *
 * @author Matt Nathan
 */
public class Literals {

  public static boolean parseIriRef(PsiBuilder builder) {
    if (parseIri(builder)) {
      return true;
    } else if (parsePrefixedName(builder)) {
      return true;
    }
    return false;
  }

  public static boolean parseIri(PsiBuilder builder) {
    if (ParserUtils.lookAhead(builder, LIT_IRI_START)) {
      final PsiBuilder.Marker iri = builder.mark();
      if (ParserUtils.getToken(builder, LIT_IRI_START, "Expecting '<'")) {
        if (ParserUtils.getToken(builder, LIT_IRI_BODY, "Expecting IRI body")) {
          ParserUtils.getToken(builder, LIT_IRI_END, "Expecting '>'");
        }
      }
      iri.done(LIT_IRI);
      return true;
    }
    return false;
  }

  public static boolean parsePrefixedName(PsiBuilder builder) {
    if (parsePNameLn(builder)) {
      return true;
    } else if (parsePNameNs(builder)) {
      return true;
    }
    return false;
  }

  public static boolean parsePNameLn(PsiBuilder builder) {
    if (ParserUtils.lookAhead(builder, NAME_NS)) {
      final PsiBuilder.Marker pname = builder.mark();
      ParserUtils.eatElement(builder, NAME_NS);
      ParserUtils.getToken(builder, NAME_COLON, "Expecting ':'");
      if (!ParserUtils.lookAhead(builder, NAME_LN)) {
        builder.error("Expecting local name part");
      } else {
        ParserUtils.eatElement(builder, NAME_LN);
      }
      pname.done(PNAME);
      return true;

    } else if (ParserUtils.lookAhead(builder, NAME_COLON)) {
      final PsiBuilder.Marker pname = builder.mark();
      ParserUtils.getToken(builder, NAME_COLON);
      if (!ParserUtils.lookAhead(builder, NAME_LN)) {
        builder.error("Expecting local name part");
      } else {
        ParserUtils.eatElement(builder, NAME_LN);
      }
      pname.done(PNAME);
      return true;
    }
    return false;
  }

  public static boolean parsePNameNs(PsiBuilder builder) {
    if (ParserUtils.lookAhead(builder, NAME_NS)) {
      final PsiBuilder.Marker pname = builder.mark();
      ParserUtils.eatElement(builder, NAME_NS);
      ParserUtils.getToken(builder, NAME_COLON, "Expecting ':'");
      pname.done(PNAME);
      return true;
    } else if (ParserUtils.lookAhead(builder, NAME_COLON)) {
      ParserUtils.eatElement(builder, PNAME);
      return true;
    }
    return false;
  }

  public static boolean parseRdfLiteral(PsiBuilder builder) {
    if (ParserUtils.getToken(builder, LIT_STRING)) {
      if (ParserUtils.getToken(builder, OP_HATHAT)) {
        if (!parseIriRef(builder)) {
          builder.error("Expecting IRIref");
        }
      } else {
        ParserUtils.getToken(builder, LANGTAG);
      }
      return true;
    }
    return false;
  }

  public static boolean parseNumericLiteral(PsiBuilder builder) {
    return parseNumericLiteralUnsigned(builder) ||
            parseNumericLiteralNeg(builder) ||
            parseNumericLiteralPos(builder);
  }

  public static boolean parseNumericLiteralUnsigned(PsiBuilder builder) {
    return ParserUtils.getToken(builder, LIT_INTEGER) ||
            ParserUtils.getToken(builder, LIT_DECIMAL) ||
            ParserUtils.getToken(builder, LIT_DOUBLE);
  }

  public static boolean parseNumericLiteralPos(PsiBuilder builder) {
    return ParserUtils.getToken(builder, LIT_INTEGER_POS) ||
            ParserUtils.getToken(builder, LIT_DECIMAL_POS) ||
            ParserUtils.getToken(builder, LIT_DOUBLE_POS);
  }

  public static boolean parseNumericLiteralNeg(PsiBuilder builder) {
    return ParserUtils.getToken(builder, LIT_INTEGER_NEG) ||
            ParserUtils.getToken(builder, LIT_DECIMAL_NEG) ||
            ParserUtils.getToken(builder, LIT_DOUBLE_NEG);
  }

  public static boolean parseBooleanLiteral(PsiBuilder builder) {
    return ParserUtils.getToken(builder, LIT_TRUE) ||
            ParserUtils.getToken(builder, LIT_FALSE);
  }

  public static boolean parseBlankNode(PsiBuilder builder) {
    return ParserUtils.getToken(builder, LIT_BLANK_NODE) ||
            ParserUtils.getToken(builder, LIT_ANON);
  }

  public static boolean parseNil(PsiBuilder builder) {
    return ParserUtils.getToken(builder, LIT_NIL);
  }

  public static boolean parseVarOrIriRef(PsiBuilder builder) {
    return parseVar(builder) ||
            parseIriRef(builder);
  }

  public static boolean parseVar(PsiBuilder builder) {
    return parseVar(builder, false);
  }

  public static boolean parseVar(PsiBuilder builder, boolean reference) {
    if (ParserUtils.lookAhead(builder, VAR)) {
      ParserUtils.eatElement(builder, reference ? VAR_REFERENCE : VAR_DECLARATION);
      return true;
    }
    return false;
  }

  public static boolean parseVarOrTerm(PsiBuilder builder) {
    if (parseVar(builder)) {
      return true;
    } else if (Graphs.parseGraphTerm(builder)) {
      return true;
    }
    return false;
  }

  public static boolean parseCollection(PsiBuilder builder) {
    if (ParserUtils.lookAhead(builder, OP_LROUND)) {
      final PsiBuilder.Marker collection = builder.mark();
      if (ParserUtils.getToken(builder, OP_LROUND)) {

        if (GraphNode.parse(builder)) {
          //noinspection StatementWithEmptyBody
          while (GraphNode.parse(builder)) ;
        } else {
          builder.error("Expecting Graph Node");
        }

        ParserUtils.getToken(builder, OP_RROUND, "Expecting ')'");
      }
      collection.done(COLLECTION);
      return true;
    }
    return false;
  }
}
