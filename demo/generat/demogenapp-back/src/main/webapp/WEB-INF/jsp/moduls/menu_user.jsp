<%@ page contentType="text/html;charset=UTF-8" language="java"
%><%@page import="org.fundaciobit.genapp.common.web.menuoptions.MenuOptionManager"
%><%@page import="org.fundaciobit.genapp.common.web.menuoptions.MenuItem"
%><%@page import="org.fundaciobit.demogenapp.back.utils.Tab"
%><%@page import="java.util.List"
%><%@page import="java.util.ArrayList"
%><%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>
<c:set var="url" value="${urlActual}" />
<div>
    <span style="font-size: 1.25rem">Menú BASIC ACCESS</span>

    <%
    List<List<MenuItem>> menus = new ArrayList<List<MenuItem>>();
    /*
    MenuItem menuGoogle = new MenuItem("=MENU Google", "", "http://www.google.com", 0);
    MenuItem menumeneame = new MenuItem("=MENU Meneame", "", "http://www.meneame.net", 1000);
    */
    List<MenuItem> discoveredMenus = MenuOptionManager.getMenuItems(Tab.MENU_USER); //, menuGoogle, menumeneame);
    menus.add(discoveredMenus);
    %>
    
    <%@ include file="/WEB-INF/jsp/moduls/menu_role_generator.jsp"%>  

</div>

