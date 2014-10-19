package com.mn.plug.idea.sparql4idea.core;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.StopWatch;
import org.openrdf.query.*;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.http.HTTPRepository;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SparqlClient {

  final HTTPRepository repository;
  RepositoryConnection connection;

  private static final DbLink[] remotesLinks = new DbLink[]{
          new DbLink(URI.create("http://dbpedia.org/sparql"), "dbPedia"),
  };

  public static final List<DbLink> DEFAULT_REPOSITORIES = Collections.unmodifiableList(Arrays.asList(remotesLinks));

  public final String url;

  public SparqlClient(String url) {
    this.url = StringUtils.isBlank(url) ? DEFAULT_REPOSITORIES.get(0).uri.toString() : url;
    this.repository = connect(this.url);
  }

  public Result readQuery(String query) {
    try {
      connection = getConnection();
      StopWatch stopWatch = new StopWatch();
      stopWatch.start();
      TupleQuery tupleQuery =
              connection.prepareTupleQuery(QueryLanguage.SPARQL, query);
      TupleQueryResult evaluate = tupleQuery.evaluate();
      stopWatch.stop();
      return new TupleResult(evaluate, stopWatch.getTime());

    } catch (RepositoryException e) {
      return new TupleResult(e.getMessage());
    } catch (QueryEvaluationException e) {
      return new TupleResult(e.getMessage());
    } catch (MalformedQueryException e) {
      return new TupleResult(e.getMessage());
    }
  }

  public Result writeQuery(String query) {
    try {
      getConnection();
      Update update = connection.prepareUpdate(QueryLanguage.SPARQL, query);
      StopWatch stopWatch = new StopWatch();
      stopWatch.start();
      update.execute();
      stopWatch.stop();
      return new EmptyResult(stopWatch.getTime());

    } catch (RepositoryException e) {
      return new TupleResult(e.getMessage());
    } catch (MalformedQueryException e) {
      return new TupleResult(e.getMessage());
    } catch (UpdateExecutionException e) {
      return new TupleResult(e.getMessage());
    }
  }

  private RepositoryConnection getConnection() throws RepositoryException {
    if (connection == null /*|| !connection.isActive()*/) {
      connection = repository.getConnection();
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
