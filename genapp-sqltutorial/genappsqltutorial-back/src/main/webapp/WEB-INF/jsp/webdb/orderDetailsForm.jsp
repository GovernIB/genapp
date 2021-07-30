
<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>
 
  <%@include file="orderDetailsFormTitle.jsp" %>


<form:form modelAttribute="orderDetailsForm" method="${(empty method)?'post':method}"
  enctype="multipart/form-data">
  
  <c:set var="contexte" value="${orderDetailsForm.contexte}"/>
  <form:hidden path="nou" />
  
  <%@include file="orderDetailsFormCorePre.jsp" %>
  <%@include file="orderDetailsFormCore.jsp" %>

  <%@include file="orderDetailsFormCorePost.jsp" %>

  <%@include file="orderDetailsFormButtons.jsp" %>

  <c:if test="${orderDetailsForm.attachedAdditionalJspCode}">
     <%@include file="../webdbmodificable/orderDetailsFormModificable.jsp" %>
  </c:if>

</form:form>


