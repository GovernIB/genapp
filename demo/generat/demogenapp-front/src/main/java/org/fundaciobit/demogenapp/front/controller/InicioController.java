package org.fundaciobit.demogenapp.front.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 
 * @author anadal
 *
 */
@Controller
public class InicioController {

    protected final Log log = LogFactory.getLog(getClass());

    @RequestMapping(value = { "/" })
    public ModelAndView inicio(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws Exception {

        ModelAndView mav = new ModelAndView("inici");

        return mav;

    }

    @RequestMapping(value = { "/error" }, method = RequestMethod.GET)
    public ModelAndView error(HttpServletRequest request, HttpServletResponse response, Exception e)
            throws Exception {

        ModelAndView mav = new ModelAndView("error");

        try {

            mav.addObject("error", e.getMessage());

        } catch (Throwable e1) {
            log.error("Error: " + e1.getMessage(), e1);
        }

        return mav;

    }

}
