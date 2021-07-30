  <c:if test="${empty orderDetailsItems}">
     <%@include file="orderDetailsListEmpty.jsp" %>

  </c:if>
  
  <c:if test="${not empty orderDetailsItems}">

  <div class="row" style="margin-left: 0px;">
  <table class="table table-condensed table-bordered table-striped" style="width:auto;"> 
    <thead>
      <tr>

          <%@include file="orderDetailsListCoreHeaderMultipleSelect.jsp" %>

          <%@include file="orderDetailsListCoreHeader.jsp" %>

          <%-- ADD HERE NEW COLUMNS HEADER  --%>

          <%@include file="orderDetailsListButtonsHeader.jsp" %>

      </tr>
    </thead>
    <tbody>

      <c:forEach var="orderDetails" items="${orderDetailsItems}">

        <tr id="orderDetails_rowid_${orderDetails.orderdetailid}">
          <%@include file="orderDetailsListCoreMultipleSelect.jsp" %>

          <%@include file="orderDetailsListCoreContent.jsp" %>

          <%--  ADD HERE NEW COLUMNS CONTENT --%>


          <%@include file="orderDetailsListButtons.jsp" %>

            
        </tr>

      </c:forEach>

    </tbody>
  </table>
  </div>
  </c:if>
  
