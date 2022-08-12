<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>
<un:useConstants var="OrderDetailsFields" className="org.fundaciobit.genappsqltutorial.model.fields.OrderDetailsFields"/>
  
        <c:if test="${!gen:contains(__theForm.hiddenFields,OrderDetailsFields.ORDERID)}">
        <tr id="orderDetails_orderid_rowid">
          <td id="orderDetails_orderid_columnlabelid">
            <label>
              <fmt:message key="${(empty __theForm.labels[OrderDetailsFields.ORDERID])?'orderDetails.orderid':__theForm.labels[OrderDetailsFields.ORDERID]}" />
             </label>
              <c:if test="${not empty __theForm.help[OrderDetailsFields.ORDERID]}">
              <i class="fas fa-info-circle" title="${__theForm.help[OrderDetailsFields.ORDERID]}" ></i>
              </c:if>
            </td>
          <td id="orderDetails_orderid_columnvalueid">
          <form:errors path="orderDetails.orderid" cssClass="errorField alert alert-danger" />
          <c:if test="${gen:contains(__theForm.readOnlyFields ,OrderDetailsFields.ORDERID)}" >
          <form:hidden path="orderDetails.orderid"/>
          <input type="text" readonly="true" class="form-control col-md-9-optional uneditable-input" value="${gen:findValue(__theForm.orderDetails.orderid,__theForm.listOfOrdersForOrderid)}"  />
          </c:if>
          <c:if test="${!gen:contains(__theForm.readOnlyFields ,OrderDetailsFields.ORDERID)}" >
          <c:set var="containEmptyValue"  value="false" />
          <form:select id="orderDetails_orderid"  onchange="if(typeof onChangeOrderid == 'function') {  onChangeOrderid(this); };"  cssClass="form-control col-md-9-optional" path="orderDetails.orderid">
            <c:forEach items="${__theForm.listOfOrdersForOrderid}" var="tmp">
                <form:option value="${tmp.key}">${tmp.value}</form:option>
                <c:if test="${empty tmp.key}">
                  <c:set var="containEmptyValue"  value="true" />
                </c:if>
            </c:forEach>
            <%-- El camp pot ser null, per la qual cosa afegim una entrada buida si no s'ha definit abans --%>
            <c:if test="${not containEmptyValue}">
              <c:if test="${empty __theForm.orderDetails.orderid }">
                  <form:option value="" selected="true" ></form:option>
              </c:if>
              <c:if test="${not empty __theForm.orderDetails.orderid }">
                  <form:option value="" ></form:option>
              </c:if>
            </c:if>
          </form:select>
          </c:if>
           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,OrderDetailsFields.PRODUCTID)}">
        <tr id="orderDetails_productid_rowid">
          <td id="orderDetails_productid_columnlabelid">
            <label>
              <fmt:message key="${(empty __theForm.labels[OrderDetailsFields.PRODUCTID])?'orderDetails.productid':__theForm.labels[OrderDetailsFields.PRODUCTID]}" />
             </label>
              <c:if test="${not empty __theForm.help[OrderDetailsFields.PRODUCTID]}">
              <i class="fas fa-info-circle" title="${__theForm.help[OrderDetailsFields.PRODUCTID]}" ></i>
              </c:if>
            </td>
          <td id="orderDetails_productid_columnvalueid">
          <form:errors path="orderDetails.productid" cssClass="errorField alert alert-danger" />
          <c:if test="${gen:contains(__theForm.readOnlyFields ,OrderDetailsFields.PRODUCTID)}" >
          <form:hidden path="orderDetails.productid"/>
          <input type="text" readonly="true" class="form-control col-md-9-optional uneditable-input" value="${gen:findValue(__theForm.orderDetails.productid,__theForm.listOfProductsForProductid)}"  />
          </c:if>
          <c:if test="${!gen:contains(__theForm.readOnlyFields ,OrderDetailsFields.PRODUCTID)}" >
          <c:set var="containEmptyValue"  value="false" />
          <form:select id="orderDetails_productid"  onchange="if(typeof onChangeProductid == 'function') {  onChangeProductid(this); };"  cssClass="form-control col-md-9-optional" path="orderDetails.productid">
            <c:forEach items="${__theForm.listOfProductsForProductid}" var="tmp">
                <form:option value="${tmp.key}">${tmp.value}</form:option>
                <c:if test="${empty tmp.key}">
                  <c:set var="containEmptyValue"  value="true" />
                </c:if>
            </c:forEach>
            <%-- El camp pot ser null, per la qual cosa afegim una entrada buida si no s'ha definit abans --%>
            <c:if test="${not containEmptyValue}">
              <c:if test="${empty __theForm.orderDetails.productid }">
                  <form:option value="" selected="true" ></form:option>
              </c:if>
              <c:if test="${not empty __theForm.orderDetails.productid }">
                  <form:option value="" ></form:option>
              </c:if>
            </c:if>
          </form:select>
          </c:if>
           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,OrderDetailsFields.QUANTITY)}">
        <tr id="orderDetails_quantity_rowid">
          <td id="orderDetails_quantity_columnlabelid">
            <label>
              <fmt:message key="${(empty __theForm.labels[OrderDetailsFields.QUANTITY])?'orderDetails.quantity':__theForm.labels[OrderDetailsFields.QUANTITY]}" />
             </label>
              <c:if test="${not empty __theForm.help[OrderDetailsFields.QUANTITY]}">
              <i class="fas fa-info-circle" title="${__theForm.help[OrderDetailsFields.QUANTITY]}" ></i>
              </c:if>
            </td>
          <td id="orderDetails_quantity_columnvalueid">
            <form:errors path="orderDetails.quantity" cssClass="errorField alert alert-danger" />
            <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,OrderDetailsFields.QUANTITY)? 'true' : 'false'}" cssClass="w-25 form-control  ${gen:contains(__theForm.readOnlyFields ,OrderDetailsFields.QUANTITY)? ' uneditable-input' : ''}"  style=""  path="orderDetails.quantity"   />

           </td>
        </tr>
        </c:if>
        
