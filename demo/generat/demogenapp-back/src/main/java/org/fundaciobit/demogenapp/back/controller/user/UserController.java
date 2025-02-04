package org.fundaciobit.demogenapp.back.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.fundaciobit.demogenapp.commons.utils.Constants;
import org.fundaciobit.genapp.common.web.menuoptions.MenuOption;
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
        group = Constants.DEM_USER,
        baseLink = "/user/option1",
        relativeLink = "")
@MenuOption(
        labelCode = "=Menú USER Option 2",
        order = 20,
        group = Constants.DEM_USER,
        baseLink = "/user/option2",
        addSeparatorBefore = true,
        relativeLink = "")
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
