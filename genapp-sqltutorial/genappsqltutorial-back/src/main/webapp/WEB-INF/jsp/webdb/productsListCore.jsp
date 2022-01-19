  <c:if test="${empty productsItems}">
     <%@include file="productsListEmpty.jsp" %>

  </c:if>
  
  <c:if test="${not empty productsItems}">

  <div class="row" style="margin-left: 0px;">
  <table class="table table-sm table-bordered table-striped table-genapp" style="width:auto;"> 
    <thead>
      <tr>

          <%@include file="productsListCoreHeaderMultipleSelect.jsp" %>

          <%@include file="productsListCoreHeader.jsp" %>

          <%-- ADD HERE NEW COLUMNS HEADER  --%>

          <%@include file="productsListButtonsHeader.jsp" %>

      </tr>
    </thead>
    <tbody>

      <c:forEach var="products" items="${productsItems}">

        <tr id="products_rowid_${products.productid}">
          <%@include file="productsListCoreMultipleSelect.jsp" %>

          <%@include file="productsListCoreContent.jsp" %>

          <%--  ADD HERE NEW COLUMNS CONTENT --%>


          <%@include file="productsListButtons.jsp" %>

            
        </tr>

      </c:forEach>

    </tbody>
  </table>
  </div>
  </c:if>
  
