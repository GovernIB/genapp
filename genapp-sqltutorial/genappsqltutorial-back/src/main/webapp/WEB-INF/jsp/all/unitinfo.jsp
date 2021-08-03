
<%@page import="org.springframework.security.core.Authentication"
%><%@page import="org.springframework.context.i18n.LocaleContextHolder"
%><%@ page language="java" 
%><%@ include file="/WEB-INF/jsp/moduls/includes.jsp" 
%>
<div class="clear"></div>
<div class="spacer"></div>

<h2>${unitInfo.index }: ${unitInfo.titol} </h2>

<ul>
 <%--   <li> Index: <b>${unitInfo.index }</b> </li>
    <li> T&iacute;tol: <b>${unitInfo.titol}</b> </li>  
    <li> Descripci&oacute;:  </li>
  
    <li> Nom Curt: <b>${unitInfo.nomCurt }</b> </li>
    <li> Nom de la Classe: <b>${unitInfo.classeNom }</b> </li>
     --%>
    <li> Classe: <b>${unitInfo.classe }</b> </li>
    <li> URL W3schools: <b><a href="${unitInfo.url}" target="_blank">${unitInfo.url}</a></b> </li>
</ul>

<b>${unitInfo.descripcio }</b>

<c:forEach var="p" items="${unitInfo.parts}">
    <br/>
    <hr/>

    <h4><c:out value="${p.titol}"></c:out></h4>
    <p style="box-sizing: border-box;color: rgb(0, 0, 0);font-family: Verdana, sans-serif;font-size: 15px;line-height: 22.5px;margin-bottom: 18px;">
    <c:out value="${p.descripcio}"></c:out>
    </p>
    SQL: <code>${p.sql}</code><br/>
    GenQL:<br/>
    <textarea rows="4" cols="90">${p.sourcecode}</textarea>
    <br/>
    <br/>
    <button class="btn btn-primary" onclick="alert('hola')">Executar test</button>     
    <br/>
</c:forEach>
