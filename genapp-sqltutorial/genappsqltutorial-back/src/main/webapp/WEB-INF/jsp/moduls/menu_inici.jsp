<%@page import="org.springframework.context.i18n.LocaleContextHolder"%>
<%@page import="java.util.Collection"%>
<%@page import="org.fundaciobit.genappsqltutorial.tutorial.utils.UnitInfo"%>
<%@page import="java.util.List"%>
<%@page import="org.fundaciobit.genappsqltutorial.tutorial.utils.UnitsManager"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"
%><%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>
<c:set var="url" value="${urlActual}" />
<div>
  <h5><fmt:message key="menuinici" /></h5>
  <ul class="tree" style="margin: 3px; padding: 0px;">
  
  <c:if test="${empty loginInfo}">
        <li style="list-style-type: disc; list-style-position: inside;">
          <a href="<c:url value="/public/index.html"/>">
            <span style="${(fn:contains(url, 'principal'))? "font-weight: bold;" : ""}">P&agrave;gina Inicial</span>
          </a>
        </li>
        
        <hr  style="margin-top: 6px;  margin-bottom: 6px;" />
        
        <%
            
        Collection<UnitInfo> unitats = UnitsManager.getAllUnits("en"); // LocaleContextHolder.getLocale().getLanguage());
        
        for(UnitInfo unitat: unitats) {
            session.setAttribute("classenom", unitat.getClasseNom() );
        %>
       <li style="list-style-type: disc; list-style-position: inside;">
        <a href="<c:url value="/public/unit/${classenom}"/>">
        
          <span style="${(fn:contains(url, classenom))? "font-weight: bold;" : ""}"><%=unitat.getIndex() %> - <%=unitat.getNomCurt() %> </span>
        </a>
      </li>
        
        <% } %>
        
        
        
        
        
        
    </c:if>
    <c:if test="${not empty loginInfo}">
    
    <hr  style="margin-top: 6px;  margin-bottom: 6px;" />

    <li style="list-style-type: disc; list-style-position: inside;">
      <a href="<c:url value="/common/principal.html"/>">
        <span style="${(fn:contains(url, 'principal'))? "font-weight: bold;" : ""}">Pàgina Inicial</span>
      </a>
    </li>

    <hr  style="margin-top: 6px;  margin-bottom: 6px;" />
    <li style="list-style-type: disc; list-style-position: inside;">
      <a href="<c:url value="/common/option1"/>">
        <span style="${(fn:contains(url, 'option1'))? "font-weight: bold;" : ""}">Menú Option 1</span>
      </a>
    </li>

   <%-- Example with security: virtual roles  --%>
   <%--
   <sec:authorize access="hasAnyRole('ROLE_SOLI', 'ROLE_DEST', 'ROLE_COLA', 'ROLE_DELE')">
      <hr  style="margin-top: 6px;  margin-bottom: 6px;" />
      <li style="list-style-type: disc; list-style-position: inside;">
       <a href="<c:url value="/common/rebreAvis/list/1"/>" >
       <span style="${(fn:contains(url, 'optionxxxxx/') && fn:contains(url, '/list'))? "font-weight: bold;" : ""}" >
       Option XXXXX</span></a></li>
   </sec:authorize>
    --%>

    <hr  style="margin-top: 6px;  margin-bottom: 6px;" />
    <li style="list-style-type: disc; list-style-position: inside;">
      <a href="<c:url value="/common/option2"/>">
        <span style="${(fn:contains(url, 'option2'))? "font-weight: bold;" : ""}">Menú Option 2</span>
      </a>
    </li>
   
   </c:if>
  </ul>
</div>
