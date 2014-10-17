package com.mn.plug.idea.sparql4idea.core;

import org.apache.commons.lang.StringUtils;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.http.HTTPRepository;

public class SparqlClient {

    final HTTPRepository repository;
    public static final String DEFAULT_REPOSITORY = "http://127.1:8080/openrdf-sesame/repositories/entitystore";
    public final String url;
    public SparqlClient(String url) {
        this.url = StringUtils.isBlank(url) ? DEFAULT_REPOSITORY : url;
        this.repository = connect(this.url);
    }

    public RepositoryConnection getConnection(){
        RepositoryConnection connection = null;
        try {
            connection = repository.getConnection();
        } catch (RepositoryException e) {
            System.err.println(e.getMessage());
        }
        return connection;
    }


    private HTTPRepository connect(String repositoryUrl) {
        HTTPRepository repository = new HTTPRepository(repositoryUrl);
        try {
            repository.initialize();
        } catch (RepositoryException e) {
            System.err.println(e.getMessage());
        }
        return repository;
    }
}
