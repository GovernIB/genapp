<#assign symbol_dollar = "$">
<%@page import="org.springframework.security.core.Authentication"
%><%@page import="org.springframework.context.i18n.LocaleContextHolder"
%><%@page import="org.springframework.security.core.context.SecurityContext"
%><%@page import="org.springframework.security.core.context.SecurityContextHolder"
%><%@ page language="java" 
%><%@ include file="/WEB-INF/jsp/moduls/includes.jsp" 
%>
<div class="clear"></div>
<div class="spacer"></div>

<div>
<br/>
<center>
<img src="<c:url value="/img/app-logo.png"/>"  alt="${fullname}" title="${fullname}"/>

<br/>
<br/>
This page is generated automatically. Please edit.

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
Username: ${dollar}{loginInfo.username}<br/>

&#36;{${prefixLowercase}:hasRole(<#list admin_virtual_roles_map as role_key,role_value>'${role_value?upper_case}'<#if role_key?is_last><#else>,</#if></#list>)}= ${dollar}{${prefixLowercase}:hasRole(<#list admin_virtual_roles_map as role_key,role_value>'${role_value?upper_case}'<#if role_key?is_last><#else>,</#if></#list>)}<br/>
&#36;{${prefixLowercase}:hasRole(<#list basic_virtual_roles_map as role_key,role_value>'${role_value?upper_case}'<#if role_key?is_last><#else>,</#if></#list>) }= ${dollar}{${prefixLowercase}:hasRole(<#list basic_virtual_roles_map as role_key,role_value>'${role_value?upper_case}'<#if role_key?is_last><#else>,</#if></#list>) }<br/>
Locale = <%=LocaleContextHolder.getLocale() %> <br/>
lang = ${symbol_dollar}{lang} <br/>
> UserInformation:<br/>
 <c:if test="${dollar}{not empty loginInfo.userInfo}">
	name= ${dollar}{loginInfo.userInfo.name} <br/> 
 	surname1= ${dollar}{loginInfo.userInfo.surname1} <br/>
 	surname2= ${dollar}{loginInfo.userInfo.surname2} <br/>
 	email= ${dollar}{loginInfo.userInfo.email} <br/>
 	nif= ${dollar}{loginInfo.userInfo.attributes["nif"]} <br/> 
  <br/>
</c:if>
<c:if test="${dollar}{empty loginInfo.userInfo}">
	Error carregant Plugin UserInfo. Revisar logs per mes informacio.<br/>
</c:if>

<c:if test="${dollar}{${prefixLowercase}:isDesenvolupament()}">
Only in Development Mode
</c:if>
  