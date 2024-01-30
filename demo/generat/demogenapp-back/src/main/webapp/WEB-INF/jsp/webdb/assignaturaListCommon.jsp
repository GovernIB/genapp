<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>

  <c:set var="contexte" value="${assignaturaFilterForm.contexte}"/>
  <c:set var="formName" value="assignatura" />
  <c:set var="__theFilterForm" value="${assignaturaFilterForm}" />
  <c:if test="${empty assignaturaFilterForm.entityNameCode}">
    <fmt:message var="entityname" key="assignatura.assignatura"/>
  </c:if>
  <c:if test="${not empty assignaturaFilterForm.entityNameCode}">
    <fmt:message var="entityname" key="${assignaturaFilterForm.entityNameCode}"/>
  </c:if>
  <c:if test="${empty assignaturaFilterForm.entityNameCodePlural}">
    <fmt:message var="entitynameplural" key="assignatura.assignatura"/>
  </c:if>
  <c:if test="${not empty assignaturaFilterForm.entityNameCodePlural}">
    <fmt:message var="entitynameplural" key="${assignaturaFilterForm.entityNameCodePlural}"/>
  </c:if>
  <%-- HIDDEN PARAMS: ORDER BY --%> 
  <form:hidden id="orderBy" path="orderBy"/> 
  <form:hidden id="orderAsc" path="orderAsc"/>

  <form:hidden path="nou" value="false"/>

<script type="text/javascript">
  function executeOrderBy(orderBy, orderType) {
    document.getElementById('orderBy').value = orderBy;
    document.getElementById('orderAsc').value = orderType;
    document.assignatura.submit();  
  }
</script>
