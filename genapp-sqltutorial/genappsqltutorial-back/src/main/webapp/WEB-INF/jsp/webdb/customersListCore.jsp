  <c:if test="${empty customersItems}">
     <%@include file="customersListEmpty.jsp" %>

  </c:if>
  
  <c:if test="${not empty customersItems}">

  <div class="row" style="margin-left: 0px;">
  <table class="table table-sm table-bordered table-striped table-genapp" style="width:auto;"> 
    <thead>
      <tr>

          <%@include file="customersListCoreHeaderMultipleSelect.jsp" %>

          <%@include file="customersListCoreHeader.jsp" %>

          <%-- ADD HERE NEW COLUMNS HEADER  --%>

          <%@include file="customersListButtonsHeader.jsp" %>

      </tr>
    </thead>
    <tbody>

      <c:forEach var="customers" items="${customersItems}">

        <tr id="customers_rowid_${customers.customerid}">
          <%@include file="customersListCoreMultipleSelect.jsp" %>

          <%@include file="customersListCoreContent.jsp" %>

          <%--  ADD HERE NEW COLUMNS CONTENT --%>


          <%@include file="customersListButtons.jsp" %>

            
        </tr>

      </c:forEach>

    </tbody>
  </table>
  </div>
  </c:if>
  
