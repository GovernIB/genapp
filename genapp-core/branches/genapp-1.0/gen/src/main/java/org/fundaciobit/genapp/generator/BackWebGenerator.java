package org.fundaciobit.genapp.generator;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.fundaciobit.genapp.FieldInfo;
import org.fundaciobit.genapp.ForeignKey;
import org.fundaciobit.genapp.Project;
import org.fundaciobit.genapp.TableInfo;
import org.fundaciobit.genapp.WebType;
import org.fundaciobit.genapp.common.web.form.BaseFilterForm;



/**
 * 
 * @author anadal
 *
 */
public class BackWebGenerator {

  
  public static List<SourceFile> generateListJsp(String packageControllerBack,
      String packageFormBack, String packageValidatorBack,String jpaPackage,
      String ejbPackage, BasicPackages packages, Project project,
      Map<String,BeanCode> beanCodeBySqlTableName) throws Exception  {
    
    TableInfo[] tables = project.getTables();

    List<SourceFile> sourceFiles = new ArrayList<SourceFile>(tables.length);
    
    for (int i = 0; i < tables.length; i++) {

      TableInfo table = tables[i];
      
      if (!tables[i].isGenerate() || tables[i].isTranslationMapEntity()) {
        continue;
      }
      
      FieldInfo[] fields = table.fields;
      final String tableJavaName = table.getNameJava();
      final String model = CodeGenUtils.getModelName(tableJavaName);
      final String filterForm = tableJavaName + "FilterForm";
      String instanceFilterForm = CodeGenUtils.getModelName(filterForm);
      
      String instanceFields = tableJavaName + "Fields";
      

      String fieldsClass = packages.fieldsPackage + "." + instanceFields;     
      
      
      
      
      
      /*
      final String validator = tableJavaName + "Validator";
      final String form = tableJavaName + "Form";
      final String jpa = tableJavaName + "JPA";
      final String instanceForm = CodeGenUtils.getModelName(form);
      final String local = tableJavaName + "Local";
      final String instanceEjb = model + "Ejb";
      final String instanceValidator = CodeGenUtils.getModelName(validator);
      final String fieldsClass = tableJavaName + "Fields";
      final String controller = tableJavaName + "Controller";
      final String tileForm = model + "Form";
      final String tileList = model + "List";
      */
      
      //BeanCode beanGenCode = beanCodeBySqlTableName.get(tables[i].name);
      
      List<FieldInfo> PKs = CodeGenUtils.getPKs(table.getFields());
      final boolean multiplePKs = (PKs.size() > 1);
      
      String pkMapping;
      if (multiplePKs) {
        pkMapping = "";
        for (FieldInfo fieldInfo : PKs) {
          pkMapping = pkMapping + "/${" + instanceFilterForm + "." + fieldInfo.getJavaName() + "}";
        }
      } else {
        FieldInfo pk = PKs.get(0);
        pkMapping = "/${" +  model + "." + pk.getJavaName() + "}";
      }
      
      
      // =================== CODE FOR BASE LIST  ===============
      // =======================================================
      List<FieldInfo> fileFields = CodeGenUtils.getFileFieldsOfTable(tables, table);
      if (fileFields == null) {
        fileFields = new ArrayList<FieldInfo>();
      }
      {
        StringBuffer code = new StringBuffer();
        code.append("<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>\n");
        code.append("<%@ include file=\"/WEB-INF/jsp/moduls/includes.jsp\"%>\n");
        code.append("\n");
        
        code.append("<form:form name=\"" + model + "\" cssClass=\"form-search\"  modelAttribute=\"" + instanceFilterForm + "\" \n");
        code.append("        method=\"${method}\"  enctype=\"multipart/form-data\">\n");
        code.append("\n");

        code.append("  <%@include file=\"" + model + "ListCommon.jsp\" %>\n");
        
        code.append("  <div class=\"filterLine lead\" style=\"margin-bottom:10px\">\n");
        code.append("    <%@include file=\"" + model + "ListHeaderButtons.jsp\" %>\n");
        code.append("    <%-- ADD HERE NEW HEADER BUTTONS (Multiple Select or similar to add item)  --%>\n\n");
        code.append("  </div>\n");
        code.append("  <%@include file=\"" + model + "ListSubtitle.jsp\" %>\n");

        code.append("  <%@include file=\"" + model + "ListFilterBy.jsp\" %>\n");

        code.append("  <%-- Inici de div d'AGRUPACIO i TAULA CONTINGUTS --%>  \n");
        code.append("  <div>\n");

        code.append("  <%@include file=\"" + model + "ListGroupBy.jsp\" %>\n");

        code.append("  <%-- Inici de div de TAULA CONTINGUTS --%>\n");
        code.append("  <div style=\"width: 100%;\">\n");

        code.append("  <%@include file=\"" + model + "ListCore.jsp\" %>\n");

        code.append("  <c:if test=\"${not empty " + model + "Items}\">\n");
        code.append("          <%@include file=\"webdbPagination.jsp\" %>\n\n");
        code.append("  </c:if>\n");
        code.append("\n");
        code.append("  </div> <%-- Final de div de TAULA CONTINGUTS --%>\n");

        code.append("  <%--  ADD HERE OTHER CONTENT --%>\n\n");
        
        code.append("  <c:if test=\"${__theFilterForm.attachedAdditionalJspCode}\">\n");
        code.append("          <%@include file=\"../webdbmodificable/" + model+ "ListModificable.jsp\" %>\n");
        code.append("  </c:if>\n");
        		
        
        code.append("  \n");
        code.append("  </div> <%-- Final de div d'AGRUPACIO i TAULA CONTINGUTS --%>\n");
                        
        code.append("\n");
        code.append("</form:form> \n");
        code.append("    \n");
        code.append("\n");

        sourceFiles.add(new SourceFile(model + "List.jsp", code.toString()));
  
      }
      
      
      {        
        // =================== CODI COMU  ===============
        // =======================================================
        
        StringBuffer codeCommon = new StringBuffer();
        
        codeCommon.append("<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>\n");
        codeCommon.append("<%@ include file=\"/WEB-INF/jsp/moduls/includes.jsp\"%>\n");
        codeCommon.append("\n");

        codeCommon.append("  <c:set var=\"contexte\" value=\"${" + instanceFilterForm + ".contexte}\"/>\n");
        codeCommon.append("  <c:set var=\"formName\" value=\"" + model + "\" />\n");
        codeCommon.append("  <c:set var=\"__theFilterForm\" value=\"${" + instanceFilterForm + "}\" />\n");
        
        
        codeCommon.append("  <c:if test=\"${empty " + instanceFilterForm + ".entityNameCode}\">\n");
        codeCommon.append("    <fmt:message var=\"entityname\" key=\"" + model + "." + model +"\"/>\n");
        codeCommon.append("  </c:if>\n");
        codeCommon.append("  <c:if test=\"${not empty " + instanceFilterForm + ".entityNameCode}\">\n");
        codeCommon.append("    <fmt:message var=\"entityname\" key=\"${" + instanceFilterForm + ".entityNameCode}\"/>\n");
        codeCommon.append("  </c:if>\n");
        
        codeCommon.append("  <c:if test=\"${empty " + instanceFilterForm + ".entityNameCodePlural}\">\n");
        codeCommon.append("    <fmt:message var=\"entitynameplural\" key=\"" + model + "." + model +"\"/>\n");
        codeCommon.append("  </c:if>\n");
        codeCommon.append("  <c:if test=\"${not empty " + instanceFilterForm + ".entityNameCodePlural}\">\n");
        codeCommon.append("    <fmt:message var=\"entitynameplural\" key=\"${" + instanceFilterForm + ".entityNameCodePlural}\"/>\n");
        codeCommon.append("  </c:if>\n");
        
        codeCommon.append("  <%-- HIDDEN PARAMS: ORDER BY --%> \n");
        codeCommon.append("  <form:hidden id=\"orderBy\" path=\"orderBy\"/> \n");
        codeCommon.append("  <form:hidden id=\"orderAsc\" path=\"orderAsc\"/>\n\n");

        codeCommon.append("  <form:hidden path=\"nou\" value=\"false\"/>\n\n");

        codeCommon.append("<script type=\"text/javascript\">\n");
        codeCommon.append("  function executeOrderBy(orderBy, orderType) {\n");
        codeCommon.append("    document.getElementById('orderBy').value = orderBy;\n");
        codeCommon.append("    document.getElementById('orderAsc').value = orderType;\n");
        codeCommon.append("    document." + model + ".submit();  \n");
        codeCommon.append("  }\n");
        codeCommon.append("</script>\n");    
        
        sourceFiles.add(new SourceFile(model + "ListCommon.jsp", codeCommon.toString()));
        
        instanceFilterForm = "__theFilterForm";
      }
      
      
      // LIST CORE
      {
        StringBuffer code = new StringBuffer();

        code.append("  <c:if test=\"${empty " + model + "Items}\">\n");
        code.append("     <%@include file=\"" + model + "ListEmpty.jsp\" %>\n\n");
        code.append("  </c:if>\n");
        code.append("  \n");
        
        
        code.append("  <c:if test=\"${not empty " + model + "Items}\">\n\n");
        code.append("  <div class=\"row\" style=\"margin-left: 0px;\">\n");
        code.append("  <table class=\"table table-condensed table-bordered table-striped\" style=\"width:auto;\"> \n");
        code.append("    <thead>\n");
        code.append("      <tr>\n\n");
        
        code.append("          <%@include file=\"" + model + "ListCoreHeaderMultipleSelect.jsp\" %>\n\n");

        code.append("          <%@include file=\"" + model + "ListCoreHeader.jsp\" %>\n\n");
        
        
        code.append("          <%-- ADD HERE NEW COLUMNS HEADER  --%>\n\n");
        
        
        code.append("          <%@include file=\"" + model + "ListButtonsHeader.jsp\" %>\n\n");
        
        code.append("      </tr>\n");
        code.append("    </thead>\n");
        code.append("    <tbody>\n\n");
        code.append("      <c:forEach var=\"" + model + "\" items=\"${" + model + "Items}\">\n\n");
        code.append("        <tr>\n");
        
        code.append("          <%@include file=\"" + model + "ListCoreMultipleSelect.jsp\" %>\n\n");
        
        code.append("          <%@include file=\"" + model + "ListCoreContent.jsp\" %>\n\n");
        
        code.append("          <%--  ADD HERE NEW COLUMNS CONTENT --%>\n\n\n");
        
        code.append("          <%@include file=\"" + model + "ListButtons.jsp\" %>\n\n");

        code.append("            \n");
        code.append("        </tr>\n");
        code.append("\n");

        code.append("      </c:forEach>\n");
        code.append("\n");
        code.append("    </tbody>\n");
        code.append("  </table>\n");
        code.append("  </div>\n");
        code.append("  </c:if>\n");        
        code.append("  \n");
        
        
        sourceFiles.add(new SourceFile(model + "ListCore.jsp", code.toString()));
        
        
      }
      
      
      
      
      {
        
        StringBuffer codeMultipleSelect = new StringBuffer();

        codeMultipleSelect.append("      <%--  CHECK DE SELECCIO MULTIPLE  --%>\n");
        codeMultipleSelect.append("      <c:if test=\"${" + instanceFilterForm + ".visibleMultipleSelection}\">\n");
        codeMultipleSelect.append("      <td>\n");
        codeMultipleSelect.append("       <form:checkbox path=\"selectedItems\" value=\"" + pkMapping.substring(1)+ "\"/>\n");
        codeMultipleSelect.append("       &nbsp;\n");
        codeMultipleSelect.append("      </td>\n");
        codeMultipleSelect.append("      </c:if>\n\n");
        
        sourceFiles.add(new SourceFile(model + "ListCoreMultipleSelect.jsp", codeMultipleSelect.toString()));
        
      }
      
      
      
      {
        StringBuffer codeHeaderMultipleSelect = new StringBuffer();

        codeHeaderMultipleSelect.append("      <c:if test=\"${" + instanceFilterForm + ".visibleMultipleSelection}\">\n");
        codeHeaderMultipleSelect.append("      <th>\n");
        codeHeaderMultipleSelect.append("         <input type=\"checkbox\" onClick=\"selectUnselectCheckBoxes(this)\" />\n");
        codeHeaderMultipleSelect.append("      </th>\n");
        codeHeaderMultipleSelect.append("      </c:if>\n");
        
        sourceFiles.add(new SourceFile(model + "ListCoreHeaderMultipleSelect.jsp", codeHeaderMultipleSelect.toString()));
        
      }
      
      
      {
        StringBuffer codeSubtitle = new StringBuffer();

        codeSubtitle.append("<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>\n");
        codeSubtitle.append("<%@ include file=\"/WEB-INF/jsp/moduls/includes.jsp\"%>\n");
        codeSubtitle.append("\n");
        codeSubtitle.append("  <c:if test=\"${not empty " + instanceFilterForm + ".subTitleCode}\">\n");
        codeSubtitle.append("<h5 style=\"line-height: 10px; margin-top: -10px; margin-bottom: 10px;\">\n");
        codeSubtitle.append("<c:set var=\"subtitleTranslated\" value=\"${fn:startsWith(" + instanceFilterForm + ".subTitleCode,'=')}\" />\n"); 
        codeSubtitle.append("<c:if test=\"${subtitleTranslated}\">\n");
        codeSubtitle.append("   <c:out value=\"${fn:substringAfter(" + instanceFilterForm + ".subTitleCode, '=')}\" escapeXml=\"false\" />\n");
        codeSubtitle.append("</c:if>\n");
        codeSubtitle.append("<c:if test=\"${not subtitleTranslated}\">\n");
        codeSubtitle.append("  <fmt:message key=\"${" + instanceFilterForm + ".subTitleCode}\" />\n");     
        codeSubtitle.append("</c:if>\n");
        codeSubtitle.append("</h5>\n");
        

        codeSubtitle.append("  </c:if>\n");

        sourceFiles.add(new SourceFile(model + "ListSubtitle.jsp", codeSubtitle.toString()));
      }




      {
        
        // =================== CODE FOR BUTTONS GROUPBY, ORDER BY i ADD  ===============
        // =======================================================
        
        StringBuffer codeHeaderButtons = new StringBuffer();
        codeHeaderButtons.append("<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>\n");
        codeHeaderButtons.append("<%@ include file=\"/WEB-INF/jsp/moduls/includes.jsp\"%>\n");
        codeHeaderButtons.append("\n");

     
        codeHeaderButtons.append("  <c:if test=\"${not empty " + instanceFilterForm + ".titleCode}\">\n");
        codeHeaderButtons.append("      <fmt:message key=\"${" + instanceFilterForm + ".titleCode}\">\n");
        codeHeaderButtons.append("        <fmt:param value=\"${" + instanceFilterForm + ".titleParam}\" />\n");
        codeHeaderButtons.append("      </fmt:message>\n");
        codeHeaderButtons.append("  </c:if>\n");
        codeHeaderButtons.append("  <c:if test=\"${empty " + instanceFilterForm + ".titleCode}\">\n");        
        codeHeaderButtons.append("    <fmt:message key=\"genapp.listtitle\">\n");
        codeHeaderButtons.append("      <fmt:param value=\"${entitynameplural}\"/>\n");
        codeHeaderButtons.append("    </fmt:message>\n");
        codeHeaderButtons.append("  </c:if>\n");
        codeHeaderButtons.append("\n");
        codeHeaderButtons.append("      <%-- AGRUPAR PER BOTO - INICI  --%>\n");
        codeHeaderButtons.append("  <c:if test=\"${fn:length(groupby_items) > 0}\">\n");  
        codeHeaderButtons.append("      <c:set var=\"displayGroupBut\" value=\"${" + instanceFilterForm + ".visibleGroupBy?'display:none;':''}\" />\n");
        codeHeaderButtons.append("      <a id=\"GroupButton\" style=\"${displayGroupBut}\" title=\"<fmt:message key=\"genapp.form.groupby\"/>\" onclick=\"document.getElementById('GroupDiv').style.display = 'inherit'; document.getElementById('GroupButton').style.display = 'none';\" class=\"btn\" role=\"button\" data-toggle=\"modal\">\n");
        codeHeaderButtons.append("         <img src=\"<c:url value=\"/img/treeicon.png\"/>\"/>\n");
        codeHeaderButtons.append("      </a>\n");
        codeHeaderButtons.append("  </c:if>\n");  
        codeHeaderButtons.append("      <%-- AGRUPAR PER BOTO - FINAL  --%>\n");
        codeHeaderButtons.append("\n");
        codeHeaderButtons.append("      <%-- FILTRAR PER BOTO - INICI  --%>\n");
        codeHeaderButtons.append("      <c:if test=\"${fn:length(" + instanceFilterForm + ".filterByFields) > 0}\">\n");
        codeHeaderButtons.append("      <c:set var=\"displayFilterBut\" value=\"${" + instanceFilterForm + ".visibleFilterBy?'display:none;':''}\" />\n");
        codeHeaderButtons.append("      <a id=\"FilterButton\" style=\"${displayFilterBut}\" title=\"<fmt:message key=\"genapp.form.filterby\"/>\" onclick=\"document.getElementById('FilterDiv').style.display = 'inherit'; document.getElementById('FilterButton').style.display = 'none';\" class=\"btn\" role=\"button\" data-toggle=\"modal\">\n");
        codeHeaderButtons.append("         <i class=\"icon-search\"></i>\n");
        codeHeaderButtons.append("      </a>\n");
        codeHeaderButtons.append("      </c:if>\n"); 
        codeHeaderButtons.append("      <%-- FILTRAR PER BOTO - FINAL  --%>\n");
        codeHeaderButtons.append("     \n");


        // BOTO DE NOU ELEMENT EN LLISTAT
        codeHeaderButtons.append("    <c:if test=\"${" + instanceFilterForm + ".addButtonVisible}\">\n");  
        codeHeaderButtons.append("      <a class=\"btn btn-small pull-right\" role=\"button\" data-toggle=\"modal\"\n");
        codeHeaderButtons.append("        href=\"<c:url value=\"${contexte}/new\"/>\"> <i class=\"icon-plus-sign\"></i>\n");
        codeHeaderButtons.append("       <fmt:message key=\"genapp.createtitle\" >\n");
        codeHeaderButtons.append("         <fmt:param value=\"${entityname}\"/>\n");
        codeHeaderButtons.append("       </fmt:message>\n");
        codeHeaderButtons.append("      </a>\n");
        codeHeaderButtons.append("    </c:if>\n");

        // BOTO DE BORRAT MULTIPLE
        codeHeaderButtons.append("    <c:if test=\"${" + instanceFilterForm + ".deleteSelectedButtonVisible && " + instanceFilterForm + ".visibleMultipleSelection && not empty " + model + "Items}\">\n");
        codeHeaderButtons.append("      <a class=\"btn btn-danger btn-small pull-right\" style=\"color: white;\" href=\"#myModal\"\n");
        codeHeaderButtons.append("        onclick=\"openModalSubmit('<c:url value=\"${contexte}/deleteSelected\"/>','show', '" + model + "');\"\n");
        codeHeaderButtons.append("        title=\"<fmt:message key=\"genapp.delete\"/>\">\n");
        codeHeaderButtons.append("        <i class=\"icon-trash icon-white\"></i>\n");
        codeHeaderButtons.append("        <fmt:message key=\"genapp.delete.selected\" />\n");
        codeHeaderButtons.append("      </a>\n");
        codeHeaderButtons.append("    </c:if>\n");
        
        // BOTONS ADICIONALS
        //codeHeaderButtons.append("    <div id=\"" + model + "_list_buttons_header\">\n");
        codeHeaderButtons.append("    <c:forEach var=\"button\" items=\"${" + instanceFilterForm + ".additionalButtons}\">\n");
        codeHeaderButtons.append("      <c:set var=\"thelink\" value=\"${button.link}\" />\n");
        codeHeaderButtons.append("     <c:set var=\"thehref\" value=\"#\"/>\n");
        codeHeaderButtons.append("      <c:if test=\"${!fn:startsWith(thelink,'javascript:')}\">\n");
        codeHeaderButtons.append("        <c:url var=\"thehref\" value=\"${thelink}\"/>\n");
        codeHeaderButtons.append("        <c:url var=\"thelink\" value=\"${thelink}\"/>\n");
        codeHeaderButtons.append("        <c:set var=\"thelink\" value=\"goTo('${thelink}')\"/>\n");
        codeHeaderButtons.append("      </c:if>\n");
        codeHeaderButtons.append("      <button type=\"button\" class=\"btn btn-small ${button.type} pull-right\""
             + " href=\"${thehref}\" onclick=\"${thelink}\" title=\"<fmt:message key=\"${button.codeText}\"/>\">\n");
        codeHeaderButtons.append("         <i class=\"${button.icon}\"></i>\n");
        codeHeaderButtons.append("         <fmt:message key=\"${button.codeText}\"/>\n");
        codeHeaderButtons.append("      </button>\n");
        codeHeaderButtons.append("    </c:forEach>\n");
        //codeHeaderButtons.append("    </div>\n");
        
        

        codeHeaderButtons.append("  \n");

        sourceFiles.add(new SourceFile(model + "ListHeaderButtons.jsp", codeHeaderButtons.toString()));
      }


      {
        // =================== CODE FOR FILTER BY  ===============
        // =======================================================
        
        
        StringBuffer codeFilterBy = new StringBuffer();
        codeFilterBy.append("<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>\n");
        codeFilterBy.append("<%@ include file=\"/WEB-INF/jsp/moduls/includes.jsp\"%>\n");
        codeFilterBy.append("<un:useConstants var=\"" + instanceFields + "\" className=\"" + fieldsClass + "\"/>\n");
        codeFilterBy.append("\n");
        
        codeFilterBy.append("  <%-- HIDDEN PARAMS: FILTER BY --%> \n");
        codeFilterBy.append("  <form:hidden path=\"visibleFilterBy\"/>\n");
        codeFilterBy.append("\n");
        
        codeFilterBy.append("  <%-- FILTRAR PER - INICI --%>\n");
        codeFilterBy.append("  \n");
        codeFilterBy.append("  <c:set var=\"displayFilterDiv\" value=\"${" + instanceFilterForm + ".visibleFilterBy?'':'display:none;'}\" />  \n");
        codeFilterBy.append("  \n");
        codeFilterBy.append("  <div id=\"FilterDiv\" class=\"well formbox\" style=\"${displayFilterDiv} margin-bottom:3px; margin-left: 1px; padding:3px;\">\n");
        codeFilterBy.append("\n");
        codeFilterBy.append("      <div class=\"page-header\">\n");
        codeFilterBy.append("        <fmt:message key=\"genapp.form.filterby\"/>\n");
        codeFilterBy.append("        \n");
        codeFilterBy.append("        <div class=\"pull-right\">\n");
        codeFilterBy.append("\n");
        codeFilterBy.append("           <a class=\"pull-right\" style=\"margin-left:10px\" href=\"#\"> <i title=\"<fmt:message key=\"genapp.form.hidefilter\"/>\" onclick=\"document.getElementById('FilterDiv').style.display='none'; document.getElementById('FilterButton').style.display='inline';\" class=\"icon-remove\"></i></a>\n");
        codeFilterBy.append("           <input style=\"margin-left: 3px\" class=\"btn btn-warning pull-right\" type=\"button\" onclick=\"clear_form_elements(this.form)\" value=\"<fmt:message key=\"genapp.form.clean\"/>\"/>\n");
        codeFilterBy.append("           <input style=\"margin-left: 3px\" class=\"btn btn-warning pull-right\" type=\"reset\" value=\"<fmt:message key=\"genapp.form.reset\"/>\"/>\n");
        codeFilterBy.append("           <input style=\"margin-left: 3px\" class=\"btn btn-primary pull-right\" type=\"submit\" value=\"<fmt:message key=\"genapp.form.search\"/>\"/>\n");
        codeFilterBy.append("\n");
        codeFilterBy.append("        </div>\n");
        codeFilterBy.append("      </div>\n");
        codeFilterBy.append("      <div class=\"form-inline\">\n");
        codeFilterBy.append("      \n");
        
        final String searchByAdditional =
            "<c:forEach var=\"__entry\" items=\"${__theFilterForm.additionalFields}\">\n"
          + "<c:if test=\"${ __entry.key < 0 && not empty __entry.value.searchBy }\">\n"
          + "<div class=\"input-prepend\" style=\"padding-right: 4px;padding-bottom: 4px;\">\n"
          + "  <span class=\"add-on\"><fmt:message key=\"${__entry.value.codeName}\" />:</span>\n" 
          + "  <fmt:message key=\"genapp.form.searchby\" var=\"cercaperAF\" >\n"
          + "    <fmt:param>\n"
          + "      <fmt:message key=\"${__entry.value.codeName}\" />\n"
          + "    </fmt:param>\n"
          + "  </fmt:message>\n"
          + "  <input id=\"${__entry.value.searchBy.fullName}\" name=\"${__entry.value.searchBy.fullName}\" class=\"search-query input-medium\" placeholder=\"${cercaperAF}\" type=\"text\" value=\"${__entry.value.searchByValue}\"/>\n"
          + "</div>\n"
          + "</c:if>\n"
          + "</c:forEach>\n";
        
        codeFilterBy.append(searchByAdditional);
        
        codeFilterBy.append("\n\n");
        for (FieldInfo field : fields) {
         
          if (fileFields.contains(field)) {
          //if (!field.isFilter()) {
            continue;
          }
          
          // TODO fer filtre per booleà
          Class<?> cls = field.getJavaType();
          if (cls.equals(java.lang.Boolean.class)) {
            continue;
          }
          
          String modelCampMay = instanceFields + "." + field.getJavaName().toUpperCase();
          
          codeFilterBy.append("        <c:if test=\"${gen:contains(" + instanceFilterForm + ".filterByFields ," + modelCampMay + ")}\">\n");
         
          /**
           
           NOTA : Hem de permetre filtrar per camps ocults
           
          codeFilterBy.append("        <c:if test=\"${!gen:contains(" + instanceFilterForm + ".hiddenFields," + modelCampMay + ")}\">\n");
          **/
          try {
          
          
          String modelCamp = field.getJavaName(); 
          if (Number.class.isAssignableFrom(cls) || cls.isPrimitive()) {
            codeFilterBy.append("            <%-- FILTRE NUMERO --%>      \n");
            codeFilterBy.append("            <div class=\"input-prepend input-append\" style=\"padding-right: 4px;padding-bottom: 4px;\">\n");
            codeFilterBy.append("              <span class=\"add-on\"><fmt:message key=\"" + model + "." + modelCamp + "\" />:</span>\n");
            codeFilterBy.append("\n");
            codeFilterBy.append("              <span class=\"add-on\"><fmt:message key=\"genapp.from\" /></span>\n");
            codeFilterBy.append("              \n");
            codeFilterBy.append("              <form:input cssClass=\"input-append input-small\" path=\"" + modelCamp + "Desde\" />\n");
            codeFilterBy.append("                                       \n");
            codeFilterBy.append("              \n");
            codeFilterBy.append("              <span class=\"add-on\"><fmt:message key=\"genapp.to\" /></span>\n");
            codeFilterBy.append("              \n");
            codeFilterBy.append("              <form:input cssClass=\"input-append input-small search-query\" path=\"" + modelCamp + "Fins\" />\n");
            codeFilterBy.append("              \n");
            codeFilterBy.append("            </div>\n");
            codeFilterBy.append("\n\n");
            continue;
          }
          if (cls.equals(String.class)) {
            codeFilterBy.append("            <%-- FILTRE STRING --%>\n");
            codeFilterBy.append("            <div class=\"input-prepend\" style=\"padding-right: 4px;padding-bottom: 4px;\">\n");
            codeFilterBy.append("              <fmt:message key=\"" + model + "." + modelCamp + "\" var=\"" + modelCamp + "\" />\n");
            codeFilterBy.append("              <fmt:message key=\"genapp.form.searchby\" var=\"cercaper" + modelCamp + "\" >                \n");
            codeFilterBy.append("                 <fmt:param value=\"${" + modelCamp + "}\"/>\n");
            codeFilterBy.append("              </fmt:message>\n");
            codeFilterBy.append("              <span class=\"add-on\"><c:out value=\"${" + modelCamp + "}\" />:</span>\n");
            codeFilterBy.append("              <form:input cssClass=\"search-query input-medium\" placeholder=\"${cercaper" + modelCamp + "}\" path=\"" + modelCamp + "\" />\n");
            codeFilterBy.append("            </div>\n");
            codeFilterBy.append("\n\n");
            continue;
          }
          if (cls.equals(Time.class)) {
          codeFilterBy.append("            <%-- FILTRE TIME --%>      \n");
          codeFilterBy.append("            <div class=\"input-prepend input-append\" style=\"padding-right: 4px;padding-bottom: 4px;\">\n");
          codeFilterBy.append("              <span class=\"add-on\"><fmt:message key=\"" + model + "." + modelCamp + "\" />:</span>\n");
          codeFilterBy.append("\n");
          codeFilterBy.append("              <span class=\"add-on\"><fmt:message key=\"genapp.from\" /></span>\n");
          codeFilterBy.append("              <div id=\"" + modelCamp + "Desde\" class=\"input-append\">\n");
          codeFilterBy.append("                <form:input cssClass=\"input-small\" path=\"" + modelCamp + "Desde\" />\n");
          codeFilterBy.append("                <span class=\"add-on\">\n");
          codeFilterBy.append("                  <i data-time-icon=\"icon-time\" data-date-icon=\"icon-calendar\">\n");
          codeFilterBy.append("                  </i>\n");
          codeFilterBy.append("                </span>\n");
          codeFilterBy.append("              </div>\n");
          codeFilterBy.append("              <script type=\"text/javascript\">                \n");
          codeFilterBy.append("                $(function() {\n");
          codeFilterBy.append("                  $('#" + modelCamp + "Desde').datetimepicker({\n");
          codeFilterBy.append("                    language: '${lang}', \n");
          codeFilterBy.append("                    pick12HourFormat: <c:out value=\"${fn:contains(gen:getTimePattern(), 'a')?'true' : 'false'}\"/>,\n");
          codeFilterBy.append("                    format:  '${gen:getJSTimePattern()}',\n");
          codeFilterBy.append("                    pickDate: false,\n");
          codeFilterBy.append("                  });\n");
          codeFilterBy.append("                });\n");
          codeFilterBy.append("              </script>\n");
          codeFilterBy.append("              \n");
          codeFilterBy.append("              \n");
          codeFilterBy.append("              <span class=\"add-on\"><fmt:message key=\"genapp.to\" /></span>\n");
          codeFilterBy.append("              <div id=\"" + modelCamp + "Fins\" class=\"input-append\">\n");
          codeFilterBy.append("                <form:input cssClass=\"input-small\" path=\"" + modelCamp + "Fins\" />\n");
          codeFilterBy.append("                <span class=\"add-on\">\n");
          codeFilterBy.append("                  <i data-time-icon=\"icon-time\" data-date-icon=\"icon-calendar\">\n");
          codeFilterBy.append("                  </i>\n");
          codeFilterBy.append("                </span>\n");
          codeFilterBy.append("              </div>            \n");
          codeFilterBy.append("              <script type=\"text/javascript\">                \n");
          codeFilterBy.append("                $(function() {\n");
          codeFilterBy.append("                  $('#" + modelCamp + "Fins').datetimepicker({\n");
          codeFilterBy.append("                    language: '${lang}', \n");
          codeFilterBy.append("                    pick12HourFormat: <c:out value=\"${fn:contains(gen:getTimePattern(), 'a')?'true' : 'false'}\"/>,\n");
          codeFilterBy.append("                    format:  '${gen:getJSTimePattern()}',\n");
          codeFilterBy.append("                    pickDate: false,\n");
          codeFilterBy.append("                  });\n");
          codeFilterBy.append("                });\n");
          codeFilterBy.append("              </script>\n");
          codeFilterBy.append("              \n");
          codeFilterBy.append("            </div>\n");
          codeFilterBy.append("            \n");
          continue;
          }
          
          if (cls.equals(Date.class)) {
          
        codeFilterBy.append("            <%-- FILTRE DATE --%>\n");
        codeFilterBy.append("            <div class=\"input-prepend input-append\" style=\"padding-right: 4px;padding-bottom: 4px;\">\n");
        codeFilterBy.append("              <span class=\"add-on\"><fmt:message key=\"" + model + "." + modelCamp + "\" />:</span>\n");
        codeFilterBy.append("              <span class=\"add-on\"><fmt:message key=\"genapp.from\" /></span>\n");
        codeFilterBy.append("              <div id=\"" + modelCamp + "Desde\" class=\"input-append\">\n");
        codeFilterBy.append("                <form:input cssClass=\"input-small\" path=\"" + modelCamp + "Desde\" />\n");
        codeFilterBy.append("                <span class=\"add-on\">\n");
        codeFilterBy.append("                  <i data-time-icon=\"icon-time\" data-date-icon=\"icon-calendar\">\n");
        codeFilterBy.append("                  </i>\n");
        codeFilterBy.append("                </span>\n");
        codeFilterBy.append("              </div>\n");
        codeFilterBy.append("              <script type=\"text/javascript\">                \n");
        codeFilterBy.append("                $(function() {\n");
        codeFilterBy.append("                  $('#" + modelCamp + "Desde').datetimepicker({\n");
        codeFilterBy.append("                    language: '${lang}',\n");
        codeFilterBy.append("                    pick12HourFormat: <c:out value=\"${fn:contains(gen:getDatePattern(), 'a')?'true' : 'false'}\"/>,\n");
        codeFilterBy.append("                    format:  '${gen:getJSDatePattern()}',\n");
        codeFilterBy.append("                    pickTime: false,\n");
        codeFilterBy.append("                    weekStart: ${gen:getFirstDayOfTheWeek()}\n");
        codeFilterBy.append("                  });\n");
        codeFilterBy.append("                });\n");
        codeFilterBy.append("              </script>\n");
        codeFilterBy.append("              <span class=\"add-on\"><fmt:message key=\"genapp.to\" /></span>              \n");
        codeFilterBy.append("              <div id=\"" + modelCamp + "Fins\" class=\"input-append\">\n");
        codeFilterBy.append("                <form:input cssClass=\"input-small\" path=\"" + modelCamp + "Fins\" />\n");
        codeFilterBy.append("                <span class=\"add-on\">\n");
        codeFilterBy.append("                  <i data-time-icon=\"icon-time\" data-date-icon=\"icon-calendar\">\n");
        codeFilterBy.append("                  </i>\n");
        codeFilterBy.append("                </span>\n");
        codeFilterBy.append("              </div>\n");
        codeFilterBy.append("              <script type=\"text/javascript\">                \n");
        codeFilterBy.append("                $(function() {\n");
        codeFilterBy.append("                  $('#" + modelCamp + "Fins').datetimepicker({\n");
        codeFilterBy.append("                    language: '${lang}',\n");
        codeFilterBy.append("                    pick12HourFormat: <c:out value=\"${fn:contains(gen:getDatePattern(), 'a')?'true' : 'false'}\"/>,\n");
        codeFilterBy.append("                    format:  '${gen:getJSDatePattern()}',\n");
        codeFilterBy.append("                    pickTime: false,\n");
        codeFilterBy.append("                    weekStart: ${gen:getFirstDayOfTheWeek()}\n");
        codeFilterBy.append("                  });\n");
        codeFilterBy.append("                });\n");
        codeFilterBy.append("              </script>\n");
        codeFilterBy.append("            </div>\n");

        codeFilterBy.append("    \n");
        continue;
          }
          
          if (cls.equals(Timestamp.class)) {
            
            codeFilterBy.append("            <%-- FILTRE DATE --%>\n");
            codeFilterBy.append("            <div class=\"input-prepend input-append\" style=\"padding-right: 4px;padding-bottom: 4px;\">\n");
            codeFilterBy.append("              <span class=\"add-on\"><fmt:message key=\"" + model + "." + modelCamp + "\" />:</span>\n");
            codeFilterBy.append("              <span class=\"add-on\"><fmt:message key=\"genapp.from\" /></span>\n");
            codeFilterBy.append("              <div id=\"" + modelCamp + "Desde\" class=\"input-append\">\n");
            codeFilterBy.append("                <form:input cssClass=\"input-large\" path=\"" + modelCamp + "Desde\" />\n");
            codeFilterBy.append("                <span class=\"add-on\">\n");
            codeFilterBy.append("                  <i data-time-icon=\"icon-time\" data-date-icon=\"icon-calendar\">\n");
            codeFilterBy.append("                  </i>\n");
            codeFilterBy.append("                </span>\n");
            codeFilterBy.append("              </div>\n");
            codeFilterBy.append("              <script type=\"text/javascript\">                \n");
            codeFilterBy.append("                $(function() {\n");
            codeFilterBy.append("                  $('#" + modelCamp + "Desde').datetimepicker({\n");
            codeFilterBy.append("                    language: '${lang}',\n");
            codeFilterBy.append("                    pick12HourFormat: <c:out value=\"${fn:contains(gen:getDateTimePattern(), 'a')?'true' : 'false'}\"/>,\n");
            codeFilterBy.append("                    format:  '${gen:getJSDateTimePattern()}',\n");
            codeFilterBy.append("                    pickTime: true,\n");
            codeFilterBy.append("                    weekStart: ${gen:getFirstDayOfTheWeek()}\n");
            codeFilterBy.append("                  });\n");
            codeFilterBy.append("                });\n");
            codeFilterBy.append("              </script>\n");
            codeFilterBy.append("              <span class=\"add-on\"><fmt:message key=\"genapp.to\" /></span>              \n");
            codeFilterBy.append("              <div id=\"" + modelCamp + "Fins\" class=\"input-append\">\n");
            codeFilterBy.append("                <form:input cssClass=\"input-large\" path=\"" + modelCamp + "Fins\" />\n");
            codeFilterBy.append("                <span class=\"add-on\">\n");
            codeFilterBy.append("                  <i data-time-icon=\"icon-time\" data-date-icon=\"icon-calendar\">\n");
            codeFilterBy.append("                  </i>\n");
            codeFilterBy.append("                </span>\n");
            codeFilterBy.append("              </div>\n");
            codeFilterBy.append("              <script type=\"text/javascript\">                \n");
            codeFilterBy.append("                $(function() {\n");
            codeFilterBy.append("                  $('#" + modelCamp + "Fins').datetimepicker({\n");
            codeFilterBy.append("                    language: '${lang}',\n");
            codeFilterBy.append("                    pick12HourFormat: <c:out value=\"${fn:contains(gen:getDateTimePattern(), 'a')?'true' : 'false'}\"/>,\n");
            codeFilterBy.append("                    format:  '${gen:getJSDateTimePattern()}',\n");
            codeFilterBy.append("                    pickTime: true,\n");
            codeFilterBy.append("                    weekStart: ${gen:getFirstDayOfTheWeek()}\n");
            codeFilterBy.append("                  });\n");
            codeFilterBy.append("                });\n");
            codeFilterBy.append("              </script>\n");
            codeFilterBy.append("            </div>\n");
            codeFilterBy.append("\n");

            codeFilterBy.append("    \n");
            continue;
              }
          } finally {
            // Final d'ocultar camp de filtre
            codeFilterBy.append("        </c:if>\n");
            // codeFilterBy.append("        </c:if>\n");
          }
          
          throw new Exception("NO existix generador de filtre pel camp " + field.javaName 
              + " de la taula " + table.getNameJava()+ " de tipus " + field.getJavaType().getName());
        
        }; // Final de for de Filtres
        codeFilterBy.append("\n");
        
        codeFilterBy.append(searchByAdditional.replace(" < 0", " >= 0"));
        
        codeFilterBy.append("      </div>\n");
        codeFilterBy.append("    </div>\n");
        
        codeFilterBy.append("\n");
        codeFilterBy.append("\n");
        codeFilterBy.append("\n");
        codeFilterBy.append("    <%-- FILTRAR PER - FINAL --%>\n");
        codeFilterBy.append("  \n");
        
        
        sourceFiles.add(new SourceFile(model + "ListFilterBy.jsp", codeFilterBy.toString()));
        
      }
      
      
      
      
      
      {
        // =================== CODE FOR GROUPBY  ===============
        // =====================================================
                
        
        StringBuffer codeGroupBy = new StringBuffer();
        codeGroupBy.append("<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>\n");
        codeGroupBy.append("<%@ include file=\"/WEB-INF/jsp/moduls/includes.jsp\"%>\n");
        codeGroupBy.append("<un:useConstants var=\"" + instanceFields + "\" className=\"" + fieldsClass + "\"/>\n");
        codeGroupBy.append("  \n");
        codeGroupBy.append("\n");
  
  codeGroupBy.append("  <%-- HIDDEN PARAMS: GROUP BY --%>\n");
  
  codeGroupBy.append("  <form:hidden path=\"visibleGroupBy\"/>\n");
  codeGroupBy.append("  <form:hidden id=\"agruparPerCamp\" path=\"groupBy\"/> \n");
  codeGroupBy.append("  <form:hidden id=\"agruparPerValor\"  path=\"groupValue\"/>\n");

  codeGroupBy.append("\n");
  codeGroupBy.append("<%-- AGRUPAR PER - FUNCIONS --%>   \n");
  codeGroupBy.append("<script type=\"text/javascript\">\n");
  codeGroupBy.append("  $(\"body\").on(\"nodeselect.tree.data-api\", \"[role=leaf]\", function (e) {\n");
  codeGroupBy.append("\n");
  codeGroupBy.append("      var parentstr = \"\" + e.node.parentage[e.node.parentage.length - 1];\n");
  codeGroupBy.append("      document.getElementById('agruparPerCamp').value = parentstr;\n");
  codeGroupBy.append("      document.getElementById('agruparPerValor').value = e.node.value;\n");
  codeGroupBy.append("      document." + model + ".submit();\n");
  codeGroupBy.append("  })\n");
  codeGroupBy.append("\n");
  // Compatibilitat IE8
  codeGroupBy.append("  function groupByFieldValue(camp, valor) {\n");
  codeGroupBy.append("    document.getElementById('agruparPerCamp').value = camp;\n");
  codeGroupBy.append("    document.getElementById('agruparPerValor').value = valor;\n");
  codeGroupBy.append("    document." + model + ".submit();\n");
  codeGroupBy.append("  }\n");
  
  codeGroupBy.append("\n");
  codeGroupBy.append("</script>\n");
  codeGroupBy.append("\n");

  codeGroupBy.append("  <%-- AGRUPAR PER - INICI --%>   \n");
  codeGroupBy.append("\n");
  codeGroupBy.append("  <c:set var=\"displayGroupDiv\" value=\"${" + instanceFilterForm + ".visibleGroupBy?'':'display:none;'}\" />  \n");
  codeGroupBy.append("\n");
  codeGroupBy.append("  <c:if test=\"${fn:length(groupby_items) > 0}\">\n");  
  codeGroupBy.append(" <fmt:message var=\"buit\" key=\"genapp.notdefined\" />\n");
  codeGroupBy.append("  \n");
  codeGroupBy.append("  <div id=\"GroupDiv\" class=\"well\" style=\"${displayGroupDiv} padding: 1px; margin-right: 4px;  float: left; \">\n");
  codeGroupBy.append("      \n");
  codeGroupBy.append("      <div class=\"pull-right\" style=\"padding-left:2px\">\n");
  codeGroupBy.append("            <div class=\"span10\">\n");
  codeGroupBy.append("               <i title=\"<fmt:message key=\"genapp.form.hidegroupby\"/>\" onclick=\"document.getElementById('GroupDiv').style.display='none'; document.getElementById('GroupButton').style.display='inline';\" class=\"icon-remove\"></i>\n");
  codeGroupBy.append("            </div>\n");
  codeGroupBy.append("      </div>\n");
  codeGroupBy.append("\n");
  codeGroupBy.append("\n");
  codeGroupBy.append("      <ul class=\"tree\" style=\"margin:3px; padding:0px; float: left; \">\n");
  codeGroupBy.append("\n");
  codeGroupBy.append("        <li>\n");
  codeGroupBy.append("          <a href=\"#\" role=\"branch\" class=\"tree-toggle\" data-toggle=\"branch\" data-value=\" \"><b><fmt:message key=\"genapp.form.groupby\"/></b></a>\n");
  codeGroupBy.append("          <ul class=\"branch in\">\n");
  // Compatibilitat IE8
  codeGroupBy.append("              <c:if test=\"${IE8}\">\n");
  codeGroupBy.append("                <c:set var=\"linkItem\" value=\"onclick=\\\"groupByFieldValue(' ',' ')\\\"\" />\n");
  codeGroupBy.append("              </c:if>\n");
  codeGroupBy.append("              <li><a href=\"#\" role=\"leaf\" data-value=\"\" ${linkItem} >&raquo; <span style=\"${(" + instanceFilterForm + ".groupBy eq null)? \"font-weight: bold;\" : \"\"}\"><fmt:message key=\"genapp.form.groupby.noneitem\"/></span></a></li>\n");
  codeGroupBy.append("\n");
  codeGroupBy.append("\n");
  codeGroupBy.append("              <c:forEach  var=\"groupby_item\"  items=\"${groupby_items}\"> \n");
  codeGroupBy.append("                <li>\n");
  codeGroupBy.append("                  <a href=\"#\" role=\"branch\" class=\"tree-toggle ${groupby_item.selected? \"\" : \"closed\"}\" data-toggle=\"branch\" data-value=\"${groupby_item.value}\">\n");
  codeGroupBy.append("                    <span style=\"${groupby_item.selected? \"font-weight: bold;\" : \"\"}\">\n");
  codeGroupBy.append("                    <c:set var=\"code\" value=\"${(empty " + instanceFilterForm + ".labels[groupby_item.field])? groupby_item.codeLabel:" + instanceFilterForm + ".labels[groupby_item.field]}\" />\n");
  codeGroupBy.append("                        <c:if test=\"${!fn:startsWith(code,'=')}\" >\n");
  codeGroupBy.append("                        <fmt:message key=\"${code}\">\n");
  codeGroupBy.append("                              <fmt:param><fmt:message key=\"${groupby_item.codeParamLabel}\"/></fmt:param>\n");
  codeGroupBy.append("                        </fmt:message>\n");
  codeGroupBy.append("                        </c:if>\n");
  codeGroupBy.append("                        <c:if test=\"${fn:startsWith(code,'=')}\" >\n");
  codeGroupBy.append("                        <c:out value=\"${fn:substringAfter(code, '=')}\" escapeXml=\"false\" />\n");
  codeGroupBy.append("                        </c:if>\n");
  codeGroupBy.append("                    </span>\n");
  codeGroupBy.append("                  </a>\n");
  codeGroupBy.append("                  <%-- AQUI VANS ELS VALUES --%>\n");
  // Compatibilitat IE8
  codeGroupBy.append("                  <ul class=\"${(groupby_item.selected || IE8)? \"branch in\" : \"branch\"}\">\n");
  codeGroupBy.append("                  <c:forEach  var=\"groupbyvalue_item\"  items=\"${groupby_item.values}\">\n");
  codeGroupBy.append("                    <li>\n");
  // Compatibilitat IE8
  codeGroupBy.append("                      <c:if test=\"${IE8}\">\n");
  codeGroupBy.append("                        <c:set var=\"linkItem\" value=\"onclick=\\\"groupByFieldValue('${groupby_item.value}','${groupbyvalue_item.value}')\\\"\" />\n");
  codeGroupBy.append("                      </c:if>\n");
  codeGroupBy.append("                      <a href=\"#\" role=\"leaf\" data-value=\"${groupbyvalue_item.value}\" ${linkItem} >\n");
  codeGroupBy.append("                        &raquo; <span style=\"${groupbyvalue_item.selected? \"font-weight: bold;\" : \"\"}\" >\n");
  codeGroupBy.append("                          ${ (empty groupbyvalue_item.codeLabel) ? buit : groupbyvalue_item.codeLabel } (${groupbyvalue_item.count})\n");
  //code.append("                          ${groupbyvalue_item.codeLabel} (${groupbyvalue_item.count})\n");
  codeGroupBy.append("                      </span>\n");
  codeGroupBy.append("                      </a>\n");
  codeGroupBy.append("                    </li>\n");
  codeGroupBy.append("                  </c:forEach>\n");
  codeGroupBy.append("                  </ul>\n");
  codeGroupBy.append("                </li>\n");
  codeGroupBy.append("              </c:forEach>\n");
  codeGroupBy.append("\n");
  codeGroupBy.append("          </ul>\n");
  codeGroupBy.append("        </li>\n");
  codeGroupBy.append("      </ul>\n");
  codeGroupBy.append("\n");
  codeGroupBy.append("  </div>\n");
  codeGroupBy.append(" </c:if>\n");
  codeGroupBy.append(" \n");  
  codeGroupBy.append("  <%-- AGRUPAR PER - FINAL --%>\n");
  codeGroupBy.append("\n");
  
  sourceFiles.add(new SourceFile(model + "ListGroupBy.jsp", codeGroupBy.toString()));
  
  }
  
      
    //============================   LIST_EMPTY
    {
        StringBuffer codeEmptyList = new StringBuffer();
        
        codeEmptyList.append("<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>\n");
        codeEmptyList.append("<%@ include file=\"/WEB-INF/jsp/moduls/includes.jsp\"%>\n");
        codeEmptyList.append("  \n");
      
        codeEmptyList.append("  <div class=\"alert alert-block\">\n");
        codeEmptyList.append("    <fmt:message key=\"genapp.emptylist\" >\n");
        codeEmptyList.append("      <fmt:param value=\"${entitynameplural}\"/>\n");
        codeEmptyList.append("    </fmt:message>\n");
        codeEmptyList.append("  </div>\n");

        sourceFiles.add(new SourceFile(model + "ListEmpty.jsp", codeEmptyList.toString()));

    }
  
    
  
  //============================   LIST_CORE HEADER TABLE
      {
        StringBuffer codeHeader = new StringBuffer();
        
        codeHeader.append("<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>\n");
        codeHeader.append("<%@ include file=\"/WEB-INF/jsp/moduls/includes.jsp\"%>\n");
        codeHeader.append("<un:useConstants var=\"" + instanceFields + "\" className=\"" + fieldsClass + "\"/>\n");
        codeHeader.append("  \n");
        
        String additionalFieldsHeaderUp = 
        "\n\n        <c:forEach var=\"__entry\" items=\"${__theFilterForm.additionalFields}\">\n"
        + "        <c:if test=\"${ __entry.key < 0 }\">\n"
        + "        <th>\n"
        + "        ${" + project.getPrefix().toLowerCase() + ":getSortIconsAdditionalField(__theFilterForm,__entry.value)}\n"
        //+ "        <fmt:message key=\"${__entry.value.codeName}\" />\n"
        + "        </th>\n"
        + "        </c:if>\n"
        + "        </c:forEach>\n\n";
        
        String additionalFieldsHeaderDown = additionalFieldsHeaderUp.replace("< 0", ">=0");

        codeHeader.append(additionalFieldsHeaderUp);


        for (FieldInfo field : fields) {
          String modelCampMay = instanceFields + "." + field.getJavaName().toUpperCase();
          codeHeader.append("        <c:if test=\"${!gen:contains(" + instanceFilterForm + ".hiddenFields," + modelCampMay + ")}\">\n");
          codeHeader.append("        <th>${" + project.getPrefix().toLowerCase() + ":getSortIcons(" + instanceFilterForm + ","
              + modelCampMay + ")}</th>\n");
          codeHeader.append("        </c:if>\n");
        }
        
        codeHeader.append(additionalFieldsHeaderDown);
        
        sourceFiles.add(new SourceFile(model + "ListCoreHeader.jsp", codeHeader.toString()));

      }
  
  // 
  
      //  ============================   LIST_BUTTONS_HEADER
      String testDeFerVisibleBotons = "${" + instanceFilterForm + ".editButtonVisible "
         + "|| " + instanceFilterForm + ".deleteButtonVisible"
         + " || not empty " + instanceFilterForm + ".additionalButtonsForEachItem"
         + " || not empty " + instanceFilterForm + ".additionalButtonsByPK}";
      {
        
      StringBuffer codeButtonsHeader = new StringBuffer();
      codeButtonsHeader.append("<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>\n");
      codeButtonsHeader.append("<%@ include file=\"/WEB-INF/jsp/moduls/includes.jsp\"%>\n");
      codeButtonsHeader.append("  \n");
          
    
      codeButtonsHeader.append("          <c:if test=\"" + testDeFerVisibleBotons + "\">\n");
      codeButtonsHeader.append("          <th><fmt:message key=\"genapp.actions\" /></th>\n");
      codeButtonsHeader.append("          </c:if>\n");
      
      sourceFiles.add(new SourceFile(model + "ListButtonsHeader.jsp", codeButtonsHeader.toString()));
      
    }
  
  
  //  ============================   LIST_BUTTONS
      {
        
  StringBuffer codeButtons = new StringBuffer();
  codeButtons.append("<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>\n");
  codeButtons.append("<%@ include file=\"/WEB-INF/jsp/moduls/includes.jsp\"%>\n");
  codeButtons.append("  \n");
  
  codeButtons.append("          <c:if test=\"" + testDeFerVisibleBotons + "\">\n");
  codeButtons.append("          <td>\n");
  codeButtons.append("          <c:set var=\"pk\" value=\"" + pkMapping.substring(1) + "\"/>\n");
  codeButtons.append("          <c:choose>\n");
  /*
  <c:when test="${number1 < number2}">
      ${"number1 is less than number2"}
  </c:when>
  <c:when test="${number1 <= number3}">
      ${"number1 is less than equal to number2"}
  </c:when>
*/
  
  
  ActionsRenderer[] renderers = new ActionsRenderer[] {
      new ActionsRendererSimpleIconList(), 
      new ActionsRendererDropDownButton() };
  
  for (ActionsRenderer render : renderers ) {
    
    codeButtons.append("           <c:when test=\"${" + instanceFilterForm + ".actionsRenderer == " + render.getID() +"}\">\n");
  //****
  //codeButtons.append("            <div class=\"btn-group\" data-toggle=\"buttons-checkbox\">\n");
  
    
    codeButtons.append("            " + render.getHeader(instanceFilterForm));

  
  // BOTO D'EDITAR
  codeButtons.append("            <c:if test=\"${" + instanceFilterForm + ".editButtonVisible}\">\n");
  /*
  codeButtons.append("            <a class=\"btn\" href=\"#\" \n");
  codeButtons.append("              onclick=\"goTo('<c:url value=\"${contexte}" + pkMapping + "/edit\"/>')\"\n");
  codeButtons.append("              title=\"<fmt:message key=\"genapp.edit\"/>\">\n");
  codeButtons.append("              <i class=\"icon-pencil\"></i>\n");
  codeButtons.append("            </a>\n");
  */
  codeButtons.append(render.getActionButtonCode("            ", "", "<c:url value=\"${contexte}" + pkMapping + "/edit\"/>",
      "goTo('<c:url value=\"${contexte}" + pkMapping + "/edit\"/>')",
      "icon-pencil", "genapp.edit"));
  codeButtons.append("            </c:if>\n");
  
  // BOTO DE BORRAR
  codeButtons.append("            <c:if test=\"${" + instanceFilterForm + ".deleteButtonVisible}\">\n");
  /*
  codeButtons.append("            <a class=\"btn btn-danger\" href=\"#myModal\"\n");
  codeButtons.append("               onclick=\"openModal('<c:url value=\"${contexte}" + pkMapping + "/delete\"/>','show');\"\n");
  codeButtons.append("               title=\"<fmt:message key=\"genapp.delete\"/>\">\n");
  codeButtons.append("               <i class=\"icon-trash icon-white\"></i>\n");
  codeButtons.append("            </a>\n");
  */
  codeButtons.append(render.getActionButtonCode("            ", "btn-danger", "#myModal",
      "openModal('<c:url value=\"${contexte}" + pkMapping + "/delete\"/>','show');",
      "icon-trash icon-white", "genapp.delete"));
  codeButtons.append("            </c:if>\n");
  
  
  final String commonCodeAddBut = 
        "                  <c:set var=\"thehref\" value=\"#\"/>\n"
      + "                  <c:set var=\"thelink\" value=\"${fn:replace(button.link,bracket, pk)}\" />\n"
      + "                  <c:if test=\"${!fn:startsWith(thelink,'javascript:')}\">\n"
      + "                  <c:url var=\"thehref\" value=\"${thelink}\"/>\n"
      + "                  <c:url var=\"thelink\" value=\"${thelink}\"/>\n"
      + "                  <c:set var=\"thelink\" value=\"goTo('${thelink}')\"/>\n"
      + "                  </c:if>\n"
      /*
      + "            <a class=\"btn ${button.type} \" href=\"#\" \n"
      + "              onclick=\"${thelink}\" \n"
      + "              title=\"<fmt:message key=\"${button.codeText}\"/>\">\n"
      + "              <i class=\"${button.icon}\"></i>\n"
      + "            </a>\n"*/
      + render.getActionButtonCode("                  ", "${button.type}", "${thehref}",
          "${thelink}",
          "${button.icon}", "${button.codeText}");

  // BOTONS ADICIONALS PER CADA ITEM
  codeButtons.append("            <c:set var=\"bracket\" value=\"{0}\"/>\n");

  codeButtons.append("            <c:forEach var=\"button\" items=\"${" + instanceFilterForm + ".additionalButtonsForEachItem}\">\n");
  codeButtons.append(commonCodeAddBut);
  codeButtons.append("            </c:forEach>\n\n");

  // BOTONS ADICIONALS BY PK
  codeButtons.append("            <c:if test=\"${not empty " + instanceFilterForm + ".additionalButtonsByPK}\">\n");
  codeButtons.append("              <c:if test=\"${not empty " + instanceFilterForm + ".additionalButtonsByPK[pk]}\">\n");
  //codeButtons.append("                <c:set var=\"button\" value=\"${" + instanceFilterForm + ".additionalButtonsByPK[pk]}\"/>\n");

  codeButtons.append("                  <c:forEach var=\"button\" items=\"${" + instanceFilterForm + ".additionalButtonsByPK[pk]}\">\n");
  codeButtons.append(commonCodeAddBut);
  codeButtons.append("                  </c:forEach>\n\n");

  codeButtons.append("               </c:if>\n\n");
  codeButtons.append("            </c:if>\n\n");

  //codeButtons.append("            </div>\n");
  codeButtons.append("            " + render.getFooter());

  codeButtons.append("            </c:when>\n");

  }; // Final for de renders
  
  codeButtons.append("            <c:otherwise>\n");
  codeButtons.append("              &nbsp;<!-- Sense Render d'accions -->\n");
  codeButtons.append("            </c:otherwise>\n");
  codeButtons.append("          </c:choose>\n");
  
  codeButtons.append("           </td>\n");
  
  codeButtons.append("           </c:if>\n");
  
  sourceFiles.add(new SourceFile(model + "ListButtons.jsp", codeButtons.toString()));
  
    }
  
      
      
  StringBuffer codeCoreContent = new StringBuffer();
  codeCoreContent.append("<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>\n");
  codeCoreContent.append("<%@ include file=\"/WEB-INF/jsp/moduls/includes.jsp\"%>\n");
  codeCoreContent.append("<un:useConstants var=\"" + instanceFields + "\" className=\"" + fieldsClass + "\"/>\n");
  
  codeCoreContent.append("\n");
  
  String additionalFieldsUp = 
   "\n\n        <!--  /** Additional Fields */  -->\n"
  + "        <c:forEach var=\"__entry\" items=\"${" + instanceFilterForm + ".additionalFields}\" >\n"
  + "        <c:if test=\"${ __entry.key < 0 }\">\n"
  + "          <td>\n"
  + "             <c:if test=\"${not empty __entry.value.valueMap }\">\n"
  //  TODO Això no funciona per claus multiples
  + "               <c:out escapeXml=\"${__entry.value.escapeXml}\" value=\"${__entry.value.valueMap["+ pkMapping.substring(3,pkMapping.length() - 1) +"]}\" />\n"
  + "             </c:if>\n"
  + "             <c:if test=\"${not empty __entry.value.valueField }\">\n"
  + "               <c:set var=\"__tmp\" value=\"${pageScope}\" />\n"
  + "               <c:set var=\"__trosos\" value=\"${fn:split(__entry.value.valueField.fullName,'.')}\" />\n"
  + "               <c:forEach var=\"__tros\" items=\"${__trosos}\">\n"
  + "                  <c:set var=\"__tmp\" value=\"${__tmp[__tros]}\" />\n"
  + "               </c:forEach>\n"
  + "               <c:out escapeXml=\"${__entry.value.escapeXml}\" value=\"${__tmp}\" />\n"
  + "             </c:if>\n"
  + "          </td>\n"
  + "          </c:if>\n"
  + "          </c:forEach>\n\n\n";
  
  
  codeCoreContent.append(additionalFieldsUp);

  
  for (FieldInfo field : fields) {
    //Class<?> cls = field.getJavaType();
    String modelCamp = field.getJavaName(); 
    String modelCampMay = instanceFields + "." + field.getJavaName().toUpperCase();
    
    codeCoreContent.append("        <c:if test=\"${!gen:contains(" + instanceFilterForm + ".hiddenFields," + modelCampMay + ")}\">\n");
    try {
      // FITXER
      if (fileFields.contains(field)) {
        if (modelCamp.endsWith("ID")) {
          modelCamp = modelCamp.substring(0, modelCamp.length() - 2);
        }
        
        codeCoreContent.append("          <td>\n");
        codeCoreContent.append("            <c:if test=\"${not empty " + model + "." + modelCamp+ "}\">\n");
        // TODO FER MÈTODE QUE PASSAT UN FITXER RETORNI EL NOM
        codeCoreContent.append("              <a target=\"_blank\" href=\"<c:url value=\"${" + project.getPrefix().toLowerCase() + ":fileUrl(" + model + "." + modelCamp+ ")}\"/>\">${" + model + "." + modelCamp+ ".nom}</a>\n");
        codeCoreContent.append("            </c:if>\n");
        codeCoreContent.append("           </td>\n");
        continue;
      }
      
      switch (field.getWebFieldInfo().getWebtype()) {

        case WebType.Text:
        case WebType.Integer:
        case WebType.Decimal:
        case WebType.TextArea:
        case WebType.RichText:
        case WebType.URL:
        case WebType.PrimaryKey:
        case WebType.Query: // TODO FALTA FER REFERENCIA A NOM de la taula desti
        case WebType.ComboBox:
          
          codeCoreContent.append("          <td>\n");
          
          ForeignKey fk =field.getWebFieldInfo().getForeignKey();
          if (fk == null && field.getWebFieldInfo().getWebtype() != WebType.ComboBox) {
            //System.out.println(" ERROR DE QUERY: " + table.getNameJava() + "::" +  field.getJavaName());
            //throw new Exception();
            codeCoreContent.append("          ");
            /* WWW if (field.traduible) {
              code.append("<fmt:message key=\"");
            } */
            codeCoreContent.append("${" + model + "." + modelCamp + "}");
            /* WWW if (field.traduible) {
              code.append("\"/>");
            }  
            */
            codeCoreContent.append("\n");
              
          } else {
            TableInfo transTable = CodeGenUtils.getSqlTableTranslation(project.getTables());
            

            if (transTable != null && fk != null && transTable.getName().equals(fk.getTable()) ) {
              codeCoreContent.append("          <c:set var=\"tmp\">${" + model + "." + modelCamp + "}</c:set>\n");
              codeCoreContent.append("          <c:if test=\"${not empty tmp}\">\n");
              
              if (modelCamp.endsWith("ID")) {
                modelCamp = modelCamp.substring(0, modelCamp.length() - 2);
              }
              
              codeCoreContent.append("          ${" + model + "." + modelCamp + ".traduccions[lang].valor}\n");
              codeCoreContent.append("          </c:if>\n");
              
            } else {
              // Mostram d'un mapping
              String fkTableName = "Values";
              if (field.getWebFieldInfo().getWebtype() == WebType.Query) {
                TableInfo fkTable = CodeGenUtils.findTableInfoByTableSQLName(project, fk.getTable());
                fkTableName = fkTable.getNameJava();
              }

              //<fmt:message key="${estatsInicials[estatTxt]}" />
              //boolean traduir =  BackGenerator.fillReferences(fkTable, new StringBuffer());
              codeCoreContent.append("          <c:set var=\"tmp\">${" + model + "." + modelCamp + "}</c:set>\n");
              codeCoreContent.append("          <c:if test=\"${not empty tmp}\">\n");
              codeCoreContent.append("          ");
              /*
              if (traduir) {
                code.append("<fmt:message key=\"");
              }
              */
              String name = Character.toUpperCase(field.javaName.charAt(0))
              + field.javaName.substring(1);
    
              codeCoreContent.append("${" + instanceFilterForm + ".mapOf" + fkTableName + "For" + name + "[tmp]}");
              /*
              if (traduir) {
                code.append("\"/>");
              } 
              */ 
              codeCoreContent.append("\n");
              codeCoreContent.append("          </c:if>\n");
            }
          }
          codeCoreContent.append("          </td>\n");
          continue;
  
        case WebType.Checkbox:
          codeCoreContent.append("          <td>\n");
          if (!field.isNotNullable()) {
            codeCoreContent.append("            &nbsp;<c:if test=\"${not empty " + model + "." + modelCamp + "}\">\n");
          }          
          codeCoreContent.append("            <img height=\"18\" width=\"18\" src=\"<c:url value=\"/img/icn_alert_${" + model + "." + modelCamp + "?'success':'error'}.png\"/>\">\n");
          if (!field.isNotNullable()) {
            codeCoreContent.append("            </c:if>\n");
          } 
          codeCoreContent.append("          </td>\n");
          continue;
          
        case WebType.Date:
          codeCoreContent.append("          <td> <fmt:formatDate pattern=\"${gen:getDatePattern()}\" value=\"${" + model + "." + modelCamp + "}\" /></td>\n");
          continue;
          
        case WebType.Time:
          codeCoreContent.append("          <td> <fmt:formatDate pattern=\"${gen:getTimePattern()}\" value=\"${" + model + "." + modelCamp + "}\" /></td>\n");
          continue;
          
        case WebType.DateTime:
          codeCoreContent.append("          <td> <fmt:formatDate pattern=\"${gen:getDateTimePattern()}\" value=\"${" + model + "." + modelCamp + "}\" /></td>\n");
          continue;
          // TODO S'han de fer que tots els camps que referenciin a la taula fitxer 
          // canviin el tipus web a FILE 
        case WebType.File:      
        case WebType.UserID:
        case WebType.RoleID:
          throw new Exception("Els tipus WEB UserID i RoleID no estan suportats !!!!!");
          
        default:
          throw new Exception("NO existix generador de LIST_JSP pel camp "
              + field.javaName + " de tipus " + field.getClass());
  
      }
    } finally {
      codeCoreContent.append("        </c:if>\n");
    }
  
    }
  
  codeCoreContent.append(additionalFieldsUp.replace("< 0", ">= 0"));
  
  sourceFiles.add(new SourceFile(model + "ListCoreContent.jsp", codeCoreContent.toString()));

  

}

return sourceFiles;
  }
  
  
  
