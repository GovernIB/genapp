
<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>


<form:form modelAttribute="assignaturaAlumneForm" method="${(empty method)?'post':method}"
  enctype="multipart/form-data">
  
  <%@include file="assignaturaAlumneFormTitle.jsp" %>
 
  <c:set var="contexte" value="${assignaturaAlumneForm.contexte}"/>
  <form:hidden path="nou" />
  
  <%@include file="assignaturaAlumneFormCorePre.jsp" %>

  <%@include file="assignaturaAlumneFormCore.jsp" %>

  <%@include file="assignaturaAlumneFormCorePost.jsp" %>

  <%@include file="assignaturaAlumneFormButtons.jsp" %>

  <c:if test="${not empty assignaturaAlumneForm.sections}">
     <c:set var="__basename" value="assignaturaAlumne" scope="page" />
     <%@include file="sections.jsp"%>
  </c:if>


  <c:if test="${assignaturaAlumneForm.attachedAdditionalJspCode}">
     <%@include file="../webdbmodificable/assignaturaAlumneFormModificable.jsp" %>
  </c:if>

</form:form>


