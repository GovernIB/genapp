      <%--  CHECK DE SELECCIO MULTIPLE  --%>
      <c:if test="${__theFilterForm.visibleMultipleSelection}">
      <td>
       <form:checkbox path="selectedItems" value="${orderDetails.orderdetailid}"/>
       &nbsp;
      </td>
      </c:if>

