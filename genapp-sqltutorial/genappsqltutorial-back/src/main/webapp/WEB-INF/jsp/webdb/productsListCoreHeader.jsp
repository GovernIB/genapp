<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>
<un:useConstants var="ProductsFields" className="org.fundaciobit.genappsqltutorial.model.fields.ProductsFields"/>
  


        <c:forEach var="__entry" items="${__theFilterForm.additionalFields}">
        <c:if test="${ __entry.key < 0 && ((empty __entry.value.searchBy)? true : !gen:contains(__theFilterForm.hiddenFields, __entry.value.searchBy)) && ((empty __entry.value.groupBy )? true : !gen:contains(__theFilterForm.hiddenFields, __entry.value.groupBy ))}">
        <th>
        ${gas:getSortIconsAdditionalField(__theFilterForm,__entry.value)}
        </th>
        </c:if>
        </c:forEach>

        <c:if test="${!gen:contains(__theFilterForm.hiddenFields,ProductsFields.PRODUCTID)}">
        <th>${gas:getSortIcons(__theFilterForm,ProductsFields.PRODUCTID)}</th>
        </c:if>
        <c:if test="${!gen:contains(__theFilterForm.hiddenFields,ProductsFields.PRODUCTNAME)}">
        <th>${gas:getSortIcons(__theFilterForm,ProductsFields.PRODUCTNAME)}</th>
        </c:if>
        <c:if test="${!gen:contains(__theFilterForm.hiddenFields,ProductsFields.SUPPLIERID)}">
        <th>${gas:getSortIcons(__theFilterForm,ProductsFields.SUPPLIERID)}</th>
        </c:if>
        <c:if test="${!gen:contains(__theFilterForm.hiddenFields,ProductsFields.CATEGORYID)}">
        <th>${gas:getSortIcons(__theFilterForm,ProductsFields.CATEGORYID)}</th>
        </c:if>
        <c:if test="${!gen:contains(__theFilterForm.hiddenFields,ProductsFields.UNIT)}">
        <th>${gas:getSortIcons(__theFilterForm,ProductsFields.UNIT)}</th>
        </c:if>
        <c:if test="${!gen:contains(__theFilterForm.hiddenFields,ProductsFields.PRICE)}">
        <th>${gas:getSortIcons(__theFilterForm,ProductsFields.PRICE)}</th>
        </c:if>


        <c:forEach var="__entry" items="${__theFilterForm.additionalFields}">
        <c:if test="${ __entry.key >=0 && ((empty __entry.value.searchBy)? true : !gen:contains(__theFilterForm.hiddenFields, __entry.value.searchBy)) && ((empty __entry.value.groupBy )? true : !gen:contains(__theFilterForm.hiddenFields, __entry.value.groupBy ))}">
        <th>
        ${gas:getSortIconsAdditionalField(__theFilterForm,__entry.value)}
        </th>
        </c:if>
        </c:forEach>

