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
<img src="<c:url value="/img/app-logo.png"/>"  alt="GenApp SQL Tutorial" title="GenApp SQL Tutorial"/>

<br/>
<br/>

<b>Tutorial de GenQL</b><br/>
<br/>

Llenguatge de Consulta de BBDD utilitzat per GenApp.<br>
Aquest es basa en Classes de Java cosa que fa que els errors en les consultes, modificacions i esborrats siguin mínims. 

<br/>
<br/>
<table border="0" >
<tr>
<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
<td valign="top">
<a href="http://blog.fundaciobit.org/category/admindigital/" target="_blank">
<img src="<c:url value="/img/fundaciobit.png"/>"  alt="Fundació Bit" title="Fundació Bit"/>
</a>
</td>
</tr>
</table>
<br/>
</center>
 
</div>

<br/>
<br/>

<c:if test="${gas:isDesenvolupament()}">
Only in Development Mode

LOGIN ANONIM <br/>
Locale = <%=LocaleContextHolder.getLocale() %> <br/>
lang = ${lang} <br/>
</c:if>
