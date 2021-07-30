<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>

  <c:set var="contexte" value="${productsFilterForm.contexte}"/>
  <c:set var="formName" value="products" />
  <c:set var="__theFilterForm" value="${productsFilterForm}" />
  <c:if test="${empty productsFilterForm.entityNameCode}">
    <fmt:message var="entityname" key="products.products"/>
  </c:if>
  <c:if test="${not empty productsFilterForm.entityNameCode}">
    <fmt:message var="entityname" key="${productsFilterForm.entityNameCode}"/>
  </c:if>
  <c:if test="${empty productsFilterForm.entityNameCodePlural}">
    <fmt:message var="entitynameplural" key="products.products"/>
  </c:if>
  <c:if test="${not empty productsFilterForm.entityNameCodePlural}">
    <fmt:message var="entitynameplural" key="${productsFilterForm.entityNameCodePlural}"/>
  </c:if>
  <%-- HIDDEN PARAMS: ORDER BY --%> 
  <form:hidden id="orderBy" path="orderBy"/> 
  <form:hidden id="orderAsc" path="orderAsc"/>

  <form:hidden path="nou" value="false"/>

<script type="text/javascript">
  function executeOrderBy(orderBy, orderType) {
    document.getElementById('orderBy').value = orderBy;
    document.getElementById('orderAsc').value = orderType;
    document.products.submit();  
  }
</script>
