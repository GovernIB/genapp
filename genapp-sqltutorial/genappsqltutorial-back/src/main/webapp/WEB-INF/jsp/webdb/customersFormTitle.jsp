<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>
  
<div class="lead" style="margin-bottom:10px">
<label style="font-size: 1.25rem;font-weight: bold;">
 <c:choose>
  <c:when test="${fn:startsWith(customersForm.titleCode,'=')}">
       <c:out value="${fn:substringAfter(customersForm.titleCode, '=')}" escapeXml="false"/>
  </c:when>
  <c:when test="${not empty customersForm.titleCode}">
    <fmt:message key="${customersForm.titleCode}" >
      <fmt:param value="${customersForm.titleParam}" />
    </fmt:message>
  </c:when>
  <c:otherwise>
    <c:if test="${empty customersForm.entityNameCode}">
      <fmt:message var="entityname" key="customers.customers"/>
    </c:if>
    <c:if test="${not empty customersForm.entityNameCode}">
      <fmt:message var="entityname" key="${customersForm.entityNameCode}"/>
    </c:if>
    <c:set var="keytitle" value="${customersForm.nou?'genapp.createtitle':(customersForm.view?'genapp.viewtitle':'genapp.edittitle')}"/>
    <fmt:message key="${keytitle}">
      <fmt:param value="${entityname}"/>
    </fmt:message>
    </c:otherwise>
 </c:choose></label>
  <c:if test="${not empty customersForm.subTitleCode}">
<h6 style="line-height: 10px; margin-top: 0px; margin-bottom: 0px;font-style:italic;">
<c:set var="subtitleTranslated" value="${fn:startsWith(customersForm.subTitleCode,'=')}" />
<c:if test="${subtitleTranslated}">
   <c:out value="${fn:substringAfter(customersForm.subTitleCode, '=')}" escapeXml="false"/>
</c:if>
<c:if test="${not subtitleTranslated}">
  <fmt:message key="${customersForm.subTitleCode}" />
</c:if>
</h6>
  </c:if>
</div>