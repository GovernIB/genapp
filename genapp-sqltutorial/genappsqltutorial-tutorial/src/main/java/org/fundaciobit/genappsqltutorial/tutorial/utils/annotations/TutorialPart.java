package org.fundaciobit.genappsqltutorial.tutorial.utils.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author anadal
 * @TutorialPart(title="", description="", sql="", order=1)
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TutorialPart {
    
    public String title();

    public String description();
    
    public String sql();
    
    public int order();
}
