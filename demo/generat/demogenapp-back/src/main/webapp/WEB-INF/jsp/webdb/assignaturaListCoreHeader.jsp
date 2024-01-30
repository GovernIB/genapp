<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>
<un:useConstants var="AssignaturaFields" className="org.fundaciobit.demogenapp.model.fields.AssignaturaFields"/>
  


        <c:forEach var="__entry" items="${__theFilterForm.additionalFields}">
        <c:if test="${ __entry.key < 0 && ((empty __entry.value.searchBy)? true : !gen:contains(__theFilterForm.hiddenFields, __entry.value.searchBy)) && ((empty __entry.value.groupBy )? true : !gen:contains(__theFilterForm.hiddenFields, __entry.value.groupBy ))}">
        <th>
        ${dem:getSortIconsAdditionalField(__theFilterForm,__entry.value)}
        </th>
        </c:if>
        </c:forEach>

        <c:if test="${!gen:contains(__theFilterForm.hiddenFields,AssignaturaFields.ASSIGNATURAID)}">
        <th>${dem:getSortIcons(__theFilterForm,AssignaturaFields.ASSIGNATURAID)}</th>
        </c:if>
        <c:if test="${!gen:contains(__theFilterForm.hiddenFields,AssignaturaFields.NOM)}">
        <th>${dem:getSortIcons(__theFilterForm,AssignaturaFields.NOM)}</th>
        </c:if>
        <c:if test="${!gen:contains(__theFilterForm.hiddenFields,AssignaturaFields.CREDITS)}">
        <th>${dem:getSortIcons(__theFilterForm,AssignaturaFields.CREDITS)}</th>
        </c:if>
        <c:if test="${!gen:contains(__theFilterForm.hiddenFields,AssignaturaFields.DIASETMANA)}">
        <th>${dem:getSortIcons(__theFilterForm,AssignaturaFields.DIASETMANA)}</th>
        </c:if>
        <c:if test="${!gen:contains(__theFilterForm.hiddenFields,AssignaturaFields.HORA)}">
        <th>${dem:getSortIcons(__theFilterForm,AssignaturaFields.HORA)}</th>
        </c:if>
        <c:if test="${!gen:contains(__theFilterForm.hiddenFields,AssignaturaFields.DESCRIPCIO)}">
        <th>${dem:getSortIcons(__theFilterForm,AssignaturaFields.DESCRIPCIO)}</th>
        </c:if>


        <c:forEach var="__entry" items="${__theFilterForm.additionalFields}">
        <c:if test="${ __entry.key >=0 && ((empty __entry.value.searchBy)? true : !gen:contains(__theFilterForm.hiddenFields, __entry.value.searchBy)) && ((empty __entry.value.groupBy )? true : !gen:contains(__theFilterForm.hiddenFields, __entry.value.groupBy ))}">
        <th>
        ${dem:getSortIconsAdditionalField(__theFilterForm,__entry.value)}
        </th>
        </c:if>
        </c:forEach>

