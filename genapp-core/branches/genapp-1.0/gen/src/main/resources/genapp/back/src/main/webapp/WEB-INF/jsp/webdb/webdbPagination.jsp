<%@page import="org.fundaciobit.genapp.common.web.exportdata.IDataExporter"%>
<%@page import="java.util.List"%>
<%@page import="org.fundaciobit.genapp.common.web.exportdata.DataExporterManager"%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp" %>

<script type="text/javascript">
function submitForm(action) {  
  document.${dollar}{formName}.action = action; 
  document.${dollar}{formName}.submit();
}
</script>
<div >
<div class="pagination pagination-centered">

  <c:if test="${dollar}{__theFilterForm.visibleExportList}">
      <div style="float:left; ">
      <%
         for(IDataExporter dataExporter : DataExporterManager.getAllDataExporters()) {
      %>
      <c:url var="exportUrl" value="${dollar}{contexte}/export"/>
          <a href="#" onclick="submitForm('${dollar}{exportUrl}/<%=dataExporter.getID()%>')">
           <img alt="<%=dataExporter.getName()%>" src="<%=request.getContextPath() + "/common/icon/" + dataExporter.getID()%>"/> 
          </a>
      <% } %>
      </div>
  </c:if>


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
                <li><a href="#" onclick="submitForm('${dollar}{firstUrl}')">&lt;&lt;</a></li>
                <li><a href="#" onclick="submitForm('${dollar}{prevUrl}')">&lt;</a></li>
            </c:otherwise>
        </c:choose>

        <c:forEach var="i" begin="${dollar}{beginIndex}" end="${dollar}{endIndex}">
            <c:url var="pageUrl" value="${dollar}{contexte}/list/${dollar}{i}" />
            <c:choose>
                <c:when test="${dollar}{i == currentIndex}">
                    <li class="active"><a href="#"><c:out value="${dollar}{i}" /></a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="#" onclick="submitForm('${dollar}{pageUrl}')"><c:out value="${dollar}{i}" /></a></li>
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <c:choose>
            <c:when test="${dollar}{currentIndex == totalPages}">
                <li class="disabled"><a href="#"> &gt;</a></li>
                <li class="disabled"><a href="#">&gt;&gt;</a></li>
            </c:when>
            <c:otherwise>
                <li><a href="#" onclick="submitForm('${dollar}{nextUrl}')">&gt;</a></li>
                <li><a href="#" onclick="submitForm('${dollar}{lastUrl}')">&gt;&gt;</a></li>
            </c:otherwise>
        </c:choose>
    </ul>
    <fmt:message var="allitems" key="genapp.form.allitems" />
    <div style="float:right; ">
      <label><fmt:message key="genapp.form.itemsperpage" />:</label>
      <form:select cssClass="input-small" cssStyle="width:4em;"  onchange="document.${dollar}{formName}.submit()" path="itemsPerPage" >
        <c:forEach var="num" items="${dollar}{__theFilterForm.allItemsPerPage}">
           <form:option value="${dollar}{num}" label="${dollar}{ (num == -1)? allitems : num}"/>                 
        </c:forEach>
      </form:select>
    </div>
</div>
</div>
