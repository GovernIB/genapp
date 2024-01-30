<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>

  <c:set var="contexte" value="${assignaturaAlumneFilterForm.contexte}"/>
  <c:set var="formName" value="assignaturaAlumne" />
  <c:set var="__theFilterForm" value="${assignaturaAlumneFilterForm}" />
  <c:if test="${empty assignaturaAlumneFilterForm.entityNameCode}">
    <fmt:message var="entityname" key="assignaturaAlumne.assignaturaAlumne"/>
  </c:if>
  <c:if test="${not empty assignaturaAlumneFilterForm.entityNameCode}">
    <fmt:message var="entityname" key="${assignaturaAlumneFilterForm.entityNameCode}"/>
  </c:if>
  <c:if test="${empty assignaturaAlumneFilterForm.entityNameCodePlural}">
    <fmt:message var="entitynameplural" key="assignaturaAlumne.assignaturaAlumne"/>
  </c:if>
  <c:if test="${not empty assignaturaAlumneFilterForm.entityNameCodePlural}">
    <fmt:message var="entitynameplural" key="${assignaturaAlumneFilterForm.entityNameCodePlural}"/>
  </c:if>
  <%-- HIDDEN PARAMS: ORDER BY --%> 
  <form:hidden id="orderBy" path="orderBy"/> 
  <form:hidden id="orderAsc" path="orderAsc"/>

  <form:hidden path="nou" value="false"/>

<script type="text/javascript">
  function executeOrderBy(orderBy, orderType) {
    document.getElementById('orderBy').value = orderBy;
    document.getElementById('orderAsc').value = orderType;
    document.assignaturaAlumne.submit();  
  }
</script>
