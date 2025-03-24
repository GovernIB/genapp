package org.fundaciobit.demogenapp.back.preparer;

import java.util.HashMap;
import java.util.Map;

import org.apache.tiles.Attribute;
import org.apache.tiles.Definition;
import org.apache.tiles.definition.DefinitionsFactory;
import org.apache.tiles.request.Request;
import org.fundaciobit.demogenapp.commons.utils.Constants;
import org.fundaciobit.genapp.common.web.tiles.TileInfo;
import org.fundaciobit.genapp.common.web.tiles.TilesDiscovery;

/**
 * 
 * @author GenApp
 * 18 mar 2025 15:06:51
 */
public class TilesFactoryApp implements DefinitionsFactory {

    private static final Map<String, Definition> definitions = new HashMap<String, Definition>();

    public static final Definition DEF_WEBDB;

    public static final Definition DEF_BASE_DEFINITION;

    public static final String CONTINGUT_PROPERTY = "contingut";

    static {

        definitions.putAll(getBaseDefinitions());

        /*{name=base.definition, template=/WEB-INF/jsp/layout/layout.jsp, role=null, preparerInstance=org.fundaciobit.demogenapp.back.preparer.BasePreparer, attributes={menu_i_contingut=base.menu_i_contingut, peu=base.peu, cap=base.cap, contingut=base.contingut, menu=base.menu}}*/
        DEF_BASE_DEFINITION = new Definition("base.definition", new Attribute("/WEB-INF/jsp/layout/layout.jsp"),
                new HashMap<String, Attribute>());
        DEF_BASE_DEFINITION.setPreparer("org.fundaciobit.demogenapp.back.preparer.BasePreparer");
        DEF_BASE_DEFINITION.putAttribute("menu_i_contingut", new Attribute("base.menu_i_contingut"));
        DEF_BASE_DEFINITION.putAttribute("peu", new Attribute("base.peu"));
        DEF_BASE_DEFINITION.putAttribute("cap", new Attribute("base.cap"));
        DEF_BASE_DEFINITION.putAttribute("contingut", new Attribute("base.contingut"));
        DEF_BASE_DEFINITION.putAttribute("menu", new Attribute("base.menu"));
        definitions.put("base.definition", DEF_BASE_DEFINITION);
        /* ================================== */

        DEF_WEBDB = new Definition(DEF_BASE_DEFINITION);
        //"webdb", new Attribute("/WEB-INF/jsp/layout/layout.jsp"), new HashMap<String, Attribute>());
        DEF_WEBDB.setName(Constants.MENU_BACK_WEBDB_ACCESS);
        DEF_WEBDB.setExtends(DEF_BASE_DEFINITION.getName());
        DEF_WEBDB.putAttribute("pipella", new Attribute(Constants.MENU_BACK_WEBDB_ACCESS));
        DEF_WEBDB.putAttribute("menu", new Attribute("/WEB-INF/jsp/webdb/menu_webdb.jsp"));

        definitions.put(DEF_WEBDB.getName(), DEF_WEBDB);

        definitions.putAll(getAppDefinitions());

        String packageToScan = Constants.DEMOGENAPP_PROPERTY_BASE + "back.controller";
        {
            Map<String, TileInfo> tiles = TilesDiscovery.getDefinitionsByAnnotation(packageToScan);
            TilesDiscovery.addAnnotationTilesToDefinitions(tiles, definitions);
        }

    }

    public TilesFactoryApp() {
        super();
    }

    @Override
    public Definition getDefinition(String name, Request tilesContext) {
        return definitions.get(name);
    }

