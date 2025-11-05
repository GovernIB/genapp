package org.fundaciobit.demogenapp.back.controller.all;

import javax.servlet.http.HttpServletRequest;

import org.fundaciobit.demogenapp.back.controller.webdb.AssignaturaController;
import org.fundaciobit.demogenapp.back.form.webdb.AssignaturaFilterForm;
import org.fundaciobit.demogenapp.back.form.webdb.AssignaturaForm;
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

@MenuOption(labelCode = "assignatura.assignatura.plural", order = 50, group = Tab.MENU_PUBLIC_AND_COMMON, addSeparatorBefore = true)
@Controller
@RequestMapping(value = "/public/assignatura")
@SessionAttributes(types = { AssignaturaForm.class, AssignaturaFilterForm.class })
@Tile(
        name = "assignaturaFormPublic",
        contentJsp = "/WEB-INF/jsp/webdb/assignaturaForm.jsp",
        extendsTile = Tab.MENU_PUBLIC_AND_COMMON,
        type = TileType.WEBDB_FORM,
        attributes = { @TileAttribute(name = "titol", value = "assignatura.assignatura") })
@Tile(
        name = "assignaturaListPublic",
        contentJsp = "/WEB-INF/jsp/webdb/assignaturaList.jsp",
        extendsTile = Tab.MENU_PUBLIC_AND_COMMON,
        type = TileType.WEBDB_LIST,
        attributes = { @TileAttribute(name = "titol", value = "assignatura.assignatura") })
public class AlumneAllController extends AssignaturaController {

    /**
     * 
     */
    @Override
    public AssignaturaFilterForm getAssignaturaFilterForm(Integer pagina, ModelAndView mav, HttpServletRequest request)
            throws I18NException {
        AssignaturaFilterForm assignaturaFilterForm = super.getAssignaturaFilterForm(pagina, mav, request);

        HtmlUtils.saveMessageInfo(request, "Exemple de gestió tota encapsulada en un Controller");

        return assignaturaFilterForm;
    }

}
