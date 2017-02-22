package ${package}.back.preparer;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.security.RunAs;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.tiles.AttributeContext;
import org.apache.tiles.context.TilesRequestContext;
import org.apache.tiles.preparer.PreparerException;
import org.apache.tiles.preparer.ViewPreparerSupport;
import org.fundaciobit.demogenapp.back.security.LoginInfo;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import ${package}.back.security.LoginInfo;

import ${package}.utils.Constants;



/**
 * @author anadal
 *
 */
@RunAs("${prefix}_USER")
@Component
public class BasePreparer extends ViewPreparerSupport implements Constants {
  

  protected final Logger log = Logger.getLogger(getClass());

  
	@Override
	public void execute(TilesRequestContext tilesContext, 
	    AttributeContext attributeContext) throws PreparerException {

	  Map<String, Object> request = tilesContext.getRequestScope();
	  
    // Informació de Login
    LoginInfo loginInfo = LoginInfo.getInstance();
    // Posa dins la sessió la informació de Login    
    request.put("loginInfo", loginInfo);

	  
	  
   	// URL 
	  // TODO ficarho dins cache (veure Capperpare.java)
	  Object[] requestObjects = tilesContext.getRequestObjects();
	  if (requestObjects[0] instanceof HttpServletRequest) {
	    HttpServletRequest httpRequest = (HttpServletRequest) requestObjects[0];

	    
	    request.put("urlActual", httpRequest.getServletPath());

      // Compatibilitat IE8
	    String userAgent = httpRequest.getHeader("User-Agent");
	    if (userAgent != null) {
	      int index = userAgent.toUpperCase().indexOf("MSIE");
	      if (index != -1) {
	        try {
	           String ieversion = userAgent.substring(index + 4,userAgent.indexOf(";", index + 4));
	           if (Float.parseFloat(ieversion) < 9.0f) {
	             request.put("IE8", true);
	           }
	        } catch(Throwable e) {
	          log.debug(e);
	        }
	      }
	    }
	  }

    // Language
    Locale loc = LocaleContextHolder.getLocale();
    request.put("lang", loc.toString()); // LANG i si es necessari el country
    request.put("onlylang", loc.getLanguage()); // només el LANG

    // Pipella
    request.put("pipella", attributeContext.getAttribute("pipella"));
    
    
    
    // TODO GENAPP
    // Warning for each ROLE 
    
    // Avisos
    Map<String,Long> avisos = new HashMap<String, Long>(); 
    //avisos.put(rol, <<Number of warnings>>);
    request.put("avisos", avisos); 

    

    
    if (attributeContext.getAttribute("menu") != null) {
      request.put("menu_tile", attributeContext.getAttribute("menu").toString());
    }
    request.put("contingut_tile", attributeContext.getAttribute("contingut").toString());


    
	}

}
