package ${package}.back.controller.all;

import org.apache.log4j.Logger;

import org.fundaciobit.genapp.common.web.HtmlUtils;
import org.fundaciobit.genapp.common.web.tiles.Tile;
import org.fundaciobit.genapp.common.web.tiles.TileType;
import org.springframework.context.i18n.LocaleContextHolder;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ${package}.commons.utils.Configuracio;
import ${package}.back.utils.Tab;
import ${package}.commons.utils.Version;

/**
 * 
 * @autor anadal
 * 
 */
// Tile per accessibilitat
@Tile(name = "acessibilitat", extendsTile = Tab.MENU_PUBLIC_AND_COMMON, type = TileType.ANOTHER, contentJsp = "/WEB-INF/jsp/all/acessibilitat.jsp")
@Controller
public class PublicController {

	protected final Logger log = Logger.getLogger(getClass());
	
	@Autowired
	protected Version versio;

	@RequestMapping(value = "/public/index.html")
	public ModelAndView principal(HttpSession session, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Boolean initialized = (Boolean) session.getAttribute("inicialitzat");

		if (initialized == null) {
			HtmlUtils.saveMessageInfo(request, "Benvingut a ${fullname}");
			session.setAttribute("inicialitzat", true);
		}

		return new ModelAndView("homepublic");

	}
	
	@RequestMapping(value = "/public/versio")
    public void versio(HttpServletResponse response) throws Exception {
        
        response.getWriter().write(versio.getVersion() + "|" + versio.getBuildTime());
        response.getWriter().flush();
        response.getWriter().close();

    }
    
    
    @RequestMapping(value = "/public/avislegal")
    public ModelAndView avislegal(HttpSession session, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        String lang = LocaleContextHolder.getLocale().getLanguage();
        
        if ("es".equals(lang)) {
            return new ModelAndView("avislegal_es");
        } else {
            return new ModelAndView("avislegal_ca");
        }
    }
    
    
    @RequestMapping(value = "/public/accessibilitat")
    public ModelAndView accessibilitat(HttpSession session, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        ModelAndView mav = new ModelAndView("acessibilitat");
        
        mav.addObject("backurl", Configuracio.getBackUrl());
        
        return mav;
        
    }

}
