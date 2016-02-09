package org.fundaciobit.genapp.common.events;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;


/**
 * Title:        Rapit Entity Bean 2010
 * Description:
 * Copyright:    Copyright (c) 2010
 * Company:      XmasSoft
 * @author Xmas
 * @version 1.0
 */
public class ModificationManager <K extends Serializable> {
  
  List<ModificationListener<K>> listeners = new ArrayList<ModificationListener<K>>();
  

  public void addListener(ModificationListener<K> newListener) {
    listeners.add(newListener);
  }
  
  public boolean removeListener(ModificationListener<K> listener) {
    return listeners.remove(listener);
  }
  
  public boolean emptyListeners() {
    return listeners.isEmpty();
  }

  
  public long preInsert(K obj) throws Exception {
    ModificationEvent<K> me = generateEvent(obj,EventType.PRE_INSERT);
    for (ModificationListener<K> ml : listeners) {
      ml.preInsert(me);
    }
    return me.id;
  }
  
  public void postInsert(long id, K obj) {
    ModificationEvent<K> me = generateEvent(id, obj,EventType.POST_INSERT);
    for (ModificationListener<K> ml : listeners) {
      try {
        ml.postInsert(me);
      } catch(Throwable th) {
        th.printStackTrace(System.err);
      }
    }
  }
  
  public long preList(ListEventInfo lei) throws Exception {
    ModificationEvent<ListEventInfo> me = generateEvent(lei, EventType.PRE_LIST);
    for (ModificationListener<K> ml : listeners) {
      ml.preList(me);
    }
    return me.id;
  }
  
  public void postList(long id, ListEventInfo lei) {
    ModificationEvent<ListEventInfo> me = generateEvent(id, lei,EventType.POST_LIST);
    for (ModificationListener<K> ml : listeners) {
      try {
        ml.postList(me);
      } catch(Throwable th) {
        th.printStackTrace(System.err);
      }
    }
  }
/*
  public long preFieldUpdate(UpdateEventInfo uei) throws Exception {
    ModificationEvent<UpdateEventInfo> me = generateEvent(uei, EventType.PRE_UPDATE);
    for (ModificationListener<K> ml : listeners) {
      ml.preFieldUpdate(me);
    }
    return me.id;
  }

  public void postFieldUpdate(long id, UpdateEventInfo uei) {
    ModificationEvent<UpdateEventInfo> me = generateEvent(id, uei, EventType.POST_UPDATE);
    for (ModificationListener<K> ml : listeners) {
      try {
        ml.postFieldUpdate(me);
      } catch(Throwable th) {
        th.printStackTrace(System.err);
      }
    }
  }
  */
  
  public long preFullUpdate(K bean) throws Exception {
    ModificationEvent<K> me = generateEvent(bean, EventType.PRE_UPDATE);
    for (ModificationListener<K> ml : listeners) {
      ml.preFullUpdate(me);
    }
    return me.id;
  }

  public void postFullUpdate(long id, K bean) {
    ModificationEvent<K> me = generateEvent(id, bean, EventType.POST_UPDATE);
    for (ModificationListener<K> ml : listeners) {
      try {
        ml.postFullUpdate(me);
      } catch(Throwable th) {
        th.printStackTrace(System.err);
      }
    }
  }
  

  public long preDelete(K obj) throws Exception {
    ModificationEvent<K> me = generateEvent(obj,EventType.PRE_DELETE);
    for (ModificationListener<K> ml : listeners) {
      ml.preDelete(me);
    }
    return me.id;
  }

  public void postDelete(long id, K obj) {
    ModificationEvent<K> me = generateEvent(id, obj,EventType.POST_DELETE);
    for (ModificationListener<K> ml : listeners) {
      try {
        ml.postDelete(me);
      } catch(Throwable th) {
        th.printStackTrace(System.err);
      }
    }
  }
  
  
  public void deleteError(long id, K obj, Throwable th) {
    ModificationEvent<K> me = generateEvent(id, obj,EventType.DELETE_ERROR);
    for (ModificationListener<K> ml : listeners) {
      try {
        ml.deleteError(me, th);
      } catch(Throwable ex) {
        th.printStackTrace(System.err);
      }
    }
  }
  
  public void insertError(long id, K obj, Throwable th) {
    ModificationEvent<K> me = generateEvent(id, obj,EventType.INSERT_ERROR);
    for (ModificationListener<K> ml : listeners) {
      try {
        ml.insertError(me, th);
      } catch(Throwable ex) {
        th.printStackTrace(System.err);
      }
    }
  }
  
  public void updateError(long id, K obj, Throwable th) {
    ModificationEvent<K> me = generateEvent(id, obj,EventType.UPDATE_ERROR);
    for (ModificationListener<K> ml : listeners) {
      try {
        ml.fullUpdateError(me, th);
      } catch(Throwable ex) {
        th.printStackTrace(System.err);
      }
    }
  }
  
  
  
  

  private static <M extends Serializable> ModificationEvent<M>
    generateEvent(M obj, EventType type) {
    long id = generateID();
    return generateEvent(id,  obj, type);
  }
  
  
  private static final Random random = new Random();

  private static long generateID() {
    return Math.abs(random.nextLong());
  }
  

  private static <M extends Serializable> ModificationEvent<M>
    generateEvent(long id, M obj, EventType type) {
    String user = "UNKNOWN"; //REB2010JspBase.GLOBAL_USERNAMES.get();
    java.sql.Timestamp date;
    date = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
    return new ModificationEvent<M>(id, type, user, date, obj);
  }
  
}
