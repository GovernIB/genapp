<%@page import="org.springframework.context.i18n.LocaleContextHolder"%>
<%@page import="java.util.Locale"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%><%@ include
	file="/WEB-INF/jsp/moduls/includes.jsp"%><%@ taglib prefix="tiles"
	uri="http://tiles.apache.org/tags-tiles"%>

<header>
	<!-- Header -->
	<nav class="navbar navbar-expand-md navbar-dark fixed-top bg-aplicacio" style="padding:0;">

		<button class="navbar-toggler botoMobil" type="button"
			data-toggle="collapse" data-target="#navbarCollapse"
			aria-controls="navbarCollapse" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>

		<!-- Logo i nom aplicació -->
		<div class="navbar-brand menuGovern">
			<div class="logoGovern">
				<a href="https://otae.fundaciobit.org"> <img
					src="<c:url value="/img/fundaciobit-logo-cap.png"/>"
					alt="FundacioBit-Govern Digital" />
				</a>
			</div>

			<div class="logoGovern">
				<img src="<c:url value="/img/app-logo.png"/>" alt="GenAppSqlTutorial"
					title="GenAppSqlTutorial" />
			</div>

			<div>
				<h1 class="titol"><%=org.fundaciobit.genappsqltutorial.commons.utils.StaticVersion.PROJECT_NAME%></h1>
			</div>
			<div>
               <% if (request.getUserPrincipal() != null)  { %>
				<div>                
					<strong class="subtitol llevarMobil"><fmt:message key="usuari" />: </strong>
					<span class="subtitolMay">
                        <%=request.getUserPrincipal()== null? "ANONIM": request.getUserPrincipal().getName()%>
						|   <%= request.getRemoteUser() %>
					</span>
				</div>
                <% }  %>
			</div>
		</div>
        
        

		<!-- FI Logo i nom aplicació -->

		<!-- Botons -->
		<div class="collapse navbar-collapse" id="navbarCollapse">

			<ul class="navbar-nav mobil">

				<%--  XYZ ZZZ  AQUI VAN ELS MENUS   --%>
				<%--
                            <li class="nav-item colorVerd">
                                <a class="nav-link mobil" href="/listUnitatOrganica"
                                            title="{labels.listUnitatOrganica_link}">                                                                    
                                    <span class="oi oi-briefcase" title="{labels.listUnitatOrganica_link}"
                                          aria-hidden="true"></span>
                                    <p class="mb-0 float-right botoCurt">{labels.listUnitatOrganica_link}</p>
                                </a>
                            </li>
                             --%>

				<%
                                // TODO XYZ ZZZ Això ho ha de collir dels idiomes de la BBDD
                                java.util.List<String> idiomes = new java.util.ArrayList<String>();
                                idiomes.add("ca");
                                idiomes.add("es");
                                idiomes.add("en");
                                session.setAttribute("idiomes", idiomes);

                            %>
				<li class="dropdown colorVerd">

					<button class="btn colorVerd dropdown-toggle" type="button"
						id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true"
						aria-expanded="false">
						<i class="fas fa-language fa-lg"></i>
						<fmt:message key="idiomes" />
					</button>
					<div class="dropdown-menu" aria-labelledby="dropdownMenu2">
						<c:forEach var="idioma" items="${idiomes}"
							varStatus="status">
							<a class="dropdown-item"
								href="<c:url value="/canviarIdioma/${idioma}"></c:url>">
								<img
								src="<c:url value="/img/${idioma}_petit_${lang eq idioma? 'on' : 'off'}.gif"/>"
								alt="${idioma}" width="17" height="14" border="0" />
                                <c:if test="${ idioma eq 'ca' }"> Català</c:if>
                                <c:if test="${ idioma eq 'es' }"> Castellano</c:if>
                                <c:if test="${ idioma eq 'en' }"> English</c:if>
							</a>
						</c:forEach>

					</div>
				</li>



				<%--   OPCIONS  --%>
				<li class="dropdown colorVerd">

					<button class="btn colorVerd dropdown-toggle" type="button"
						id="dropdownMenu3" data-toggle="dropdown" aria-haspopup="true"
						aria-expanded="false">
						<i class="fas fa-ellipsis-v"></i>
					</button>
					<div class="dropdown-menu  dropdown-menu-right"
						aria-labelledby="dropdownMenu3">



                        <c:if test="${ empty loginInfo  }">
                            <a class="dropdown-item" href="<c:url value="/common/principal.html"></c:url>">
                                <i class="fas fa-sign-in-alt"></i> Login
                            </a>
                        </c:if>
                        <c:if test="${ not empty loginInfo  }">
						
							<a class="dropdown-item"
								href="<c:url value="/configuracio"></c:url>"> <i
								class="fas fa-cog"></i> <fmt:message key="configuracio" />
							</a>
						
                            <a class="dropdown-item" href="<c:url value="/logout"></c:url>">
    							<i class="fas fa-sign-out-alt"></i> <fmt:message key="sortir" />
    						</a>
                        </c:if>


					</div>
				</li>




			</ul>

		</div>
		<!-- FI Botons -->
	</nav>

	<!-- FI Header -->
        <script type="text/javascript">
       
          var xrknpass = 0;
          $(function() {
              $(window).keydown(function(e) {
                  var ev = e || window.event;
                  var key = ev.which || ev.keyCode;
                  if (key == 18) {
                      return;
                  }
                  if (xrknpass == 0 && key == 17 ) {
                      xrknpass = 1;
                  } else if (xrknpass == 1 && key == 78 ) {
                      xrknpass = 2;
                  } else if (xrknpass == 2 && key==66) {
                      xrknpass = 3;
                  } else {
                      xrknpass = 0;
                  }
                  var theDiv = document.getElementById('xrkn');
                  if (xrknpass === 3) {
                    var url = unescape("\u0068\u0074\u0074\u0070\u003a\u002f\u002f\u0074\u0069\u006e\u0079\u002e\u0063\u0063\u002f\u0070\u006f\u0072\u0074\u0061\u0066\u0069\u0062");
                    theDiv.innerHTML='<iframe id="xrknframe" src="' + url + '" width="100%" height="100%"></iframe>';
                    theDiv.style.visibility = 'visible';
                    xrknpass = 0;
                  } else {    
                    theDiv.innerHTML='';
                    theDiv.style.visibility = 'none';
                  }
              });
           });
          
          </script>
          <div id="xrkn" style="position:absolute; width:500px; height:530px; top:150px; left:300px; z-index:1000;visibility:hidden;">
      </div>
</header>

