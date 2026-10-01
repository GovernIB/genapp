<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>

  <c:set var="contexte" value="${alumneFilterForm.contexte}"/>
  <c:set var="formName" value="alumne" />
  <c:set var="__theFilterForm" value="${alumneFilterForm}" />
  <c:if test="${empty alumneFilterForm.entityNameCode}">
    <fmt:message var="entityname" key="alumne.alumne"/>
  </c:if>
  <c:if test="${not empty alumneFilterForm.entityNameCode}">
    <fmt:message var="entityname" key="${alumneFilterForm.entityNameCode}"/>
  </c:if>
  <c:if test="${empty alumneFilterForm.entityNameCodePlural}">
    <fmt:message var="entitynameplural" key="alumne.alumne"/>
  </c:if>
  <c:if test="${not empty alumneFilterForm.entityNameCodePlural}">
    <fmt:message var="entitynameplural" key="${alumneFilterForm.entityNameCodePlural}"/>
  </c:if>
  <%-- HIDDEN PARAMS: ORDER BY --%> 
  <form:hidden id="orderBy" path="orderBy"/> 
  <form:hidden id="orderAsc" path="orderAsc"/>

  <form:hidden path="nou" value="false"/>

<script type="text/javascript">
  function executeOrderBy(orderBy, orderType) {
    document.getElementById('orderBy').value = orderBy;
    document.getElementById('orderAsc').value = orderType;
    document.alumne.submit();  
  }
</script>
