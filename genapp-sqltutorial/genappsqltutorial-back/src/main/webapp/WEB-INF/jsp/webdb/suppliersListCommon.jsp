<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>

  <c:set var="contexte" value="${suppliersFilterForm.contexte}"/>
  <c:set var="formName" value="suppliers" />
  <c:set var="__theFilterForm" value="${suppliersFilterForm}" />
  <c:if test="${empty suppliersFilterForm.entityNameCode}">
    <fmt:message var="entityname" key="suppliers.suppliers"/>
  </c:if>
  <c:if test="${not empty suppliersFilterForm.entityNameCode}">
    <fmt:message var="entityname" key="${suppliersFilterForm.entityNameCode}"/>
  </c:if>
  <c:if test="${empty suppliersFilterForm.entityNameCodePlural}">
    <fmt:message var="entitynameplural" key="suppliers.suppliers"/>
  </c:if>
  <c:if test="${not empty suppliersFilterForm.entityNameCodePlural}">
    <fmt:message var="entitynameplural" key="${suppliersFilterForm.entityNameCodePlural}"/>
  </c:if>
  <%-- HIDDEN PARAMS: ORDER BY --%> 
  <form:hidden id="orderBy" path="orderBy"/> 
  <form:hidden id="orderAsc" path="orderAsc"/>

  <form:hidden path="nou" value="false"/>

<script type="text/javascript">
  function executeOrderBy(orderBy, orderType) {
    document.getElementById('orderBy').value = orderBy;
    document.getElementById('orderAsc').value = orderType;
    document.suppliers.submit();  
  }
</script>
