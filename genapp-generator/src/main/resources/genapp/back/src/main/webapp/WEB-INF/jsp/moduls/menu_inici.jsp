<#assign symbol_dollar = "$">
<%@ page contentType="text/html;charset=UTF-8" language="java"
%><%@page import="org.fundaciobit.genapp.common.web.menuoptions.MenuOptionManager"
%><%@page import="org.fundaciobit.genapp.common.web.menuoptions.MenuItem"
%><%@page import="${package}.back.utils.Tab"
%><%@page import="java.util.List"
%><%@page import="java.util.ArrayList"
%><%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>
<c:set var="url" value="${symbol_dollar}{urlActual}" />
<div>
  <span style="font-size: 1.25rem"><fmt:message key="menuinici" /></span>
    <%
    List<List<MenuItem>> menus = new ArrayList<List<MenuItem>>();
    List<MenuItem> discoveredMenus = MenuOptionManager.getMenuItems(request, Tab.MENU_PUBLIC_AND_COMMON);
    menus.add(discoveredMenus);
    %>
    <%@ include file="/WEB-INF/jsp/moduls/menu_role_generator.jsp"%>
</div>

