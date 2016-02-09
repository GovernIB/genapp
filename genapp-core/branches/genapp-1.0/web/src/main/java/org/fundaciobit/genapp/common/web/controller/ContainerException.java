package org.fundaciobit.genapp.common.web.controller;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genapp.common.web.i18n.I18NUtils;

//TODO Moure de GENAPP com a MultipleException
/**
 * 
 */
public class ContainerException extends Exception {

  /**
 * 
 */
  private static final long serialVersionUID = -3058343662140399916L;

  private List<I18NException> innerExceptions = new ArrayList<I18NException>();

  // some constructors

  public void add(I18NException e) {
    innerExceptions.add(e);
  }

  public Collection<I18NException> getExceptions() {
    return innerExceptions;
  }

  @Override
  public String getMessage() {
    StringBuffer str = new StringBuffer();
    for (I18NException e : innerExceptions) {
      str.append(I18NUtils.getMessage(e)).append('\n');
    }
    return str.toString();
  }

  @Override
  public String getLocalizedMessage() {
    return getMessage();
  }

  @Override
  public void printStackTrace() {
    this.printStackTrace(System.err);
  }

  @Override
  public void printStackTrace(PrintWriter pw) {
    if (pw == null) {
      return;
    }
    for (I18NException e : innerExceptions) {
      e.printStackTrace(pw);
    }

  }

  @Override
  public void printStackTrace(PrintStream ps) {
    if (ps == null) {
      return;
    }
    for (I18NException e : innerExceptions) {
      e.printStackTrace(ps);
    }
  }

}
