<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>
  
<div class="lead" style="margin-bottom:10px">
 <c:choose>
  <c:when test="${fn:startsWith(ordersForm.titleCode,'=')}">
       <c:out value="${fn:substringAfter(ordersForm.titleCode, '=')}" escapeXml="false"/>
  </c:when>
  <c:when test="${not empty ordersForm.titleCode}">
    <fmt:message key="${ordersForm.titleCode}" >
      <fmt:param value="${ordersForm.titleParam}" />
    </fmt:message>
  </c:when>
  <c:otherwise>
    <c:if test="${empty ordersForm.entityNameCode}">
      <fmt:message var="entityname" key="orders.orders"/>
    </c:if>
    <c:if test="${not empty ordersForm.entityNameCode}">
      <fmt:message var="entityname" key="${ordersForm.entityNameCode}"/>
    </c:if>
    <c:set var="keytitle" value="${ordersForm.nou?'genapp.createtitle':(ordersForm.view?'genapp.viewtitle':'genapp.edittitle')}"/>
    <fmt:message key="${keytitle}">
      <fmt:param value="${entityname}"/>
    </fmt:message>
    </c:otherwise>
 </c:choose>
  
  <c:if test="${not empty ordersForm.subTitleCode}">
  <br/><h5 style="line-height: 10px; margin-top: 0px; margin-bottom: 0px;">
<c:set var="subtitleTranslated" value="${fn:startsWith(ordersForm.subTitleCode,'=')}" />
<c:if test="${subtitleTranslated}">
   <c:out value="${fn:substringAfter(ordersForm.subTitleCode, '=')}" escapeXml="false"/>
</c:if>
<c:if test="${not subtitleTranslated}">
  <fmt:message key="${ordersForm.subTitleCode}" />
</c:if>
</h5>
  </c:if>
</div>