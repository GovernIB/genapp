package org.fundaciobit.genapp.common.web.menuoptions;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.fundaciobit.genapp.common.web.controller.CommonBaseController;
import org.springframework.web.context.request.RequestAttributes;

/**
 * 
 * @author anadal
 * 8 ene 2025 10:34:25
 */
public class MenuOptionManager {

    private static final MenuItemOptionComparator MENUITEM_COMPARATOR = new MenuItemOptionComparator();

    protected static final Logger log = Logger.getLogger(MenuOptionManager.class);

    private static DiscoverMenuOptionAnnotations discoverMenuOptionAnnotations = null;

    public static void setDiscoverMenuOptionAnnotations(DiscoverMenuOptionAnnotations discoverMenuOptionAnnotations) {
        MenuOptionManager.discoverMenuOptionAnnotations = discoverMenuOptionAnnotations;
    }

    /**
     * 
     * @param group
     * @return
     * @throws Exception
     * @deprecated additionalMenuItem s'ha de substituir per "condition" dins de @MenuOption i requereix paràmetres request.
     */
    @Deprecated
    public static List<MenuItem> getMenuItems(String group, MenuItem... additionalMenuItems) throws Exception {
        // Obtenir HttpServletRequest de ContextHolder de Spring
        RequestAttributes ra = org.springframework.web.context.request.RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request;
        if (ra  instanceof org.springframework.web.context.request.ServletRequestAttributes) {
            request = ((org.springframework.web.context.request.ServletRequestAttributes) ra).getRequest();
        } else {
            request =  null;
            log.warn("No s'ha pogut obtenir HttpServletRequest de RequestContextHolder."
                    + " Algunes condicions de visibilitat de menú no funcionaran correctament.");
        }
        
        return getMenuItems(request, group, additionalMenuItems);
    }

    /**
     * 
     * @param group
     * @return
     * @throws Exception
     * @deprecated  additionalMenuItem s'ha de substituir per "condition" dins de @MenuOption
     */
    @Deprecated
    public static List<MenuItem> getMenuItems(HttpServletRequest request, String group, MenuItem... additionalMenuItems)
            throws Exception {
        return internalGetMenuItems(request, group, additionalMenuItems);
    }

    public static List<MenuItem> getMenuItems(HttpServletRequest request, String group) throws Exception {
        return getMenuItems(request, group, (MenuItem[]) null);
    }

    /**
     * 
     * @param group
     * @return
     * @throws Exception
     */
    private static List<MenuItem> internalGetMenuItems(HttpServletRequest request, String group,
            MenuItem... additionalMenuItems) throws Exception {

        if (discoverMenuOptionAnnotations == null) {
            throw new Exception("MenuOptionManager no ha sigut inicialitzat.\n"
                    + "Has d'afegir el següent codi a InitServlet:\n" + "        // Inicialitzar sistema de menus\n"
                    + "        new Thread(\n" + "        new Runnable() {\n" + "            @Override\n"
                    + "            public void run() {\n" + "                try {\r\n"
                    + "                    MenuOptionManager.setDiscoverMenuOptionAnnotations(\n"
                    + "                            "
                    + "new DiscoverMenuOptionAnnotations(Constants.[PREFIX_APP]_PROPERTY_BASE + \"back.controller\"));\n"
                    + "                } catch (Throwable th) {\n"
                    + "                    log.error(\"Error inicialitzant sistema de menus: \"+th.getMessage(),th);\n"
                    + "                }\n" + "            }\n" + "        }).start();\n");

        }

        TreeMap<MenuItemOption, Class<?>> menuOptions = discoverMenuOptionAnnotations.getMenuOptionByGroup(group);

        if (menuOptions == null) {
            menuOptions = new TreeMap<MenuItemOption, Class<?>>(MENUITEM_COMPARATOR);
        } else {
            TreeMap<MenuItemOption, Class<?>> menuOptions2 = new TreeMap<MenuItemOption, Class<?>>(MENUITEM_COMPARATOR);
            menuOptions2.putAll(menuOptions);
            menuOptions = menuOptions2;
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

            // Si la condició no es compleix, l'opció no es mostra
            if (isVisible(request, menuOption) == false) {
                continue;
            }

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
                    if (baseLink != null && !baseLink.endsWith("/")) {
                        baseLink = baseLink + "/";
                    }
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

            items.add(new MenuItem(menuOption.getLabel(), (baseLink + menuOption.getUrl()).replaceAll("//", "/"),
                    baseLink, menuOption.getOrder()));

            if (menuOption.isAddSeparatorAfter()) {
                items.add(null);
            }
        }

        return items;

    }

    /**
     * Retorna els MenuItem agrupats per la classe Controller que els declara.
     * 
     * @param group
     * @param additionalMenuItems MenuItems addicionals que no provenen de cap classe anotada
     *                             (s'afegiran agrupats sota la clau {@code null})
     * @return Un Map on la clau és la classe Controller i el valor la llista de MenuItem
     *         que aquesta classe declara (ordenats igual que a {@link #getMenuItems(String, MenuItem...)}).
     * @throws Exception
     */
    @Deprecated
    public static Map<Class<?>, List<MenuItem>> getMenuItemsByClass(String group, MenuItem... additionalMenuItems)
            throws Exception {

        return getMenuItemsByClass(null, group, additionalMenuItems);

    }

