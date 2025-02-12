package org.fundaciobit.genapp.common.web.menuoptions;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.fundaciobit.genapp.common.web.controller.CommonBaseController;

/**
 * 
 * @author anadal
 * 8 ene 2025 10:34:25
 */
public class MenuOptionManager {

    private static final MenuItemOptionComparator MENUITEM_COMPARATOR = new MenuItemOptionComparator();

    private static final Logger log = Logger.getLogger(MenuOptionManager.class);

    private static DiscoverMenuOptionAnnotations discoverMenuOptionAnnotations = null;

    public static void setDiscoverMenuOptionAnnotations(DiscoverMenuOptionAnnotations discoverMenuOptionAnnotations) {
        MenuOptionManager.discoverMenuOptionAnnotations = discoverMenuOptionAnnotations;
    }

    /**
     * 
     * @param group
     * @return
     * @throws Exception
     */
    public static List<MenuItem> getMenuItems(String group, MenuItem... additionalMenuItems) throws Exception {

        if (discoverMenuOptionAnnotations == null) {
            throw new Exception("MenuOptionManager no ha sigut inicialitzat.\n"
                    + "Has d'afegir el següetn codi a InitServlet:\n" + "        // Inicialitzar sistema de menus\n"
                    + "        new Thread(\n" + "        new Runnable() {\n" + "            @Override\n"
                    + "            public void run() {\n" + "                try {\r\n"
                    + "                    MenuOptionManager.setDiscoverMenuOptionAnnotations(\n"
                    + "                            "
                    + "new DiscoverMenuOptionAnnotations(Constants.DEMOGENAPP_PROPERTY_BASE + \"back.controller\"));\n"
                    + "                } catch (Throwable th) {\n"
                    + "                    log.error(\"Error inicialitzant sistema de menus: \"+th.getMessage(),th);\n"
                    + "                }\n" + "            }\n" + "        }).start();\n");

        }

        TreeMap<MenuItemOption, Class<?>> menuOptions = discoverMenuOptionAnnotations.getMenuOptionByGroup(group);

        if (menuOptions == null) {
            menuOptions = new TreeMap<MenuItemOption, Class<?>>(MENUITEM_COMPARATOR);
        } else {
            menuOptions = new TreeMap<MenuItemOption, Class<?>>(MENUITEM_COMPARATOR);
            menuOptions.putAll(menuOptions);
        }

        if (additionalMenuItems != null && additionalMenuItems.length != 0) {
            MenuItemOption lastOption = null;
            for (MenuItem menuItem : additionalMenuItems) {
                if (menuItem == null) {
                    if (lastOption != null) {
                        lastOption.setAddSeparatorAfter(true);
                    }
                } else {
                    MenuItemOption mio = new MenuItemOption(menuItem.getLabel(), menuItem.getUrl(),
                            menuItem.getUrlbase(), menuItem.getOrder());
                    mio.setGroup(group);
                    menuOptions.put(mio, null);
                    lastOption = mio;
                }
            }
        }

        // Convertir menuoption a menuitems
        List<MenuItem> items = new ArrayList<MenuItem>();
        for (MenuItemOption menuOption : menuOptions.keySet()) {
            String baseLink;
            if (menuOption.getUrlbase() == null) {
                baseLink = "";
            } else if (menuOption.getUrlbase().isEmpty()) {

                // Cercar el request mapping de la classe

                Class<?> classe = menuOptions.get(menuOption);

                /*
                log.info(" ========  "+ menuOption.getLabel() + " ============ ");
                log.info("url: "+ menuOption.getUrl());
                log.info("classe: "+classe);
                */

                if (CommonBaseController.class.isAssignableFrom(classe)) {
                    CommonBaseController<?, ?> cbc = (CommonBaseController<?, ?>) classe.getDeclaredConstructor()
                            .newInstance();
                    baseLink = cbc.getContextWeb();
                } else {
                    throw new Exception("La classe " + classe.getName()
                            + " té definida l'anotació MenuOption, però aquesta requereix que la classe estengui"
                            + " de CommonBaseController "
                            + "o afegir l'atribut baseLink amb valor null a l'anotació @MenuOption.");
                }

            } else {
                baseLink = menuOption.getUrlbase();
            }

            if (menuOption.isAddSeparatorBefore()) {
                items.add(null);
            }

            items.add(new MenuItem(menuOption.getLabel(), baseLink + menuOption.getUrl(), baseLink,
                    menuOption.getOrder()));

            if (menuOption.isAddSeparatorAfter()) {
                items.add(null);
            }
        }

        return items;

    }

}
