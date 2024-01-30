<%@page import="org.fundaciobit.pluginsib.login.springutils.PluginLoginController"%>
<%@page contentType="text/html;charset=UTF-8" language="java"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@include file="/WEB-INF/views/pages/taglib.jsp"%>
<c:set var="currentLang" value="${pageContext.response.locale.language}" />
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="${currentLang}" lang="${pageContext.response.locale.language}">
<%@include file="/WEB-INF/views/pages/head.jsp"%>
<body>
<script>
function doLogin() {
    var url = new URL(window.location.href);
    var urlbase = url.protocol + '//' + url.host;
    var newUrl = urlbase + '<%=request.getContextPath() + PluginLoginController.MAPPING_PRELOGIN + "?urlbase="%>'
                                + urlbase;
                        window.location.href = newUrl;
                    }
</script>
    <center>
        <br /> <br />
        <h1>
            <fmt:message key="titol.tab" />
        </h1>
        <br />
        <div class="row justify-content-center">
            <div>
                <img style="background: #000" src="<c:url value="/images/app-logo.png"/>" />
            </div>

            <%
            request.setAttribute("idiomes", new String[] { "ca", "es", "en" });
            %>

            <c:forEach var="idiomaID" items="${idiomes}" varStatus="status">

                <div style="text-align: center; margin-left: 20px">
                    <a href="?lang=${idiomaID}"> <img
                        src="<c:url value="/images/${idiomaID}_petit_${currentLang eq idiomaID? 'on' : 'off'}.gif"/>"
                        alt="${idiomaID}" style="margin-right: 0.5rem;" width="17" height="14" border="0" /> <br>
                        <small> ${currentLang eq idiomaID? '<b>' : ''} <fmt:message key="idioma.${idiomaID}" />
                            ${currentLang eq idiomaID? '</b>' : ''}
                    </small>
                    </a>
                </div>
            </c:forEach>

            <div style="text-align: center; margin-left: 20px">
                <sec:authorize access="!isAuthenticated()">

                    <a class="btn btn-primary btn-lg" href="javascript:doLogin()" role="button"><fmt:message
                            key="ferlogin" /></a>

                </sec:authorize>


                <sec:authorize access="isAuthenticated()">

                    <a class="btn btn-warning btn-lg" href="<c:url value="<%=PluginLoginController.MAPPING_LOGOUT%>"/>"
                        role="button"><fmt:message key="sortirlogin" /></a>
                </sec:authorize>
            </div>

        </div>

        </div>

        <br /> <br />
        <div>
            <sec:authorize access="!isAuthenticated()">
                <h2>
                    <i><fmt:message key="autenticat.no" /></i>
                </h2>
            </sec:authorize>

            <sec:authorize access="isAuthenticated()">
                <%
                Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

                request.getSession().setAttribute("principal", principal);
                %>

                <h2>
                    <i><fmt:message key="autenticat.si" /></i>
                </h2>

                <table class="table table-striped table-bordered" style="width: auto;">

                    <tr>
                        <td>Name</td>
                        <td>${principal.usuario.name}</td>
                    </tr>
                    <tr>
                        <td>Surname1</td>
                        <td>${principal.usuario.surname1}</td>
                    </tr>
                    <tr>
                        <td>Surname2</td>
                        <td>${principal.usuario.surname2}</td>
                    </tr>
                    <tr>
                        <td>NIF</td>
                        <td>${principal.usuario.administrationID}</td>
                    </tr>
                    <tr>
                        <td>Method</td>
                        <td>${principal.usuario.authenticationMethod}</td>
                    </tr>
                    <tr>
                        <td>LevelAut</td>
                        <td>${principal.usuario.qaa}</td>
                    </tr>
                    <tr>
                        <td>identityProvider</td>
                        <td>${principal.usuario.identityProvider}</td>
                    </tr>
                    <tr>
                        <td>EsEmpresa</td>
                        <td>${principal.usuario.business}</td>
                    </tr>
                </table>



                <c:set var="userRepresentant" value="${principal.usuario.representative}" />
                <c:if test="${not empty userRepresentant}">
                    <h2>
                        <i><fmt:message key="representant" /></i>
                    </h2>
                    <table class="table table-striped table-bordered" style="width: auto;">
                        <tr>
                            <td>representant - Name</td>
                            <td>${userRepresentant.name}</td>
                        </tr>
                        <tr>
                            <td>representant - Surname1</td>
                            <td>${userRepresentant.surname1}</td>
                        </tr>
                        <tr>
                            <td>representant - Surname2</td>
                            <td>${userRepresentant.surname2}</td>
                        </tr>
                        <tr>
                            <td>representant - DNI</td>
                            <td>${userRepresentant.administrationID}</td>
                        </tr>
                    </table>
                </c:if>

                </table>
            </sec:authorize>
        </div>
    </center>
</body>
</html>