    /**
     * Retorna els MenuItem agrupats per la classe Controller que els declara.
     * 
     * @param group
     * @param additionalMenuItems MenuItems addicionals que no provenen de cap classe anotada
     *                             (s'afegiran agrupats sota la clau {@code null})
     * @return Un Map on la clau és la classe Controller i el valor la llista de MenuItem
     *         que aquesta classe declara (ordenats igual que a {@link #getMenuItems(String, MenuItem...)}).
     * @throws Exception
     */
    public static Map<Class<?>, List<MenuItem>> getMenuItemsByClass(HttpServletRequest request, String group,
            MenuItem... additionalMenuItems) throws Exception {

        if (discoverMenuOptionAnnotations == null) {
            throw new Exception("MenuOptionManager no ha sigut inicialitzat.\n"
                    + "Has d'afegir el següent codi a InitServlet:\n" + "        // Inicialitzar sistema de menus\n"
                    + "        new Thread(\n" + "        new Runnable() {\n" + "            @Override\n"
                    + "            public void run() {\n" + "                try {\r\n"
                    + "                    MenuOptionManager.setDiscoverMenuOptionAnnotations(\n"
                    + "                            "
                    + "new DiscoverMenuOptionAnnotations(Constants.[PREFIX_APP]_PROPERTY_BASE + \"back.controller\"));\n"
                    + "                } catch (Throwable th) {\n"
                    + "                    log.error(\"Error inicialitzant sistema de menus: \"+th.getMessage(),th);\n"
                    + "                }\n" + "            }\n" + "        }).start();\n");

        }

        TreeMap<MenuItemOption, Class<?>> menuOptions = discoverMenuOptionAnnotations.getMenuOptionByGroup(group);

        if (menuOptions == null) {
            menuOptions = new TreeMap<MenuItemOption, Class<?>>(MENUITEM_COMPARATOR);
        } else {
            TreeMap<MenuItemOption, Class<?>> menuOptions2 = new TreeMap<MenuItemOption, Class<?>>(MENUITEM_COMPARATOR);
            menuOptions2.putAll(menuOptions);
            menuOptions = menuOptions2;
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
                    // Els additionalMenuItems no provenen de cap classe, per tant s'agrupen amb clau null
                    menuOptions.put(mio, null);
                    lastOption = mio;
                }
            }
        }

        // Convertir menuoption a menuitems, agrupats per classe
        Map<Class<?>, List<MenuItem>> itemsByClass = new LinkedHashMap<Class<?>, List<MenuItem>>();

        for (MenuItemOption menuOption : menuOptions.keySet()) {

            // Si la condició no es compleix, l'opció no es mostra
            if (!isVisible(request, menuOption)) {
                continue;
            }

            String baseLink;
            Class<?> classe = menuOptions.get(menuOption);

            if (menuOption.getUrlbase() == null) {
                baseLink = "";
            } else if (menuOption.getUrlbase().isEmpty()) {

                // Cercar el request mapping de la classe

                if (classe != null && CommonBaseController.class.isAssignableFrom(classe)) {
                    CommonBaseController<?, ?> cbc = (CommonBaseController<?, ?>) classe.getDeclaredConstructor()
                            .newInstance();
                    baseLink = cbc.getContextWeb();
                    if (baseLink != null && !baseLink.endsWith("/")) {
                        baseLink = baseLink + "/";
                    }
                } else if (classe != null) {
                    throw new Exception("La classe " + classe.getName()
                            + " té definida l'anotació MenuOption, però aquesta requereix que la classe estengui"
                            + " de CommonBaseController "
                            + "o afegir l'atribut baseLink amb valor null a l'anotació @MenuOption.");
                } else {
                    baseLink = "";
                }

            } else {
                baseLink = menuOption.getUrlbase();
            }

            List<MenuItem> items = itemsByClass.get(classe);
            if (items == null) {
                items = new ArrayList<MenuItem>();
                itemsByClass.put(classe, items);
            }

            if (menuOption.isAddSeparatorBefore()) {
                items.add(null);
            }

            items.add(new MenuItem(menuOption.getLabel(), (baseLink + menuOption.getUrl()).replaceAll("//", "/"),
                    baseLink, menuOption.getOrder()));

            if (menuOption.isAddSeparatorAfter()) {
                items.add(null);
            }
        }

        return itemsByClass;

    }

    /**
     * Instàncies de les condicions (una per classe) per no crear-les a cada petició.
     */
    private static final Map<Class<? extends MenuOptionCondition>, MenuOptionCondition> CONDITIONS = new ConcurrentHashMap<Class<? extends MenuOptionCondition>, MenuOptionCondition>();

    /**
     * Avalua la condició de visibilitat d'una opció de menú.
     * Si no té condició retorna true. Si la condició llança una excepció,
     * es registra l'error i l'opció s'amaga.
     */
    private static boolean isVisible(HttpServletRequest request, MenuItemOption menuOption) {

        if (request == null) {
            return true;
        }

        Class<? extends MenuOptionCondition> conditionClass = menuOption.getCondition();
        if (conditionClass == null || conditionClass == MenuOptionConditionAlwaysVisible.class) {
            return true;
        }
        try {
            MenuOptionCondition condition = CONDITIONS.get(conditionClass);
            if (condition == null) {
                condition = conditionClass.getDeclaredConstructor().newInstance();
                CONDITIONS.put(conditionClass, condition);
            }
            return condition.isVisible(request);
        } catch (Throwable th) {
            log.error("Error avaluant la condició " + conditionClass.getName() + " de l'opció de menú '"
                    + menuOption.getLabel() + "': " + th.getMessage(), th);
            return false;
        }
    }

}
