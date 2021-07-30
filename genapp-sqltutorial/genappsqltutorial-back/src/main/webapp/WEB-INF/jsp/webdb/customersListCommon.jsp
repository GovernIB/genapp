<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>

  <c:set var="contexte" value="${customersFilterForm.contexte}"/>
  <c:set var="formName" value="customers" />
  <c:set var="__theFilterForm" value="${customersFilterForm}" />
  <c:if test="${empty customersFilterForm.entityNameCode}">
    <fmt:message var="entityname" key="customers.customers"/>
  </c:if>
  <c:if test="${not empty customersFilterForm.entityNameCode}">
    <fmt:message var="entityname" key="${customersFilterForm.entityNameCode}"/>
  </c:if>
  <c:if test="${empty customersFilterForm.entityNameCodePlural}">
    <fmt:message var="entitynameplural" key="customers.customers"/>
  </c:if>
  <c:if test="${not empty customersFilterForm.entityNameCodePlural}">
    <fmt:message var="entitynameplural" key="${customersFilterForm.entityNameCodePlural}"/>
  </c:if>
  <%-- HIDDEN PARAMS: ORDER BY --%> 
  <form:hidden id="orderBy" path="orderBy"/> 
  <form:hidden id="orderAsc" path="orderAsc"/>

  <form:hidden path="nou" value="false"/>

<script type="text/javascript">
  function executeOrderBy(orderBy, orderType) {
    document.getElementById('orderBy').value = orderBy;
    document.getElementById('orderAsc').value = orderType;
    document.customers.submit();  
  }
</script>
