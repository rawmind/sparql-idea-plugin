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

  // SPARQL keywords
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

  // SPARQL synonyms
  IElementType KW_A = new SparqlElementType("KW_A");

  // SPARQL 1.1
  IElementType KW_INSERT = new SparqlElementType("KW_INSERT");
  IElementType KW_DELETE = new SparqlElementType("KW_DELETE");
  IElementType KW_DATA = new SparqlElementType("KW_DATA");
  IElementType KW_DROP = new SparqlElementType("KW_DROP");
  IElementType KW_SILENT = new SparqlElementType("KW_SILENT");
  IElementType KW_DEFAULT = new SparqlElementType("KW_DEFAULT");
  IElementType KW_ALL = new SparqlElementType("KW_ALL");
  IElementType KW_CREATE = new SparqlElementType("KW_CREATE");
  IElementType KW_CLEAR = new SparqlElementType("KW_CLEAR");
  IElementType KW_LOAD = new SparqlElementType("KW_LOAD");
  IElementType KW_MOVE = new SparqlElementType("KW_MOVE");
  IElementType KW_ADD = new SparqlElementType("KW_ADD");
  IElementType KW_WITH = new SparqlElementType("KW_WITH");
  IElementType KW_USING = new SparqlElementType("KW_USING");
  IElementType KW_COPY = new SparqlElementType("KW_COPY");
  IElementType KW_EXISTS = new SparqlElementType("KW_EXISTS");
  IElementType KW_NOT = new SparqlElementType("KW_NOT");
  IElementType KW_MINUS = new SparqlElementType("KW_MINUS");
  IElementType KW_HAVING = new SparqlElementType("KW_HAVING");
  IElementType KW_GROUP = new SparqlElementType("KW_GROUP");
  IElementType KW_BIND = new SparqlElementType("KW_BIND");
  IElementType KW_SERVICE = new SparqlElementType("KW_SERVICE");
  IElementType KW_VALUES = new SparqlElementType("KW_VALUES");

  // Functions on numerics
  IElementType KW_ABS = new SparqlElementType("KW_ABS");
  IElementType KW_ROUND = new SparqlElementType("KW_ROUND");
  IElementType KW_CEIL = new SparqlElementType("KW_CEIL");
  IElementType KW_FLOOR = new SparqlElementType("KW_FLOOR");
  IElementType KW_RAND = new SparqlElementType("KW_RAND");

  //Functions on strings
  IElementType KW_STRLEN = new SparqlElementType("KW_STRLEN");
  IElementType KW_SUBSTR = new SparqlElementType("KW_SUBSTR");
  IElementType KW_UCASE = new SparqlElementType("KW_UCASE");
  IElementType KW_LCASE = new SparqlElementType("KW_LCASE");
  IElementType KW_STRSTARTS = new SparqlElementType("KW_STRSTARTS");
  IElementType KW_STRENDS = new SparqlElementType("KW_STRENDS");
  IElementType KW_CONTAINS = new SparqlElementType("KW_CONTAINS");
  IElementType KW_STRBEFORE = new SparqlElementType("KW_STRBEFORE");
  IElementType KW_STRAFTER = new SparqlElementType("KW_STRAFTER");
  IElementType KW_ENCODE_FOR_URI = new SparqlElementType("KW_ENCODE_FOR_URI");
  IElementType KW_CONCAT = new SparqlElementType("KW_CONCAT");
  IElementType KW_LANGMATCHES = new SparqlElementType("KW_LANGMATCHES");
  IElementType KW_REGEX = new SparqlElementType("KW_REGEX");
  IElementType KW_REPLACE = new SparqlElementType("KW_REPLACE");

  //Functions on dates and times
  IElementType KW_NOW = new SparqlElementType("KW_NOW");
  IElementType KW_YEAR = new SparqlElementType("KW_YEAR");
  IElementType KW_MONTH = new SparqlElementType("KW_MONTH");
  IElementType KW_DAY = new SparqlElementType("KW_DAY");
  IElementType KW_HOURS = new SparqlElementType("KW_HOURS");
  IElementType KW_MINUTES = new SparqlElementType("KW_MINUTES");
  IElementType KW_SECONDS = new SparqlElementType("KW_SECONDS");
  IElementType KW_TIMEZONE = new SparqlElementType("KW_TIMEZONE");
  IElementType KW_TZ = new SparqlElementType("KW_TZ");

  // Hash functions
  IElementType KW_MD5 = new SparqlElementType("KW_MD5");
  IElementType KW_SHA1 = new SparqlElementType("KW_SHA1");
  IElementType KW_SHA256 = new SparqlElementType("KW_SHA256");
  IElementType KW_SHA384 = new SparqlElementType("KW_SHA384");
  IElementType KW_SHA512 = new SparqlElementType("KW_SHA512");

  //Functions on RDF terms
  IElementType KW_IS_NUMERIC = new SparqlElementType("IS_NUMERIC");
  IElementType KW_STR = new SparqlElementType("KW_STR");
  IElementType KW_BNODE = new SparqlElementType("KW_BNODE");
  IElementType KW_STRDT = new SparqlElementType("KW_STRDT");
  IElementType KW_STRLANG = new SparqlElementType("KW_STRLANG");
  IElementType KW_UUID = new SparqlElementType("KW_UUID");
  IElementType KW_STRUUID = new SparqlElementType("KW_STRUUID");
  IElementType KW_LANG = new SparqlElementType("KW_LANG");
  IElementType KW_DATATYPE = new SparqlElementType("KW_DATATYPE");
  IElementType KW_SAME_TERM = new SparqlElementType("KW_SAME_TERM");
  IElementType KW_IS_URI = new SparqlElementType("KW_IS_URI");
  IElementType KW_IS_IRI = new SparqlElementType("KW_IS_IRI");
  IElementType KW_IS_BLANK = new SparqlElementType("KW_IS_BLANK");
  IElementType KW_IS_LITERAL = new SparqlElementType("KW_IS_LITERAL");

  // Functional forms
  IElementType KW_BOUND = new SparqlElementType("KW_BOUND");
  IElementType KW_IF = new SparqlElementType("KW_IF");
  IElementType KW_COALESCE = new SparqlElementType("KW_COALESCE");
  IElementType KW_IN = new SparqlElementType("KW_IN");

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
