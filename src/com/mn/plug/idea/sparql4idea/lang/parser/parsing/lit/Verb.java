package com.mn.plug.idea.sparql4idea.lang.parser.parsing.lit;

import com.intellij.lang.PsiBuilder;
import com.mn.plug.idea.sparql4idea.lang.parser.parsing.util.ParserUtils;

import static com.mn.plug.idea.sparql4idea.lang.Sparql.KW_A;


/**
 * Verb parser
 *
 * @author Matt Nathan
 */
public class Verb {
  public static boolean parse(PsiBuilder builder) {
    return Literals.parseVarOrIriRef(builder) ||
            ParserUtils.getToken(builder, KW_A);
  }
}
