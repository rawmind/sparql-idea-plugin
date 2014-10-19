package com.mn.plug.idea.sparql4idea.core;

import java.io.Serializable;
import java.net.URI;

/**
 * @author Andrey Kovrov
 */
public class DbLink implements Serializable {

  public final URI uri;
  public final String name;

  public DbLink(URI uri, String name) {
    this.uri = uri;
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }
}
