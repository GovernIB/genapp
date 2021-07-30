<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>

  <c:set var="contexte" value="${shippersFilterForm.contexte}"/>
  <c:set var="formName" value="shippers" />
  <c:set var="__theFilterForm" value="${shippersFilterForm}" />
  <c:if test="${empty shippersFilterForm.entityNameCode}">
    <fmt:message var="entityname" key="shippers.shippers"/>
  </c:if>
  <c:if test="${not empty shippersFilterForm.entityNameCode}">
    <fmt:message var="entityname" key="${shippersFilterForm.entityNameCode}"/>
  </c:if>
  <c:if test="${empty shippersFilterForm.entityNameCodePlural}">
    <fmt:message var="entitynameplural" key="shippers.shippers"/>
  </c:if>
  <c:if test="${not empty shippersFilterForm.entityNameCodePlural}">
    <fmt:message var="entitynameplural" key="${shippersFilterForm.entityNameCodePlural}"/>
  </c:if>
  <%-- HIDDEN PARAMS: ORDER BY --%> 
  <form:hidden id="orderBy" path="orderBy"/> 
  <form:hidden id="orderAsc" path="orderAsc"/>

  <form:hidden path="nou" value="false"/>

<script type="text/javascript">
  function executeOrderBy(orderBy, orderType) {
    document.getElementById('orderBy').value = orderBy;
    document.getElementById('orderAsc').value = orderType;
    document.shippers.submit();  
  }
</script>
