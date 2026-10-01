package org.fundaciobit.genapp.common.i18n;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 
 * @author anadal
 *
 */
public class I18NCommonDateFormat extends I18NAbstractFormat {

    protected final Locale locale;

    /**
     * @param locale
     */
    public I18NCommonDateFormat(Locale locale) {
        super();
        this.locale = locale;
    }

    protected SimpleDateFormat getInstanceOfSimpleDateFormat(Locale loc) {
        return (SimpleDateFormat) SimpleDateFormat.getDateInstance(DateFormat.SHORT, loc);
    }

    protected Date convertToSql(Date d) {
        if (d == null) {
            return null;
        } else {
            return new java.sql.Date(d.getTime());
        }
    }

    @Override
    public Locale getLocale() {
        return locale;
    }

}
