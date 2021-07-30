package org.fundaciobit.genappsqltutorial.back.form.webdb;

import java.util.List;
import org.fundaciobit.genapp.common.StringKeyValue;
import org.fundaciobit.genappsqltutorial.back.form.GenAppSqlTutorialBaseForm;
import org.fundaciobit.genappsqltutorial.persistence.OrdersJPA;

/**
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * Created by GenApp. Do not modify.
 */
public class OrdersForm extends GenAppSqlTutorialBaseForm {
  
  private OrdersJPA orders;
  
  public OrdersForm() {
  }
  
  public OrdersForm(OrdersForm __toClone) {
    super(__toClone);
      this.orders = __toClone.orders;
    this.listOfCustomersForCustomerid = __toClone.listOfCustomersForCustomerid;
    this.listOfEmployeesForEmployeeid = __toClone.listOfEmployeesForEmployeeid;
    this.listOfSuppliersForShipperid = __toClone.listOfSuppliersForShipperid;
  }
  
  public OrdersForm(OrdersJPA orders, boolean nou) {
    super(nou);
    this.orders = orders;
  }
  
  public OrdersJPA getOrders() {
    return orders;
  }
  public void setOrders(OrdersJPA orders) {
    this.orders = orders;
  }
  
  
  private List<StringKeyValue> listOfCustomersForCustomerid;

  public List<StringKeyValue> getListOfCustomersForCustomerid() {
    return this.listOfCustomersForCustomerid;
  }

  public void setListOfCustomersForCustomerid(List<StringKeyValue> listOfCustomersForCustomerid) {
    this.listOfCustomersForCustomerid = listOfCustomersForCustomerid;
  }



  private List<StringKeyValue> listOfEmployeesForEmployeeid;

  public List<StringKeyValue> getListOfEmployeesForEmployeeid() {
    return this.listOfEmployeesForEmployeeid;
  }

  public void setListOfEmployeesForEmployeeid(List<StringKeyValue> listOfEmployeesForEmployeeid) {
    this.listOfEmployeesForEmployeeid = listOfEmployeesForEmployeeid;
  }



  private List<StringKeyValue> listOfSuppliersForShipperid;

  public List<StringKeyValue> getListOfSuppliersForShipperid() {
    return this.listOfSuppliersForShipperid;
  }

  public void setListOfSuppliersForShipperid(List<StringKeyValue> listOfSuppliersForShipperid) {
    this.listOfSuppliersForShipperid = listOfSuppliersForShipperid;
  }



  
} // Final de Classe 
