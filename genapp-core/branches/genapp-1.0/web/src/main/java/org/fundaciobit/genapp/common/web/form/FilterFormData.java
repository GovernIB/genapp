package org.fundaciobit.genapp.common.web.form;

import java.util.Map;

import org.fundaciobit.genapp.common.query.OrderBy;
import org.fundaciobit.genapp.common.query.Where;
/**
 * 
 * @author anadal
 *
 */
public class FilterFormData {

  
  final Where where;
  
  final OrderBy[] orderBy;
  
  final Map<String, Object> mav;

  /**
   * @param itemsPerPage
   * @param where
   * @param orderBy
   * @param mav
   */
  public FilterFormData(Where where, OrderBy[] orderBy,
      Map<String, Object> mav) {
    super();
    this.where = where;
    this.orderBy = orderBy;
    this.mav = mav;
  }


  public Where getWhere() {
    return where;
  }

  public OrderBy[] getOrderBy() {
    return orderBy;
  }

  public Map<String, Object> getMav() {
    return mav;
  }
  

}
