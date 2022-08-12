  <c:if test="${empty suppliersItems}">
     <%@include file="suppliersListEmpty.jsp" %>

  </c:if>
  
  <c:if test="${not empty suppliersItems}">

  <div class="row" style="margin-left: 0px;">
  <table class="table table-sm table-bordered table-striped table-genapp" style="width:auto;"> 
    <thead>
      <tr>

          <%@include file="suppliersListCoreHeaderMultipleSelect.jsp" %>

          <%@include file="suppliersListCoreHeader.jsp" %>

          <%-- ADD HERE NEW COLUMNS HEADER  --%>

          <%@include file="suppliersListButtonsHeader.jsp" %>

      </tr>
    </thead>
    <tbody>

      <c:forEach var="suppliers" items="${suppliersItems}">

        <tr id="suppliers_rowid_${suppliers.supplierid}">
          <%@include file="suppliersListCoreMultipleSelect.jsp" %>

          <%@include file="suppliersListCoreContent.jsp" %>

          <%--  ADD HERE NEW COLUMNS CONTENT --%>


          <%@include file="suppliersListButtons.jsp" %>


        </tr>

      </c:forEach>

    </tbody>
  </table>
  </div>
  </c:if>
  
