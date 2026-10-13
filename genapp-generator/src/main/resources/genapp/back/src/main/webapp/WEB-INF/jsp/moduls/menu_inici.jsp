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
    MenuItem[] menusAddicionals;
    Object loginInfo = request.getAttribute("loginInfo");
    
    if (loginInfo == null) {
        menusAddicionals = new MenuItem[] {
                new MenuItem("=Pàgina Inicial Public","/public/index.html", 10),
            
        };
    } else {
        menusAddicionals = new MenuItem[] {
                new MenuItem("=Pàgina Inicial Autenticat","/common/principal.html", 10),
                null,
                new MenuItem("=Menú Inici Option 1","/common/option1", 20),
                new MenuItem("=Menú Inici Option 2","/common/option2", 30),
        };
    }
    List<MenuItem> discoveredMenus = MenuOptionManager.getMenuItems(Tab.MENU_PUBLIC_AND_COMMON, menusAddicionals);
    menus.add(discoveredMenus);
    %>

   <%@ include file="/WEB-INF/jsp/moduls/menu_role_generator.jsp"%>
  </ul>
</div>

