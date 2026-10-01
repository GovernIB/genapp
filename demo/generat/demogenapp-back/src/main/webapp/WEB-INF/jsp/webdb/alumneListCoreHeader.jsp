<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>
<un:useConstants var="AlumneFields" className="org.fundaciobit.demogenapp.model.fields.AlumneFields"/>
  


        <c:forEach var="__entry" items="${__theFilterForm.additionalFields}">
        <c:if test="${ __entry.key < 0 && ((empty __entry.value.searchBy)? true : !gen:contains(__theFilterForm.hiddenFields, __entry.value.searchBy)) && ((empty __entry.value.groupBy )? true : !gen:contains(__theFilterForm.hiddenFields, __entry.value.groupBy ))}">
        <th>
        ${dem:getSortIconsAdditionalField(__theFilterForm,__entry.value)}
        </th>
        </c:if>
        </c:forEach>

        <c:if test="${!gen:contains(__theFilterForm.hiddenFields,AlumneFields.ALUMNEID)}">
        <th>${dem:getSortIcons(__theFilterForm,AlumneFields.ALUMNEID)}</th>
        </c:if>
        <c:if test="${!gen:contains(__theFilterForm.hiddenFields,AlumneFields.NOM)}">
        <th>${dem:getSortIcons(__theFilterForm,AlumneFields.NOM)}</th>
        </c:if>
        <c:if test="${!gen:contains(__theFilterForm.hiddenFields,AlumneFields.IDIOMAID)}">
        <th>${dem:getSortIcons(__theFilterForm,AlumneFields.IDIOMAID)}</th>
        </c:if>
        <c:if test="${!gen:contains(__theFilterForm.hiddenFields,AlumneFields.DATANAIXEMENT)}">
        <th>${dem:getSortIcons(__theFilterForm,AlumneFields.DATANAIXEMENT)}</th>
        </c:if>
        <c:if test="${!gen:contains(__theFilterForm.hiddenFields,AlumneFields.SEXE)}">
        <th>${dem:getSortIcons(__theFilterForm,AlumneFields.SEXE)}</th>
        </c:if>
        <c:if test="${!gen:contains(__theFilterForm.hiddenFields,AlumneFields.ACTIU)}">
        <th>${dem:getSortIcons(__theFilterForm,AlumneFields.ACTIU)}</th>
        </c:if>
        <c:if test="${!gen:contains(__theFilterForm.hiddenFields,AlumneFields.DARRERACCES)}">
        <th>${dem:getSortIcons(__theFilterForm,AlumneFields.DARRERACCES)}</th>
        </c:if>
        <c:if test="${!gen:contains(__theFilterForm.hiddenFields,AlumneFields.FOTOID)}">
        <th>${dem:getSortIcons(__theFilterForm,AlumneFields.FOTOID)}</th>
        </c:if>
        <c:if test="${!gen:contains(__theFilterForm.hiddenFields,AlumneFields.TITOLACADEMICID)}">
        <th>${dem:getSortIcons(__theFilterForm,AlumneFields.TITOLACADEMICID)}</th>
        </c:if>
        <c:if test="${!gen:contains(__theFilterForm.hiddenFields,AlumneFields.DESPERTADOR)}">
        <th>${dem:getSortIcons(__theFilterForm,AlumneFields.DESPERTADOR)}</th>
        </c:if>
        <c:if test="${!gen:contains(__theFilterForm.hiddenFields,AlumneFields.PAGINAWEB)}">
        <th>${dem:getSortIcons(__theFilterForm,AlumneFields.PAGINAWEB)}</th>
        </c:if>
        <c:if test="${!gen:contains(__theFilterForm.hiddenFields,AlumneFields.DESCRIPCIO)}">
        <th>${dem:getSortIcons(__theFilterForm,AlumneFields.DESCRIPCIO)}</th>
        </c:if>


        <c:forEach var="__entry" items="${__theFilterForm.additionalFields}">
        <c:if test="${ __entry.key >=0 && ((empty __entry.value.searchBy)? true : !gen:contains(__theFilterForm.hiddenFields, __entry.value.searchBy)) && ((empty __entry.value.groupBy )? true : !gen:contains(__theFilterForm.hiddenFields, __entry.value.groupBy ))}">
        <th>
        ${dem:getSortIconsAdditionalField(__theFilterForm,__entry.value)}
        </th>
        </c:if>
        </c:forEach>

