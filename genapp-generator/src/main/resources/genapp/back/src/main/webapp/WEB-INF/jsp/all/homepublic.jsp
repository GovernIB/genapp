<#assign symbol_dollar = "$">
<%@page import="org.springframework.security.core.Authentication"
%><%@page import="org.springframework.context.i18n.LocaleContextHolder"
%><%@ page language="java" 
%><%@ include file="/WEB-INF/jsp/moduls/includes.jsp" 
%>
<div class="clear"></div>
<div class="spacer"></div>

<div>
<br/>
<center>
<img src="<c:url value="/img/app-logo.png"/>"  alt="${fullname}"/>

<br/>
<br/>

PAGINA PUBLICA <br/>

This page is generated automatically. Please edit.

<br/>
<br/>
<div>
<a href="https://governdigital.fundaciobit.org" target="_blank">
<img src="<c:url value="/img/fundaciobit.png"/>"  alt="Fundació Bit"/>
</a>
</div>
<br/>
</center>
 
</div>

<br/>

<c:if test="${dollar}{${prefixLowercase}:isDesenvolupament()}">

Aquest informaci&oacute; es mostra ja que la propietat ${package}.development &eacute;s true<br/>
<br>

+ LOGIN ANONIM <br/>
+ Locale = <%=LocaleContextHolder.getLocale() %> <br/>
+ lang = ${dollar}{lang} <br/>
<br/>

</c:if>
