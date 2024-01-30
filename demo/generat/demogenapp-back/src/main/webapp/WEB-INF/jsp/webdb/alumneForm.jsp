
<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>


<form:form modelAttribute="alumneForm" method="${(empty method)?'post':method}"
  enctype="multipart/form-data">
  
  <%@include file="alumneFormTitle.jsp" %>
 
  <c:set var="contexte" value="${alumneForm.contexte}"/>
  <form:hidden path="nou" />
  
  <%@include file="alumneFormCorePre.jsp" %>

  <%@include file="alumneFormCore.jsp" %>

  <%@include file="alumneFormCorePost.jsp" %>

  <%@include file="alumneFormButtons.jsp" %>

  <c:if test="${not empty alumneForm.sections}">
     <c:set var="__basename" value="alumne" scope="page" />
     <%@include file="sections.jsp"%>
  </c:if>


  <c:if test="${alumneForm.attachedAdditionalJspCode}">
     <%@include file="../webdbmodificable/alumneFormModificable.jsp" %>
  </c:if>

</form:form>


