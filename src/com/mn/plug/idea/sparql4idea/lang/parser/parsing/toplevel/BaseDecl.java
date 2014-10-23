package com.mn.plug.idea.sparql4idea.lang.parser.parsing.toplevel;

import com.intellij.lang.PsiBuilder;
import com.mn.plug.idea.sparql4idea.lang.parser.parsing.lit.Literals;
import com.mn.plug.idea.sparql4idea.lang.parser.parsing.util.ParserUtils;

import static com.mn.plug.idea.sparql4idea.lang.Sparql.BASE_DECL;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.KW_BASE;


/**
 * BASE decl parser
 *
 * @author Matt Nathan
 */
public class BaseDecl {

  public static boolean parse(PsiBuilder builder) {
    if (ParserUtils.lookAhead(builder, KW_BASE)) {
      final PsiBuilder.Marker baseDecl = builder.mark();

      if (ParserUtils.getToken(builder, KW_BASE, "Expecing \"BASE\"")) {
        if (!Literals.parseIri(builder)) {
          builder.error("Expecting Iri");
        }
      }

      baseDecl.done(BASE_DECL);
      return true;
    }
    return false;
  }
}
