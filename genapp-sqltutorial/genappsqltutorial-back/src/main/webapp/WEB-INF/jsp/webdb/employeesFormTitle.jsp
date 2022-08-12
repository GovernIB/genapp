<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>
  
<div class="lead" style="margin-bottom:10px">
<label style="font-size: 1.25rem;font-weight: bold;">
 <c:choose>
  <c:when test="${fn:startsWith(employeesForm.titleCode,'=')}">
       <c:out value="${fn:substringAfter(employeesForm.titleCode, '=')}" escapeXml="false"/>
  </c:when>
  <c:when test="${not empty employeesForm.titleCode}">
    <fmt:message key="${employeesForm.titleCode}" >
      <fmt:param value="${employeesForm.titleParam}" />
    </fmt:message>
  </c:when>
  <c:otherwise>
    <c:if test="${empty employeesForm.entityNameCode}">
      <fmt:message var="entityname" key="employees.employees"/>
    </c:if>
    <c:if test="${not empty employeesForm.entityNameCode}">
      <fmt:message var="entityname" key="${employeesForm.entityNameCode}"/>
    </c:if>
    <c:set var="keytitle" value="${employeesForm.nou?'genapp.createtitle':(employeesForm.view?'genapp.viewtitle':'genapp.edittitle')}"/>
    <fmt:message key="${keytitle}">
      <fmt:param value="${entityname}"/>
    </fmt:message>
    </c:otherwise>
 </c:choose></label>
  <c:if test="${not empty employeesForm.subTitleCode}">
<h6 style="line-height: 10px; margin-top: 0px; margin-bottom: 0px;font-style:italic;">
<c:set var="subtitleTranslated" value="${fn:startsWith(employeesForm.subTitleCode,'=')}" />
<c:if test="${subtitleTranslated}">
   <c:out value="${fn:substringAfter(employeesForm.subTitleCode, '=')}" escapeXml="false"/>
</c:if>
<c:if test="${not subtitleTranslated}">
  <fmt:message key="${employeesForm.subTitleCode}" />
</c:if>
</h6>
  </c:if>
</div>