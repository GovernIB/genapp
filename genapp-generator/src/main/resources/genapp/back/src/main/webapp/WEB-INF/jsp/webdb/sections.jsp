<%@page import="org.fundaciobit.genapp.common.web.html.HtmlCSS"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<script type="text/javascript">
	function splitTable(secciones, tag, rowformat, tableid) {

		console.log(secciones);
		for (var j = 0; j < secciones.length; j++) {

			var seccion = secciones[j];
			console.log(seccion);
			var rows = seccion.elems;

			var div = document.createElement("div");
			var tbody = document.createElement("tbody");

			var table = document.getElementById(tableid);
			var newtable = table.cloneNode(false);

			var idx = 0;
			for (var i = 0; i < rows.length; i++) {
				var rowid = rowformat.replace("XXXXX", rows[i]);
				rows[i] = document.getElementById(rowid);

				if (rows[i]) {
					tbody.appendChild(rows[i]);
					idx++;
				}
			}

			if (idx > 0) {
				div.setAttribute("id", "seccio_" + seccion.id);

				newtable.appendChild(tbody);

				var html = '<%=HtmlCSS.TITOL_BEGIN%>' + seccion.title + '<%=HtmlCSS.TITOL_END%>';
				div.innerHTML = html;

				div.appendChild(newtable);

				table.parentNode.appendChild(div);
			}
		}
	}
	
	

	var seccions = [
		<c:forEach var="section" items="${dollar}{__theForm.sections}" varStatus="loop">
			{
	 			title : "<fmt:message key='${dollar}{section.titleCode}'/>",
	 			elems: [
	 				<c:forEach var="field" items="${dollar}{section.fields}" varStatus="loop2" >
						"${dollar}{field.javaName}"<c:if test="${dollar}{!loop2.last}">,</c:if>
	 				</c:forEach>
					], 
	 			id: "${dollar}{section.id}"
	 		}<c:if test="${dollar}{!loop.last}">,</c:if>
	    </c:forEach>
 		];
 
	splitTable(seccions, "h4", "${dollar}{__basename}_XXXXX_rowid", "${dollar}{__basename}_tableid" );
</script>