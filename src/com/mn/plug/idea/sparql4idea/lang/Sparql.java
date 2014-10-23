package com.mn.plug.idea.sparql4idea.lang;

import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.mn.plug.idea.sparql4idea.lang.lexer.SparqlElementType;

/**
 * @author Andrey Kovrov
 */
public enum Sparql {

  V_20130321 {
    @Override
    public TokenSet getKeywords() {
      return TokenSet.create(SPARQL_KEYWORDS);
    }

    @Override
    public TokenSet getComments() {
      return COMMENTS;
    }

  };

  public abstract TokenSet getKeywords();

  public abstract TokenSet getComments();

  public TokenSet getWhitespace() {
    return WHITESPACE;
  }

  public TokenSet getStringLiterals() {
    return STRING_LITERALS;
  }

  public TokenSet getNumberLiterals() {
    return NUMBER_LITERALS;
  }

  // element types
  public static final IElementType BAD_CHARACTER = TokenType.BAD_CHARACTER;
  public static final IElementType WHITE_SPACE = TokenType.WHITE_SPACE;
  public static final IElementType UNKNOWN = new SparqlElementType("UNKNOWN");
  public static final IElementType COMMENT = new SparqlElementType("COMMENT");
  // SPARQL keywords
  public static final IElementType KW_BASE = new SparqlElementType("KW_BASE");
  public static final IElementType KW_PREFIX = new SparqlElementType("KW_PREFIX");
  public static final IElementType KW_SELECT = new SparqlElementType("KW_SELECT");
  public static final IElementType KW_ASK = new SparqlElementType("KW_ASK");
  public static final IElementType KW_CONSTRUCT = new SparqlElementType("KW_CONSTRUCT");
  public static final IElementType KW_DESCRIBE = new SparqlElementType("KW_DESCRIBE");
  public static final IElementType KW_ORDER = new SparqlElementType("KW_ORDER");
  public static final IElementType KW_BY = new SparqlElementType("KW_BY");
  public static final IElementType KW_ASC = new SparqlElementType("KW_ASC");
  public static final IElementType KW_DESC = new SparqlElementType("KW_DESC");
  public static final IElementType KW_LIMIT = new SparqlElementType("KW_LIMIT");
  public static final IElementType KW_OFFSET = new SparqlElementType("KW_OFFSET");
  public static final IElementType KW_DISTINCT = new SparqlElementType("KW_DISTINCT");
  public static final IElementType KW_REDUCED = new SparqlElementType("KW_REDUCED");
  public static final IElementType KW_FROM = new SparqlElementType("KW_FROM");
  public static final IElementType KW_NAMED = new SparqlElementType("KW_NAMED");
  public static final IElementType KW_WHERE = new SparqlElementType("KW_WHERE");
  public static final IElementType KW_GRAPH = new SparqlElementType("KW_GRAPH");
  public static final IElementType KW_OPTIONAL = new SparqlElementType("KW_OPTIONAL");
  public static final IElementType KW_UNION = new SparqlElementType("KW_UNION");
  public static final IElementType KW_FILTER = new SparqlElementType("KW_FILTER");
  // SPARQL synonyms
  public static final IElementType KW_A = new SparqlElementType("KW_A");
  // SPARQL 1.1
  public static final IElementType KW_INSERT = new SparqlElementType("KW_INSERT");
  public static final IElementType KW_DELETE = new SparqlElementType("KW_DELETE");
  public static final IElementType KW_DATA = new SparqlElementType("KW_DATA");
  public static final IElementType KW_DROP = new SparqlElementType("KW_DROP");
  public static final IElementType KW_SILENT = new SparqlElementType("KW_SILENT");
  public static final IElementType KW_DEFAULT = new SparqlElementType("KW_DEFAULT");
  public static final IElementType KW_ALL = new SparqlElementType("KW_ALL");
  public static final IElementType KW_CREATE = new SparqlElementType("KW_CREATE");
  public static final IElementType KW_CLEAR = new SparqlElementType("KW_CLEAR");
  public static final IElementType KW_LOAD = new SparqlElementType("KW_LOAD");
  public static final IElementType KW_MOVE = new SparqlElementType("KW_MOVE");
  public static final IElementType KW_ADD = new SparqlElementType("KW_ADD");
  public static final IElementType KW_WITH = new SparqlElementType("KW_WITH");
  public static final IElementType KW_USING = new SparqlElementType("KW_USING");
  public static final IElementType KW_COPY = new SparqlElementType("KW_COPY");
  public static final IElementType KW_EXISTS = new SparqlElementType("KW_EXISTS");
  public static final IElementType KW_NOT = new SparqlElementType("KW_NOT");
  public static final IElementType KW_MINUS = new SparqlElementType("KW_MINUS");
  public static final IElementType KW_HAVING = new SparqlElementType("KW_HAVING");
  public static final IElementType KW_GROUP = new SparqlElementType("KW_GROUP");
  public static final IElementType KW_BIND = new SparqlElementType("KW_BIND");
  public static final IElementType KW_SERVICE = new SparqlElementType("KW_SERVICE");
  public static final IElementType KW_VALUES = new SparqlElementType("KW_VALUES");
  // Functions on numerics
  public static final IElementType KW_ABS = new SparqlElementType("KW_ABS");
  public static final IElementType KW_ROUND = new SparqlElementType("KW_ROUND");
  public static final IElementType KW_CEIL = new SparqlElementType("KW_CEIL");
  public static final IElementType KW_FLOOR = new SparqlElementType("KW_FLOOR");
  public static final IElementType KW_RAND = new SparqlElementType("KW_RAND");
  //Functions on strings
  public static final IElementType KW_STRLEN = new SparqlElementType("KW_STRLEN");
  public static final IElementType KW_SUBSTR = new SparqlElementType("KW_SUBSTR");
  public static final IElementType KW_UCASE = new SparqlElementType("KW_UCASE");
  public static final IElementType KW_LCASE = new SparqlElementType("KW_LCASE");
  public static final IElementType KW_STRSTARTS = new SparqlElementType("KW_STRSTARTS");
  public static final IElementType KW_STRENDS = new SparqlElementType("KW_STRENDS");
  public static final IElementType KW_CONTAINS = new SparqlElementType("KW_CONTAINS");
  public static final IElementType KW_STRBEFORE = new SparqlElementType("KW_STRBEFORE");
  public static final IElementType KW_STRAFTER = new SparqlElementType("KW_STRAFTER");
  public static final IElementType KW_ENCODE_FOR_URI = new SparqlElementType("KW_ENCODE_FOR_URI");
  public static final IElementType KW_CONCAT = new SparqlElementType("KW_CONCAT");
  public static final IElementType KW_LANGMATCHES = new SparqlElementType("KW_LANGMATCHES");
  public static final IElementType KW_REGEX = new SparqlElementType("KW_REGEX");
  public static final IElementType KW_REPLACE = new SparqlElementType("KW_REPLACE");
  //Functions on dates and times
  public static final IElementType KW_NOW = new SparqlElementType("KW_NOW");
  public static final IElementType KW_YEAR = new SparqlElementType("KW_YEAR");
  public static final IElementType KW_MONTH = new SparqlElementType("KW_MONTH");
  public static final IElementType KW_DAY = new SparqlElementType("KW_DAY");
  public static final IElementType KW_HOURS = new SparqlElementType("KW_HOURS");
  public static final IElementType KW_MINUTES = new SparqlElementType("KW_MINUTES");
  public static final IElementType KW_SECONDS = new SparqlElementType("KW_SECONDS");
  public static final IElementType KW_TIMEZONE = new SparqlElementType("KW_TIMEZONE");
  public static final IElementType KW_TZ = new SparqlElementType("KW_TZ");
  // Hash functions
  public static final IElementType KW_MD5 = new SparqlElementType("KW_MD5");
  public static final IElementType KW_SHA1 = new SparqlElementType("KW_SHA1");
  public static final IElementType KW_SHA256 = new SparqlElementType("KW_SHA256");
  public static final IElementType KW_SHA384 = new SparqlElementType("KW_SHA384");
  public static final IElementType KW_SHA512 = new SparqlElementType("KW_SHA512");
  //Functions on RDF terms
  public static final IElementType KW_IS_NUMERIC = new SparqlElementType("IS_NUMERIC");
  public static final IElementType KW_STR = new SparqlElementType("KW_STR");
  public static final IElementType KW_BNODE = new SparqlElementType("KW_BNODE");
  public static final IElementType KW_STRDT = new SparqlElementType("KW_STRDT");
  public static final IElementType KW_STRLANG = new SparqlElementType("KW_STRLANG");
  public static final IElementType KW_UUID = new SparqlElementType("KW_UUID");
  public static final IElementType KW_STRUUID = new SparqlElementType("KW_STRUUID");
  public static final IElementType KW_LANG = new SparqlElementType("KW_LANG");
  public static final IElementType KW_DATATYPE = new SparqlElementType("KW_DATATYPE");
  public static final IElementType KW_SAME_TERM = new SparqlElementType("KW_SAME_TERM");
  public static final IElementType KW_IS_URI = new SparqlElementType("KW_IS_URI");
  public static final IElementType KW_IS_IRI = new SparqlElementType("KW_IS_IRI");
  public static final IElementType KW_IS_BLANK = new SparqlElementType("KW_IS_BLANK");
  public static final IElementType KW_IS_LITERAL = new SparqlElementType("KW_IS_LITERAL");

