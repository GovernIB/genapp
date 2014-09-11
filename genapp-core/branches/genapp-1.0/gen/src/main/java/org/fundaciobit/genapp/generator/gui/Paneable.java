package org.fundaciobit.genapp.generator.gui;

import javax.swing.JPanel;

/**
 * Title:        Rapit Entity Bean 2010
 * Description:
 * Copyright:    Copyright (c) 2014
 * Company:      XmasSoft
 * @author anadal
 * @version 1.0
 */

public abstract class Paneable extends JPanel
{
   /**
   * 
   */
  private static final long serialVersionUID = 2936101524280202083L;

  /** Indica si estando en este panel puedemos pasar al siguiente */
   public abstract boolean next();

   /** Indica si estando en el panel sguienete podemos volver a este */
   public abstract boolean previus();

   /** Indica si en este punto podemos finalizar la transaccion */
   public abstract boolean isFinalizable();

   public abstract boolean initPanel();
   
   /**
    * 
    * @return true, then exit application, false , maintain the windows open
    */
   public boolean finish() { return true; };

}