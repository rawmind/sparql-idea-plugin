package com.mn.plug.idea.sparql4idea.lang.parser.parsing.graph;

import com.intellij.lang.PsiBuilder;
import com.mn.plug.idea.sparql4idea.lang.parser.parsing.common.Filter;
import com.mn.plug.idea.sparql4idea.lang.parser.parsing.util.ParserUtils;

import static com.mn.plug.idea.sparql4idea.lang.Sparql.GROUP_GRAPH_PATTERN;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.OP_DOT;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.OP_LCURLY;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.OP_RCURLY;

/**
 * GroupGraphPattern block
 *
 * @author Matt Nathan
 */
public class GroupGraphPattern {

  public static boolean parse(PsiBuilder builder) {
    if (ParserUtils.lookAhead(builder, OP_LCURLY)) {

      final PsiBuilder.Marker groupGraphPattern = builder.mark();

      if (ParserUtils.getToken(builder, OP_LCURLY, "Expecting '{'")) {

        Triples.parseTriplesBlock(builder); // optional triples block

        while (Graphs.parseGraphPatternNotTriples(builder) || Filter.parse(builder)) {
          ParserUtils.getToken(builder, OP_DOT); // '.'?
          Triples.parseTriplesBlock(builder); // TriplesBlock?
        }

        ParserUtils.getToken(builder, OP_RCURLY, "Expecting '}'");
      }

      groupGraphPattern.done(GROUP_GRAPH_PATTERN);

      return true;
    }
    return false;
  }
}
