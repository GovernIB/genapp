package org.fundaciobit.genapp.common.ws;

import java.util.Calendar;

import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.annotation.adapters.*;
/**
 * 
 * @author anadal
 *
 */
public class WsSqlDateAdapter extends XmlAdapter<java.util.Date, java.sql.Date> {

    @Override
    public java.util.Date marshal(java.sql.Date sqlDate) throws Exception {
        if(null == sqlDate) {
            return null;
        }
        return new java.util.Date(sqlDate.getTime());
    }

    @Override
    public java.sql.Date unmarshal(java.util.Date utilDate) throws Exception {
        if(null == utilDate) {
            return null;
        }
        return new java.sql.Date(utilDate.getTime());
    }
    
    public static java.sql.Date parseDate(String s) {
      if (s == null) {
        return null;
      }
      return new java.sql.Date(DatatypeConverter.parseDate(s).getTimeInMillis());
    }
    
    public static String printDate(java.sql.Date dt) {
      if (dt == null) {
        return null;
      }
      Calendar c = Calendar.getInstance();
      c.setTime(dt);
      return DatatypeConverter.printDate(c);
    }

}
