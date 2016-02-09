<%@page import="org.springframework.context.i18n.LocaleContextHolder"%>
<%@page import="java.util.Locale"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"
%><%@ include file="/WEB-INF/jsp/moduls/includes.jsp"
%><%@ taglib prefix="tiles"  uri="http://tiles.apache.org/tags-tiles" 
%>

<div class="row-fluid container nav-container">

  <div class="logo pull-left" >
    <a href="Fundacio Bit (Your organization)" target="_blank">    
    <img src="<c:url value="/img/fundaciobit-logo-cap.png"/>"  title="Fundacio Bit" alt="Fundacio Bit" />
    </a>
  </div>

  <div class="aplication-logo pull-left">
    <img src="<c:url value="/img/app-logo.png"/>"   alt="${fullname}" title="${fullname}"/>
  </div>
  
  <div class="pull-right main-menu">
    <ul class="user-nav pull-right dropdown">

      
       
      <li>
         <a class="dropdown-toggle" data-toggle="dropdown" href="#">
           <i class="icon-home icon-white"></i>List 1<span class="caret"></span>
         </a>
  
        <ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">
        <li><a class="" href="#"><i> Current Item </i> </a></li>
        
        <li><a tabindex="-1" href="<c:url value="value1"/>"> Item 1</a></li>
        
        <li><a tabindex="-1" href="<c:url value="value1"/>"> Item 2</a></li>
        
        </ul>
      </li>

      <li>
         Organitzacio
      </li>


      
      <li>
        <a class="dropdown-toggle" data-toggle="dropdown" href="#"> 
          <i class="icon-user icon-white"></i>
          Nom de l'usuari: <%=request.getUserPrincipal().getName()%>  |  <%= request.getRemoteUser() %>
          <span class="caret"></span>
        </a>
        <ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">
          <li><a tabindex="-1" href="#">Configuració</a></li>
          <li><a tabindex="-1" href="<c:url value="/j_spring_security_logout" />">Sortir</a></li>
        </ul>
      </li>

    </ul>
    <div class="clearfix"></div>
<%
    java.util.List<String> idiomes = new java.util.ArrayList<String>();
    idiomes.add("ca");
    idiomes.add("es");
    idiomes.add("en");
    session.setAttribute("idiomes", idiomes);

%>
    <div style="text-align: left; width: ${r"${(17 + 5)* fn:length(idiomes)}"}px;" class="pull-right">
    <c:forEach  var="idioma"  items="${r"${idiomes}"}" varStatus="status">
       <a href="<c:url value="${r"${urlActual}"}"><c:param name="lang" value="${r"${idioma}"}"/></c:url>">
          <img src="<c:url value="/img/${r"${idioma}"}_petit_${r"${lang eq idioma? 'on' : 'off'"}}.gif"/>" alt="${r"${idioma}"}" width="17"
            height="14" border="0" />
       </a>
    </c:forEach>

    </div>
  </div>

</div>
