package org.fundaciobit.genapp.common.ws;

import java.util.Calendar;
import java.util.Date;
import java.sql.Time;

import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * 
 * @author anadal
 * 
 */
public class WsTimeAdapter extends XmlAdapter<Date, Time> {

  @Override
  public Date marshal(Time v) {
    return new Date(v.getTime());
  }

  @Override
  public Time unmarshal(Date v) {
    return new Time(v.getTime());
  }
  
  
  
  public static java.sql.Time parseTime(String s) {
    if (s == null) {
      return null;
    }
    return new java.sql.Time(DatatypeConverter.parseTime(s).getTimeInMillis());
  }
  
  public static String printTime(java.sql.Time dt) {
    if (dt == null) {
      return null;
    }
    Calendar c = Calendar.getInstance();
    c.setTime(dt);
    return DatatypeConverter.printTime(c);
  }
  
}
