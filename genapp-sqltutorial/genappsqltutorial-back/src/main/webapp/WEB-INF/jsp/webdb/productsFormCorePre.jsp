<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>
<un:useConstants var="ProductsFields" className="org.fundaciobit.genappsqltutorial.model.fields.ProductsFields"/>
  
  <c:set var="__theForm" value="${productsForm}"/>

  <div class="module_content">
    <div class="tab_container">
    
    <c:if test="${not __theForm.view}">    <c:forEach items="${__theForm.hiddenFields}" var="hiddenFieldF" >
      <c:set  var="hiddenField" value="${hiddenFieldF.javaName}" />
      <c:if test="${gen:hasProperty(__theForm.products,hiddenField)}">
        <form:errors path="products.${hiddenField}" cssClass="errorField alert alert-danger" />
        <form:hidden path="products.${hiddenField}"/>
      </c:if>
    </c:forEach>
    </c:if>

    <form:errors cssClass="errorField alert alert-danger" delimiter="&lt;p/&gt;" />
    <table class="tdformlabel table-condensed table table-bordered table-striped marTop10  " > 
    <tbody>      
