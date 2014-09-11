package org.fundaciobit.genapp.common.events;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Title:        Rapit Entity Bean 2010
 * Description:
 * Copyright:    Copyright (c) 2010
 * Company:      XmasSoft
 * @author Xmas
 * @version 1.0
 */
public class ModificationEvent <K extends Serializable> implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = -4278513086891972656L;


  public final long id;
  public final EventType type;
  public final String user;
  public final Timestamp date;
  public final K object;

  /**
   * @param id
   * @param type
   * @param user
   * @param date
   * @param object
   */
  public ModificationEvent(long id, EventType type, String user, Timestamp date,
      K object) {
    super();
    this.id = id;
    this.type = type;
    this.user = user;
    this.date = date;
    this.object = object;
  }

  
  
}
