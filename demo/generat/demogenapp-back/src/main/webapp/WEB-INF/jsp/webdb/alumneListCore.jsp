  <c:if test="${empty alumneItems}">
     <%@include file="alumneListEmpty.jsp" %>

  </c:if>
  
  <c:if test="${not empty alumneItems}">

  <div class="row" style="margin-left: 0px;">
  <table class="table table-sm table-bordered table-striped table-genapp table-genapp-list" style="width:auto;"> 
    <thead>
      <tr>

          <%@include file="alumneListCoreHeaderMultipleSelect.jsp" %>

          <%@include file="alumneListCoreHeader.jsp" %>

          <%-- ADD HERE NEW COLUMNS HEADER  --%>

          <%@include file="alumneListButtonsHeader.jsp" %>

      </tr>
    </thead>
    <tbody>

      <c:forEach var="alumne" items="${alumneItems}">

        <tr id="alumne_rowid_${alumne.alumneID}">
          <%@include file="alumneListCoreMultipleSelect.jsp" %>

          <%@include file="alumneListCoreContent.jsp" %>

          <%--  ADD HERE NEW COLUMNS CONTENT --%>


          <%@include file="alumneListButtons.jsp" %>


        </tr>

      </c:forEach>

    </tbody>
  </table>
  </div>
  </c:if>
  
