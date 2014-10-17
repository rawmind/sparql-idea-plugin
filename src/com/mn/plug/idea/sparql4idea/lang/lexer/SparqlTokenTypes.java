package com.mn.plug.idea.sparql4idea.lang.lexer;

import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;

/**
 * Set of lexer token types
 *
 * @author Matt Nathan
 */
public interface SparqlTokenTypes {

  IElementType BAD_CHARACTER = TokenType.BAD_CHARACTER;
  IElementType WHITE_SPACE = TokenType.WHITE_SPACE;
  IElementType UNKNOWN = new SparqlElementType("UNKNOWN");
  IElementType COMMENT = new SparqlElementType("COMMENT");

  // keywords
  IElementType KW_BASE = new SparqlElementType("KW_BASE");
  IElementType KW_PREFIX = new SparqlElementType("KW_PREFIX");
  IElementType KW_SELECT = new SparqlElementType("KW_SELECT");
  IElementType KW_CONSTRUCT = new SparqlElementType("KW_CONSTRUCT");
  IElementType KW_DESCRIBE = new SparqlElementType("KW_DESCRIBE");
  IElementType KW_ASK = new SparqlElementType("KW_ASK");
  IElementType KW_ORDER = new SparqlElementType("KW_ORDER");
  IElementType KW_BY = new SparqlElementType("KW_BY");
  IElementType KW_ASC = new SparqlElementType("KW_ASC");
  IElementType KW_DESC = new SparqlElementType("KW_DESC");
  IElementType KW_LIMIT = new SparqlElementType("KW_LIMIT");
  IElementType KW_OFFSET = new SparqlElementType("KW_OFFSET");
  IElementType KW_DISTINCT = new SparqlElementType("KW_DISTINCT");
  IElementType KW_REDUCED = new SparqlElementType("KW_REDUCED");
  IElementType KW_FROM = new SparqlElementType("KW_FROM");
  IElementType KW_NAMED = new SparqlElementType("KW_NAMED");
  IElementType KW_WHERE = new SparqlElementType("KW_WHERE");
  IElementType KW_GRAPH = new SparqlElementType("KW_GRAPH");
  IElementType KW_OPTIONAL = new SparqlElementType("KW_OPTIONAL");
  IElementType KW_UNION = new SparqlElementType("KW_UNION");
  IElementType KW_FILTER = new SparqlElementType("KW_FILTER");
  IElementType KW_A = new SparqlElementType("KW_A");
  IElementType KW_STR = new SparqlElementType("KW_STR");
  IElementType KW_LANG = new SparqlElementType("KW_LANG");
  IElementType KW_LANGMATCHES = new SparqlElementType("KW_LANGMATCHES");
  IElementType KW_DATATYPE = new SparqlElementType("KW_DATATYPE");
  IElementType KW_BOUND = new SparqlElementType("KW_BOUND");
  IElementType KW_SAME_TERM = new SparqlElementType("KW_SAME_TERM");
  IElementType KW_IS_URI = new SparqlElementType("KW_IS_URI");
  IElementType KW_IS_IRI = new SparqlElementType("KW_IS_IRI");
  IElementType KW_IS_BLANK = new SparqlElementType("KW_IS_BLANK");
  IElementType KW_IS_LITERAL = new SparqlElementType("KW_IS_LITERAL");
  IElementType KW_REGEX = new SparqlElementType("KW_REGEX");
  // sparql 1.1
  IElementType KW_INSERT = new SparqlElementType("KW_INSERT");
  IElementType KW_DELETE = new SparqlElementType("KW_DELETE");

  IElementType LIT_TRUE = new SparqlElementType("true");
  IElementType LIT_FALSE = new SparqlElementType("false");
  IElementType LIT_STRING = new SparqlElementType("STRING");
  IElementType LIT_INTEGER = new SparqlElementType("INTEGER");
  IElementType LIT_DECIMAL = new SparqlElementType("DECIMAL");
  IElementType LIT_DOUBLE = new SparqlElementType("DOUBLE");
  IElementType LIT_INTEGER_POS = new SparqlElementType("+INTEGER");
  IElementType LIT_DECIMAL_POS = new SparqlElementType("+DECIMAL");
  IElementType LIT_DOUBLE_POS = new SparqlElementType("+DOUBLE");
  IElementType LIT_INTEGER_NEG = new SparqlElementType("-INTEGER");
  IElementType LIT_DECIMAL_NEG = new SparqlElementType("-DECIMAL");
  IElementType LIT_DOUBLE_NEG = new SparqlElementType("-DOUBLE");
  IElementType LIT_IRI = new SparqlElementType("IRI");
  IElementType LIT_IRI_START = new SparqlElementType("IRI_START");
  IElementType LIT_IRI_BODY = new SparqlElementType("IRI_BODY");
  IElementType LIT_IRI_END = new SparqlElementType("IRI_END");
  IElementType LIT_PNAME_LN = new SparqlElementType("PNAME_NS");
  IElementType LIT_PNAME_NS = new SparqlElementType("PNAME_LN");
  IElementType LIT_BLANK_NODE = new SparqlElementType("BLANK_NODE");
  IElementType LIT_ANON = new SparqlElementType("ANON");
  IElementType LIT_NIL = new SparqlElementType("NIL");
  IElementType VAR = new SparqlElementType("VAR");
  IElementType LANGTAG = new SparqlElementType("LANGTAG");

  IElementType NAME_NS = new SparqlElementType("NAME_NS");
  IElementType NAME_COLON = new SparqlElementType("NAME_COLON");
  IElementType NAME_LN = new SparqlElementType("NAME_LN");

  IElementType OP_LROUND = new SparqlElementType("(");
  IElementType OP_RROUND = new SparqlElementType(")");
  IElementType OP_LSQUARE = new SparqlElementType("[");
  IElementType OP_RSQUARE = new SparqlElementType("]");
  IElementType OP_LCURLY = new SparqlElementType("{");
  IElementType OP_RCURLY = new SparqlElementType("}");
  IElementType OP_DOT = new SparqlElementType(".");
  IElementType OP_SEMI = new SparqlElementType(";");
  IElementType OP_COMMA = new SparqlElementType(",");
  IElementType OP_PIPEPIPE = new SparqlElementType("||");
  IElementType OP_ANDAND = new SparqlElementType("&&");
  IElementType OP_EQ = new SparqlElementType("=");
  IElementType OP_NE = new SparqlElementType("!=");
  IElementType OP_LT = new SparqlElementType("<");
  IElementType OP_GT = new SparqlElementType(">");
  IElementType OP_LE = new SparqlElementType("<=");
  IElementType OP_GE = new SparqlElementType(">=");
  IElementType OP_PLUS = new SparqlElementType("+");
  IElementType OP_MINUS = new SparqlElementType("-");
  IElementType OP_MULT = new SparqlElementType("*");
  IElementType OP_DIV = new SparqlElementType("/");
  IElementType OP_NOT = new SparqlElementType("!");
  IElementType OP_HATHAT = new SparqlElementType("^^");
}
