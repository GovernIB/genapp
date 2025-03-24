<%@ page contentType="text/html;charset=UTF-8" language="java"
%><%@page import="org.fundaciobit.genapp.common.web.menuoptions.MenuOptionManager"
%><%@page import="org.fundaciobit.genapp.common.web.menuoptions.MenuItem"
%><%@page import="org.fundaciobit.demogenapp.commons.utils.Constants"
%><%@page import="java.util.List"
%><%@page import="java.util.ArrayList"
%><%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>
<c:set var="url" value="${urlActual}" />
<div>
  <h5><fmt:message key="menuinici" /></h5>
  
  <%--
  <ul class="tree" style="margin: 3px; padding: 0px;">
  
  <c:if test="${empty loginInfo}">
        <li style="list-style-type: disc; list-style-position: inside;">
          <a href="<c:url value="/public/index.html"/>">
            <span style="${(fn:contains(url, 'principal'))? "font-weight: bold;" : ""}">Pàgina Inicial</span>
          </a>
        </li>
    </c:if>
    <c:if test="${not empty loginInfo}">

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


    <hr  style="margin-top: 6px;  margin-bottom: 6px;" />
    <li style="list-style-type: disc; list-style-position: inside;">
      <a href="<c:url value="/common/option2"/>">
        <span style="${(fn:contains(url, 'option2'))? "font-weight: bold;" : ""}">Menú Option 2</span>
      </a>
    </li>
       </c:if>
    --%>
    
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
    List<MenuItem> discoveredMenus = MenuOptionManager.getMenuItems(Constants.MENU_BACK_PUBLIC_AND_COMMON_ACCESS, menusAddicionals);
    menus.add(discoveredMenus);
    %>

   <%@ include file="/WEB-INF/jsp/moduls/menu_role_generator.jsp"%>
  </ul>
</div>

