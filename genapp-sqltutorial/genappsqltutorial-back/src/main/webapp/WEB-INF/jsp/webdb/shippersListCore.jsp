  <c:if test="${empty shippersItems}">
     <%@include file="shippersListEmpty.jsp" %>

  </c:if>
  
  <c:if test="${not empty shippersItems}">

  <div class="row" style="margin-left: 0px;">
  <table class="table table-sm table-bordered table-striped table-genapp" style="width:auto;"> 
    <thead>
      <tr>

          <%@include file="shippersListCoreHeaderMultipleSelect.jsp" %>

          <%@include file="shippersListCoreHeader.jsp" %>

          <%-- ADD HERE NEW COLUMNS HEADER  --%>

          <%@include file="shippersListButtonsHeader.jsp" %>

      </tr>
    </thead>
    <tbody>

      <c:forEach var="shippers" items="${shippersItems}">

        <tr id="shippers_rowid_${shippers.shipperid}">
          <%@include file="shippersListCoreMultipleSelect.jsp" %>

          <%@include file="shippersListCoreContent.jsp" %>

          <%--  ADD HERE NEW COLUMNS CONTENT --%>


          <%@include file="shippersListButtons.jsp" %>


        </tr>

      </c:forEach>

    </tbody>
  </table>
  </div>
  </c:if>
  
