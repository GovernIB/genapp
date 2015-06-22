<%@page import="org.fundaciobit.genapp.common.web.exportdata.IDataExporter"%>
<%@page import="java.util.List"%>
<%@page import="org.fundaciobit.genapp.common.web.exportdata.DataExporterManager"%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp" %>

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
<div id="${dollar}{formName}_pagination" style="width:100%; text-align:center;" >

  <div style="float:left;" id="${dollar}{formName}_pagination_left">
  <c:if test="${dollar}{__theFilterForm.visibleExportList}">
      
      <%
         for(IDataExporter dataExporter : DataExporterManager.getAllDataExporters()) {
      %>
      <c:url var="exportUrl" value="${dollar}{contexte}/export"/>
          <a href="#" onclick="submitForm('${dollar}{exportUrl}/<%=dataExporter.getID()%>', true)">
           <img alt="<%=dataExporter.getName()%>" src="<%=request.getContextPath() + "/common/icon/" + dataExporter.getID()%>"/> 
          </a>
      <% } %>
  </c:if>
  </div>
  
  <fmt:message var="allitems" key="genapp.form.allitems" />
    <div style="float:right;" id="${dollar}{formName}_pagination_right" >
      <label><fmt:message key="genapp.form.itemsperpage" />:</label>
      <form:select cssClass="input-small" cssStyle="width:4em;"  onchange="document.${dollar}{formName}.submit()" path="itemsPerPage" >
        <c:forEach var="num" items="${dollar}{__theFilterForm.allItemsPerPage}">
           <form:option value="${dollar}{num}" label="${dollar}{ (num == -1)? allitems : num}"/>                 
        </c:forEach>
      </form:select>
    </div>


  <div class="pagination pagination-centered" id="${dollar}{formName}_pagination_center">
    <c:url var="firstUrl" value="${dollar}{contexte}/list/1" 
    /><c:url var="lastUrl" value="${dollar}{contexte}/list/${dollar}{totalPages}" 
    /><c:url var="prevUrl" value="${dollar}{contexte}/list/${dollar}{currentIndex - 1}" 
    /><c:url var="nextUrl" value="${dollar}{contexte}/list/${dollar}{currentIndex + 1}" />

    <ul>
        <c:choose>
            <c:when test="${dollar}{currentIndex == 1}">
                <li class="disabled"><a href="#">&lt;&lt;</a></li>
                <li class="disabled"><a href="#">&lt;</a></li>
            </c:when>
            <c:otherwise>
                <li><a href="#" onclick="submitForm('${dollar}{firstUrl}', false)">&lt;&lt;</a></li>
                <li><a href="#" onclick="submitForm('${dollar}{prevUrl}', false)">&lt;</a></li>
            </c:otherwise>
        </c:choose>

        <c:forEach var="i" begin="${dollar}{beginIndex}" end="${dollar}{endIndex}">
            <c:url var="pageUrl" value="${dollar}{contexte}/list/${dollar}{i}" />
            <c:choose>
                <c:when test="${dollar}{i == currentIndex}">
                    <li class="active"><a href="#"><c:out value="${dollar}{i}" /></a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="#" onclick="submitForm('${dollar}{pageUrl}', false)"><c:out value="${dollar}{i}" /></a></li>
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <c:choose>
            <c:when test="${dollar}{currentIndex == totalPages}">
                <li class="disabled"><a href="#"> &gt;</a></li>
                <li class="disabled"><a href="#">&gt;&gt;</a></li>
            </c:when>
            <c:otherwise>
                <li><a href="#" onclick="submitForm('${dollar}{nextUrl}', false)">&gt;</a></li>
                <li><a href="#" onclick="submitForm('${dollar}{lastUrl}', false)">&gt;&gt;</a></li>
            </c:otherwise>
        </c:choose>
    </ul>
    </div>
    
    
</div>

