<%@ page contentType="text/html;charset=UTF-8" language="java" 
%><%@page import="java.io.InputStream"
%><%@page import="java.util.Properties"
%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" 
%><%!

public static Boolean existsSecureContextStatic = null;

public boolean existsSecureContext() {
  //log.info("CAS 0  - " + existsSecureContextStatic);
  if (existsSecureContextStatic == null) {
    try {  
      InputStream propData = getClass().getClassLoader().getResourceAsStream("APP-INF/classes/clientcert.properties");
      //log.info("CAS 1  - " + propData);
      if (propData == null) {
        //log.info("CAS 1A  - [NO EXISTEIX]");
        existsSecureContextStatic = false;
      } else {
        
        Properties properties = new Properties(); 
        properties.load(propData);
        String value = properties.getProperty("${name}.clientcert");
        //log.info("CAS 1A  - [EXISTEIX] = value");
        existsSecureContextStatic = "true".equals(value);
      }
    } catch(Exception e) {
      //log.info("CAS 1E  - Error: " + e.getMessage());
      existsSecureContextStatic = false;    
    }
  }
  //log.info("CAS 2  - : " + existsSecureContextStatic.booleanValue());
  return existsSecureContextStatic.booleanValue();
}


%><%
// TODO ficar dins una variable estatica
boolean existsSecureContext = existsSecureContext(); 


String context = request.getContextPath();

//log.info("");

if (existsSecureContext) {
  
  final String scheme = request.getScheme();
  //log.info(" -------------  SHEME = " + scheme);
  
  //log.info(" -------------  CONTEXT = " + context);
  final boolean thisContextIsClientCert = "/${name}s".equals(context);
  
  if (thisContextIsClientCert && "http".equals(scheme)) {
    // Hem de passar a ${name}
    context = "/${name}";
  } else {
    // Hem de passar a ${name}s
    if (!thisContextIsClientCert && "https".equals(scheme)) {
      context ="/${name}s";
    }
  }
  
}


//log.info(" -------------   CAS 333");
//log.info(" -------------   NEW CONTEXT = " + context);

request.getSession().setAttribute("theContext", context);

%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head></head>
<body>
<c:redirect context="${r"${theContext}"}" url="/common/principal.html"/>
</body>
</html>