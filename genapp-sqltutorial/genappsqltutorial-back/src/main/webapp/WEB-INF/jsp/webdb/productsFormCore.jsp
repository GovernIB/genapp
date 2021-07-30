<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>
<un:useConstants var="ProductsFields" className="org.fundaciobit.genappsqltutorial.model.fields.ProductsFields"/>
  
        <c:if test="${!gen:contains(__theForm.hiddenFields,ProductsFields.PRODUCTNAME)}">
        <tr id="products_productname_rowid">
          <td>
            <label>
              <fmt:message key="${(empty __theForm.labels[ProductsFields.PRODUCTNAME])?'products.productname':__theForm.labels[ProductsFields.PRODUCTNAME]}" />
              <c:if test="${not empty __theForm.help[ProductsFields.PRODUCTNAME]}">
              <i class="fas fa-info-circle" title="${__theForm.help[ProductsFields.PRODUCTNAME]}" ></i>
              </c:if>
             </label>
            </td>
            <td>
            <form:errors path="products.productname" cssClass="errorField alert alert-danger" />
            <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,ProductsFields.PRODUCTNAME)? 'true' : 'false'}" cssClass="form-control ${gen:contains(__theForm.readOnlyFields ,ProductsFields.PRODUCTNAME)? ' uneditable-input' : ''}"  style="" maxlength="255" path="products.productname"   />

           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,ProductsFields.SUPPLIERID)}">
        <tr id="products_supplierid_rowid">
          <td>
            <label>
              <fmt:message key="${(empty __theForm.labels[ProductsFields.SUPPLIERID])?'products.supplierid':__theForm.labels[ProductsFields.SUPPLIERID]}" />
              <c:if test="${not empty __theForm.help[ProductsFields.SUPPLIERID]}">
              <i class="fas fa-info-circle" title="${__theForm.help[ProductsFields.SUPPLIERID]}" ></i>
              </c:if>
             </label>
            </td>
            <td>
            <form:errors path="products.supplierid" cssClass="errorField alert alert-danger" />
            <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,ProductsFields.SUPPLIERID)? 'true' : 'false'}" cssClass="form-control ${gen:contains(__theForm.readOnlyFields ,ProductsFields.SUPPLIERID)? ' uneditable-input' : ''}"  style=""  path="products.supplierid"   />

           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,ProductsFields.CATEGORYID)}">
        <tr id="products_categoryid_rowid">
          <td>
            <label>
              <fmt:message key="${(empty __theForm.labels[ProductsFields.CATEGORYID])?'products.categoryid':__theForm.labels[ProductsFields.CATEGORYID]}" />
              <c:if test="${not empty __theForm.help[ProductsFields.CATEGORYID]}">
              <i class="fas fa-info-circle" title="${__theForm.help[ProductsFields.CATEGORYID]}" ></i>
              </c:if>
             </label>
            </td>
            <td>
            <form:errors path="products.categoryid" cssClass="errorField alert alert-danger" />
            <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,ProductsFields.CATEGORYID)? 'true' : 'false'}" cssClass="form-control ${gen:contains(__theForm.readOnlyFields ,ProductsFields.CATEGORYID)? ' uneditable-input' : ''}"  style=""  path="products.categoryid"   />

           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,ProductsFields.UNIT)}">
        <tr id="products_unit_rowid">
          <td>
            <label>
              <fmt:message key="${(empty __theForm.labels[ProductsFields.UNIT])?'products.unit':__theForm.labels[ProductsFields.UNIT]}" />
              <c:if test="${not empty __theForm.help[ProductsFields.UNIT]}">
              <i class="fas fa-info-circle" title="${__theForm.help[ProductsFields.UNIT]}" ></i>
              </c:if>
             </label>
            </td>
            <td>
            <form:errors path="products.unit" cssClass="errorField alert alert-danger" />
            <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,ProductsFields.UNIT)? 'true' : 'false'}" cssClass="form-control ${gen:contains(__theForm.readOnlyFields ,ProductsFields.UNIT)? ' uneditable-input' : ''}"  style="" maxlength="255" path="products.unit"   />

           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,ProductsFields.PRICE)}">
        <tr id="products_price_rowid">
          <td>
            <label>
              <fmt:message key="${(empty __theForm.labels[ProductsFields.PRICE])?'products.price':__theForm.labels[ProductsFields.PRICE]}" />
              <c:if test="${not empty __theForm.help[ProductsFields.PRICE]}">
              <i class="fas fa-info-circle" title="${__theForm.help[ProductsFields.PRICE]}" ></i>
              </c:if>
             </label>
            </td>
            <td>
            <form:errors path="products.price" cssClass="errorField alert alert-danger" />
            <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,ProductsFields.PRICE)? 'true' : 'false'}" cssClass="form-control ${gen:contains(__theForm.readOnlyFields ,ProductsFields.PRICE)? ' uneditable-input' : ''}"  style=""  path="products.price"   />

           </td>
        </tr>
        </c:if>
        
