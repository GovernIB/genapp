
<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>


<form:form modelAttribute="assignaturaForm" method="${(empty method)?'post':method}"
  enctype="multipart/form-data">
  
  <%@include file="assignaturaFormTitle.jsp" %>
 
  <c:set var="contexte" value="${assignaturaForm.contexte}"/>
  <form:hidden path="nou" />
  
  <%@include file="assignaturaFormCorePre.jsp" %>

  <%@include file="assignaturaFormCore.jsp" %>

  <%@include file="assignaturaFormCorePost.jsp" %>

  <%@include file="assignaturaFormButtons.jsp" %>

  <c:if test="${not empty assignaturaForm.sections}">
     <c:set var="__basename" value="assignatura" scope="page" />
     <%@include file="sections.jsp"%>
  </c:if>


  <c:if test="${assignaturaForm.attachedAdditionalJspCode}">
     <%@include file="../webdbmodificable/assignaturaFormModificable.jsp" %>
  </c:if>

</form:form>


