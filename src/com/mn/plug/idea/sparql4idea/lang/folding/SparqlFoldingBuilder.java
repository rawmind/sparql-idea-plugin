package com.mn.plug.idea.sparql4idea.lang.folding;

import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingBuilder;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.mn.plug.idea.sparql4idea.lang.Sparql.GROUP_GRAPH_PATTERN;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.PREFIX_DECLS;

/**
 * Folding builder
 *
 * @author Matt Nathan
 */
public class SparqlFoldingBuilder implements FoldingBuilder {

  @NotNull
  @Override
  public FoldingDescriptor[] buildFoldRegions(@NotNull ASTNode node, @NotNull Document document) {
    List<FoldingDescriptor> descriptors = new ArrayList<FoldingDescriptor>();
    appendDescriptors(node, descriptors);
    return descriptors.toArray(new FoldingDescriptor[descriptors.size()]);
  }

  private void appendDescriptors(ASTNode node, List<FoldingDescriptor> descriptors) {
    if (node == null) {
      return;
    }
    IElementType type = node.getElementType();
    if (type == GROUP_GRAPH_PATTERN && isMultiline(node)) {
      descriptors.add(new FoldingDescriptor(node, node.getTextRange()));
    } else if (type == PREFIX_DECLS && isMultiline(node)) {
      descriptors.add(new FoldingDescriptor(node, node.getTextRange()));
    }

    final PsiElement psi = node.getPsi();
    if (psi != null) {
      PsiElement child = psi.getFirstChild();
      while (child != null) {
        appendDescriptors(child.getNode(), descriptors);
        child = child.getNextSibling();
      }
    }
  }

  private boolean isMultiline(ASTNode node) {
    final PsiElement psi = node.getPsi();
    if (psi == null) {
      return false;
    }
    final String text = psi.getText();
    return text.contains("\n") || text.contains("\r") || text.contains("\r\n");
  }

  @Override
  public String getPlaceholderText(@NotNull ASTNode node) {
    if (node.getElementType() == GROUP_GRAPH_PATTERN) {
      return "{ ... }";
    } else if (node.getElementType() == PREFIX_DECLS) {
      return "PREFIX ...";
    }
    return null;
  }

  @Override
  public boolean isCollapsedByDefault(@NotNull ASTNode node) {
    return node.getElementType() == PREFIX_DECLS;
  }
}