  public static abstract class ActionsRenderer {
    
    abstract int getID();
    
    abstract String getHeader(String instanceFilterForm);
    
    abstract String getFooter();
    
    abstract String getActionButtonCode(String tab, String type, 
        String href, String onclick, String icon, String codeMessage);
    
    
    public String getImage(String icon, String tab) {
      if (icon.trim().startsWith("${")) {
        StringBuffer code = new StringBuffer();
        String simpleVar = icon.trim().substring(2, icon.length() - 1);
        code.append(tab + "<c:if test=\"${fn:startsWith(" + simpleVar  + ", '/')}\">\n");
        code.append(tab + "<img src=\"<c:url value=\"" + icon+"\"/>\"/>\n");
        code.append(tab + "</c:if>");
        code.append(tab + "<c:if test=\"${!fn:startsWith(" + simpleVar  + ", '/')}\">\n");
        code.append(tab + "<i class=\"" + icon +  "\"></i>\n");        
        code.append(tab + "</c:if>");

        return code.toString();
      } else {
        return tab + "<i class=\"" + icon + "\"></i>";
      }
      
      
      
    }

  }
  
  
  
  public static class ActionsRendererDropDownButton extends ActionsRenderer {
    
    @Override
    public int getID() {
      return BaseFilterForm.ACTIONS_RENDERER_DROPDOWN_BUTTON; 
    }
    
