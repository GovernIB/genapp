  <c:if test="${empty categoriesItems}">
     <%@include file="categoriesListEmpty.jsp" %>

  </c:if>
  
  <c:if test="${not empty categoriesItems}">

  <div class="row" style="margin-left: 0px;">
  <table class="table table-sm table-bordered table-striped table-genapp" style="width:auto;"> 
    <thead>
      <tr>

          <%@include file="categoriesListCoreHeaderMultipleSelect.jsp" %>

          <%@include file="categoriesListCoreHeader.jsp" %>

          <%-- ADD HERE NEW COLUMNS HEADER  --%>

          <%@include file="categoriesListButtonsHeader.jsp" %>

      </tr>
    </thead>
    <tbody>

      <c:forEach var="categories" items="${categoriesItems}">

        <tr id="categories_rowid_${categories.categoryid}">
          <%@include file="categoriesListCoreMultipleSelect.jsp" %>

          <%@include file="categoriesListCoreContent.jsp" %>

          <%--  ADD HERE NEW COLUMNS CONTENT --%>


          <%@include file="categoriesListButtons.jsp" %>

            
        </tr>

      </c:forEach>

    </tbody>
  </table>
  </div>
  </c:if>
  