  // Functional forms
  public static final IElementType KW_BOUND = new SparqlElementType("KW_BOUND");
  public static final IElementType KW_IF = new SparqlElementType("KW_IF");
  public static final IElementType KW_COALESCE = new SparqlElementType("KW_COALESCE");
  public static final IElementType KW_IN = new SparqlElementType("KW_IN");

  // Aggregate algebra
  public static final IElementType KW_SUM = new SparqlElementType("KW_SUM");
  public static final IElementType KW_COUNT = new SparqlElementType("KW_COUNT");
  public static final IElementType KW_MIN = new SparqlElementType("KW_MIN");
  public static final IElementType KW_MAX = new SparqlElementType("KW_MAX");
  public static final IElementType KW_AVG = new SparqlElementType("KW_AVG");
  public static final IElementType KW_GROUP_CONCAT = new SparqlElementType("KW_GROUP_CONCAT");
  public static final IElementType KW_SAMPLE = new SparqlElementType("KW_SAMPLE");

  // Literals
  public static final IElementType LIT_TRUE = new SparqlElementType("true");
  public static final IElementType LIT_FALSE = new SparqlElementType("false");
  public static final IElementType LIT_STRING = new SparqlElementType("STRING");
  public static final IElementType LIT_INTEGER = new SparqlElementType("INTEGER");
  public static final IElementType LIT_DECIMAL = new SparqlElementType("DECIMAL");
  public static final IElementType LIT_DOUBLE = new SparqlElementType("DOUBLE");
  public static final IElementType LIT_INTEGER_POS = new SparqlElementType("+INTEGER");
  public static final IElementType LIT_DECIMAL_POS = new SparqlElementType("+DECIMAL");
  public static final IElementType LIT_DOUBLE_POS = new SparqlElementType("+DOUBLE");
  public static final IElementType LIT_INTEGER_NEG = new SparqlElementType("-INTEGER");
  public static final IElementType LIT_DECIMAL_NEG = new SparqlElementType("-DECIMAL");
  public static final IElementType LIT_DOUBLE_NEG = new SparqlElementType("-DOUBLE");
  public static final IElementType LIT_IRI = new SparqlElementType("IRI");
  public static final IElementType LIT_IRI_START = new SparqlElementType("IRI_START");
  public static final IElementType LIT_IRI_BODY = new SparqlElementType("IRI_BODY");
  public static final IElementType LIT_IRI_END = new SparqlElementType("IRI_END");
  public static final IElementType LIT_PNAME_LN = new SparqlElementType("PNAME_NS");
  public static final IElementType LIT_PNAME_NS = new SparqlElementType("PNAME_LN");
  public static final IElementType LIT_BLANK_NODE = new SparqlElementType("BLANK_NODE");
  public static final IElementType LIT_ANON = new SparqlElementType("ANON");
  public static final IElementType LIT_NIL = new SparqlElementType("NIL");
  public static final IElementType VAR = new SparqlElementType("VAR");
  public static final IElementType LANGTAG = new SparqlElementType("LANGTAG");

