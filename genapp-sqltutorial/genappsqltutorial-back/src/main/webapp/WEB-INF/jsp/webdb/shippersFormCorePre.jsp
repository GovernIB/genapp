<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>
<un:useConstants var="ShippersFields" className="org.fundaciobit.genappsqltutorial.model.fields.ShippersFields"/>
  
  <c:set var="__theForm" value="${shippersForm}"/>

  <div class="module_content">
    <div class="tab_container">
    
    <c:if test="${not __theForm.view}">    <c:forEach items="${__theForm.hiddenFields}" var="hiddenFieldF" >
      <c:set  var="hiddenField" value="${hiddenFieldF.javaName}" />
      <c:if test="${gen:hasProperty(__theForm.shippers,hiddenField)}">
        <form:errors path="shippers.${hiddenField}" cssClass="errorField alert alert-danger" />
        <form:hidden path="shippers.${hiddenField}"/>
      </c:if>
    </c:forEach>
    </c:if>

    <form:errors cssClass="errorField alert alert-danger" delimiter="&lt;p/&gt;" />
    <table class="tdformlabel table-sm table table-bordered table-striped marTop10 table-responsive" > 
    <tbody>      

