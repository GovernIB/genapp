package org.fundaciobit.genapp;

import java.io.Serializable;

/**
 * Title: Rapit Entity Bean 2010 Description: Copyright: Copyright (c) 2014
 * Company: XmasSoft
 * 
 * @author anadal
 */
public class WebType implements Serializable {

    public static final int PrimaryKey = 0;
    public static final int Text = 1;
    public static final int TextArea = 2;
    public static final int Query = 3;
    public static final int Checkbox = 4;
    public static final int Date = 5;// java.sql.Date
    public static final int Time = 6; // java.sql.Time
    public static final int DateTime = 7; // java.sql.TimeStamp
    public static final int RichText = 8;
    public static final int File = 9;
    public static final int UserID = 11;
    public static final int RoleID = 12;
    public static final int URL = 13;
    public static final int Integer = 14;
    public static final int Decimal = 15;
    public static final int ComboBox = 16;

    public static String toString(int webtype) throws Exception {
        switch (webtype) {
            case PrimaryKey:
                return "PrimaryKey";
            case Text:
                return "Text";
            case TextArea:
                return "TextArea";
            case Query:
                return "Query";
            case Checkbox:
                return "Checkbox";
            case Date:
                return "Date";
            case Time:
                return "Time";
            case DateTime:
                return "DateTime";
            case RichText:
                return "RichText";
            case File:
                return "File";
            case UserID:
                return "UserID";
            case RoleID:
                return "RoleID";
            case URL:
                return "URL";
            case Integer:
                return "Integer";
            case Decimal:
                return "Decimal";
            case ComboBox:
                return "ComboBox";

            default:
                throw new Exception("Tipus no suportat " + webtype);

        }

    }
}
