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
import org.fundaciobit.genapp.common.web.html.HtmlCSS;
import org.fundaciobit.genapp.common.web.html.IconUtils;

/**
 * 
 * @author anadal
 *
 */
public class BackWebGenerator extends IconUtils {

	public static List<SourceFile> generateListJsp(String packageControllerBack, String packageFormBack,
			String packageValidatorBack, String jpaPackage, String ejbPackage, BasicPackages packages, Project project,
			Map<String, BeanCode> beanCodeBySqlTableName) throws Exception {

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

			// BeanCode beanGenCode = beanCodeBySqlTableName.get(tables[i].name);

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
				pkMapping = "/${" + model + "." + pk.getJavaName() + "}";
			}

			// =================== CODE FOR BASE LIST ===============
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

				code.append("<form:form name=\"" + model + "\" cssClass=\"form-search\"  modelAttribute=\""
						+ instanceFilterForm + "\" \n");
				code.append("        method=\"${(empty method)?'post':method}\"  enctype=\"multipart/form-data\">\n");
				code.append("\n");

				code.append("  <%@include file=\"" + model + "ListCommon.jsp\" %>\n");

				code.append(
						"  <div id=\"${formName}_listheader\" class=\"filterLine lead\" style=\"margin-bottom:10px\">\n");
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

				code.append("  <c:if test=\"${not empty " + model + "Items && __theFilterForm.footerListVisible}\">\n");
				code.append("          <%@include file=\"webdbPagination.jsp\" %>\n\n");
				code.append("  </c:if>\n");
				code.append("\n");
				code.append("  </div> <%-- Final de div de TAULA CONTINGUTS --%>\n");

				code.append("  <%--  ADD HERE OTHER CONTENT --%>\n\n");

				code.append("  <c:if test=\"${__theFilterForm.attachedAdditionalJspCode}\">\n");
				code.append("          <%@include file=\"../webdbmodificable/" + model + "ListModificable.jsp\" %>\n");
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
				// =================== CODI COMU ===============
				// =======================================================

				StringBuffer codeCommon = new StringBuffer();

				codeCommon.append("<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>\n");
				codeCommon.append("<%@ include file=\"/WEB-INF/jsp/moduls/includes.jsp\"%>\n");
				codeCommon.append("\n");

				codeCommon.append("  <c:set var=\"contexte\" value=\"${" + instanceFilterForm + ".contexte}\"/>\n");
				codeCommon.append("  <c:set var=\"formName\" value=\"" + model + "\" />\n");
				codeCommon.append("  <c:set var=\"__theFilterForm\" value=\"${" + instanceFilterForm + "}\" />\n");

				codeCommon.append("  <c:if test=\"${empty " + instanceFilterForm + ".entityNameCode}\">\n");
				codeCommon.append("    <fmt:message var=\"entityname\" key=\"" + model + "." + model + "\"/>\n");
				codeCommon.append("  </c:if>\n");
				codeCommon.append("  <c:if test=\"${not empty " + instanceFilterForm + ".entityNameCode}\">\n");
				codeCommon.append(
						"    <fmt:message var=\"entityname\" key=\"${" + instanceFilterForm + ".entityNameCode}\"/>\n");
				codeCommon.append("  </c:if>\n");

				codeCommon.append("  <c:if test=\"${empty " + instanceFilterForm + ".entityNameCodePlural}\">\n");
				codeCommon.append("    <fmt:message var=\"entitynameplural\" key=\"" + model + "." + model + "\"/>\n");
				codeCommon.append("  </c:if>\n");
				codeCommon.append("  <c:if test=\"${not empty " + instanceFilterForm + ".entityNameCodePlural}\">\n");
				codeCommon.append("    <fmt:message var=\"entitynameplural\" key=\"${" + instanceFilterForm
						+ ".entityNameCodePlural}\"/>\n");
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
				code.append(
						"  <table class=\"table table-sm table-bordered table-striped table-genapp\" style=\"width:auto;\"> \n");
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
				code.append("        <tr id=\"" + model + "_rowid_${" + pkMapping.substring(3, pkMapping.length() - 1)
						+ "}\">\n");

				code.append("          <%@include file=\"" + model + "ListCoreMultipleSelect.jsp\" %>\n\n");

				code.append("          <%@include file=\"" + model + "ListCoreContent.jsp\" %>\n\n");

				code.append("          <%--  ADD HERE NEW COLUMNS CONTENT --%>\n\n\n");

				code.append("          <%@include file=\"" + model + "ListButtons.jsp\" %>\n\n");

				code.append("\n");
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
				codeMultipleSelect
						.append("      <c:if test=\"${" + instanceFilterForm + ".visibleMultipleSelection}\">\n");
				codeMultipleSelect.append("      <td>\n");
				codeMultipleSelect.append(
						"       <form:checkbox path=\"selectedItems\" value=\"" + pkMapping.substring(1) + "\"/>\n");
				codeMultipleSelect.append("       &nbsp;\n");
				codeMultipleSelect.append("      </td>\n");
				codeMultipleSelect.append("      </c:if>\n\n");

