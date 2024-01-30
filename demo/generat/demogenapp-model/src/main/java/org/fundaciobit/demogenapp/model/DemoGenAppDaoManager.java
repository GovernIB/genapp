package org.fundaciobit.demogenapp.model;

public class DemoGenAppDaoManager {
  
  private static IDemoGenAppDaoManagers instance = null;
  
  public static void setDaoManagers(IDemoGenAppDaoManagers managers) {
    instance = managers;
  }
  
  public static IDemoGenAppDaoManagers getDaoManagers() throws Exception {
    if(instance == null) {
      throw new Exception("Ha de inicialitzar el sistema de Managers cridant "
          + " al mètode DemoGenAppDaoManager.setDaoManagers(...)");
    }
    return instance;
  }
  
}
