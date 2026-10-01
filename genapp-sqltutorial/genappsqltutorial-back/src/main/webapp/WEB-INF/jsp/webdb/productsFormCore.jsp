<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>
<un:useConstants var="ProductsFields" className="org.fundaciobit.genappsqltutorial.model.fields.ProductsFields"/>
  
        <c:if test="${!gen:contains(__theForm.hiddenFields,ProductsFields.PRODUCTNAME)}">
        <tr id="products_productname_rowid">
          <td id="products_productname_columnlabelid">
            <label>
              <fmt:message key="${(empty __theForm.labels[ProductsFields.PRODUCTNAME])?'products.productname':__theForm.labels[ProductsFields.PRODUCTNAME]}" />
             </label>
              <c:if test="${not empty __theForm.help[ProductsFields.PRODUCTNAME]}">
              <i class="fas fa-info-circle" title="${__theForm.help[ProductsFields.PRODUCTNAME]}" ></i>
              </c:if>
            </td>
          <td id="products_productname_columnvalueid">
            <form:errors path="products.productname" cssClass="errorField alert alert-danger" />
            <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,ProductsFields.PRODUCTNAME)? 'true' : 'false'}" cssClass="w-100 form-control  ${gen:contains(__theForm.readOnlyFields ,ProductsFields.PRODUCTNAME)? ' uneditable-input' : ''}"  style="" maxlength="255" path="products.productname"   />

           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,ProductsFields.SUPPLIERID)}">
        <tr id="products_supplierid_rowid">
          <td id="products_supplierid_columnlabelid">
            <label>
              <fmt:message key="${(empty __theForm.labels[ProductsFields.SUPPLIERID])?'products.supplierid':__theForm.labels[ProductsFields.SUPPLIERID]}" />
             </label>
              <c:if test="${not empty __theForm.help[ProductsFields.SUPPLIERID]}">
              <i class="fas fa-info-circle" title="${__theForm.help[ProductsFields.SUPPLIERID]}" ></i>
              </c:if>
            </td>
          <td id="products_supplierid_columnvalueid">
            <form:errors path="products.supplierid" cssClass="errorField alert alert-danger" />
            <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,ProductsFields.SUPPLIERID)? 'true' : 'false'}" cssClass="w-25 form-control  ${gen:contains(__theForm.readOnlyFields ,ProductsFields.SUPPLIERID)? ' uneditable-input' : ''}"  style=""  path="products.supplierid"   />

           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,ProductsFields.CATEGORYID)}">
        <tr id="products_categoryid_rowid">
          <td id="products_categoryid_columnlabelid">
            <label>
              <fmt:message key="${(empty __theForm.labels[ProductsFields.CATEGORYID])?'products.categoryid':__theForm.labels[ProductsFields.CATEGORYID]}" />
             </label>
              <c:if test="${not empty __theForm.help[ProductsFields.CATEGORYID]}">
              <i class="fas fa-info-circle" title="${__theForm.help[ProductsFields.CATEGORYID]}" ></i>
              </c:if>
            </td>
          <td id="products_categoryid_columnvalueid">
            <form:errors path="products.categoryid" cssClass="errorField alert alert-danger" />
            <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,ProductsFields.CATEGORYID)? 'true' : 'false'}" cssClass="w-25 form-control  ${gen:contains(__theForm.readOnlyFields ,ProductsFields.CATEGORYID)? ' uneditable-input' : ''}"  style=""  path="products.categoryid"   />

           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,ProductsFields.UNIT)}">
        <tr id="products_unit_rowid">
          <td id="products_unit_columnlabelid">
            <label>
              <fmt:message key="${(empty __theForm.labels[ProductsFields.UNIT])?'products.unit':__theForm.labels[ProductsFields.UNIT]}" />
             </label>
              <c:if test="${not empty __theForm.help[ProductsFields.UNIT]}">
              <i class="fas fa-info-circle" title="${__theForm.help[ProductsFields.UNIT]}" ></i>
              </c:if>
            </td>
          <td id="products_unit_columnvalueid">
            <form:errors path="products.unit" cssClass="errorField alert alert-danger" />
            <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,ProductsFields.UNIT)? 'true' : 'false'}" cssClass="w-100 form-control  ${gen:contains(__theForm.readOnlyFields ,ProductsFields.UNIT)? ' uneditable-input' : ''}"  style="" maxlength="255" path="products.unit"   />

           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,ProductsFields.PRICE)}">
        <tr id="products_price_rowid">
          <td id="products_price_columnlabelid">
            <label>
              <fmt:message key="${(empty __theForm.labels[ProductsFields.PRICE])?'products.price':__theForm.labels[ProductsFields.PRICE]}" />
             </label>
              <c:if test="${not empty __theForm.help[ProductsFields.PRICE]}">
              <i class="fas fa-info-circle" title="${__theForm.help[ProductsFields.PRICE]}" ></i>
              </c:if>
            </td>
          <td id="products_price_columnvalueid">
            <form:errors path="products.price" cssClass="errorField alert alert-danger" />
            <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,ProductsFields.PRICE)? 'true' : 'false'}" cssClass="w-50 form-control  ${gen:contains(__theForm.readOnlyFields ,ProductsFields.PRICE)? ' uneditable-input' : ''}"  style=""  path="products.price"   />

           </td>
        </tr>
        </c:if>
        
