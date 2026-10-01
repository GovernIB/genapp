<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>

  <c:set var="contexte" value="${categoriesFilterForm.contexte}"/>
  <c:set var="formName" value="categories" />
  <c:set var="__theFilterForm" value="${categoriesFilterForm}" />
  <c:if test="${empty categoriesFilterForm.entityNameCode}">
    <fmt:message var="entityname" key="categories.categories"/>
  </c:if>
  <c:if test="${not empty categoriesFilterForm.entityNameCode}">
    <fmt:message var="entityname" key="${categoriesFilterForm.entityNameCode}"/>
  </c:if>
  <c:if test="${empty categoriesFilterForm.entityNameCodePlural}">
    <fmt:message var="entitynameplural" key="categories.categories"/>
  </c:if>
  <c:if test="${not empty categoriesFilterForm.entityNameCodePlural}">
    <fmt:message var="entitynameplural" key="${categoriesFilterForm.entityNameCodePlural}"/>
  </c:if>
  <%-- HIDDEN PARAMS: ORDER BY --%> 
  <form:hidden id="orderBy" path="orderBy"/> 
  <form:hidden id="orderAsc" path="orderAsc"/>

  <form:hidden path="nou" value="false"/>

<script type="text/javascript">
  function executeOrderBy(orderBy, orderType) {
    document.getElementById('orderBy').value = orderBy;
    document.getElementById('orderAsc').value = orderType;
    document.categories.submit();  
  }
</script>
