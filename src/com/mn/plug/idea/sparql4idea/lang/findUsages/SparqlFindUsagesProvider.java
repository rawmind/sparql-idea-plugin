package com.mn.plug.idea.sparql4idea.lang.findUsages;

import com.intellij.lang.cacheBuilder.DefaultWordsScanner;
import com.intellij.lang.cacheBuilder.WordsScanner;
import com.intellij.lang.findUsages.FindUsagesProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.tree.TokenSet;
import com.mn.plug.idea.sparql4idea.lang.lexer.SparqlLexer;
import com.mn.plug.idea.sparql4idea.lang.psi.PNameNsDeclaration;
import com.mn.plug.idea.sparql4idea.lang.psi.expressions.VariableBase;
import org.jetbrains.annotations.NotNull;

import static com.mn.plug.idea.sparql4idea.lang.Sparql.CURRENT;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.LIT_PNAME_LN;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.LIT_PNAME_NS;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.VAR_DECLARATION;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.VAR_REFERENCE;

/**
 * Find usages provider
 *
 * @author Matt Nathan
 */
public class SparqlFindUsagesProvider implements FindUsagesProvider {

  @Override
  public WordsScanner getWordsScanner() {
    return new DefaultWordsScanner(new SparqlLexer(),
            TokenSet.create(LIT_PNAME_LN, LIT_PNAME_NS, VAR_DECLARATION, VAR_REFERENCE), CURRENT.getComments(),
            TokenSet.andSet(CURRENT.getNumberLiterals(), CURRENT.getStringLiterals()));
  }

  @Override
  public boolean canFindUsagesFor(@NotNull PsiElement psiElement) {
    return psiElement instanceof PNameNsDeclaration || psiElement instanceof VariableBase;
  }

  @Override
  public String getHelpId(@NotNull PsiElement psiElement) {
    return null;
  }

  @NotNull
  @Override
  public String getType(@NotNull PsiElement element) {
    if (element instanceof PNameNsDeclaration) {
      return "Namespace";
    } else if (element instanceof VariableBase) {
      return "Variable";
    }
    return "";
  }

  @NotNull
  @Override
  public String getDescriptiveName(@NotNull PsiElement element) {
    if (element instanceof PsiNamedElement) {
      final String name = ((PsiNamedElement) element).getName();
      return name == null ? element.toString() : name;
    }
    return element.toString();
  }

  @NotNull
  @Override
  public String getNodeText(@NotNull PsiElement element, boolean useFullName) {
    return element.toString();
  }
}
