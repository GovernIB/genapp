<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>
<un:useConstants var="OrdersFields" className="org.fundaciobit.genappsqltutorial.model.fields.OrdersFields"/>
  


        <c:forEach var="__entry" items="${__theFilterForm.additionalFields}">
        <c:if test="${ __entry.key < 0 && ((empty __entry.value.searchBy)? true : !gen:contains(__theFilterForm.hiddenFields, __entry.value.searchBy)) && ((empty __entry.value.groupBy )? true : !gen:contains(__theFilterForm.hiddenFields, __entry.value.groupBy ))}">
        <th>
        ${gas:getSortIconsAdditionalField(__theFilterForm,__entry.value)}
        </th>
        </c:if>
        </c:forEach>

        <c:if test="${!gen:contains(__theFilterForm.hiddenFields,OrdersFields.ORDERID)}">
        <th>${gas:getSortIcons(__theFilterForm,OrdersFields.ORDERID)}</th>
        </c:if>
        <c:if test="${!gen:contains(__theFilterForm.hiddenFields,OrdersFields.CUSTOMERID)}">
        <th>${gas:getSortIcons(__theFilterForm,OrdersFields.CUSTOMERID)}</th>
        </c:if>
        <c:if test="${!gen:contains(__theFilterForm.hiddenFields,OrdersFields.EMPLOYEEID)}">
        <th>${gas:getSortIcons(__theFilterForm,OrdersFields.EMPLOYEEID)}</th>
        </c:if>
        <c:if test="${!gen:contains(__theFilterForm.hiddenFields,OrdersFields.ORDERDATE)}">
        <th>${gas:getSortIcons(__theFilterForm,OrdersFields.ORDERDATE)}</th>
        </c:if>
        <c:if test="${!gen:contains(__theFilterForm.hiddenFields,OrdersFields.SHIPPERID)}">
        <th>${gas:getSortIcons(__theFilterForm,OrdersFields.SHIPPERID)}</th>
        </c:if>


        <c:forEach var="__entry" items="${__theFilterForm.additionalFields}">
        <c:if test="${ __entry.key >=0 && ((empty __entry.value.searchBy)? true : !gen:contains(__theFilterForm.hiddenFields, __entry.value.searchBy)) && ((empty __entry.value.groupBy )? true : !gen:contains(__theFilterForm.hiddenFields, __entry.value.groupBy ))}">
        <th>
        ${gas:getSortIconsAdditionalField(__theFilterForm,__entry.value)}
        </th>
        </c:if>
        </c:forEach>

