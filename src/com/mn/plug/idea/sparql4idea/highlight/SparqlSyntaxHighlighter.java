package com.mn.plug.idea.sparql4idea.highlight;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import com.mn.plug.idea.sparql4idea.lang.lexer.SparqlLexer;
import com.mn.plug.idea.sparql4idea.lang.lexer.SparqlTokenTypeSets;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import static com.mn.plug.idea.sparql4idea.lang.lexer.SparqlTokenTypes.BAD_CHARACTER;
import static com.mn.plug.idea.sparql4idea.lang.lexer.SparqlTokenTypes.WHITE_SPACE;

/**
 * Syntax highlighter.
 *
 * @author Matt Nathan
 */
public class SparqlSyntaxHighlighter extends SyntaxHighlighterBase {

  private static final Map<IElementType, TextAttributesKey> ATTRIBUTES = new HashMap<IElementType, TextAttributesKey>();

  static {
    // setup the styles
    ATTRIBUTES.put(BAD_CHARACTER, HighlighterColors.BAD_CHARACTER);
    ATTRIBUTES.put(WHITE_SPACE, HighlighterColors.TEXT);
    fillMap(ATTRIBUTES, SparqlTokenTypeSets.KEYWORDS, DefaultLanguageHighlighterColors.KEYWORD);
    fillMap(ATTRIBUTES, SparqlTokenTypeSets.NUMBER_LITERALS, DefaultLanguageHighlighterColors.NUMBER);
    fillMap(ATTRIBUTES, SparqlTokenTypeSets.STRING_LITERALS, DefaultLanguageHighlighterColors.STRING);
    fillMap(ATTRIBUTES, SparqlTokenTypeSets.COMMENTS, DefaultLanguageHighlighterColors.LINE_COMMENT);
  }

  @NotNull
  @Override
  public Lexer getHighlightingLexer() {
    return new SparqlLexer();
  }

  @NotNull
  @Override
  public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
    return pack(ATTRIBUTES.get(tokenType));
  }
}
