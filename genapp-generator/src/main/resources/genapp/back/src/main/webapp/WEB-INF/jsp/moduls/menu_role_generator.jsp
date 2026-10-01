<%@page import="java.util.List"
%><%@page import="org.fundaciobit.genapp.common.web.menuoptions.MenuItem"
%><%@page contentType="text/html;charset=UTF-8" language="java"
%><%@ include file="/WEB-INF/jsp/moduls/includes.jsp"
%><%
{
int count = 0;

for (List<MenuItem> menu : menus) {
    pageContext.setAttribute("menu", menu);
%>
<ul class="tree" style="margin: 3px; padding: 0px;">
    <c:forEach var="item" items="${dollar}{menu}">
        <c:if test="${dollar}{empty item }">
            <hr style="margin-top: 6px; margin-bottom: 6px;" />
        </c:if>
        <c:if test="${dollar}{not empty item }">
            <c:if test="${dollar}{fn:substring(item.label, 0, 1) == '='}">
                <c:set var="traduccio" value="${dollar}{fn:substring(item.label, 1, fn:length(item.label))}" />
            </c:if>
            <c:if test="${dollar}{fn:substring(item.label, 0, 1) != '='}">
                <fmt:message var="traduccio" key="${dollar}{item.label}" />
            </c:if>

            <c:set var="theurl" value="${dollar}{item.url}" />
            <c:set var="theurlbase" value="${dollar}{item.urlbase}" />
            <li style="list-style-type: disc; list-style-position: inside;">
            <a href="<c:url value="${dollar}{theurl}"/>">
               <span style="${dollar}{(fn:contains(urlActual, theurlbase))? "font-weight:bold;" : ""} ${dollar}{(fn:endsWith(traduccio, '(*)'))? "color:red;" : ""}">${dollar}{traduccio}</span>
            </a>
            </li>
        </c:if>
    </c:forEach>

</ul>

<%
count++;

} // final FOR
}
%>
