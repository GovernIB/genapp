package org.fundaciobit.demogenapp.back.controller.all;

import org.apache.log4j.Logger;

import org.fundaciobit.genapp.common.web.HtmlUtils;
import org.springframework.context.i18n.LocaleContextHolder;

import org.fundaciobit.demogenapp.commons.utils.Version;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	@Autowired
	protected Version versio;

	@RequestMapping(value = "/public/index.html")
	public ModelAndView principal(HttpSession session, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Boolean initialized = (Boolean) session.getAttribute("inicialitzat");

		if (initialized == null) {
			HtmlUtils.saveMessageInfo(request, "Benvingut a DemoGenApp");
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

}
