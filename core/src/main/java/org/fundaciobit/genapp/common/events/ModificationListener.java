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
public interface ModificationListener <K extends Serializable> {
 
  /**
   * 
   * @param obj
   * @return identifier to link with post event.
   * @throws Exception
   */
  public void preInsert(ModificationEvent<K> event) throws Exception;

  public void postInsert(ModificationEvent<K> event);

  public void preList(ModificationEvent<ListEventInfo> event) throws Exception;

  public void postList(ModificationEvent<ListEventInfo> event);
  

  // public void preFieldUpdate(ModificationEvent<UpdateEventInfo> event) throws Exception;
  

  // public void postFieldUpdate(ModificationEvent<UpdateEventInfo> event);
  
  
  public void preFullUpdate(ModificationEvent<K> event) throws Exception;
  

  public void postFullUpdate(ModificationEvent<K> event);


  public void preDelete(ModificationEvent<K> event) throws Exception;
  
  public void postDelete(ModificationEvent<K> event);

  public void insertError(ModificationEvent<K> event, Throwable th);
  
  public void fullUpdateError(ModificationEvent<K> event, Throwable th);
  
  public void deleteError(ModificationEvent<K> event, Throwable th);

}
