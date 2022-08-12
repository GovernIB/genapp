
<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>
 
  <%@include file="employeesFormTitle.jsp" %>


<form:form modelAttribute="employeesForm" method="${(empty method)?'post':method}"
  enctype="multipart/form-data">
  
  <c:set var="contexte" value="${employeesForm.contexte}"/>
  <form:hidden path="nou" />
  
  <%@include file="employeesFormCorePre.jsp" %>

  <%@include file="employeesFormCore.jsp" %>

  <%@include file="employeesFormCorePost.jsp" %>

  <%@include file="employeesFormButtons.jsp" %>

  <c:if test="${not empty employeesForm.sections}">
     <c:set var="__basename" value="employees" scope="page" />
     <%@include file="sections.jsp"%>
  </c:if>


  <c:if test="${employeesForm.attachedAdditionalJspCode}">
     <%@include file="../webdbmodificable/employeesFormModificable.jsp" %>
  </c:if>

</form:form>


