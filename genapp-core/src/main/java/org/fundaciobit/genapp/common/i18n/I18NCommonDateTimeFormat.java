package org.fundaciobit.genapp.common.i18n;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 
 * @author anadal
 *
 */
public class I18NCommonDateTimeFormat extends I18NAbstractFormat {

    protected final Locale locale;

    /**
     * @param locale
     */
    public I18NCommonDateTimeFormat(Locale locale) {
        super();
        this.locale = locale;
    }

    @Override
    protected SimpleDateFormat getInstanceOfSimpleDateFormat(Locale loc) {
        return (SimpleDateFormat) SimpleDateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, loc);
    }

    @Override
    protected Date convertToSql(Date d) {
        if (d == null) {
            return null;
        } else {
            return new Timestamp(d.getTime());
        }
    }

    @Override
    public Locale getLocale() {
        return this.locale;
    }

}
