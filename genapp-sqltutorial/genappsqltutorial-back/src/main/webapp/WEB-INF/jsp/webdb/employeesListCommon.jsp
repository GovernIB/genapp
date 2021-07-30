<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>

  <c:set var="contexte" value="${employeesFilterForm.contexte}"/>
  <c:set var="formName" value="employees" />
  <c:set var="__theFilterForm" value="${employeesFilterForm}" />
  <c:if test="${empty employeesFilterForm.entityNameCode}">
    <fmt:message var="entityname" key="employees.employees"/>
  </c:if>
  <c:if test="${not empty employeesFilterForm.entityNameCode}">
    <fmt:message var="entityname" key="${employeesFilterForm.entityNameCode}"/>
  </c:if>
  <c:if test="${empty employeesFilterForm.entityNameCodePlural}">
    <fmt:message var="entitynameplural" key="employees.employees"/>
  </c:if>
  <c:if test="${not empty employeesFilterForm.entityNameCodePlural}">
    <fmt:message var="entitynameplural" key="${employeesFilterForm.entityNameCodePlural}"/>
  </c:if>
  <%-- HIDDEN PARAMS: ORDER BY --%> 
  <form:hidden id="orderBy" path="orderBy"/> 
  <form:hidden id="orderAsc" path="orderAsc"/>

  <form:hidden path="nou" value="false"/>

<script type="text/javascript">
  function executeOrderBy(orderBy, orderType) {
    document.getElementById('orderBy').value = orderBy;
    document.getElementById('orderAsc').value = orderType;
    document.employees.submit();  
  }
</script>