  public static final IElementType NAME_NS = new SparqlElementType("NAME_NS");
  public static final IElementType NAME_COLON = new SparqlElementType("NAME_COLON");
  public static final IElementType NAME_LN = new SparqlElementType("NAME_LN");

  public static final IElementType OP_LROUND = new SparqlElementType("(");
  public static final IElementType OP_RROUND = new SparqlElementType(")");
  public static final IElementType OP_LSQUARE = new SparqlElementType("[");
  public static final IElementType OP_RSQUARE = new SparqlElementType("]");
  public static final IElementType OP_LCURLY = new SparqlElementType("{");
  public static final IElementType OP_RCURLY = new SparqlElementType("}");
  public static final IElementType OP_DOT = new SparqlElementType(".");
  public static final IElementType OP_SEMI = new SparqlElementType(";");
  public static final IElementType OP_COMMA = new SparqlElementType(",");
  public static final IElementType OP_PIPEPIPE = new SparqlElementType("||");
  public static final IElementType OP_ANDAND = new SparqlElementType("&&");
  public static final IElementType OP_EQ = new SparqlElementType("=");
  public static final IElementType OP_NE = new SparqlElementType("!=");
  public static final IElementType OP_LT = new SparqlElementType("<");
  public static final IElementType OP_GT = new SparqlElementType(">");
  public static final IElementType OP_LE = new SparqlElementType("<=");
  public static final IElementType OP_GE = new SparqlElementType(">=");
  public static final IElementType OP_PLUS = new SparqlElementType("+");
  public static final IElementType OP_MINUS = new SparqlElementType("-");
  public static final IElementType OP_MULT = new SparqlElementType("*");
  public static final IElementType OP_DIV = new SparqlElementType("/");
  public static final IElementType OP_NOT = new SparqlElementType("!");
  public static final IElementType OP_HATHAT = new SparqlElementType("^^");

