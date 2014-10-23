package com.mn.plug.idea.sparql4idea.lang.parser.parsing.graph;

import com.intellij.lang.PsiBuilder;
import com.mn.plug.idea.sparql4idea.lang.parser.parsing.common.PropertyList;
import com.mn.plug.idea.sparql4idea.lang.parser.parsing.lit.Literals;
import com.mn.plug.idea.sparql4idea.lang.parser.parsing.util.ParserUtils;

import static com.mn.plug.idea.sparql4idea.lang.Sparql.OP_DOT;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.OP_LSQUARE;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.OP_RSQUARE;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.TRIPLES_BLOCK;

/**
 * Triples statements
 *
 * @author Matt Nathan
 */
public class Triples {

  public static boolean parseTriplesNode(PsiBuilder builder) {
    if (Literals.parseCollection(builder)) {
      return true;
    } else if (parseBlankNodePropertyList(builder)) {
      return true;
    }
    return false;
  }

  public static boolean parseBlankNodePropertyList(PsiBuilder builder) {
    if (ParserUtils.getToken(builder, OP_LSQUARE)) {
      if (!PropertyList.parse(builder)) {
        builder.error("Expecting PropertyList");
      }
      ParserUtils.getToken(builder, OP_RSQUARE, "Expecting ']'");
      return true;
    }
    return false;
  }

  public static boolean parseTriplesBlock(PsiBuilder builder) {
    if (!parseTriplesSameSubject(builder)) {
      return false;
    }

    while (ParserUtils.getToken(builder, OP_DOT)) {
      if (!parseTriplesSameSubject(builder)) {
        break;
      }
    }
    return true;
  }

  private static boolean parseTriplesSameSubject(PsiBuilder builder) {
    final PsiBuilder.Marker triplesBlock = builder.mark();
    if (Literals.parseVarOrTerm(builder)) {
      if (!PropertyList.parse(builder)) {
        builder.error("Expecting PropertyListNotEmpty");
      }
      triplesBlock.done(TRIPLES_BLOCK);
      return true;
    } else if (parseTriplesNode(builder)) {
      PropertyList.parse(builder);
      triplesBlock.done(TRIPLES_BLOCK);
      return true;
    } else {
      triplesBlock.drop();
      return false;
    }
  }
}
