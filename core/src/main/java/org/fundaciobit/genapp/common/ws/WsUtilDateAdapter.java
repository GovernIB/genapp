package org.fundaciobit.genapp.common.ws;

import java.util.Calendar;

import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.annotation.adapters.*;
/**
 * 
 * @author anadal
 *
 */
public class WsUtilDateAdapter extends XmlAdapter<java.util.Date, java.util.Date> {

    @Override
    public java.util.Date marshal(java.util.Date utilDate) throws Exception {
        if(null == utilDate) {
            return null;
        }
        return new java.util.Date(utilDate.getTime());
    }

    @Override
    public java.util.Date unmarshal(java.util.Date utilDate) throws Exception {
        if(null == utilDate) {
            return null;
        }
        return new java.util.Date(utilDate.getTime());
    }
    
    public static java.util.Date parseDate(String s) {
      if (s == null) {
        return null;
      }
      return new java.util.Date(DatatypeConverter.parseDate(s).getTimeInMillis());
    }
    
    public static String printDate(java.util.Date dt) {
      if (dt == null) {
        return null;
      }
      Calendar c = Calendar.getInstance();
      c.setTime(dt);
      return DatatypeConverter.printDate(c);
    }

}
