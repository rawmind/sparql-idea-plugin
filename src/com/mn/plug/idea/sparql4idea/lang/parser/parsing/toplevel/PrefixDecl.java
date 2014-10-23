package com.mn.plug.idea.sparql4idea.lang.parser.parsing.toplevel;

import com.intellij.lang.PsiBuilder;
import com.mn.plug.idea.sparql4idea.lang.parser.parsing.lit.Literals;
import com.mn.plug.idea.sparql4idea.lang.parser.parsing.util.ParserUtils;

import static com.mn.plug.idea.sparql4idea.lang.Sparql.KW_PREFIX;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.PREFIX_DECL;

/**
 * Prefix decl
 *
 * @author Matt Nathan
 */
public class PrefixDecl {

  public static boolean parse(PsiBuilder builder) {
    if (ParserUtils.lookAhead(builder, KW_PREFIX)) {
      final PsiBuilder.Marker prefixDecl = builder.mark();

      if (ParserUtils.getToken(builder, KW_PREFIX, "Expecing \"PREFIX\"")) {
        if (Literals.parsePNameNs(builder)) {
          if (!Literals.parseIri(builder)) {
            builder.error("Expecting Iri");
          }
        } else {
          builder.error("Expecting prefix namespace (e.g. \"myName:\")");
        }
      }

      prefixDecl.done(PREFIX_DECL);

      return true;
    }
    return false;
  }
}