    @Override
    public String getHeader(String instanceFilterForm) {

      String botomenu = instanceFilterForm + ".additionalInfoForActionsRendererByPK[pk]";
      return "    <div class=\"btn-group\">\n"
             + "      <a class=\"btn btn-small ${" + botomenu + "}\" href=\"#\" style=\"${(empty " + botomenu + ")? '' : 'color: white;'}\"><i class=\"icon-list ${(empty " + botomenu + ")? '' : 'icon-white'}\"></i> <fmt:message key=\"genapp.actions\" /></a>\n"
             + "      <a class=\"btn btn-small ${" + botomenu + "} dropdown-toggle\" data-toggle=\"dropdown\" href=\"#\">&nbsp;<span class=\"caret\"> </span></a>\n"
             + "      <ul class=\"dropdown-menu pull-right\" style=\"min-width:35px;padding:5px 5px 0px 5px;margin:0px;font-size: 12px\" >\n";
    }

    @Override
    String getFooter() {
      return "     </ul>\n"    
          + "    </div>\n";
    }

    @Override
    String getActionButtonCode(String tab, String type, String href, String onclick,
        String icon, String codeMessage) {
      return    
          tab + "<li>\n"
          + tab + "<a class=\"btn " + type + " btn-small a_item\" style=\"margin-bottom:5px;${(empty " + excludeVariableJSTL(type) + ")? '' : 'color: white;'}\" "
          + "href=\"" + href + "\" onclick=\"" + onclick + "\">\n"
          + getImage(icon, tab) + "\n"
          + tab + " <fmt:message key=\"" + codeMessage + "\"/>\n"
          + tab + "</a>\n"
          + tab + "</li>\n";
    }

  }
  