    private static Map<String, Definition> getAppDefinitions() {

        HashMap<String, Definition> map = new HashMap<String, Definition>();

        /*{name=all, template=/WEB-INF/jsp/layout/layout.jsp, role=null, preparerInstance=org.fundaciobit.demogenapp.back.preparer.BasePreparer, attributes={menu_i_contingut=base.menu_i_contingut, peu=base.peu, contingut=/WEB-INF/jsp/all/homepublic.jsp, cap=base.cap, menu=/WEB-INF/jsp/moduls/menu_inici.jsp}}*/
        Definition def_all = new Definition(DEF_BASE_DEFINITION);
        def_all.setName("all");
        def_all.putAttribute("menu", new Attribute("/WEB-INF/jsp/moduls/menu_inici.jsp"));
        def_all.putAttribute("contingut", new Attribute("/WEB-INF/jsp/all/homepublic.jsp"));
        map.put(def_all.getName(), def_all);
        /* ================================== */

        /*{name=avislegal_ca, template=/WEB-INF/jsp/layout/layout.jsp, role=null, preparerInstance=org.fundaciobit.demogenapp.back.preparer.BasePreparer, attributes={menu_i_contingut=base.menu_i_contingut, peu=base.peu, contingut=/WEB-INF/jsp/all/avislegal_ca.jsp, cap=base.cap, menu=/WEB-INF/jsp/moduls/menu_inici.jsp}}*/
        Definition def_avislegal_ca = new Definition(def_all);
        def_avislegal_ca.setName("avislegal_ca");
        def_avislegal_ca.putAttribute("contingut", new Attribute("/WEB-INF/jsp/all/avislegal_ca.jsp"));
        map.put(def_avislegal_ca.getName(), def_avislegal_ca);
        /* ================================== */

        /*{name=avislegal_es, template=/WEB-INF/jsp/layout/layout.jsp, role=null, preparerInstance=org.fundaciobit.demogenapp.back.preparer.BasePreparer, attributes={menu_i_contingut=base.menu_i_contingut, peu=base.peu, contingut=/WEB-INF/jsp/all/avislegal_es.jsp, cap=base.cap, menu=/WEB-INF/jsp/moduls/menu_inici.jsp}}*/
        Definition def_avislegal_es = new Definition(def_all);
        def_avislegal_es.setName("avislegal_es");
        def_avislegal_es.putAttribute("contingut", new Attribute("/WEB-INF/jsp/all/avislegal_es.jsp"));
        map.put(def_avislegal_es.getName(), def_avislegal_es);
        /* ================================== */

        /*{name=homepublic, template=/WEB-INF/jsp/layout/layout.jsp, role=null, preparerInstance=org.fundaciobit.demogenapp.back.preparer.BasePreparer, attributes={menu_i_contingut=base.menu_i_contingut, peu=base.peu, contingut=/WEB-INF/jsp/all/homepublic.jsp, cap=base.cap, menu=/WEB-INF/jsp/moduls/menu_inici.jsp}}*/
        Definition def_homepublic = new Definition(def_all);
        def_homepublic.setName("homepublic");
        def_homepublic.putAttribute("contingut", new Attribute("/WEB-INF/jsp/all/homepublic.jsp"));
        map.put(def_homepublic.getName(), def_homepublic);
        /* ================================== */

        /*{name=common, template=/WEB-INF/jsp/layout/layout.jsp, role=null, preparerInstance=org.fundaciobit.demogenapp.back.preparer.BasePreparer, attributes={menu_i_contingut=base.menu_i_contingut, peu=base.peu, contingut=/WEB-INF/jsp/principal.jsp, cap=base.cap, menu=/WEB-INF/jsp/moduls/menu_inici.jsp}}*/
        Definition def_common = new Definition(DEF_BASE_DEFINITION);
        def_common.setName(Constants.MENU_BACK_PUBLIC_AND_COMMON_ACCESS);
        def_common.putAttribute("menu", new Attribute("/WEB-INF/jsp/moduls/menu_inici.jsp"));
        def_common.putAttribute("contingut", new Attribute("/WEB-INF/jsp/principal.jsp"));
        map.put(def_common.getName(), def_common);
        /* ================================== */

        /*{name=principal, template=/WEB-INF/jsp/layout/layout.jsp, role=null, preparerInstance=org.fundaciobit.demogenapp.back.preparer.BasePreparer, attributes={menu_i_contingut=base.menu_i_contingut, peu=base.peu, contingut=/WEB-INF/jsp/principal.jsp, cap=base.cap, menu=/WEB-INF/jsp/moduls/menu_inici.jsp}}*/
        Definition def_principal = new Definition(def_common);
        def_principal.setName("principal");
        def_principal.putAttribute("contingut", new Attribute("/WEB-INF/jsp/principal.jsp"));
        map.put(def_principal.getName(), def_principal);
        /* ================================== */

        /*{name=option1Common, template=/WEB-INF/jsp/layout/layout.jsp, role=null, preparerInstance=org.fundaciobit.demogenapp.back.preparer.BasePreparer, attributes={menu_i_contingut=base.menu_i_contingut, peu=base.peu, contingut=/WEB-INF/jsp/common/option.jsp, cap=base.cap, menu=/WEB-INF/jsp/moduls/menu_inici.jsp}}*/
        Definition def_option1Common = new Definition(def_common);
        def_option1Common.setName("option1Common");
        def_option1Common.putAttribute("contingut", new Attribute("/WEB-INF/jsp/common/option.jsp"));
        map.put(def_option1Common.getName(), def_option1Common);
        /* ================================== */

        /*{name=option2Common, template=/WEB-INF/jsp/layout/layout.jsp, role=null, preparerInstance=org.fundaciobit.demogenapp.back.preparer.BasePreparer, attributes={menu_i_contingut=base.menu_i_contingut, peu=base.peu, contingut=/WEB-INF/jsp/common/option.jsp, cap=base.cap, menu=/WEB-INF/jsp/moduls/menu_inici.jsp}}*/
        Definition def_option2Common = new Definition(def_common);
        def_option2Common.setName("option2Common");
        def_option2Common.putAttribute("contingut", new Attribute("/WEB-INF/jsp/common/option.jsp"));
        map.put(def_option2Common.getName(), def_option2Common);
        /* ================================== */

        /*{name=user, template=/WEB-INF/jsp/layout/layout.jsp, role=null, preparerInstance=org.fundaciobit.demogenapp.back.preparer.BasePreparer, attributes={menu_i_contingut=base.menu_i_contingut, peu=base.peu, pipella=user, cap=base.cap, contingut=base.contingut, menu=/WEB-INF/jsp/moduls/menu_user.jsp}}*/
        Definition def_user = new Definition(DEF_BASE_DEFINITION);
        def_user.setName(Constants.MENU_BACK_BASIC_ACCESS);
        def_user.putAttribute("pipella", new Attribute(Constants.MENU_BACK_BASIC_ACCESS));
        def_user.putAttribute("menu", new Attribute("/WEB-INF/jsp/moduls/menu_user.jsp"));
        map.put(def_user.getName(), def_user);
        /* ================================== */

        /*{name=option1User, template=/WEB-INF/jsp/layout/layout.jsp, role=null, preparerInstance=org.fundaciobit.demogenapp.back.preparer.BasePreparer, attributes={menu_i_contingut=base.menu_i_contingut, peu=base.peu, contingut=/WEB-INF/jsp/user/user.jsp, pipella=user, cap=base.cap, menu=/WEB-INF/jsp/moduls/menu_user.jsp}}*/
        Definition def_option1User = new Definition(def_user);
        def_option1User.setName("option1User");
        def_option1User.putAttribute("contingut", new Attribute("/WEB-INF/jsp/user/user.jsp"));
        map.put(def_option1User.getName(), def_option1User);
        /* ================================== */

        /*{name=option2User, template=/WEB-INF/jsp/layout/layout.jsp, role=null, preparerInstance=org.fundaciobit.demogenapp.back.preparer.BasePreparer, attributes={menu_i_contingut=base.menu_i_contingut, peu=base.peu, contingut=/WEB-INF/jsp/user/user.jsp, pipella=user, cap=base.cap, menu=/WEB-INF/jsp/moduls/menu_user.jsp}}*/
        Definition def_option2User = new Definition(def_user);
        def_option2User.setName("option2User");
        def_option2User.putAttribute("contingut", new Attribute("/WEB-INF/jsp/user/user.jsp"));
        map.put(def_option2User.getName(), def_option2User);
        /* ================================== */

        /*{name=admin, template=/WEB-INF/jsp/layout/layout.jsp, role=null, preparerInstance=org.fundaciobit.demogenapp.back.preparer.BasePreparer, attributes={menu_i_contingut=base.menu_i_contingut, peu=base.peu, pipella=admin, cap=base.cap, contingut=base.contingut, menu=/WEB-INF/jsp/moduls/menu_admin.jsp}}*/
        Definition def_admin = new Definition(DEF_BASE_DEFINITION);
        def_admin.setName(Constants.MENU_BACK_ADMIN_ACCESS);
        def_admin.putAttribute("pipella", new Attribute(Constants.MENU_BACK_ADMIN_ACCESS));
        def_admin.putAttribute("menu", new Attribute("/WEB-INF/jsp/moduls/menu_admin.jsp"));
        map.put(def_admin.getName(), def_admin);
        /* ================================== */

        /*{name=option1Admin, template=/WEB-INF/jsp/layout/layout.jsp, role=null, preparerInstance=org.fundaciobit.demogenapp.back.preparer.BasePreparer, attributes={menu_i_contingut=base.menu_i_contingut, peu=base.peu, contingut=/WEB-INF/jsp/admin/admin.jsp, pipella=admin, cap=base.cap, menu=/WEB-INF/jsp/moduls/menu_admin.jsp}}*/
        Definition def_option1Admin = new Definition(def_admin);
        def_option1Admin.setName("option1Admin");
        def_option1Admin.putAttribute("contingut", new Attribute("/WEB-INF/jsp/admin/admin.jsp"));
        map.put(def_option1Admin.getName(), def_option1Admin);
        /* ================================== */

        /*{name=option2Admin, template=/WEB-INF/jsp/layout/layout.jsp, role=null, preparerInstance=org.fundaciobit.demogenapp.back.preparer.BasePreparer, attributes={menu_i_contingut=base.menu_i_contingut, peu=base.peu, contingut=/WEB-INF/jsp/admin/admin.jsp, pipella=admin, cap=base.cap, menu=/WEB-INF/jsp/moduls/menu_admin.jsp}}*/
        Definition def_option2Admin = new Definition(def_admin);
        def_option2Admin.setName("option2Admin");
        def_option2Admin.putAttribute("contingut", new Attribute("/WEB-INF/jsp/admin/admin.jsp"));
        map.put(def_option2Admin.getName(), def_option2Admin);
        /* ================================== */

        /* ================================== */

        /*{name=desenvolupament, template=/WEB-INF/jsp/layout/layout.jsp, role=null, preparerInstance=org.fundaciobit.demogenapp.back.preparer.BasePreparer, attributes={menu_i_contingut=base.menu_i_contingut, peu=base.peu, pipella=desenvolupament, contingut=/WEB-INF/jsp/desenvolupament.jsp, cap=base.cap, menu=/WEB-INF/jsp/moduls/menu_desenvolupament.jsp}}*/
        Definition def_desenvolupament = new Definition(DEF_BASE_DEFINITION);
        def_desenvolupament.setName("desenvolupament");
        def_desenvolupament.putAttribute("contingut", new Attribute("/WEB-INF/jsp/desenvolupament.jsp"));
        map.put(def_desenvolupament.getName(), def_desenvolupament);
        /* ================================== */

        return map;

    }

