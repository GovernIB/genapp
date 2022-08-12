
<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>
 
  <%@include file="customersFormTitle.jsp" %>


<form:form modelAttribute="customersForm" method="${(empty method)?'post':method}"
  enctype="multipart/form-data">
  
  <c:set var="contexte" value="${customersForm.contexte}"/>
  <form:hidden path="nou" />
  
  <%@include file="customersFormCorePre.jsp" %>

  <%@include file="customersFormCore.jsp" %>

  <%@include file="customersFormCorePost.jsp" %>

  <%@include file="customersFormButtons.jsp" %>

  <c:if test="${not empty customersForm.sections}">
     <c:set var="__basename" value="customers" scope="page" />
     <%@include file="sections.jsp"%>
  </c:if>


  <c:if test="${customersForm.attachedAdditionalJspCode}">
     <%@include file="../webdbmodificable/customersFormModificable.jsp" %>
  </c:if>

</form:form>


