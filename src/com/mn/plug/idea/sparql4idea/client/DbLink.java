package com.mn.plug.idea.sparql4idea.client;

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

  @SuppressWarnings("RedundantIfStatement")
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    DbLink dbLink = (DbLink) o;

    if (name != null ? !name.equals(dbLink.name) : dbLink.name != null) return false;
    if (uri != null ? !uri.equals(dbLink.uri) : dbLink.uri != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = uri != null ? uri.hashCode() : 0;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    return result;
  }
}
