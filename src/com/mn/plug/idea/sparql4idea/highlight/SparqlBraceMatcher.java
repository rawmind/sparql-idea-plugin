package com.mn.plug.idea.sparql4idea.highlight;

import com.intellij.lang.BracePair;
import com.intellij.lang.PairedBraceMatcher;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import com.mn.plug.idea.sparql4idea.lang.Sparql;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Brace matcher
 *
 * @author Matt Nathan
 */
public class SparqlBraceMatcher implements PairedBraceMatcher {

  private static final BracePair[] PAIRS = {
          new BracePair(Sparql.OP_LCURLY, Sparql.OP_RCURLY, true),
          new BracePair(Sparql.OP_LROUND, Sparql.OP_RROUND, true),
          new BracePair(Sparql.OP_LSQUARE, Sparql.OP_RSQUARE, false),
          new BracePair(Sparql.LIT_IRI_START, Sparql.LIT_IRI_END, false),
  };

  @Override
  public BracePair[] getPairs() {
    return PAIRS;
  }

  @Override
  public boolean isPairedBracesAllowedBeforeType(@NotNull IElementType lbraceType, @Nullable IElementType contextType) {
    return true;
  }

  @Override
  public int getCodeConstructStart(PsiFile file, int openingBraceOffset) {
    return openingBraceOffset;
  }
}
