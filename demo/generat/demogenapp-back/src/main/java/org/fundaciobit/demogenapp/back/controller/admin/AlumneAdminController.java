package org.fundaciobit.demogenapp.back.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.fundaciobit.demogenapp.back.controller.webdb.AlumneController;
import org.fundaciobit.demogenapp.back.form.webdb.AlumneFilterForm;
import org.fundaciobit.demogenapp.back.form.webdb.AlumneForm;
import org.fundaciobit.demogenapp.back.utils.Tab;
import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genapp.common.web.HtmlUtils;
import org.fundaciobit.genapp.common.web.menuoptions.MenuOption;
import org.fundaciobit.genapp.common.web.tiles.Tile;
import org.fundaciobit.genapp.common.web.tiles.TileAttribute;
import org.fundaciobit.genapp.common.web.tiles.TileType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author anadal
 * 5 nov 2025 10:57:58
 */
@MenuOption(labelCode = "alumne.alumne.plural", order = 20, group = Tab.MENU_ADMIN, addSeparatorBefore = true)
@Controller
@RequestMapping(value = "/admin/alumne")
@SessionAttributes(types = { AlumneForm.class, AlumneFilterForm.class })
@Tile(
        name = "alumneFormAdmin",
        contentJsp = "/WEB-INF/jsp/webdb/alumneForm.jsp",
        extendsTile = Tab.MENU_ADMIN,
        type = TileType.WEBDB_FORM,
        attributes = { @TileAttribute(name = "titol", value = "alumne.alumne") })
@Tile(
        name = "alumneListAdmin",
        contentJsp = "/WEB-INF/jsp/webdb/alumneList.jsp",
        extendsTile = Tab.MENU_ADMIN,
        type = TileType.WEBDB_LIST,
        attributes = { @TileAttribute(name = "titol", value = "alumne.alumne") })
public class AlumneAdminController extends AlumneController {

    /**
     * 
     */
    @Override
    public AlumneFilterForm getAlumneFilterForm(Integer pagina, ModelAndView mav, HttpServletRequest request)
            throws I18NException {
        AlumneFilterForm alumneFilterForm = super.getAlumneFilterForm(pagina, mav, request);

        HtmlUtils.saveMessageInfo(request, "Exemple de gestió tota encapsulada en un Controller");

        return alumneFilterForm;
    }

}
