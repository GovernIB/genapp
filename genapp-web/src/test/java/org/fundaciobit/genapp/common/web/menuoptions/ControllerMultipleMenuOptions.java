package org.fundaciobit.genapp.common.web.menuoptions;

/**
 * 
 * @author anadal
 * 3 feb 2025 14:35:38
 */
@MenuOption(
        labelCode = "Menu Option 1",
        order = 10,
        group = MenuOptionsTest.MENUOPTION_GROUP_TEST,
        baseLink = "/public",
        relativeLink = "/optionmultiple1")
@MenuOption(
        labelCode = "Menu Option 2",
        order = 20,
        group = MenuOptionsTest.MENUOPTION_GROUP_TEST,
        baseLink = "/public",
        relativeLink = "/optionmultiple2",
        addSeparatorAfter = true)
public class ControllerMultipleMenuOptions {

}