  public static String excludeVariableJSTL(String value) {
    if (value == null || value.trim().length() == 0) {
      return value;
    }
    value = value.trim();
    if (value.startsWith("${")) {
      value = value.substring(2, value.length() - 1);
    }
    return value;
  }
  
  public static class ActionsRendererSimpleIconList extends ActionsRenderer {
    
    
    
    @Override
    public int getID() {
      return BaseFilterForm.ACTIONS_RENDERER_SIMPLE_ICON_LIST; 
    }
    
    @Override
    public String getHeader(String instanceFilterForm) {
      return "<div class=\"btn-group\" data-toggle=\"buttons-checkbox\">\n";
    }
    
    @Override
    public String getFooter() {
      return "</div>\n";
    }
    
    /**

       codeButtons.append("            <a class=\"btn btn-danger\" href=\"#myModal\"\n");
  codeButtons.append("               onclick=\"openModal('<c:url value=\"${contexte}" + pkMapping + "/delete\"/>','show');\"\n");
  codeButtons.append("               title=\"<fmt:message key=\"genapp.delete\"/>\">\n");
  codeButtons.append("               <i class=\"icon-trash icon-white\"></i>\n");
  codeButtons.append("            </a>\n");
     


     */
    @Override
    public String getActionButtonCode(String tab, String type, String href, String onclick, String icon, String codeMessage) {
      StringBuffer codeButtons = new StringBuffer();
      
      codeButtons.append(tab + "<a class=\"btn " + type + "\" href=\"" + href + "\" onclick=\"" 
          + onclick + "\" title=\"<fmt:message key=\"" + codeMessage + "\"/>\">\n");
      codeButtons.append( getImage(icon, tab + "   ") + "\n");
      codeButtons.append(tab + "</a>\n");
      return codeButtons.toString();
    }
    
  }
  
  
  


