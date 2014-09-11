package org.fundaciobit.genapp.generator.gui;


import java.io.File;

import org.fundaciobit.genapp.Project;


/**
 * Title:        Rapit Entity Bean 2010
 * Description:
 * Copyright:    Copyright (c) 2014
 * Company:      XmasSoft
 * @author anadal
 * @version 1.0
 */
public class SharedData  {
  
  public enum ProjectType { NEW, UPDATE, OPEN, GENERATE };

  private static final long serialVersionUID = -6992336889553331811L;
  
  public static ProjectType project;

  public static Project data = null;

  public static File projectFile = null;

}
