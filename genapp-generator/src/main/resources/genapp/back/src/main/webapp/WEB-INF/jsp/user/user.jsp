<%@page import="org.springframework.security.core.Authentication"
%><%@page import="org.springframework.security.core.context.SecurityContext"
%><%@page import="org.springframework.security.core.context.SecurityContextHolder"
%><%@page language="java" 
%><%@include file="/WEB-INF/jsp/moduls/includes.jsp" 
%><%@page import="${package}.back.utils.Tab" %>
<div class="clear"></div>
<div class="spacer"></div>

<center><h1>Option Page ${dollar}{optionNumber}</h1></center>

<center><img src="<c:url value="/img/icn_alert_success.png"/>"  alt="exemple" title="exemple"/></center>

Exemple de Controler que "extends" de WebDB:

<table border="1" >
<tr><td>
<textarea cols="120" rows="25" style="font-family: 'Courier New'">
package ${package}.back.controller.user;

import ${package}.back.controller.webdb.AlumneController;
import ${package}.back.form.webdb.AlumneFilterForm;
import ${package}.back.form.webdb.AlumneForm;

import ${package}.back.utils.Tab;
import org.fundaciobit.genapp.common.web.menuoptions.MenuOption;
import org.fundaciobit.genapp.common.web.tiles.Tile;
import org.fundaciobit.genapp.common.web.tiles.TileType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@MenuOption(labelCode = "alumne.alumne.plural", order = 25, group = Tab.MENU_USER)
@Controller
@RequestMapping(value = "/user/alumne")
@SessionAttributes(types = { AlumneForm.class, AlumneFilterForm.class })
@Tile(name = "alumneListUser", type = TileType.WEBDB_LIST, extendsTile = Tab.MENU_USER)
@Tile(name = "alumneFormUser", type = TileType.WEBDB_FORM, extendsTile = Tab.MENU_USER)
public class AlumneUserController extends AlumneController {

}
</textarea>
</td></tr>
</table>

<center>
<img src="<c:url value='/img/atom_spinner.svg'/>" alt="Atom Spinner"/>
</center>