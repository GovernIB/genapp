package ${package}.back.controller.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.fundaciobit.genapp.common.web.menuoptions.MenuOption;
import org.fundaciobit.genapp.common.web.tiles.Tile;
import org.fundaciobit.genapp.common.web.tiles.TileType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ${package}.back.utils.Tab;

/**
 * 
 * @author anadal
 *
 */
@Controller
@RequestMapping(value = "/common/")
@Tile(name = "option1Common",  extendsTile = Tab.MENU_PUBLIC_AND_COMMON, type = TileType.ANOTHER, contentJsp = "/WEB-INF/jsp/common/option.jsp")
@Tile(name = "option2Common",  extendsTile =  "option1Common", type = TileType.ANOTHER)
public class CommonController {
  
  @RequestMapping(value = "/option1")
  public ModelAndView option1(HttpSession session,
      HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    
    ModelAndView mav = new ModelAndView("option1Common");
    mav.addObject("optionNumber", "OPCIÓ -1-");
    return mav;
    
  }

  @RequestMapping(value = "/option2")
  public ModelAndView option2(HttpSession session,
      HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    
    ModelAndView mav = new ModelAndView("option2Common");
    mav.addObject("optionNumber", "OPCIÓ -2-");
    return mav;
  }
  
  
}