  public static final IElementType BASE_DECL = new SparqlElementType("BASE_DECL");
  public static final IElementType PREFIX_DECL = new SparqlElementType("PREFIX_DECL");
  public static final IElementType PREFIX_DECLS = new SparqlElementType("PREFIX_DECLS");

  public static final IElementType SELECT_QUERY = new SparqlElementType("SELECT_QUERY");
  public static final IElementType PROJECTION_VARIABLES = new SparqlElementType("PROJECTION_VARIABLES");

  public static final IElementType CONSTRUCT_QUERY = new SparqlElementType("CONSTRUCT_QUERY");
  public static final IElementType DESCRIBE_QUERY = new SparqlElementType("DESCRIBE_QUERY");
  public static final IElementType ASK_QUERY = new SparqlElementType("ASK_QUERY");

  public static final IElementType DATASET_CLAUSE = new SparqlElementType("DATASET_CLAUSE");
  public static final IElementType WHERE_CLAUSE = new SparqlElementType("WHERE_CLAUSE");

  public static final IElementType OR_EXPR = new SparqlElementType("OR_EXPR");
  public static final IElementType AND_EXPR = new SparqlElementType("AND_EXPR");
  public static final IElementType RELATIONAL_EXPR = new SparqlElementType("RELATIONAL_EXPR");
  public static final IElementType ADDITIVE_EXPR = new SparqlElementType("ADDITIVE_EXPR");
  public static final IElementType MULTIPLICATIVE_EXPR = new SparqlElementType("MULTIPLICATIVE_EXPR");
  public static final IElementType UNARY_EXPR = new SparqlElementType("UNARY_EXPR");
  public static final IElementType SOLUTION_MODIFIERS = new SparqlElementType("SOLUTION_MODIFIERS");
  public static final IElementType GROUP_GRAPH_PATTERN = new SparqlElementType("GROUP_GRAPH_PATTERN");
  public static final IElementType COLLECTION = new SparqlElementType("COLLECTION");
  public static final IElementType TRIPLE_PROPERTY = new SparqlElementType("TRIPLE_PROPERTY");
  public static final IElementType TRIPLES_BLOCK = new SparqlElementType("TRIPLES_BLOCK");
  public static final IElementType OPTIONAL_GRAPH = new SparqlElementType("OPTIONAL_GRAPH");
  public static final IElementType GRAPH_GRAPH = new SparqlElementType("GRAPH_GRAPH");
  public static final IElementType UNION_GRAPH = new SparqlElementType("UNION_GRAPH");
  public static final IElementType FILTER = new SparqlElementType("FILTER");
  public static final IElementType CONSTRUCT_TEMPLATE = new SparqlElementType("CONSTRUCT_TEMPLATE");

