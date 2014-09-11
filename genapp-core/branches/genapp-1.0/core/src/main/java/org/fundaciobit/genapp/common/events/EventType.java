package org.fundaciobit.genapp.common.events;

import java.io.Serializable;
/**
 * Title:        Rapit Entity Bean 2010
 * Description:
 * Copyright:    Copyright (c) 2010
 * Company:      XmasSoft
 * @author Xmas
 * @version 1.0
 */
public enum EventType implements Serializable {
    PRE_INSERT,
    POST_INSERT,
    PRE_LIST,
    POST_LIST,
    PRE_UPDATE,
    POST_UPDATE,
    PRE_DELETE,
    POST_DELETE,
    INSERT_ERROR,
    UPDATE_ERROR,
    DELETE_ERROR
    
}
