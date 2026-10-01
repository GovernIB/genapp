<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>

  <c:set var="contexte" value="${orderDetailsFilterForm.contexte}"/>
  <c:set var="formName" value="orderDetails" />
  <c:set var="__theFilterForm" value="${orderDetailsFilterForm}" />
  <c:if test="${empty orderDetailsFilterForm.entityNameCode}">
    <fmt:message var="entityname" key="orderDetails.orderDetails"/>
  </c:if>
  <c:if test="${not empty orderDetailsFilterForm.entityNameCode}">
    <fmt:message var="entityname" key="${orderDetailsFilterForm.entityNameCode}"/>
  </c:if>
  <c:if test="${empty orderDetailsFilterForm.entityNameCodePlural}">
    <fmt:message var="entitynameplural" key="orderDetails.orderDetails"/>
  </c:if>
  <c:if test="${not empty orderDetailsFilterForm.entityNameCodePlural}">
    <fmt:message var="entitynameplural" key="${orderDetailsFilterForm.entityNameCodePlural}"/>
  </c:if>
  <%-- HIDDEN PARAMS: ORDER BY --%> 
  <form:hidden id="orderBy" path="orderBy"/> 
  <form:hidden id="orderAsc" path="orderAsc"/>

  <form:hidden path="nou" value="false"/>

<script type="text/javascript">
  function executeOrderBy(orderBy, orderType) {
    document.getElementById('orderBy').value = orderBy;
    document.getElementById('orderAsc').value = orderType;
    document.orderDetails.submit();  
  }
</script>