  public static String generateMenu(TableInfo[] tables) {
    StringBuffer code = new StringBuffer();
    
    tables = (TableInfo[])tables.clone();
    
    Arrays.sort(tables);
    
    for (TableInfo table : tables) {
      
      if (!table.isGenerate() || table.isTranslationMapEntity()) {
        continue;
      }
      
      String model = CodeGenUtils.getModelName(table.getNameJava());
      code.append("\n");
      code.append("    <%-- " + table.getNameJava() + " --%>\n");
      code.append("    <li>\n");
      code.append("      <a href=\"#\" role=\"branch\" class=\"tree-toggle ${fn:contains(url, '"
          + model
          + "/')? \"\" : \"closed\"}\" data-toggle=\"branch\" data-value=\"suportada\"><span style=\"${fn:contains(url, '"
          + model
          + "/')? \"font-weight: bold;\" : \"\"}\"><fmt:message key=\""
          + model
          + "."
          + model + "\"/></span></a>\n");
      code.append("      <ul class=\"${fn:contains(url, '" + model
          + "/')? \"branch in\" : \"branch\"}\">\n");
      code.append("        <li style=\"list-style-type: disc; list-style-position: inside;\" ><a href=\"<c:url value=\"/webdb/"
          + model
          + "/new\"/>\" ><span style=\"${(fn:contains(url, '"
          + model
          + "/') && fn:contains(url, '/new'))? \"font-weight: bold;\" : \"\"}\" >\n");
      code.append("       <fmt:message var=\"entityname\" key=\"" + model + "." + model +"\"/>\n");
      code.append("       <fmt:message key=\"genapp.createtitle\" >\n");
      code.append("         <fmt:param value=\"${entityname}\"/>\n");
      code.append("       </fmt:message>\n");
      
      code.append("       </span></a></li>\n");
      code.append("        <li style=\"list-style-type: disc; list-style-position: inside;\"><a href=\"<c:url value=\"/webdb/"
          + model
          + "/list/1\"/>\" ><span style=\"${(fn:contains(url, '"
          + model
          + "/') && fn:contains(url, '/list'))? \"font-weight: bold;\" : \"\"}\" >\n");
      code.append("        <fmt:message key=\"genapp.listtitle\" >\n");
      code.append("         <fmt:param value=\"${entityname}\"/>\n");
      code.append("       </fmt:message>\n");
      
      code.append("        </span></a>\n");
      code.append("        </li>\n");
      code.append("      </ul>\n");
      code.append("    </li>\n");
    }
    
    
    return code.toString();
  }
  
