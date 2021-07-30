<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>
<un:useConstants var="EmployeesFields" className="org.fundaciobit.genappsqltutorial.model.fields.EmployeesFields"/>
  


        <c:forEach var="__entry" items="${__theFilterForm.additionalFields}">
        <c:if test="${ __entry.key < 0 && ((empty __entry.value.searchBy)? true : !gen:contains(__theFilterForm.hiddenFields, __entry.value.searchBy)) && ((empty __entry.value.groupBy )? true : !gen:contains(__theFilterForm.hiddenFields, __entry.value.groupBy ))}">
        <th>
        ${gas:getSortIconsAdditionalField(__theFilterForm,__entry.value)}
        </th>
        </c:if>
        </c:forEach>

        <c:if test="${!gen:contains(__theFilterForm.hiddenFields,EmployeesFields.EMPLOYEEID)}">
        <th>${gas:getSortIcons(__theFilterForm,EmployeesFields.EMPLOYEEID)}</th>
        </c:if>
        <c:if test="${!gen:contains(__theFilterForm.hiddenFields,EmployeesFields.LASTNAME)}">
        <th>${gas:getSortIcons(__theFilterForm,EmployeesFields.LASTNAME)}</th>
        </c:if>
        <c:if test="${!gen:contains(__theFilterForm.hiddenFields,EmployeesFields.FIRSTNAME)}">
        <th>${gas:getSortIcons(__theFilterForm,EmployeesFields.FIRSTNAME)}</th>
        </c:if>
        <c:if test="${!gen:contains(__theFilterForm.hiddenFields,EmployeesFields.BIRTHDATE)}">
        <th>${gas:getSortIcons(__theFilterForm,EmployeesFields.BIRTHDATE)}</th>
        </c:if>
        <c:if test="${!gen:contains(__theFilterForm.hiddenFields,EmployeesFields.PHOTO)}">
        <th>${gas:getSortIcons(__theFilterForm,EmployeesFields.PHOTO)}</th>
        </c:if>
        <c:if test="${!gen:contains(__theFilterForm.hiddenFields,EmployeesFields.NOTES)}">
        <th>${gas:getSortIcons(__theFilterForm,EmployeesFields.NOTES)}</th>
        </c:if>


        <c:forEach var="__entry" items="${__theFilterForm.additionalFields}">
        <c:if test="${ __entry.key >=0 && ((empty __entry.value.searchBy)? true : !gen:contains(__theFilterForm.hiddenFields, __entry.value.searchBy)) && ((empty __entry.value.groupBy )? true : !gen:contains(__theFilterForm.hiddenFields, __entry.value.groupBy ))}">
        <th>
        ${gas:getSortIconsAdditionalField(__theFilterForm,__entry.value)}
        </th>
        </c:if>
        </c:forEach>

