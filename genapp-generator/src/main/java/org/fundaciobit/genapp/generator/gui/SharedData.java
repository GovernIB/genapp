package org.fundaciobit.genapp.generator.gui;

import java.io.File;

import org.fundaciobit.genapp.Project;

/**
 * Title: Rapit Entity Bean 2010 Description: Copyright: Copyright (c) 2014
 * Company: XmasSoft
 * 
 * @author anadal
 */
public class SharedData {

    public enum ProjectType {
        NEW, UPDATE, OPEN, GENERATE
    };

    public static ProjectType project;

    public static Project data = null;

    public static File projectFile = null;

}