  public static String generateTile(TableInfo[] tables) {
    StringBuffer code = new StringBuffer();
    for (TableInfo table : tables) {
      
      if (!table.isGenerate() || table.isTranslationMapEntity()) {
        continue;
      }
      
      String model = CodeGenUtils.getModelName(table.getNameJava());

      code.append("\n");
      code.append("    <definition name=\"" + model + "FormWebDB\" extends=\"webdb\">\n");
      code.append("        <put-attribute name=\"titol\" value=\"" + model + "." + model
          + "\" />\n");
      code.append("        <put-attribute name=\"contingut\" value=\"/WEB-INF/jsp/webdb/"
          + model + "Form.jsp\" />\n");
      code.append("    </definition>\n");
      code.append("    \n");
      code.append("    <definition name=\"" + model + "ListWebDB\" extends=\"webdb\">\n");
      
      //code.append("        <put-attribute name=\"titol\" value=\"" + model + ".llistat\" />\n");
      code.append("        <put-attribute name=\"contingut\" value=\"/WEB-INF/jsp/webdb/"
          + model + "List.jsp\" />\n");
      code.append("    </definition>\n\n");
    }
    return code.toString();
  }
  
  
  public static String getEmptyTilesXML() {
    StringBuffer code = new StringBuffer();
    code.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
    code.append("<!DOCTYPE tiles-definitions PUBLIC\n");
    code.append("       \"-//Apache Software Foundation//DTD Tiles Configuration 2.0//EN\"\n");
    code.append("       \"http://tiles.apache.org/dtds/tiles-config_2_0.dtd\">\n");
    code.append("<tiles-definitions>\n");
    code.append("\n");

    code.append("    <definition name=\"base.cap\" template=\"/WEB-INF/jsp/moduls/cap.jsp\" preparer=\"capPreparer\"/>\n");
    code.append("    <definition name=\"base.menu_i_contingut\" template=\"/WEB-INF/jsp/moduls/menu_i_contingut.jsp\"/>\n");
    code.append("    <definition name=\"base.menu\" template=\"/WEB-INF/jsp/moduls/blanc.jsp\"/>\n");
    code.append("    <definition name=\"base.contingut\" template=\"/WEB-INF/jsp/moduls/blanc.jsp\"/>\n");
    code.append("    <definition name=\"base.peu\" template=\"/WEB-INF/jsp/moduls/peu.jsp\" preparer=\"peuPreparer\"/>\n");

    code.append(" <definition name=\"base.contingut\" template=\"/WEB-INF/jsp/moduls/blanc.jsp\"/>\n");
    code.append("\n");
    code.append("    <definition name=\"base.definition\" template=\"/WEB-INF/jsp/layout/layout.jsp\" preparer=\"basePreparer\">\n");
    code.append("        <put-attribute name=\"cap\" value=\"base.cap\" />\n");
    code.append("        <put-attribute name=\"menu_i_contingut\" value=\"base.menu_i_contingut\" />\n");
    code.append("        <put-attribute name=\"menu\" value=\"base.menu\" />\n");
    code.append("        <put-attribute name=\"contingut\" value=\"base.contingut\" />\n");
    code.append("        <put-attribute name=\"peu\" value=\"base.peu\" />\n");
    code.append("    </definition>\n");
    code.append("\n");
    code.append("\n");
    code.append("    <definition name=\"principal.body\" template=\"/WEB-INF/web/tiles/principal/principal.jsp\"/>\n");
    code.append(" \n");
    code.append("\n");
    code.append("    <definition name=\"principal\" extends=\"base.definition\">\n");
    code.append("        <put-attribute name=\"menu\" value=\"/WEB-INF/jsp/moduls/menu_inici.jsp\" />\n");
    code.append("        <put-attribute name=\"contingut\" value=\"/WEB-INF/jsp/principal.jsp\" />\n");
    code.append("    </definition>\n");
    code.append("    \n");
    code.append("    <definition name=\"sense_enllaz\" extends=\"base.definition\">\n");
    code.append("        <put-attribute name=\"enlaces\" value=\"\" />\n");
    code.append("    </definition>\n");
    code.append("    \n");
    code.append("    <definition name=\"exceptionTile\" extends=\"base.definition\">\n");
    code.append("        <put-attribute name=\"contingut\" value=\"/error.jsp\" />\n");
    code.append("    </definition>\n");

    code.append("    <!--  ===========  DESENVOLUPAMENT ================== -->\n");
    code.append("    \n");
    code.append("    <definition name=\"desenvolupament\" extends=\"base.definition\">\n");
    code.append("        <put-attribute name=\"pipella\" value=\"desenvolupament\" />\n");
    code.append("        <put-attribute name=\"menu\" value=\"/WEB-INF/jsp/moduls/menu_desenvolupament.jsp\" />\n");
    code.append("        <put-attribute name=\"contingut\" value=\"/WEB-INF/jsp/desenvolupament.jsp\" />\n");
    code.append("    </definition>\n");
    

    code.append("\n");
    code.append(" <!-- ============ EXEMPLES ====================  -->\n");
    code.append(" \n");
    code.append(" <!--\n");
    code.append("    <definition name=\"exemple_plantilla\" extends=\"sense_enlaces\">\n");
    code.append("        <put-attribute name=\"titulo\" value=\"Portafirmes\" />\n");
    code.append("        <put-attribute name=\"contingut\" value=\"/WEB-INF/jsp/exemples/example_plantilla.jsp\" />\n");
    code.append("    </definition>\n");
    code.append("    \n");
    code.append("\n");
    code.append("    <definition name=\"exemple_pagina completa\" template=\"/WEB-INF/jsp/layout/onlycontentlayout.jsp\">\n");
    code.append("        <put-attribute name=\"contingut\" value=\"/WEB-INF/jsp/exemples/exemple_pagina completa.jsp\" />\n");
    code.append("    </definition>\n");
    code.append("\n");
    code.append("    <definition name=\"rss\" template=\"/WEB-INF/jsp/rss/agro.rss.jsp\" />\n");
    code.append(" \n");
    code.append(" -->\n");

    code.append("\n");
    code.append("    <!--  ===========  WEBDB ================== -->\n");
    code.append("\n");
    code.append("    <definition name=\"webdb\" extends=\"base.definition\">\n");
    code.append("        <put-attribute name=\"pipella\" value=\"webdb\" />\n");
    code.append("        <put-attribute name=\"menu\" value=\"/WEB-INF/jsp/webdb/menu_webdb.jsp\" />\n");
    code.append("    </definition>\n");

    code.append("\n");
    code.append("<!-- ==== GENAPP MARK START -->\n");
    code.append("\n");
    code.append("\n");
    code.append("<!-- ==== GENAPP MARK END -->\n");
 
    code.append("\n");
    code.append("</tiles-definitions>\n");
    code.append("\n");
    code.append("\n");
    code.append("\n");
    code.append("\n");
    return code.toString();
  }
  
