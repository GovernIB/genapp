package ${package}.ws.utils;

import javax.jws.WebMethod;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ${package}.logic.utils.LogicUtils;
import ${package}.utils.Constants;

/**
 * 
 * @author anadal
 *
 */
public class BaseWsImpl implements Constants {
  
  protected final Log log = LogFactory.getLog(getClass());
  
  // -------------------------------------------------------------------
  // -------------------------------------------------------------------
  // --------------------------| UTILITATS |----------------------------
  // -------------------------------------------------------------------
  // -------------------------------------------------------------------

  
  @WebMethod
  public String getVersion() {
    return LogicUtils.getVersio();
  }


  @WebMethod
  public int getVersionWs() {
    return VersionsWs.VERSIO_WS_1;
  }
}
