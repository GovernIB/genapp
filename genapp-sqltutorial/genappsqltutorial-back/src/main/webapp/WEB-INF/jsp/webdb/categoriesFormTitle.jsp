<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>
  
<div class="lead" style="margin-bottom:10px">
<label style="font-size: 1.25rem;font-weight: bold;">
 <c:choose>
  <c:when test="${fn:startsWith(categoriesForm.titleCode,'=')}">
       <c:out value="${fn:substringAfter(categoriesForm.titleCode, '=')}" escapeXml="false"/>
  </c:when>
  <c:when test="${not empty categoriesForm.titleCode}">
    <fmt:message key="${categoriesForm.titleCode}" >
      <fmt:param value="${categoriesForm.titleParam}" />
    </fmt:message>
  </c:when>
  <c:otherwise>
    <c:if test="${empty categoriesForm.entityNameCode}">
      <fmt:message var="entityname" key="categories.categories"/>
    </c:if>
    <c:if test="${not empty categoriesForm.entityNameCode}">
      <fmt:message var="entityname" key="${categoriesForm.entityNameCode}"/>
    </c:if>
    <c:set var="keytitle" value="${categoriesForm.nou?'genapp.createtitle':(categoriesForm.view?'genapp.viewtitle':'genapp.edittitle')}"/>
    <fmt:message key="${keytitle}">
      <fmt:param value="${entityname}"/>
    </fmt:message>
    </c:otherwise>
 </c:choose></label>
  <c:if test="${not empty categoriesForm.subTitleCode}">
<h6 style="line-height: 10px; margin-top: 0px; margin-bottom: 0px;font-style:italic;">
<c:set var="subtitleTranslated" value="${fn:startsWith(categoriesForm.subTitleCode,'=')}" />
<c:if test="${subtitleTranslated}">
   <c:out value="${fn:substringAfter(categoriesForm.subTitleCode, '=')}" escapeXml="false"/>
</c:if>
<c:if test="${not subtitleTranslated}">
  <fmt:message key="${categoriesForm.subTitleCode}" />
</c:if>
</h6>
  </c:if>
</div>