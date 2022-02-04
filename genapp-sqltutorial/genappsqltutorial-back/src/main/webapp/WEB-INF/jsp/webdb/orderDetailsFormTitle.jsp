<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>
  
<div class="lead" style="margin-bottom:10px">
 <c:choose>
  <c:when test="${fn:startsWith(orderDetailsForm.titleCode,'=')}">
       <c:out value="${fn:substringAfter(orderDetailsForm.titleCode, '=')}" escapeXml="false"/>
  </c:when>
  <c:when test="${not empty orderDetailsForm.titleCode}">
    <fmt:message key="${orderDetailsForm.titleCode}" >
      <fmt:param value="${orderDetailsForm.titleParam}" />
    </fmt:message>
  </c:when>
  <c:otherwise>
    <c:if test="${empty orderDetailsForm.entityNameCode}">
      <fmt:message var="entityname" key="orderDetails.orderDetails"/>
    </c:if>
    <c:if test="${not empty orderDetailsForm.entityNameCode}">
      <fmt:message var="entityname" key="${orderDetailsForm.entityNameCode}"/>
    </c:if>
    <c:set var="keytitle" value="${orderDetailsForm.nou?'genapp.createtitle':(orderDetailsForm.view?'genapp.viewtitle':'genapp.edittitle')}"/>
    <fmt:message key="${keytitle}">
      <fmt:param value="${entityname}"/>
    </fmt:message>
    </c:otherwise>
 </c:choose>
  
  <c:if test="${not empty orderDetailsForm.subTitleCode}">
  <br/><h5 style="line-height: 10px; margin-top: 0px; margin-bottom: 0px;">
<c:set var="subtitleTranslated" value="${fn:startsWith(orderDetailsForm.subTitleCode,'=')}" />
<c:if test="${subtitleTranslated}">
   <c:out value="${fn:substringAfter(orderDetailsForm.subTitleCode, '=')}" escapeXml="false"/>
</c:if>
<c:if test="${not subtitleTranslated}">
  <fmt:message key="${orderDetailsForm.subTitleCode}" />
</c:if>
</h5>
  </c:if>
</div>