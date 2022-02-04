<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>
  
<div class="lead" style="margin-bottom:10px">
 <c:choose>
  <c:when test="${fn:startsWith(suppliersForm.titleCode,'=')}">
       <c:out value="${fn:substringAfter(suppliersForm.titleCode, '=')}" escapeXml="false"/>
  </c:when>
  <c:when test="${not empty suppliersForm.titleCode}">
    <fmt:message key="${suppliersForm.titleCode}" >
      <fmt:param value="${suppliersForm.titleParam}" />
    </fmt:message>
  </c:when>
  <c:otherwise>
    <c:if test="${empty suppliersForm.entityNameCode}">
      <fmt:message var="entityname" key="suppliers.suppliers"/>
    </c:if>
    <c:if test="${not empty suppliersForm.entityNameCode}">
      <fmt:message var="entityname" key="${suppliersForm.entityNameCode}"/>
    </c:if>
    <c:set var="keytitle" value="${suppliersForm.nou?'genapp.createtitle':(suppliersForm.view?'genapp.viewtitle':'genapp.edittitle')}"/>
    <fmt:message key="${keytitle}">
      <fmt:param value="${entityname}"/>
    </fmt:message>
    </c:otherwise>
 </c:choose>
  
  <c:if test="${not empty suppliersForm.subTitleCode}">
  <br/><h5 style="line-height: 10px; margin-top: 0px; margin-bottom: 0px;">
<c:set var="subtitleTranslated" value="${fn:startsWith(suppliersForm.subTitleCode,'=')}" />
<c:if test="${subtitleTranslated}">
   <c:out value="${fn:substringAfter(suppliersForm.subTitleCode, '=')}" escapeXml="false"/>
</c:if>
<c:if test="${not subtitleTranslated}">
  <fmt:message key="${suppliersForm.subTitleCode}" />
</c:if>
</h5>
  </c:if>
</div>