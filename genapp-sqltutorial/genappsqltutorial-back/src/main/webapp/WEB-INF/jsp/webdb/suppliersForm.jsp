
<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>
 
  <%@include file="suppliersFormTitle.jsp" %>


<form:form modelAttribute="suppliersForm" method="${(empty method)?'post':method}"
  enctype="multipart/form-data">
  
  <c:set var="contexte" value="${suppliersForm.contexte}"/>
  <form:hidden path="nou" />
  
  <%@include file="suppliersFormCorePre.jsp" %>

  <%@include file="suppliersFormCore.jsp" %>

  <%@include file="suppliersFormCorePost.jsp" %>

  <%@include file="suppliersFormButtons.jsp" %>

  <c:if test="${not empty suppliersForm.sections}">
     <c:set var="__basename" value="suppliers" scope="page" />
     <%@include file="sections.jsp"%>
  </c:if>


  <c:if test="${suppliersForm.attachedAdditionalJspCode}">
     <%@include file="../webdbmodificable/suppliersFormModificable.jsp" %>
  </c:if>

</form:form>


