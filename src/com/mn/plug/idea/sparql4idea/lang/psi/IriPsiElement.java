package com.mn.plug.idea.sparql4idea.lang.psi;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

import static com.mn.plug.idea.sparql4idea.lang.Sparql.LIT_IRI_BODY;

/**
 * Generated JavaDoc Comment.
 *
 * @author Matt Nathan
 */
public class IriPsiElement extends ASTWrapperPsiElement {

  public IriPsiElement(@NotNull ASTNode node) {
    super(node);
  }

  public String getIri() {
    final PsiElement iri = findChildByType(LIT_IRI_BODY);
    if (iri == null) {
      return "";
    }
    return iri.getText();
  }

  @Override
  public String toString() {
    return "IriElement(" + getIri() + ")";
  }
}
