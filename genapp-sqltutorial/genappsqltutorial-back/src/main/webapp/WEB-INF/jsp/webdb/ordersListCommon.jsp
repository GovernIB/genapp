<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>

  <c:set var="contexte" value="${ordersFilterForm.contexte}"/>
  <c:set var="formName" value="orders" />
  <c:set var="__theFilterForm" value="${ordersFilterForm}" />
  <c:if test="${empty ordersFilterForm.entityNameCode}">
    <fmt:message var="entityname" key="orders.orders"/>
  </c:if>
  <c:if test="${not empty ordersFilterForm.entityNameCode}">
    <fmt:message var="entityname" key="${ordersFilterForm.entityNameCode}"/>
  </c:if>
  <c:if test="${empty ordersFilterForm.entityNameCodePlural}">
    <fmt:message var="entitynameplural" key="orders.orders"/>
  </c:if>
  <c:if test="${not empty ordersFilterForm.entityNameCodePlural}">
    <fmt:message var="entitynameplural" key="${ordersFilterForm.entityNameCodePlural}"/>
  </c:if>
  <%-- HIDDEN PARAMS: ORDER BY --%> 
  <form:hidden id="orderBy" path="orderBy"/> 
  <form:hidden id="orderAsc" path="orderAsc"/>

  <form:hidden path="nou" value="false"/>

<script type="text/javascript">
  function executeOrderBy(orderBy, orderType) {
    document.getElementById('orderBy').value = orderBy;
    document.getElementById('orderAsc').value = orderType;
    document.orders.submit();  
  }
</script>
