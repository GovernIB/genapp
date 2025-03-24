package org.fundaciobit.genapp.common.web.tiles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author anadal
 * 21 mar 2025 8:43:15
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TileAttribute {

    public String name();

    public String value();

}