  <c:if test="${empty employeesItems}">
     <%@include file="employeesListEmpty.jsp" %>

  </c:if>
  
  <c:if test="${not empty employeesItems}">

  <div class="row" style="margin-left: 0px;">
  <table class="table table-sm table-bordered table-striped table-genapp" style="width:auto;"> 
    <thead>
      <tr>

          <%@include file="employeesListCoreHeaderMultipleSelect.jsp" %>

          <%@include file="employeesListCoreHeader.jsp" %>

          <%-- ADD HERE NEW COLUMNS HEADER  --%>

          <%@include file="employeesListButtonsHeader.jsp" %>

      </tr>
    </thead>
    <tbody>

      <c:forEach var="employees" items="${employeesItems}">

        <tr id="employees_rowid_${employees.employeeid}">
          <%@include file="employeesListCoreMultipleSelect.jsp" %>

          <%@include file="employeesListCoreContent.jsp" %>

          <%--  ADD HERE NEW COLUMNS CONTENT --%>


          <%@include file="employeesListButtons.jsp" %>

            
        </tr>

      </c:forEach>

    </tbody>
  </table>
  </div>
  </c:if>
  
