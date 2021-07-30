package org.fundaciobit.genappsqltutorial.tutorial.utils.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author anadal
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TutorialUnit {

    public String title();
    
    public String description();
    
    public String url();

}
