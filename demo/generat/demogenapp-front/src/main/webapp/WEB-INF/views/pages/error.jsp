<%@page import="java.io.PrintWriter"%>
<%@page import="java.io.StringWriter"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" pageEncoding="UTF-8"%>
<%@ page import="org.fundaciobit.genapp.common.web.i18n.I18NUtils"%>
<%@ page import="org.apache.log4j.Logger"%>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder"%>
<%@include file="/WEB-INF/views/pages/taglib.jsp"%>
<%!protected final Logger log = Logger.getLogger(getClass());%>
<%
// Página d'error que mostra per pantalla i amb format els errors que es produeixen.

// Definim les etiquetes que mostram traduides.
String etiquetaBoto = I18NUtils.tradueix("error.jsp.tornarprincipal");
String titolPagina = I18NUtils.tradueix("error.jsp.pagina");
String detallError = I18NUtils.tradueix("error.jsp.detall");
String veureDetall = I18NUtils.tradueix("error.jsp.veuredetall");

String msg;

String stackTrace = null;

try {

	log.error(" ===  ENTRA ERROR.JSP (" + request.getAttribute("error") + request.getSession() + ")");

	log.error("LANNNNNNG: " + LocaleContextHolder.getLocale().toString());

	String e = (String) request.getAttribute("error");
	if (response.getStatus() == 500 && exception != null) {
		msg = exception.getMessage();

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		exception.printStackTrace(pw);

		stackTrace = sw.toString();

	} else {
		msg = I18NUtils.tradueix("error.jsp." + response.getStatus());
	}

} catch (Throwable th) {
	msg = "Error processant un error:" + th.getMessage();
	log.error(msg, th);
}

request.setAttribute("msg", msg);
request.setAttribute("stacktrace", stackTrace);
%>

<html>
<%@include file="/WEB-INF/views/pages/head.jsp"%>
<body>

    <script type="text/javascript">
                    function tornaEnrera(path) {
                        window.location.href = "/";
                    }
                </script>


    <div class="alert alert-danger" role="alert">

        <div>
            <h4><%=titolPagina%></h4>
        </div>
        <br />
        <div>
            <b>Error:</b>${msg}</div>
        <b>StackTrace:</b><br>
        <textarea row="50" cols="120"> ${stacktrace}</textarea>

        <div>
            <br /> <br />

            <!-- Mostram el botó de tornar a principal -->
            <a class="btn btn-primary" role="button" href="<c:url value="/"/>"><%=etiquetaBoto%></a>

        </div>
    </div>
</body>

</html>
