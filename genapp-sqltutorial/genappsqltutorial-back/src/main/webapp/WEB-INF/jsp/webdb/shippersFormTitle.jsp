<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>
  
<div class="lead" style="margin-bottom:10px">
 <c:choose>
  <c:when test="${fn:startsWith(shippersForm.titleCode,'=')}">
       <c:out value="${fn:substringAfter(shippersForm.titleCode, '=')}" escapeXml="false"/>
  </c:when>
  <c:when test="${not empty shippersForm.titleCode}">
    <fmt:message key="${shippersForm.titleCode}" >
      <fmt:param value="${shippersForm.titleParam}" />
    </fmt:message>
  </c:when>
  <c:otherwise>
    <c:if test="${empty shippersForm.entityNameCode}">
      <fmt:message var="entityname" key="shippers.shippers"/>
    </c:if>
    <c:if test="${not empty shippersForm.entityNameCode}">
      <fmt:message var="entityname" key="${shippersForm.entityNameCode}"/>
    </c:if>
    <c:set var="keytitle" value="${shippersForm.nou?'genapp.createtitle':(shippersForm.view?'genapp.viewtitle':'genapp.edittitle')}"/>
    <fmt:message key="${keytitle}">
      <fmt:param value="${entityname}"/>
    </fmt:message>
    </c:otherwise>
 </c:choose>
  
  <c:if test="${not empty shippersForm.subTitleCode}">
  <br/><h5 style="line-height: 10px; margin-top: 0px; margin-bottom: 0px;">
<c:set var="subtitleTranslated" value="${fn:startsWith(shippersForm.subTitleCode,'=')}" />
<c:if test="${subtitleTranslated}">
   <c:out value="${fn:substringAfter(shippersForm.subTitleCode, '=')}" escapeXml="false"/>
</c:if>
<c:if test="${not subtitleTranslated}">
  <fmt:message key="${shippersForm.subTitleCode}" />
</c:if>
</h5>
  </c:if>
</div>