  public static final IElementType PNAME = new SparqlElementType("PNAME");
  public static final IElementType VAR_DECLARATION = new SparqlElementType("VAR_DECLARATION");
  public static final IElementType VAR_REFERENCE = new SparqlElementType("VAR_REFERENCE");
  public static final IElementType GRAPH_NODE = new SparqlElementType("GRAPH_NODE");

  public static final Sparql CURRENT = Sparql.V_20130321;

  public static final IElementType[] SPARQL_KEYWORDS = new IElementType[]{
          KW_BASE, KW_PREFIX, KW_SELECT, KW_CONSTRUCT, KW_DESCRIBE, KW_ASK,
          KW_ORDER, KW_BY, KW_ASC, KW_DESC, KW_LIMIT, KW_OFFSET, KW_DISTINCT,
          KW_REDUCED, KW_FROM, KW_NAMED, KW_WHERE, KW_GRAPH, KW_OPTIONAL, KW_UNION,
          KW_FILTER, KW_A, KW_INSERT, KW_DELETE, KW_DATA, KW_DROP, KW_SILENT,
          KW_DEFAULT, KW_ALL, KW_CREATE, KW_CLEAR, KW_LOAD, KW_MOVE, KW_ADD,
          KW_WITH, KW_USING, KW_COPY, KW_EXISTS, KW_NOT, KW_MINUS, KW_HAVING,
          KW_GROUP, KW_BIND, KW_SERVICE, KW_VALUES, KW_ABS, KW_ROUND, KW_CEIL,
          KW_FLOOR, KW_RAND, KW_STRLEN, KW_SUBSTR, KW_UCASE, KW_LCASE,
          KW_STRSTARTS, KW_STRENDS, KW_CONTAINS, KW_STRBEFORE, KW_STRAFTER,
          KW_ENCODE_FOR_URI, KW_CONCAT, KW_LANGMATCHES, KW_REGEX, KW_REPLACE,
          KW_NOW, KW_YEAR, KW_MONTH, KW_DAY, KW_HOURS, KW_MINUTES, KW_SECONDS,
          KW_TIMEZONE, KW_TZ, KW_MD5, KW_SHA1, KW_SHA256, KW_SHA384, KW_SHA512,
          KW_IS_NUMERIC, KW_STR, KW_BNODE, KW_STRDT, KW_STRLANG, KW_UUID,
          KW_STRUUID, KW_LANG, KW_DATATYPE, KW_SAME_TERM, KW_IS_URI, KW_IS_IRI,
          KW_IS_BLANK, KW_IS_LITERAL, KW_BOUND, KW_IF, KW_COALESCE, KW_IN,
          KW_SUM, KW_COUNT, KW_MIN, KW_MAX, KW_AVG, KW_GROUP_CONCAT, KW_SAMPLE,
          LIT_TRUE, LIT_FALSE
  };

  // token sets
  private static final TokenSet COMMENTS = TokenSet.create(COMMENT);
  private static final TokenSet WHITESPACE = TokenSet.create(WHITE_SPACE);
  private static final TokenSet STRING_LITERALS = TokenSet.create(LIT_STRING);
  private static final TokenSet NUMBER_LITERALS = TokenSet.create(LIT_INTEGER, LIT_DECIMAL, LIT_DOUBLE, LIT_INTEGER_NEG, LIT_INTEGER_POS,
          LIT_DECIMAL_NEG, LIT_DECIMAL_POS, LIT_DOUBLE_NEG, LIT_DOUBLE_POS);

}
