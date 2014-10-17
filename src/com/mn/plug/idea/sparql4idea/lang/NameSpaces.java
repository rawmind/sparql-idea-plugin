package com.mn.plug.idea.sparql4idea.lang;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NameSpaces {

    private static final Map<String, String> defaultNamespaces = new HashMap<String, String>();

    static {
        defaultNamespaces.put("w-o", "http://entities.wiley.com/objects/");
        defaultNamespaces.put("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
        defaultNamespaces.put("w-m", "http://model.wiley.com/ontologies/EntityStore.owl#");
        defaultNamespaces.put("geo-pos", "http://www.w3.org/2003/01/geo/wgs84_pos#");
        defaultNamespaces.put("umbel-ac", "http://umbel.org/umbel/ac/");
        defaultNamespaces.put("sw-vocab", "http://www.w3.org/2003/06/sw-vocab-status/ns#");
        defaultNamespaces.put("ff", "http://factforge.net/");
        defaultNamespaces.put("music-ont", "http://purl.org/ontology/mo/");
        defaultNamespaces.put("opencyc-en", "http://sw.opencyc.org/2008/06/10/concept/en/");
        defaultNamespaces.put("om", "http://www.ontotext.com/owlim/");
        defaultNamespaces.put("dc-term", "http://purl.org/dc/terms/");
        defaultNamespaces.put("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
        defaultNamespaces.put("factbook", "http://www.daml.org/2001/12/factbook/factbook-ont#");
        defaultNamespaces.put("pext", "http://proton.semanticweb.org/protonext#");
        defaultNamespaces.put("ot", "http://www.ontotext.com/");
        defaultNamespaces.put("dc", "http://purl.org/dc/elements/1.1/");
        defaultNamespaces.put("onto", "http://www.ontotext.com/");
        defaultNamespaces.put("foaf", "http://xmlns.com/foaf/0.1/");
        defaultNamespaces.put("yago", "http://mpii.de/yago/resource/");
        defaultNamespaces.put("umbel", "http://umbel.org/umbel#");
        defaultNamespaces.put("pkm", "http://proton.semanticweb.org/protonkm#");
        defaultNamespaces.put("wordnet16", "http://xmlns.com/wordnet/1.6/");
        defaultNamespaces.put("owl", "http://www.w3.org/2002/07/owl#");
        defaultNamespaces.put("gr", "http://purl.org/goodrelations/v1#");
        defaultNamespaces.put("wordnet", "http://www.w3.org/2006/03/wn/wn20/instances/");
        defaultNamespaces.put("opencyc", "http://sw.opencyc.org/concept/");
        defaultNamespaces.put("wordn-sc", "http://www.w3.org/2006/03/wn/wn20/schema/");
        defaultNamespaces.put("nytimes", "http://data.nytimes.com/");
        defaultNamespaces.put("dbp-prop", "http://dbpedia.org/property/");
        defaultNamespaces.put("geonames", "http://sws.geonames.org/");
        defaultNamespaces.put("dbpedia", "http://dbpedia.org/resource/");
        defaultNamespaces.put("oasis", "http://psi.oasis-open.org/iso/639/#");
        defaultNamespaces.put("geo-ont", "http://www.geonames.org/ontology#");
        defaultNamespaces.put("umbel-en", "http://umbel.org/umbel/ne/wikipedia/");
        defaultNamespaces.put("ptop", "http://proton.semanticweb.org/protontop#");
        defaultNamespaces.put("bbc-pont", "http://purl.org/ontology/po/");
        defaultNamespaces.put("lingvoj", "http://www.lingvoj.org/ontology#");
        defaultNamespaces.put("PostalAddress", "http://schema.org/PostalAddress#");
        defaultNamespaces.put("fb", "http://rdf.freebase.com/ns/");
        defaultNamespaces.put("dbtune", "http://dbtune.org/bbc/peel/work/");
        defaultNamespaces.put("psys", "http://proton.semanticweb.org/protonsys#");
        defaultNamespaces.put("umbel-sc", "http://umbel.org/umbel/sc/");
        defaultNamespaces.put("dct", "http://purl.org/dc/terms/");
        defaultNamespaces.put("dbp-ont", "http://dbpedia.org/ontology/");
        defaultNamespaces.put("ub", "http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#");
        defaultNamespaces.put("xsd", "http://www.w3.org/2001/XMLSchema#");
        defaultNamespaces.put("skos", "http://www.w3.org/2004/02/skos/core#");
    }

    public String normalize(String uri) {
        for (Map.Entry<String, String> entry : defaultNamespaces.entrySet()) {
            String prefix = entry.getValue();
            if (StringUtils.startsWith(uri, prefix)) {
                StringBuilder builder = new StringBuilder(uri);
                builder.replace(0, prefix.length(), entry.getKey() + ":");
                return builder.toString();
            }
        }
        return uri;
    }

    public List<String> asPrefixList(String... filter) {
        boolean withoutFiltering = ArrayUtils.isEmpty(filter);
        List<String> prefixes = new ArrayList<String>(defaultNamespaces.size());
        for (Map.Entry<String, String> entry : defaultNamespaces.entrySet()) {
            if (withoutFiltering) {
                prefixes.add(String.format("PREFIX %s: <%s>", entry.getKey(), entry.getValue()));
            }
        }
        return prefixes;
    }

}