				sourceFiles.add(new SourceFile(model + "ListCoreMultipleSelect.jsp", codeMultipleSelect.toString()));

			}

			{
				StringBuffer codeHeaderMultipleSelect = new StringBuffer();

				codeHeaderMultipleSelect
						.append("      <c:if test=\"${" + instanceFilterForm + ".visibleMultipleSelection}\">\n");
				codeHeaderMultipleSelect.append("      <th>\n");
				codeHeaderMultipleSelect
						.append("         <input type=\"checkbox\" onClick=\"selectUnselectCheckBoxes(this)\" />\n");
				codeHeaderMultipleSelect.append("      </th>\n");
				codeHeaderMultipleSelect.append("      </c:if>\n");

				sourceFiles.add(new SourceFile(model + "ListCoreHeaderMultipleSelect.jsp",
						codeHeaderMultipleSelect.toString()));

			}

			{
				StringBuffer codeSubtitle = new StringBuffer();

				codeSubtitle.append("<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>\n");
				codeSubtitle.append("<%@ include file=\"/WEB-INF/jsp/moduls/includes.jsp\"%>\n");
				codeSubtitle.append("\n");
				codeSubtitle.append("  <c:if test=\"${not empty " + instanceFilterForm + ".subTitleCode}\">\n");
				codeSubtitle.append(
						"<h6 style=\"line-height: 10px; margin-top: -10px; margin-bottom: 10px; font-style:italic;\">\n");
				codeSubtitle.append("<c:set var=\"subtitleTranslated\" value=\"${fn:startsWith(" + instanceFilterForm
						+ ".subTitleCode,'=')}\" />\n");
				codeSubtitle.append("<c:if test=\"${subtitleTranslated}\">\n");
				codeSubtitle.append("   <c:out value=\"${fn:substringAfter(" + instanceFilterForm
						+ ".subTitleCode, '=')}\" escapeXml=\"false\" />\n");
				codeSubtitle.append("</c:if>\n");
				codeSubtitle.append("<c:if test=\"${not subtitleTranslated}\">\n");
				codeSubtitle.append("  <fmt:message key=\"${" + instanceFilterForm + ".subTitleCode}\" />\n");
				codeSubtitle.append("</c:if>\n");
				codeSubtitle.append("</h6>\n");

				codeSubtitle.append("  </c:if>\n");

				sourceFiles.add(new SourceFile(model + "ListSubtitle.jsp", codeSubtitle.toString()));
			}

			{

				// =================== CODE FOR BUTTONS GROUPBY, ORDER BY i ADD ===============
				// =======================================================

				StringBuffer codeHeaderButtons = new StringBuffer();
				codeHeaderButtons.append("<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>\n");
				codeHeaderButtons.append("<%@ include file=\"/WEB-INF/jsp/moduls/includes.jsp\"%>\n");
				codeHeaderButtons.append("\n");

				codeHeaderButtons.append(HtmlCSS.TITOL_BEGIN + "\n");
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
				codeHeaderButtons.append("  </label>\n");
				codeHeaderButtons.append("\n");
				codeHeaderButtons.append("      <%-- AGRUPAR PER BOTO - INICI  --%>\n");
				codeHeaderButtons.append("  <c:if test=\"${fn:length(groupby_items) > 0}\">\n");
				codeHeaderButtons.append("      <c:set var=\"displayGroupBut\" value=\"${" + instanceFilterForm
						+ ".visibleGroupBy?'display:none;':''}\" />\n");
				codeHeaderButtons.append(
						"      <a id=\"GroupButton\" style=\"${displayGroupBut}\" title=\"<fmt:message key=\"genapp.form.groupby\"/>\" onclick=\"document.getElementById('GroupDiv').style.display = 'inherit'; document.getElementById('GroupButton').style.display = 'none';\" class=\"btn btn-sm btn-secondary\" role=\"button\" data-toggle=\"modal\">\n");
				codeHeaderButtons.append("         <img src=\"<c:url value=\"/img/treeicon.png\"/>\"/>\n");
				codeHeaderButtons.append("      </a>\n");
				codeHeaderButtons.append("  </c:if>\n");
				codeHeaderButtons.append("      <%-- AGRUPAR PER BOTO - FINAL  --%>\n");
				codeHeaderButtons.append("\n");
				codeHeaderButtons.append("      <%-- FILTRAR PER BOTO - INICI  --%>\n");
				codeHeaderButtons
						.append("      <c:if test=\"${fn:length(" + instanceFilterForm + ".filterByFields) > 0}\">\n");
				codeHeaderButtons.append("      <c:set var=\"displayFilterBut\" value=\"${" + instanceFilterForm
						+ ".visibleFilterBy?'display:none;':''}\" />\n");
				codeHeaderButtons.append(
						"      <a id=\"FilterButton\" style=\"${displayFilterBut}\" title=\"<fmt:message key=\"genapp.form.filterby\"/>\" onclick=\"document.getElementById('FilterDiv').style.display = 'inherit'; document.getElementById('FilterButton').style.display = 'none';\" class=\"btn btn-sm btn-secondary\" role=\"button\" data-toggle=\"modal\">\n");
				codeHeaderButtons.append("         <i class=\"" + IconUtils.ICON_SEARCH + "\"></i>\n");
				codeHeaderButtons.append("      </a>\n");
				codeHeaderButtons.append("      </c:if>\n");
				codeHeaderButtons.append("      <%-- FILTRAR PER BOTO - FINAL  --%>\n");
				codeHeaderButtons.append("     \n");

				// codeHeaderButtons.append(" <div id=\"${formName}_headerbuttons\">\n");

				// BOTO DE NOU ELEMENT EN LLISTAT
				codeHeaderButtons.append("      <%-- BOTO DE NOU ELEMENT EN LLISTAT  --%>\n");
				codeHeaderButtons.append("    <c:if test=\"${" + instanceFilterForm + ".addButtonVisible}\">\n");
				codeHeaderButtons.append(
						"      <a class=\"btn btn-sm btn-success float-right botoselecciolist\"  style=\"\" role=\"button\" \n"); /// data-toggle=\"modal\"
				codeHeaderButtons.append("        href=\"<c:url value=\"${contexte}/new\"/>\"> <i class=\""
						+ IconUtils.ICON_PLUS_SIGN + "\"></i>\n");
				codeHeaderButtons.append("       <fmt:message key=\"genapp.createtitle\" >\n");
				codeHeaderButtons.append("         <fmt:param value=\"${entityname}\"/>\n");
				codeHeaderButtons.append("       </fmt:message>\n");
				codeHeaderButtons.append("      </a>\n");
				codeHeaderButtons.append("    </c:if>\n");

				// BOTO DE ESBORRAT MULTIPLE
				codeHeaderButtons.append("      <%-- BOTO DE ESBORRAT MULTIPLE  --%>\n");
				codeHeaderButtons.append("    <c:if test=\"${" + instanceFilterForm + ".deleteSelectedButtonVisible && "
						+ instanceFilterForm + ".visibleMultipleSelection && not empty " + model + "Items}\">\n");
				codeHeaderButtons.append(
						"      <a class=\"btn btn-danger btn-sm float-right botoselecciolist\" style=\"\" href=\"#myModal\"\n");
				codeHeaderButtons.append(
						"        onclick=\"openModalSubmit('<c:url value=\"${contexte}/deleteSelected\"/>','show', '"
								+ model + "');\"\n");
				codeHeaderButtons.append("        title=\"<fmt:message key=\"genapp.delete\"/>\">\n");
				codeHeaderButtons.append("        <i class=\"" + getWhite(ICON_TRASH) + "\"></i>\n");
				codeHeaderButtons.append("        <fmt:message key=\"genapp.delete.selected\" />\n");
				codeHeaderButtons.append("      </a>\n");
				codeHeaderButtons.append("    </c:if>\n");

				// BOTONS ADICIONALS
				// codeHeaderButtons.append(" <div id=\"" + model +
				// "_list_buttons_header\">\n");
				codeHeaderButtons.append(
						"    <c:forEach var=\"button\" items=\"${" + instanceFilterForm + ".additionalButtons}\">\n");
				codeHeaderButtons.append("      <c:set var=\"thelink\" value=\"${button.link}\" />\n");
				codeHeaderButtons.append("     <c:set var=\"thehref\" value=\"#\"/>\n");
				codeHeaderButtons.append("      <c:if test=\"${!fn:startsWith(thelink,'javascript:')}\">\n");
				codeHeaderButtons.append("        <c:url var=\"thehref\" value=\"${thelink}\"/>\n");

				// NOU Ticket #17
				codeHeaderButtons.append("        <c:url var=\"thelink\" value=\"\"/>\n");
				// codeHeaderButtons.append(" <c:url var=\"thelink\" value=\"${thelink}\"/>\n");
				// codeHeaderButtons.append(" <c:set var=\"thelink\"
				// value=\"goTo('${thelink}')\"/>\n");

				codeHeaderButtons.append("      </c:if>\n");

				// #17 codeHeaderButtons.append(" <button type=\"button\" ");
				codeHeaderButtons.append("<a ");
				codeHeaderButtons.append("class=\"btn btn-sm ${button.type} float-right botoselecciolist\" style=\"\" "
						+ " href=\"${thehref}\" onclick=\"${thelink}\" title=\"<fmt:message key=\"${button.codeText}\"/>\">\n");
				codeHeaderButtons.append("         <i class=\"${button.icon}\"></i>\n");
				codeHeaderButtons.append("         <fmt:message key=\"${button.codeText}\"/>\n");

				// #17 codeHeaderButtons.append(" </button>\n");
				codeHeaderButtons.append("      </a>\n");

				codeHeaderButtons.append("    </c:forEach>\n");

				// codeHeaderButtons.append(" </div>\n");

				codeHeaderButtons.append("  \n");

				sourceFiles.add(new SourceFile(model + "ListHeaderButtons.jsp", codeHeaderButtons.toString()));
			}

			{
				// =================== CODE FOR FILTER BY ===============
				// =======================================================

				StringBuffer codeFilterBy = new StringBuffer();
				codeFilterBy.append("<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>\n");
				codeFilterBy.append("<%@ include file=\"/WEB-INF/jsp/moduls/includes.jsp\"%>\n");
				codeFilterBy.append(
						"<un:useConstants var=\"" + instanceFields + "\" className=\"" + fieldsClass + "\"/>\n");
				codeFilterBy.append("\n");

				codeFilterBy.append("  <%-- HIDDEN PARAMS: FILTER BY --%> \n");
				codeFilterBy.append("  <form:hidden path=\"visibleFilterBy\"/>\n");
				codeFilterBy.append("\n");

				codeFilterBy.append("  <%-- FILTRAR PER - INICI --%>\n");
				codeFilterBy.append("  \n");
				codeFilterBy.append("  <c:set var=\"displayFilterDiv\" value=\"${" + instanceFilterForm
						+ ".visibleFilterBy?'':'display:none;'}\" />  \n");
				codeFilterBy.append("  \n");
				codeFilterBy.append(
						"  <div id=\"FilterDiv\" class=\"wellgroupfilter formbox\" style=\"${displayFilterDiv} margin-bottom:3px; margin-left: 1px; padding:3px;\">\n");
				codeFilterBy.append("\n");
				codeFilterBy.append("      <div class=\"page-header\">\n");
				codeFilterBy.append("        <fmt:message key=\"genapp.form.filterby\"/>\n");
				codeFilterBy.append("        \n");
				codeFilterBy.append("        <div class=\"float-right\">\n");
				codeFilterBy.append("\n");
				codeFilterBy.append(
						"           <a class=\"float-right\" style=\"margin-left:10px\" href=\"#\"> <i title=\"<fmt:message key=\"genapp.form.hidefilter\"/>\" onclick=\"document.getElementById('FilterDiv').style.display='none'; document.getElementById('FilterButton').style.display='inline';\" class=\"far fa-window-close\"></i></a>\n");
				codeFilterBy.append(
						"           <input style=\"margin-left: 3px\" class=\"btn btn-sm btn-warning float-right\" type=\"button\" onclick=\"clear_form_elements(this.form)\" value=\"<fmt:message key=\"genapp.form.clean\"/>\"/>\n");
				codeFilterBy.append(
						"           <input style=\"margin-left: 3px\" class=\"btn btn-sm btn-warning float-right\" type=\"reset\" value=\"<fmt:message key=\"genapp.form.reset\"/>\"/>\n");
				codeFilterBy.append(
						"           <input style=\"margin-left: 3px\" class=\"btn btn-sm btn-primary float-right\" type=\"submit\" value=\"<fmt:message key=\"genapp.form.search\"/>\"/>\n");
				codeFilterBy.append("\n");
				codeFilterBy.append("        </div>\n");
				codeFilterBy.append("      </div>\n");
				codeFilterBy.append("      <div class=\"form-inline\">\n");
				codeFilterBy.append("      \n");

				final String inputgroupcss = "input-group";

				final String searchByAdditional = "      <c:forEach var=\"__entry\" items=\"${__theFilterForm.additionalFields}\">\n"
						+ "      <c:if test=\"${ __entry.key < 0 && not empty __entry.value.searchBy }\">\n"
						+ "      <div class=\"" + inputgroupcss
						+ "\" style=\"padding-right: 4px;padding-bottom: 4px;\">\n"
						+ "        <span class=\"add-on\"><fmt:message key=\"${__entry.value.codeName}\" />:</span>\n"
						+ "        <fmt:message key=\"genapp.form.searchby\" var=\"cercaperAF\" >\n"
						+ "          <fmt:param>\n" + "            <fmt:message key=\"${__entry.value.codeName}\" />\n"
						+ "          </fmt:param>\n" + "        </fmt:message>\n" + "        <c:choose>\n"
						+ "          <c:when test=\"${gen:isFieldSearchInRange(__entry.value.searchBy)}\">\n"
						+ "            <span class=\"add-on\"><fmt:message key=\"genapp.from\" /></span>\n"
						+ "            <input id=\"${__entry.value.searchBy.fullName}\" name=\"${__entry.value.searchBy.fullName}\" class=\"input-small input-medium\" type=\"text\" value=\"${__entry.value.searchByValue}\"/>\n"
						+ "            <span class=\"add-on\"><fmt:message key=\"genapp.to\" /></span>\n"
						+ "            <input id=\"${__entry.value.searchBy.fullName}Fins\" name=\"${__entry.value.searchBy.fullName}Fins\" class=\"input-small input-medium search-query\" type=\"text\" value=\"${__entry.value.searchByValueFins}\"/>\n"
						+ "          </c:when>\n" + "          <c:otherwise>\n"
						+ "            <input id=\"${__entry.value.searchBy.fullName}\" name=\"${__entry.value.searchBy.fullName}\" class=\"search-query input-medium\" placeholder=\"${cercaperAF}\" type=\"text\" value=\"${__entry.value.searchByValue}\"/>\n"
						+ "          </c:otherwise>\n" + "        </c:choose>\n" + "      </div>\n" + "      </c:if>\n"
						+ "      </c:forEach>\n";

				codeFilterBy.append(searchByAdditional);

				codeFilterBy.append("\n\n");
				for (FieldInfo field : fields) {

					if (fileFields.contains(field)) {
						// if (!field.isFilter()) {
						continue;
					}

					// TODO fer filtre per booleà
					Class<?> cls = field.getJavaType();
					if (cls.equals(java.lang.Boolean.class)) {
						continue;
					}

					String modelCampMay = instanceFields + "." + field.getJavaName().toUpperCase();

					codeFilterBy.append("        <c:if test=\"${gen:contains(" + instanceFilterForm
							+ ".filterByFields ," + modelCampMay + ")}\">\n");

					/**
					 * 
					 * NOTA : Hem de permetre filtrar per camps ocults
					 * 
					 * codeFilterBy.append(" <c:if test=\"${!gen:contains(" + instanceFilterForm +
					 * ".hiddenFields," + modelCampMay + ")}\">\n");
					 **/
					try {

						String modelCamp = field.getJavaName();
						if (Number.class.isAssignableFrom(cls) || cls.isPrimitive()) {
						    // NUMERO
						    String name = Character.toUpperCase(field.javaName.charAt(0)) + field.javaName.substring(1);
						    String id = table.getShortName() + "_" + field.javaName + "_select";
						    codeFilterBy.append("            <div class=\"" + inputgroupcss
                                    + "\" style=\"padding-right: 4px;padding-bottom: 4px;\">\n");
						    if (field.getWebFieldInfo().getWebtype() == WebType.ComboBox) {
						        codeFilterBy.append(
						                  "              <%-- FILTRE NUMERO SELECT MULTIPLE --%>\n"
						                + "              <div class=\"input-group-prepend\" style=\"padding-top: 5px;padding-right: 5px;\">\n"
						                + "                 <span class=\"add-on\"><fmt:message key=\"" + model + "." + modelCamp + "\" />:</span>\n"
						                + "              </div>\n"
						                + "\n"
						                + "              <div class=\"input-group-prepend\" style=\"min-width:200px\">\n" 
   						                + "                <form:select id=\"" + id + "\" path=\"" + modelCamp + "Select\" cssClass=\"search-query input-medium form-control select2 select2-hidden-accessible\" multiple=\"true\" style=\"width:100%;\" tabindex=\"-1\" aria-hidden=\"true\">\n"
						                + "                    <c:forEach var=\"_entry\" items=\"${__theFilterForm.mapOfValuesFor" + name + "}\">\n"
						                + "                      <option value=\"${_entry.key}\" ${fn:contains(__theFilterForm."  + modelCamp + "Select, _entry.key)?'selected':''} >${_entry.value}</option>\n"
						                + "                    </c:forEach>\n"
						                + "                </form:select>\n"
						                + "              </div>\n"
						                + "\n"
						                + "              <script type=\"text/javascript\">\n"
						                + "                $(document).ready(function() {\n"
						                + "                    $('#" + id + "').select2({\n"
						                + "                        closeOnSelect: false\n"
						                + "                    });\n"
						                + "                    $('.select2-selection__rendered').css('padding-bottom','5px');\n"
						                + "                });\n"
						                + "              </script>\n"
						                );
						        
						    } else {
						    
    							codeFilterBy.append("            <%-- FILTRE NUMERO DESDE-FINS --%>\n");
    							
    							codeFilterBy.append("              <span class=\"add-on\"><fmt:message key=\"" + model + "."
    									+ modelCamp + "\" />:</span>\n");
    							codeFilterBy.append("\n");
    							codeFilterBy.append(
    									"              <span class=\"add-on\">&nbsp;<fmt:message key=\"genapp.from\" /></span>\n");
    							codeFilterBy.append("              \n");
    							codeFilterBy
    									.append("              <form:input cssClass=\"input-append input-small\" path=\""
    											+ modelCamp + "Desde\" />\n");
    							codeFilterBy.append("\n");
    							codeFilterBy.append("\n");
    							codeFilterBy.append(
    									"              <span class=\"add-on\">&nbsp;<fmt:message key=\"genapp.to\" />&nbsp;</span>\n");
    							codeFilterBy.append("\n");
    							codeFilterBy.append(
    									"              <form:input cssClass=\"input-append input-small search-query\" path=\""
    											+ modelCamp + "Fins\" />\n");
    							codeFilterBy.append("\n");
    							
    							
						    }
						    codeFilterBy.append("            </div>\n");
						    codeFilterBy.append("\n\n");
							continue;
						}
						if (cls.equals(String.class)) {
							codeFilterBy.append("            <%-- FILTRE STRING --%>\n");
							codeFilterBy.append(
									"            <div class=\"input-prepend\" style=\"padding-right: 4px;padding-bottom: 4px;\">\n");
							codeFilterBy.append("              <fmt:message key=\"" + model + "." + modelCamp
									+ "\" var=\"" + modelCamp + "\" />\n");
							codeFilterBy.append("              <fmt:message key=\"genapp.form.searchby\" var=\"cercaper"
									+ modelCamp + "\" >                \n");
							codeFilterBy.append("                 <fmt:param value=\"${" + modelCamp + "}\"/>\n");
							codeFilterBy.append("              </fmt:message>\n");
							codeFilterBy.append("              <span class=\"add-on\"><c:out value=\"${" + modelCamp
									+ "}\" />:</span>\n");
							codeFilterBy.append(
									"              <form:input cssClass=\"search-query input-medium\" placeholder=\"${cercaper"
											+ modelCamp + "}\" path=\"" + modelCamp + "\" />\n");
							codeFilterBy.append("            </div>\n");
							codeFilterBy.append("\n\n");
							continue;
						}
						if (cls.equals(Time.class)) {
							codeFilterBy.append("            <%-- FILTRE TIME --%>      \n");
							codeFilterBy.append("            <div class=\"" + inputgroupcss
									+ "\" style=\"padding-right:4px;padding-bottom:4px;align-items:center;\">\n");
							codeFilterBy.append("              <span class=\"add-on\"><fmt:message key=\"" + model + "."
									+ modelCamp + "\" />:</span>\n");
							codeFilterBy.append("\n");
							codeFilterBy.append(
									"              <span class=\"add-on\">&nbsp;<fmt:message key=\"genapp.from\" /></span>\n");

							codeFilterBy.append(generateTimePicker(modelCamp + "Desde", modelCamp + "Desde"));

							codeFilterBy.append("              \n");
							codeFilterBy.append("              \n");

							codeFilterBy.append(
									"              <span class=\"add-on\">&nbsp;<fmt:message key=\"genapp.to\" />&nbsp;</span>\n");

							codeFilterBy.append(generateTimePicker(modelCamp + "Fins", modelCamp + "Fins"));

							codeFilterBy.append("            </div>\n");

							codeFilterBy.append("\n");

							continue;
						}

						if (cls.equals(Date.class)) {

							codeFilterBy.append("            <%-- FILTRE DATE --%>\n");
							codeFilterBy.append("            <div class=\"" + inputgroupcss
									+ "\" style=\"padding-right:4px;padding-bottom:4px;align-items:center;\">\n");
							codeFilterBy.append("              <span class=\"add-on\"><fmt:message key=\"" + model + "."
									+ modelCamp + "\" />:</span>\n");
							codeFilterBy.append(
									"              <span class=\"add-on\">&nbsp;<fmt:message key=\"genapp.from\" /></span>\n");

							codeFilterBy.append(generateDatePicker(modelCamp + "Desde", modelCamp + "Desde"));

							codeFilterBy.append("\n");
							codeFilterBy.append(
									"              <span class=\"add-on\">&nbsp;<fmt:message key=\"genapp.to\" />&nbsp;</span>\n");

							codeFilterBy.append(generateDatePicker(modelCamp + "Fins", modelCamp + "Fins"));

							codeFilterBy.append("            </div>\n");

							codeFilterBy.append("    \n");
							continue;
						}

						if (cls.equals(Timestamp.class)) {

							codeFilterBy.append("<%-- FILTRE DATE-TIME --%>\n");
							codeFilterBy.append("            <div class=\"" + inputgroupcss
									+ "\" style=\"padding-right:4px;padding-bottom:4px;align-items:center;\">\n");
							codeFilterBy.append("              <span class=\"add-on\"><fmt:message key=\"" + model + "."
									+ modelCamp + "\" />:</span>\n");
							codeFilterBy.append(
									"              <span class=\"add-on\">&nbsp;<fmt:message key=\"genapp.from\" /></span>\n");

							codeFilterBy.append(generateDateTimePicker(modelCamp + "Desde", modelCamp + "Desde"));

							codeFilterBy.append(
									"              <span class=\"add-on\">&nbsp;<fmt:message key=\"genapp.to\" />&nbsp;</span>\n");

							codeFilterBy.append(generateDateTimePicker(modelCamp + "Fins", modelCamp + "Fins"));

							codeFilterBy.append("            </div>\n");

							codeFilterBy.append("\n");

							codeFilterBy.append("    \n");
							continue;
						}
					} finally {
						// Final d'ocultar camp de filtre
						codeFilterBy.append("        </c:if>\n");
						// codeFilterBy.append(" </c:if>\n");
					}

					throw new Exception("NO existix generador de filtre pel camp " + field.javaName + " de la taula "
							+ table.getNameJava() + " de tipus " + field.getJavaType().getName());

				}
				; // Final de for de Filtres
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
				// =================== CODE FOR GROUPBY ===============
				// =====================================================

				StringBuffer codeGroupBy = new StringBuffer();
				codeGroupBy.append("<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>\n");
				codeGroupBy.append("<%@ include file=\"/WEB-INF/jsp/moduls/includes.jsp\"%>\n");
				codeGroupBy.append(
						"<un:useConstants var=\"" + instanceFields + "\" className=\"" + fieldsClass + "\"/>\n");
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
				codeGroupBy.append("  <c:set var=\"displayGroupDiv\" value=\"${" + instanceFilterForm
						+ ".visibleGroupBy?'':'display:none;'}\" />  \n");
				codeGroupBy.append("\n");
				codeGroupBy.append("  <c:if test=\"${fn:length(groupby_items) > 0}\">\n");
				codeGroupBy.append(" <fmt:message var=\"buit\" key=\"genapp.notdefined\" />\n");
				codeGroupBy.append("  \n");
				codeGroupBy.append(
						"  <div id=\"GroupDiv\" class=\"wellgroupfilter\" style=\"${displayGroupDiv} padding:1px; margin-right:4px; float:left; \">\n");
				codeGroupBy.append("      <div  style=\"text-align:right\">" + "\n"); // class=\"float-right\" >\n"); //
																						// style=\"padding-left:2px\"

				// codeGroupBy.append(" <div class=\"span10\">\n");
				// codeFilterBy.append(" <div class=\"float-right\">\n");
				// codeFilterBy.append("\n");
				codeGroupBy.append("           <a style=\"margin-right:4px\" href=\"#\">" + "\n");
				codeGroupBy.append(
						"               <i title=\"<fmt:message key=\"genapp.form.hidegroupby\"/>\" onclick=\"document.getElementById('GroupDiv').style.display='none'; document.getElementById('GroupButton').style.display='inline';\" class=\"far fa-window-close\"></i>\n");
				codeGroupBy.append("            </a>\n");
				codeGroupBy.append("      </div>\n");
				codeGroupBy.append("\n");
				codeGroupBy.append("\n");

				codeGroupBy.append("<style>" + " ul.gj-list-bootstrap li [data-role=display]{padding:0px 0px 0px 0px;} "
						+ " ul.gj-list-bootstrap li [data-role=expander].gj-tree-material-icons-expander { padding-top: 0px; padding-bottom: 0px; }"
						+ " </style>");

				codeGroupBy.append("      <div id=\"tree\"></div>\n" + "      \n" + "      <script>\n" + "      \n"
						+ "      \n" + "      <c:set var=\"expandID\" value=\"-\" />\n" + "      \n"
						+ "      var treedata =\n" + "            [{\n" + "                \"id\": '-1',\n"
						+ "                \"text\": \"<b><fmt:message key=\"genapp.form.groupby\"/></b>\",\n"
						+ "                \"field\": null,\n" 
						+ "                \"hasChildren\": true,\n"
						+ "                \"children\": [{\n"
						+ "                        \"id\": \"${groupby_item.value}\",\n"
						+ "                        \"text\": \"&#8811; <span style='${(__theFilterForm.groupBy eq null)? \"font-weight: bold;\" : \"\"}'><fmt:message key=\"genapp.form.groupby.noneitem\"/></span>\",\n"
						+ "                        \"field\": ' ',\n"
						+ "                        \"hasChildren\": false,\n"
						+ "                        \"children\": []\n" + "                    }\n"
						+ "                    <c:set var=\"counterParent\" value=\"0\" />\n"
						+ "                    <c:forEach  var=\"groupby_item\"  items=\"${groupby_items}\">\n"
						+ "                    \n"
						+ "                        <c:set var=\"code\" value=\"${(empty __theFilterForm.labels[groupby_item.field])? groupby_item.codeLabel:__theFilterForm.labels[groupby_item.field]}\" />\n"
						+ "                        <c:if test=\"${!fn:startsWith(code,'=')}\" >\n"
						+ "                        <fmt:message var=\"thetext\" key=\"${code}\">\n"
						+ "                              <fmt:param><fmt:message key=\"${groupby_item.codeParamLabel}\"/></fmt:param>\n"
						+ "                        </fmt:message>\n" + "                        </c:if>\n"
						+ "                        <c:if test=\"${fn:startsWith(code,'=')}\" >\n"
						+ "                        <c:set var=\"thetext\" value=\"${fn:substringAfter(code, '=')}\" />\n"
						+ "                        </c:if>\n" + "                    \n"
						+ "                    <c:set var=\"ParentID\" value=\"${groupby_item.value}_${counterParent}\" />\n"
						+ "                    <c:set var=\"counterParent\" value=\"${counterParent + 1}\" />\n"
						+ "                    \n" + "                    <c:if test=\"${groupby_item.selected}\" >\n"
						+ "                      <c:set var=\"expandID\" value=\"${ParentID}\"/>\n"
						+ "                    </c:if>\n" + "                    ,{\n"
						+ "                        \"id\": '${ParentID}',\n"
						+ "                        \"text\": \"<span style='${groupby_item.selected? \"font-weight: bold;\" : \"\"}'><c:out value=\"${thetext}\"/></span>\",\n"
						+ "                        \"field\": '<c:out value=\"${groupby_item.value}\"/>',\n"
						+ "                        \"hasChildren\": true,\n"
						+ "                        \"children\": [\n" + "                            \n"
						+ "                            <c:set var=\"counterG\" value=\"0\" />\n"
						+ "                            \n"
						+ "                            <c:forEach  var=\"groupbyvalue_item\"  items=\"${groupby_item.values}\">\n"
						+ "                            \n" + "                            \n"
						+ "                            \n"
						+ "                            <c:if test=\"${counterG ne 0}\">,</c:if>\n"
						+ "                            <c:set var=\"counterG\" value=\"${counterG + 1}\" />\n"
						+ "                            {\n"
						+ "                                \"id\": '<c:out value=\"${groupby_item.value}_${groupbyvalue_item.value}_${counterG}\"/>',\n"
						+ "                                \"text\": '<span style=\"${groupbyvalue_item.selected? \"font-weight: bold;\" : \"\"}\" ><c:out value = \"${ (empty groupbyvalue_item.codeLabel) ? buit : groupbyvalue_item.codeLabel}\" /> (${groupbyvalue_item.count})</span>',\n"
						+ "                                \"field\": '${groupby_item.value}',\n"
						+ "                                \"value\" : '<c:out value = \"${groupbyvalue_item.value}\"/>',\n"
						+ "                                \"hasChildren\": false,\n"
						+ "                                \"children\": []\n" + "                            }\n"
						+ "                            \n" + "                          </c:forEach>\n"
						+ "                            \n" + "                        ]\n"
						+ "                    }   \n" + "                    \n" + "                    </c:forEach>\n"
						+ "\n" + "                ]\n" + "            }];\n" + "\n"
						+ "      var tree = $('#tree').tree({\n" + "          dataSource: treedata,\n"
						+ "          primaryKey: 'id',\n" + "          uiLibrary: 'bootstrap4',\n"
						+ "          select: function (e, node, id) {\n"
						+ "              var nodedata = tree.getDataById(id);\n"
						+ "              if (!nodedata.hasChildren) {\n" +
						// ,"'","%27"
						// " //alert('select is fired for node with id=' + id + \"::\" + nodedata.text +
						// \" | Field: \" + nodedata.field + \" | Value: \" + nodedata.value);\n" +
						"                  groupByFieldValue(nodedata.field, nodedata.value);                  \n"
						+ "              }\n" + "          },\n" + "          icons: {\n"
						+ "              expand: '<i class=\"far fa-plus-square\"></i>',\n"
						+ "              collapse: '<i class=\"far fa-minus-square\"></i>'\n" + "          }\n"
						+ "        });\n" + "      \n" + "          var noderoot = tree.getNodeById('-1');\n"
						+ "          tree.expand(noderoot);\n" + "       <c:if test=\"${expandID ne '-'}\" >\n"
						+ "          var node = tree.getNodeById('${expandID}');\n" + "          tree.expand(node);\n"
						+ "          \n" +

						"       </c:if>\n" + "      \n" + "      \n" + "      </script>");

				codeGroupBy.append("\n");
				codeGroupBy.append("  </div>\n");
				codeGroupBy.append(" </c:if>\n");
				codeGroupBy.append(" \n");
				codeGroupBy.append("  <%-- AGRUPAR PER - FINAL --%>\n");
				codeGroupBy.append("\n");

				sourceFiles.add(new SourceFile(model + "ListGroupBy.jsp", codeGroupBy.toString()));

			}

			// ============================ LIST_EMPTY
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

			// ============================ LIST_CORE HEADER TABLE
			{
				StringBuffer codeHeader = new StringBuffer();

				codeHeader.append("<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>\n");
				codeHeader.append("<%@ include file=\"/WEB-INF/jsp/moduls/includes.jsp\"%>\n");
				codeHeader.append(
						"<un:useConstants var=\"" + instanceFields + "\" className=\"" + fieldsClass + "\"/>\n");
				codeHeader.append("  \n");

				String additionalFieldsHeaderUp = "\n\n        <c:forEach var=\"__entry\" items=\"${__theFilterForm.additionalFields}\">\n"
						+ "        <c:if test=\"${ __entry.key < 0 && ((empty __entry.value.searchBy)? true : !gen:contains(__theFilterForm.hiddenFields, __entry.value.searchBy)) && ((empty __entry.value.groupBy )? true : !gen:contains(__theFilterForm.hiddenFields, __entry.value.groupBy ))}\">\n"
						+ "        <th>\n" + "        ${" + project.getPrefix().toLowerCase()
						+ ":getSortIconsAdditionalField(__theFilterForm,__entry.value)}\n"
						// + " <fmt:message key=\"${__entry.value.codeName}\" />\n"
						+ "        </th>\n" + "        </c:if>\n" + "        </c:forEach>\n\n";

				String additionalFieldsHeaderDown = additionalFieldsHeaderUp.replace("< 0", ">=0");

				codeHeader.append(additionalFieldsHeaderUp);

				for (FieldInfo field : fields) {
					String modelCampMay = instanceFields + "." + field.getJavaName().toUpperCase();
					codeHeader.append("        <c:if test=\"${!gen:contains(" + instanceFilterForm + ".hiddenFields,"
							+ modelCampMay + ")}\">\n");
					codeHeader.append("        <th>${" + project.getPrefix().toLowerCase() + ":getSortIcons("
							+ instanceFilterForm + "," + modelCampMay + ")}</th>\n");
					codeHeader.append("        </c:if>\n");
				}

				codeHeader.append(additionalFieldsHeaderDown);

				sourceFiles.add(new SourceFile(model + "ListCoreHeader.jsp", codeHeader.toString()));

			}

			//

			// ============================ LIST_BUTTONS_HEADER
			String testDeFerVisibleBotons = "${" + instanceFilterForm + ".editButtonVisible " 
			        + "|| "  + instanceFilterForm + ".viewButtonVisible " + "||"
					+ instanceFilterForm + ".deleteButtonVisible" + " || not empty " + instanceFilterForm
					+ ".additionalButtonsForEachItem" + " || not empty " + instanceFilterForm
					+ ".additionalButtonsByPK}";
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

			// ============================ LIST_BUTTONS
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
				 * <c:when test="${number1 < number2}"> ${"number1 is less than number2"}
				 * </c:when> <c:when test="${number1 <= number3}">
				 * ${"number1 is less than equal to number2"} </c:when>
				 */

				ActionsRenderer[] renderers = new ActionsRenderer[] { new ActionsRendererSimpleIconList(),
						new ActionsRendererDropDownButton() };

				for (ActionsRenderer render : renderers) {

					codeButtons.append("           <c:when test=\"${" + instanceFilterForm + ".actionsRenderer == "
							+ render.getID() + "}\">\n");
					// ****
					// codeButtons.append(" <div class=\"btn-group\"
					// data-toggle=\"buttons-checkbox\">\n");

					codeButtons.append("            " + render.getHeader(instanceFilterForm));

					// BOTO D'EDITAR
					codeButtons.append("            <c:if test=\"${" + instanceFilterForm + ".editButtonVisible}\">\n");

					codeButtons.append(render.getActionButtonCode("            ", "btn-warning",
							"<c:url value=\"${contexte}" + pkMapping + "/edit\"/>", null, ICON_EDIT, "genapp.edit"));
					codeButtons.append("            </c:if>\n");

					
	                // BOTO DE VISTA
                    codeButtons.append("            <c:if test=\"${" + instanceFilterForm + ".viewButtonVisible}\">\n");

                    codeButtons.append(render.getActionButtonCode("            ", "btn-info",
                            "<c:url value=\"${contexte}/view" + pkMapping + "\"/>", null, ICON_EYE, "genapp.viewtitle"));
                    codeButtons.append("            </c:if>\n");
					
					
					// BOTO DE BORRAR
					codeButtons
							.append("            <c:if test=\"${" + instanceFilterForm + ".deleteButtonVisible}\">\n");

					codeButtons.append(render.getActionButtonCode("            ", "btn-danger", "#myModal",
							"openModal('<c:url value=\"${contexte}" + pkMapping + "/delete\"/>','show');",
							getWhite(ICON_TRASH), "genapp.delete"));
					codeButtons.append("            </c:if>\n");

					final String commonCodeAddBut = "                  <c:set var=\"thehref\" value=\"#\"/>\n"
							+ "                  <c:set var=\"thelink\" value=\"${fn:replace(button.link,bracket, pk)}\" />\n"
							+ "                  <c:if test=\"${!fn:startsWith(thelink,'javascript:')}\">\n"
							+ "                  <c:url var=\"thehref\" value=\"${thelink}\"/>\n"
							+ "                  <c:url var=\"thelink\" value=\"\"/>\n"
							// + " <c:url var=\"thelink\" value=\"${thelink}\"/>\n"
							// + " <c:set var=\"thelink\" value=\"goTo('${thelink}')\"/>\n"
							+ "                  </c:if>\n"
							/*
							 * + "            <a class=\"btn ${button.type} \" href=\"#\" \n" +
							 * "              onclick=\"${thelink}\" \n" +
							 * "              title=\"<fmt:message key=\"${button.codeText}\"/>\">\n" +
							 * "              <i class=\"${button.icon}\"></i>\n" + "            </a>\n"
							 */
							+ render.getActionButtonCode("                  ", "${button.type}", "${thehref}",
									"${thelink}", "${button.icon}", "${button.codeText}");

					// BOTONS ADICIONALS PER CADA ITEM
					codeButtons.append("            <c:set var=\"bracket\" value=\"{0}\"/>\n");

					codeButtons.append("            <c:forEach var=\"button\" items=\"${" + instanceFilterForm
							+ ".additionalButtonsForEachItem}\">\n");
					codeButtons.append(commonCodeAddBut);
					codeButtons.append("            </c:forEach>\n\n");

					// BOTONS ADICIONALS BY PK
					codeButtons.append("            <c:if test=\"${not empty " + instanceFilterForm
							+ ".additionalButtonsByPK}\">\n");
					codeButtons.append("              <c:if test=\"${not empty " + instanceFilterForm
							+ ".additionalButtonsByPK[pk]}\">\n");
					// codeButtons.append(" <c:set var=\"button\" value=\"${" + instanceFilterForm +
					// ".additionalButtonsByPK[pk]}\"/>\n");

					codeButtons.append("                  <c:forEach var=\"button\" items=\"${" + instanceFilterForm
							+ ".additionalButtonsByPK[pk]}\">\n");
					codeButtons.append(commonCodeAddBut);
					codeButtons.append("                  </c:forEach>\n\n");

					codeButtons.append("               </c:if>\n\n");
					codeButtons.append("            </c:if>\n\n");

					// codeButtons.append(" </div>\n");
					codeButtons.append("            " + render.getFooter());

					codeButtons.append("            </c:when>\n");

				}
				; // Final for de renders

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
			codeCoreContent
					.append("<un:useConstants var=\"" + instanceFields + "\" className=\"" + fieldsClass + "\"/>\n");

			codeCoreContent.append("\n");

			String additionalFieldsUp = "\n\n        <!--  /** Additional Fields */  -->\n"
					+ "        <c:forEach var=\"__entry\" items=\"${" + instanceFilterForm + ".additionalFields}\" >\n"
					+ "        <c:if test=\"${ __entry.key < 0  && ((empty __entry.value.searchBy)? true : !gen:contains(__theFilterForm.hiddenFields, __entry.value.searchBy)) && ((empty __entry.value.groupBy )? true : !gen:contains(__theFilterForm.hiddenFields, __entry.value.groupBy ))}\">\n"
					+ "          <td>\n" + "             <c:if test=\"${not empty __entry.value.valueMap }\">\n"
					// TODO Això no funciona per claus multiples
					+ "               <c:out escapeXml=\"${__entry.value.escapeXml}\" value=\"${__entry.value.valueMap["
					+ pkMapping.substring(3, pkMapping.length() - 1) + "]}\" />\n" + "             </c:if>\n"
					+ "             <c:if test=\"${not empty __entry.value.valueField }\">\n"
					+ "               <c:set var=\"__tmp\" value=\"${pageScope}\" />\n"
					+ "               <c:set var=\"__trosos\" value=\"${fn:split(__entry.value.valueField.fullName,'.')}\" />\n"
					+ "               <c:forEach var=\"__tros\" items=\"${__trosos}\">\n"
					+ "                  <c:set var=\"__tmp\" value=\"${__tmp[__tros]}\" />\n"
					+ "               </c:forEach>\n"
					+ "               <c:out escapeXml=\"${__entry.value.escapeXml}\" value=\"${__tmp}\" />\n"
					+ "             </c:if>\n" + "          </td>\n" + "          </c:if>\n"
					+ "          </c:forEach>\n\n\n";

			codeCoreContent.append(additionalFieldsUp);

			for (FieldInfo field : fields) {
				// Class<?> cls = field.getJavaType();
				String modelCamp = field.getJavaName();
				String modelCampMay = instanceFields + "." + field.getJavaName().toUpperCase();

				codeCoreContent.append("        <c:if test=\"${!gen:contains(" + instanceFilterForm + ".hiddenFields,"
						+ modelCampMay + ")}\">\n");
				try {
					// FITXER
					if (fileFields.contains(field)) {
						if (modelCamp.endsWith("ID")) {
							modelCamp = modelCamp.substring(0, modelCamp.length() - 2);
						}

						codeCoreContent.append("          <td>\n");
						// TODO FER MÈTODE QUE PASSAT UN FITXER RETORNI EL NOM

						codeCoreContent.append(getOnlyReadFileField(project, model, modelCamp));
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
					/*
					 * // CAS ESPECIAL ÉS COMBOBOX DE BOOLEAN if
					 * (field.getJavaType().equals(Boolean.class) ||
					 * field.getJavaType().equals(Boolean.TYPE)) { // SALTAM A L'APARTAT DE CHECKBOX
					 * } else
					 */
					{

						codeCoreContent.append("          <td>\n");
						ForeignKey fk = field.getWebFieldInfo().getForeignKey();
						if (fk == null && field.getWebFieldInfo().getWebtype() != WebType.ComboBox) {
							// System.out.println(" ERROR DE QUERY: " + table.getNameJava() + "::" +
							// field.getJavaName());
							// throw new Exception();
							codeCoreContent.append("          ");
							/*
							 * WWW if (field.traduible) { code.append("<fmt:message key=\""); }
							 */

							if (field.getWebFieldInfo().getWebtype() == WebType.URL) {
								codeCoreContent.append(
										"             <c:if test=\"${ not empty " + model + "." + modelCamp + "}\">\n");
								codeCoreContent.append("               <a href=\"${" + model + "." + modelCamp
										+ "}\" target=\"_blank\">" + "${" + model + "." + modelCamp + "}</a>\n");
								codeCoreContent.append("             </c:if>\n");
							} else {
								codeCoreContent.append("${" + model + "." + modelCamp + "}");
							}
							/*
							 * WWW if (field.traduible) { code.append("\"/>"); }
							 */
							codeCoreContent.append("\n");

						} else {
							TableInfo transTable = CodeGenUtils.getSqlTableTranslation(project.getTables());

							if (transTable != null && fk != null && transTable.getName().equals(fk.getTable())) {
								// Es un camp de Traducció
								codeCoreContent.append(
										"          <c:set var=\"tmp\">${" + model + "." + modelCamp + "}</c:set>\n");
								codeCoreContent.append("          <c:if test=\"${not empty tmp}\">\n");

								if (modelCamp.endsWith("ID")) {
									modelCamp = modelCamp.substring(0, modelCamp.length() - 2);
								}

								codeCoreContent.append(
										"          ${" + model + "." + modelCamp + ".traduccions[lang].valor}\n");
								codeCoreContent.append("          </c:if>\n");

							} else {
								// Mostram d'un mapping
								String fkTableName = "Values";
								if (field.getWebFieldInfo().getWebtype() == WebType.Query) {
									TableInfo fkTable = CodeGenUtils.findTableInfoByTableSQLName(project,
											fk.getTable());
									fkTableName = fkTable.getNameJava();
								}

								// <fmt:message key="${estatsInicials[estatTxt]}" />
								// boolean traduir = BackGenerator.fillReferences(fkTable, new StringBuffer());
								codeCoreContent.append(
										"          <c:set var=\"tmp\">${" + model + "." + modelCamp + "}</c:set>\n");
								codeCoreContent.append("          <c:if test=\"${not empty tmp}\">\n");
								codeCoreContent.append("          ");
								/*
								 * if (traduir) { code.append("<fmt:message key=\""); }
								 */
								String name = Character.toUpperCase(field.javaName.charAt(0))
										+ field.javaName.substring(1);

								codeCoreContent.append(
										"${" + instanceFilterForm + ".mapOf" + fkTableName + "For" + name + "[tmp]}");
								/*
								 * if (traduir) { code.append("\"/>"); }
								 */
								codeCoreContent.append("\n");
								codeCoreContent.append("          </c:if>\n");
							}
						}
						codeCoreContent.append("          </td>\n");
						continue;
					}

					case WebType.Checkbox:
						codeCoreContent.append("          <td>\n");

						if (field.getMinAllowedValue() != null) {

							codeCoreContent.append("            <fmt:message key=\"" + field.getMinAllowedValue()
									+ ".${" + model + "." + modelCamp + "}\" />");

						} else {

							if (!field.isNotNullable()) {
								codeCoreContent.append("            &nbsp;<c:if test=\"${not empty " + model + "."
										+ modelCamp + "}\">\n");
							}
							codeCoreContent.append(
									"            <img height=\"18\" width=\"18\" src=\"<c:url value=\"/img/icn_alert_${"
											+ model + "." + modelCamp + "?'success':'error'}.png\"/>\">\n");
							if (!field.isNotNullable()) {
								codeCoreContent.append("            </c:if>\n");
							}
						}
						codeCoreContent.append("          </td>\n");
						continue;

					case WebType.Date:
						codeCoreContent
								.append("          <td> <fmt:formatDate pattern=\"${gen:getDatePattern()}\" value=\"${"
										+ model + "." + modelCamp + "}\" /></td>\n");
						continue;

					case WebType.Time:
						codeCoreContent
								.append("          <td> <fmt:formatDate pattern=\"${gen:getTimePattern()}\" value=\"${"
										+ model + "." + modelCamp + "}\" /></td>\n");
						continue;

					case WebType.DateTime:
						codeCoreContent.append(
								"          <td> <fmt:formatDate pattern=\"${gen:getDateTimePattern()}\" value=\"${"
										+ model + "." + modelCamp + "}\" /></td>\n");
						continue;
					// TODO S'han de fer que tots els camps que referenciin a la taula fitxer
					// canviin el tipus web a FILE
					case WebType.File:
					case WebType.UserID:
					case WebType.RoleID:
						throw new Exception("Els tipus WEB UserID i RoleID no estan suportats !!!!!");

					default:
						throw new Exception("NO existix generador de LIST_JSP pel camp " + field.javaName + " de tipus "
								+ field.getClass());

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

	private static String getOnlyReadFileField(Project project, final String model, String modelCamp) {

		StringBuffer codeCoreContent = new StringBuffer();
		codeCoreContent.append("            <c:if test=\"${not empty " + model + "." + modelCamp + "}\">\n");
		codeCoreContent.append(getOnlyReadFileFieldWithoutCondition(project, model, modelCamp));
		codeCoreContent.append("            </c:if>\n");
		return codeCoreContent.toString();
	}

	private static String getOnlyReadFileFieldWithoutCondition(Project project, final String model, String modelCamp) {
		return "              <a target=\"_blank\" href=\"<c:url value=\"${" + project.getPrefix().toLowerCase()
				+ ":fileUrl(" + model + "." + modelCamp + ")}\"/>\">${" + model + "." + modelCamp + ".nom}</a>\n";
	}

	public static abstract class ActionsRenderer {

		abstract int getID();

		abstract String getHeader(String instanceFilterForm);

		abstract String getFooter();

		abstract String getActionButtonCode(String tab, String type, String href, String onclick, String icon,
				String codeMessage);

		public String getImage(String icon, String tab) {
			if (icon.trim().startsWith("${")) {
				StringBuffer code = new StringBuffer();
				String simpleVar = icon.trim().substring(2, icon.length() - 1);
				code.append(tab + "<c:if test=\"${fn:startsWith(" + simpleVar + ", '/')}\">\n");
				code.append(tab + "<img src=\"<c:url value=\"" + icon + "\"/>\"/>\n");
				code.append(tab + "</c:if>");
				code.append(tab + "<c:if test=\"${!fn:startsWith(" + simpleVar + ", '/')}\">\n");
				code.append(tab + "<i class=\"" + icon + "\"></i>\n");
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
			return "    <div class=\"btn-group\">\n" + "      <a class=\"btn btn-sm " + " ${(empty " + botomenu
					+ ")?'btn-secondary' : " + botomenu + "}\" href=\"#\" style=\"${(empty " + botomenu
					+ ")? '' : 'color: white;'}\">" + "<i class=\"" + ICON_LIST + " ${(empty " + botomenu
					+ ")? '' : 'icon-white'}\"></i>" + " <fmt:message key=\"genapp.actions\" /></a>\n"
					+ "      <a class=\"btn btn-sm ${(empty " + botomenu + ")?'btn-secondary' : " + botomenu
					+ "} dropdown-toggle\" data-toggle=\"dropdown\" href=\"#\">"
					+ "&nbsp;<span class=\"caret\"> </span></a>\n"
					+ "  <ul class=\"dropdown-menu float-right\" style=\"min-width:35px;padding:5px 5px 0px 5px;margin:0px;font-size: 12px\" >\n";
		}

		@Override
		String getFooter() {
			return "     </ul>\n" + "    </div>\n";
		}

		@Override
		String getActionButtonCode(String tab, String type, String href, String onclick, String icon,
				String codeMessage) {

			String style;

			if (type == null || type.trim().length() == 0) {
				style = "";
			} else if (type.trim().startsWith("${")) {
				final String variable = type.trim().substring(2, type.length() - 1);
				style = "${(empty " + variable + ")? '' : 'color: white;'};";
			} else {
				style = "color: white;";
			}

			return tab + "<li>\n" + tab + "<a class=\"btn " + type + " btn-sm a_item\" style=\"margin-bottom:5px;"
					+ style + "\" " + "href=\"" + href + "\" onclick=\"" + onclick + "\">\n" + getImage(icon, tab)
					+ "\n" + tab + " <fmt:message key=\"" + codeMessage + "\"/>\n" + tab + "</a>\n" + tab + "</li>\n";
		}

	}

	public static class ActionsRendererSimpleIconList extends ActionsRenderer {
		/*
		 * <div class="btn-group" role="group" aria-label="Basic example"> <button
		 * type="button" class="btn btn-secondary">Left</button> <button type="button"
		 * class="btn btn-secondary">Middle</button> <button type="button"
		 * class="btn btn-secondary">Right</button> </div>
		 * 
		 * 
		 */
		@Override
		public int getID() {
			return BaseFilterForm.ACTIONS_RENDERER_SIMPLE_ICON_LIST;
		}

		@Override
		public String getHeader(String instanceFilterForm) {
			return "<div class=\"btn-group\" role=\"group\" >\n"; // data-toggle=\"buttons-checkbox\"
		}

		@Override
		public String getFooter() {
			return "</div>\n";
		}

		@Override
		public String getActionButtonCode(String tab, String type, String href, String onclick, String icon,
				String codeMessage) {
			StringBuffer codeButtons = new StringBuffer();

			codeButtons.append(tab + "<a class=\"btn " + type + "\" href=\"" + href + "\" role=\"button\" ");

			if (onclick != null) {
				codeButtons.append(" onclick=\"" + onclick + "\"");
			}
			codeButtons.append(" title=\"<fmt:message key=\"" + codeMessage + "\"/>\">\n");
			codeButtons.append(getImage(icon, tab + "   ") + "\n");
			codeButtons.append(tab + "</a>\n");
			return codeButtons.toString();
		}

	}

	public static String generateMenu(TableInfo[] tables) {
		StringBuffer code = new StringBuffer();

		tables = (TableInfo[]) tables.clone();

		Arrays.sort(tables);

		for (TableInfo table : tables) {

			if (!table.isGenerate() || table.isTranslationMapEntity()) {
				continue;
			}

			String model = CodeGenUtils.getModelName(table.getNameJava());
			code.append("\n");
			code.append("    <%-- " + table.getNameJava() + " --%>\n");

			code.append("       <fmt:message var=\"entityname\" key=\"" + model + "." + model + ".plural\"/>\n");

			code.append(
					"        <li style=\"list-style-type: disc; list-style-position: inside;\"><a href=\"<c:url value=\"/webdb/"
							+ model + "/list/1\"/>\" ><span style=\"${(fn:contains(url, '" + model
							+ "/') && fn:contains(url, '/list'))? \"font-weight: bold;\" : \"\"}\" >\n");

			code.append("${entityname}\n");
			// XYZ ZZZ
//          code.append("        <fmt:message key=\"genapp.listtitle\" >\n");
//          code.append("         <fmt:param value=\"${entityname}\"/>\n");
//          code.append("       </fmt:message>\n");

			code.append("        </span></a>\n");
			code.append("        </li>\n");

			/*
			 * XYZ ZZZ code.append("    <li>\n");
			 * 
			 * code.
			 * append("      <a href=\"#\" role=\"branch\" class=\"tree-toggle ${fn:contains(url, '"
			 * + model +
			 * "/')? \"\" : \"closed\"}\" data-toggle=\"branch\" data-value=\"suportada\"><span style=\"${fn:contains(url, '"
			 * + model + "/')? \"font-weight: bold;\" : \"\"}\"><fmt:message key=\"" + model
			 * + "." + model + "\"/></span></a>\n");
			 * code.append("      <ul class=\"${fn:contains(url, '" + model +
			 * "/')? \"branch in\" : \"branch\"}\">\n"); code.
			 * append("        <li style=\"list-style-type: disc; list-style-position: inside;\" ><a href=\"<c:url value=\"/webdb/"
			 * + model + "/new\"/>\" ><span style=\"${(fn:contains(url, '" + model +
			 * "/') && fn:contains(url, '/new'))? \"font-weight: bold;\" : \"\"}\" >\n");
			 * code.append("       <fmt:message var=\"entityname\" key=\"" + model + "." +
			 * model +"\"/>\n");
			 * code.append("       <fmt:message key=\"genapp.createtitle\" >\n");
			 * code.append("         <fmt:param value=\"${entityname}\"/>\n");
			 * code.append("       </fmt:message>\n");
			 * 
			 * code.append("       </span></a></li>\n"); code.
			 * append("        <li style=\"list-style-type: disc; list-style-position: inside;\"><a href=\"<c:url value=\"/webdb/"
			 * + model + "/list/1\"/>\" ><span style=\"${(fn:contains(url, '" + model +
			 * "/') && fn:contains(url, '/list'))? \"font-weight: bold;\" : \"\"}\" >\n");
			 * code.append("        <fmt:message key=\"genapp.listtitle\" >\n");
			 * code.append("         <fmt:param value=\"${entityname}\"/>\n");
			 * code.append("       </fmt:message>\n");
			 * 
			 * code.append("        </span></a>\n"); code.append("        </li>\n");
			 * code.append("      </ul>\n");
			 * 
			 * code.append("    </li>\n");
			 */
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
			code.append("        <put-attribute name=\"titol\" value=\"" + model + "." + model + "\" />\n");
			code.append("        <put-attribute name=\"contingut\" value=\"/WEB-INF/jsp/webdb/" + model
					+ "Form.jsp\" />\n");
			code.append("    </definition>\n");
			code.append("    \n");
			code.append("    <definition name=\"" + model + "ListWebDB\" extends=\"webdb\">\n");

			// code.append(" <put-attribute name=\"titol\" value=\"" + model + ".llistat\"
			// />\n");
			code.append("        <put-attribute name=\"contingut\" value=\"/WEB-INF/jsp/webdb/" + model
					+ "List.jsp\" />\n");
			code.append("    </definition>\n\n");
		}
		return code.toString();
	}

	public static String getEmptyTilesXML(String backPackage) {
		StringBuffer code = new StringBuffer();
		code.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
		code.append(
				"<!DOCTYPE tiles-definitions PUBLIC  \"-//Apache Software Foundation//DTD Tiles Configuration 3.0//EN\"  \"http://tiles.apache.org/dtds/tiles-config_3_0.dtd\">\n");
		code.append("<tiles-definitions>\n");
		code.append("\n");

		code.append("    <definition name=\"base.cap\" template=\"/WEB-INF/jsp/moduls/cap.jsp\" preparer=\""
				+ backPackage + ".preparer.CapPreparer\"/>\n");
		code.append(
				"    <definition name=\"base.menu_i_contingut\" template=\"/WEB-INF/jsp/moduls/menu_i_contingut.jsp\"/>\n");
		code.append("    <definition name=\"base.menu\" template=\"/WEB-INF/jsp/moduls/blanc.jsp\" preparer=\""
				+ backPackage + ".preparer.MenuPreparer\"/>\n");
		code.append("    <definition name=\"base.contingut\" template=\"/WEB-INF/jsp/moduls/blanc.jsp\"/>\n");
		code.append("    <definition name=\"base.peu\" template=\"/WEB-INF/jsp/moduls/peu.jsp\" preparer=\""
				+ backPackage + ".preparer.PeuPreparer\"/>\n");

		code.append(" <definition name=\"base.contingut\" template=\"/WEB-INF/jsp/moduls/blanc.jsp\"/>\n");
		code.append("\n");
		code.append("    <definition name=\"base.definition\" template=\"/WEB-INF/jsp/layout/layout.jsp\" preparer=\""
				+ backPackage + ".preparer.BasePreparer\">\n");
		code.append("        <put-attribute name=\"cap\" value=\"base.cap\" />\n");
		code.append("        <put-attribute name=\"menu_i_contingut\" value=\"base.menu_i_contingut\" />\n");
		code.append("        <put-attribute name=\"menu\" value=\"base.menu\" />\n");
		code.append("        <put-attribute name=\"contingut\" value=\"base.contingut\" />\n");
		code.append("        <put-attribute name=\"peu\" value=\"base.peu\" />\n");
		code.append("    </definition>\n");
		code.append("\n");
		code.append("\n");
		code.append(
				"    <definition name=\"principal.body\" template=\"/WEB-INF/web/tiles/principal/principal.jsp\"/>\n");
		code.append(" \n");
		code.append("\n");
		code.append("    <definition name=\"sense_enllaz\" extends=\"base.definition\">\n");
		code.append("        <put-attribute name=\"enlaces\" value=\"\" />\n");
		code.append("    </definition>\n");
		code.append("\n");
		code.append("    <definition name=\"exceptionTile\" extends=\"base.definition\">\n");
		code.append("        <put-attribute name=\"contingut\" value=\"/error.jsp\" />\n");
		code.append("    </definition>\n");
		code.append("\n");

		code.append("    <!--  ==================PUBLIC =============== -->\n");
		code.append("\n");
		code.append("    <definition name=\"all\" extends=\"base.definition\">\n");
		code.append("        <put-attribute name=\"menu\" value=\"/WEB-INF/jsp/moduls/menu_inici.jsp\" />\n");
		code.append("        <put-attribute name=\"contingut\" value=\"/WEB-INF/jsp/all/homepublic.jsp\" />\n");
		code.append("    </definition>\n");
		code.append("\n");
		
		code.append("   <definition name=\"avislegal_ca\" extends=\"base.definition\">\r\n"
		        + "        <put-attribute name=\"menu\" value=\"/WEB-INF/jsp/moduls/menu_inici.jsp\" />\n"
		        + "        <put-attribute name=\"contingut\" value=\"/WEB-INF/jsp/all/avislegal_ca.jsp\" />\n"
		        + "   </definition>\n"
		        + "\n"
		        + "   <definition name=\"avislegal_es\" extends=\"base.definition\">\r\n"
		        + "        <put-attribute name=\"menu\" value=\"/WEB-INF/jsp/moduls/menu_inici.jsp\" />\n"
		        + "        <put-attribute name=\"contingut\" value=\"/WEB-INF/jsp/all/avislegal_es.jsp\" />\n"
		        + "    </definition>");
		code.append("\n");
		code.append("\n");
		code.append("    <definition name=\"homepublic\" extends=\"all\">\n");
		code.append("        <put-attribute name=\"contingut\" value=\"/WEB-INF/jsp/all/homepublic.jsp\" />\n");
		code.append("    </definition>\n");

		code.append("    <!--  ===========  COMMON  ================== -->\n");
		code.append("\n");
		code.append("    <definition name=\"common\" extends=\"base.definition\">\n");
		code.append("        <put-attribute name=\"menu\" value=\"/WEB-INF/jsp/moduls/menu_inici.jsp\" />\n");
		code.append("        <put-attribute name=\"contingut\" value=\"/WEB-INF/jsp/principal.jsp\" />\n");
		code.append("    </definition>\n");
		code.append("\n");
		code.append("    <definition name=\"principal\" extends=\"common\">\n");
		code.append("        <put-attribute name=\"contingut\" value=\"/WEB-INF/jsp/principal.jsp\" />\n");
		code.append("    </definition>\n");
		code.append("\n");
		code.append("    <definition name=\"option1Common\" extends=\"common\">\n");
		code.append("        <put-attribute name=\"contingut\" value=\"/WEB-INF/jsp/common/option.jsp\" />\n");
		code.append("    </definition>\n");
		code.append("\n");
		code.append("    <definition name=\"option2Common\" extends=\"common\">\n");
		code.append("        <put-attribute name=\"contingut\" value=\"/WEB-INF/jsp/common/option.jsp\" />\n");
		code.append("    </definition>\n");
		code.append("\n");
		code.append("    <!--  ===========  USER  ================== -->\n");
		code.append("        \n");
		code.append("    <definition name=\"user\" extends=\"base.definition\">\n");
		code.append("        <put-attribute name=\"menu\" value=\"/WEB-INF/jsp/moduls/menu_user.jsp\" />\n");
		code.append("         <put-attribute name=\"pipella\" value=\"user\" />\n");
		code.append("    </definition>\n");
		code.append("    \n");
		code.append("    <definition name=\"option1User\" extends=\"user\">\n");
		code.append("        <put-attribute name=\"contingut\" value=\"/WEB-INF/jsp/user/user.jsp\" />\n");
		code.append("    </definition>\n");
		code.append("    \n");
		code.append("    <definition name=\"option2User\" extends=\"user\">\n");
		code.append("        <put-attribute name=\"contingut\" value=\"/WEB-INF/jsp/user/user.jsp\" />\n");
		code.append("    </definition>\n");
		code.append("\n");
		code.append("    <!--  ===========  ADMIN  ================== -->\n");
		code.append("        \n");
		code.append("    <definition name=\"admin\" extends=\"base.definition\">\n");
		code.append("        <put-attribute name=\"menu\" value=\"/WEB-INF/jsp/moduls/menu_admin.jsp\" />\n");
		code.append("        <put-attribute name=\"pipella\" value=\"admin\" />\n");
		code.append("    </definition>\n");
		code.append("    \n");
		code.append("    <definition name=\"option1Admin\" extends=\"admin\">\n");
		code.append("        <put-attribute name=\"contingut\" value=\"/WEB-INF/jsp/admin/admin.jsp\" />\n");
		code.append("    </definition>\n");
		code.append("     \n");
		code.append("    <definition name=\"option2Admin\" extends=\"admin\">\n");
		code.append("        <put-attribute name=\"contingut\" value=\"/WEB-INF/jsp/admin/admin.jsp\" />\n");
		code.append("    </definition>\n");
		code.append("    \n");
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
		code.append(
				"        <put-attribute name=\"contingut\" value=\"/WEB-INF/jsp/exemples/example_plantilla.jsp\" />\n");
		code.append("    </definition>\n");
		code.append("    \n");
		code.append("\n");
		code.append(
				"    <definition name=\"exemple_pagina completa\" template=\"/WEB-INF/jsp/layout/onlycontentlayout.jsp\">\n");
		code.append(
				"        <put-attribute name=\"contingut\" value=\"/WEB-INF/jsp/exemples/exemple_pagina completa.jsp\" />\n");
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

	public static List<SourceFile> generateFormJsp(String packageControllerBack, String packageFormBack,
			String packageValidatorBack, String jpaPackage, String ejbPackage, BasicPackages packages, Project project,
			Map<String, BeanCode> beanCodeBySqlTableName) throws Exception {

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

			// TableInfo fileTable = CodeGenUtils.getSqlTableFile(tables);
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

				codeBase.append("\n\n");

				codeBase.append("<form:form modelAttribute=\"" + instanceForm
						+ "\" method=\"${(empty method)?'post':method}\"\n");
				codeBase.append("  enctype=\"multipart/form-data\">\n");
				codeBase.append("  \n");

                codeBase.append("  <%@include file=\"" + model + "FormTitle.jsp\" %>\n");
                codeBase.append(" \n");
				
				codeBase.append("  <c:set var=\"contexte\" value=\"${" + instanceForm + ".contexte}\"/>\n");
				codeBase.append("  <form:hidden path=\"nou\" />\n");

				codeBase.append("  \n");
				codeBase.append("  <%@include file=\"" + model + "FormCorePre.jsp\" %>\n");

				codeBase.append("\n");
				codeBase.append("  <%@include file=\"" + model + "FormCore.jsp\" %>\n");

				codeBase.append("\n");
				codeBase.append("  <%@include file=\"" + model + "FormCorePost.jsp\" %>\n");

				codeBase.append("\n");
				codeBase.append("  <%@include file=\"" + model + "FormButtons.jsp\" %>\n");

				codeBase.append("\n");
				codeBase.append("  <c:if test=\"${not empty " + model + "Form.sections}\">\n");
				codeBase.append("     <c:set var=\"__basename\" value=\"" + model + "\" scope=\"page\" />\n");
				codeBase.append("     <%@include file=\"sections.jsp\"%>\n");
				codeBase.append("  </c:if>\n");
				codeBase.append("\n");

				codeBase.append("\n");
				codeBase.append("  <c:if test=\"${" + instanceForm + ".attachedAdditionalJspCode}\">\n");
				codeBase.append("     <%@include file=\"../webdbmodificable/" + model + "FormModificable.jsp\" %>\n");
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
				codeTitle.append(HtmlCSS.TITOL_BEGIN + "\n");
				codeTitle.append(" <c:choose>\n");
				codeTitle.append("  <c:when test=\"${fn:startsWith(" + instanceForm + ".titleCode,'=')}\">\n");
				codeTitle.append("       <c:out value=\"${fn:substringAfter(" + instanceForm
						+ ".titleCode, '=')}\" escapeXml=\"false\"/>\n");
				codeTitle.append("  </c:when>\n");

				codeTitle.append("  <c:when test=\"${not empty " + instanceForm + ".titleCode}\">\n");
				codeTitle.append("    <fmt:message key=\"${" + instanceForm + ".titleCode}\" >\n");
				codeTitle.append("      <fmt:param value=\"${" + instanceForm + ".titleParam}\" />\n");
				codeTitle.append("    </fmt:message>\n");
				codeTitle.append("  </c:when>\n");
				codeTitle.append("  <c:otherwise>\n");
				codeTitle.append("    <c:if test=\"${empty " + instanceForm + ".entityNameCode}\">\n");
				codeTitle.append("      <fmt:message var=\"entityname\" key=\"" + model + "." + model + "\"/>\n");
				codeTitle.append("    </c:if>\n");

				codeTitle.append("    <c:if test=\"${not empty " + instanceForm + ".entityNameCode}\">\n");
				codeTitle.append(
						"      <fmt:message var=\"entityname\" key=\"${" + instanceForm + ".entityNameCode}\"/>\n");
				codeTitle.append("    </c:if>\n");
				// codeTitle.append(" <c:when test=\"${empty " + instanceForm +
				// ".titleCode}\">\n");
				codeTitle.append("    <c:set var=\"keytitle\" value=\"${" + instanceForm + ".nou?'genapp.createtitle':("
						+ instanceForm + ".view?'genapp.viewtitle':'genapp.edittitle')}\"/>\n");
				codeTitle.append("    <fmt:message key=\"${keytitle}\">\n");
				codeTitle.append("      <fmt:param value=\"${entityname}\"/>\n");
				codeTitle.append("    </fmt:message>\n");
				// codeTitle.append(" </c:when>\n");
				codeTitle.append("    </c:otherwise>\n");
				codeTitle.append(" </c:choose>");
				codeTitle.append(HtmlCSS.TITOL_END + "\n");

				codeTitle.append("  <c:if test=\"${not empty " + instanceForm + ".subTitleCode}\">\n");
				codeTitle.append(HtmlCSS.SUBTITOL_BEGIN + "\n");
				codeTitle.append("<c:set var=\"subtitleTranslated\" value=\"${fn:startsWith(" + instanceForm
						+ ".subTitleCode,'=')}\" />\n");
				codeTitle.append("<c:if test=\"${subtitleTranslated}\">\n");
				codeTitle.append("   <c:out value=\"${fn:substringAfter(" + instanceForm
						+ ".subTitleCode, '=')}\" escapeXml=\"false\"/>\n");
				codeTitle.append("</c:if>\n");
				codeTitle.append("<c:if test=\"${not subtitleTranslated}\">\n");
				codeTitle.append("  <fmt:message key=\"${" + instanceForm + ".subTitleCode}\" />\n");
				codeTitle.append("</c:if>\n");
				codeTitle.append(HtmlCSS.SUBTITOL_END + "\n");
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
				code.append("    <c:if test=\"${not " + instanceForm + ".view}\">");
				code.append("    <c:forEach items=\"${" + instanceForm + ".hiddenFields}\" var=\"hiddenFieldF\" >\n");
				code.append("      <c:set  var=\"hiddenField\" value=\"${hiddenFieldF.javaName}\" />\n");
				code.append("      <c:if test=\"${gen:hasProperty(__theForm." + model + ",hiddenField)}\">\n");
				code.append("        <form:errors path=\"" + model
						+ ".${hiddenField}\" cssClass=\"errorField alert alert-danger\" />\n");
				code.append("        <form:hidden path=\"" + model + ".${hiddenField}\"/>\n");
				code.append("      </c:if>\n");
				code.append("    </c:forEach>\n");
				code.append("    </c:if>\n\n");

				// Errors Globals del Formulari
				code.append(
						"    <form:errors cssClass=\"errorField alert alert-danger\" delimiter=\"&lt;p/&gt;\" />\n");

				code.append("    <table id=\"" + model
						+ "_tableid\" class=\"tdformlabel table-sm table table-bordered table-striped marTop10 table-genapp\" > \n");
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
						pkMapping = pkMapping + "/${" + instanceForm + "." + model + "." + fieldInfo.getJavaName()
								+ "}";
					}
				} else {
					FieldInfo pk = PKs.get(0);
					pkMapping = "/${" + instanceForm + "." + model + "." + pk.getJavaName() + "}";
				}

				StringBuffer codeButton = new StringBuffer();

				codeButton.append("<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>\n");
				codeButton.append("<%@ include file=\"/WEB-INF/jsp/moduls/includes.jsp\"%>\n");
				codeButton.append("  \n");

				codeButton.append("  <div class=\"navbar-form\" style=\"text-align:right\">\n");
				// BOTO GUARDAR
				codeButton.append("    <c:if test=\"${" + instanceForm + ".saveButtonVisible}\">\n");
				codeButton.append(
						"    <input type=\"submit\" class=\"btn btn-primary\" value=\"<fmt:message key=\"genapp.save\"/>\">\n");
				codeButton.append("    </c:if>\n");
				// BOTO CANCEL.LAR
				codeButton.append("    <c:if test=\"${" + instanceForm + ".cancelButtonVisible}\">\n");
				codeButton.append(
						"    <input type=\"button\" class=\"btn btn-secondary\" onclick=\"goTo('<c:url value=\"${contexte}"
								+ pkMapping + "/cancel\"/>')\" value=\"<fmt:message key=\"genapp.cancel\"/>\">\n");
				codeButton.append("    </c:if>\n");
				// BOTO DELETE
				codeButton.append("    <c:if test=\"${!" + instanceForm + ".nou && " + instanceForm
						+ ".deleteButtonVisible}\">\n");
				codeButton.append(
						"    <button type=\"button\" class=\"btn btn-danger\" onclick=\"openModal('<c:url value=\"${contexte}"
								+ pkMapping
								+ "/delete\"/>','show');\"><fmt:message key=\"genapp.delete\"/></button>\n");
				codeButton.append("    </c:if>\n");

				// BOTONS ADICIONALS
				codeButton.append("    <c:set var=\"bracket\" value=\"{0}\"/>\n");
				codeButton
						.append("    <c:forEach var=\"button\" items=\"${" + instanceForm + ".additionalButtons}\">\n");
				codeButton.append("    <c:if test=\"${!" + instanceForm
						+ ".nou || (-1 == fn:indexOf(button.link,bracket))}\">\n");
				codeButton.append("    <c:set var=\"pk\" value=\"" + pkMapping.substring(1) + "\"/>\n");
				codeButton.append("    <c:set var=\"thelink\" value=\"${fn:replace(button.link,bracket, pk)}\" />\n");
				codeButton.append("    <c:set var=\"thehref\" value=\"#\"/>\n");
				codeButton.append("    <c:if test=\"${!fn:startsWith(thelink,'javascript:')}\">\n");
				codeButton.append("     <c:url var=\"thehref\" value=\"${thelink}\"/>\n");

				// NOU Ticket #17
				codeButton.append("     <c:url var=\"thelink\" value=\"\"/>\n");
				// codeButton.append(" <c:url var=\"thelink\" value=\"${thelink}\"/>\n");
				// codeButton.append(" <c:set var=\"thelink\"
				// value=\"goTo('${thelink}')\"/>\n");

				codeButton.append("    </c:if>\n");
				// #17 codeButton.append(" <button type=\"button\"
				codeButton.append("    <a class=\"btn ${button.type}\" \n");
				codeButton.append(
						"       href=\"${thehref}\" onclick=\"${thelink}\" style=\"${(empty button.type)? '' : 'color: white;'}\"  >\n");
				codeButton.append("       <i class=\"${button.icon}\"></i><fmt:message key=\"${button.codeText}\"/>\n");

				// #17 codeButton.append(" </button>\n");
				codeButton.append("    </a>\n");

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
					String required = field.isNotNullable()
							&& !(type.equals(boolean.class) || type.equals(Boolean.class)) ? " &nbsp;(*)" : "";

					String codeField = generateFieldFormInput(model, instanceForm, fileFields, translationFields, field,
							modelCamp, project, modelCampMay);

					if (codeField != null) {

						code.append("        <c:if test=\"${!gen:contains(" + instanceForm + ".hiddenFields,"
								+ modelCampMay + ")}\">\n");
						// code.append(" <tr>\n");
						code.append("        <tr id=\"" + model + "_" + modelCamp + "_rowid\">\n");

						code.append("          <td id=\"" + model + "_" + modelCamp + "_columnlabelid\">\n");
						code.append("            <label>\n");
						code.append("              <fmt:message key=\"" + "${(empty " + instanceForm + ".labels["
								+ modelCampMay + "])?'" + model + "." + modelCamp + "':" + instanceForm + ".labels["
								+ modelCampMay + "]}" + "\" />" + required + "\n");
						code.append("             </label>\n");
						code.append("              <c:if test=\"${not empty " + instanceForm + ".help[" + modelCampMay
								+ "]}\">\n");
						code.append("              <i class=\"" + ICON_INFO + "\" title=\"${" + instanceForm + ".help["
								+ modelCampMay + "]}\" ></i>\n");
						code.append("              </c:if>\n");

						code.append("            </td>\n");
						code.append("          <td id=\"" + model + "_" + modelCamp + "_columnvalueid\">\n");
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

	private static String generateFieldFormInput(final String model, final String instanceForm,
			List<FieldInfo> fileFields, List<FieldInfo> translationFields, FieldInfo field, String modelCamp,
			Project project, String modelCampMay) throws Exception {

		StringBuffer code = new StringBuffer();

		final String readOnlyCondition = "gen:contains(" + instanceForm + ".readOnlyFields ," + modelCampMay + ")";
		final String readOnlyAttribute = "readonly=\"${ " + readOnlyCondition + "? 'true' : 'false'}\"";
		final String cssClass = "form-control  ${" + readOnlyCondition + "? ' uneditable-input' : ''}"; // col-md-9-optional
		final String formInputReadOnly = readOnlyAttribute + " cssClass=\"" + cssClass + "\" ";

		if (fileFields.contains(field)) {

			String formInputReadOnlyFile = readOnlyAttribute + " cssClass=\"custom-file-input " + cssClass + "\" ";

			String fileObj = modelCamp;
			if (modelCamp.endsWith("ID")) {
				fileObj = modelCamp.substring(0, modelCamp.length() - 2);
			}
			code.append("              <form:errors path=\"" + model + "." + modelCamp
					+ "\" cssClass=\"errorField alert alert-danger\" />\n");

			code.append("            <c:if test=\"${" + readOnlyCondition + "}\" >\n");
			code.append(getOnlyReadFileFieldWithoutCondition(project, instanceForm + "." + model, fileObj));
			code.append("            </c:if>\n");

			code.append("            <c:if test=\"${!" + readOnlyCondition + "}\" >\n");
			// code.append(" <div class=\"row\" style=\"margin-right:
			// 0px;margin-left:0px;\"/>");
			code.append("              <div class=\"input-group col-md-9-optional\" style=\"padding: 0px\">\n");
			code.append("                <div class=\"custom-file\">\n");
			// code.append(" <input type=\"file\" class=\"custom-file-input\"
			// id=\"inputGroupFile02\">\n");
			code.append("                  <form:input  " + formInputReadOnlyFile + "  path=\"" + modelCamp
					+ "\" type=\"file\" />\n");

			code.append("                  <label class=\"custom-file-label\" for=\"" + modelCamp + "\">\n");
			// code.append(" <fmt:message key=\"genapp.form.file.select\"/>\n");
			code.append("                  </label>\n");

			code.append("                </div>\n");
			// code.append(" <div class=\"input-group-append\">\n");
			// code.append(" <span class=\"input-group-text\" id=\"\">Upload</span>\n");

			code.append("                <c:choose>\n");
			code.append("                <c:when test=\"${not empty " + instanceForm + "." + model + "." + fileObj
					+ "}\">\n");

			// code.append(" <c:if test=\"${!" + readOnlyCondition + "}\" >\n");
			code.append("                <div class=\"input-group-append\">\n");
			code.append("                  <span class=\"input-group-text\" id=\"\">\n");
			// code.append(" <fmt:message key=\"genapp.form.file.change\"/>\n");
			code.append("                  <small>"
					+ getOnlyReadFileFieldWithoutCondition(project, instanceForm + "." + model, fileObj)
					+ "</small>\n");

			code.append("                  </span>\n");
			if (!field.isNotNullable) {
				code.append("                  <span class=\"input-group-text\" id=\"\">\n");
				code.append("                        <form:checkbox path=\"" + modelCamp + "Delete\"/>\n");
				code.append("                        <small><fmt:message key=\"genapp.form.file.delete\"/></small>\n");
				code.append("                  </span>\n");

			}
			code.append("                </div>\n");
			code.append("                </c:when>\n");
			code.append("                <c:otherwise>\n");

			code.append("                <div class=\"input-group-append input-group-append-file\">\n");
			code.append("                  <span class=\"input-group-text\" id=\"" + modelCamp
					+ "-custom-file-label\" style=\"display:none\">\n");
			code.append("                  <small></small>\n");
			code.append("                  </span>\n");
			code.append("                </div>\n");
			code.append("                <script type=\"text/javascript\">\n");
			code.append("					$('#" + modelCamp + "').on('change', function(){\n");
			code.append("						var ruta = $('#" + modelCamp + "').val(); \n");
			code.append("						var rutaArray = ruta.split('\\\\');\n");
			code.append("						$('#" + modelCamp + "-custom-file-label').css('display','block');\n");
			code.append("						$('#" + modelCamp
					+ "-custom-file-label small').html(rutaArray[rutaArray.length - 1]);\n");
			code.append("					});\n");
			code.append("				</script>");

			code.append("                </c:otherwise>\n");
			code.append("                </c:choose>\n");
			code.append("              </div>\n");
			// code.append(" </div>\n");
			code.append("            </c:if>\n");

			return code.toString();
		}

		if (translationFields.contains(field)) {
			String simpleName = modelCamp.substring(0, modelCamp.length() - 2); // Llevar ID
			String fullmodel = model + "." + simpleName;

			code.append(
					"       <form:errors path=\"" + fullmodel + "\" cssClass=\"errorField alert alert-danger\" />\n");
			code.append("       <div class=\"row-fluid col-md-9-optional\">\n");
			code.append("         <ul class=\"nav nav-tabs\" style=\"margin: 0 15px -1px;\">\n");
			code.append(
					"             <c:forEach items=\"${__theForm.idiomesTraduccio}\" var=\"idioma\" varStatus=\"counter\">\n");

			code.append("            <li class=\"nav-item \">\n");
			code.append(
					"                 <a class=\"nav-link ${(counter.index == 0)? 'active':''}\" href=\"#${counter.index}_tab_"
							+ simpleName + "_${idioma.idiomaID}\" data-toggle=\"tab\">${idioma.nom}</a>\n");
			code.append("            </li>\n");
			code.append("          </c:forEach>\n");
			code.append("           \n");
			code.append("         </ul>\n");
			code.append("         <div class=\"tab-content well well-white\" style=\"padding:8px;margin:0px;\">\n");
			code.append(
					"           <c:forEach items=\"${__theForm.idiomesTraduccio}\" var=\"idioma\" varStatus=\"counter\">\n");
			code.append(
					"           <div class=\"tab-pane ${(counter.index == 0)? 'active':'' }\" id=\"${counter.index}_tab_"
							+ simpleName + "_${idioma.idiomaID}\">\n");
			code.append("               <form:errors path=\"" + fullmodel
					+ ".traduccions['${idioma.idiomaID}'].valor\" cssClass=\"errorField alert alert-danger\"/>\n");
			String maxlength = "maxlength=\"4000\" ";

			boolean textarea = "textarea".equalsIgnoreCase(field.getMinAllowedValue());

			String type;
			String config;
			if (textarea) {
				type = "<form:textarea";
				config = " rows=\"3\" wrap=\"soft\" style=\"overflow:auto;display: inline;resize:both;\" ";
				// cssClass=\"form-control \"
			} else {
				type = "<form:input";
				config = "";
			}

			code.append("               " + type + " path=\"" + fullmodel
					+ ".traduccions['${idioma.idiomaID}'].valor\" " + "cssClass=\"" + cssClass + "\" " + "readonly=\"${"
					+ readOnlyCondition + "}\" " + maxlength + config + "/>\n");

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
			if (field.isAutoIncrement()) {
				return null;
			}
		case WebType.URL:
		case WebType.Text:
		case WebType.Integer:
		case WebType.Decimal:

			String maxlength = "";
			if (field.getJavaType().equals(String.class)) {
				maxlength = "maxlength=\"" + field.size + "\"";
			}
			// TODO maxlength = String.valueOf(Integer.MAX_VALUE).length() DEPEN DE TIPUS
			// !!!
			// TODO maxlength = String.valueOf(Double.MAX_VALUE).length() DEPEN DE TIPUS !!!

			// TODO tamany via css segons el size de BBDD
			String newInputSize;
			if (webType == WebType.Integer || webType == WebType.Decimal) {
				newInputSize = getCssInputTypeBySize(field.getDigits());
			} else {
				newInputSize = getCssInputTypeBySize(field.getSize());
			}

			// final String condiURL = readOnlyCondition + " && not empty " + instanceForm +
			// "." + model + "." + modelCamp;
			// final String onClick = "window.open('${" + instanceForm + "." + model + "." +
			// modelCamp + "}', '_blank')";
			if (webType == WebType.URL) {
				code.append("           <c:if test=\"${" + readOnlyCondition + "}\">\n\n");
				code.append("             <c:if test=\"${ not empty " + instanceForm + "." + model + "." + modelCamp
						+ "}\">\n");
				code.append("               <a href=\"${" + instanceForm + "." + model + "." + modelCamp
						+ "}\" target=\"_blank\">" + "${" + instanceForm + "." + model + "." + modelCamp + "}</a>\n\n");
				code.append("             </c:if>\n");
				code.append("           </c:if>\n\n");
				code.append("           <c:if test=\"${not (" + readOnlyCondition + ")}\">\n\n");
			}

			code.append("            <form:errors path=\"" + model + "." + modelCamp
					+ "\" cssClass=\"errorField alert alert-danger\" />\n");
			// code.append(" <div class=\"input-append\">\n");
			code.append("            <form:input "
					+ formInputReadOnly.replace("cssClass=\"", "cssClass=\"" + newInputSize + " ") + " style=\"\" "
					+ maxlength + " path=\"" + model + "." + modelCamp + "\" "
					// + ((webType == WebType.URL)?" onClick=\"${__onClick}\" ": "")
					+ "  />\n\n");

			if (webType == WebType.URL) {
				code.append("           </c:if>\n\n");
				// code.append(" <c:if test=\"${" + condiURL + "}\">\n");
				// code.append(" <button class=\"btn\" onClick=\"${__onClick}; return
				// false;\"><i class=\"icon-download-alt\"></i></button>\n");
				// code.append(" </c:if>\n");
			}
			// code.append(" </div>\n");

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
			String name = Character.toUpperCase(field.javaName.charAt(0)) + field.javaName.substring(1);

			code.append("          <form:errors path=\"" + model + "." + modelCamp
					+ "\" cssClass=\"errorField alert alert-danger\" />\n");

			code.append("          <c:if test=\"${" + readOnlyCondition + "}\" >\n");
			code.append("          <form:hidden path=\"" + model + "." + modelCamp + "\"/>\n");

			code.append(
					"          <input type=\"text\" readonly=\"true\" class=\"form-control col-md-9-optional uneditable-input\" "
							+ "value=\"");
			/*
			 * WWW if (traduir) { code.append("<fmt:message key=\""); }
			 */
			code.append("${gen:findValue(" + instanceForm + "." + model + "." + modelCamp + "," + instanceForm
					+ ".listOf" + fkTableName + "For" + name + ")}");
			/*
			 * WWW if (traduir) { code.append("\" />"); }
			 */
			code.append("\"  />\n");
			code.append("          </c:if>\n");
			code.append("          <c:if test=\"${!" + readOnlyCondition + "}\" >\n");
			code.append("          <c:set var=\"containEmptyValue\"  value=\"false\" />\n");
			code.append("          <form:select id=\"" + id + "\"  onchange=\"if(typeof onChange" + name
					+ " == 'function') {  onChange" + name
					+ "(this); };\"  cssClass=\"form-control col-md-9-optional\" path=\"" + model + "." + modelCamp
					+ "\">\n");

			code.append("            <c:forEach items=\"${" + instanceForm + ".listOf" + fkTableName + "For" + name
					+ "}\" var=\"tmp\">\n");
			code.append("                <form:option value=\"${tmp.key}\">${tmp.value}</form:option>\n");
			code.append("                <c:if test=\"${empty tmp.key}\">\n");
			code.append("                  <c:set var=\"containEmptyValue\"  value=\"true\" />\n");
			code.append("                </c:if>\n");
			code.append("            </c:forEach>\n");
			if (!field.isNotNullable()) {

				code.append(
						"            <%-- El camp pot ser null, per la qual cosa afegim una entrada buida si no s'ha definit abans --%>\n");
				code.append("            <c:if test=\"${not containEmptyValue}\">\n");
				// No funciona el seleccionar automàticamernt un valor buit o null
				code.append("              <c:if test=\"${empty __theForm." + model + "." + modelCamp + " }\">\n");
				code.append("                  <form:option value=\"\" selected=\"true\" ></form:option>\n");
				code.append("              </c:if>\n");
				code.append("              <c:if test=\"${not empty __theForm." + model + "." + modelCamp + " }\">\n");
				code.append("                  <form:option value=\"\" ></form:option>\n");
				code.append("              </c:if>\n");
				code.append("            </c:if>\n");
			}

			code.append("          </form:select>\n");
			code.append("          </c:if>\n");
			// code.append(generateReadOnlySelect(" ", id, readOnlyCondition));

			return code.toString();

		case WebType.TextArea:
		case WebType.RichText:
			code.append("              <form:errors path=\"" + model + "." + modelCamp
					+ "\" cssClass=\"errorField alert alert-danger\" />\n");

			if (webType == WebType.TextArea) {
				code.append("  <table style=\"width:100%\">\n");
				code.append("  <tr>\n");
				code.append("  <td>\n");
			}

			code.append("       <form:textarea");
			if (webType == WebType.RichText) {
				code.append(" cssClass=\" ${" + readOnlyCondition + "? 'mceEditorReadOnly':'mceEditor'}\" "); // col-md-9-optional
			} else {
				code.append(
						" rows=\"3\" wrap=\"soft\" style=\"overflow:auto;display: inline;resize:both;\" cssClass=\"form-control col-md-9-optional\" "
								+ readOnlyAttribute);
			}
			String path = model + "." + modelCamp;
			code.append(" path=\"" + path + "\"  />\n");
			// Wrap
			if (webType == WebType.TextArea) {
				code.append("   </td>\n");
				code.append("   <td style=\"width:40px\">\n");
				code.append("      <div id=\"dropdownMenuButton_" + modelCamp
						+ "\" style=\"vertical-align:top;display:inline;position:relative;\">\n");
				code.append(
						"        <button  class=\"btn btn-secondary btn-sm dropdown-toggle\" type=\"button\" style=\"margin-left:0px;\"><span class=\"caret\"></span></button>\n");
				code.append("        <div id=\"dropdownMenuContainer_" + modelCamp
						+ "\" class=\"dropdown-menu dropdown-menu-right\">\n");
				code.append(
						"          <a class=\"dropdown-item\" href=\"#\" onclick=\"javascript:var ta=document.getElementById('"
								+ path + "'); ta.wrap='off';\" >No Wrap</a>\n");
				code.append(
						"          <a class=\"dropdown-item\"  href=\"#\" onclick=\"javascript:var ta=document.getElementById('"
								+ path + "'); ta.wrap='soft';\">Soft Wrap</a>\n");
				code.append(
						"          <a class=\"dropdown-item\" href=\"#\" onclick=\"javascript:var ta=document.getElementById('"
								+ path + "'); ta.wrap='hard';\">Hard Wrap</a>\n");
				code.append("        </div>\n");
				code.append("      </div>\n");
				code.append("      <script type=\"text/javascript\">\n");
				code.append("			$('#dropdownMenuButton_" + modelCamp + "').on('click', function(){\n"
						+ "					var valor = ($('#dropdownMenuContainer_" + modelCamp
						+ "').css('display') != 'none') ? 'none' : 'block';\n"
						+ "                 $('#dropdownMenuContainer_" + modelCamp + "').css('display', valor);\n"
						+ "                 return false;\n" + "				});\n" + "      </script>");
				code.append("   </td>\n");
				code.append("   </tr>\n");
				code.append("   </table>\n");
			}

			return code.toString();

		case WebType.Checkbox:
			String prefix = field.getMinAllowedValue();
			String name2 = Character.toUpperCase(field.javaName.charAt(0)) + field.javaName.substring(1);
			if (prefix == null || prefix.trim().length() == 0) {
				prefix = null;
			}
			code.append("          <c:if test=\"${!" + readOnlyCondition + "}\" >\n");
			if (field.isNotNullable && prefix == null) {
				prefix = "genapp.checkbox";
				code.append("              <form:errors path=\"" + model + "." + modelCamp
						+ "\" cssClass=\"errorField alert alert-danger\" />\n");
				code.append("              <form:checkbox cssClass=\"\" onclick=\"javascript:return ${ "
						+ readOnlyCondition + "? 'false' : 'true'}\" path=\"" + model + "." + modelCamp + "\" />\n"); // style=\"width:1%\"
																														// ????
			} else {
				if (prefix == null) {
					prefix = "genapp.checkbox";
				}
				code.append(
						"              <form:select cssClass=\"form-control col-md-6\" onchange=\"if(typeof onChange"
								+ name2 + " == 'function') {  onChange" + name2 + "(this); };\"  path=\"" + model + "."
								+ modelCamp + "\">\n");
				if (!field.isNotNullable) {
					code.append("                <form:option value=\"\"><fmt:message key=\"" + prefix
							+ ".\" /></form:option>\n");
				}
				code.append("                <form:option value=\"true\" ><fmt:message key=\"" + prefix
						+ ".true\" /></form:option>\n");
				code.append("                <form:option value=\"false\" ><fmt:message key=\"" + prefix
						+ ".false\" /></form:option>\n");
				code.append("              </form:select>\n");
			}
			code.append("          </c:if>\n");
			code.append("          <c:if test=\"${" + readOnlyCondition + "}\" >\n");
			code.append("                <fmt:message key=\"" + prefix + ".${" + instanceForm + "." + model + "."
					+ modelCamp + "}\" />\n");
			code.append("          </c:if>\n");
			return code.toString();

		case WebType.Date:
			/*
			 * code.append("    <form:errors path=\"" + model + "." + modelCamp +
			 * "\" cssClass=\"errorField alert alert-danger\" />\n");
			 * 
			 * code.append("            <div class=\"form-group\">\n");
			 * code.append("                <div class=\"input-group date\" id=\"" + model +
			 * "_" + modelCamp + "\" data-target-input=\"nearest\">\n");
			 * 
			 * //code.
			 * append("                    <input type=\"text\" class=\"form-control datetimepicker-input\" data-target=\"#datetimepicker4\">\n"
			 * ); code.append("                      <form:input " + readOnlyAttribute +
			 * " cssClass=\"form-control datetimepicker-input\" " + " data-target=\"#" +
			 * model + "_" + modelCamp + "\"" + " path=\"" + model + "." + modelCamp +
			 * "\" />\n"); code.append("                    <c:if test=\"${!" +
			 * readOnlyCondition + "}\" >\n"); code.
			 * append("                    <div class=\"input-group-append\"  data-target=\"#"
			 * + model + "_" + modelCamp + "\"  data-toggle=\"datetimepicker\">\n"); code.
			 * append("                        <div class=\"input-group-text\"><i class=\""
			 * + ICON_CALENDAR + "\"></i></div>\n");
			 * code.append("                    </div>\n");
			 * code.append("                    </c:if>\n");
			 * code.append("                </div>\n"); code.append("            </div>\n");
			 * 
			 * { String componentID = model + "_" + modelCamp;
			 * code.append(generateDatePicker(componentID)); }
			 * 
			 * 
			 * return code.toString();
			 */
		case WebType.Time:
			/*
			 * code.append("    <form:errors path=\"" + model + "." + modelCamp +
			 * "\" cssClass=\"errorField alert alert-danger\" />\n");
			 * 
			 * 
			 * code.append("            <div class=\"form-group\">\n");
			 * code.append("                <div class=\"input-group date\" id=\"" + model +
			 * "_" + modelCamp + "\" data-target-input=\"nearest\">\n");
			 * 
			 * 
			 * code.append("                      <form:input " + readOnlyAttribute +
			 * " cssClass=\"form-control datetimepicker-input\" " + " data-target=\"#" +
			 * model + "_" + modelCamp + "\"" + " path=\"" + model + "." + modelCamp +
			 * "\" />\n"); code.append("                    <c:if test=\"${!" +
			 * readOnlyCondition + "}\" >\n"); code.
			 * append("                    <div class=\"input-group-append\"  data-target=\"#"
			 * + model + "_" + modelCamp + "\"  data-toggle=\"datetimepicker\">\n"); code.
			 * append("                        <div class=\"input-group-text\"><i class=\""
			 * + IconUtils.ICON_CLOCK + "\"></i></div>\n");
			 * code.append("                    </div>\n");
			 * code.append("                    </c:if>\n");
			 * code.append("                </div>\n"); code.append("            </div>\n");
			 * 
			 * 
			 * { String componentID = model + "_" + modelCamp;
			 * code.append(generateTimePicker(componentID)); }
			 * 
			 * 
			 * 
			 * return code.toString();
			 */
		case WebType.DateTime: {
			String pathID = model + "." + modelCamp;
			code.append("    <form:errors path=\"" + pathID + "\" cssClass=\"errorField alert alert-danger\" />\n");
			String componentID = model + "_" + modelCamp;
			code.append(
					generateCommonDateTimePicker(webType, readOnlyCondition, readOnlyAttribute, componentID, pathID));
			return code.toString();
		}

		// TODO S'han de fer que tots els camps que referenciin a la taula fitxer
		// canviin el tipus web a FILE
		case WebType.File:
		case WebType.UserID:
		case WebType.RoleID:
			throw new Exception("Els tipus WEB UserID i RoleID no estan suportats !!!!!");

		default:
			throw new Exception(
					"NO existix generador de FORM_JSP pel camp " + field.javaName + " de tipus " + field.getClass());

		} // Final Switch

	}

	protected static String generateTimePicker(String componentID, String path) throws Exception {
		final int webType = WebType.Time;
		final String readOnlyCondition = "false";
		final String readOnlyAttribute = "";
		return generateCommonDateTimePicker(webType, readOnlyCondition, readOnlyAttribute, componentID, path);
	}

	protected static String generateDateTimePicker(String componentID, String path) throws Exception {
		final int webType = WebType.DateTime;
		final String readOnlyCondition = "false";
		final String readOnlyAttribute = "";
		return generateCommonDateTimePicker(webType, readOnlyCondition, readOnlyAttribute, componentID, path);
	}

	protected static String generateDatePicker(String componentID, String path) throws Exception {

		final int webType = WebType.Date;
		final String readOnlyCondition = "false";
		final String readOnlyAttribute = "";
		return generateCommonDateTimePicker(webType, readOnlyCondition, readOnlyAttribute, componentID, path);
	}

	protected static String generateCommonDateTimePicker(int webType, final String readOnlyCondition,
			final String readOnlyAttribute, String componentID, String path) throws Exception {

		String pattern;
		String icon;

		switch (webType) {

		case WebType.DateTime:
			pattern = "${gen:getJSDateTimePattern()}";
			icon = IconUtils.ICON_CALENDAR;
			break;

		case WebType.Date:
			pattern = "${gen:getJSDatePattern()}";
			icon = IconUtils.ICON_CALENDAR;
			break;

		case WebType.Time:
			pattern = "${gen:getJSTimePattern()}";
			icon = IconUtils.ICON_CLOCK;
			break;

		default:
			throw new Exception("S'ha enviat un tipus de DATE-WEB no suportat:" + webType);

		}

		StringBuffer code = new StringBuffer();

		code.append("            <div class=\"form-group\"  style=\"margin-bottom: 0px;\" >\n");
		code.append("                <div class=\"input-group date\" id=\"" + componentID
				+ "\" data-target-input=\"nearest\">\n");

		code.append("                      <form:input " + readOnlyAttribute
				+ " cssClass=\"form-control datetimepicker-input\" " + " data-target=\"#" + componentID + "\""
				+ " path=\"" + path + "\" />\n");
		code.append("                    <c:if test=\"${!" + readOnlyCondition + "}\" >\n");
		code.append("                    <div class=\"input-group-append\"  data-target=\"#" + componentID
				+ "\"  data-toggle=\"datetimepicker\">\n");
		code.append("                        <div class=\"input-group-text\"><i class=\"" + icon + "\"></i></div>\n");
		code.append("                    </div>\n");
		code.append("                    </c:if>\n");
		code.append("                </div>\n");
		code.append("            </div>\n");

		code.append("        <script type=\"text/javascript\">\n");
		code.append("            $(function () {\n");
		code.append("                $('#" + componentID + "').datetimepicker({\n");
		code.append("                    format: '" + pattern + "',\n");
		code.append("                    locale: '${lang}',\n");
		code.append("                    icons: {\n");
		code.append("                       time: '" + ICON_CLOCK + "'\n");
		code.append("                    }\n");
		code.append("                });\n");
		code.append("            });\n");
		code.append("        </script>");
		return code.toString();
	}

	/*
	 * public static String generateReadOnlySelect(String tab, String id, String
	 * readOnlyFieldCondition) {
	 * 
	 * StringBuffer code = new StringBuffer();
	 * code.append(tab).append("<c:if test=\"${" + readOnlyFieldCondition +
	 * "}\" >\n"); code.append(tab).append("<script language=\"javascript\">\n");
	 * code.append(tab).append("   $(\"#" + id +
	 * " option\").not(\":selected\").attr(\"disabled\", \"disabled\");\n");
	 * code.append(tab).append("</script>\n"); code.append(tab).append("</c:if>\n");
	 * 
	 * return code.toString(); }
	 */

	protected static String getCssInputTypeBySize(int size) {
		// return "";

		if (size < 10) {
			return "w-25";
		}
		/*
		 * if (size < 10) { return "w-25"; }
		 */
		if (size < 40) {
			return "w-50";
		}
		if (size < 80) {
			return "w-75";
		}

		return "w-100";

	}

}
