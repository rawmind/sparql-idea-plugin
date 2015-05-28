package com.mn.plug.idea.sparql4idea.client;

import com.intellij.openapi.diagnostic.Logger;
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

  private static final Logger LOG =
          Logger.getInstance("#com.mn.plug.idea.sparql4idea.client.SparqlClient");

  private static final DbLink[] remotesLinks = new DbLink[]{
          new DbLink(URI.create("http://dbpedia.org/sparql"), "dbPedia"),
  };

  public static final List<DbLink> DEFAULT_REPOSITORIES = Collections.unmodifiableList(Arrays.asList(remotesLinks));

  public final String url;
  public long connectionTime;

  public SparqlClient(String url) {
    this.url = StringUtils.isBlank(url) ? DEFAULT_REPOSITORIES.get(0).uri.toString() : url;
    StopWatch connectionTimeWatch = new StopWatch();
    connectionTimeWatch.start();
    this.repository = connect(this.url);
    connectionTimeWatch.stop();
    connectionTime = connectionTimeWatch.getTime();
  }

  public Result readQuery(String query) {
    try {

      StopWatch stopWatch = new StopWatch();
      stopWatch.start();
      TupleQuery tupleQuery =
              getConnection().prepareTupleQuery(QueryLanguage.SPARQL, query);
      TupleQueryResult evaluate = tupleQuery.evaluate();
      stopWatch.stop();
      return new TupleResult(evaluate, stopWatch.getTime(), connectionTime);

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
      Update update = getConnection().prepareUpdate(QueryLanguage.SPARQL, query);
      StopWatch queryTimeWatch = new StopWatch();
      queryTimeWatch.start();
      update.execute();
      queryTimeWatch.stop();
      return new EmptyResult(queryTimeWatch.getTime(), connectionTime);

    } catch (RepositoryException e) {
      return new TupleResult(e.getMessage());
    } catch (MalformedQueryException e) {
      return new TupleResult(e.getMessage());
    } catch (UpdateExecutionException e) {
      return new TupleResult(e.getMessage());
    }
  }

  private RepositoryConnection getConnection() throws RepositoryException {
    if (connection == null) {
      connection = repository.getConnection();
    }
    return connection;
  }

  private HTTPRepository connect(String repositoryUrl) {
    HTTPRepository repository = new HTTPRepository(repositoryUrl);
    try {
      repository.initialize();
      connection = repository.getConnection();
    } catch (RepositoryException e) {
      LOG.error("Can't connect to database:", e.getMessage());
    }
    return repository;
  }
}
