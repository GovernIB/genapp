package org.fundaciobit.genapp.generator;

import java.util.List;
import java.util.Map;

import org.fundaciobit.genapp.FieldInfo;
import org.fundaciobit.genapp.generator.CodeGenerator.BeanFieldCode;
import org.fundaciobit.genapp.generator.CodeGenerator.DaoCommonCode;

/**
 * 
 * @author anadal
 *
 */
public class BeanCode {
  String attributesNonPK;
  String attributesPK;
  Map<FieldInfo, BeanFieldCode> codeByField;
  Constructors constructors;
  List<FieldInfo> pkFields;
  DaoCommonCode daoCommonCode;
}
