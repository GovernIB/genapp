      <%--  CHECK DE SELECCIO MULTIPLE  --%>
      <c:if test="${__theFilterForm.visibleMultipleSelection}">
      <td>
       <form:checkbox path="selectedItems" value="${assignatura.assignaturaID}"/>
       &nbsp;
      </td>
      </c:if>
