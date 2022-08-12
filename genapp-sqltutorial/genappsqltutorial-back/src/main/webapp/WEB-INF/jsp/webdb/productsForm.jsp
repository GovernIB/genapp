
<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>
 
  <%@include file="productsFormTitle.jsp" %>


<form:form modelAttribute="productsForm" method="${(empty method)?'post':method}"
  enctype="multipart/form-data">
  
  <c:set var="contexte" value="${productsForm.contexte}"/>
  <form:hidden path="nou" />
  
  <%@include file="productsFormCorePre.jsp" %>

  <%@include file="productsFormCore.jsp" %>

  <%@include file="productsFormCorePost.jsp" %>

  <%@include file="productsFormButtons.jsp" %>

  <c:if test="${not empty productsForm.sections}">
     <c:set var="__basename" value="products" scope="page" />
     <%@include file="sections.jsp"%>
  </c:if>


  <c:if test="${productsForm.attachedAdditionalJspCode}">
     <%@include file="../webdbmodificable/productsFormModificable.jsp" %>
  </c:if>

</form:form>


