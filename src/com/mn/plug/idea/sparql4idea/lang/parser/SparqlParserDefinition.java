package com.mn.plug.idea.sparql4idea.lang.parser;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import com.mn.plug.idea.sparql4idea.SparqlFileType;
import com.mn.plug.idea.sparql4idea.lang.lexer.SparqlLexer;
import com.mn.plug.idea.sparql4idea.lang.psi.IriPsiElement;
import com.mn.plug.idea.sparql4idea.lang.psi.PNameExpression;
import com.mn.plug.idea.sparql4idea.lang.psi.PNameNsDeclaration;
import com.mn.plug.idea.sparql4idea.lang.psi.SparqlFileImpl;
import com.mn.plug.idea.sparql4idea.lang.psi.VariableList;
import com.mn.plug.idea.sparql4idea.lang.psi.expressions.VariableDeclaration;
import com.mn.plug.idea.sparql4idea.lang.psi.expressions.VariableReference;
import com.mn.plug.idea.sparql4idea.lang.psi.graph.GraphNode;
import com.mn.plug.idea.sparql4idea.lang.psi.graph.TripleBlock;
import com.mn.plug.idea.sparql4idea.lang.psi.graph.TripleProperty;
import com.mn.plug.idea.sparql4idea.lang.psi.query.SelectQuery;
import com.mn.plug.idea.sparql4idea.lang.psi.query.WhereClause;
import com.mn.plug.idea.sparql4idea.lang.psi.toplevel.PrefixDeclaration;
import com.mn.plug.idea.sparql4idea.lang.psi.toplevel.PrefixDeclarations;
import org.jetbrains.annotations.NotNull;

import static com.mn.plug.idea.sparql4idea.lang.Sparql.CURRENT;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.GRAPH_NODE;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.LIT_IRI;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.NAME_NS;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.PNAME;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.PREFIX_DECL;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.PREFIX_DECLS;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.PROJECTION_VARIABLES;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.SELECT_QUERY;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.TRIPLES_BLOCK;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.TRIPLE_PROPERTY;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.VAR_DECLARATION;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.VAR_REFERENCE;
import static com.mn.plug.idea.sparql4idea.lang.Sparql.WHERE_CLAUSE;

/**
 * Parser definition for SPARQL.
 *
 * @author Matt Nathan
 */
public class SparqlParserDefinition implements ParserDefinition {

  private static final IFileElementType SPARQL_FILE_TYPE = new IFileElementType("SPARQL", SparqlFileType.SPARQL_LANGUAGE);

  @NotNull
  @Override
  public Lexer createLexer(Project project) {
    return new SparqlLexer();
  }

  @Override
  public PsiParser createParser(Project project) {
    return new SparqlParser();
  }

  @Override
  public IFileElementType getFileNodeType() {
    return SPARQL_FILE_TYPE;
  }

  @NotNull
  @Override
  public TokenSet getWhitespaceTokens() {
    return CURRENT.getWhitespace();
  }

  @NotNull
  @Override
  public TokenSet getCommentTokens() {
    return CURRENT.getComments();
  }

  @NotNull
  @Override
  public TokenSet getStringLiteralElements() {
    return CURRENT.getStringLiterals();
  }

  @NotNull
  @Override
  public PsiElement createElement(ASTNode astNode) {
    if (astNode.getElementType() == PREFIX_DECL) {
      return new PrefixDeclaration(astNode);
    } else if (astNode.getElementType() == VAR_DECLARATION) {
      return new VariableDeclaration(astNode);
    } else if (astNode.getElementType() == VAR_REFERENCE) {
      return new VariableReference(astNode);
    } else if (astNode.getElementType() == LIT_IRI) {
      return new IriPsiElement(astNode);
    } else if (astNode.getElementType() == NAME_NS) {
      return new PNameNsDeclaration(astNode);
    } else if (astNode.getElementType() == PNAME) {
      return new PNameExpression(astNode);
    } else if (astNode.getElementType() == PREFIX_DECLS) {
      return new PrefixDeclarations(astNode);
    } else if (astNode.getElementType() == PROJECTION_VARIABLES) {
      return new VariableList(astNode);
    } else if (astNode.getElementType() == SELECT_QUERY) {
      return new SelectQuery(astNode);
    } else if (astNode.getElementType() == WHERE_CLAUSE) {
      return new WhereClause(astNode);
    } else if (astNode.getElementType() == TRIPLES_BLOCK) {
      return new TripleBlock(astNode);
    } else if (astNode.getElementType() == TRIPLE_PROPERTY) {
      return new TripleProperty(astNode);
    } else if (astNode.getElementType() == GRAPH_NODE) {
      return new GraphNode(astNode);
    }

    return new ASTWrapperPsiElement(astNode);
  }

  @Override
  public PsiFile createFile(FileViewProvider fileViewProvider) {
    return new SparqlFileImpl(fileViewProvider);
  }

  @Override
  public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode astNode, ASTNode astNode1) {
    return SpaceRequirements.MAY;
  }
}
