package ${package}.back.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ${package}.back.utils.Tab;

import org.apache.tiles.Attribute;
import org.apache.tiles.Definition;
import org.fundaciobit.genapp.common.web.menuoptions.MenuOption;
import org.fundaciobit.genapp.common.web.tiles.Tile;
import org.fundaciobit.genapp.common.web.tiles.TileAttribute;
import org.fundaciobit.genapp.common.web.tiles.TileType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author anadal
 *
 */
@Controller
@RequestMapping(value = "/user/")
@MenuOption(
        labelCode = "=Menú USER Option 1",
        order = 10,
        group = Tab.MENU_USER,
        baseLink = "/user/option1",
        relativeLink = "")
@MenuOption(
        labelCode = "=Menú USER Option 2",
        order = 20,
        group = Tab.MENU_USER,
        baseLink = "/user/option2",
        addSeparatorBefore = true,
        relativeLink = "")
@Tile(name = "option1User",  extendsTile = Tab.MENU_USER, type = TileType.ANOTHER, contentJsp = "/WEB-INF/jsp/user/user.jsp")
@Tile(name = "option2User",  extendsTile =  "option1User", type = TileType.ANOTHER)
public class UserController {

    @RequestMapping(value = "/option1")
    public ModelAndView option1(HttpSession session, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        ModelAndView mav = new ModelAndView("option1User");
        mav.addObject("optionNumber", "OPCIÓ USER -1-");
        return mav;
    }

    @RequestMapping(value = "/option2")
    public ModelAndView option2(HttpSession session, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        ModelAndView mav = new ModelAndView("option2User");
        mav.addObject("optionNumber", "OPCIÓ USER -2-");
        return mav;
    }

}
