<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>
  
<div class="lead" style="margin-bottom:10px">
<label style="font-size: 1.25rem;font-weight: bold;">
 <c:choose>
  <c:when test="${fn:startsWith(assignaturaAlumneForm.titleCode,'=')}">
       <c:out value="${fn:substringAfter(assignaturaAlumneForm.titleCode, '=')}" escapeXml="false"/>
  </c:when>
  <c:when test="${not empty assignaturaAlumneForm.titleCode}">
    <fmt:message key="${assignaturaAlumneForm.titleCode}" >
      <fmt:param value="${assignaturaAlumneForm.titleParam}" />
    </fmt:message>
  </c:when>
  <c:otherwise>
    <c:if test="${empty assignaturaAlumneForm.entityNameCode}">
      <fmt:message var="entityname" key="assignaturaAlumne.assignaturaAlumne"/>
    </c:if>
    <c:if test="${not empty assignaturaAlumneForm.entityNameCode}">
      <fmt:message var="entityname" key="${assignaturaAlumneForm.entityNameCode}"/>
    </c:if>
    <c:set var="keytitle" value="${assignaturaAlumneForm.nou?'genapp.createtitle':(assignaturaAlumneForm.view?'genapp.viewtitle':'genapp.edittitle')}"/>
    <fmt:message key="${keytitle}">
      <fmt:param value="${entityname}"/>
    </fmt:message>
    </c:otherwise>
 </c:choose></label>
  <c:if test="${not empty assignaturaAlumneForm.subTitleCode}">
<h6 style="line-height: 10px; margin-top: 0px; margin-bottom: 0px;font-style:italic;">
<c:set var="subtitleTranslated" value="${fn:startsWith(assignaturaAlumneForm.subTitleCode,'=')}" />
<c:if test="${subtitleTranslated}">
   <c:out value="${fn:substringAfter(assignaturaAlumneForm.subTitleCode, '=')}" escapeXml="false"/>
</c:if>
<c:if test="${not subtitleTranslated}">
  <fmt:message key="${assignaturaAlumneForm.subTitleCode}" />
</c:if>
</h6>
  </c:if>
</div>