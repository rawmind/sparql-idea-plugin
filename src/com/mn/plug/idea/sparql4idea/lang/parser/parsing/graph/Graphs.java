package com.mn.plug.idea.sparql4idea.lang.parser.parsing.graph;

import com.intellij.lang.PsiBuilder;
import com.mn.plug.idea.sparql4idea.lang.parser.parsing.lit.Literals;
import com.mn.plug.idea.sparql4idea.lang.parser.parsing.util.ParserUtils;

import static com.mn.plug.idea.sparql4idea.lang.Sparql.GRAPH_GRAPH;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.KW_GRAPH;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.KW_OPTIONAL;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.KW_UNION;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.OPTIONAL_GRAPH;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.UNION_GRAPH;

/**
 * Graph related parsing methods
 *
 * @author Matt Nathan
 */
public class Graphs {

  public static boolean parseGraphTerm(PsiBuilder builder) {
    return Literals.parseIriRef(builder) ||
            Literals.parseRdfLiteral(builder) ||
            Literals.parseNumericLiteral(builder) ||
            Literals.parseBooleanLiteral(builder) ||
            Literals.parseBlankNode(builder) ||
            Literals.parseNil(builder);
  }

  public static boolean parseGraphPatternNotTriples(PsiBuilder builder) {
    return parseOptionalGraphPattern(builder) ||
            parseGroupOrUnionGraphPattern(builder) ||
            parseGraphGraphPattern(builder);
  }

  private static boolean parseGraphGraphPattern(PsiBuilder builder) {
    if (ParserUtils.lookAhead(builder, KW_GRAPH)) {
      final PsiBuilder.Marker graphGraphPattern = builder.mark();
      if (ParserUtils.getToken(builder, KW_GRAPH)) {
        if (!Literals.parseVarOrIriRef(builder)) {
          builder.error("Expecting VarOrIriRef");
        } else {
          if (!GroupGraphPattern.parse(builder)) {
            builder.error("Expecting GroupGraphPattern");
          }
        }
      }
      graphGraphPattern.done(GRAPH_GRAPH);
      return true;
    }
    return false;
  }

  private static boolean parseGroupOrUnionGraphPattern(PsiBuilder builder) {
    if (GroupGraphPattern.parse(builder)) {
      while (ParserUtils.lookAhead(builder, KW_UNION)) {
        final PsiBuilder.Marker unionGraph = builder.mark();
        if (ParserUtils.getToken(builder, KW_UNION)) {
          if (!GroupGraphPattern.parse(builder)) {
            builder.error("Expecting GroupGraphPattern");
          }
        }
        unionGraph.done(UNION_GRAPH);
      }
      return true;
    }
    return false;
  }

  private static boolean parseOptionalGraphPattern(PsiBuilder builder) {
    if (ParserUtils.lookAhead(builder, KW_OPTIONAL)) {
      final PsiBuilder.Marker optionalGraph = builder.mark();
      if (ParserUtils.getToken(builder, KW_OPTIONAL)) {
        GroupGraphPattern.parse(builder);
      }
      optionalGraph.done(OPTIONAL_GRAPH);
      return true;
    }
    return false;
  }
}
