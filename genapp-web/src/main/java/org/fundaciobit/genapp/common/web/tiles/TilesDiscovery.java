package org.fundaciobit.genapp.common.web.tiles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.tiles.Attribute;
import org.apache.tiles.Definition;
import org.fundaciobit.genapp.common.web.controller.CommonBaseController;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;

/**
 * 
 * @author anadal
 * 19 mar 2025 11:50:31
 */
public class TilesDiscovery {

    public static final String CONTINGUT_PROPERTY = "contingut";

    public static final Logger log = Logger.getLogger(TilesDiscovery.class);

    public static Map<String, TileInfo> getDefinitionsByAnnotation(String packageToScan) {

        Map<String, TileInfo> tiles = new HashMap<String, TileInfo>();

        ClassGraph cg = new ClassGraph().enableAnnotationInfo();

        if (packageToScan != null) {
            cg.acceptPackages(packageToScan);
        }

        // Habilitar escaneo de anotaciones
        ScanResult scanResult = cg.scan();

        ArrayList<ClassInfo> cil = scanResult.getClassesWithAnnotation(Tile.class);

        for (ClassInfo classInfo2 : cil) {
            // Obtén la clase
            Class<?> classe = classInfo2.loadClass();

            // Imprime la clase y el valor de la anotación
            Tile[] anotacions = classe.getAnnotationsByType(Tile.class);
            if (anotacions == null) {
                continue;
            } else {
                for (Tile anotacio : anotacions) {
                    if (anotacio != null) {
                        tiles.put(anotacio.name(), new TileInfo(anotacio, classe));
                    }
                }
            }
        }

        return tiles;

    }

    public static void addAnnotationTilesToDefinitions(Map<String, TileInfo> tiles,
            Map<String, Definition> baseDefinitions) {

        for (Entry<String, TileInfo> tileKV : tiles.entrySet()) {

            TileInfo tile = tileKV.getValue();

            processTile(baseDefinitions, tiles, tile);
        }

    }

    private static void processTile(Map<String, Definition> baseDefinitions, Map<String, TileInfo> tiles,
            TileInfo tileInfo) {

        final Tile tile = tileInfo.tile;
        // Cercam l'extends

        //String extendsName;

        String contentJsp;

        if (tile.contentJsp().isEmpty()) {

            // Mirarem si la classe fa extends d'una classe WEbDBController
            Class<?> classe = tileInfo.classe;

            if (CommonBaseController.class.isAssignableFrom(classe)) {
                CommonBaseController<?, ?> cbc;

                // Cercam el tileName de la classe pare
                classe = classe.getSuperclass();
                try {
                    cbc = (CommonBaseController<?, ?>) classe.getDeclaredConstructor().newInstance();

                } catch (Exception e) {
                    throw new IllegalArgumentException(
                            "Error instanciant la classe " + classe.getName() + ": " + e.getMessage(), e);
                }

                String parentClassTileName;
                switch (tile.type()) {
                    case WEBDB_LIST: //_EXTENDS:
                        // Invocar al método getTileList del objeto 'cbc' cuya clase es 'classe'
                        try {
                            log.info("Invocant getTileList de " + classe.getName());
                            parentClassTileName = (String) classe.getMethod("getTileList").invoke(cbc);
                        } catch (Exception e) {
                            throw new IllegalArgumentException("Error invocant el mètode getTileList de la classe "
                                    + classe.getName() + ": " + e.getMessage(), e);
                        }
                    break;
                    case WEBDB_FORM: //_EXTENDS:
                        try {
                            // Invocar al método getTileForm del objeto 'cbc' cuya clase es 'classe'
                            parentClassTileName = (String) classe.getMethod("getTileForm").invoke(cbc);
                        } catch (Exception e) {
                            throw new IllegalArgumentException("Error invocant el mètode getTileForm de la classe "
                                    + classe.getName() + ": " + e.getMessage(), e);
                        }
                    break;

                    case ANOTHER:
                        throw new IllegalArgumentException("L'anotació @Tile amb nom " + tile.name() + " de la classe "
                                + classe.getName()
                                + " té tipus ANOTHER, que significa que requereix definir l'atribut 'extendsTile'.");

                    default:
                        throw new IllegalArgumentException("El tipus de Tile " + tile.type() + " de la classe "
                                + classe.getName() + " no està implementat.");
                }

                TileInfo tileInfoOfParentClass = tiles.get(parentClassTileName);

                contentJsp = tileInfoOfParentClass.tile.contentJsp();

            } else {
                if (tile.extendsTile() == null || tile.extendsTile().trim().length() == 0) {
                    throw new IllegalArgumentException("La classe " + classe.getName()
                        + " té definida l'anotació @Tile, però aquesta requereix que la classe estengui"
                        + " de CommonBaseController o afegir l'atribut extendsTile a l'anotació @Tile.");
                }
                contentJsp = null;
            }

        } else {
            //extendsName = tile.extendsTile();
            contentJsp = tile.contentJsp();
        }

        //log.error("=======================================");
        //log.error(" Processant Tile " + tile.name() + "\n" + " extendsName = " + tile.extendsTile() + "\n"
        //        + " contentJsp = " + contentJsp + " \n **************** \n ");

        Definition base = baseDefinitions.get(tile.extendsTile());

        if (base == null) {

            TileInfo baseTile = tiles.get(tile.extendsTile());

            if (baseTile != null) {
                processTile(baseDefinitions, tiles, baseTile);
                base = baseDefinitions.get(tile.extendsTile());
            }

            if (base == null) {
                throw new IllegalArgumentException(
                        "L'anotacio @Tile amb nom " + tile.name() + " te definit un 'extends' " + tile.extendsTile()
                                + " que no està declarat ni a TilesFactoryApp ni en cap anotacio @Tile.");
            }
        }

        Definition def = new Definition(base);
        def.setName(tile.name());
        def.setExtends(tile.extendsTile());

        TileAttribute[] attributes = tile.attributes();
        if (attributes != null && attributes.length != 0) {
            for (TileAttribute attr : attributes) {
                def.putAttribute(attr.name(), new Attribute(attr.value()));
                //log.error("    NEW ATTRIBUTE => " + attr.name() + " = " + attr.value());
            }
        }

        if (contentJsp != null) {
          def.putAttribute(CONTINGUT_PROPERTY, new Attribute(contentJsp));
        }

        baseDefinitions.put(def.getName(), def);

    }

}