  public static List<SourceFile> generateFormJsp(String packageControllerBack,
      String packageFormBack, String packageValidatorBack,String jpaPackage,
      String ejbPackage, BasicPackages packages, Project project,
      Map<String,BeanCode> beanCodeBySqlTableName) throws Exception  {
    
    TableInfo[] tables = project.getTables();

    List<SourceFile> sourceFiles = new ArrayList<SourceFile>(tables.length);

    for (int i = 0; i < tables.length; i++) {

      TableInfo table = tables[i];
      if (!tables[i].isGenerate() || tables[i].isTranslationMapEntity()) {
        continue;
      }
      FieldInfo[] fields = table.fields;
      final String tableJavaName = table.getNameJava();
      final String model = CodeGenUtils.getModelName(tableJavaName);
      final String form = tableJavaName + "Form";
      
      String instanceForm = CodeGenUtils.getModelName(form);
      
      String instanceFields = tableJavaName + "Fields";
      String fieldsClass = packages.fieldsPackage + "." + instanceFields;
      
      List<FieldInfo> translationFields = CodeGenUtils.getTranslationFieldsOfTable(tables, table);
      if (translationFields == null) {
        translationFields = new ArrayList<FieldInfo>();
      }
      
      
      //TableInfo fileTable = CodeGenUtils.getSqlTableFile(tables);
      List<FieldInfo> fileFields = CodeGenUtils.getFileFieldsOfTable(tables, table);
      if (fileFields == null) {
        fileFields = new ArrayList<FieldInfo>();
      }
    
      
    // ============= JSP PRINCIPAL  
    {
      StringBuffer codeBase = new StringBuffer();
  
      codeBase.append("\n");
      codeBase.append("<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>\n");
      codeBase.append("<%@ include file=\"/WEB-INF/jsp/moduls/includes.jsp\"%>\n");
      codeBase.append(" \n");
  
      codeBase.append("  <%@include file=\"" + model + "FormTitle.jsp\" %>\n");
  
      codeBase.append("\n\n");
      
      codeBase.append("<form:form modelAttribute=\"" + instanceForm + "\" method=\"${method}\"\n");
      codeBase.append("  enctype=\"multipart/form-data\">\n");
      codeBase.append("  \n");
      codeBase.append("  <c:set var=\"contexte\" value=\"${" + instanceForm + ".contexte}\"/>\n");
      codeBase.append("  <form:hidden path=\"nou\" />\n");
      codeBase.append("  \n");

      codeBase.append("  <%@include file=\"" + model + "FormCorePre.jsp\" %>\n");
      
      codeBase.append("  <%@include file=\"" + model + "FormCore.jsp\" %>\n");
      
      codeBase.append("\n");
      codeBase.append("  <%@include file=\"" + model + "FormCorePost.jsp\" %>\n");
      
      codeBase.append("\n");
      codeBase.append("  <%@include file=\"" + model + "FormButtons.jsp\" %>\n");
      
      
      codeBase.append("\n");
      codeBase.append("  <c:if test=\"${" + instanceForm + ".attachedAdditionalJspCode}\">\n");
      codeBase.append("     <%@include file=\"../webdbmodificable/" + model+ "FormModificable.jsp\" %>\n");
      codeBase.append("  </c:if>\n");
      codeBase.append("\n");
      
      codeBase.append("</form:form>\n");
      codeBase.append("\n");
      codeBase.append("\n");
  
      sourceFiles.add(new SourceFile(model + "Form.jsp", codeBase.toString()));
    }

    
    // ============= JSP FORM TITLE
    {
    StringBuffer codeTitle = new StringBuffer();
    
    codeTitle.append("<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>\n");
    codeTitle.append("<%@ include file=\"/WEB-INF/jsp/moduls/includes.jsp\"%>\n");
    codeTitle.append("  \n");
    
    codeTitle.append("<div class=\"lead\" style=\"margin-bottom:10px\">\n");

    codeTitle.append("  <c:if test=\"${empty " + instanceForm + ".entityNameCode}\">\n");
    codeTitle.append("    <fmt:message var=\"entityname\" key=\"" + model + "." + model +"\"/>\n");
    codeTitle.append("  </c:if>\n");
    codeTitle.append("  <c:if test=\"${not empty " + instanceForm + ".entityNameCode}\">\n");
    codeTitle.append("    <fmt:message var=\"entityname\" key=\"${" + instanceForm + ".entityNameCode}\"/>\n");
    codeTitle.append("  </c:if>\n");

    codeTitle.append("  <c:if test=\"${not empty " + instanceForm + ".titleCode}\">\n");
    codeTitle.append("    <fmt:message key=\"${" + instanceForm + ".titleCode}\" >\n");
    codeTitle.append("      <fmt:param value=\"${" + instanceForm + ".titleParam}\" />\n");
    codeTitle.append("    </fmt:message>\n");
    codeTitle.append("  </c:if>\n");
    codeTitle.append("  <c:if test=\"${empty " + instanceForm + ".titleCode}\">\n");
    codeTitle.append("    <c:set var=\"keytitle\" value=\"${" + instanceForm + ".nou?'genapp.createtitle':(" + instanceForm + ".view?'genapp.viewtitle':'genapp.edittitle')}\"/>\n");
    codeTitle.append("    <fmt:message key=\"${keytitle}\">\n");
    codeTitle.append("      <fmt:param value=\"${entityname}\"/>\n");
    codeTitle.append("    </fmt:message>\n");
    codeTitle.append("  </c:if>\n");
    codeTitle.append("  \n");
    
    
    codeTitle.append("  <c:if test=\"${not empty " + instanceForm + ".subTitleCode}\">\n");
    codeTitle.append("  <br/><h5 style=\"line-height: 10px; margin-top: 0px; margin-bottom: 0px;\">\n");
    codeTitle.append("<c:set var=\"subtitleTranslated\" value=\"${fn:startsWith(" + instanceForm + ".subTitleCode,'=')}\" />\n"); 
    codeTitle.append("<c:if test=\"${subtitleTranslated}\">\n");
    codeTitle.append("   <c:out value=\"${fn:substringAfter(" + instanceForm + ".subTitleCode, '=')}\" escapeXml=\"false\"/>\n");
    codeTitle.append("</c:if>\n");
    codeTitle.append("<c:if test=\"${not subtitleTranslated}\">\n");
    codeTitle.append("  <fmt:message key=\"${" + instanceForm + ".subTitleCode}\" />\n");     
    codeTitle.append("</c:if>\n");
    codeTitle.append("</h5>\n");
    codeTitle.append("  </c:if>\n");
    
    codeTitle.append("</div>");
    
    
    
    
    sourceFiles.add(new SourceFile(model + "FormTitle.jsp", codeTitle.toString()));
    
    }
    
    
    // ============= JSP FORM CORE - PRE
    {
    StringBuffer code = new StringBuffer();
    
    code.append("<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>\n");
    code.append("<%@ include file=\"/WEB-INF/jsp/moduls/includes.jsp\"%>\n");
    code.append("<un:useConstants var=\"" + instanceFields + "\" className=\"" + fieldsClass + "\"/>\n");
    
    code.append("  \n");

    code.append("  <c:set var=\"__theForm\" value=\"${" + instanceForm + "}\"/>\n");
    
    instanceForm = "__theForm";
    
    code.append("\n");
    code.append("  <div class=\"module_content\">\n");
    code.append("    <div class=\"tab_container\">\n");
    code.append("    \n");

    // Mostrar errors de camps ocults
    code.append("    <c:forEach items=\"${" + instanceForm + ".hiddenFields}\" var=\"hiddenFieldF\" >\n");
    code.append("      <c:set  var=\"hiddenField\" value=\"${hiddenFieldF.javaName}\" />\n");
    code.append("      <c:if test=\"${gen:hasProperty(__theForm." + model + ",hiddenField)}\">\n");
    code.append("        <form:errors path=\"" + model + ".${hiddenField}\" cssClass=\"errorField alert alert-error\" />\n");
    code.append("        <form:hidden path=\"" + model + ".${hiddenField}\"/>\n");
    code.append("      </c:if>\n");
    code.append("    </c:forEach>\n");

    code.append("    <table class=\"tdformlabel table-condensed table table-bordered table-striped marTop10  \" > \n");
    code.append("    <tbody>      \n");
    code.append("\n");
    sourceFiles.add(new SourceFile(model + "FormCorePre.jsp", code.toString()));
        
    }
    
    
    
    {
    // ============= JSP FORM BUTTONS
    
      List<FieldInfo> PKs = CodeGenUtils.getPKs(table.getFields());
      final boolean multiplePKs = (PKs.size() > 1);
      
      String pkMapping;
            
      if (multiplePKs) {
        pkMapping = "";
        for (FieldInfo fieldInfo : PKs) {          
          pkMapping = pkMapping + "/${" + instanceForm + "." + model + "." + fieldInfo.getJavaName() + "}";
        }
      } else {
        FieldInfo pk = PKs.get(0);
        pkMapping = "/${" + instanceForm + "." + model + "." + pk.getJavaName() + "}";
      }
      
      
      
    StringBuffer codeButton = new StringBuffer();
    
    codeButton.append("<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>\n");
    codeButton.append("<%@ include file=\"/WEB-INF/jsp/moduls/includes.jsp\"%>\n");
    codeButton.append("  \n");
    
    
    codeButton.append("  <div class=\"navbar-form pull-right\">\n");
    // BOTO GUARDAR
    codeButton.append("    <c:if test=\"${" + instanceForm + ".saveButtonVisible}\">\n");
    codeButton.append("    <input type=\"submit\" class=\"btn btn-primary\" value=\"<fmt:message key=\"genapp.save\"/>\">\n");
    codeButton.append("    </c:if>\n");
    // BOTO CANCEL.LAR
    codeButton.append("    <c:if test=\"${" + instanceForm + ".cancelButtonVisible}\">\n");
    codeButton.append("    <input type=\"button\" class=\"btn\" onclick=\"goTo('<c:url value=\"${contexte}" + pkMapping + "/cancel\"/>')\" value=\"<fmt:message key=\"genapp.cancel\"/>\">\n");
    codeButton.append("    </c:if>\n");
    // BOTO DELETE
    codeButton.append("    <c:if test=\"${!" + instanceForm + ".nou && " + instanceForm + ".deleteButtonVisible}\">\n");
    codeButton.append("    <button type=\"button\" class=\"btn btn-danger\" onclick=\"openModal('<c:url value=\"${contexte}" + pkMapping + "/delete\"/>','show');\"><fmt:message key=\"genapp.delete\"/></button>\n");
    codeButton.append("    </c:if>\n");

    // BOTONS ADICIONALS
    codeButton.append("    <c:set var=\"bracket\" value=\"{0}\"/>\n");
    codeButton.append("    <c:forEach var=\"button\" items=\"${" + instanceForm + ".additionalButtons}\">\n");
    codeButton.append("    <c:if test=\"${!" + instanceForm + ".nou || (-1 == fn:indexOf(button.link,bracket))}\">\n");
    codeButton.append("    <c:set var=\"pk\" value=\"" + pkMapping.substring(1) + "\"/>\n");
    codeButton.append("    <c:set var=\"thelink\" value=\"${fn:replace(button.link,bracket, pk)}\" />\n");
    codeButton.append("    <c:set var=\"thehref\" value=\"#\"/>\n");
    codeButton.append("    <c:if test=\"${!fn:startsWith(thelink,'javascript:')}\">\n");
    codeButton.append("     <c:url var=\"thehref\" value=\"${thelink}\"/>\n");
    codeButton.append("     <c:url var=\"thelink\" value=\"${thelink}\"/>\n");
    codeButton.append("     <c:set var=\"thelink\" value=\"goTo('${thelink}')\"/>\n");
    codeButton.append("    </c:if>\n");
    codeButton.append("    <button type=\"button\" class=\"btn ${button.type}\" \n");
    codeButton.append("       href=\"${thehref}\" onclick=\"${thelink}\">\n");
    codeButton.append("       <i class=\"${button.icon}\"></i><fmt:message key=\"${button.codeText}\"/>\n");
    codeButton.append("    </button>\n");
    codeButton.append("    </c:if>\n");
    codeButton.append("    </c:forEach>\n");
    codeButton.append("  </div>\n");
    
    sourceFiles.add(new SourceFile(model + "FormButtons.jsp", codeButton.toString()));
    }
    
   
    
    // ============= JSP FORM CORE - POST
    {
    StringBuffer code = new StringBuffer();
    
    code.append("<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>\n");
    code.append("<%@ include file=\"/WEB-INF/jsp/moduls/includes.jsp\"%>\n");
    code.append("  \n");

    code.append("     </tbody>\n");
    code.append("    </table>\n");
    code.append("\n");
    code.append("    </div>\n");
    code.append("\n");
    code.append("  </div>\n");
    code.append("\n");

    code.append("\n");

    code.append("\n");
    sourceFiles.add(new SourceFile(model + "FormCorePost.jsp", code.toString()));
        
    }
    
    
    
    // ============= JSP FORM CORE
    {
    StringBuffer code = new StringBuffer();
    
    code.append("<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>\n");
    code.append("<%@ include file=\"/WEB-INF/jsp/moduls/includes.jsp\"%>\n");
    code.append("<un:useConstants var=\"" + instanceFields + "\" className=\"" + fieldsClass + "\"/>\n");
    code.append("  \n");
        
    

    for (FieldInfo field : fields) {

      String modelCamp = field.getJavaName();
      String modelCampMay = instanceFields + "." + field.getJavaName().toUpperCase();
      Class<?> type = field.getJavaType();
      String required = field.isNotNullable() && !(type.equals(boolean.class) || type.equals(Boolean.class) ) ?" &nbsp;(*)" : "";

      String codeField = generateFieldFormInput(model, instanceForm, fileFields,
          translationFields, field, modelCamp, project, modelCampMay);

      if (codeField != null ) {

        code.append("        <c:if test=\"${!gen:contains(" + instanceForm + ".hiddenFields," + modelCampMay + ")}\">\n");
        code.append("        <tr>\n"); 
        code.append("          <td>\n");
        code.append("            <label>\n");
        code.append("              <fmt:message key=\""
        		 + "${(empty " + instanceForm + ".labels[" + modelCampMay + "])?'" + model + "." + modelCamp + "':" + instanceForm + ".labels[" + modelCampMay + "]}"
        		 + "\" />" + required + "\n");
        code.append("              <c:if test=\"${not empty " + instanceForm + ".help[" + modelCampMay + "]}\">\n");
        code.append("              <i class=\"icon-info-sign\" title=\"${" + instanceForm + ".help[" +  modelCampMay + "]}\" ></i>\n");
        code.append("              </c:if>\n");
        code.append("             </label>\n");
        code.append("            </td>\n");
        code.append("            <td>\n");
        code.append(codeField);
        code.append("           </td>\n");
        code.append("        </tr>\n");
        code.append("        </c:if>\n");
        code.append("        \n");
      }

    } // Final For fields
   
  

    sourceFiles.add(new SourceFile(model + "FormCore.jsp", code.toString()));
    }

  }

  return sourceFiles;
    }


