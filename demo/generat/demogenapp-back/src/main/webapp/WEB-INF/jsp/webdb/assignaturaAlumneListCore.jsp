  <c:if test="${empty assignaturaAlumneItems}">
     <%@include file="assignaturaAlumneListEmpty.jsp" %>

  </c:if>
  
  <c:if test="${not empty assignaturaAlumneItems}">

  <div class="row" style="margin-left: 0px;">
  <c:if test="${__theFilterForm.visibleMultipleSelection}">
  <fieldset>
  <legend class="sr-only">Selecció d'elements de la llista</legend>
  </c:if>
  <table class="table table-sm table-bordered table-striped table-genapp table-genapp-list" style="width:auto;"> 
    <thead>
      <tr>

          <%@include file="assignaturaAlumneListCoreHeaderMultipleSelect.jsp" %>

          <%@include file="assignaturaAlumneListCoreHeader.jsp" %>

          <%-- ADD HERE NEW COLUMNS HEADER  --%>

          <%@include file="assignaturaAlumneListButtonsHeader.jsp" %>

      </tr>
    </thead>
    <tbody>

      <c:forEach var="assignaturaAlumne" items="${assignaturaAlumneItems}">

        <tr id="assignaturaAlumne_rowid_${assignaturaAlumne.id}">
          <%@include file="assignaturaAlumneListCoreMultipleSelect.jsp" %>

          <%@include file="assignaturaAlumneListCoreContent.jsp" %>

          <%--  ADD HERE NEW COLUMNS CONTENT --%>


          <%@include file="assignaturaAlumneListButtons.jsp" %>


        </tr>

      </c:forEach>

    </tbody>
  </table>
  <c:if test="${__theFilterForm.visibleMultipleSelection}">
  </fieldset>
  </c:if>
  </div>
  </c:if>
  
