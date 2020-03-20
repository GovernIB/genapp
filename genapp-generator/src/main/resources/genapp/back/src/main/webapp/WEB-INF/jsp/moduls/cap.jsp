<#assign symbol_dollar = "$">
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
				<a href="http://www.fundaciobit.org"> <img
					src="<c:url value="/img/fundaciobit-logo-cap.png"/>"
					alt="FundacioBit-Govern Digital" />
				</a>
			</div>

			<div class="logoGovern">
				<img src="<c:url value="/img/app-logo.png"/>" alt="${fullname}"
					title="${fullname}" />
			</div>

			<div>
				<h1 class="titol"><%=${package}.utils.StaticVersion.PROJECT_NAME%></h1>
			</div>
			<div>
				<div>
					<strong class="subtitol llevarMobil"><fmt:message
							key="usuari" />: </strong> <span class="subtitolMay"> <%=request.getUserPrincipal().getName()%>
						| <%= request.getRemoteUser() %>
					</span>
				</div>
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
						<c:forEach var="idioma" items="${symbol_dollar}{idiomes}"
							varStatus="status">
							<a class="dropdown-item"
								href="<c:url value="/canviarIdioma/${symbol_dollar}{idioma}"></c:url>">
								<img
								src="<c:url value="/img/${symbol_dollar}{idioma}_petit_${symbol_dollar}{lang eq idioma? 'on' : 'off'}.gif"/>"
								alt="${symbol_dollar}{idioma}" width="17" height="14" border="0" />
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

						<a class="dropdown-item"
							href="<c:url value="/configuracio"></c:url>"> <i
							class="fas fa-cog"></i> <fmt:message key="configuracio" />
						</a> <a class="dropdown-item" href="<c:url value="/logout"></c:url>">
							<i class="fas fa-sign-out-alt"></i> <fmt:message key="sortir" />
						</a>


					</div>
				</li>




			</ul>

		</div>
		<!-- FI Botons -->
	</nav>

	<!-- FI Header -->
</header>

