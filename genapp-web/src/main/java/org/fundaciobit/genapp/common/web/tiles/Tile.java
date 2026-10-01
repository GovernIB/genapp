package org.fundaciobit.genapp.common.web.tiles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



/**
 * 
 * @author anadal
 * 29 nov 2024 13:13:21
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Repeatable(Tiles.class)
public @interface Tile {

    public String name();

    public String extendsTile();

    public String contentJsp() default "";
    
    public TileAttribute[] attributes() default {};
    
    public TileType type();

}