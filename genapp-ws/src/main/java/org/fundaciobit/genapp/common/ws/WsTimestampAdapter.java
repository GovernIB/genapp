package org.fundaciobit.genapp.common.ws;

import java.util.Calendar;
import java.util.Date;
import java.sql.Timestamp;

import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * 
 * @author anadal
 * 
 */
public class WsTimestampAdapter extends XmlAdapter<Date, Timestamp> {

  @Override
  public Date marshal(Timestamp v) {
    return new Date(v.getTime());
  }

  @Override
  public Timestamp unmarshal(Date v) {
    return new Timestamp(v.getTime());
  }
  
  
  
  public static Timestamp parseDateTime(String s) {
    if (s == null) {
      return null;
    }
    return new Timestamp(DatatypeConverter.parseDateTime(s).getTime().getTime());
  
  }
  
  public static String printDateTime(Timestamp ts) {
  
    if (ts == null) {
      return null;
    }    

    Calendar c = Calendar.getInstance();
    c.setTimeInMillis(ts.getTime());
    return DatatypeConverter.printDateTime(c);
  
  }
  
  
}
