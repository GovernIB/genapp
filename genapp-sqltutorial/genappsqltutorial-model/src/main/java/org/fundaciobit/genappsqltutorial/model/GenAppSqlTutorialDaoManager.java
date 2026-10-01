package org.fundaciobit.genappsqltutorial.model;

public class GenAppSqlTutorialDaoManager {
  
  private static IGenAppSqlTutorialDaoManagers instance = null;
  
  public static void setDaoManagers(IGenAppSqlTutorialDaoManagers managers) {
    instance = managers;
  }
  
  public static IGenAppSqlTutorialDaoManagers getDaoManagers() throws Exception {
    if(instance == null) {
      throw new Exception("Ha de inicialitzar el sistema de Managers cridant "
          + " al mètode GenAppSqlTutorialDaoManager.setDaoManagers(...)");
    }
    return instance;
  }
  
}
