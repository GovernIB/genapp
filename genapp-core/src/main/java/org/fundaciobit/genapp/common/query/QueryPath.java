package org.fundaciobit.genapp.common.query;

/**
 * 
 * @author anadal
 * 
 */
public abstract class QueryPath {

  public final QueryPath parentQueryPath;

  public QueryPath() {
    this.parentQueryPath = null;
  }

  public QueryPath(QueryPath parentQueryPath) {
    this.parentQueryPath = parentQueryPath;
  }

  public abstract String getQueryPath();
}