    private static Map<String, Definition> getBaseDefinitions() {

        HashMap<String, Definition> map = new HashMap<String, Definition>();

        /*{name=base.cap, template=/WEB-INF/jsp/moduls/cap.jsp, role=null, preparerInstance=org.fundaciobit.demogenapp.back.preparer.CapPreparer, attributes=null}*/
        Definition def_base_cap = new Definition("base.cap", new Attribute("/WEB-INF/jsp/moduls/cap.jsp"),
                new HashMap<String, Attribute>());
        def_base_cap.setPreparer("org.fundaciobit.demogenapp.back.preparer.CapPreparer");
        map.put("base.cap", def_base_cap);
        /* ================================== */

        /*{name=base.menu_i_contingut, template=/WEB-INF/jsp/moduls/menu_i_contingut.jsp, role=null, preparerInstance=null, attributes=null}*/
        Definition def_base_menu_i_contingut = new Definition("base.menu_i_contingut",
                new Attribute("/WEB-INF/jsp/moduls/menu_i_contingut.jsp"), new HashMap<String, Attribute>());
        map.put("base.menu_i_contingut", def_base_menu_i_contingut);
        /* ================================== */

        /*{name=base.menu, template=/WEB-INF/jsp/moduls/blanc.jsp, role=null, preparerInstance=org.fundaciobit.demogenapp.back.preparer.MenuPreparer, attributes=null}*/
        Definition def_base_menu = new Definition("base.menu", new Attribute("/WEB-INF/jsp/moduls/blanc.jsp"),
                new HashMap<String, Attribute>());
        def_base_menu.setPreparer("org.fundaciobit.demogenapp.back.preparer.MenuPreparer");
        map.put("base.menu", def_base_menu);
        /* ================================== */

        /*{name=base.contingut, template=/WEB-INF/jsp/moduls/blanc.jsp, role=null, preparerInstance=null, attributes=null}*/
        Definition def_base_contingut = new Definition("base.contingut", new Attribute("/WEB-INF/jsp/moduls/blanc.jsp"),
                new HashMap<String, Attribute>());
        map.put("base.contingut", def_base_contingut);
        /* ================================== */

        /*{name=base.peu, template=/WEB-INF/jsp/moduls/peu.jsp, role=null, preparerInstance=org.fundaciobit.demogenapp.back.preparer.PeuPreparer, attributes=null}*/
        Definition def_base_peu = new Definition("base.peu", new Attribute("/WEB-INF/jsp/moduls/peu.jsp"),
                new HashMap<String, Attribute>());
        def_base_peu.setPreparer("org.fundaciobit.demogenapp.back.preparer.PeuPreparer");
        map.put("base.peu", def_base_peu);
        /* ================================== */

        /*{name=principal.body, template=/WEB-INF/web/tiles/principal/principal.jsp, role=null, preparerInstance=null, attributes=null}*/
        Definition def_principal_body = new Definition("principal.body",
                new Attribute("/WEB-INF/web/tiles/principal/principal.jsp"), new HashMap<String, Attribute>());
        map.put("principal.body", def_principal_body);
        /* ================================== */

        /*{name=sense_enllaz, template=/WEB-INF/jsp/layout/layout.jsp, role=null, preparerInstance=org.fundaciobit.demogenapp.back.preparer.BasePreparer, attributes={menu_i_contingut=base.menu_i_contingut, peu=base.peu, enlaces=, cap=base.cap, contingut=base.contingut, menu=base.menu}}*/
        Definition def_sense_enllaz = new Definition("sense_enllaz", new Attribute("/WEB-INF/jsp/layout/layout.jsp"),
                new HashMap<String, Attribute>());
        def_sense_enllaz.setExtends("base.definition");
        def_sense_enllaz.setPreparer("org.fundaciobit.demogenapp.back.preparer.BasePreparer");
        def_sense_enllaz.putAttribute("menu_i_contingut", new Attribute("base.menu_i_contingut"));
        def_sense_enllaz.putAttribute("peu", new Attribute("base.peu"));
        def_sense_enllaz.putAttribute("enlaces", new Attribute(""));
        def_sense_enllaz.putAttribute("cap", new Attribute("base.cap"));
        def_sense_enllaz.putAttribute("contingut", new Attribute("base.contingut"));
        def_sense_enllaz.putAttribute("menu", new Attribute("base.menu"));
        map.put("sense_enllaz", def_sense_enllaz);
        /* ================================== */

        /*{name=exceptionTile, template=/WEB-INF/jsp/layout/layout.jsp, role=null, preparerInstance=org.fundaciobit.demogenapp.back.preparer.BasePreparer, attributes={menu_i_contingut=base.menu_i_contingut, peu=base.peu, contingut=/error.jsp, cap=base.cap, menu=base.menu}}*/
        Definition def_exceptionTile = new Definition("exceptionTile", new Attribute("/WEB-INF/jsp/layout/layout.jsp"),
                new HashMap<String, Attribute>());
        def_exceptionTile.setExtends("base.definition");
        def_exceptionTile.setPreparer("org.fundaciobit.demogenapp.back.preparer.BasePreparer");
        def_exceptionTile.putAttribute("menu_i_contingut", new Attribute("base.menu_i_contingut"));
        def_exceptionTile.putAttribute("peu", new Attribute("base.peu"));
        def_exceptionTile.putAttribute("contingut", new Attribute("/error.jsp"));
        def_exceptionTile.putAttribute("cap", new Attribute("base.cap"));
        def_exceptionTile.putAttribute("menu", new Attribute("base.menu"));
        map.put("exceptionTile", def_exceptionTile);
        /* ================================== */

        return map;

    }

}
