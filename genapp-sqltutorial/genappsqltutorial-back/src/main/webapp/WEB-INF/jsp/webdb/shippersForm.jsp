
<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>
 
  <%@include file="shippersFormTitle.jsp" %>


<form:form modelAttribute="shippersForm" method="${(empty method)?'post':method}"
  enctype="multipart/form-data">
  
  <c:set var="contexte" value="${shippersForm.contexte}"/>
  <form:hidden path="nou" />
  
  <%@include file="shippersFormCorePre.jsp" %>

  <%@include file="shippersFormCore.jsp" %>

  <%@include file="shippersFormCorePost.jsp" %>

  <%@include file="shippersFormButtons.jsp" %>

  <c:if test="${not empty shippersForm.sections}">
     <c:set var="__basename" value="shippers" scope="page" />
     <%@include file="sections.jsp"%>
  </c:if>


  <c:if test="${shippersForm.attachedAdditionalJspCode}">
     <%@include file="../webdbmodificable/shippersFormModificable.jsp" %>
  </c:if>

</form:form>


