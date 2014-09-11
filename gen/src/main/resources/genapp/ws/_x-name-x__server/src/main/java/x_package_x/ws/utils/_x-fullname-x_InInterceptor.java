package ${package}.ws.utils;

import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Locale;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.binding.soap.SoapFault;
import org.apache.cxf.frontend.MethodDispatcher;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.security.SecurityContext;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genapp.common.i18n.I18NValidationException;

import org.fundaciobit.genapp.common.ws.WsI18NException;
import org.fundaciobit.genapp.common.ws.WsValidationException;

import ${package}.utils.Configuracio;
import ${package}.utils.Constants;
import org.apache.cxf.service.Service;

import ${package}.logic.utils.I18NLogicUtils;

/**
 * 
 * @author anadal Veure https://cxf.apache.org/docs/interceptors.html
 */

public class ${fullname}InInterceptor extends AbstractPhaseInterceptor<Message> {

  protected static final javax.xml.namespace.QName QNAME = new javax.xml.namespace.QName("-1");

  protected final Log log = LogFactory.getLog(getClass());

  

  public ${fullname}InInterceptor() {
    // Veure https://cxf.apache.org/docs/interceptors.html
    super(Phase.PRE_INVOKE);
  }

  @SuppressWarnings("unchecked")
  public void handleMessage(Message message) throws Fault {
    
    boolean logEnable = true; // log.isDebugEnabled(); 
   
    if (logEnable) {
      log.info(" ------------------ ${fullname}WSInInterceptor  --------------");
      
         
      
      try {
        
        Method method = getTargetMethod(message);
        
              
        log.info("  + Method NAME = " + method.getName());
        log.info("  + Method CLASS = " + method.getDeclaringClass());
        
  
      HttpServletRequest hsr = (HttpServletRequest)message.get("HTTP.REQUEST"); 
      log.info(" USR_1:  " +hsr.getRemoteUser());
  
      log.info(" ROLE: ${prefix}_ADMIN  " +hsr.isUserInRole(Constants.${prefix}_ADMIN));
      log.info(" ROLE: ${prefix}_USER  " +hsr.isUserInRole(Constants.${prefix}_USER));
      
      } catch (Exception e) {
       log.error(e.getMessage());
      }

    
      log.info("${fullname}InInterceptor::handleMessage() =>  Thread = "
        + Thread.currentThread().getId());
  }
    

    SecurityContext context = message.get(SecurityContext.class);
    if (context == null || context.getUserPrincipal() == null) {
      log.error("S'ha cridat a l'API sense autenticar la petició.");
      return;
    }

    String userapp = context.getUserPrincipal().getName();
    // DEBUG
    if (logEnable) {
      log.info("${fullname}InInterceptor::handleMessage() => user " + userapp);
      log.info("${fullname}InInterceptor::handleMessage() => ${prefix}_USER "
          + context.isUserInRole(Constants.${prefix}_USER));
      log.info("${fullname}InInterceptor::handleMessage() => ${prefix}_ADMIN "
          + context.isUserInRole(Constants.${prefix}_ADMIN));
    }

	// TODO GENAPP Get username from DATA-BASE
	String usuariAplicacio = null;
	
    // Si estam a CAIB llavors s'ha autenticat i autoritzat contra seycon,
    // però a més hauria de tenir usuari app donat d'alta al ${fullname}
    if (Configuracio.isCAIB()) {

      if (usuariAplicacio == null) {
        // TODO Traduccio
        // LogicI18NUtils.tradueix(loc, code, args)
        final String msg = "L´usuari aplicació " + userapp
          + " està autenticat en el domini de seguretat,"
          + " però no esta donat d'alta dins ${fullname}.";
        
        log.error("${fullname}InInterceptor::handleMessage(CAIB) ", new Exception(msg));
        SoapFault sf;

        sf = new SoapFault(msg,QNAME);
        throw sf;
      }

    } else {
      if (usuariAplicacio == null) {
        log.error("${fullname}InInterceptor::handleMessage(DEFAULT) Usuari APP = null");
        return;
      }
    }
  }
  
  
  
  private Method getTargetMethod(Message m) {
    BindingOperationInfo bop = m.getExchange().get(BindingOperationInfo.class);
    MethodDispatcher md = (MethodDispatcher) 
        m.getExchange().get(Service.class).get(MethodDispatcher.class.getName());
   
    return md.getMethod(bop);
  }
  

  @Override
  public void handleFault(Message message) {

    Fault f = (Fault) message.getContent(Exception.class);

    log.error("${fullname}InInterceptor::handleFault() - Code = " + f.getCode());
    log.error("${fullname}InInterceptor::handleFault() - Msg = " + f.getMessage());

    Throwable cause = f.getCause();
	
	// TODO GENAPP obtenir Idioma de l'usuari aplicacio
	String language = "ca";

    log.error("${fullname}InInterceptor::handleFault() - Cause = " + cause);
    if (cause != null) {
      log.error("${fullname}InInterceptor::handleFault() - Cause Class = " + cause.getClass());
      if (cause instanceof UndeclaredThrowableException) {
        log.error("${fullname}InInterceptor::handleFault() - Cause.UndeclaredThrowable");
        cause = ((UndeclaredThrowableException) cause).getUndeclaredThrowable();
      }
      if (cause instanceof I18NException) {
        log.error("${fullname}InInterceptor::handleFault() - CAUSE.I18NException");
        
        I18NException i18n = (I18NException) cause;
        String msg = I18NLogicUtils.getMessage(i18n, new Locale(language));
        message.setContent(Exception.class,
        // new WsI18NException(i18n.getTraduccio(), msg, cause));
            new WsI18NException(WsUtils.convertToWsTranslation(i18n.getTraduccio()), msg, cause));
      } else if (cause instanceof I18NValidationException) {
        log.error("${fullname}InInterceptor::handleFault() - CAUSE.ValidationException");
        I18NValidationException ve = (I18NValidationException) cause;
        message.setContent(
            Exception.class,
            WsUtils.convertToWsValidationException(ve,
                new Locale(language)));
      } else {
        log.error("${fullname}InInterceptor::handleFault() - Cause.msg = " + cause.getMessage());
        log.error("${fullname}InInterceptor::handleFault() - Cause.type = " + cause.getClass());
      }

    }
    super.handleFault(message);
  }



}