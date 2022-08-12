
<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>
 
  <%@include file="ordersFormTitle.jsp" %>


<form:form modelAttribute="ordersForm" method="${(empty method)?'post':method}"
  enctype="multipart/form-data">
  
  <c:set var="contexte" value="${ordersForm.contexte}"/>
  <form:hidden path="nou" />
  
  <%@include file="ordersFormCorePre.jsp" %>

  <%@include file="ordersFormCore.jsp" %>

  <%@include file="ordersFormCorePost.jsp" %>

  <%@include file="ordersFormButtons.jsp" %>

  <c:if test="${not empty ordersForm.sections}">
     <c:set var="__basename" value="orders" scope="page" />
     <%@include file="sections.jsp"%>
  </c:if>


  <c:if test="${ordersForm.attachedAdditionalJspCode}">
     <%@include file="../webdbmodificable/ordersFormModificable.jsp" %>
  </c:if>

</form:form>


