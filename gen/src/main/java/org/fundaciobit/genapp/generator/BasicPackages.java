package org.fundaciobit.genapp.generator;

/**
 * 
 * @author anadal
 * 
 */

public class BasicPackages {
  public final String modelPackage;
  public final String entityPackage;
  public final String daoPackage;
  public final String fieldsPackage;
  public final String beanPackage;

  public BasicPackages(String packageName) {

    modelPackage = packageName + ".model";
    entityPackage = modelPackage + ".entity";
    daoPackage = modelPackage + ".dao";
    fieldsPackage = modelPackage + ".fields";
    beanPackage = modelPackage + ".bean";
  }
}
