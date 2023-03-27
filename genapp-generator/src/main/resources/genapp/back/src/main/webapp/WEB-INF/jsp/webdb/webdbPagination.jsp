<%@page
    import="org.fundaciobit.genapp.common.web.exportdata.IDataExporter"%>
<%@page import="java.util.List"%>
<%@page
    import="org.fundaciobit.genapp.common.web.exportdata.DataExporterManager"%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>

<script type="text/javascript">

var currentActionForExporter = "";

function submitForm(action, reassign) {
  if (reassign) {  
	 currentActionForExporter = document.${dollar}{formName}.action;
  }
  document.${dollar}{formName}.action = action;
  if (reassign) {
    setTimeout(reassignAction, 3000);
  } 
  document.${dollar}{formName}.submit();
}

function reassignAction() {
  document.${dollar}{formName}.action = currentActionForExporter;
  currentActionForExporter = "";
}
</script>
<div class="row" id="${dollar}{formName}_pagination"
    style="width: 100%;">

    <div class="col" style="text-align: left;"
        id="${dollar}{formName}_pagination_left">
        <c:if test="${dollar}{__theFilterForm.visibleExportList}">

            <%
            for (IDataExporter dataExporter : DataExporterManager.getAllDataExporters()) {
            %>
            <c:url var="exportUrl" value="${dollar}{contexte}/export" />
            <a href="#"
                onclick="submitForm('${dollar}{exportUrl}/<%=dataExporter.getID()%>', true)">
                <img alt="<%=dataExporter.getName()%>"
                src="<%=request.getContextPath() + "/common/icon/" + dataExporter.getID()%>" />
            </a>
            <%
            }
            %>
        </c:if>
    </div>

    <div class="col-md-auto text-center"
        id="${dollar}{formName}_pagination_center">
        <c:if test="${dollar}{not empty __theFilterForm.itemsPerPage}">
          <%@include file="webdbPaginationOnlyBar.jsp" %>
        </c:if>
    </div>

    <fmt:message var="allitems" key="genapp.form.allitems" />
    <div class="col" style="text-align: right"
        id="${dollar}{formName}_pagination_right">
        <div class="row float-right">
        <label><fmt:message key="genapp.form.itemsperpage" />:</label>
        <form:select cssClass="input-small" cssStyle="width:4em;"
            onchange="document.${dollar}{formName}.submit()"
            path="itemsPerPage">
            <c:forEach var="num"
                items="${dollar}{__theFilterForm.allItemsPerPage}">
                <form:option value="${dollar}{num}"
                    label="${dollar}{ (num == -1)? allitems : num}" />
            </c:forEach>
        </form:select>
        </div>
    </div>


</div>

</div>

