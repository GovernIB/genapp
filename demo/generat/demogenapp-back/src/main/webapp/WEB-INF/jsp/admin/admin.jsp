<%@page import="org.springframework.security.core.Authentication"
%><%@page import="org.springframework.security.core.context.SecurityContext"
%><%@page import="org.springframework.security.core.context.SecurityContextHolder"
%><%@ page language="java" 
%><%@ include file="/WEB-INF/jsp/moduls/includes.jsp" 
%>
<div class="clear"></div>
<div class="spacer"></div>

<div style="text-align:center;"><h2>Option Page ${optionNumber}</h2></div>

<div style="text-align:center;"><img src="<c:url value="/img/icn_alert_success.png"/>"  alt="exemple" title="exemple"/></div>
<span id="thelabel" style="font-size: 1.25rem; font-weight: bold;">Exemple de Controler que "extends" de WebDB:</span>

<table style="border: 1px solid; border-collapse: collapse;">
<tr><td style="border: 1px solid;">
<textarea cols="120" rows="25" style="font-family: 'Courier New'" aria-label="thelabel">
package org.fundaciobit.demogenapp.back.controller.admin;

import org.fundaciobit.demogenapp.back.controller.webdb.AlumneController;
import org.fundaciobit.demogenapp.back.form.webdb.AlumneFilterForm;
import org.fundaciobit.demogenapp.back.form.webdb.AlumneForm;

import org.fundaciobit.demogenapp.back.utils.Tab;
import org.fundaciobit.genapp.common.web.menuoptions.MenuOption;
import org.fundaciobit.genapp.common.web.tiles.Tile;
import org.fundaciobit.genapp.common.web.tiles.TileType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@MenuOption(labelCode = "alumne.alumne.plural", order = 25, group = Tab.MENU_ADMIN)
@Controller
@RequestMapping(value = "/admin/alumne")
@SessionAttributes(types = { AlumneForm.class, AlumneFilterForm.class })
@Tile(name = "alumneListAdmin", type = TileType.WEBDB_LIST, extendsTile = Tab.MENU_ADMIN)
@Tile(name = "alumneFormAdmin", type = TileType.WEBDB_FORM, extendsTile = Tab.MENU_ADMIN)
public class AlumneAdminController extends AlumneController {

}
</textarea>
</td></tr>
</table>

<div style="text-align:center;">
<img src="<c:url value='/img/atom_spinner.svg'/>" alt="Atom Spinner"/>
</div>



