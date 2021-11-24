  <c:if test="${empty ordersItems}">
     <%@include file="ordersListEmpty.jsp" %>

  </c:if>
  
  <c:if test="${not empty ordersItems}">

  <div class="row" style="margin-left: 0px;">
  <table class="table table-sm table-bordered table-striped table-responsive" style="width:auto;"> 
    <thead>
      <tr>

          <%@include file="ordersListCoreHeaderMultipleSelect.jsp" %>

          <%@include file="ordersListCoreHeader.jsp" %>

          <%-- ADD HERE NEW COLUMNS HEADER  --%>

          <%@include file="ordersListButtonsHeader.jsp" %>

      </tr>
    </thead>
    <tbody>

      <c:forEach var="orders" items="${ordersItems}">

        <tr id="orders_rowid_${orders.orderid}">
          <%@include file="ordersListCoreMultipleSelect.jsp" %>

          <%@include file="ordersListCoreContent.jsp" %>

          <%--  ADD HERE NEW COLUMNS CONTENT --%>


          <%@include file="ordersListButtons.jsp" %>

            
        </tr>

      </c:forEach>

    </tbody>
  </table>
  </div>
  </c:if>
  
