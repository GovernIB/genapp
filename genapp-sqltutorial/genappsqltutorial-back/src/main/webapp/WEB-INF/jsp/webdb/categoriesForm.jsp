
<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>
 
  <%@include file="categoriesFormTitle.jsp" %>


<form:form modelAttribute="categoriesForm" method="${(empty method)?'post':method}"
  enctype="multipart/form-data">
  
  <c:set var="contexte" value="${categoriesForm.contexte}"/>
  <form:hidden path="nou" />
  
  <%@include file="categoriesFormCorePre.jsp" %>
  <%@include file="categoriesFormCore.jsp" %>

  <%@include file="categoriesFormCorePost.jsp" %>

  <%@include file="categoriesFormButtons.jsp" %>

  <c:if test="${categoriesForm.attachedAdditionalJspCode}">
     <%@include file="../webdbmodificable/categoriesFormModificable.jsp" %>
  </c:if>

</form:form>


