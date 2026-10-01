<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>
  
<div class="lead" style="margin-bottom:10px">
<label style="font-size: 1.25rem;font-weight: bold;">
 <c:choose>
  <c:when test="${fn:startsWith(assignaturaForm.titleCode,'=')}">
       <c:out value="${fn:substringAfter(assignaturaForm.titleCode, '=')}" escapeXml="false"/>
  </c:when>
  <c:when test="${not empty assignaturaForm.titleCode}">
    <fmt:message key="${assignaturaForm.titleCode}" >
      <fmt:param value="${assignaturaForm.titleParam}" />
    </fmt:message>
  </c:when>
  <c:otherwise>
    <c:if test="${empty assignaturaForm.entityNameCode}">
      <fmt:message var="entityname" key="assignatura.assignatura"/>
    </c:if>
    <c:if test="${not empty assignaturaForm.entityNameCode}">
      <fmt:message var="entityname" key="${assignaturaForm.entityNameCode}"/>
    </c:if>
    <c:set var="keytitle" value="${assignaturaForm.nou?'genapp.createtitle':(assignaturaForm.view?'genapp.viewtitle':'genapp.edittitle')}"/>
    <fmt:message key="${keytitle}">
      <fmt:param value="${entityname}"/>
    </fmt:message>
    </c:otherwise>
 </c:choose></label>
  <c:if test="${not empty assignaturaForm.subTitleCode}">
<h6 style="line-height: 10px; margin-top: 0px; margin-bottom: 0px;font-style:italic;">
<c:set var="subtitleTranslated" value="${fn:startsWith(assignaturaForm.subTitleCode,'=')}" />
<c:if test="${subtitleTranslated}">
   <c:out value="${fn:substringAfter(assignaturaForm.subTitleCode, '=')}" escapeXml="false"/>
</c:if>
<c:if test="${not subtitleTranslated}">
  <fmt:message key="${assignaturaForm.subTitleCode}" />
</c:if>
</h6>
  </c:if>
</div>