<%@ page contentType="text/html;charset=UTF-8" language="java"
%><%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>
<c:set var="url" value="${r"${urlActual}"}" />
<div>
  <h5><fmt:message key="menuinici" /></h5>
  <ul class="tree" style="margin: 3px; padding: 0px;">

    <li style="list-style-type: disc; list-style-position: inside;">
      <a href="<c:url value="/common/principal.html"/>">
        <span style="${dollar}{(fn:contains(url, 'principal'))? "font-weight: bold;" : ""}">Pàgina Inicial</span>
      </a>
    </li>

    <hr  style="margin-top: 6px;  margin-bottom: 6px;" />
    <li style="list-style-type: disc; list-style-position: inside;">
      <a href="<c:url value="/common/option1"/>">
        <span style="${dollar}{(fn:contains(url, 'option1'))? "font-weight: bold;" : ""}">Menú Option 1</span>
      </a>
    </li>

   <%-- Example with security: virtual roles  --%>
   <%--
   <sec:authorize access="hasAnyRole('ROLE_SOLI', 'ROLE_DEST', 'ROLE_COLA', 'ROLE_DELE')">
      <hr  style="margin-top: 6px;  margin-bottom: 6px;" />
      <li style="list-style-type: disc; list-style-position: inside;">
       <a href="<c:url value="/common/rebreAvis/list/1"/>" >
       <span style="${dollar}{(fn:contains(url, 'optionxxxxx/') && fn:contains(url, '/list'))? "font-weight: bold;" : ""}" >
       Option XXXXX</span></a></li>
   </sec:authorize>
    --%>

    <hr  style="margin-top: 6px;  margin-bottom: 6px;" />
    <li style="list-style-type: disc; list-style-position: inside;">
      <a href="<c:url value="/common/option2"/>">
        <span style="${dollar}{(fn:contains(url, 'option2'))? "font-weight: bold;" : ""}">Menú Option 2</span>
      </a>
    </li>
   
  </ul>
</div>

