package org.fundaciobit.genapp.common.query;

import java.util.ArrayList;
import java.util.List;

import org.fundaciobit.genapp.common.i18n.I18NException;


/**
 * TODO Classe base per a la generaci� de Where's per a consultes SQL.
 * Title:        Rapit Entity Bean 2010
 * Description:
 * Copyright:    Copyright (c) 2010
 * Company:      XmasSoft
 * @author Xmas
 * @version 1.0
 * 
 */
public abstract class Where {

  /**
   * Mètode d'utilitat per juntar varis Where mitjan�ant una OR 
   * @param wheres
   * @return 
   */
  public static Where OR(Where... wheres) {
    return checkNulls("OR", wheres);
  }

  /**
   * Mètode d'utilitat per juntar varis Where mitjan�ant una AND 
   * @param wheres
   * @return 
   */
  public static Where AND(Where... wheres) {
    return checkNulls("AND", wheres);
  }
  
  private static Where checkNulls(String andor, Where... wheres) {
    if (wheres == null || wheres.length == 0) {
      return null;
    }
    List<Where> nouswhere = new ArrayList<Where>();
    for (Where where : wheres) {
      if (where != null) {
        nouswhere.add(where);
      }
    }
    if (nouswhere.size() == 0) {
      return null;
    } else {
      if (nouswhere.size() == 1) {
        return nouswhere.get(0);
      } else {
        return new AND_OR(andor, nouswhere.toArray(new Where[nouswhere.size()]));
      }
    }
  }
  
  
  public final int setValues(javax.persistence.Query query)  throws I18NException {
    return setValues(query, 1);
  }
  

  /**
   * M�todo per assignar els valors als camps '?' detallats en el SQL.
   * @param ps Instancia de PreparedStatement per afegir els valors
   * @param index Posici� inicial on afeger
   * @return Darrera posici� insertada
   * @throws SQLException Si error
   */
  protected abstract int setValues(javax.persistence.Query query, int index)
      throws I18NException;

  public abstract String toSQL();

  /**
   * 
   * Classe d'utilitat per juntar varis Where mitjan�ant una OR o una AND
   * @author anadal
   * 
   */
  private static class AND_OR extends Where {

    private final Where[] wheres;

    private final String andor;

    /**
     * Constructor.
     * @param andorcadena que pot valer 'AND' o 'OR'
     * @param wheres Llista de where's a juntar. No s'admeten nulls !!!!
     */
    public AND_OR(final String andor, Where... wheres) {
      this.wheres = wheres;
      this.andor = andor;
    }

    @Override
    public String toSQL() {
      StringBuffer sql = new StringBuffer("( ");
      for (int i = 0; i < wheres.length; i++) {        
        if (i != 0) {
          sql.append(' ').append(andor).append(' ');
        }
        sql.append(" ( ").append(wheres[i].toSQL()).append(" ) ");
      }
      sql.append(" )");
      return sql.toString();
    }

    @Override
    public int setValues(javax.persistence.Query query, int index) throws I18NException {
      for (int i = 0; i < wheres.length; i++) {
        index = wheres[i].setValues(query, index);
      }
      return index;
    }

  }

}
