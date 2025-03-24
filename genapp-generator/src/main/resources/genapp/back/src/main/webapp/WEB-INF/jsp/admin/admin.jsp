<%@page import="org.springframework.security.core.Authentication"
%><%@page import="org.springframework.security.core.context.SecurityContext"
%><%@page import="org.springframework.security.core.context.SecurityContextHolder"
%><%@ page language="java" 
%><%@ include file="/WEB-INF/jsp/moduls/includes.jsp" 
%>
<div class="clear"></div>
<div class="spacer"></div>

<center><h1>Option Page ${dollar}{optionNumber}</h1></center>

<center><img src="<c:url value="/img/icn_alert_success.png"/>"  alt="exemple" title="exemple"/></center>


Exemple de Controler que "extends" de WebDB:

<table border="1" >
<tr><td>
<textarea cols="120" rows="25" style="font-family: 'Courier New'">
package ${package}.back.controller.admin;

import ${package}.back.controller.webdb.AlumneController;
import ${package}.back.form.webdb.AlumneFilterForm;
import ${package}.back.form.webdb.AlumneForm;

import ${package}.commons.utils.Constants;
import org.fundaciobit.genapp.common.web.menuoptions.MenuOption;
import org.fundaciobit.genapp.common.web.tiles.Tile;
import org.fundaciobit.genapp.common.web.tiles.TileType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@MenuOption(labelCode = "alumne.alumne.plural", order = 25, group = Constants.MENU_BACK_ADMIN_ACCESS)
@Controller
@RequestMapping(value = "/admin/alumne")
@SessionAttributes(types = { AlumneForm.class, AlumneFilterForm.class })
@Tile(name = "alumneListAdmin", type = TileType.WEBDB_LIST, extendsTile = Constants.MENU_BACK_ADMIN_ACCESS)
@Tile(name = "alumneFormAdmin", type = TileType.WEBDB_FORM, extendsTile = Constants.MENU_BACK_ADMIN_ACCESS)
public class AlumneAdminController extends AlumneController {

}
</textarea>
</td></tr>
</table>
