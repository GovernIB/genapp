<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>
<un:useConstants var="SuppliersFields" className="org.fundaciobit.genappsqltutorial.model.fields.SuppliersFields"/>
  


        <c:forEach var="__entry" items="${__theFilterForm.additionalFields}">
        <c:if test="${ __entry.key < 0 && ((empty __entry.value.searchBy)? true : !gen:contains(__theFilterForm.hiddenFields, __entry.value.searchBy)) && ((empty __entry.value.groupBy )? true : !gen:contains(__theFilterForm.hiddenFields, __entry.value.groupBy ))}">
        <th>
        ${gas:getSortIconsAdditionalField(__theFilterForm,__entry.value)}
        </th>
        </c:if>
        </c:forEach>

        <c:if test="${!gen:contains(__theFilterForm.hiddenFields,SuppliersFields.SUPPLIERID)}">
        <th>${gas:getSortIcons(__theFilterForm,SuppliersFields.SUPPLIERID)}</th>
        </c:if>
        <c:if test="${!gen:contains(__theFilterForm.hiddenFields,SuppliersFields.SUPPLIERNAME)}">
        <th>${gas:getSortIcons(__theFilterForm,SuppliersFields.SUPPLIERNAME)}</th>
        </c:if>
        <c:if test="${!gen:contains(__theFilterForm.hiddenFields,SuppliersFields.CONTACTNAME)}">
        <th>${gas:getSortIcons(__theFilterForm,SuppliersFields.CONTACTNAME)}</th>
        </c:if>
        <c:if test="${!gen:contains(__theFilterForm.hiddenFields,SuppliersFields.ADDRESS)}">
        <th>${gas:getSortIcons(__theFilterForm,SuppliersFields.ADDRESS)}</th>
        </c:if>
        <c:if test="${!gen:contains(__theFilterForm.hiddenFields,SuppliersFields.CITY)}">
        <th>${gas:getSortIcons(__theFilterForm,SuppliersFields.CITY)}</th>
        </c:if>
        <c:if test="${!gen:contains(__theFilterForm.hiddenFields,SuppliersFields.POSTALCODE)}">
        <th>${gas:getSortIcons(__theFilterForm,SuppliersFields.POSTALCODE)}</th>
        </c:if>
        <c:if test="${!gen:contains(__theFilterForm.hiddenFields,SuppliersFields.COUNTRY)}">
        <th>${gas:getSortIcons(__theFilterForm,SuppliersFields.COUNTRY)}</th>
        </c:if>
        <c:if test="${!gen:contains(__theFilterForm.hiddenFields,SuppliersFields.PHONE)}">
        <th>${gas:getSortIcons(__theFilterForm,SuppliersFields.PHONE)}</th>
        </c:if>


        <c:forEach var="__entry" items="${__theFilterForm.additionalFields}">
        <c:if test="${ __entry.key >=0 && ((empty __entry.value.searchBy)? true : !gen:contains(__theFilterForm.hiddenFields, __entry.value.searchBy)) && ((empty __entry.value.groupBy )? true : !gen:contains(__theFilterForm.hiddenFields, __entry.value.groupBy ))}">
        <th>
        ${gas:getSortIconsAdditionalField(__theFilterForm,__entry.value)}
        </th>
        </c:if>
        </c:forEach>

