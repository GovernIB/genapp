package org.fundaciobit.genapp.common.web.tiles;

/**
 * 
 * @author anadal
 * 21 mar 2025 10:51:05
 */
public class TileInfo {

    public final Tile tile;
    
    public final Class<?> classe;
    
    public TileInfo(Tile tile, Class<?> classe) {
        this.tile = tile;
        this.classe = classe;
    }
    
}
