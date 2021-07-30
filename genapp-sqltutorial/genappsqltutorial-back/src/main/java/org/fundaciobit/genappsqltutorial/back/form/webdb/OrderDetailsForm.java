package org.fundaciobit.genappsqltutorial.back.form.webdb;

import java.util.List;
import org.fundaciobit.genapp.common.StringKeyValue;
import org.fundaciobit.genappsqltutorial.back.form.GenAppSqlTutorialBaseForm;
import org.fundaciobit.genappsqltutorial.persistence.OrderDetailsJPA;

/**
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * Created by GenApp. Do not modify.
 */
public class OrderDetailsForm extends GenAppSqlTutorialBaseForm {
  
  private OrderDetailsJPA orderDetails;
  
  public OrderDetailsForm() {
  }
  
  public OrderDetailsForm(OrderDetailsForm __toClone) {
    super(__toClone);
      this.orderDetails = __toClone.orderDetails;
    this.listOfOrdersForOrderid = __toClone.listOfOrdersForOrderid;
    this.listOfProductsForProductid = __toClone.listOfProductsForProductid;
  }
  
  public OrderDetailsForm(OrderDetailsJPA orderDetails, boolean nou) {
    super(nou);
    this.orderDetails = orderDetails;
  }
  
  public OrderDetailsJPA getOrderDetails() {
    return orderDetails;
  }
  public void setOrderDetails(OrderDetailsJPA orderDetails) {
    this.orderDetails = orderDetails;
  }
  
  
  private List<StringKeyValue> listOfOrdersForOrderid;

  public List<StringKeyValue> getListOfOrdersForOrderid() {
    return this.listOfOrdersForOrderid;
  }

  public void setListOfOrdersForOrderid(List<StringKeyValue> listOfOrdersForOrderid) {
    this.listOfOrdersForOrderid = listOfOrdersForOrderid;
  }



  private List<StringKeyValue> listOfProductsForProductid;

  public List<StringKeyValue> getListOfProductsForProductid() {
    return this.listOfProductsForProductid;
  }

  public void setListOfProductsForProductid(List<StringKeyValue> listOfProductsForProductid) {
    this.listOfProductsForProductid = listOfProductsForProductid;
  }



  
} // Final de Classe 