  private static String  generateFieldFormInput(final String model, final String instanceForm,
      List<FieldInfo> fileFields, List<FieldInfo> translationFields, 
      FieldInfo field, String modelCamp, Project project,String modelCampMay)
      throws Exception {
    
    StringBuffer code = new StringBuffer();
    
    final String readOnlyCondition = "gen:contains(" + instanceForm + ".readOnlyFields ," + modelCampMay + ")";
    final String readOnlyAttribute = "readonly=\"${ " + readOnlyCondition + "? 'true' : 'false'}\"";
    final String formInputReadOnly = readOnlyAttribute + " cssClass=\"${" + readOnlyCondition + "? 'input uneditable-input' : 'input'}\" "; 
    
    if (fileFields.contains(field)) {
      String fileObj = modelCamp;
      if (modelCamp.endsWith("ID")) {
        fileObj = modelCamp.substring(0, modelCamp.length() - 2);
      }
      code.append("              <form:errors path=\"" + model + "." + modelCamp + "\" cssClass=\"errorField alert alert-error\" />\n");
      code.append("              <div class=\"fileupload fileupload-new\" data-provides=\"fileupload\" style=\"margin-bottom: 0px\">\n");
      code.append("                <div class=\"input-append\">\n");
      code.append("                <c:if test=\"${!" + readOnlyCondition + "}\" >\n");
      code.append("                    <div class=\"uneditable-input span3\">\n");
      code.append("                      <i class=\"icon-file fileupload-exists\"></i>\n");
      code.append("                      <span class=\"fileupload-preview\"></span>\n");
      code.append("                    </div>\n");
      code.append("                    <span class=\"btn btn-file\">\n");
      code.append("                      <span class=\"fileupload-new\"><fmt:message key=\"genapp.form.file.select\"/></span>\n");
      code.append("                      <span class=\"fileupload-exists\"><fmt:message key=\"genapp.form.file.change\"/></span>\n");
      code.append("                      <form:input " + formInputReadOnly + " path=\"" + modelCamp + "\" type=\"file\" />\n");
      code.append("                    </span>\n");
      code.append("                    <a href=\"#\" class=\"btn fileupload-exists\" data-dismiss=\"fileupload\"><fmt:message key=\"genapp.form.file.unselect\"/></a>\n");
      code.append("                    <span class=\"add-on\">&nbsp;</span>\n");
      code.append("                </c:if>\n");
      code.append("                <c:if test=\"${not empty " + instanceForm + "." + model + "." + fileObj + "}\">\n");
      if (!field.isNotNullable) {
        code.append("                <c:if test=\"${!" + readOnlyCondition + "}\" >\n");
        code.append("                    <span class=\"add-on\">\n");
        code.append("                        <form:checkbox path=\"" + modelCamp + "Delete\"/>\n");
        code.append("                        <fmt:message key=\"genapp.form.file.delete\"/>\n");
        code.append("                    </span>\n");
        code.append("                    <span class=\"add-on\">&nbsp;</span>   \n");
        code.append("                </c:if>\n");  
      }

      code.append("                    <span class=\"add-on\">\n");
      // TODO FER UN MÈTODE QUE PASSAT UN FITXER RETORNI EL NOM
      code.append("                        <a target=\"_blank\" href=\"<c:url value=\"${" + project.getPrefix().toLowerCase() + ":fileUrl(" + instanceForm + "." + model + "." + fileObj + ")}\"/>\">${" + instanceForm + "." + model + "." + fileObj + ".nom}</a>\n");
      code.append("                    </span>\n");
      code.append("                </c:if>\n");
      code.append("                </div>\n");
      code.append("              </div>\n");

      return code.toString();
    }
    
    if (translationFields.contains(field)) {
      String simpleName = modelCamp.substring(0, modelCamp.length() - 2); // Llevar ID
      String fullmodel = model + "." + simpleName;
      code.append("       <form:errors path=\"" + fullmodel + "\" cssClass=\"errorField alert alert-error\" />\n");
      code.append("       <div class=\"tabbable\">\n");
      code.append("         <ul class=\"nav nav-tabs\" style=\"margin-bottom: 3px;\">\n");
      code.append("             <c:forEach items=\"${__theForm.idiomesTraduccio}\" var=\"idioma\" varStatus=\"counter\">\n");
      code.append("               <li class=\"${(counter.index == 0)? 'active':''}\"  ><a href=\"#${counter.index}_tab_" + simpleName + "_${idioma.idiomaID}\" data-toggle=\"tab\">${idioma.nom}</a></li>\n");
      code.append("           </c:forEach>\n");
      code.append("           \n");
      code.append("         </ul>\n");
      code.append("         <div class=\"tab-content\">\n");
      code.append("           <c:forEach items=\"${__theForm.idiomesTraduccio}\" var=\"idioma\" varStatus=\"counter\">\n");
      code.append("           <div class=\"tab-pane ${(counter.index == 0)? 'active':'' }\" id=\"${counter.index}_tab_" + simpleName + "_${idioma.idiomaID}\">\n");
      code.append("               <form:errors path=\"" + fullmodel + ".traduccions['${idioma.idiomaID}'].valor\" cssClass=\"errorField alert alert-error\"/>\n");
      String maxlength="maxlength=\"4000\"";
      code.append("               <form:input path=\"" + fullmodel + ".traduccions['${idioma.idiomaID}'].valor\" " + formInputReadOnly.replace("'input", "'input-xxlarge") + " " + maxlength + " />\n");
      /*
      code.append("               " +
      		"<form:input path=\"" + fullmodel + ".traduccions['${idioma.idiomaID}'].valor\" cssClass=\"${gen:contains(__theForm.readOnlyFields ,Prova2Fields.NOM2TRADUCCIOID)? 'input-xxlarge uneditable-input' : 'input-xxlarge'}\"/>\n");
      		*/
      code.append("           </div>\n");
      code.append("           </c:forEach>\n");
      code.append("         </div>\n");
      code.append("       </div>\n");
      code.append("\n");
      		
      return code.toString();
    }
    
    
  
    int webType = field.getWebFieldInfo().getWebtype();
    switch (webType) {

    case WebType.PrimaryKey:
      if (field.isAutoIncrement) {
        return null;
      }
    case WebType.URL:
    case WebType.Text:
    case WebType.Integer:
    case WebType.Decimal:
    
      String maxlength="";
      if (field.getJavaType().equals(String.class)) {
        maxlength="maxlength=\"" + field.size + "\"";
      }
      // TODO maxlength = String.valueOf(Integer.MAX_VALUE).length() DEPEN DE TIPUS !!!
      // TODO maxlength = String.valueOf(Double.MAX_VALUE).length() DEPEN DE TIPUS !!! 
      
      // TODO tamany via css segons el size de BBDD
      String newInputSize;
      if (webType == WebType.Integer || webType == WebType.Decimal) {
        newInputSize = getCssInputTypeBySize(field.getDigits());
      } else {
        newInputSize = getCssInputTypeBySize(field.getSize());
      }
      
      //final String condiURL = readOnlyCondition + " && not empty " + instanceForm + "." + model + "." + modelCamp;
      //final String onClick = "window.open('${" + instanceForm + "." + model + "." + modelCamp + "}', '_blank')";
      if (webType == WebType.URL) {
        code.append("           <c:if test=\"${" + readOnlyCondition + "}\">\n\n");
        code.append("             <c:if test=\"${ not empty " + instanceForm + "." + model + "." + modelCamp + "}\">\n");
        code.append("               <a href=\"${" + instanceForm + "." + model + "." + modelCamp + "}\" target=\"_blank\">"
            + "${" + instanceForm + "." + model + "." + modelCamp + "}</a>\n\n");
        code.append("             </c:if>\n");
        code.append("           </c:if>\n\n");
        code.append("           <c:if test=\"${not (" + readOnlyCondition + ")}\">\n\n");
      }
      
      code.append("            <form:errors path=\"" + model + "." + modelCamp + "\" cssClass=\"errorField alert alert-error\" />\n");
      //code.append("            <div class=\"input-append\">\n"); 
      code.append("            <form:input " + formInputReadOnly.replace("'input", "'" + newInputSize) + " " + maxlength 
          + " path=\"" + model + "." + modelCamp + "\" "
             // + ((webType == WebType.URL)?" onClick=\"${__onClick}\" ": "")
              + "  />\n\n");
      
      if (webType == WebType.URL) {
        code.append("           </c:if>\n\n");
      //code.append("            <c:if test=\"${" + condiURL + "}\">\n");
      //code.append("            <button class=\"btn\" onClick=\"${__onClick}; return false;\"><i class=\"icon-download-alt\"></i></button>\n");
      //code.append("            </c:if>\n"); 
      }
      //code.append("            </div>\n");
      
      
      return code.toString();

    case WebType.ComboBox:
    case WebType.Query:
      String id = model + "_" + modelCamp;
      
      String fkTableName = "Values";
      if (webType == WebType.Query) {
        ForeignKey fk = field.getWebFieldInfo().getForeignKey();
        TableInfo fkTable = CodeGenUtils.findTableInfoByTableSQLName(project, fk.getTable());
        fkTableName = fkTable.getNameJava();
        
        // Cercam si la taula de referències es traduible
        // WWW boolean traduir =  
        BackGenerator.fillReferences(fkTable, new StringBuffer());
        
      }
      String name = Character.toUpperCase(field.javaName.charAt(0))
      + field.javaName.substring(1);
      
      
      code.append("          <form:errors path=\"" + model + "." + modelCamp + "\" cssClass=\"errorField alert alert-error\" />\n");
      
      code.append("          <c:if test=\"${" + readOnlyCondition + "}\" >\n");
      code.append("          <form:hidden path=\"" + model + "." + modelCamp + "\"/>\n");

      code.append("          <input type=\"text\" readonly=\"true\" class=\"input-xxlarge uneditable-input\" " 
              + "value=\"");
      /* WWW
      if (traduir) {
        code.append("<fmt:message key=\"");        		
      }
      */
      code.append("${gen:findValue(" + instanceForm + "." + model + "." + modelCamp + "," + instanceForm + ".listOf"+ fkTableName + "For" + name +")}"); 
      /* WWW
      if (traduir) {
        code.append("\" />");
      }
      */
      code.append("\"  />\n");      
      code.append("          </c:if>\n");
      code.append("          <c:if test=\"${!" + readOnlyCondition + "}\" >\n");
      
      code.append("          <form:select id=\"" + id + "\"  onchange=\"if(typeof onChange" + name+ " == 'function') {  onChange" + name+ "(this); };\"  cssClass=\"input-xxlarge\" path=\"" + model + "." + modelCamp + "\">\n");      
      if (!field.isNotNullable()) {
        code.append("          <%-- El camp pot ser null, per la qual cosa afegim una entrada buida --%>\n");
        code.append("          <form:option value=\"\" ></form:option>\n");
      }
      code.append("            <c:forEach items=\"${" + instanceForm + ".listOf"+ fkTableName +"For" + name + "}\" var=\"tmp\">\n");
      code.append("            <form:option value=\"${tmp.key}\" >");
      /* WWW
      if (traduir) {
        code.append("<fmt:message key=\"${tmp.value}\" />");
      } else */ {
        code.append("${tmp.value}");
      }
      code.append("</form:option>\n");
      // selected=\"${(" + model + "Form." + model + "." + field.getJavaName()+ " eq tmp.key)? 'selected' : '' }
      code.append("            </c:forEach>\n");
      code.append("          </form:select>\n");
      code.append("          </c:if>\n");
      //code.append(generateReadOnlySelect("              ", id, readOnlyCondition));
      
      
      return code.toString();
      
    case WebType.TextArea:
    case WebType.RichText: 
      code.append("              <form:errors path=\"" + model + "." + modelCamp + "\" cssClass=\"errorField alert alert-error\" />\n");
      code.append("              <form:textarea");
      if (webType == WebType.RichText) {
        code.append(" cssClass=\"input-xxlarge ${" + readOnlyCondition + "? 'mceEditorReadOnly':'mceEditor'}\"");
      } else {
        code.append(" rows=\"3\" wrap=\"soft\" style=\"overflow:auto;\" cssClass=\"input-xxlarge\" "
            + readOnlyAttribute);
        
            //formInputReadOnly.replace("'input", "'input-xxlarge") + " ");
      }
      code.append(" path=\"" + model + "." + modelCamp + "\"  />\n");
      return code.toString();
     
      
    case WebType.Checkbox:
      String prefix = field.getMinAllowedValue();
      String name2 = Character.toUpperCase(field.javaName.charAt(0))
      + field.javaName.substring(1);
      if (prefix == null || prefix.trim().length() == 0) {
        prefix = null;
      }
      code.append("          <c:if test=\"${!" + readOnlyCondition + "}\" >\n");
      if (field.isNotNullable && prefix == null) {
        prefix = "genapp.checkbox";
        code.append("              <form:errors path=\"" + model + "." + modelCamp + "\" cssClass=\"errorField alert alert-error\" />\n");        
        code.append("              <form:checkbox onclick=\"javascript:return ${ " + readOnlyCondition + "? 'false' : 'true'}\" path=\"" + model + "." + modelCamp + "\" />\n");
      } else {
        if (prefix == null) {
          prefix = "genapp.checkbox";
        }
        code.append("              <form:select cssClass=\"input-medium\" onchange=\"if(typeof onChange" + name2 + " == 'function') {  onChange" + name2+ "(this); };\"  path=\"" + model + "." + modelCamp + "\">\n");
        if (!field.isNotNullable) {
          code.append("                <form:option value=\"\"><fmt:message key=\"" + prefix + ".\" /></form:option>\n");
        }
        code.append("                <form:option value=\"true\" ><fmt:message key=\"" + prefix + ".true\" /></form:option>\n");
        code.append("                <form:option value=\"false\" ><fmt:message key=\"" + prefix + ".false\" /></form:option>\n");
        code.append("              </form:select>\n");
      }
      code.append("          </c:if>\n"); 
      code.append("          <c:if test=\"${" + readOnlyCondition + "}\" >\n");
      code.append("                <fmt:message key=\"" + prefix + ".${" + instanceForm + "." + model + "." + modelCamp + "}\" />\n");
      code.append("          </c:if>\n");
      return code.toString();
      
    case WebType.Date:
      code.append("              <form:errors path=\"" + model + "." + modelCamp + "\" cssClass=\"errorField alert alert-error\" />\n");
      code.append("              <div id=\"" + modelCamp + "\" class=\"input-append\">\n");
      code.append("                <form:input " + formInputReadOnly.replace("'input", "'input-small") + " path=\"" + model + "." + modelCamp + "\" />\n");
      code.append("                <c:if test=\"${!" + readOnlyCondition + "}\" >\n");
      code.append("                <span class=\"add-on\">\n");
      code.append("                  <i data-time-icon=\"icon-time\" data-date-icon=\"icon-calendar\">\n");
      code.append("                  </i>\n");
      code.append("                </span>\n");
      code.append("              </c:if>\n"); 
      code.append("              </div>\n");
      code.append("              <script type=\"text/javascript\">                \n");
      code.append("                $(function() {\n");
      code.append("                  $('#" + modelCamp + "').datetimepicker({\n");
      code.append("                    language: '${lang}',\n");
      code.append("                    pick12HourFormat: <c:out value=\"${fn:contains(gen:getDatePattern(), 'a')?'true' : 'false'}\"/>,\n");
      code.append("                    format:  '${gen:getJSDatePattern()}',\n");
      code.append("                    pickTime: false,\n");
      code.append("                    weekStart: ${gen:getFirstDayOfTheWeek()}\n");
      code.append("                  });\n");
      code.append("                });\n");
      code.append("              </script>\n");
      return code.toString();
      
    case WebType.Time:
      code.append("              <form:errors path=\"" + model + "." + modelCamp + "\" cssClass=\"errorField alert alert-error\" />\n");
      code.append("              <div id=\"" + modelCamp + "\" class=\"input-append\">\n");
      code.append("                <form:input " + formInputReadOnly.replace("'input", "'input-small") + " path=\"" + model + "." + modelCamp + "\" />\n");
      code.append("                <c:if test=\"${!" + readOnlyCondition + "}\" >\n");
      code.append("                <span class=\"add-on\">\n");
      code.append("                  <i data-time-icon=\"icon-time\" data-date-icon=\"icon-calendar\">\n");
      code.append("                  </i>\n");
      code.append("                </span>\n");
      code.append("              </c:if>\n"); 
      code.append("              </div>            \n");
      code.append("              <script type=\"text/javascript\">                \n");
      code.append("                $(function() {\n");
      code.append("                  $('#" + modelCamp + "').datetimepicker({\n");
      code.append("                    language: '${lang}', \n");
      code.append("                    pick12HourFormat: <c:out value=\"${fn:contains(gen:getTimePattern(), 'a')?'true' : 'false'}\"/>,\n");
      code.append("                    format:  '${gen:getJSTimePattern()}',\n");
      code.append("                    pickDate: false,\n");
      code.append("                  });\n");
      code.append("                });\n");
      code.append("              </script>\n");
      return code.toString();
      
    case WebType.DateTime:
      code.append("              <form:errors path=\"" + model + "." + modelCamp + "\" cssClass=\"errorField alert alert-error\" />\n");
      code.append("              <div id=\"" + modelCamp + "\" class=\"input-append\">\n");
      code.append("                <form:input " + formInputReadOnly.replace("'input", "'input-medium") + " path=\"" + model + "." + modelCamp + "\" />\n");
      code.append("                <c:if test=\"${!" + readOnlyCondition + "}\" >\n");
      code.append("                <span class=\"add-on\">\n");
      code.append("                  <i data-time-icon=\"icon-time\" data-date-icon=\"icon-calendar\">\n");
      code.append("                  </i>\n");
      code.append("                </span>\n");
      code.append("              </c:if>\n"); 
      code.append("              </div>\n");
      code.append("              <script type=\"text/javascript\">                \n");
      code.append("                $(function() {\n");
      code.append("                  $('#" + modelCamp + "').datetimepicker({\n");
      code.append("                    language: '${lang}',\n");
      code.append("                    pick12HourFormat: <c:out value=\"${fn:contains(gen:getDateTimePattern(), 'a')?'true' : 'false'}\"/>,\n");
      code.append("                    format:  '${gen:getJSDateTimePattern()}',\n");
      code.append("                    pickTime: true,\n");
      code.append("                    weekStart: ${gen:getFirstDayOfTheWeek()}\n");
      code.append("                  });\n");
      code.append("                });\n");
      code.append("              </script>\n");
      return code.toString();
      
      // TODO S'han de fer que tots els camps que referenciin a la taula fitxer 
      // canviin el tipus web a FILE 
    case WebType.File:      
    case WebType.UserID:
    case WebType.RoleID:
      throw new Exception("Els tipus WEB UserID i RoleID no estan suportats !!!!!");
      
    default:
      throw new Exception("NO existix generador de FORM_JSP pel camp "
          + field.javaName + " de tipus " + field.getClass());
      
    } // Final Switch
   
  }
  
  /*
  public static String generateReadOnlySelect(String tab, String id,
      String readOnlyFieldCondition) {

    StringBuffer code = new StringBuffer();
    code.append(tab).append("<c:if test=\"${" + readOnlyFieldCondition + "}\" >\n");
    code.append(tab).append("<script language=\"javascript\">\n");
    code.append(tab).append("   $(\"#" + id + " option\").not(\":selected\").attr(\"disabled\", \"disabled\");\n");
    code.append(tab).append("</script>\n");
    code.append(tab).append("</c:if>\n");

    return code.toString();
  }
  */
  
  
  public static String getCssInputTypeBySize(int size) {
    if (size < 8) {
      return "input-mini";
    }
    if (size < 11) {
      return "input-small";
    }
    if (size < 18) {
      return "input-medium";
    }
    if (size < 26) {
      return "input-large";
    }
    if (size < 33) {
      return "input-xlarge";
    }
    
    return "input-xxlarge";
    
    
  }

}

