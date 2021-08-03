package org.fundaciobit.genappsqltutorial.back.controller.all;

import org.apache.log4j.Logger;
import org.fundaciobit.genapp.common.web.HtmlUtils;
import org.fundaciobit.genappsqltutorial.tutorial.utils.UnitInfo;
import org.fundaciobit.genappsqltutorial.tutorial.utils.UnitsManager;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 
 * @autor anadal
 * 
 */
@Controller
public class PublicController {

	protected final Logger log = Logger.getLogger(getClass());

	@RequestMapping(value = "/public/index.html")
	public ModelAndView principal(HttpSession session, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Boolean initialized = (Boolean) session.getAttribute("inicialitzat");

		if (initialized == null) {
			HtmlUtils.saveMessageInfo(request, "MessageInfo : Benvingut a GenApp SQL Tutorial");
			session.setAttribute("inicialitzat", true);
		}

		return new ModelAndView("homepublic");

	}
	
	
	@RequestMapping("/public/unit/{unitClassName}")
    public ModelAndView arxiu(@PathVariable("unitClassName") String unitClassName, 
        HttpServletRequest request, HttpServletResponse response) throws Exception {
	    
	    
	    
	    
	    UnitInfo unitInfo = UnitsManager.getPartsOfUnit(unitClassName, LocaleContextHolder.getLocale().getLanguage());
	    
	    
	    ModelAndView mav = new ModelAndView("unitinfo");
	    
	    mav.addObject("unitInfo", unitInfo);
	    
	    return mav; 
	    
	}
}
