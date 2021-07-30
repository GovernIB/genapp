<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>
  
<div class="lead" style="margin-bottom:10px">
 <c:choose>
  <c:when test="${fn:startsWith(productsForm.titleCode,'=')}">
       <c:out value="${fn:substringAfter(productsForm.titleCode, '=')}" escapeXml="false"/>
  </c:when>
  <c:when test="${not empty productsForm.titleCode}">
    <fmt:message key="${productsForm.titleCode}" >
      <fmt:param value="${productsForm.titleParam}" />
    </fmt:message>
  </c:when>
  <c:otherwise>
    <c:if test="${empty productsForm.entityNameCode}">
      <fmt:message var="entityname" key="products.products"/>
    </c:if>
    <c:if test="${not empty productsForm.entityNameCode}">
      <fmt:message var="entityname" key="${productsForm.entityNameCode}"/>
    </c:if>
    <c:set var="keytitle" value="${productsForm.nou?'genapp.createtitle':(productsForm.view?'genapp.viewtitle':'genapp.edittitle')}"/>
    <fmt:message key="${keytitle}">
      <fmt:param value="${entityname}"/>
    </fmt:message>
    </c:otherwise>
 </c:choose>
  
  <c:if test="${not empty productsForm.subTitleCode}">
  <br/><h5 style="line-height: 10px; margin-top: 0px; margin-bottom: 0px;">
<c:set var="subtitleTranslated" value="${fn:startsWith(productsForm.subTitleCode,'=')}" />
<c:if test="${subtitleTranslated}">
   <c:out value="${fn:substringAfter(productsForm.subTitleCode, '=')}" escapeXml="false"/>
</c:if>
<c:if test="${not subtitleTranslated}">
  <fmt:message key="${productsForm.subTitleCode}" />
</c:if>
</h5>
  </c:if>
</div>