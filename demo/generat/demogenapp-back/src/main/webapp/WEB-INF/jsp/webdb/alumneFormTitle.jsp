<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>
  
<div class="lead" style="margin-bottom:10px">
<label style="font-size: 1.25rem;font-weight: bold;">
 <c:choose>
  <c:when test="${fn:startsWith(alumneForm.titleCode,'=')}">
       <c:out value="${fn:substringAfter(alumneForm.titleCode, '=')}" escapeXml="false"/>
  </c:when>
  <c:when test="${not empty alumneForm.titleCode}">
    <fmt:message key="${alumneForm.titleCode}" >
      <fmt:param value="${alumneForm.titleParam}" />
    </fmt:message>
  </c:when>
  <c:otherwise>
    <c:if test="${empty alumneForm.entityNameCode}">
      <fmt:message var="entityname" key="alumne.alumne"/>
    </c:if>
    <c:if test="${not empty alumneForm.entityNameCode}">
      <fmt:message var="entityname" key="${alumneForm.entityNameCode}"/>
    </c:if>
    <c:set var="keytitle" value="${alumneForm.nou?'genapp.createtitle':(alumneForm.view?'genapp.viewtitle':'genapp.edittitle')}"/>
    <fmt:message key="${keytitle}">
      <fmt:param value="${entityname}"/>
    </fmt:message>
    </c:otherwise>
 </c:choose></label>
  <c:if test="${not empty alumneForm.subTitleCode}">
<h6 style="line-height: 10px; margin-top: 0px; margin-bottom: 0px;font-style:italic;">
<c:set var="subtitleTranslated" value="${fn:startsWith(alumneForm.subTitleCode,'=')}" />
<c:if test="${subtitleTranslated}">
   <c:out value="${fn:substringAfter(alumneForm.subTitleCode, '=')}" escapeXml="false"/>
</c:if>
<c:if test="${not subtitleTranslated}">
  <fmt:message key="${alumneForm.subTitleCode}" />
</c:if>
</h6>
  </c:if>
</div>