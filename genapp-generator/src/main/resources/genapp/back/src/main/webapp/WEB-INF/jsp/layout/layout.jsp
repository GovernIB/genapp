<%@ page contentType="text/html;charset=UTF-8" language="java"
%><%@include file="/WEB-INF/jsp/moduls/includes.jsp"%>
<un:useConstants var="Tab" className="${package}.back.utils.Tab"/>
<!DOCTYPE html><html xmlns="http://www.w3.org/1999/xhtml"
    xml:lang="<c:out value="${dollar}{pageContext.response.locale.language}"/>"
    lang="<c:out value="${dollar}{pageContext.response.locale.language}"/>">
<head>
<%@ include file="/WEB-INF/jsp/moduls/imports.jsp"%>
</head>
<body>

    <!--  INICI CAPÇALERA -->

    <tiles:insertAttribute name="cap">
        <tiles:putAttribute name="data" value="${dollar}{data}" />
    </tiles:insertAttribute>


    <!--  PIPELLES -->
    <div class="row-fluid container main" style="max-width: none;">

        <ul class="nav nav-tabs custom-submenu">
            <%
            //session.setAttribute("pipella", )
            %>

            <li class="nav-item"><a
                class="nav-link ${dollar}{(empty pipella)?'active' : '' }"
                href="<c:url value="/canviarPipella/"/>"><fmt:message
                        key="inici" /></a></li>

            <%--  DRAW MENU OPTIONS  XYZ ZZZ
	    <c:forEach var="rolG" items="${dollar}{loginInfo.roles}">
	    <c:set var="rol" value="${dollar}{rolG.authority}"/>
	    <c:if test="${dollar}{not(rol eq 'ROLE_USER')}">
	    <li ${dollar}{(pipella eq rol)?'class="active"' : '' }>
	       <a href="<c:url value="/canviarPipella/${dollar}{rol}"/>"><fmt:message key="${dollar}{rol}" />
	       <c:if test="${dollar}{not(empty avisos[rol])}">
	         &nbsp; <span class="badge badge-warning">${dollar}{avisos[rol]}</span>
	       </c:if>
	       </a>
	    </li>
	    </c:if>  
	    </c:forEach>
	    --%>

            <sec:authorize access="hasRole(<#list basic_virtual_roles_map as role_key,role>'${role?upper_case}',</#list>)">
                <li class="nav-item"><a
                    class="nav-link ${dollar}{(pipella eq Tab.MENU_USER)?'active' : '' }"
                    href="<c:url value="/canviarPipella/${dollar}{Tab.MENU_USER}"/>">BASIC ACCESS</a>
                </li>
            </sec:authorize>

            <sec:authorize access="hasRole(<#list admin_virtual_roles_map as role_key,role>'${role?upper_case}',</#list>)">
                <li class="nav-item"><a
                    class="nav-link ${dollar}{(pipella eq Tab.MENU_ADMIN)?'active' : '' }"
                    href="<c:url value="/canviarPipella/${dollar}{Tab.MENU_ADMIN}"/>">ADMIN ACCESS</a>
                </li>
            </sec:authorize>

            <sec:authorize access="hasRole(<#list admin_virtual_roles_map as role_key,role>'${role?upper_case}',</#list>)">
                <li class="nav-item"><a
                    class="nav-link ${dollar}{(pipella eq Tab.MENU_WEBDB)?'active' : '' }"
                    href="<c:url value="/canviarPipella/${dollar}{Tab.MENU_WEBDB}"/>">WebDatabase</a>
                </li>
            </sec:authorize>

            <c:if test="${dollar}{prefixLowercase}:isDesenvolupament()}">
                <li class="nav-item"><a
                    class="nav-link ${dollar}{(pipella eq 'desenvolupament')?'active' : '' }"
                    href="<c:url value="/canviarPipella/desenvolupament"/>">
                        <fmt:message key="desenvolupament" />
                </a></li>
            </c:if>

        </ul>

        <%-- INICI MENU + CONTINGUT --%>
        <div class="well well-white" style="padding: 10px">
            <tiles:insertAttribute name="menu_i_contingut">
                <tiles:putAttribute name="menu"
                    value="${dollar}{menu_tile}" />
                <tiles:putAttribute name="contingut"
                    value="${dollar}{contingut_tile}" />
            </tiles:insertAttribute>
            <%-- FINAL MENU + CONTINGUT --%>
        </div>

        <%-- FINAL DIV PIPELLES --%>
    </div>

    <div class="container-fluid">
        <tiles:insertAttribute name="peu">
        </tiles:insertAttribute>
    </div>

</body>
</html>
