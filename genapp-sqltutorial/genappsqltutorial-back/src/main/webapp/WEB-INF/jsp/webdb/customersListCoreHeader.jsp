<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>
<un:useConstants var="CustomersFields" className="org.fundaciobit.genappsqltutorial.model.fields.CustomersFields"/>
  


        <c:forEach var="__entry" items="${__theFilterForm.additionalFields}">
        <c:if test="${ __entry.key < 0 && ((empty __entry.value.searchBy)? true : !gen:contains(__theFilterForm.hiddenFields, __entry.value.searchBy)) && ((empty __entry.value.groupBy )? true : !gen:contains(__theFilterForm.hiddenFields, __entry.value.groupBy ))}">
        <th>
        ${gas:getSortIconsAdditionalField(__theFilterForm,__entry.value)}
        </th>
        </c:if>
        </c:forEach>

        <c:if test="${!gen:contains(__theFilterForm.hiddenFields,CustomersFields.CUSTOMERID)}">
        <th>${gas:getSortIcons(__theFilterForm,CustomersFields.CUSTOMERID)}</th>
        </c:if>
        <c:if test="${!gen:contains(__theFilterForm.hiddenFields,CustomersFields.CUSTOMERNAME)}">
        <th>${gas:getSortIcons(__theFilterForm,CustomersFields.CUSTOMERNAME)}</th>
        </c:if>
        <c:if test="${!gen:contains(__theFilterForm.hiddenFields,CustomersFields.CONTACTNAME)}">
        <th>${gas:getSortIcons(__theFilterForm,CustomersFields.CONTACTNAME)}</th>
        </c:if>
        <c:if test="${!gen:contains(__theFilterForm.hiddenFields,CustomersFields.ADDRESS)}">
        <th>${gas:getSortIcons(__theFilterForm,CustomersFields.ADDRESS)}</th>
        </c:if>
        <c:if test="${!gen:contains(__theFilterForm.hiddenFields,CustomersFields.CITY)}">
        <th>${gas:getSortIcons(__theFilterForm,CustomersFields.CITY)}</th>
        </c:if>
        <c:if test="${!gen:contains(__theFilterForm.hiddenFields,CustomersFields.COUNTRY)}">
        <th>${gas:getSortIcons(__theFilterForm,CustomersFields.COUNTRY)}</th>
        </c:if>
        <c:if test="${!gen:contains(__theFilterForm.hiddenFields,CustomersFields.POSTALCODE)}">
        <th>${gas:getSortIcons(__theFilterForm,CustomersFields.POSTALCODE)}</th>
        </c:if>


        <c:forEach var="__entry" items="${__theFilterForm.additionalFields}">
        <c:if test="${ __entry.key >=0 && ((empty __entry.value.searchBy)? true : !gen:contains(__theFilterForm.hiddenFields, __entry.value.searchBy)) && ((empty __entry.value.groupBy )? true : !gen:contains(__theFilterForm.hiddenFields, __entry.value.groupBy ))}">
        <th>
        ${gas:getSortIconsAdditionalField(__theFilterForm,__entry.value)}
        </th>
        </c:if>
        </c:forEach>

