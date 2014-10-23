package com.mn.plug.idea.sparql4idea.lang.completition;

import com.intellij.codeInsight.lookup.LookupElement;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrey Kovrov
 */
public class BasicDictionary {

  static String[] BUILD_IN_SPARQL_1_1 = new String[]{
          "STR()",
          "LANG()",
          "LANGMATCHES()",
          "DATATYPE()",
          "BOUND()",
          "IRI",
          "URI",
          "BNODE",
          "RAND",
          "ABS",
          "CEIL",
          "FLOOR",
          "ROUND",
          "CONCAT",
          "STRLEN",
          "UCASE",
          "LCASE",
          "ENCODE_FOR_URI",
          "CONTAINS",
          "STRSTARTS",
          "STRENDS",
          "STRBEFORE",
          "STRAFTER",
          "YEAR",
          "MONTH",
          "DAY",
          "HOURS",
          "MINUTES",
          "SECONDS",
          "TIMEZONE",
          "TZ",
          "NOW",
          "UUID",
          "STRUUID",
          "MD5",
          "SHA1()",
          "SHA256()",
          "SHA384()",
          "SHA512()",
          "COALESCE()",
          "STRLANG()",
          "STRDT()",
          "sameTerm()",
          "isIRI()",
          "isURI()",
          "isBLANK()",
          "isLITERAL()",
          "isNUMERIC()",
          "COUNT",
          "MIN",
          "MAX",
          "SAMPLE",
          "SUM",
          "GROUP_CONCAT",
          "AVG"
  };

  static String[] KW_SPARQL_1_1 = new String[]{
          "BASE",
          "SELECT",
          "PREFIX",
          "DISTINCT",
          "REDUCED",
          "CONSTRUCT",
          "DESCRIBE",
          "ASK",
          "FROM",
          "NAMED",
          "WHERE",
          "GROUP BY",
          "GROUP BY",
          "HAVING",
          "AS",
          "ORDER BY",
          "ASC",
          "DESC",
          "LIMIT",
          "OFFSET",
          "VALUES",
          "DELETE",
          "INSERT",
          "USING",
          "OPTIONAL",
          "BIND",
          "MINUS",
          "FILTER",
          "UNION",
          "DISTINCT",
  };

  public static List<LookupElement> LOOKUP_ELEMENTS_SPARQL_1_1 = new ArrayList<LookupElement>();

  static {
    for (final String s : BUILD_IN_SPARQL_1_1) {
      LOOKUP_ELEMENTS_SPARQL_1_1.add(new LookupElement() {
        @NotNull
        @Override
        public String getLookupString() {
          return s.toLowerCase();
        }

      });
    }

    for (final String s : BUILD_IN_SPARQL_1_1) {
      LOOKUP_ELEMENTS_SPARQL_1_1.add(new LookupElement() {
        @NotNull
        @Override
        public String getLookupString() {
          return s;
        }

      });
    }
    for (final String s : KW_SPARQL_1_1) {
      LOOKUP_ELEMENTS_SPARQL_1_1.add(new LookupElement() {
        @NotNull
        @Override
        public String getLookupString() {
          return s.toLowerCase();
        }
      });
    }
    for (final String s : KW_SPARQL_1_1) {
      LOOKUP_ELEMENTS_SPARQL_1_1.add(new LookupElement() {
        @NotNull
        @Override
        public String getLookupString() {
          return s;
        }
      });
    }
  }

}
