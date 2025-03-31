package ${package}.back.controller.admin;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.fundaciobit.genapp.common.web.html.IconUtils;
import org.fundaciobit.genapp.common.web.menuoptions.MenuOption;
import org.fundaciobit.genapp.common.web.tiles.Tile;
import org.fundaciobit.genapp.common.web.tiles.TileType;
import org.fundaciobit.genapp.common.web.tiles.TileAttribute;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ${package}.commons.utils.Configuracio;
import ${package}.back.utils.Tab;

/**
 * 
 * @author anadal
 *
 */
@Controller
@RequestMapping(value = "/admin")
@MenuOption(
        labelCode = "=Menú ADMIN Option 1",
        order = 10,
        group = Tab.MENU_ADMIN,
        baseLink = "/admin/option1",
        relativeLink = "")
@Tile(name = "option1Admin", extendsTile = Tab.MENU_ADMIN, type = TileType.ANOTHER, contentJsp = "/WEB-INF/jsp/admin/admin.jsp")
@MenuOption(
        labelCode = "=Menú ADMIN Option 2",
        order = 20,
        group = Tab.MENU_ADMIN,
        baseLink = "/admin/option2",
        relativeLink = "",
        addSeparatorBefore = true)
@Tile(name = "option2Admin", extendsTile =  "option1Admin", type = TileType.ANOTHER)
@MenuOption(
        labelCode = "=Contents of ${name}.properties file",
        order = 1000,
        group = Tab.MENU_ADMIN,
        baseLink = "/admin/properties",
        relativeLink = "",
        addSeparatorBefore = true)
@MenuOption(
        labelCode = "=Contents of ${name}.system.properties file",
        order = 1010,
        group = Tab.MENU_ADMIN,
        baseLink = "/admin/systemproperties",
        relativeLink = "")
@MenuOption(
        labelCode = "=Reload contents of property files",
        order = 1020,
        group = Tab.MENU_ADMIN,
        baseLink = "/admin/reloadproperties",
        relativeLink = "")
@Tile(
        name = AdminController.KEYVALUE_ADMIN_TILE,
        extendsTile = Tab.MENU_ADMIN,
        contentJsp = "/WEB-INF/jsp/common/keyvalue.jsp",
        attributes = {
            @TileAttribute(name = "titol", value = "admin.admin")
        },
        type = TileType.ANOTHER)
public class AdminController {

    public static final String KEYVALUE_ADMIN_TILE = "keyvalueAdmin";

    @RequestMapping(value = "/option1")
    public ModelAndView option1(HttpSession session, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        ModelAndView mav = new ModelAndView("option1Admin");
        mav.addObject("optionNumber", "OPCIÓ ADMIN -1-");
        return mav;

    }

    @RequestMapping(value = "/option2")
    public ModelAndView option2(HttpSession session, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        ModelAndView mav = new ModelAndView("option2Admin");
        mav.addObject("optionNumber", "OPCIÓ ADMIN -2-");
        return mav;
    }


    @RequestMapping(value = "/properties")
    public ModelAndView properties(HttpSession session, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        Properties prop = Configuracio.getAppProperties();

        List<KeyValueItem> keyValuelist = new ArrayList<KeyValueItem>();

        for (Object key : prop.keySet()) {
            keyValuelist.add(new KeyValueItem((String) key, prop.getProperty((String) key, ""),
                    "<i class=\"" + IconUtils.ICON_INFO + "\"></i>", ""));
        }

        Collections.sort(keyValuelist);

        ModelAndView mav = new ModelAndView("keyvalueAdmin");
        mav.addObject("title", "Item list of ${name}.app.properties file");
        mav.addObject("subtitle", "");
        mav.addObject("keyValueList", keyValuelist);
        return mav;
    }

    @RequestMapping(value = "/systemproperties")
    public ModelAndView systemproperties(HttpSession session, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        Properties prop = Configuracio.getAppSystemProperties();

        List<KeyValueItem> keyValuelist = new ArrayList<KeyValueItem>();

        for (Object key : prop.keySet()) {
            keyValuelist.add(new KeyValueItem((String) key, "***************"));
        }

        Collections.sort(keyValuelist);

        ModelAndView mav = new ModelAndView("keyvalueAdmin");
        mav.addObject("title", "Item list of ${name}.system.properties file");
        mav.addObject("subtitle", "");
        mav.addObject("keyValueList", keyValuelist);
        return mav;
    }

    @RequestMapping(value = "/reloadproperties")
    public String reloadproperties(HttpSession session, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        Configuracio.reloadProperties();

        return "redirect:/admin/properties";
    }

    public class KeyValueItem implements Comparable<KeyValueItem> {
        private String key;
        private String value;
        private String pre;
        private String post;

        public KeyValueItem(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public KeyValueItem(String key, String value, String pre, String post) {
            this.key = key;
            this.value = value;
            this.pre = pre;
            this.post = post;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getPre() {
            return pre;
        }

        public void setPre(String pre) {
            this.pre = pre;
        }

        public String getPost() {
            return post;
        }

        public void setPost(String post) {
            this.post = post;
        }

        @Override
        public int compareTo(KeyValueItem o2) {
            return this.getKey().compareTo(o2.getKey());
        }
    }


}
