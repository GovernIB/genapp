  <c:if test="${empty assignaturaItems}">
     <%@include file="assignaturaListEmpty.jsp" %>

  </c:if>
  
  <c:if test="${not empty assignaturaItems}">

  <div class="row" style="margin-left: 0px;">
  <table class="table table-sm table-bordered table-striped table-genapp table-genapp-list" style="width:auto;"> 
    <thead>
      <tr>

          <%@include file="assignaturaListCoreHeaderMultipleSelect.jsp" %>

          <%@include file="assignaturaListCoreHeader.jsp" %>

          <%-- ADD HERE NEW COLUMNS HEADER  --%>

          <%@include file="assignaturaListButtonsHeader.jsp" %>

      </tr>
    </thead>
    <tbody>

      <c:forEach var="assignatura" items="${assignaturaItems}">

        <tr id="assignatura_rowid_${assignatura.assignaturaID}">
          <%@include file="assignaturaListCoreMultipleSelect.jsp" %>

          <%@include file="assignaturaListCoreContent.jsp" %>

          <%--  ADD HERE NEW COLUMNS CONTENT --%>


          <%@include file="assignaturaListButtons.jsp" %>


        </tr>

      </c:forEach>

    </tbody>
  </table>
  </div>
  </c:if>
  
